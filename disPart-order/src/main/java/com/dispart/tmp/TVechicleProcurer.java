package com.dispart.tmp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
    * 车辆进出管理
    */
@Data
@TableName(value = "t_vechicle_procurer")
public class TVechicleProcurer {
    /**
     * 进出场ID YYMMDD + 8位序列号
     */
    @TableId(value = "IN_ID", type = IdType.INPUT)
    private String inId;

    /**
     * 车牌号
     */
    @TableField(value = "VEHICLE_NUM")
    private String vehicleNum;

    /**
     * 车辆类别名称
     */
    @TableField(value = "VEHICLE_TP")
    private String vehicleTp;

    /**
     * 车辆类型ID
     */
    @TableField(value = "VEHICLE_ID")
    private String vehicleId;

    /**
     * 客户编号
     */
    @TableField(value = "PROV_ID")
    private String provId;

    /**
     * 客户卡号
     */
    @TableField(value = "CARD_NO")
    private String cardNo;

    /**
     * 客户名称
     */
    @TableField(value = "PROV_NM")
    private String provNm;

    /**
     * 手机号码
     */
    @TableField(value = "PHONE")
    private String phone;

    /**
     * 摊位编号
     */
    @TableField(value = "BOOTH_NUM")
    private String boothNum;

    /**
     * 车辆图片url
     */
    @TableField(value = "VEHICLE_URL")
    private String vehicleUrl;

    /**
     * 进场总重
     */
    @TableField(value = "IN_TTL_WGHT")
    private BigDecimal inTtlWght;

    /**
     * 进场车辆皮重
     */
    @TableField(value = "TARE_WGHT")
    private BigDecimal tareWght;

    /**
     * 进场货物净重
     */
    @TableField(value = "CARGO_WGHT")
    private BigDecimal cargoWght;

    /**
     * 进场地点
     */
    @TableField(value = "IN_DOOR")
    private String inDoor;

    /**
     * 进场时间
     */
    @TableField(value = "IN_TIME")
    private Date inTime;

    /**
     * 进场操作人
     */
    @TableField(value = "IN_OPER_ID")
    private String inOperId;

    /**
     * 进场备注
     */
    @TableField(value = "IN_REMARK")
    private String inRemark;

    /**
     * 进场收费总金额
     */
    @TableField(value = "IN_TTL_AMT")
    private BigDecimal inTtlAmt;

    /**
     * 进场费
     */
    @TableField(value = "IN_AMT")
    private BigDecimal inAmt;

    /**
     * 临时摊位费
     */
    @TableField(value = "BOOTH_AMT")
    private BigDecimal boothAmt;

    /**
     * 进场实际收费
     */
    @TableField(value = "IN_ACT_AMT")
    private BigDecimal inActAmt;

    /**
     * 是否报备
     */
    @TableField(value = "IS_REPORT")
    private String isReport;

    /**
     * 出场地点
     */
    @TableField(value = "OUT_DOOR")
    private String outDoor;

    /**
     * 出场时间
     */
    @TableField(value = "OUT_TIME")
    private Date outTime;

    /**
     * 出场总重
     */
    @TableField(value = "OUT_TTL_WGHT")
    private BigDecimal outTtlWght;

    /**
     * 出场操作人
     */
    @TableField(value = "OUT_OPER_ID")
    private String outOperId;

    /**
     * 来货报备是否减免 0-否  1-是
     */
    @TableField(value = "IS_DERATED")
    private String isDerated;

    /**
     * 进场是否核验 0-未核验 1-已核验
     */
    @TableField(value = "IS_CHECK")
    private String isCheck;

    /**
     * 进场核验是否补退费 0-否 1-是
     */
    @TableField(value = "IS_RETURN")
    private String isReturn;

    /**
     * 进场核验应退金额
     */
    @TableField(value = "CHECK_RETURN_AMT")
    private BigDecimal checkReturnAmt;

    /**
     * 进场核验应补金额
     */
    @TableField(value = "CHECK_SUPPT_AMT")
    private BigDecimal checkSupptAmt;

    /**
     * 出场去皮是否补退费 0-否 1-是
     */
    @TableField(value = "IS_PEEL")
    private String isPeel;

