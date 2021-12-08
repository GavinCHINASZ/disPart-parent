package com.dispart.dto.orgdto;

import lombok.Data;

@Data
public class DISP20210025OrgFindByParamInDto {
    private String orgNm;
    private String orgId;
    private String parentOrgId;
    //是否获取机构树0-需要，其他-不需要
    private String getTree;

    //当前页数
    private Integer pageNum;
    //页面条数
    private Integer pageSize;
}
