package com.dispart.dao.mapper;

import com.dispart.dto.parmeterdto.ParmeterSelectInVo;
import com.dispart.model.ParmeterInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ParmeterInfoMapper {

    int deleteByPrimaryKey(@Param("paramType") String paramType, @Param("paramNm") String paramNm);

    int insertSelective(ParmeterInfo record);

    @Select("select *from t_parmeter_info where PARAM_TYPE = #{paramType} and PARAM_NM=#{paramNm}")
    ParmeterInfo selectByTypeAndNm(@Param("paramType")String paramType, @Param("paramNm")String paramNm);

    int updateByPrimaryKeySelective(ParmeterInfo record);

    List<ParmeterInfo> selectParmeter(ParmeterSelectInVo inVo);

    Integer selectParmeterCount(ParmeterSelectInVo inVo);
}