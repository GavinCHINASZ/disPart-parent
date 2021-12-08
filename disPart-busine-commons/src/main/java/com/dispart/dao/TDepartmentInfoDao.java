package com.dispart.dao;

import com.dispart.vo.commons.TDepartmentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TDepartmentInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-19 20:18:09
 */
@Mapper
public interface TDepartmentInfoDao {

    List<TDepartmentInfo> queryAllObj ();
    List<TDepartmentInfo> queryById (String depId);

}