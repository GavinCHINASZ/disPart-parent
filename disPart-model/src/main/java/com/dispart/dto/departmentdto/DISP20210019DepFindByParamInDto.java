package com.dispart.dto.departmentdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class DISP20210019DepFindByParamInDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String depNm;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String subOrg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String depId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String parentDepId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String getTree;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    //当前页数
    private Integer pageNum;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    //页面条数
    private Integer pageSize;
}
