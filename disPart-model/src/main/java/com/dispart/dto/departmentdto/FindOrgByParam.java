package com.dispart.dto.departmentdto;

import lombok.Data;

@Data
public class FindOrgByParam {
    private String orgId;
    private String orgNm;
    private String parentOrgId;

 }
