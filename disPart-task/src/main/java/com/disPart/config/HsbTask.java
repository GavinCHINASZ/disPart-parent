package com.disPart.config;

import com.alibaba.fastjson.JSONObject;
import com.disPart.dao.*;
import com.disPart.enums.OrderTpEnum;
import com.disPart.utils.HSBUtil;
import com.dispart.dto.hsbdto.*;
import com.dispart.vo.commons.BaseMerchant;
import com.dispart.vo.commons.TCustomSignInfo;
import com.dispart.vo.entrance.TCustomInfoManager;
import com.dispart.vo.hsb.PlaceOrderTypeVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author:zhongfei
 * @date:Created in 2021/6/19 3:53
 * @description 惠市宝商户信息查询自动任务（同步商户签约信息）
 * @modified by:
 * @version: 1.0
 */
@Component
@Slf4j
public class HsbTask extends AbstractTask implements ISchedule {

    @Autowired
    @Qualifier("restTemplate1")
    private RestTemplate restTemplate;
    @Resource
    private TCustomInfoManagerMapper tCustomInfoManagerMapper;
    @Resource
    private TCustomSignInfoMapper tCustomSignInfoDao;
    @Resource
    private ParmeterInfoMapper parmeterInfoMapper;
    @Value("${out-hsb.url}")
    private String outHsbUrl;
    @Resource
    TPlaceOrderTypeMapper tPlaceOrderTypeMapper;

