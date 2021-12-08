package com.dispart.vo.basevo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ApiModel(description = "角色的权限管理")
@TableName(value = "t_dep_org_menu_info")
public class RoleMenu {
    private static final long serialVersionUID = 1L;


    @TableId(value = "ROLE_ID", type = IdType.INPUT)
    @ApiModelProperty(value="角色ID")
    private String roleId;

    @TableId(value = "MENU_Id")
    @ApiModelProperty(value="菜单ID")
    private String menuId;

    @TableId(value = "REMARK")
    @ApiModelProperty(value="备注")
    private String remark;

    @TableId(value = "DATA_PARM")
    @ApiModelProperty(value="数据权限")
    private String dataParm;

    @TableField(value = "UPDATE_DT")
    @ApiModelProperty(value="修该时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDt;

}
