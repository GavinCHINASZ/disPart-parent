package com.dispart.controller;

import com.dispart.dto.ResultOutDto;
import com.dispart.dto.makeCard.*;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.MakeCardManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/securityCenter")
@Api(tags = "制卡管理")
@Slf4j
@CrossOrigin
public class MakeCardManagerController {
    @Resource
    MakeCardManagerService makeCardManagerService;

    @PostMapping("/DISP20210173")
    @ApiOperation(value = "查询制卡申请信息")
    public Result<QuryMakeCardInfoOutDto> quryMakeCardApply(@RequestBody Request<QuryMakeCardInfoInDto> param) {
        return makeCardManagerService.quryMakeCardApply(param);
    }

    @PostMapping("/DISP20210174")
    @ApiOperation(value = "制卡申请新增")
    public Result<ResultOutDto> addMakeCardApply(@RequestBody Request<ApplyMakeCardInDto> param) {
        return makeCardManagerService.addMakeCardApply(param);
    }

    @PostMapping("/DISP20210176")
    @ApiOperation(value = "制卡申请信息修改")
    public Result<ResultOutDto> updateMakeCardApply(@RequestBody Request<UpdateApplyMakeCardInDto> param) {
        return makeCardManagerService.updateMakeCardApply(param);
    }

    @PostMapping("/DISP20210175")
    @ApiOperation(value = "制卡申请取消")
    public Result<ResultOutDto> cancelMakeCardApply(@RequestBody Request<UpdateMakeCardStatInDto> param) {
        return makeCardManagerService.cancelMakeCardApply(param);
    }

    @PostMapping("/DISP20210177")
    @ApiOperation(value = "制卡申请入库")
    public Result<ResultOutDto> warehousingMakeCardApply(@RequestBody Request<WarehousingInDto> param) {
        return makeCardManagerService.warehousingMakeCardApply(param);
    }

    @PostMapping("/DISP20210179")
    @ApiOperation(value = "查询制卡入库信息")
    public Result<QuryWarehousingOutDto> quryWarehousingCard(@RequestBody Request<QuryWarehousingInDto> param) {
        return makeCardManagerService.quryWarehousingCard(param);
    }

    @PostMapping("/DISP20210180")
    @ApiOperation(value = "入库制卡信息修改")
    public Result<ResultOutDto> updateWarehousingCardInfo(@RequestBody Request<QuryWarehousingInDto> param) {
        return makeCardManagerService.updateWarehousingCardInfo(param);
    }

    @PostMapping("/DISP20210178")
    @ApiOperation(value = "查询制卡入库信息详情")
    public Result<QuryWarehousingDetailsOutDto> quryWarehousingCardDetails(@RequestBody Request<QuryWarehousingDetailsInDto> param) {
        return makeCardManagerService.quryWarehousingCardDetails(param);
    }

    @PostMapping("/DISP20210290")
    @ApiOperation(value = "入库登记查询")
    public Result<QuryWarehousingOutDto> quryCanWarehousingCardInfo(@RequestBody Request<QuryWarehousingInDto> param) {
        return makeCardManagerService.quryCanWarehousingCardInfo(param);
    }

    @PostMapping("/DISP20210291")
    @ApiOperation(value = "制卡信息状态变更")
    public Result<ResultOutDto> updateCardInfoStatus(@RequestBody Request<UpdateMakeCardStatInDto> param) {
        return makeCardManagerService.updateCardInfoStatus(param);
    }
}
