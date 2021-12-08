package com.dispart.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dispart.model.order.TPalceOrderType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface TPalceOrderTypeMapper extends BaseMapper<TPalceOrderType> {

    @Select("select prov_nm,status from t_custom_info_manager where prov_id = #{provId}")
    Map getProvIdName(String provId);

//    String getProvStatus()
}