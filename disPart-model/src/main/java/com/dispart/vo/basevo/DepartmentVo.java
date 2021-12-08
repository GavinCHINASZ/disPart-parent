package com.dispart.vo.basevo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


@Data
@ApiModel(description = "部门")
@TableName(value = "t_department_info")
public class DepartmentVo {

    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    @TableId(value = "DEP_ID", type = IdType.INPUT)
    @ApiModelProperty(value="部门ID")
    private String depId;

    /**
     * 上级部门ID
     */
    @TableField(value = "PARENT_DEP_ID")
    @ApiModelProperty(value="上级部门ID")
    private String parentDepId;

    /**
     * 部门名称
     */
    @TableField(value = "DEP_NM")
    @ApiModelProperty(value="部门名称")
    private String depNm;

    /**
     * 部门简称
     */
    @TableField(value = "DEP_SHRT_NM")
    @ApiModelProperty(value="部门简称")
    private String depShrtNm;

    /**
     * 部门状态
     */
    @TableField(value = "DEP_ST")
    @ApiModelProperty(value="部门状态")
    private String depSt;

    /**
     * 所属机构
     */
    @TableField(value = "SUB_ORG")
    @ApiModelProperty(value="所属机构")
    private String subOrg;

    /**
     * 备注
     */
    @TableField(value = "REMARK")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 操作员
     */
    @TableField(value = "OPER_ID")
    @ApiModelProperty(value="操作员")
    private String operId;

    /**
     * 创建日期
     */
    @TableField(value = "CREAT_DT")
    @ApiModelProperty(value="创建日期")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatDt;

    /**
     * 时间错
     */
    @TableField(value = "UPDATE_DT")
    @ApiModelProperty(value="时间错")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDt;

    public static final String COL_DEP_ID = "DEP_ID";

    public static final String COL_PARENT_DEP_ID = "PARENT_DEP_ID";

    public static final String COL_DEP_NM = "DEP_NM";

    public static final String COL_DEP_SHRT_NM = "DEP_SHRT_NM";

    public static final String COL_DEP_ST = "DEP_ST";

    public static final String COL_SUB_ORG = "SUB_ORG";

    public static final String COL_REMARK = "REMARK";

    public static final String COL_OPER_ID = "OPER_ID";

    public static final String COL_CREAT_DT = "CREAT_DT";

    public static final String COL_UPDATE_DT = "UPDATE_DT";
}