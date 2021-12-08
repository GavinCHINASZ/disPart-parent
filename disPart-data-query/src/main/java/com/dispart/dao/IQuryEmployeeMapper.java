package com.dispart.dao;

import com.dispart.dto.empdto.QuryEmpInDto;
import com.dispart.vo.user.EmployeeInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface IQuryEmployeeMapper {

    public List<EmployeeInfoVo> selectEmpInfos(@Param("inDto") QuryEmpInDto inDto, @Param("curPage") int curPage,@Param("pageSize") int pageSize,@Param("deleteSt") String deleteSt);

    public Integer selectCount(@Param("inDto") QuryEmpInDto inDto,@Param("deleteSt") String deleteSt);

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
