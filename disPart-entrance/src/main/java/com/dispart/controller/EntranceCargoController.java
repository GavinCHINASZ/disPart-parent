package com.dispart.controller;

import com.dispart.dto.entrance.D_0223FindDto;
import com.dispart.dto.entrance.D_0224AddDto;
import com.dispart.dto.entrance.D_0225FindDto;
import com.dispart.dto.entrance.D_0226UpInDto;
import com.dispart.dto.entrance.D_0228FindInDto;
import com.dispart.dto.entrance.D_0238FindDto;
import com.dispart.dto.entrance.D_0281UpInDto;
import com.dispart.dto.entrance.D_0345FindDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.EntranceCargoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/securityCenter")
@Api(tags = "来货报备管理")
@Slf4j
@CrossOrigin
public class EntranceCargoController {
    @Autowired
    EntranceCargoService entranceCargoService;

    @PostMapping("DISP20210223")
    @ApiOperation(value = "客户信息查询")
    public Result findCusByParam(@RequestBody Request<D_0223FindDto> d_0223FindDtoRequest){
        return  entranceCargoService.findCus(d_0223FindDtoRequest.getBody());
    }

    @PostMapping("DISP20210224")
    @ApiOperation(value = "来货报备申请")
    public Result addRepprt(@RequestBody Request<D_0224AddDto>  d_0224AddDtoRequest){
        return entranceCargoService.inCargo(d_0224AddDtoRequest);
    }

    @PostMapping("DISP20210225")
    @ApiOperation(value = "来货报备查询")
    public Result findByParm(@RequestBody Request<D_0225FindDto>  d_0225FindDtoRequest){
        return entranceCargoService.findCargo(d_0225FindDtoRequest);

    }
    @PostMapping("DISP20210226")
    @ApiOperation(value = "来货报备复核")
    public Result checkByParm(@RequestBody Request<D_0226UpInDto>  d_0226UpInDtoRequest){
        return entranceCargoService.updateByPrimaryKey(d_0226UpInDtoRequest);

    }
    @PostMapping("DISP20210228")
    @ApiOperation(value = "品种信息查询")
    public Result findPZById(@RequestBody Request<D_0228FindInDto>  d_0228FindInDtoRequest){
        return entranceCargoService.findProd(d_0228FindInDtoRequest.getBody());

    }
    @PostMapping("DISP20210281")
    @ApiOperation(value = "报备记录修改或作废")
    public Result upMenuById(@RequestBody Request<D_0281UpInDto> d_0281UpInDtoRequest){
        return entranceCargoService.upCargo(d_0281UpInDtoRequest);
    }

    @PostMapping("DISP20210238")
    @ApiOperation(value = "查询车辆档案记录")
    public Result findVeByVeNum(@RequestBody Request<D_0238FindDto> d_0238FindDtoRequest){
        return entranceCargoService.findVeByVeNum(d_0238FindDtoRequest);

    }
    @PostMapping("DISP20210345")
    @ApiOperation(value = "报备商品产地查询")
    public Result findProdPlace(@RequestBody Request<D_0345FindDto> d_0345FindDtoRequest){
        return entranceCargoService.findProdPlace(d_0345FindDtoRequest);
    }
}
