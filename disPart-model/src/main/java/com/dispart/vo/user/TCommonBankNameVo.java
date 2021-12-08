package com.dispart.vo.user;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * TCommonBankNameVo
 *
 * @author 黄贵川
 * @version 1.0.0:
 * @description 银行常用列表
 * @date 2021/8/18
 * @Copyright 2020-2021
 */
@Data
@ApiModel(description = "银行常用列表")
@TableName("t_common_bank_name")
public class TCommonBankNameVo {
    /**
     * 银行联行号
     */
    private String bankPayNo;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作员
     */
    private String operId;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 更新时间
     */
    private Date upTime;
}