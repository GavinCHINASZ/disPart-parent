package com.dispart.vo.base;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dispart.vo.basevo.PageInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户优惠记录表 DiscountsUserVo
 *
 * @author 黄贵川
 * @version 1.0.0:
 * @date 2021-09-06
 */
@Data
@ApiModel(description = "用户优惠记录表")
@TableName("t_discounts_user")
public class DiscountsUserVo extends PageInfo {
    /**
     * 优惠活动ID
     */
    private Integer actId;
    /**
     * 客户编号+
     */
    private String provId;
    /**
     * 优惠日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date discntDt;
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
     * 优惠金额
     */
    private BigDecimal discntAmt;
}
