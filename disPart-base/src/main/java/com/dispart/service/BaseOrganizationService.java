package com.dispart.service;

import com.dispart.dto.orgdto.DISP20210025OrgFindByParamInDto;
import com.dispart.dto.orgdto.DISP20210025OrgFindByParamOutDto;
import com.dispart.dto.orgdto.DISP20210026UpOrgInDto;
import com.dispart.result.Result;
import com.dispart.vo.basevo.OrganizationVo;
import com.baomidou.mybatisplus.extension.service.IService;
public interface BaseOrganizationService extends IService<DISP20210026UpOrgInDto>{
    /**
     * 添加机构
     * @param organizationVo
     * @return
     */
    Result addOrg(OrganizationVo organizationVo);

    /**
     * 修改机构状态为删除0-正常 1-删除
     * @param organizationVo
     * @return
     */
    Result upOrgSt(OrganizationVo organizationVo);

    /**
     * 查询指定添加的正常机构信息
     * @param disp20210025OrgFindByParamInDto
     * @return
     */
    Result<DISP20210025OrgFindByParamOutDto> seOrgList(DISP20210025OrgFindByParamInDto disp20210025OrgFindByParamInDto);




}
