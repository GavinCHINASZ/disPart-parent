package com.dispart.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dispart.dto.menudto.*;
import com.dispart.vo.basevo.DepOrgMenuVo;
import com.dispart.vo.basevo.MenuVo;
import com.dispart.vo.basevo.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BaseMenuMapper extends BaseMapper<MenuVo> {

    /**
     * 查询全部菜单（无条件时查全部，有条件时查询指定条件的菜单）
     * @param disp20210031SeMenuInDto
     * @return
     */
    List<DISP20210031SeMenuOutDto> findAllMenu(DISP20210031SeMenuInDto disp20210031SeMenuInDto);


    /**
     * 查询部门或级机构已有的菜单
     */
    List<DISP20210022DepFindMenuOutDto> findHaveMenuVoList(@Param("id") String id);
    /**
     * 查询级部门或机构没有的菜单
     */
    List<DISP20210022DepFindMenuOutDto> findNoHaveMenuVoList(@Param("id") String id);

    /**
     * 查询1级机构以下的部门或机构的菜单
     * @param pid-上级ID
     * @param did-自身ID
     * @return
     */
    List<DISP20210022DepFindMenuPOutDto> find2DepOrgMenuList(@Param("did") String did, @Param("pid") String pid);

    /**
     * 查询1级机构机构的菜单
     * @param id
     * @return
     */
    List<DISP20210022DepFindMenuPOutDto> find1OrgMenuList( @Param("id") String id);


    /**
     * 联表查询角色对应的菜单
     */
    List<DISP20210037RoleFindMenuPOutDto> findRoleMenuVoList(DISP20210037RoleFindMenuInDto disp20210037RoleFindMenuInDto);

    /**
     * 单表查询角色拥有的菜单ID
     * @param roleID
     * @return
     */
    List<RoleMenu> findRoleMenuList(@Param("roleID") String roleID);

    /**
     * 删除角色已有权限
     * @param roleID
     * @return
     */
    int deRoleMenu(@Param("roleID") String roleID);

    /**
     * 添加角色权限
     * @param roleMenu
     * @return
     */
    int inRoleMenu(@Param("list") List<RoleMenu> roleMenu);

    /**
     * 单表查询机构或部门的权限
     * @param id
     * @return
     */
    List<DepOrgMenuVo> seDepOrgMenuList(@Param("id") String id);

    /**
     * 删除部门或者机构权限权限
     * @param id
     * @return
     */
    int deDepOrgMenu(@Param("id") String id);
    /**
     * 添加部门或者机构权限
     * @param depOrgMenuVos
     * @return
     */
    int inOrgDepMenu(@Param("list") List<DepOrgMenuVo> depOrgMenuVos);

}