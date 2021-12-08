package com.dispart.dto.entrance;

import com.dispart.vo.entrance.TVechicleProcurerDetails;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author:王思州
 * @date:Created in 2021/8/07 11:25
 * @description 增加货物信息申请dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class D_0229addInDto {
    /**
     * 进出场ID YYMMDD + 8位序列号
     */
    @ApiModelProperty(value="进出场ID YYMMDD + 8位序列号")
    private String inId;

    @ApiModelProperty(value="进场总重")
    private BigDecimal inTtlWght;
    @ApiModelProperty(value="车牌号")
    private String vehicleNum;
    @ApiModelProperty(value="客户卡号")
    private String cardNo;
    @ApiModelProperty(value="客户编号")
    private String provId;
    @ApiModelProperty(value="客户名称")
    private String provNm;
    @ApiModelProperty(value="手机号码")
    private String phone;
    @ApiModelProperty(value="车辆皮重")
    private BigDecimal tareWght;
    @ApiModelProperty(value="货物净重")
    private BigDecimal cargoWght;
    @ApiModelProperty(value="车辆类别")
    private String vehicleTp;
    @ApiModelProperty(value="车辆类别ID")
    private String vehicleTpId;
    @ApiModelProperty(value="车主名称")
    private String driverNm;
    @ApiModelProperty(value="车主电话")
    private String driverPhone;


    @ApiModelProperty(value="车辆类型")
    private String vehicle;
    @ApiModelProperty(value="车辆类型ID")

    private String vehicleId;

    @ApiModelProperty(value="进场地点")
    private String inDoor;

    /**
     * 进场点
     */
    @ApiModelProperty(value="进场点")
    private String inDoorNm;

    @ApiModelProperty(value="进场时间")
    private Date inTime;//后台自动获取
    @ApiModelProperty(value="摊位编号")
    private String boothNum;

    @ApiModelProperty(value="核验状态 0-未核验，1-已核验")
    private String isCheck;
    @ApiModelProperty(value = "是否固定收取 0-默认，1-是")
    private String isFixed;
//    @ApiModelProperty(value="产地图片url")
//    private String placeUrl;

    @ApiModelProperty(value="进场备注")
    private String inRemark;

    @ApiModelProperty(value="车辆图片url")
    private String vehicleUrl;

    @ApiModelProperty(value="进场收费总金额")
    private BigDecimal inTtlAmt;

    @ApiModelProperty(value="进场优惠金额")
    private BigDecimal inAmt;

    @ApiModelProperty(value="进场实际收费")
    private BigDecimal inActAmt;

    @ApiModelProperty(value="临时摊位费")
    private BigDecimal boothAmt;

    @ApiModelProperty(value="进场操作员")
    private String inOperId;

    /**
     * 进场操作人名称
     */
    @ApiModelProperty(value="进场操作人名称")
    private String inOperNm;


    /**
     * 状态 0-未付款 1-已进场 2-退款中 3-已出场
     */
    @ApiModelProperty(value="状态 0-未付款 1-已进场 2-退款中 3-已出场")
    private String status;
    /**
     * 固定费用
     */
    @ApiModelProperty(value="固定费用")
    private BigDecimal fixedAmt;
    /**
     * 是否减免 0-否/1-是
     */
    @ApiModelProperty(value="是否减免")
    private String isDerated;

    /**
     * 进场支付状态 0-待支付 1-支付失败 2-支付成功
     */
    @ApiModelProperty(value="状态 0-未付款 1-已进场 2-退款中 3-已出场")
    private String inPayStatus;

    /**
     * 出场支付状态 0-待支付 1-支付失败 2-支付成功
     */
    @ApiModelProperty(value="状态 0-未付款 1-已进场 2-退款中 3-已出场")
    private String outPayStatus;

    /**
     * 出场去皮应补金额
     */
    @ApiModelProperty(value="出场去皮应补金额")
    private BigDecimal peelSupptAmt;

    /**
     * 出场去皮应退金额
     */
    @ApiModelProperty(value="出场去皮应退金额")
    private BigDecimal peelReturnAmt;

    /**
     * 进场核验应退金额
     */
    @ApiModelProperty(value="进场核验应退金额")
    private BigDecimal checkReturnAmt;

    /**
     * 进场核验应补金额
     */
    @ApiModelProperty(value="进场核验应补金额")
    private BigDecimal checkSupptAmt;

    /**
     * 出场应收或应退总金额
     */
    @ApiModelProperty(value="出场应收或应退总金额")
    private BigDecimal outTtlAmt;

    /**
     * 来货报备主表ID
     */
    @ApiModelProperty(value="来货报备主表ID")
    private String reportId;

    //产品信息-来货报备明细
    private List<TVechicleProcurerDetails> prdList;

}
