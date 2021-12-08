package com.dispart.service;

import com.dispart.result.Result;
import com.dispart.vo.busineCommon.TPushNotesVo;

/**
 * 个推
 *
 * @author 黄贵川
 * @date 2021/08/28
 * @version 1.0
 */
public interface GeTuiService {
    /**
     * 个推:消息推送服务
     *
     * @author 黄贵川
     * @date 2021/08/28
     * @return Result
     */
    Result geTuiPushMessage(TPushNotesVo body);
}