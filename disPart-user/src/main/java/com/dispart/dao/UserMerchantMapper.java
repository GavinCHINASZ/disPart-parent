package com.dispart.dao;

import com.dispart.vo.basevo.BaseMerchantVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMerchantMapper {
    /**
     * 获取客户二维码下载地址
     * @return
     */
    BaseMerchantVo findMeQrcodeUrl(BaseMerchantVo baseMerchantVo);
}