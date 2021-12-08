package com.dispart.dto.entrance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:王思州
 * @date:Created in 2021/8/07 11:25
 * @description 增加货物信息申请dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class D_0230addOutDto {
    /**
     * 进出场ID YYMMDD + 8位序列号
     */
    @ApiModelProperty(value="进出场ID YYMMDD + 8位序列号")
    private String inId;

}
