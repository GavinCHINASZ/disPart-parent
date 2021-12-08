package com.dispart.controller;

import com.dispart.dto.menudto.DISP20210031SeMenuInDto;
import com.dispart.dto.menudto.DISP20210031SeMenuOutDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.BaseMenuService;
import com.dispart.vo.basevo.MenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/securityCenter")
@Api(tags = "菜单管理")
@Slf4j
@CrossOrigin
public class BaseMenuController {
    @Autowired
    BaseMenuService baseMenuService;

    @PostMapping("DISP20210031")
    @ApiOperation(value = "功能菜单查询")
    public Result findMenuByParam(@RequestBody Request<DISP20210031SeMenuInDto> disp20210031SeMenuInDto){
        return  baseMenuService.findAllMenu(disp20210031SeMenuInDto.getBody());
    }

    @PostMapping("DISP20210032")
    @ApiOperation(value = "修改菜单信息")
    public Result upMenuById(@RequestBody Request<MenuVo>  menuVo){
        boolean flag = baseMenuService.updateById(menuVo.getBody());
        if (flag) {
            log.info("修改成功");
            return Result.ok();
        }else {
            log.error("修改失败");
            return Result.fail();

        }
    }


}
