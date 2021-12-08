package com.dispart.model.base;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CarTypeAndDetails {
    @ApiModelProperty("车辆车型")
    private String vehicleTpId;
    private String vehicleTp;
    List<CarTypeManage> carTypeManage=new ArrayList<>();
}
