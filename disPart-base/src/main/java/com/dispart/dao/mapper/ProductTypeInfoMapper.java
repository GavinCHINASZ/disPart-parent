package com.dispart.dao.mapper;

import com.dispart.model.ProductTypeInfo;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ProductTypeInfoMapper {

    List<ProductTypeInfo> findTopPrdctType();

    List<ProductTypeInfo> findList();

    int deleteByPrimaryKey(String prdctTypeId);

    int insert(ProductTypeInfo record);

    ProductTypeInfo selectByTypeNm(String prdctType);

    @Select("select nextval(#{seqName})")
    Integer getInsertPrdctTypeId(String seqName);

    int updateByPrimaryKey(ProductTypeInfo record);

    @Select("select * from t_product_type_info p where p.`LEVEL` = '1' ")
    List<ProductTypeInfo> selectTopType();

    @Select("select * from t_product_type_info where PARENT_TYPE_ID = #{prdctTypeId}")
    List<ProductTypeInfo> findChildList(String prdctTypeId);


}