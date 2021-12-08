package com.dispart.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dispart.model.order.TProductInventoryInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.awt.*;
import java.util.List;

@Mapper
public interface TProductInventoryInfoMapper extends BaseMapper<TProductInventoryInfo> {

    List<TProductInventoryInfo> queryResult(String provId);

}