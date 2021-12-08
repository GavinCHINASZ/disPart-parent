package com.dispart.dao;

import com.dispart.vo.commons.TEmployeeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TEmployeeInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-20 15:07:32
 */
@Mapper
public interface TEmployeeInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param empId 主键
     * @return 实例对象
     */
    TEmployeeInfo queryById(String empId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TEmployeeInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tEmployeeInfo 实例对象
     * @return 对象列表
     */
    List<TEmployeeInfo> queryAll(TEmployeeInfo tEmployeeInfo);

    /**
     * 新增数据
     *
     * @param tEmployeeInfo 实例对象
     * @return 影响行数
     */
    int insert(TEmployeeInfo tEmployeeInfo);

    /**
     * 修改数据
     *
     * @param tEmployeeInfo 实例对象
     * @return 影响行数
     */
    int update(TEmployeeInfo tEmployeeInfo);

    /**
     * 通过主键删除数据
     *
     * @param empId 主键
     * @return 影响行数
     */
    int deleteById(String empId);

}