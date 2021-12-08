package com.dispart.dto.entrance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 进场货物核验-货物明细
 */
@Data
public class EntranceVeCheckOutDatilsDto {

    /**
     * 进出场ID
     */
    private String inId;

    /**
     * 品种ID
     */
    private String varietyId;

    /**
     * 车牌号
     */
    private String vehicleNum;

    /**
     * 客户编号
     */
    private String provId;

    /**
     * 费用类型 0-进场品种收费 1-摊位费
     */
    private String expTp;

    /**
     * 品种名称
     */
    private String prdctNm;

    /**
     * 品种费率
     */
    private BigDecimal rate;

    /**
     * 重量
     */
    private BigDecimal num;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 金额
     */
    private BigDecimal amt;

    /**
     * 单位
     */
    private String unit;

    /**
     * 产地
     */
    private String prodPlace;

    /**
     * 产地图片Url
     */
    private String placeUrl;

    /**
     * 生产企业
     */
    private String manufactEnter;

    /**
     * 状态 0-未检测 5-待检测 默认0
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作员/创建人
     */
    private String operId;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 更新时间
     */
    private Date upTime;

    /**
     * 操作类型
     */
    private String operTp;

    /**
     * 重量
     */
    private BigDecimal Weight;

    /**
     * 品种ID
     */
    private String categoryId;
    /**
     *
     */
    private String categoryNm;

}
