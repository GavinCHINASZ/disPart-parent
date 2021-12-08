package com.dispart.dao;

import com.dispart.vo.commons.TMenuAuthInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TMenuAuthInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-19 19:52:47
 */
@Mapper
public interface TMenuAuthInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param authId 主键
     * @return 实例对象
     */
    TMenuAuthInfo queryById(Integer authId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TMenuAuthInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tMenuAuthInfo 实例对象
     * @return 对象列表
     */
    List<TMenuAuthInfo> queryAll(TMenuAuthInfo tMenuAuthInfo);

    /**
     * 新增数据
     *
     * @param tMenuAuthInfo 实例对象
     * @return 影响行数
     */
    int insert(TMenuAuthInfo tMenuAuthInfo);

    /**
     * 修改数据
     *
     * @param tMenuAuthInfo 实例对象
     * @return 影响行数
     */
    int update(TMenuAuthInfo tMenuAuthInfo);

    /**
     * 通过主键删除数据
     *
     * @param authId 主键
     * @return 影响行数
     */
    int deleteById(Integer authId);

}