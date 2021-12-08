package com.dispart.controller;

import com.dispart.dto.entrance.QuryEntranceVeCheckInDto;
import com.dispart.dto.entrance.QuryEntranceVeCheckOutDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.EntranceTurnOverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/securityCenter")
@Api(tags = "进出场交接")
@Slf4j
@CrossOrigin
public class EntranceTurnOverController {

    @Autowired
    EntranceTurnOverService entranceTurnOverService;

    @PostMapping("/DISP20210243")
    @ApiOperation(value = "查询业务人员办理进出场数据")
    public Result<QuryEntranceVeCheckOutDto> quryInOutInfo(@RequestBody Request<QuryEntranceVeCheckInDto> param) {
        return entranceTurnOverService.quryInOutInfo(param);
    }
}
