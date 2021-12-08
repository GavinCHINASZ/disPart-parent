package com.dispart.controller;

import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.PushNotesService;
import com.dispart.vo.busineCommon.TPushNotesVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 推送消息
 *
 * @author 黄贵川
 * @date 2021-09-04
 * @version 1.0
 */
@Slf4j
@Api(tags = "推送消息通知")
@RestController
@CrossOrigin
@RequestMapping("/securityCenter")
public class PushNotesController {
    @Autowired
    private PushNotesService pushNotesService;

    /**
     * 查询推送的历史消息
     *
     * @author 黄贵川
     * @date 2021-09-04
     * @param param 请求参数
     * @return Result
     */
    @PostMapping("/DISP20210315")
    @ApiOperation(value = "查询推送的历史消息")
    public Result findPushNotesList(@RequestBody Request<TPushNotesVo> param) {
        return pushNotesService.findPushNotesList(param);
    }

    /**
     * 更新消息
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param 请求参数
     * @return Result
     */
    @PostMapping("/DISP20210316")
    @ApiOperation(value = "更新消息")
    public Result updatePushNotes(@RequestBody Request<TPushNotesVo> param) {
        return pushNotesService.updatePushNotes(param);
    }
}