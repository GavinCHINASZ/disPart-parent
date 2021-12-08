package com.dispart.controller;

import com.dispart.dto.entrance.*;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.EntranceVeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/securityCenter")
@Api(tags = "车辆进出场管理")
@Slf4j
@CrossOrigin
public class EntranceVeController {
    @Autowired
    EntranceVeService entranceVeService;

    @PostMapping("DISP20210227")
    @ApiOperation(value = "大车进场-查询报备信息")
    public Result findCargoByParam(@RequestBody Request<D_0227FindDto> d_0227FindDtoRequest){
        return  entranceVeService.findVePro(d_0227FindDtoRequest);
    }
    @PostMapping("DISP20210229")
    @ApiOperation(value = "大车进场登记")
    public Result addRepprt(@RequestBody Request<D_0229addInDto> d_0229addInDtoRequest){
        return entranceVeService.addVeDate(d_0229addInDtoRequest);

    }
    @PostMapping("DISP20210230")
    @ApiOperation(value = "空车进场登记")
    public Result addMinRepprt(@RequestBody Request<D_0230addInDto> d_0230addInDtoRequest){
        return entranceVeService.addMinVeDate(d_0230addInDtoRequest);
    }
    @PostMapping("DISP20210231")
    @ApiOperation(value = "收款后变更记录状态")
    public Result upVeStRepprt(@RequestBody Request<D_0231upInDto>  d_0231upInDtoRequest) {
        return entranceVeService.upVeSt(d_0231upInDtoRequest);
    }
    @PostMapping("DISP20210232")
    @ApiOperation(value = "查询进出场口信息")
    public Result findVeInOut(@RequestBody Request<D_0232FindDto>  d_0232FindDtoRequest) {
        return entranceVeService.findVeInOut(d_0232FindDtoRequest);
    }
    @PostMapping("DISP20210233")
    @ApiOperation(value = "无人工进出场")
    public Result upInOut(@RequestBody Request<D_0233Dto>  d_0233DtoRequest) {
        return entranceVeService.upInOut(d_0233DtoRequest);
    }

    @PostMapping("DISP20210234")
    @ApiOperation(value = "大车出场-查询大车进场记录")
    public Result findGVeStRepprt(@RequestBody Request<D_0234findInDto>  d_0234findInDtoRequest){
        return entranceVeService.findInVe(d_0234findInDtoRequest);
    }

    @PostMapping("DISP20210236")
    @ApiOperation(value = "出场缴费")
    public Result outPay(@RequestBody Request<D_0236Dto>  d_0236DtoRequest){
        return entranceVeService.outPay(d_0236DtoRequest);
    }

    @PostMapping("DISP20210237")
    @ApiOperation(value = "查询出场支付结果")
    public Result finOutPayInfo(@RequestBody Request<D_0237FindDto>  d_0237FindDtoRequest){
        return entranceVeService.finOutPayInfo(d_0237FindDtoRequest);
    }

    @PostMapping("DISP20210235")
    @ApiOperation(value = "变更大车进场记录")
    public Result upVeDateRepprt(@RequestBody Request<D_0235UpInDto> d_0235UpInDtoRequest){
        return entranceVeService.upInVe(d_0235UpInDtoRequest);
    }


    @PostMapping("DISP20210239")
    @ApiOperation(value = "查询空车进场记录")
    public Result findMVeStRepprt(@RequestBody Request<D_0234findInDto> d_0234findInDtoRequest){
        return entranceVeService.findInVe(d_0234findInDtoRequest);
    }

    @PostMapping("DISP20210240")
    @ApiOperation(value = "变更大车进场记录")
    public Result upMVeDateRepprt(@RequestBody Request<D_0235UpInDto> d_0235UpInDtoRequest){
        return entranceVeService.upInVe(d_0235UpInDtoRequest);
    }

    @PostMapping("DISP20210346")
    @ApiOperation(value = "大车出场-货物去向查询")
    public Result findWhither(@RequestBody Request<D_0346FindDto> d_0346FindDtoRequest){
        return entranceVeService.findWhither(d_0346FindDtoRequest);
    }

    @PostMapping("DISP20210365")
    @ApiOperation(value = "进出场管理-收费员收费统计")
    public Result chargeStatistics(@RequestBody Request<D_0365FindDto> d_0365FindDtoRequest){
        return entranceVeService.chargeStatistics(d_0365FindDtoRequest);
    }

    @PostMapping("DISP20210370")
    @ApiOperation(value = "进出场管理-收费员收费统计导出")
    public Result getStatisticsExcel(@RequestBody Request<D_0365FindDto> d_0365FindDtoRequest){
        return entranceVeService.getStatisticsExcel(d_0365FindDtoRequest);

    }

    @PostMapping("DISP20210503")
    @ApiOperation(value = "进出场管理-进出场停车费查询")
    public Result getParkingInfo(@RequestBody Request<D_0503FindDto> d_0503FindDtoRequest){
        return entranceVeService.getParkingInfo(d_0503FindDtoRequest);

    }

    @PostMapping("DISP20210504")
    @ApiOperation(value = "进出场管理-进出场停车费查询导出")
    public Result getParkingExcel(@RequestBody Request<D_0503FindDto> d_0503FindDtoRequest){
        return entranceVeService.getParkingExcel(d_0503FindDtoRequest);

    }

}
