package com.dispart.controller;

import com.dispart.dto.ResultOutDto;
import com.dispart.dto.entrance.TGoodsUnitInDto;
import com.dispart.dto.entrance.TGoodsUnitOutDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.GoodsUnitSerivce;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/securityCenter")
@CrossOrigin
@Api(tags = "商品计量单位设置")
public class GoodsUnitController {
    @Autowired
    GoodsUnitSerivce goodsUnitSerivce;

    @PostMapping("/DISP20210329")
    @ApiOperation(value = "商品计量单位查询")
    public Result<TGoodsUnitOutDto> quryGoodsUnitInfo(@RequestBody Request<TGoodsUnitInDto> param) {
        return goodsUnitSerivce.quryGoodsUnitInfo(param);
    }

    @PostMapping("/DISP20210326")
    @ApiOperation(value = "商品计量单位设置")
    public Result<ResultOutDto> setGoodUnit(@RequestBody Request<TGoodsUnitInDto> param) {
        return goodsUnitSerivce.setGoodUnit(param);
    }

    @PostMapping("/DISP20210327")
    @ApiOperation(value = "商品计量单位规格设置")
    public Result<ResultOutDto> setGoodUnitRelevance(@RequestBody Request<TGoodsUnitInDto> param) {
        return goodsUnitSerivce.setGoodUnitRelevance(param);
    }

    @PostMapping("/DISP20210328")
    @ApiOperation(value = "商品计量单位规格删除")
    public Result<ResultOutDto> deleteGoodUnitRelevance(@RequestBody Request<TGoodsUnitInDto> param) {
        return goodsUnitSerivce.deleteGoodUnitRelevance(param);
    }

    @PostMapping("/DISP20210330")
    @ApiOperation(value = "商品计量单位删除")
    public Result<ResultOutDto> deleteGoodUnit(@RequestBody Request<TGoodsUnitInDto> param) {
        return goodsUnitSerivce.deleteGoodUnit(param);
    }
}
