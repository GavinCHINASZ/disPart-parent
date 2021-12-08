package com.dispart.dto.departmentdto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DISP20210020UpDepInDto {
    //部门ID
    private String depId;
    //部门名称
    private String depNm;
    //部门简称
    private String depShrtNm;
    //备注
    private String remark;
}
