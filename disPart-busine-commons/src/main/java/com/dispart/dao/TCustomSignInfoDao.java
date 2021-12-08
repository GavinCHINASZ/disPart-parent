package com.dispart.dao;

import com.dispart.vo.commons.TCustomSignInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TCustomSignInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-13 23:11:13
 */
@Mapper
public interface TCustomSignInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param provId 主键
     * @return 实例对象
     */
    TCustomSignInfo queryById(String provId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TCustomSignInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tCustomSignInfo 实例对象
     * @return 对象列表
     */
    List<TCustomSignInfo> queryAll(TCustomSignInfo tCustomSignInfo);

    /**
     * 新增数据
     *
     * @param tCustomSignInfo 实例对象
     * @return 影响行数
     */
    int insert(TCustomSignInfo tCustomSignInfo);

    /**
     * 修改数据
     *
     * @param tCustomSignInfo 实例对象
     * @return 影响行数
     */
    int update(TCustomSignInfo tCustomSignInfo);

    /**
     * 通过主键删除数据
     *
     * @param provId 主键
     * @return 影响行数
     */
    int deleteById(String provId);

    /**
     * 查询惠市宝请求id
     * @return
     */
    int selectHSBReqId();

}