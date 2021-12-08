package com.dispart.vo.base;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dispart.vo.basevo.PageInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 优惠活动表 DiscountsActivityVo
 *
 * @author 黄贵川
 * @version 1.0.0:
 * @date 2021-09-06
 */
@Data
@ApiModel(description = "优惠活动表")
@TableName("t_discounts_activity")
public class DiscountsActivityVo extends PageInfo {
    /**
     * 优惠活动ID
     */
    private Integer actId;
    /**
     * 优惠活动名称
     */
    private String actNm;
    /**
     * 优惠活动开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDt;
    /**
     * 优惠活动结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDt;
    /**
     * 优惠周期 单位（天）
     */
    private Integer discntPrd;
    /**
     *优惠次数
     */
    private Integer discntNum;
    /**
     * 状态 0-未启用  1-已启用
     */
    private String status;
    /**
     * 操作员名称
     */
    private String operName;
    private String remark;
    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 更新时间
     */
    private Date upTime;

    private List<DiscountsDetailVo> discountsDetailVoList;
}