    /**
     * 出场去皮应补金额
     */
    @TableField(value = "PEEL_SUPPT_AMT")
    private BigDecimal peelSupptAmt;

    /**
     * 出场去皮应退金额
     */
    @TableField(value = "PEEL_RETURN_AMT")
    private BigDecimal peelReturnAmt;

    /**
     * 进场是否固定收费 0-默认 1-固定收费
     */
    @TableField(value = "IS_FIXED")
    private String isFixed;

    /**
     * 进场固定收费收取金额
     */
    @TableField(value = "FIXED_AMT")
    private BigDecimal fixedAmt;

    /**
     * 应退金额(不用)
     */
    @TableField(value = "REFUND_AMT")
    private BigDecimal refundAmt;

    /**
     * 应补金额(不用)
     */
    @TableField(value = "SUPPT_AMT")
    private BigDecimal supptAmt;

    /**
     * 出场应收或应退总金额
     */
    @TableField(value = "OUT_TTL_AMT")
    private BigDecimal outTtlAmt;

    /**
     * 出场优惠金额
     */
    @TableField(value = "PREFER_PRICE")
    private BigDecimal preferPrice;

    /**
     * 出场实收或实退金额
     */
    @TableField(value = "ACT_RECEV_AMT")
    private BigDecimal actRecevAmt;

    /**
     * 停车时长
     */
    @TableField(value = "PARK_TIME")
    private Integer parkTime;

    /**
     * 停车收费金额
     */
    @TableField(value = "PARK_AMT")
    private BigDecimal parkAmt;

    /**
     * 停车优惠金额
     */
    @TableField(value = "PARK_CPN")
    private BigDecimal parkCpn;

    /**
     * 是否停车免费 0-免费  1-收费
     */
    @TableField(value = "IS_PARK_FREE")
    private Integer isParkFree;

    /**
     * 停车实收金额
     */
    @TableField(value = "PARK_ACT_AMT")
    private BigDecimal parkActAmt;

    /**
     * 收费类型 0-空车 1-供货车 2-免费月卡
     */
    @TableField(value = "CHRG_TP")
    private String chrgTp;

    /**
     * 支付模式  1HSB,2现金,3pos银行卡,4一卡通,5银行卡,6pos二维码
     */
    @TableField(value = "PAYMENT_MODE")
    private String paymentMode;

    /**
     * 支付模式  1HSB,2现金,3pos银行卡,4一卡通,5银行卡,6pos二维码
     */
    @TableField(value = "OUT_PAYMENT_MODE")
    private String outPaymentMode;

    /**
     * 状态 0-未付款 1-已进场 2-退款中 3-已出场
     */
    @TableField(value = "STATUS")
    private String status;

    /**
     * 0-待支付 1-支付失败 2-支付成功
     */
    @TableField(value = "IN_PAY_STATUS")
    private String inPayStatus;

    /**
     * 0-待支付 1-支付失败 2-支付成功
     */
    @TableField(value = "OUT_PAY_STATUS")
    private String outPayStatus;

    /**
     * 去向
     */
    @TableField(value = "WHITHER")
    private String whither;

    /**
     * 采购品种 蔬菜 水果 干货粮油 混装 肉类 其他
     */
    @TableField(value = "PURCH_PRDT")
    private String purchPrdt;

    /**
     * 备注
     */
    @TableField(value = "REMARK")
    private String remark;

    /**
     * 操作员
     */
    @TableField(value = "OPER_ID")
    private String operId;

    /**
     * 创建时间
     */
    @TableField(value = "CREAT_TIME")
    private Date creatTime;

    /**
     * 出场货物净重
     */
    @TableField(value = "OUT_CARGO_WGHT")
    private BigDecimal outCargoWght;

    /**
     * 更新时间
     */
    @TableField(value = "UP_TIME")
    private Date upTime;

    /**
     * 来货报备主表ID
     */
    @TableField(value = "REPORT_ID")
    private String reportId;

    public static final String COL_IN_ID = "IN_ID";

    public static final String COL_VEHICLE_NUM = "VEHICLE_NUM";

    public static final String COL_VEHICLE_TP = "VEHICLE_TP";

    public static final String COL_VEHICLE_ID = "VEHICLE_ID";

