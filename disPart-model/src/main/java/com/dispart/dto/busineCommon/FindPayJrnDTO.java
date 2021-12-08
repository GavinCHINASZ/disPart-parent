package com.dispart.dto.busineCommon;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FindPayJrnDTO {
    /**
     * 流水号/自生成
     */
    @ApiModelProperty(value="流水号/自生成")
    private String jrnlNum;

    /**
     * 业务号
     */
    @ApiModelProperty(value="业务号")
    private String businessNo;

    /**
     * 主订单ID
     */
    @ApiModelProperty(value="主订单ID")
    private String mainOrderId;

}
