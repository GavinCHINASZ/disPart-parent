package com.dispart.vo.basevo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@ApiModel(value="com-dispart-model-base-TPartModeType")
@Data
public class PartModeTypeVo {
    private static final long serialVersionUID = 1L;
    /**
    * 分账模式ID
    */
    @TableId(value = "PART_MD_ID" , type = IdType.INPUT)
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
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="更新时间戳")
    private Date updateDt;
}