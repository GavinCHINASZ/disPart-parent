package com.dispart.dto.entrance;

import com.dispart.vo.basevo.PageInfo;
import com.dispart.vo.entrance.TVechicleProcurerDetails;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 车辆进出管理
 */
@Data
public class D_0297FindDto extends PageInfo {
    /**
     * 0货车(供应商) 1空车(采购商)
     */
    private String carType;

    /**
     * 手机号码/车牌号
     */
    private String likeValue;

    private List<TVechicleProcurerDetails> tVechicleProcurerDetailsList;

    /**
     * 进出场ID YYMMDD + 8位序列号
     */
    @ApiModelProperty(value = "进出场ID YYMMDD + 8位序列号")
    private String inId;
    private String[] inIdArr;

    private String vehicle;

    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    private String vehicleNum;

    /**
     * 车辆类型
     */
    @ApiModelProperty(value = "车辆类型ID")
    private String vehicleId;

    @ApiModelProperty(value = "车辆类型名称")
    private String vehicleTp;

    private String vehicleTpId;

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    private String provId;

    /**
     * 客户卡号
     */
    @ApiModelProperty(value = "客户卡号")
    private String cardNo;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String provNm;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String phone;

    /**
     * 摊位编号
     */
    @ApiModelProperty(value = "摊位编号")
    private String boothNum;

    /**
     * 车辆图片url
     */
    @ApiModelProperty(value = "车辆图片url")
    private String vehicleUrl;

    /**
     * 进场总重
     */
    @ApiModelProperty(value = "进场总重")
    private BigDecimal inTtlWght;

    /**
     * 车辆皮重
     */
    @ApiModelProperty(value = "车辆皮重")
    private BigDecimal tareWght;

    /**
     * 货物净重
     */
    @ApiModelProperty(value = "货物净重")
    private BigDecimal cargoWght;

    /**
     * 进场地点
     */
    @ApiModelProperty(value = "进场地点")
    private String inDoor;

    /**
     * 进场地点名称
     */
    private String inDoorNm;

    /**
     * 进场时间
     */
    @ApiModelProperty(value = "进场时间")
    private Date inTime;
    private Date inTimeStart;
    private Date inTimeEnd;

    /**
     * 进场操作人
     */
    @ApiModelProperty(value = "进场操作人")
    private String inOperId;

    private String inOperName;

    /**
     * 进场备注
     */
    @ApiModelProperty(value = "进场备注")
    private String inRemark;

    /**
     * 进场收费总金额
     */
    @ApiModelProperty(value = "进场收费总金额")
    private BigDecimal inTtlAmt;

    /**
     * 进场优惠金额
     */
    @ApiModelProperty(value = "进场优惠金额")
    private BigDecimal inAmt;

    /**
     * 临时摊位费
     */
    @ApiModelProperty(value = "临时摊位费")
    private BigDecimal boothAmt;

    /**
     * 进场实际收费
     */
    @ApiModelProperty(value = "进场实际收费")
    private BigDecimal inActAmt;

    /**
     * 是否报备
     */
    @ApiModelProperty(value = "是否报备")
    private String isReport;

    /**
     * 来货报备主表ID
     */
    @ApiModelProperty(value = "来货报备主表ID")
    private String reportId;

    /**
     * 出场地点
     */
    @ApiModelProperty(value = "出场地点")
    private String outDoor;

    /**
     * 出场地点名称
     */
    private String outDoorNm;

    /**
     * 出场时间
     */
    @ApiModelProperty(value = "出场时间")
    private Date outTime;
    private Date outTimeStart;
    private Date outTimeEnd;

    /**
     * 出场总重 出场总重量
     */
    @ApiModelProperty(value = "出场总重")
    private BigDecimal outTtlWght;

    /**
     * 出场操作人
     */
    @ApiModelProperty(value = "出场操作人")
    private String outOperId;

    private String outOperName;

    /**
     * 是否退费
     */
    @ApiModelProperty(value = "是否退费")
    private String isReturn;

    /**
     * 应退金额
     */
    @ApiModelProperty(value = "应退金额")
    private BigDecimal refundAmt;

    /**
     * 应补金额
     */
    @ApiModelProperty(value = "应补金额")
    private BigDecimal supptAmt;

