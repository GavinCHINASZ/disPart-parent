package com.disPart.dao.mapper.auto.dao;

import com.disPart.dao.mapper.auto.entity.BaseMerchant;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (BaseMerchant)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-19 10:10:25
 */
public interface BaseMerchantDao {

    /**
     * 通过ID查询单条数据
     *
     * @param merchantcode 主键
     * @return 实例对象
     */
    BaseMerchant queryById(String merchantcode);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<BaseMerchant> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param baseMerchant 实例对象
     * @return 对象列表
     */
    List<BaseMerchant> queryAll(BaseMerchant baseMerchant);

    /**
     * 新增数据
     *
     * @param baseMerchant 实例对象
     * @return 影响行数
     */
    int insert(BaseMerchant baseMerchant);

    /**
     * 修改数据
     *
     * @param baseMerchant 实例对象
     * @return 影响行数
     */
    int update(BaseMerchant baseMerchant);

    /**
     * 通过主键删除数据
     *
     * @param merchantcode 主键
     * @return 影响行数
     */
    int deleteById(String merchantcode);

}