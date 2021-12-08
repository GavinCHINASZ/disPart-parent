package com.dispart.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="com-dispart-model-order-Sequence")
@Data
@TableName(value = "logistics.`sequence`")
public class Sequence {
    /**
     * 序列名称
     */
    @TableId(value = "SEQ_NAME", type = IdType.INPUT)
    @ApiModelProperty(value="序列名称")
    private String seqName;

    /**
     * 当前值
     */
    @TableField(value = "CURR_VAL")
    @ApiModelProperty(value="当前值")
    private Long currVal;

    /**
     * 步长
     */
    @TableField(value = "INCREMENT_VAL")
    @ApiModelProperty(value="步长")
    private Long incrementVal;

    /**
     * 备注
     */
    @TableField(value = "REMAKR")
    @ApiModelProperty(value="备注")
    private String remakr;

    public static final String COL_SEQ_NAME = "SEQ_NAME";

    public static final String COL_CURR_VAL = "CURR_VAL";

    public static final String COL_INCREMENT_VAL = "INCREMENT_VAL";

    public static final String COL_REMAKR = "REMAKR";
}