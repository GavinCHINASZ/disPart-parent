package com.dispart.vo.hsb;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @author zhongfei
 * @version 1.0.0:
 * @title placeOrderType
 * @Description TODO 员工信息
 * @dateTime 2021/6/11 15:02
 * @Copyright 2020-2021
 */
@Data
@ApiModel(description = "客户下单模式表")
@TableName("t_place_order_type")
public class PlaceOrderTypeVo {
    //供货商id
    private String provId;
    //下单模式
    private String placeOrderMd;
    //下单模式名称
    private String placeOrderNm;
    //状态
    private String status;
    //备注
    private String remark;
    //更新时间
    private Date updateDt;

}
