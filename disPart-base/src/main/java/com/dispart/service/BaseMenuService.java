package com.dispart.service;

import com.dispart.dto.menudto.*;
import com.dispart.result.Result;
import com.dispart.vo.basevo.DepOrgMenuVo;
import com.dispart.vo.basevo.MenuVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dispart.vo.basevo.RoleMenu;

import java.util.List;

public interface BaseMenuService extends IService<MenuVo>{

    /**
     * 查询全部菜单
     * @param disp20210031SeMenuInDto
     * @return
     */
    Result<List<DISP20210031SeMenuOutDto>> findAllMenu(DISP20210031SeMenuInDto disp20210031SeMenuInDto);

    /**
     * 部门对应权限查询-PC端
     * @param disp20210022DepFindMenuInDto
     * @return
     */
    Result<DISP20210022DepFindMenuOutDto> findMenuTree(DISP20210022DepFindMenuInDto disp20210022DepFindMenuInDto);

    /**
     * 角色对应权限查询-pc端
     * @param disp20210037RoleFindMenuInDto
     * @return
     */
    Result<DISP20210037RoleFindMenuOutDto> findMenuTree(DISP20210037RoleFindMenuInDto disp20210037RoleFindMenuInDto);

    /**
     * 修改角色权限
     * @param disp20210038RoleUpMenuInDto
     * @return
     */
    Result upRoleMenu(DISP20210038RoleUpMenuInDto disp20210038RoleUpMenuInDto);

    /**
     * 设置机构部门权限
     * @param disp20210023UpDepMenuInDto
     * @return
     */
    Result upDepOrgMenu(DISP20210023UpDepMenuInDto disp20210023UpDepMenuInDto);
}
