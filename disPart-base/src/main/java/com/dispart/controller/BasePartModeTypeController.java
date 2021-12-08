package com.dispart.controller;

import com.dispart.dto.departmentdto.DISP20210019DepFindByParamInDto;
import com.dispart.dto.partmodetype.DISP20210101SePMTInDto;
import com.dispart.dto.partmodetype.DISP20210101SePMTOutDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeBaseEnum;
import com.dispart.service.BasePartModeTypeService;
import com.dispart.vo.basevo.PartModeTypeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/securityCenter")
@Api(tags = "分账模式管理")
@Slf4j
@CrossOrigin
public class BasePartModeTypeController {
    @Autowired
    BasePartModeTypeService basePartModeTypeService;

    @PostMapping("DISP20210101")
    @ApiOperation(value = "分账模式查询")
    public Result<DISP20210101SePMTOutDto> sePMTOutDto(@RequestBody Request<DISP20210101SePMTInDto> disp20210101SePMTInDto){
        log.info("分账模式查询-模式参数： "+disp20210101SePMTInDto.getBody());
        return basePartModeTypeService.sePMT(disp20210101SePMTInDto.getBody());
    }

    @PostMapping("DISP20210102")
    @ApiOperation(value = "分账模式新增")
    public Result insertPMT(@RequestBody Request<PartModeTypeVo>  partModeTypeVo){
        log.info("添加分账模式-模式参数： "+partModeTypeVo.getBody());
        return basePartModeTypeService.insert(partModeTypeVo.getBody());
    }

    @PostMapping("DISP20210103")
    @ApiOperation(value = "分账模式修改")
    public Result updatePMT(@RequestBody Request<PartModeTypeVo>  partModeTypeVo){
        return basePartModeTypeService.updateByPrimaryKey(partModeTypeVo.getBody());
    }

    @PostMapping("DISP20210104")
    @ApiOperation(value = "分账模式选中状态修改")
    public Result upStPMT(@RequestBody Request<PartModeTypeVo>  partModeTypeVo){
        return basePartModeTypeService.upStByPrimaryKey(partModeTypeVo.getBody());
    }
}
