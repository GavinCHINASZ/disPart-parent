package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dispart.dao.DataMerchantMapper;
import com.dispart.dao.DataMerchantMonthCountMapper;

import com.dispart.modle.DataMerchant;
import com.dispart.modle.DataMerchantMonthCount;
import com.dispart.modle.dto.Disp20210347InDto;
import com.dispart.modle.dto.Disp20210347OutDto;
import com.dispart.modle.vo.Disp20210347InVo;
import com.dispart.modle.vo.Disp20210347TransInfoOutVo;
import com.dispart.result.Result;
import com.dispart.service.GnYunDaiService;
import com.dispart.util.DESUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static com.dispart.enums.DataMerchantEnum.*;

@Slf4j
@Service
public class GnYunDaiServiceImpl implements GnYunDaiService {

    private static final int BEGIN_DATE = -12; //开始日期: T-12
    private static final int END_DATE = -1; //结束日期: T-1

    @Resource
    private DataMerchantMapper dataMerchantMapper;

    @Resource
    private DataMerchantMonthCountMapper dataMerchantMonthCountMapper;

    @Override
    public String getBorrowerInfo(String param) {

        log.info("建行内网与物流园平台查询客户借贷查询接口开始，参数：" + param);
        String decrypt = DESUtil.decrypt(param);
        log.debug("解密后的请求参数" + decrypt);
        Disp20210347InDto inDto = JSONObject.parseObject(decrypt, Disp20210347InDto.class);

        if (StringUtils.isEmpty(inDto.getCst_Lgl_Nm())){
            return JSON.toJSONString(new Result(306,"借款人名称不能为空",null,false));
        }
        if (StringUtils.isEmpty(inDto.getCrdt_No())){
            return JSON.toJSONString(new Result(306,"借款人证件号码不能为空",null,false));
        }
        if (StringUtils.isEmpty(inDto.getEnqr_Dt_BgDy())){
        }
        log.info("开始进行参数转换");
        Disp20210347InVo inVo = new Disp20210347InVo();
        inVo.setLegalPerson(inDto.getCst_Lgl_Nm());
        inVo.setIdCard(inDto.getCrdt_No());
        inVo.setIdCardLowCase(inDto.getCrdt_No().toLowerCase());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        if (StringUtils.isEmpty(inDto.getEnqr_Dt_BgDy())){
            log.info("未传入日期参数，当前日期往前推12个月作为查询开始日期");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MONTH, BEGIN_DATE);
            inVo.setBeginDateMonth(df.format(calendar.getTime()));
            calendar.setTime(new Date());
            calendar.add(Calendar.MONTH,END_DATE);
            log.info("----------------------------" + calendar.getTime());
            inVo.setQueryDate(df.format(calendar.getTime()));
        }else {
            log.info("传入查询日期，按查询日期到T-1查询");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MONTH, END_DATE);
            inVo.setQueryDate(df.format(calendar.getTime()));
            inVo.setBeginDateMonth(inDto.getEnqr_Dt_BgDy());
        }
        DataMerchant borrowerInfo;
        ArrayList<DataMerchantMonthCount> dataMerchantMonthCount;
        BigDecimal TAmt;
        try{
            borrowerInfo = dataMerchantMapper.getBorrowerInfo(inVo);
            if (borrowerInfo == null){
                log.info("未查询到该商户信息");
                return JSON.toJSONString(new Result(308,"未查询到该商户信息或商户信息不全",null,false));
            }
            inVo.setCode(borrowerInfo.getCode());
            dataMerchantMonthCount = dataMerchantMonthCountMapper.getTransInfo(inVo);
            if (dataMerchantMonthCount.size()>0){
                TAmt = dataMerchantMonthCountMapper.getTransTolAmt(inVo);
            }else {
                return JSON.toJSONString(new Result(308,"商户无交易数据",null,false));
            }
        }catch (Exception e){
            log.error("数据库异常");
            throw new RuntimeException(e);
        }

        log.info("开始转换响应报文");
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Disp20210347OutDto outDto = new Disp20210347OutDto();
        outDto.setCstLglNm(borrowerInfo.getLegalPerson());
        outDto.setCrdtNo(borrowerInfo.getIdCard().toUpperCase());
        outDto.setEntNm(borrowerInfo.getCname());
        outDto.setSignStDt(date.format(borrowerInfo.getEntryDate()));
        outDto.setTAmt(TAmt.toString());
        List<Disp20210347TransInfoOutVo> transInfo = new ArrayList();
        Disp20210347TransInfoOutVo transInfoOutVo;
        for (DataMerchantMonthCount count : dataMerchantMonthCount){
            transInfoOutVo = new Disp20210347TransInfoOutVo();
            transInfoOutVo.setStatMo(count.getCountMonth());
            transInfoOutVo.setTxnAmt(count.getTransAmount().toString());
            transInfo.add(transInfoOutVo);
        }
        outDto.setTxnInf(transInfo);
        if (borrowerInfo.getVarietyType().equals(VARIETY_TYPE_VEGETABLE.getCode())){
            outDto.setOprtScop(VARIETY_TYPE_VEGETABLE.getValue());
        }
        if (borrowerInfo.getVarietyType().equals(VARIETY_TYPE_FRUIT.getCode())){
            outDto.setOprtScop(VARIETY_TYPE_FRUIT.getValue());
        }
        if (borrowerInfo.getVarietyType().equals(VARIETY_TYPE_MUSHROOM.getCode())){
            outDto.setOprtScop(VARIETY_TYPE_OTHER.getValue());
        }
        if (borrowerInfo.getVarietyType().equals(VARIETY_TYPE_OTHER.getCode())){
            outDto.setOprtScop(VARIETY_TYPE_OTHER.getValue());
        }
        if (borrowerInfo.getSeasonType().equals(SEASON_TYPE_YES.getCode())){
            outDto.setCstTpNm(SEASON_TYPE_YES.getValue());
        }
        if (borrowerInfo.getSeasonType().equals(SEASON_TYPE_NO.getCode())){
            outDto.setCstTpNm(SEASON_TYPE_NO.getValue());
        }
        if (StringUtils.isEmpty(borrowerInfo.getStatus()) || borrowerInfo.getStatus().equals(STATUS_NORMAL.getCode())){
            outDto.setOprtSttnCd("20");
        }
        if (!borrowerInfo.getStatus().equals(STATUS_NORMAL.getCode())){
            outDto.setOprtSttnCd("16");
        }
        outDto.setCstRkGdCd(borrowerInfo.getRiskLevel());
        if (borrowerInfo.getRentExpireDate() != null){
            outDto.setAR_ExDat(date.format(borrowerInfo.getRentExpireDate()));
        }
        else {
            outDto.setAR_ExDat("");
        }
        if (StringUtils.isEmpty(borrowerInfo.getIsCcb())){
            outDto.setCstTpVerfCd("04");
        }else {
            if (borrowerInfo.getIsCcb().equals(IS_CCB.getCode())){
                outDto.setCstTpVerfCd("04");
            }else {
                outDto.setCstTpVerfCd("05");
            }
        }
        outDto.setCurDt(date.format(new Date()));
        log.info("未加密查询结果为：" + JSON.toJSONString(outDto));
        log.info("开始DES数据加密");
        String outData = DESUtil.encrypt(JSON.toJSONString(outDto));
        String res = JSON.toJSONString(new Result(200,"成功",outData,true));
        log.info("建行内网与物流园平台查询客户借贷查询接口结束，返回：" + res);
        return res;
    }
}
