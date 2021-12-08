package com.dispart.dao;

import com.dispart.modle.DataMerchant;
import com.dispart.modle.vo.Disp20210347InVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataMerchantMapper {
    DataMerchant getBorrowerInfo(Disp20210347InVo inVo);
}