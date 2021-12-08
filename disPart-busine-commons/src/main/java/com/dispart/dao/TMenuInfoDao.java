package com.dispart.dao;

import com.dispart.vo.commons.TMenuInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TMenuInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-19 20:00:16
 */
@Mapper
public interface TMenuInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    TMenuInfo queryById(String menuId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TMenuInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tMenuInfo 实例对象
     * @return 对象列表
     */
    List<TMenuInfo> queryAll(TMenuInfo tMenuInfo);

    /**
     * 新增数据
     *
     * @param tMenuInfo 实例对象
     * @return 影响行数
     */
    int insert(TMenuInfo tMenuInfo);

    /**
     * 修改数据
     *
     * @param tMenuInfo 实例对象
     * @return 影响行数
     */
    int update(TMenuInfo tMenuInfo);

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 影响行数
     */
    int deleteById(String menuId);

}