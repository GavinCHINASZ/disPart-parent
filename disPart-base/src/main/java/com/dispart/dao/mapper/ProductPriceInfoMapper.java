package com.dispart.dao.mapper;

import com.dispart.model.ProductPriceInfo;
import java.util.Date;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProductPriceInfoMapper {

    int insert(ProductPriceInfo record);

    int delete(ProductPriceInfo info);

    @Select("select 1 from t_product_price_info where DATE = #{date,jdbcType=DATE} and PRDCT_NM = #{prdctNm,jdbcType=VARCHAR}")
    int selectIsExist(ProductPriceInfo info);
}