package com.dispart.vo.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
@ApiModel(description = "客户提现累加金额")
@TableName(value = "t_custom_withdraw_tmp")
public class CustomWithdrawTmpVo implements Serializable {
    private static final long serialVersionUID = -1790835803389330185L;

    /**
     * 客户编号
     */
    @TableId(value = "PROV_ID")
    @ApiModelProperty(value="客户编号")
    private String provId;

    /**
     * 累计金额
     */
    @TableId(value = "ACCRU_AMT")
    @ApiModelProperty(value="累计金额")
    private BigDecimal accruAmt;

    /**
     * 备注
     */
    @TableId(value = "REMARK", type = IdType.INPUT)
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "CREAT_TIME")
    @ApiModelProperty(value="创建时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatTime;

    /**
     * 修该时间
     */
    @TableField(value = "UP_TIME")
    @ApiModelProperty(value="修该时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date upTime;
}
