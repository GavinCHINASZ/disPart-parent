package com.dispart.service;


import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.vo.busineCommon.TPushNotesVo;

/**
 * 推送消息
 *
 * @author 黄贵川
 * @date 2021-09-04
 * @version 1.0
 */
public interface PushNotesService {
    /**
     * 查询推送的历史消息
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param 请求参数
     * @return Result
     */
    Result findPushNotesList(Request<TPushNotesVo> param);

    /**
     * 更新消息
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param 请求参数
     * @return Result
     */
    Result updatePushNotes(Request<TPushNotesVo> param);
}