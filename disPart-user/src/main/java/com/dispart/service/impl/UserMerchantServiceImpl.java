package com.dispart.service.impl;

import com.dispart.dao.UserMerchantMapper;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.UserMerchantService;
import com.dispart.vo.basevo.BaseMerchantVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserMerchantServiceImpl implements UserMerchantService {

    @Autowired
    UserMerchantMapper userMerchantMapper;

    @Override
    public Result<BaseMerchantVo> findMeQrcodeUrl(BaseMerchantVo baseMerchantVo) {
        if(baseMerchantVo.getMerchantcode()==null || "".equals(baseMerchantVo.getMerchantcode())){
            return Result.build(1,"客户编号不能为空");
        }
        BaseMerchantVo baseMerchantReq=userMerchantMapper.findMeQrcodeUrl(baseMerchantVo);
        log.info("获取客户二维码下载路径-返回： "+baseMerchantReq);
        return Result.build(baseMerchantReq, ResultCodeEnum.SUCCESS);
    }
}