    /**
     * 出场优惠金额
     */
    @ApiModelProperty(value = "出场优惠金额")
    private BigDecimal preferPrice;

    /**
     * 出场实收/退金额
     */
    @ApiModelProperty(value = "出场实收/退金额")
    private BigDecimal actRecevAmt;

    /**
     * 停车时长
     */
    @ApiModelProperty(value = "停车时长")
    private Integer parkTime;

    /**
     * 是否停车免费 0-免费  1-收费
     */
    @ApiModelProperty(value = "是否停车免费")
    private Integer isParkFree;

    /**
     * 停车收费金额
     */
    @ApiModelProperty(value = "停车收费金额")
    private BigDecimal parkAmt;

    /**
     * 停车优惠金额
     */
    @ApiModelProperty(value = "停车优惠金额")
    private BigDecimal parkCpn;

    /**
     * 停车实收金额
     */
    @ApiModelProperty(value = "停车实收金额")
    private BigDecimal parkActAmt;

    /**
     * 收费类型 0-空车 1-供货车 2-免费月卡
     */
    @ApiModelProperty(value = "收费类型 0-空车 1-供货车 2-免费月卡")
    private String chrgTp;

    /**
     * 状态 0-未付款 1-已进场 2-退款中 3-已出场
     */
    @ApiModelProperty(value = "状态 0-未付款 1-已进场 2-退款中 3-已出场")
    private String status;

    /**
     * 去向
     */
    @ApiModelProperty(value = "去向")
    private String whither;

    /**
     * 采购品种 蔬菜 水果 干货粮油 混装 肉类 其他
     */
    @ApiModelProperty(value = "采购品种 蔬菜 水果 干货粮油 混装 肉类 其他")
    private String purchPrdt;

    /**
     * 出场货物净重
     */
    @ApiModelProperty(value = "出场货物净重")
    private BigDecimal outCargoWght;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 操作员
     */
    @ApiModelProperty(value = "操作员")
    private String operId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date creatTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date upTime;

    /**
     * 是否核验
     */
    @ApiModelProperty(value = "是否核验")
    private String isCheck;

    private String isDerated;
    private String isFixed;
    /**
     * 是否补贴申请过 0没有， 1有
     */
    private Integer subsidInfoFor;
    /**
     * -1--没有补贴申请 0-申请中 1-已发放 2-已撤回 3-待撤回  4--作废 5--撤回申请中
     */
    private String subsidStatus;

    /**
     * 进场核验应退金额
     */
    private BigDecimal checkReturnAmt;

    /**
     * 进场核验应补金额
     */
    private BigDecimal checkSupptAmt;
    /**
     * 支付模式  1HSB,2现金,3pos银行卡,4一卡通,5银行卡,6pos二维码
     */
    private String paymentMode;

    /**
     * 0-待支付 1-支付失败 2-支付成功
     */
    private String inPayStatus;
    /**
     * 0-待支付 1-支付失败 2-支付成功
     */
    private String outPayStatus;

    /**
     * 操作人ID
     */
    private String operator;

    /**
     * 员工ID
     */
    private String empId;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 菜单ID
     */
    private String menuId;

    /**
     * 部门ID
     */
    private String depId;

    /**
     * 品种ID
     */
    private String categoryId;

    /**
     *品种名称 品种父名称(品类名称)
     */
    private String categoryNm;

    /**
     * 详情状态  默认0未检测,5待检测
     */
    private String detailsStatus;

    /**
     * 出场支付模式  1HSB,2现金,3pos银行卡,4一卡通,5银行卡,6pos二维码
     */
    private String outPaymentMode;

    /**
     * 无感支付 0-人工  1-半无感  2-全无感
     */
    private int nonInductive;

    /**
     * 补贴流水号(t_pay_jrn流水表 业务号) 进出场in_Id=BUSINESS_NO业务号
     */
    private String jrnlNum;

    // 采购商补贴查询或供应商补贴查询 时传1       jrnlType!=null供应商补贴或采购商补贴查询已出场的数据
    private String jrnlType;

    /**
     * 交易时间
     */
    private Date txnTm;
    /**
     * pos支付的订单号
     */
    private String posOrderId;

    /**
     * 出场去皮应补金额
     */
    private BigDecimal peelSupptAmt;

    /**
     * 出场去皮应退金额
     */
    private BigDecimal peelReturnAmt;
}
