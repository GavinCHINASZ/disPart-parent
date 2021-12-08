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
public class D_0224AddDto {
    //来货报备主表ID
    private String reportId;
    //电话号码
    private String phone;
    //客户名称
    private String provNm;
    //客户编号
    private String provId;
    //车牌号
    private String vehicleNum;

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
    //车辆净重(驾驶证上的车重)
    @ApiModelProperty(value="车辆净重(驾驶证上的车重)")
    private BigDecimal vehicleWeight;
    //报备日期
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reportDt;
    //创建人
    private String creator;
    //操作员
    private String operId;
    //审核状态
    private String auditorSt;
    //产品信息-来货报备明细
    private List<TCargoInfoReportDetails> prdList;
}
