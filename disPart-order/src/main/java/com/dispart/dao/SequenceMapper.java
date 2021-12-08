package com.dispart.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dispart.model.order.Sequence;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SequenceMapper extends BaseMapper<Sequence> {

}