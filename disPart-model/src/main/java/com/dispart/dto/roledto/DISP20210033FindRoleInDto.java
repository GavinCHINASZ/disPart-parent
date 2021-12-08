package com.dispart.dto.roledto;

import lombok.Data;

@Data
public class DISP20210033FindRoleInDto {
    private String roleNm;
    private String orgId;

    //当前页数
    private Integer pageNum;
    //页面条数
    private Integer pageSize;
}
