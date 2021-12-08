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

import java.util.Date;

@Data
@ApiModel(description = "部门或机构的权限管理")
@TableName(value = "t_dep_org_menu_info")
public class DepOrgMenuVo {
    private static final long serialVersionUID = 1L;

    /**
     * 部门或机构ID
     */
    @TableId(value = "ID", type = IdType.INPUT)
    @ApiModelProperty(value="部门或机构ID")
    private String id;
    /**
     * 菜单ID
     */
    @TableId(value = "MENU_Id")
    @ApiModelProperty(value="菜单ID")
    private String menuId;
    /**
     * 菜单ID
     */
    @TableId(value = "REMARK", type = IdType.INPUT)
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 修该时间
     */
    @TableField(value = "UPDATE_DT")
    @ApiModelProperty(value="修该时间")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDt;



}
