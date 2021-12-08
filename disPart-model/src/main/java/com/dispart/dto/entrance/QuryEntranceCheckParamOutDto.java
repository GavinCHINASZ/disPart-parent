package com.dispart.dto.entrance;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 进出场信息明细
 */
@Data
public class QuryEntranceCheckParamOutDto {

    /**
     * 进出场ID YYMMDD + 8位序列号
     */
    private String inId;

    /**
     * 车牌号
     */
    private String vehicleNum;

    /**
     * 车辆类型
     */
    private String vehicleId;

    /**
     * 客户编号
     */
    private String provId;

    /**
     * 客户卡号
     */
    private String cardNo;

    /**
     * 客户名称
     */
    private String provNm;

    /**
     * 手机号码
     */
    private String phone;


    /**
     * 进场总重
     */
    private BigDecimal inTtlWght;

    /**
     * 车辆皮重
     */
    private BigDecimal tareWght;

    /**
     * 货物净重
     */
    private BigDecimal cargoWght;

    /**
     * 进场地点
     */
    private String inDoor;

    /**
     * 进场时间
     */
    private Date inTime;


    /**
     * 进场操作人
     */
    private String inOperId;


    /**
     * 进场实际收费
     */
    private BigDecimal inActAmt;
    /**
     * 出场实际收费
     */
    private BigDecimal actRecevAmt;

    /**
     * 进出场类型  0-进场 1-出场
     */
    private String inOutTp;

    /**
     * 出场时间
     */
    private Date outTime;

    /**
     * 出场地点
     */
    private String outDoor;

    /**
     * 是否核验
     */
    private String isCheck;


    /**
     * 是否报备
     */
    private String isReport;

    /**
     * 出场总重
     */
    private BigDecimal outTtlWght;

    /**
     * 出场操作人
     */
    private String outOperId;

    /**
     * 是否退费
     */
    private String isReturn;

    /**
     * 应退金额
     */
    private BigDecimal refundAmt;

    /**
     * 应补金额
     */
    private BigDecimal supptAmt;

    /**
     * 优惠金额
     */
    private BigDecimal preferPrice;

    /**
     * 优惠金额
     */
    private BigDecimal inAmt;


    /**
     * 停车时长
     */
    private Integer parkTime;

    /**
     * 停车收费金额
     */
    private BigDecimal parkAmt;

    /**
     * 停车优惠金额
     */
    private BigDecimal parkCpn;

    /**
     * 停车实收金额
     */
    private BigDecimal parkActAmt;

    /**
     * 收费类型 0-空车 1-供货车 2-免费月卡
     */
    private String chrgTp;

    /**
     * 状态 0-未付款 1-已进场 2-退款中 3-已出场
     */
    private String status;

    /**
     * 去向
     */
    private String whither;


    /**
     * 备注
     */
    private String remark;


    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 更新时间
     */
    private Date upTime;


    /**
     * 是否减免 0-默认 1-固定收费
     */
    private String isDerated;

    /**
     * 是否固定收费 0-否 1-是
     */
    private String isFixed;




    /**
     * 摊位编号
     */
    private String boothNum;

    /**
     * 车辆图片url
     */
    private String vehicleUrl;


    /**
     * 进场备注
     */
    private String inRemark;

    /**
     * 进场收费总金额
     */
    private BigDecimal inTtlAmt;


    /**
     * 临时摊位费
     */
    private BigDecimal boothAmt;


    /**
     * 采购品种 蔬菜 水果 干货粮油 混装 肉类 其他
     */
    private String purchPrdt;


    /**
     * 货物核验人
     */
    private String operId;

    /**
     * 进场核验应退金额
     */
    private BigDecimal checkReturnAmt;

    /**
     * 进场核验应补金额
     */
    private BigDecimal checkSupptAmt;

    /**
     * 支付方式
     */
    private String paymentMode;

    /**
     * 进场操作人名称
     */
    private String inOperNm;


    private List<EntranceVeCheckOutDatilsDto> datilsList;
}
