package com.dispart.dao;

import com.dispart.vo.commons.TRoleMenuInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TRoleMenuInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-19 19:46:03
 */
@Mapper
public interface TRoleMenuInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    TRoleMenuInfo queryById(String roleId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TRoleMenuInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tRoleMenuInfo 实例对象
     * @return 对象列表
     */
    List<TRoleMenuInfo> queryAll(TRoleMenuInfo tRoleMenuInfo);

    /**
     * 新增数据
     *
     * @param tRoleMenuInfo 实例对象
     * @return 影响行数
     */
    int insert(TRoleMenuInfo tRoleMenuInfo);

    /**
     * 修改数据
     *
     * @param tRoleMenuInfo 实例对象
     * @return 影响行数
     */
    int update(TRoleMenuInfo tRoleMenuInfo);

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 影响行数
     */
    int deleteById(String roleId);

}