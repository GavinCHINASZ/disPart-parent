package com.dispart.dao;

import com.dispart.vo.commons.TDepOrgMenuInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TDepOrgMenuInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-19 20:23:22
 */
@Mapper
public interface TDepOrgMenuInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TDepOrgMenuInfo queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TDepOrgMenuInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tDepOrgMenuInfo 实例对象
     * @return 对象列表
     */
    List<TDepOrgMenuInfo> queryAll(TDepOrgMenuInfo tDepOrgMenuInfo);

    /**
     * 新增数据
     *
     * @param tDepOrgMenuInfo 实例对象
     * @return 影响行数
     */
    int insert(TDepOrgMenuInfo tDepOrgMenuInfo);

    /**
     * 修改数据
     *
     * @param tDepOrgMenuInfo 实例对象
     * @return 影响行数
     */
    int update(TDepOrgMenuInfo tDepOrgMenuInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}