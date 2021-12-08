package com.dispart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dispart.result.Result;
import com.dispart.vo.User;
import com.dispart.vo.basevo.BaseMerchantVo;

public interface UserMerchantService{
    /**
     * 获取客户二维码下载地址
     * @return
     */
    Result<BaseMerchantVo> findMeQrcodeUrl(BaseMerchantVo baseMerchantVo);
}