    public static final String COL_PROV_ID = "PROV_ID";

    public static final String COL_CARD_NO = "CARD_NO";

    public static final String COL_PROV_NM = "PROV_NM";

    public static final String COL_PHONE = "PHONE";

    public static final String COL_BOOTH_NUM = "BOOTH_NUM";

    public static final String COL_VEHICLE_URL = "VEHICLE_URL";

    public static final String COL_IN_TTL_WGHT = "IN_TTL_WGHT";

    public static final String COL_TARE_WGHT = "TARE_WGHT";

    public static final String COL_CARGO_WGHT = "CARGO_WGHT";

    public static final String COL_IN_DOOR = "IN_DOOR";

    public static final String COL_IN_TIME = "IN_TIME";

    public static final String COL_IN_OPER_ID = "IN_OPER_ID";

    public static final String COL_IN_REMARK = "IN_REMARK";

    public static final String COL_IN_TTL_AMT = "IN_TTL_AMT";

    public static final String COL_IN_AMT = "IN_AMT";

    public static final String COL_BOOTH_AMT = "BOOTH_AMT";

    public static final String COL_IN_ACT_AMT = "IN_ACT_AMT";

    public static final String COL_IS_REPORT = "IS_REPORT";

    public static final String COL_OUT_DOOR = "OUT_DOOR";

    public static final String COL_OUT_TIME = "OUT_TIME";

    public static final String COL_OUT_TTL_WGHT = "OUT_TTL_WGHT";

    public static final String COL_OUT_OPER_ID = "OUT_OPER_ID";

    public static final String COL_IS_DERATED = "IS_DERATED";

    public static final String COL_IS_CHECK = "IS_CHECK";

    public static final String COL_IS_RETURN = "IS_RETURN";

    public static final String COL_CHECK_RETURN_AMT = "CHECK_RETURN_AMT";

    public static final String COL_CHECK_SUPPT_AMT = "CHECK_SUPPT_AMT";

    public static final String COL_IS_PEEL = "IS_PEEL";

    public static final String COL_PEEL_SUPPT_AMT = "PEEL_SUPPT_AMT";

    public static final String COL_PEEL_RETURN_AMT = "PEEL_RETURN_AMT";

    public static final String COL_IS_FIXED = "IS_FIXED";

    public static final String COL_FIXED_AMT = "FIXED_AMT";

    public static final String COL_REFUND_AMT = "REFUND_AMT";

    public static final String COL_SUPPT_AMT = "SUPPT_AMT";

    public static final String COL_OUT_TTL_AMT = "OUT_TTL_AMT";

    public static final String COL_PREFER_PRICE = "PREFER_PRICE";

    public static final String COL_ACT_RECEV_AMT = "ACT_RECEV_AMT";

    public static final String COL_PARK_TIME = "PARK_TIME";

    public static final String COL_PARK_AMT = "PARK_AMT";

    public static final String COL_PARK_CPN = "PARK_CPN";

    public static final String COL_IS_PARK_FREE = "IS_PARK_FREE";

    public static final String COL_PARK_ACT_AMT = "PARK_ACT_AMT";

    public static final String COL_CHRG_TP = "CHRG_TP";

    public static final String COL_PAYMENT_MODE = "PAYMENT_MODE";

    public static final String COL_OUT_PAYMENT_MODE = "OUT_PAYMENT_MODE";

    public static final String COL_NON_INDUCTIVE = "NON_INDUCTIVE";

    public static final String COL_STATUS = "STATUS";

    public static final String COL_IN_PAY_STATUS = "IN_PAY_STATUS";

    public static final String COL_OUT_PAY_STATUS = "OUT_PAY_STATUS";

    public static final String COL_WHITHER = "WHITHER";

    public static final String COL_PURCH_PRDT = "PURCH_PRDT";

    public static final String COL_REMARK = "REMARK";

    public static final String COL_OPER_ID = "OPER_ID";

    public static final String COL_CREAT_TIME = "CREAT_TIME";

    public static final String COL_OUT_CARGO_WGHT = "OUT_CARGO_WGHT";

    public static final String COL_UP_TIME = "UP_TIME";

    public static final String COL_REPORT_ID = "REPORT_ID";
}