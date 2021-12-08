package com.dispart.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@ApiModel(value="com-dispart-model-order-TPartModeType")
@Data
@TableName(value = "logistics.t_part_mode_type")
public class TPartModeType {
    /**
     * 分账模式ID
     */
    @TableId(value = "PART_MD_ID", type = IdType.INPUT)
    @ApiModelProperty(value="分账模式ID")
    private String partMdId;

    /**
     * 分账模式类型(0-比例模式 1-固定值模式)
     */
    @TableField(value = "PART_MD_TP")
    @ApiModelProperty(value="分账模式类型(0-比例模式 1-固定值模式)")
    private String partMdTp;

    /**
     * 分账模式名称
     */
    @TableField(value = "PART_MD_NM")
    @ApiModelProperty(value="分账模式名称")
    private String partMdNm;

    /**
     * 分账模式值
     */
    @TableField(value = "PART_MD_VAL")
    @ApiModelProperty(value="分账模式值")
    private BigDecimal partMdVal;

    /**
     * 分账模式状态（0-选中 1-未选中）
     */
    @TableField(value = "STATUS")
    @ApiModelProperty(value="分账模式状态（0-选中 1-未选中）")
    private String status;

    /**
     * 备注
     */
    @TableField(value = "REMARK")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 更新时间戳
     */
    @TableField(value = "UPDATE_DT")
    @ApiModelProperty(value="更新时间戳")
    private Date updateDt;

    public static final String COL_PART_MD_ID = "PART_MD_ID";

    public static final String COL_PART_MD_TP = "PART_MD_TP";

    public static final String COL_PART_MD_NM = "PART_MD_NM";

    public static final String COL_PART_MD_VAL = "PART_MD_VAL";

    public static final String COL_STATUS = "STATUS";

    public static final String COL_REMARK = "REMARK";

    public static final String COL_UPDATE_DT = "UPDATE_DT";
}