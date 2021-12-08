package com.dispart.dao.mapper;

import com.dispart.vo.entrance.TSubsidInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 补贴申请 dao
 *
 * @author 黄贵川
 * @date 2021/08/23
 */
@Mapper
public interface TSubsidInfoMapper {
    /**
     * 补贴申请--新增
     *
     * @author 黄贵川
     * @date 2021/08/23
     * @param body TSubsidInfo
     * @return Result
     */
    int addSubsidInfo(TSubsidInfo body);

    /**
     * 补贴申请--查询
     *
     * @author 黄贵川
     * @date 2021/08/23
     * @param body TSubsidInfo
     * @return Result
     */
    List<TSubsidInfo> selectSubsidInfoList(TSubsidInfo body);

    /**
     * 补贴申请--修改
     *
     * @author 黄贵川
     * @date 2021/08/23
     * @param body TSubsidInfo
     * @return Result
     */
    int updateSubsidInfo(TSubsidInfo body);

    /**
     * 补贴申请--查询数量
     *
     * @author 黄贵川
     * @date 2021/08/24
     * @param body TSubsidInfo
     * @return Integer
     */
    int findNumByParm(TSubsidInfo body);
}
