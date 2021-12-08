package com.dispart.model.businessCommon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
    * 卡片明细表
    */
@ApiModel(value="com-dispart-tmp-TCardInfo")
@Data
@TableName(value = "logistics.t_card_info")
public class TCardInfo {
    /**
     * 卡号
     */
    @TableId(value = "CARD_NO", type = IdType.INPUT)
    @ApiModelProperty(value="卡号")
    private String cardNo;

    /**
     * 单据号
     */
    @TableField(value = "DOCUMENT_NUM")
    @ApiModelProperty(value="单据号")
    private String documentNum;

    /**
     * 状态 0-未使用 1-已使用
     */
    @TableField(value = "STATUS")
    @ApiModelProperty(value="状态 0-未使用 1-已使用")
    private String status;

    /**
     * 备注(异常时填写)
     */
    @TableField(value = "REMARK")
    @ApiModelProperty(value="备注(异常时填写)")
    private String remark;

    /**
     * 操作员
     */
    @TableField(value = "OPER_ID")
    @ApiModelProperty(value="操作员")
    private String operId;

    /**
     * 创建时间/申请时间
     */
    @TableField(value = "CREAT_TIME")
    @ApiModelProperty(value="创建时间/申请时间")
    private Date creatTime;

    /**
     * 更新时间
     */
    @TableField(value = "UP_TIME")
    @ApiModelProperty(value="更新时间")
    private Date upTime;

    public static final String COL_CARD_NO = "CARD_NO";

    public static final String COL_DOCUMENT_NUM = "DOCUMENT_NUM";

    public static final String COL_STATUS = "STATUS";

    public static final String COL_REMARK = "REMARK";

    public static final String COL_OPER_ID = "OPER_ID";

    public static final String COL_CREAT_TIME = "CREAT_TIME";

    public static final String COL_UP_TIME = "UP_TIME";
}