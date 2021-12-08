package com.disPart.service;


public interface TSubsidInfoService{

    /**
     * 修改 3待撤回 的补贴申请
     *
     * @author 黄贵川
     * @date 2021/08/25
     */
    void updateSubsidInfo();

    /**
     * 查询是否有可执行的任务
     *
     * @author 黄贵川
     * @date 2021/08/25
     */
    boolean judgeTaskStatus();
}
