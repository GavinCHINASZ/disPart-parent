package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.dao.IBaseMerchantDao;
import com.dispart.dao.TCustomSignInfoDao;
import com.dispart.dto.ResultOutDto;
import com.dispart.dto.hsbdto.*;
import com.dispart.result.BusineConmonCodeEnum;
import com.dispart.result.Result;
import com.dispart.service.HsbService;
import com.dispart.utils.HSBUtil;
import com.dispart.vo.commons.BaseMerchant;
import com.dispart.vo.commons.TCustomSignInfo;
import com.dispart.vo.hsb.HsbCustomVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author:xts
 * @date:Created in 2021/6/13 21:42
 * @description 惠市宝交易接口实现
 * @modified by:
 * @version: 1.0
 */
@Service
@Slf4j
public class HsbServiceImpl implements HsbService {
    @Resource
    private IBaseMerchantDao iBaseMerchantDao;
    @Resource
    private TCustomSignInfoDao tCustomSignInfoDao;
    @Resource
    private RestTemplate restTemplate;
    @Value("${out-hsb.url}")
    private String outHsbUrl;
    /**
     * 惠市宝客户信息增量交易回调接口
     * @param params json格式请求参数
     * @return
     */
    @Override
    public String customInfoCallback(String params) {
        HsbCustomDto hsbCustomDto=new HsbCustomDto();
        if(StringUtils.isBlank(params)){
            hsbCustomDto.setErrCode("01");
            hsbCustomDto.setErrMsg("请求参数为空");
            hsbCustomDto.setTxnSt("01");//失败
            return  JSON.toJSONString(hsbCustomDto);
        }
        //将请求json数组转为对象数组
        HsbCustomVo hsbCustomVo= JSON.parseObject(params,HsbCustomVo.class);
        /**
         * 入库规则：将惠市宝签约商户信息与平台商户信息以证件号进行比对，若匹配上的商户将进行绑定入库，若匹配不上的商户，将不进行绑定入库
         */
        try {
            TCustomSignInfo tCustomSignInfo=new TCustomSignInfo();
            tCustomSignInfo.setDelFlag(hsbCustomVo.getDelFlag());
            tCustomSignInfo.setContacts(hsbCustomVo.getContacts());
            tCustomSignInfo.setCertTp(hsbCustomVo.getCertType());
            tCustomSignInfo.setCertNum(hsbCustomVo.getCertNum());
            tCustomSignInfo.setPosNo(hsbCustomVo.getPosNo());
            tCustomSignInfo.setProvCertNo(hsbCustomVo.getProvCertNo());
            tCustomSignInfo.setProvCertTp(hsbCustomVo.getProvCertld());
            tCustomSignInfo.setProvCustId(hsbCustomVo.getProvCustld());
            tCustomSignInfo.setRemark("数据来源：惠市宝增量更新");
            tCustomSignInfo.setTelehone(hsbCustomVo.getTelephone());
            tCustomSignInfo.setUpdateDt(new Date());
            tCustomSignInfo.setProvCntNo(hsbCustomVo.getProvCntNo());
            tCustomSignInfo.setProvNm(hsbCustomVo.getProvNm());
            tCustomSignInfo.setProvId(hsbCustomVo.getProvId());
            //处理绑定关系
            BaseMerchant baseMerchant=new BaseMerchant();
            baseMerchant.setIdcard(hsbCustomVo.getCertNum());
            List<BaseMerchant> baseMerchants=iBaseMerchantDao. queryAll( baseMerchant);
            if(baseMerchants!=null&&baseMerchants.size()>0){
                tCustomSignInfo.setMerchantCode(baseMerchants.get(0).getMerchantcode());
            }
            tCustomSignInfoDao.insert( tCustomSignInfo);
        }catch (Exception e){
            log.error("新增签约客户信息失败",e);
            hsbCustomDto.setTxnSt("01");//成功
            return  JSON.toJSONString(hsbCustomDto);
        }
        hsbCustomDto.setTxnSt("00");//成功
        return  JSON.toJSONString(hsbCustomDto);
    }
    /**
     * 惠市宝商户信息查询接口
     *
     * @param params json格式请求参数
     * @return
     */
    @Override
    public Result<HsbCustomDto> customInfoQury(QuryCustomInfoInDto params) {
        HsbCustomDto outDto = new HsbCustomDto();
        if (ObjectUtils.isEmpty(params)||StringUtils.isBlank(params.getStartDt()) || StringUtils.isBlank(params.getEndDt()) || StringUtils.isBlank(params.getMarketId())) {
            log.info("业务区-公共服务-" + BusineConmonCodeEnum.PARAM_NULL.getMessage());
            return Result.build(BusineConmonCodeEnum.PARAM_NULL.getCode(), BusineConmonCodeEnum.PARAM_NULL.getMessage());
        }
        QuryCustomInfoHsbReqDto hsbDto = new QuryCustomInfoHsbReqDto();
        String sndDtTm = HSBUtil.getTimeStamp();//发起方时间戳
        int hsbReqId = tCustomSignInfoDao.selectHSBReqId();//取请求id
        hsbDto.setSndDtTm(sndDtTm);//发起方时间戳
        hsbDto.setSndTraceId(HSBUtil.getTimeStampSeq(hsbReqId));//发起方流水号
        hsbDto.setStartDt(params.getStartDt());//开始时间
        hsbDto.setEndDt(params.getEndDt());//结束时间
        hsbDto.setMarketId(params.getMarketId());//惠市宝市场编号
        hsbDto.setDelFlag(params.getDelFlag());//删除标志
        hsbDto.setProvCustId(params.getProvCustId());//商家自定义编号
        hsbDto.setVersion("03");//版本号
        ObjectMapper mapper = new ObjectMapper();
        QuryCustomInfoHsbResDto response = null;
        String res= null;
        try {
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<QuryCustomInfoHsbReqDto> request = new HttpEntity<>(hsbDto, header);
            res = restTemplate.postForObject(outHsbUrl, request, String.class);
            log.info("返回json信息： "+res);
            //将json字符转为实体
            response=mapper.readValue(res,QuryCustomInfoHsbResDto.class);
        } catch (Exception e) {
           log.error("数据插入异常",e);
            return Result.fail();
        }
        outDto.setTxnSt(response.getTxnSt());
        outDto.setErrCode(response.getErrCode());
        outDto.setErrMsg(response.getErrMsg());
        if(!"00".equals(response.getTxnSt())){
            return Result.fail(outDto);
        }
        for(QuryCustomInfoHsbResParamDto resDto:response.getList()){
            TCustomSignInfo tCustomSignInfo = new TCustomSignInfo();
            tCustomSignInfo.setProvId(resDto.getProvId());
            tCustomSignInfo.setDelFlag(resDto.getDelFlag());
            tCustomSignInfo.setProvNm(resDto.getProvNm());
            tCustomSignInfo.setPosNo(resDto.getPosNo());
            tCustomSignInfo.setProvCustId(resDto.getProvCustId());
            tCustomSignInfo.setProvCertNo(resDto.getProvCertNo());
            tCustomSignInfo.setProvCntNo(resDto.getProvCntNo());
            tCustomSignInfo.setCertTp(resDto.getCertType());
            tCustomSignInfo.setCertNum(resDto.getCertNum());
            tCustomSignInfo.setContacts(resDto.getContacts());
            tCustomSignInfo.setTelehone(resDto.getTelephone());
            tCustomSignInfo.setProvCustId(resDto.getProvCustId());
            tCustomSignInfo.setUpdateDt(new Date());
            tCustomSignInfo.setRemark("数据来源：惠市宝商户信息查询");
            if(StringUtils.isEmpty(resDto.getCertNum())){
                log.error("同步商家信息失败，证件号为空,商家编号："+resDto.getProvId()+" 商家名称："+resDto.getProvNm());
                continue;
            }
            //处理绑定关系
            try {
                BaseMerchant baseMerchant = new BaseMerchant();
                baseMerchant.setIdcard(tCustomSignInfo.getCertNum());
                List<BaseMerchant> baseMerchants = iBaseMerchantDao.queryAll(baseMerchant);
                if (baseMerchants != null && baseMerchants.size() > 0) {
                    tCustomSignInfo.setMerchantCode(baseMerchants.get(0).getMerchantcode());
                }
                TCustomSignInfo quryVo = tCustomSignInfoDao.queryById(resDto.getProvId());
                if (ObjectUtils.isEmpty(quryVo)) {
                    tCustomSignInfoDao.insert(tCustomSignInfo);
                } else {
                    tCustomSignInfoDao.update(tCustomSignInfo);
                }
            }catch(Exception e){
                log.error("同步商家信息失败，商家编号："+tCustomSignInfo.getProvId());
               log.error("数据插入异常",e);
            }
        }
        return Result.ok(outDto);
    }

}