package com.dispart.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dispart.dto.orgdto.DISP20210025OrgFindByParamInDto;
import com.dispart.dto.orgdto.DISP20210025OrgFindByParamOutDto;
import com.dispart.dto.orgdto.DISP20210025OrgFindByParamPOutDto;
import com.dispart.dto.orgdto.DISP20210026UpOrgInDto;
import com.dispart.vo.basevo.OrganizationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BaseOrganizationMapper extends BaseMapper<DISP20210026UpOrgInDto> {
    //获取序列号
    Integer findNextval(@Param("seqName") String seqName);

    //添加机构
    Integer addOrg(OrganizationVo organizationVo);

    //查询正常机构信息(上级机构正常，或者上级机构编号是‘00000000’)
    List<DISP20210025OrgFindByParamPOutDto> seOrgList(DISP20210025OrgFindByParamInDto disp20210025OrgFindByParamInDto);
    //查询正常机构数量(上级机构正常，或者上级机构编号是‘00000000’)
    Integer orgNum(DISP20210025OrgFindByParamInDto disp20210025OrgFindByParamInDto);

    //查询最大机构号
    String seMaxOrgId(@Param("minId") String minId, @Param("maxId") String maxId);
    
    //删除机构(修改机构状态为删除)
    Integer upOrgSt(OrganizationVo organizationVo);

    //查询上级机构ID
    OrganizationVo findPOrgIDByOrgId(@Param("orgId") String orgID);

    //指定条件查询机构信息
    List<OrganizationVo> findNextOrgId(OrganizationVo organizationVo);
}