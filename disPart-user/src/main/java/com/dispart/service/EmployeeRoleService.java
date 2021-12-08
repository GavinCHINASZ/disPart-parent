package com.dispart.service;

import com.dispart.dto.ResultOutDto;
import com.dispart.dto.empdto.BundingRoleInDto;
import com.dispart.dto.empdto.QuryBindRoleInDto;
import com.dispart.dto.empdto.QuryBindRoleOutDto;
import com.dispart.dto.empdto.UnBundingRoleByInDto;
import com.dispart.dto.userdto.EmpFindMenuInDto;
import com.dispart.dto.userdto.EmpRoleFindMenuOutDto;
import com.dispart.result.Result;

import java.util.List;

public interface EmployeeRoleService {
    /**
     * 用户绑定角色查询
     * @param param
     * @return
     */
    public Result<QuryBindRoleOutDto> quryBindRole(QuryBindRoleInDto param);

    /**
     * 用户绑定角色
     * @param param
     * @return
     */
    public Result<ResultOutDto> bindRole(BundingRoleInDto param);

    /**
     * 用户解绑角色查询
     * @param param
     * @return
     */
    public Result<ResultOutDto> unBindRole(UnBundingRoleByInDto param);

    /**
     * 查询用户权限菜单
     * @param param
     * @return
     */
    Result<List<EmpRoleFindMenuOutDto>> qryEmpAuthMenu(EmpFindMenuInDto param);

}
