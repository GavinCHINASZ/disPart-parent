package com.dispart.dto.entrance;

import com.dispart.vo.entrance.TCargoInfoReportDetails;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
public class D_0225FindOutYDto {

    /**
     * 来货报备主表ID
     */
    @ApiModelProperty(value="来货报备主表ID")
    private String reportId;
    /**
     * 客户名称
     */
    @ApiModelProperty(value="客户名称")
    private String provNm;
    /**
     * 客户编号
     */
    @ApiModelProperty(value="客户编号")
    private String provId;
    /**
     * 车牌号
     */
    @ApiModelProperty(value="车牌号")
    private String vehicleNum;
    /**
     * 手机号码
     */
    @ApiModelProperty(value="手机号码")
    private String phone;
    /**
     * 审核状态 0-未审核 1-审核通过 2-审核未通过 3-已进场
     */
    @ApiModelProperty(value="审核状态 0-未审核 1-审核通过 2-审核未通过 3-已进场")
    private String auditorSt;
    /**
     * 车辆净重(驾驶证上的车重)
     */
    @ApiModelProperty(value="车辆净重(驾驶证上的车重)")
    private BigDecimal vehicleWeight;
    /**
     * 品种总重
     */
    @ApiModelProperty(value="品种总重")
    private BigDecimal prdctWght;
    /**
     * 车辆总重量
     */
    @ApiModelProperty(value="车辆总重量")
    private BigDecimal vehicleTtlWght;
    /**
     * 记录状态 0-正常  1-作废
     */
    @ApiModelProperty(value="记录状态 0-正常  1-作废")
    private String recordSt;
    /**
     * 操作员/创建人
     */
    @ApiModelProperty(value="操作员/创建人")
    private String operId;
    /**
     * 审核人
     */
    @ApiModelProperty(value="审核人")
    private String auditor;

    /**
     * 审核时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value="审核时间")
    private Date auditorTm;

    /**
     * 报备日期
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value="报备日期")
    private Date reportDt;


    /**
     * 车辆车型id
     */
    @ApiModelProperty(value="车辆车型id")
    private String vehicleId;

    /**
     * 车辆类别ID
     */
    @ApiModelProperty(value="车辆类别ID")
    private String vehicleTpId;

    /**
     * 车辆类别
     */
    @ApiModelProperty(value="车辆类别")
    private String vehicleTp;


    /**
     * 车辆车型ID
     */
    @ApiModelProperty(value="车辆车型")
    private String vehicle;

    /**
     * 是否免进场费
     */
    @ApiModelProperty(value="是否免进场费")
    private String isFree;

    /**
     * 创建人
     */
    @ApiModelProperty(value="创建人")
    private String creator;

    /**
     * 修改人
     */
    @ApiModelProperty(value="修改人")
    private String modifier;

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

    //产品信息-来货报备明细
    private List<TCargoInfoReportDetails> prdList;


}
