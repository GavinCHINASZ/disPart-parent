package com.dispart.dao;

import com.dispart.dto.dataquery.Disp20210069InDto;
import com.dispart.dto.dataquery.Disp20210069OutMx;
import com.dispart.dto.dataquery.Disp20210335InDto;
import com.dispart.dto.dataquery.Disp20210335OutDto;
import com.dispart.entity.DatabaseCount;
import com.dispart.model.ProductTypeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface OrderQueryMapper {

    @Select("select USER_NM userNm from t_user_info where USER_ID = #{userId}")
    public String getUserNm(String userId);

    @Select("select cname from base_merchant where merchantcode = #{provId}")
    public String getProvNm(String provId);

    public ArrayList<Disp20210069OutMx> quryOrderInfo(Disp20210069InDto inDto);

    public DatabaseCount quryOrderInfo_count(Disp20210069InDto inDto);

    public List<String> queryDownLevelPrdctType(@Param("varietyNo") String varietyNo);

    @Select("select VARIETY_NO from t_product_type_info where VARIETY_NO = #{prdctTypeId}")
    public String getPrdctTypeID(String prdctTypeId);

    Integer getOrderGoodsCount(Disp20210335InDto inDto);

    ArrayList<Disp20210335OutDto> getOrderGoods(Disp20210335InDto inDto);

}
