package com.dispart.dto.entrance;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 出场缴费
 */
@Data
public class D_0236OutDto {
    /**
     * 订单信息
     */
    @ApiModelProperty(value="订单信息")
    private Object orderInfo;

    /**
     * 车辆收费信息
     */
    @ApiModelProperty(value="车辆收费信息")
    private Object vechicleInfo;

    /**
     * 支付地址
     */
    @ApiModelProperty(value="支付地址")
    private String payUrl;

    public D_0236OutDto() {
    }

    public D_0236OutDto(Object orderInfo, Object vechicleInfo, String payUrl) {
        this.orderInfo = orderInfo;
        this.vechicleInfo = vechicleInfo;
        this.payUrl = payUrl;
    }
}