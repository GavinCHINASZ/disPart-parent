package com.dispart.dao;

import com.dispart.vo.commons.TUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (TUserInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-20 13:42:00
 */
@Mapper
public interface TUserInfoDao {
    /**
     * 获取微信号对应的用户信息
     * @param wxpayId
     * @return
     */
    TUserInfo queryBywxId(String  wxpayId);
    /**
     * 获取支付宝账号对应的用户信息
     * @param alipayId
     * @return
     */
    TUserInfo queryByZfbId(String  alipayId);
    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    TUserInfo queryById(String userId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<TUserInfo> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param tUserInfo 实例对象
     * @return 对象列表
     */
    List<TUserInfo> queryAll(TUserInfo tUserInfo);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tUserInfo 实例对象
     * @return 对象列表
     */
    TUserInfo queryTUserInfo(TUserInfo tUserInfo);

    /**
     * 新增数据
     *
     * @param tUserInfo 实例对象
     * @return 影响行数
     */
    int insert(TUserInfo tUserInfo);

    /**
     * 修改数据
     *
     * @param tUserInfo 实例对象
     * @return 影响行数
     */
    int update(TUserInfo tUserInfo);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteById(String userId);

    /**
     * 修改数据
     *
     * @param tUserInfo 实例对象
     * @return 影响行数
     */
    int updateClientId(TUserInfo tUserInfo);
    int updateProvId(TUserInfo tUserInfo);
}