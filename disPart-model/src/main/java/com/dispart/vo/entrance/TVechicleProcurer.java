package com.dispart.vo.entrance;

import com.dispart.vo.basevo.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
    * 车辆进出管理
    */
@ApiModel(value="com-dispart-model-base-TVechicleProcurer")
@Data
public class TVechicleProcurer extends PageInfo {
    /**
    * 进出场ID YYMMDD + 8位序列号
    */
    @ApiModelProperty(value="进出场ID YYMMDD + 8位序列号")
    private String inId;

    /**
    * 车牌号
    */
    @ApiModelProperty(value="车牌号")
    private String vehicleNum;

    /**
    * 车辆类型
    */
    @ApiModelProperty(value="车辆类型")
    private String vehicleId;

    /**
    * 客户编号
    */
    @ApiModelProperty(value="客户编号")
    private String provId;

    /**
    * 客户卡号
    */
    @ApiModelProperty(value="客户卡号")
    private String cardNo;

    /**
    * 客户名称
    */
    @ApiModelProperty(value="客户名称")
    private String provNm;

    /**
    * 手机号码
    */
    @ApiModelProperty(value="手机号码")
    private String phone;

    /**
    * 摊位编号
    */
    @ApiModelProperty(value="摊位编号")
    private String boothNum;

    /**
    * 车辆图片url
    */
    @ApiModelProperty(value="车辆图片url")
    private String vehicleUrl;

    /**
    * 进场总重
    */
    @ApiModelProperty(value="进场总重")
    private BigDecimal inTtlWght;

    /**
    * 车辆皮重
    */
    @ApiModelProperty(value="车辆皮重")
    private BigDecimal tareWght;

    /**
    * 货物净重
    */
    @ApiModelProperty(value="货物净重")
    private BigDecimal cargoWght;

    /**
    * 进场地点
    */
    @ApiModelProperty(value="进场地点")
    private String inDoor;

    /**
    * 进场时间
    */
    @ApiModelProperty(value="进场时间")
    private Date inTime;

    /**
    * 进场操作人
    */
    @ApiModelProperty(value="进场操作人")
    private String inOperId;

    /**
    * 进场备注
    */
    @ApiModelProperty(value="进场备注")
    private String inRemark;

    /**
    * 进场收费总金额
    */
    @ApiModelProperty(value="进场收费总金额")
    private BigDecimal inTtlAmt;

    /**
    * 进场费
    */
    @ApiModelProperty(value="进场费")
    private BigDecimal inAmt;

    /**
    * 临时摊位费
    */
    @ApiModelProperty(value="临时摊位费")
    private BigDecimal boothAmt;

    /**
    * 进场实际收费
    */
    @ApiModelProperty(value="进场实际收费")
    private BigDecimal inActAmt;

    /**
    * 是否报备
    */
    @ApiModelProperty(value="是否报备")
    private String isReport;

    /**
    * 出场地点
    */
    @ApiModelProperty(value="出场地点")
    private String outDoor;

    /**
    * 出场时间
    */
    @ApiModelProperty(value="出场时间")
    private Date outTime;

    /**
    * 出场总重
    */
    @ApiModelProperty(value="出场总重")
    private BigDecimal outTtlWght;

    /**
    * 出场操作人
    */
    @ApiModelProperty(value="出场操作人")
    private String outOperId;

    /**
    * 是否退费
    */
    @ApiModelProperty(value="是否退费")
    private String isReturn;

    /**
    * 应退金额
    */
    @ApiModelProperty(value="应退金额")
    private BigDecimal refundAmt;

    /**
    * 应补金额
    */
    @ApiModelProperty(value="应补金额")
    private BigDecimal supptAmt;

    /**
    * 优惠金额
    */
    @ApiModelProperty(value="优惠金额")
    private BigDecimal preferPrice;

    /**
    * 实收金额
    */
    @ApiModelProperty(value="实收金额")
    private BigDecimal actRecevAmt;

    /**
    * 停车时长
    */
    @ApiModelProperty(value="停车时长")
    private Integer parkTime;

    /**
    * 停车收费金额
    */
    @ApiModelProperty(value="停车收费金额")
    private BigDecimal parkAmt;

    /**
    * 停车优惠金额
    */
    @ApiModelProperty(value="停车优惠金额")
    private BigDecimal parkCpn;

    /**
    * 停车实收金额
    */
    @ApiModelProperty(value="停车实收金额")
    private BigDecimal parkActAmt;

    /**
    * 收费类型 0-空车 1-供货车 2-免费月卡
    */
    @ApiModelProperty(value="收费类型 0-空车 1-供货车 2-免费月卡")
    private String chrgTp;

    /**
    * 状态 0-未付款 1-已进场 2-退款中 3-已出场
    */
    @ApiModelProperty(value="状态 0-未付款 1-已进场 2-退款中 3-已出场")
    private String status;

    /**
    * 去向
    */
    @ApiModelProperty(value="去向")
    private String whither;

    /**
    * 采购品种 蔬菜 水果 干货粮油 混装 肉类 其他
    */
    @ApiModelProperty(value="采购品种 蔬菜 水果 干货粮油 混装 肉类 其他")
    private String purchPrdt;

    /**
    * 备注
    */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
    * 操作员
    */
    @ApiModelProperty(value="操作员")
    private String operId;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date creatTime;

    /**
    * 更新时间
    */
    @ApiModelProperty(value="更新时间")
    private Date upTime;

    /**
     * 是否核验
     */
    @ApiModelProperty(value="是否核验")
    private String isCheck;

    /**
     * 是否减免 0-默认 1-固定收费
     */
    private String isDerated;
    /**
     * 进场核验应退金额
     */
    private BigDecimal checkReturnAmt;

    /**
     * 进场核验应补金额
     */
    private BigDecimal checkSupptAmt;


    /**
     * 是否固定收费 0-否 1-是
     */
    private String isFixed;

    /**
     * 固定费用
     */
    @ApiModelProperty(value="固定费用")
    private BigDecimal fixedAmt;

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

    @ApiModelProperty(value="车辆类别")
    private String vehicleTp;

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
     * 出场应收或应退总金额
     */
    @ApiModelProperty(value="出场应收或应退总金额")
    private BigDecimal outTtlAmt;

    /**
     * 出场去皮是否补退费 0-否 1-是
     */
    @ApiModelProperty(value="出场去皮是否补退费 0-否 1-是")
    private String isPeel;

    /**
     * 出场货物净重
     */
    @ApiModelProperty(value="出场货物净重")
    private BigDecimal outCargoWght;

    /**
     * 支付模式  1HSB,2现金,3pos银行卡,4一卡通,5银行卡,6pos二维码
     */
    @ApiModelProperty(value="支付模式  1HSB,2现金,3pos银行卡,4一卡通,5银行卡,6pos二维码")
    private String paymentMode;

    /**
     * 支付模式  1HSB,2现金,3pos银行卡,4一卡通,5银行卡,6pos二维码
     */
    @ApiModelProperty(value="支付模式  1HSB,2现金,3pos银行卡,4一卡通,5银行卡,6pos二维码")
    private String outPaymentMode;

    /**
     * 是否停车免费 0-免费  1-收费
     */
    @ApiModelProperty(value="是否停车免费 0-免费  1-收费")
    private String isParkFree;

    /**
     * 来货报备主表ID
     */
    @ApiModelProperty(value="来货报备主表ID")
    private String reportId;

    /**
     * 进场点
     */
    @ApiModelProperty(value="进场点")
    private String inDoorNm;

    /**
     * 出场点
     */
    @ApiModelProperty(value="出场点")
    private String outDoorNm;
}