    @Override
    @Scheduled(cron = "0 0/10 * * * ?")
//    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void doScheduled() {
        super.doScheduled();
    }

    //上一次执行开始时间点之后3个小时再执行
    @Transactional
    @Override
    protected void task() {
        // 查询t_parmeter_info    02	hsb.marketNumber	41060860898189	惠市宝签约的平台市场编号
        //查询市场编号
        String paramVal = parmeterInfoMapper.selectByPrimaryKey("02", "hsb.marketNumber");
        //时间取当天+前29天
        QuryCustomInfoHsbReqDto hsbDto = new QuryCustomInfoHsbReqDto();
        String sndDtTm = HSBUtil.getTimeStamp();//发起方时间戳
        int hsbReqId = tCustomSignInfoDao.selectHSBReqId();//取请求id
        hsbDto.setSndDtTm(sndDtTm);//发起方时间戳
        hsbDto.setSndTraceId(HSBUtil.getTimeStampSeq(hsbReqId));//发起方流水号
        hsbDto.setStartDt(HSBUtil.getDayBeforeDate(29));//开始时间
        hsbDto.setEndDt(HSBUtil.getDate8());//结束时间
        hsbDto.setMarketId(paramVal);//惠市宝市场编号
//        hsbDto.setDelFlag(params.getDelFlag());//删除标志
//        hsbDto.setProvCustId(params.getProvCustId());//商家自定义编号
        hsbDto.setVersion("03");//版本号
        ObjectMapper mapper = new ObjectMapper();
        QuryCustomInfoHsbResDto response = null;
        String res = null;
        try {
            log.info("请求out-hsb url： " + outHsbUrl);
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<QuryCustomInfoHsbReqDto> request = new HttpEntity<>(hsbDto, header);
            log.info("请求参数：" + JSONObject.toJSONString(hsbDto));
            res = restTemplate.postForObject(outHsbUrl, request, String.class);
            log.info("返回json信息： " + res);
            //将json字符转为实体
            response = mapper.readValue(res, QuryCustomInfoHsbResDto.class);
        } catch (Exception e) {
            log.error("请求out-hsb失败" + e);
            return;
        }
        log.info("out-hsb响应状态为：" + response.getTxnSt());
        if (!"00".equals(response.getTxnSt()) || response.getRecordNum() <= 0) {
            return;
        }
        for (QuryCustomInfoHsbResParamDto resDto : response.getList()) {
            TCustomSignInfo tCustomSignInfo = new TCustomSignInfo();
            tCustomSignInfo.setProvId(resDto.getProvId());
            tCustomSignInfo.setDelFlag(resDto.getDelFlag());
            tCustomSignInfo.setProvNm(resDto.getProvNm());
            tCustomSignInfo.setPosNo(resDto.getPosNo());
            tCustomSignInfo.setProvCertTp(resDto.getProvCertId());
            tCustomSignInfo.setProvCertNo(resDto.getProvCertNo());
            tCustomSignInfo.setProvCntNo(resDto.getProvCntNo());
            tCustomSignInfo.setCertTp(resDto.getCertType());
            tCustomSignInfo.setCertNum(resDto.getCertNum());
            tCustomSignInfo.setContacts(resDto.getContacts());
            tCustomSignInfo.setTelehone(resDto.getTelephone());
            tCustomSignInfo.setProvCustId(resDto.getProvCustId());
            tCustomSignInfo.setUpdateDt(new Date());
            tCustomSignInfo.setRemark("数据来源：惠市宝商户信息查询");
            if (StringUtils.isEmpty(resDto.getProvCustId())) {
                log.error(resDto.getProvId() + ":   自定义编号为空");
                continue;
            }
            //处理绑定关系
            try {
                TCustomInfoManager baseMerchant = new TCustomInfoManager();
                baseMerchant.setCertNum(resDto.getProvCustId());
                List<TCustomInfoManager> baseMerchants = tCustomInfoManagerMapper.selectByCertNum(baseMerchant.getCertNum());
                if (baseMerchants != null && baseMerchants.size() > 0) {
                    tCustomSignInfo.setMerchantCode(baseMerchants.get(0).getProvId());
                    TCustomSignInfo quryVo = tCustomSignInfoDao.queryById(resDto.getProvId());
                    if (ObjectUtils.isEmpty(quryVo)) {
                        tCustomSignInfoDao.insert(tCustomSignInfo);
                        //设置下单模式
                        setPlaceOrderType(baseMerchants.get(0).getProvId());
                    } else {
                        tCustomSignInfoDao.update(tCustomSignInfo);
                    }
                } else {
                    log.info("商家自定义编号：" + resDto.getProvCustId() + "同步失败,未查询到客户信息");
                }
            } catch (Exception e) {
                log.error("同步商家编号:" + resDto.getProvCustId() + "异常:", e);
            }
        }
    }

    /**
     * 设置客户默认下单模式
     *
     * @param sysProvId
     */
    private void setPlaceOrderType(String sysProvId) {
        //查询当前是否存在下单模式
        String datilType = OrderTpEnum.DATIL.getCode();
        String easyType = OrderTpEnum.EASY.getCode();

        //明细模式
        PlaceOrderTypeVo quryDatilPlaceOrderType = tPlaceOrderTypeMapper.selectByPrimaryKey(sysProvId, datilType);
        //简易模式
        PlaceOrderTypeVo quryEasyPlaceOrderType = tPlaceOrderTypeMapper.selectByPrimaryKey(sysProvId, easyType);
        //两种下单模式都不存在，插入默认明细模式
        if (ObjectUtils.isEmpty(quryDatilPlaceOrderType) && ObjectUtils.isEmpty(quryEasyPlaceOrderType)) {
            PlaceOrderTypeVo placeOrderType = new PlaceOrderTypeVo();
            placeOrderType.setProvId(sysProvId);
            placeOrderType.setPlaceOrderMd(datilType);//明细模式
            placeOrderType.setPlaceOrderNm(OrderTpEnum.DATIL.getDesc());
            placeOrderType.setStatus(OrderTpEnum.EFFE.getCode());//启用
            placeOrderType.setUpdateDt(new Date());
            tPlaceOrderTypeMapper.insert(placeOrderType);
        } else {//存在一种下单模式或两种下单模式
            if (ObjectUtils.isEmpty(quryEasyPlaceOrderType)) {//存在明细模式
                if (!OrderTpEnum.EFFE.getCode().equals(quryDatilPlaceOrderType.getStatus())) {
                    quryDatilPlaceOrderType.setStatus(OrderTpEnum.EFFE.getCode());
                    quryDatilPlaceOrderType.setUpdateDt(new Date());
                    tPlaceOrderTypeMapper.updateByPrimaryKey(quryDatilPlaceOrderType);
                }
            } else if (ObjectUtils.isEmpty(quryDatilPlaceOrderType) &&
                    !OrderTpEnum.EFFE.getCode().equals(quryEasyPlaceOrderType.getStatus())) {//存在简易模式
                quryDatilPlaceOrderType = new PlaceOrderTypeVo();
                quryDatilPlaceOrderType.setProvId(sysProvId);
                quryDatilPlaceOrderType.setPlaceOrderMd(datilType);//明细模式
                quryDatilPlaceOrderType.setPlaceOrderNm(OrderTpEnum.DATIL.getDesc());
                quryDatilPlaceOrderType.setStatus(OrderTpEnum.EFFE.getCode());//启用
                quryDatilPlaceOrderType.setUpdateDt(new Date());
                tPlaceOrderTypeMapper.insert(quryDatilPlaceOrderType);
            } else if (!ObjectUtils.isEmpty(quryDatilPlaceOrderType) && !ObjectUtils.isEmpty(quryEasyPlaceOrderType)) {//存在两种下单模式
                if ((!OrderTpEnum.EFFE.getCode().equals(quryDatilPlaceOrderType.getStatus())
                        && !OrderTpEnum.EFFE.getCode().equals(quryEasyPlaceOrderType.getStatus())))
                    quryDatilPlaceOrderType.setStatus(OrderTpEnum.EFFE.getCode());
                quryDatilPlaceOrderType.setUpdateDt(new Date());
                tPlaceOrderTypeMapper.updateByPrimaryKey(quryDatilPlaceOrderType);
            }
        }


    }


    @Override
    protected String getLockName() {
        String lockName = "synCustomSingTask";
        log.debug("获取任务锁:{}", lockName);
        return lockName;
    }

    @Override
    protected boolean judgeTaskStatus(String lockName) {
        return true;
    }

    @Override
    protected String getServiceName() {
        return "synCustomSingTask";
    }


}
