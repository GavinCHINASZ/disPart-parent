package com.dispart.dao;


import com.dispart.dto.empdto.UnBundingRoleParamInDto;
import com.dispart.dto.userdto.EmpRoleFindMenuOutDto;
import com.dispart.vo.user.EmployeeRoleInfoVo;
import com.dispart.vo.user.RoleMenuVo;
import com.dispart.vo.user.RoleStatVo;
import com.dispart.vo.user.RoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IEmployeeRoleDao {

    /**
     * 查询员工绑定角色总条数
     * @param empId
     * @return
     */
    public Integer quryEmpRoleInfoCount(@Param("empId") String empId,@Param("orgId") String orgId,@Param("chnalNo") String chnalNo);

    /**
     * 员工绑定角色查询
     *
     * @param empId
     * @return
     */
    public List<RoleStatVo> quryEmpRoleInfoList(@Param("empId") String empId,@Param("orgId") String orgId, @Param("chnalNo") String chnalNo,@Param("strNum")  int strNum, @Param("endNum") int endNum);


    /**
     * 员工绑定角色
     *
     * @param vo
     * @return
     */
    public Integer insertEmpRoleList(@Param("voList") List<EmployeeRoleInfoVo> vo);

    /**
     * 员工解绑角色
     *
     * @param dto
     * @return
     */
    public Integer deleteEmpRoleList(@Param("voList") List<UnBundingRoleParamInDto> dto, @Param("empId") String empId);


    /**
     * 员工解绑角色
     *
     * @param roleId
     * @param empId
     * @return
     */
    public Integer quryEmpRoleInfo(@Param("roleId") String roleId, @Param("empId") String empId);

    /**
     * 查询员工所有权限菜单
     *
     * @param roleId
     * @param empId
     * @return
     */
    public List<RoleMenuVo> selectAllEmpMenuList(@Param("roleId") String roleId, @Param("depId") String empId);

    /**
     * 查询所有菜单
     *
     * @return
     */
    public List<EmpRoleFindMenuOutDto> findAllMenu(@Param("chnalNoType") String chnalNoType);

    /**
     * 机构和角色id查询角色信息
     * @param roleId
     * @param orgId
     * @return
     */
    public RoleVo selectRoleInfo(@Param("roleId") String roleId, @Param("orgId") String orgId);


}
