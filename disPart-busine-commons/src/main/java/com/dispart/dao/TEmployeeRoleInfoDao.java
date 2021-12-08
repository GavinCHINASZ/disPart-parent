package com.dispart.dao;

import com.dispart.dto.auth.TRoleInfoDto;
import com.dispart.vo.commons.TEmployeeRoleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TEmployeeRoleInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-20 15:17:10
 */
@Mapper
public interface TEmployeeRoleInfoDao {

    /**
     * 通过ID查询单条数据
     *
     * @param  主键
     * @return 实例对象
     */
    TEmployeeRoleInfo queryById( );

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TEmployeeRoleInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tEmployeeRoleInfo 实例对象
     * @return 对象列表
     */
    List<TEmployeeRoleInfo> queryAll(TEmployeeRoleInfo tEmployeeRoleInfo);
    List<TRoleInfoDto> queryAllByChanlNo(TEmployeeRoleInfo tEmployeeRoleInfo);

    /**
     * 新增数据
     *
     * @param tEmployeeRoleInfo 实例对象
     * @return 影响行数
     */
    int insert(TEmployeeRoleInfo tEmployeeRoleInfo);

    /**
     * 修改数据
     *
     * @param tEmployeeRoleInfo 实例对象
     * @return 影响行数
     */
    int update(TEmployeeRoleInfo tEmployeeRoleInfo);

    /**
     * 通过主键删除数据
     *
     * @param  主键
     * @return 影响行数
     */
    int deleteById( );

}