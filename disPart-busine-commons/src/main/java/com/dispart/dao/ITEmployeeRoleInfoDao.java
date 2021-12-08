package com.dispart.dao;

import com.dispart.dto.auth.TRoleInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * (TEmployeeRoleInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-20 15:17:10
 */
@Mapper
public interface ITEmployeeRoleInfoDao {


    /**
     * 通过实体作为筛选条件查询
     * @return 对象列表
     */
    List<TRoleInfoDto> queryAll(Map map);



}