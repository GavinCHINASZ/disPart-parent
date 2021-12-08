package com.dispart.dto.entrance;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 出场缴费
 */
@Data
public class D_0237FindDto {
    /**
     * 出场口编号
     */
    @ApiModelProperty(value="出场口编号")
    private String outId;

}