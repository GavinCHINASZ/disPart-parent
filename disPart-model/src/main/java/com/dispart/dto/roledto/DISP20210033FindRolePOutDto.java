package com.dispart.dto.roledto;

import lombok.Data;

@Data
public class DISP20210033FindRolePOutDto {
    private String roleNm;//角色名称
    private String roleId;//角色ID
    private String remark;//备注
    private String  chanlNo; //渠道类型
    private String orgId;//所属机构ID
    private String OrgNm;//所属机构名称
    private String updateDt;//更新时间
}
