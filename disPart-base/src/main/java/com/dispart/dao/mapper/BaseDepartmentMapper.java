package com.dispart.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dispart.dto.departmentdto.DISP20210019DepFindByParamInDto;
import com.dispart.dto.departmentdto.DISP20210019DepFindByParamPOutDto;
import com.dispart.dto.departmentdto.DISP20210020UpDepInDto;
import com.dispart.vo.basevo.DepartmentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface BaseDepartmentMapper extends BaseMapper<DepartmentVo> {
    //查询正常部门列表(所属级机构正常，上级部门正常或上级部门编号是‘000000’)
    List<DISP20210019DepFindByParamPOutDto> findDepByParam(DISP20210019DepFindByParamInDto record);
    //查询正常部门数量(所属级机构正常，上级部门正常或上级部门编号是‘000000’)
    Integer findDepNum(DISP20210019DepFindByParamInDto record);

    int insert(DepartmentVo departmentVo);

    //查询限定的最大部门编号
    String seMaxDepID(@Param("minId") String minId, @Param("maxId") String maxId);

    //修改部门状态
    int upDepSt(DepartmentVo departmentVo);

    //修改部门信息
    int upDep(DISP20210020UpDepInDto disp20210020UpDepInDto);

    //查询上级部门ID和部门所属级机构ID
    DepartmentVo findPDepIdByDepId(@Param("depId") String parentDepId);

    //指定条件查询部门信息
    List<DepartmentVo> findDepData(DepartmentVo departmentVo);

    //查询部门名下的正常状态员工
    List<String> findEmpByDepId(@Param("depId") String depId);

}