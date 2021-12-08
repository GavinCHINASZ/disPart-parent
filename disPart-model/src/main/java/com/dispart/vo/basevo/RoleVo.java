package com.dispart.vo.basevo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

@ApiModel(value="com-dispart-model-base-TRoleInfo")
@Data
@TableName(value = "t_role_info")
public class RoleVo {
    private static final long serialVersionUID = 1L;
    @TableId(value = "ROLE_ID", type = IdType.INPUT)
    @ApiModelProperty(value="")
    private String roleId;

    @TableField(value = "ROLE_NM")
    @ApiModelProperty(value="")
    private String roleNm;

    @TableField(value = "REMARK")
    @ApiModelProperty(value="")
    private String remark;

    @TableField(value = "UPDATE_DT")
    @ApiModelProperty(value="")
    private Date updateDt;

    @TableField(value = "ORG_ID")
    @ApiModelProperty(value="")
    private String orgId;

    @TableField(value = "CHANL_NO")
    @ApiModelProperty(value="渠道类型 0-PC端，1-移动端")
    private String chanlNo;

    public static final String COL_ROLE_ID = "ROLE_ID";

    public static final String COL_ROLE_NM = "ROLE_NM";

    public static final String COL_REMARK = "REMARK";

    public static final String COL_UPDATE_DT = "UPDATE_DT";

    public static final String ORG_ID = "ORG_ID";
    public static final String CHANL_NO = "chanlNo";
}