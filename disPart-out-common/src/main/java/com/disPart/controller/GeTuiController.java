package com.disPart.controller;

import com.disPart.Service.GeTuiService;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.vo.busineCommon.TPushNotesVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 个推
 *
 * @author 黄贵川
 * @date 2021/08/28
 * @version 1.0
 */
@RestController
@RequestMapping("/securityCenter")
public class GeTuiController {
    @Autowired
    private GeTuiService geTuiService;

    /**
     * 个推:消息推送服务
     *
     * @author 黄贵川
     * @date 2021/09/01
     * @return Result
     */
    @PostMapping("/DISP20210313")
    @ApiOperation(value = "个推:消息推送服务")
    public Result geTuiPushMessage(@RequestBody Request<TPushNotesVo> param){
        // code=200消息推送成功
        return geTuiService.geTuiPushMessage(param.getBody());
    }

}