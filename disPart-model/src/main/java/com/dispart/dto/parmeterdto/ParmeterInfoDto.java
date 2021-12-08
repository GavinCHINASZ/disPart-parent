package com.dispart.dto.parmeterdto;

import lombok.Data;

@Data
public class ParmeterInfoDto {
    //参数类型
    private String paramType;

    //参数名
    private String paramNm;

    //参数值
    private String paramVal;

    //参数描述
    private String paramDesc;

    //备注
    private String remark;
}
