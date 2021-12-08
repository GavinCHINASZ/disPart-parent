package com.dispart.model.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CarTypeManage {
    /**
     * 车辆类别ID 01-一类车、02-二类车、03-三类车、04-四类车
     */
    @ApiModelProperty("车辆类别ID 01-一类车、02-二类车、03-三类车、04-四类车")
    @TableField("VEHICEL_TP_ID")
    private String vehicleTpId;
    /**
     * 车辆类别
     */
    @ApiModelProperty("车辆类别")
    @TableField("VEHICLE_TPD")
    private String vehicleTp;
    /**
     * 车辆车型ID
     */
    @ApiModelProperty("车辆车型ID")
    @TableField("VEHICLE_ID")
    private String vehicleId;
    /**
     * 车辆车型
     */
    @ApiModelProperty("车辆车型")
    @TableField("VEHICLE")
    private String vehicle;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    @TableField("REMARK")
    private String remark;
    /**
     * 操作员
     */
    @ApiModelProperty("操作员")
    @TableField("OPER_ID")
    private String operId;
    /**
     * 创建时间
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<CarChargeStdManage> carChargeStdManage=new ArrayList<>();
}
