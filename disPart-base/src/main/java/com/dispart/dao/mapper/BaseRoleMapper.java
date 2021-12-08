package com.dispart.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dispart.dto.roledto.DISP20210033FindRoleInDto;
import com.dispart.dto.roledto.DISP20210033FindRolePOutDto;
import com.dispart.vo.basevo.RoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BaseRoleMapper extends BaseMapper<RoleVo> {
    /**
     * 查询角色列表信息
     * @param disp20210033FindRoleInDto
     * @return
     */
    List<DISP20210033FindRolePOutDto> findRoleByParam(DISP20210033FindRoleInDto disp20210033FindRoleInDto);

    /**
     * 查询角色总数
     * @param disp20210033FindRoleInDto
     * @return
     */
    Integer roleCount(DISP20210033FindRoleInDto disp20210033FindRoleInDto);

    /**
     * 添加角色
     * @return
     */
    Integer inRole(RoleVo roleVo);

    /**
     * 指定条件查询角色信息
     * @param roleVo
     * @return
     */
    List<RoleVo> findRoleData(RoleVo roleVo);

    /**
     * 根据角色ID删除角色
     * @param roleId
     * @return
     */
    Integer delRoleById(@Param("roleId") String roleId);


    /**
     * 查询用户角色关联表，获取角色关联的用户t_employee_role_info
     * @return
     */
    List<String> findEmpRoleByRoleId(@Param("roleId") String roleId);

}