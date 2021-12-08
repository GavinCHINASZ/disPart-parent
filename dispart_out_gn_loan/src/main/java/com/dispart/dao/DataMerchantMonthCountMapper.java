package com.dispart.dao;

import com.dispart.modle.DataMerchantMonthCount;
import com.dispart.modle.vo.Disp20210347InVo;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.ArrayList;

@Mapper
public interface DataMerchantMonthCountMapper {

    ArrayList<DataMerchantMonthCount> getTransInfo(Disp20210347InVo inVo);

    BigDecimal getTransTolAmt(Disp20210347InVo inVo);

}