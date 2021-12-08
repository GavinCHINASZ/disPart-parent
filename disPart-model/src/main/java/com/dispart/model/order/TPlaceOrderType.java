package com.dispart.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

@ApiModel(value="com-dispart-model-order-TPlaceOrderType")
@Data
@TableName(value = "logistics.t_place_order_type")
public class TPlaceOrderType {
    /**
     * 商家ID
     */
    @TableId(value = "PROV_ID", type = IdType.INPUT)
    @ApiModelProperty(value="商家ID")
    private String provId;

    /**
     * 下单模式
     */
    @TableId(value = "PLACE_ORDER_MD", type = IdType.INPUT)
    @ApiModelProperty(value="下单模式")
    private String placeOrderMd;

    /**
     * 下单模式名称
     */
    @TableField(value = "PLACE_ORDER_NM")
    @ApiModelProperty(value="下单模式名称")
    private String placeOrderNm;

    /**
     * 状态 启用 停用
     */
    @TableField(value = "STATUS")
    @ApiModelProperty(value="状态 启用 停用")
    private String status;

    /**
     * 备注
     */
    @TableField(value = "REMARK")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 时间戳
     */
    @TableField(value = "UPDATE_DT")
    @ApiModelProperty(value="时间戳")
    private Date updateDt;

    public static final String COL_PROV_ID = "PROV_ID";

    public static final String COL_PLACE_ORDER_MD = "PLACE_ORDER_MD";

    public static final String COL_PLACE_ORDER_NM = "PLACE_ORDER_NM";

    public static final String COL_STATUS = "STATUS";

    public static final String COL_REMARK = "REMARK";

    public static final String COL_UPDATE_DT = "UPDATE_DT";
}