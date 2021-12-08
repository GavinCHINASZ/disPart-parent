package com.dispart.controller;

import com.dispart.dto.ResultOutDto;
import com.dispart.dto.entrance.TProductInventoryInfoDto;
import com.dispart.dto.entrance.TProductInventoryInfoOutDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.GoodsManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/securityCenter")
@CrossOrigin
@Api(tags = "货物管理")
public class GoodsManagerController {
    @Autowired
    GoodsManagerService goodsManagerService;

    @PostMapping("/DISP20210248")
    @ApiOperation(value = "货物信息查询")
    public Result<TProductInventoryInfoOutDto> quryGoodsInfo(@RequestHeader  String userNo,@RequestBody Request<TProductInventoryInfoDto> param) {
        return goodsManagerService.quryGoodsInfo(userNo,param);
    }

    @PostMapping("/DISP20210246")
    @ApiOperation(value = "货物信息新增")
    public Result<ResultOutDto> addGoodsInfo(@RequestBody Request<TProductInventoryInfoDto> param) {
        return goodsManagerService.addGoodsInfo(param);
    }

    @PostMapping("/DISP20210247")
    @ApiOperation(value = "货物信息修改")
    public Result<ResultOutDto> updateGoodsInfo(@RequestBody Request<TProductInventoryInfoDto> param) {
        return goodsManagerService.updateGoodsInfo(param);
    }

    @PostMapping("/DISP20210251")
    @ApiOperation(value = "货物信息统计查询")
    public Result<TProductInventoryInfoOutDto> countGoodsInfo(@RequestBody Request<TProductInventoryInfoDto> param) {
        return goodsManagerService.countGoodsInfo(param);
    }
}
