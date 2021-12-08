package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.dao.GnydMerchantsInfoMapper;
import com.dispart.dto.GnydMerchantsInfoDto;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.vo.DISP20210604ReqVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 贵农贷服务类
 */
@Service
@Slf4j
public class LoanServiceImpl {

    @Resource
    GnydMerchantsInfoMapper merchantsInfoMapper;

    //商户贷款信息录入
    private Result<Object> addLoanInfo(DISP20210604ReqVo reqVo) {

        log.debug("商户贷款信息录入, 接收到的请求报文:{}", JSON.toJSONString(reqVo));

        if(reqVo.getChildren() > reqVo.getFamily()) {
            log.error("商户贷款信息录入, 参数输入错误,子女人数{}大于家庭人数{}", reqVo.getChildren(), reqVo.getFamily());
            return Result.build(ResultCodeEnum.PARAM_ERROR.getCode(), "子女人数大于家庭人数");
        }

        GnydMerchantsInfoDto merchantsInfoDto = new GnydMerchantsInfoDto();
        BeanUtils.copyProperties(reqVo, merchantsInfoDto);

        log.debug("商户贷款信息录入, 待录入数据:{}", JSON.toJSONString(merchantsInfoDto));

        try {
            merchantsInfoMapper.addloanInfo(merchantsInfoDto);
        } catch (Exception e) {
            log.error("商户贷款信息录入, 插入数据出现异常", e);
            return Result.build(ResultCodeEnum.DATA_ERROR.getCode(), "信息录入失败");
        }

        return Result.ok();
    }

    //商户贷款信息修改
    private Result<Object> updateInfo(DISP20210604ReqVo reqVo) {
        log.debug("商户贷款信息修改, 接收到的请求报文:{}", JSON.toJSONString(reqVo));

        if(reqVo.getChildren() > reqVo.getFamily()) {
            log.error("商户贷款信息修改, 参数输入错误,子女人数{}大于家庭人数{}", reqVo.getChildren(), reqVo.getFamily());
            return Result.build(ResultCodeEnum.PARAM_ERROR.getCode(), "子女人数大于家庭人数");
        }

        GnydMerchantsInfoDto merchantsInfoDto = new GnydMerchantsInfoDto();
        BeanUtils.copyProperties(reqVo, merchantsInfoDto);

        log.debug("商户贷款信息修改, 待录入数据:{}", JSON.toJSONString(merchantsInfoDto));

        int i = 0;
        try {
            i = merchantsInfoMapper.updateLoanInfo(merchantsInfoDto);
        } catch (Exception e) {
            log.error("商户贷款信息修改, 数据库出现异常", e);
        }

        if(i != 1) {
            log.error("商户贷款信息修改, 修改信息失败");
            return Result.build(ResultCodeEnum.DATA_ERROR.getCode(), "修改信息失败");
        }

        return Result.ok();
    }

    //商户贷款信息查询
    private Result<GnydMerchantsInfoDto> queryByPK(DISP20210604ReqVo reqVo) {
        log.debug("商户贷款信息查询, 接收到请求报文:{}", JSON.toJSONString(reqVo));

        GnydMerchantsInfoDto merchantsInfoDto = new GnydMerchantsInfoDto();
        BeanUtils.copyProperties(reqVo, merchantsInfoDto);

        GnydMerchantsInfoDto rslt;
        try {
            rslt = merchantsInfoMapper.queryByPK(merchantsInfoDto);
        } catch (Exception e) {
            log.error("商户贷款信息查询, 数据库出现异常", e);
            return Result.build(ResultCodeEnum.DATA_ERROR.getCode(), "查询失败");
        }

        return Result.ok(rslt);
    }

    //市场方对商户评价

    //市场方对商户评价列表查询

    //市场方对商户评价修改

}
