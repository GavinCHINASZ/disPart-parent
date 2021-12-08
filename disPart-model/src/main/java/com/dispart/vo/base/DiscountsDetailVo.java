package com.dispart.vo.base;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dispart.vo.basevo.PageInfo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠活动详情表 DiscountsDetailVo
 *
 * @author 黄贵川
 * @version 1.0.0:
 * @date 2021-09-06
 */
@Data
@ApiModel(description = "优惠活动详情表")
@TableName("t_discounts_detail")
public class DiscountsDetailVo extends PageInfo {
    /**
     * 明细ID
     */
    private Integer detailId;
    /**
     * 优惠活动ID
     */
    private Integer actId;
    /**
     * 条件金额
     */
    private BigDecimal condAmt;
    /**
     * 优惠金额
     */
    private BigDecimal preferPrice;

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
    private String provId;
}