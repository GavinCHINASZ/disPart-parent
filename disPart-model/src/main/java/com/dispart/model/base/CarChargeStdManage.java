package com.dispart.model.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@TableName("t_vechicle_pay_manager")
public class CarChargeStdManage extends PageInfo{
    /**
     * 车辆收费标准Id
     */
    @ApiModelProperty("车辆收费标准Id")
    @TableField(value = "CHRG_ID")
    private String chrgId;
    /**
     *车辆车型Id
     */
    @ApiModelProperty("车辆车型Id")
    @TableField("VEHICLE_ID")
    private String vehicleId;
    /**
     * 车辆类别
     */
    @ApiModelProperty("车辆类别")
    @TableField("VEHICLE_TP")
    private String vehicleTp;
    /**
     *车车辆车型
     */
    @ApiModelProperty("车辆车型")
    @TableField("VEHICLE")
    private String vehicle;
    /**
     *收费类型
     */
    @ApiModelProperty("收费类型")
    @TableField("CHRG_TP")
    private String chrgTp;
    /**
     *车辆收费方式  0-每多少小时多少钱 1-固定收费
     */
    @ApiModelProperty("车辆收费方式")
    @TableField("CHRG_MD")
    private String chrgMd;
    /**
     *收费开始区间
     */
    @ApiModelProperty("收费开始区间")
    @TableField("CHRG_ST_TM")
    private Double chrgStTm;
    /**
     *收费结束区间
     */
    @ApiModelProperty("收费结束区间")
    @TableField("CHRG_END_TM")
    private Double chrgEndTm;
    /**
     *固定费用
     */
    @ApiModelProperty("固定费用")
    @TableField("FIXED_CHRG")
    private Double fixedChrg;
    /**
     *收费标准时长/单位小时
     */
    @ApiModelProperty("收费标准时长/单位小时")
    @TableField("CHRG_TM")
    private Integer chrgTm;
    /**
     *车收费标准单价/单位元
     */
    @ApiModelProperty("车收费标准单价/单位元")
    @TableField("CHRG_RATE")
    private double chrgRate;
    /**
     *备注
     */
    @ApiModelProperty(value = "备注")
    @TableField(value = "REMARK")
    private String remark;
    /**
     * 操作员
     */
    @ApiModelProperty(value = "操作员")
    @TableField(value = "OPER_ID")
    private String operId;
    /**
     *创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREAT_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatTime;
    /**
     *更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField(value = "UP_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date upTime;
}
