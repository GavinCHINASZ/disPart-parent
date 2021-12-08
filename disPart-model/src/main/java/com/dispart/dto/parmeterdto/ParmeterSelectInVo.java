package com.dispart.dto.parmeterdto;

import lombok.Data;

@Data
public class ParmeterSelectInVo {

    //参数类型
    private String paramType;

    //参数名
    private String paramNm;

    //分页参数，当前页
    private Integer pageNum;

    //分页参数，分页大小
    private Integer pageSize;

    public ParmeterSelectInVo(String paramType, String paramNm) {
        this.paramType = paramType;
        this.paramNm = paramNm;
    }
}
