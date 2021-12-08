package com.dispart.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dispart.dto.departmentdto.FindDepByParam;
import com.dispart.dto.departmentdto.FindOrgByParam;
import com.dispart.dto.menudto.DISP20210022DepFindMenuInDto;
import com.dispart.dto.menudto.DISP20210022DepFindMenuOutDto;
import com.dispart.dto.menudto.DISP20210023UpDepMenuInDto;
import com.dispart.dto.orgdto.DISP20210025OrgFindByParamInDto;
import com.dispart.dto.orgdto.DISP20210025OrgFindByParamOutDto;
import com.dispart.dto.orgdto.DISP20210026UpOrgInDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.BaseMenuService;
import com.dispart.service.BaseOrganizationService;
import com.dispart.vo.basevo.DepartmentVo;
import com.dispart.vo.basevo.OrganizationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/securityCenter")
@Api(tags = "机构管理")
@Slf4j
@CrossOrigin
public class BaseOrganizationController {
    @Autowired
    BaseOrganizationService baseOrganizationService;

    @Autowired
    private BaseMenuService baseMenuService;

    @PostMapping("DISP20210025")
    @ApiOperation(value = "机构查询")
    public Result findOrgByParam(@RequestBody Request<DISP20210025OrgFindByParamInDto> disp20210025OrgFindByParamInDto){
        return baseOrganizationService.seOrgList(disp20210025OrgFindByParamInDto.getBody());
    }

    @PostMapping("DISP20210026")
    @ApiOperation(value = "修改机构信息")
    public Result upDepByParam(@RequestBody Request<DISP20210026UpOrgInDto> organizationVo){
        log.info("修改机构信息-入参： "+organizationVo.getBody());
        boolean flag = baseOrganizationService.updateById(organizationVo.getBody());
        if (flag) {
            log.info("修改机构-修改成功");
            return Result.ok();
        }else {
            log.error("修改机构-修改失败");
            return Result.fail();

        }
    }
    @PostMapping("DISP20210028")
    @ApiOperation(value = "机构对应权限查询")
    public Result findOrgMenu(@RequestBody Request<DISP20210022DepFindMenuInDto>   disp20210022DepFindMenuInDto){
        log.info("机构对应权限查询-入参： "+disp20210022DepFindMenuInDto.getBody());
        disp20210022DepFindMenuInDto.getBody().setDOSt(1);
        return baseMenuService.findMenuTree(disp20210022DepFindMenuInDto.getBody());
    }

    @PostMapping("DISP20210030")
    @ApiOperation(value = "添加机构")
    public Result addDep(@RequestBody Request<OrganizationVo>  organizationVo){
        log.info("添加机构-入参： "+organizationVo.getBody());
        return baseOrganizationService.addOrg(organizationVo.getBody());
    }

    @PostMapping("DISP20210027")
    @ApiOperation(value = "根据id删除机构")
    public Result deOrgById(@RequestBody Request<OrganizationVo> organizationVo){
        log.info("根据id删除机构-机构ID："+organizationVo.getBody().getOrgId());
        return baseOrganizationService.upOrgSt(organizationVo.getBody());
    }

    @PostMapping("DISP20210029")
    @ApiOperation(value = "设置机构权限")
    public Result upOrgMenu(@RequestBody Request<DISP20210023UpDepMenuInDto> disp20210023UpDepMenuInDtoRequest){
        log.info("设置机构权限-权限集合："+disp20210023UpDepMenuInDtoRequest.getBody());
        return baseMenuService.upDepOrgMenu(disp20210023UpDepMenuInDtoRequest.getBody());
    }

}
