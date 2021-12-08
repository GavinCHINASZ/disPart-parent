package com.disPart.dao;

import com.dispart.vo.entrance.TSubsidInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
     * 补贴申请--查询
     *
     * @author 黄贵川
     * @date 2021/08/23
     * @return Result
     */
    List<TSubsidInfo> selectSubsidInfoList();

    /**
     * 查询是否有可执行的任务
     *
     * @author 黄贵川
     * @date 2021/08/25
     */
    int judgeTaskStatus();

    /**
     * 更新状态为 已撤回
     *
     * @author 黄贵川
     * @date 2021/08/25
     */
    void updateSubsidInfoStatus(@Param("inId") String inId, @Param("status") String status);
}
