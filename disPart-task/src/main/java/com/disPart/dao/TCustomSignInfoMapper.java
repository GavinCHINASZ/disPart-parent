package com.disPart.dao;

import com.dispart.vo.commons.TCustomSignInfo;
import org.apache.ibatis.annotations.Mapper;
/**
 * (TCustomSignInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-13 23:11:13
 */
@Mapper
public interface TCustomSignInfoMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param provId 主键
     * @return 实例对象
     */
    TCustomSignInfo queryById(String provId);
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
     * 查询惠市宝请求id
     * @return
     */
    int selectHSBReqId();

}