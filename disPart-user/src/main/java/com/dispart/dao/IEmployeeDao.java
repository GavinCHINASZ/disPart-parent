package com.dispart.dao;

import com.dispart.dto.empdto.QuryEmpInDto;
import com.dispart.dto.empdto.UpdateEmpParamDto;
import com.dispart.vo.user.EmployeeInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Mapper
public  interface IEmployeeDao {
    /**
     * 插入员工信息
     * @param empVo
     * @return
     */
    public Integer insertEmployeeInfo(@Param("empVo") EmployeeInfoVo empVo);

    /**
     * 修改员工信息
     * @param empVo
     * @return
     */
    public Integer updateEmployeeInfo(EmployeeInfoVo empVo);

    /**
     * 根据员工编号查询员工信息
     * @param empId
     * @return
     */
    public EmployeeInfoVo selectEmpInfoById(@Param("empId") String empId);

    /**
     * 修改员工状态
     * @param paramDto
     * @return
     */
    public Integer updateEmpStById(UpdateEmpParamDto paramDto);

    /**
     *  注锁员工
     * @param paramDto
     * @return
     */
    public Integer updateDeleteEmpById(UpdateEmpParamDto paramDto);

    /**
     * 获取员工序列员
     * @return
     */
    public Integer selectEmployeeIdSeq();

    /**
     * 查询员工信息
     * @param inDto
     * @param curPage
     * @param pageSize
     * @param deleteSt
     * @return
     */
    public List<EmployeeInfoVo> selectEmpInfos(@Param("inDto") QuryEmpInDto inDto, @Param("curPage") int curPage, @Param("pageSize") int pageSize, @Param("deleteSt") String deleteSt);

    /**
     * 查询员工信息总数
     * @param inDto
     * @return
     */
    public Integer selectCount(@Param("inDto") QuryEmpInDto inDto);

    /**
     * 根据机构id查询机构名称
     * @param orgId
     * @return
     */
    public String selectOrgNameByOrgId(@Param("orgId") String orgId);

    /**
     * 根据部门id查询部门名称
     * @param depId
     * @return
     */
    public String selectDepNameDepId(@Param("depId") String depId);

}
