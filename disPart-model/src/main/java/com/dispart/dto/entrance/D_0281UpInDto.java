package com.dispart.dto.entrance;

import com.dispart.vo.entrance.TCargoInfoReportDetails;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
public class D_0281UpInDto {

    /**
     * 来货报备主表ID
     */
    @ApiModelProperty(value="来货报备主表ID")
    private String reportId;

    @ApiModelProperty(value="操作 0-修改 1-作废")
    private String workType;

    @ApiModelProperty(value="手机号码")
    private String phone;

    @ApiModelProperty(value="车牌号")
    private String vehicleNum;

    @ApiModelProperty(value="车辆车型")
    private String vehicleId;

    @ApiModelProperty(value="车辆类别ID")
    private String vehicleTpId;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value="报备时间")
    private Date reportDt;

    @ApiModelProperty(value="车辆净重")
    private double vehicleWeight;

    @ApiModelProperty(value="品种总重")
    private double prdctWght;

    @ApiModelProperty(value="车辆总重量")
    private double vehicleTtlWght;

    @ApiModelProperty(value="修改人")
    private String modifier;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="修改时间")
    private Date modifyTime;


    @ApiModelProperty(value="来货报备明细")
    private List<TCargoInfoReportDetails> prdList;

}
