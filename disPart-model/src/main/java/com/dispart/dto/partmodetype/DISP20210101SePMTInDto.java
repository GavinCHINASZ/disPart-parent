package com.dispart.dto.partmodetype;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DISP20210101SePMTInDto {
    //分账模式名称
    private String partMdNm;
    //当前页数
    private Integer pageNum;
    //页面条数
    private Integer pageSize;
}
