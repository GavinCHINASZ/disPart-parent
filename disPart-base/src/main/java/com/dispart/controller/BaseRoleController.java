package com.dispart.controller;

import com.dispart.dto.menudto.DISP20210037RoleFindMenuInDto;
import com.dispart.dto.menudto.DISP20210037RoleFindMenuOutDto;
import com.dispart.dto.menudto.DISP20210038RoleUpMenuInDto;
import com.dispart.dto.roledto.DISP20210033FindRoleInDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.BaseChangRoleMeToBusine;
import com.dispart.service.BaseMenuService;
import com.dispart.service.BaseRoleService;
import com.dispart.vo.basevo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/securityCenter")
@Api(tags = "角色管理")
@Slf4j
@CrossOrigin
public class BaseRoleController {
    @Autowired
    BaseRoleService baseRoleService;
    @Autowired
    BaseMenuService baseMenuService;

    @Autowired
    private BaseChangRoleMeToBusine baseChangRoleMeToBusine;

    @PostMapping("DISP20210033")
    @ApiOperation(value = "角色查询")
    public Result findRoleByParam(@RequestBody Request<DISP20210033FindRoleInDto>   disp20210033FindRoleInDtoRequest){
        return baseRoleService.findRoleByParam(disp20210033FindRoleInDtoRequest.getBody());
    }

    @PostMapping("DISP20210034")
    @ApiOperation(value = "修改角色信息")
    public Result upDepByParam(@RequestBody Request<RoleVo> roleVo){
        Date newTime=new Date();
        roleVo.getBody().setUpdateDt(newTime);
        boolean flag = baseRoleService.updateById(roleVo.getBody());
        if (flag) {
            log.info("修改成功");
            return Result.ok();
        }else {
            log.error("修改失败");
            return Result.fail();

        }
    }

    @PostMapping("DISP20210036")
    @ApiOperation(value = "添加角色")
    public Result addDep(@RequestBody Request<RoleVo>  roleVo){
        return  baseRoleService.inRole(roleVo.getBody());
    }

    @PostMapping("DISP20210035")
    @ApiOperation(value = "根据id删除角色")
    public Result deleteDep(@RequestBody Request<RoleVo>  roleVo){
        return baseRoleService.deRole(roleVo.getBody());
    }

    @PostMapping("DISP20210037")
    @ApiOperation(value = "角色对应权限查询")
    public Result findRoleMenu(@RequestBody Request<DISP20210037RoleFindMenuInDto>  disp20210037RoleFindMenuInDto){

        if(disp20210037RoleFindMenuInDto.getBody().getRoleId()!=null || !"".equals(disp20210037RoleFindMenuInDto.getBody().getRoleId())){
            log.info("角色对应权限查询-入参： "+disp20210037RoleFindMenuInDto.getBody());
            return baseMenuService.findMenuTree(disp20210037RoleFindMenuInDto.getBody());
        }else {
            return Result.build(null,1,"角色ID为空");
        }
    }

    @PostMapping("DISP20210038")
    @ApiOperation(value = "设置角色权限")
    public Result upRoleMenu(@RequestBody Request<DISP20210038RoleUpMenuInDto>  disp20210038RoleUpMenuInDtoRequest){
        log.info("设置角色权限-入参："+disp20210038RoleUpMenuInDtoRequest.getBody());
        return baseMenuService.upRoleMenu(disp20210038RoleUpMenuInDtoRequest.getBody());
    }


}
