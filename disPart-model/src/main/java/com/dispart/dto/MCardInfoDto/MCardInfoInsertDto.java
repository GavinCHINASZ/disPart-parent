package com.dispart.dto.MCardInfoDto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class MCardInfoInsertDto {
    /**
     * 月卡单号
     */
    @ApiModelProperty(value = "月卡单号")
    private String mcardNum;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private String provId;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String provNm;

    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    private String vehicleNum;

    /**
     * 卡号
     */
    @ApiModelProperty(value = "卡号")
    private String cardNo;

    /**
     * 月卡类型 0-普通月卡 1-超级月卡 2-免费月卡
     */
    @ApiModelProperty(value = "月卡类型 0-普通月卡 1-超级月卡 2-免费月卡")
    private String mcardTp;

    /**
     * 开卡日期
     */
    @ApiModelProperty(value = "开卡日期")
    private String openCardDt;

    /**
     * 开始日期
     */
    @ApiModelProperty(value = "开始日期")
    private String startDt;

    /**
     * 到期日期
     */
    @ApiModelProperty(value = "到期日期")
    private String dueDt;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 进场门口编号
     */
    @ApiModelProperty(value = "进场门口编号")
    private String inNum;

    /**
     * 出场门口编号
     */
    @ApiModelProperty(value = "出场门口编号")
    private String outNum;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 操作员ID
     */
    @ApiModelProperty(value = "操作员ID")
    private String operId;

    /**
     * 操作员
     */
    @ApiModelProperty(value = "操作员")
    private String operNm;

}
