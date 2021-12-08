package com.dispart.service;

import com.dispart.dto.roledto.DISP20210033FindRoleInDto;
import com.dispart.dto.roledto.DISP20210033FindRoleOutDto;
import com.dispart.result.Result;
import com.dispart.vo.basevo.RoleVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BaseRoleService extends IService<RoleVo>{

    /**
     * 查询角色列表
     * @param disp20210033FindRoleInDto
     * @return
     */
    Result<DISP20210033FindRoleOutDto> findRoleByParam(DISP20210033FindRoleInDto disp20210033FindRoleInDto);

    /**
     * 添加角色
     * @param roleVo
     * @return
     */
    Result inRole(RoleVo roleVo);

    /**
     * 删除角色
     * @param roleVo
     * @return
     */
    Result deRole(RoleVo roleVo);

}
