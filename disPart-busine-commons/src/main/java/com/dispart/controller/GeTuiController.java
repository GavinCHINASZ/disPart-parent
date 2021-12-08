package com.dispart.controller;

import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.GeTuiService;
import com.dispart.vo.busineCommon.TPushNotesVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 个推
 *
 * @author 黄贵川
 * @date 2021/08/28
 * @version 1.0
 */
@RestController
public class GeTuiController {
    @Autowired
    private GeTuiService geTuiService;

    /**
     * 个推:消息推送服务
     *
     * @author 黄贵川
     * @date 2021/08/28
     * @return Result
     */
    @PostMapping("/securityCenter/DISP20210310")
    @ApiOperation(value = "个推:消息推送服务")
    public Result geTuiPushMessage(@RequestBody Request<TPushNotesVo> param){
        // code=200消息推送成功
        return geTuiService.geTuiPushMessage(param.getBody());
    }

}