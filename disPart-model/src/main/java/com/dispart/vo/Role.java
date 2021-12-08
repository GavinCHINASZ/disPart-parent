package com.dispart.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dispart.vo.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wujie
 * @version 1.0.0:
 * @title Role
 * @Description TODO 角色
 * @dateTime 2021/6/3 15:14
 * @Copyright 2020-2021
 */
@Data
@ApiModel(description = "角色")
@TableName("acl_role")
public class Role extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "角色名称")
	@TableField("role_name")
	private String roleName;

	@ApiModelProperty(value = "备注")
	@TableField("remark")
	private String remark;

}

