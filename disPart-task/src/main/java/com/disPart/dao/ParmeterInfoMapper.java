package com.disPart.dao;

import com.dispart.dto.parmeterdto.ParmeterSelectInVo;
import com.dispart.model.ParmeterInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ParmeterInfoMapper {


    String selectByPrimaryKey(@Param("paramType")String paramType, @Param("paramNm")String paramNm);

}