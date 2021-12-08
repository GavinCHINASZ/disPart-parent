package com.dispart.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dispart.dto.departmentdto.*;
import com.dispart.dto.menudto.DISP20210022DepFindMenuInDto;
import com.dispart.dto.menudto.DISP20210022DepFindMenuOutDto;
import com.dispart.dto.menudto.DISP20210023UpDepMenuInDto;
import com.dispart.dto.menudto.DISP20210031SeMenuInDto;
import com.dispart.request.Request;
import com.dispart.service.BaseMenuService;
import com.dispart.vo.User;
import com.dispart.vo.basevo.DepartmentVo;
import com.dispart.result.Result;
import com.dispart.service.BaseDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/securityCenter")
@Api(tags = "部门管理")
@Slf4j
@CrossOrigin
public class BaseDepartmentController {
    @Autowired
    private BaseDepartmentService baseDepartmentService;

    @Autowired
    private BaseMenuService baseMenuService;

    @PostMapping("DISP20210019")
    @ApiOperation(value = "部门查询")
    public Result findDepByParam(@RequestBody Request<DISP20210019DepFindByParamInDto>  findDepByParam){
        Result<DISP20210019DepFindByParamOutDto> ok = baseDepartmentService.findDepByParam(findDepByParam.getBody());
        return ok;
    }

    @PostMapping("DISP20210020")
    @ApiOperation(value = "修改部门信息")
    public Result upDepByParam(@RequestBody Request<DISP20210020UpDepInDto>  departmentVo){
        return baseDepartmentService.upDep(departmentVo.getBody());
    }

    @PostMapping("DISP20210024")
    @ApiOperation(value = "增加部门")
    public Result addDep(@RequestBody Request<DepartmentVo>  departmentVo){
        return baseDepartmentService.insert(departmentVo.getBody());
    }

    @PostMapping("DISP20210021")
    public Result deDep(@RequestBody Request<DepartmentVo>  departmentVo){
        log.info("部门查询-入参： "+departmentVo.getBody());
        return  baseDepartmentService.upDepSt(departmentVo.getBody());
    }

    @PostMapping("DISP20210022")
    @ApiOperation(value = "查询部门对应权限")
    public Result seDepMenu(@RequestBody Request<DISP20210022DepFindMenuInDto>  disp20210022DepFindMenuInDto){
        disp20210022DepFindMenuInDto.getBody().setDOSt(0);
        return baseMenuService.findMenuTree(disp20210022DepFindMenuInDto.getBody());
    }

    @PostMapping("DISP20210023")
    @ApiOperation(value = "设置部门权限")
    public Result deleteDep(@RequestBody Request<DISP20210023UpDepMenuInDto>  disp20210023UpDepMenuInDtoRequest){
        disp20210023UpDepMenuInDtoRequest.getBody().setIsDepOrg(1);//标志部门使用
        return baseMenuService.upDepOrgMenu(disp20210023UpDepMenuInDtoRequest.getBody());
    }

}
