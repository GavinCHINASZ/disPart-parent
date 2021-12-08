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
 * @title UserRole
 * @Description TODO 用户角色
 * @dateTime 2021/6/3 15:14
 * @Copyright 2020-2021
 */
@Data
@ApiModel(description = "用户角色")
@TableName("acl_user_role")
public class UserRole extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "角色id")
	@TableField("role_id")
	private Long roleId;

	@ApiModelProperty(value = "用户id")
	@TableField("user_id")
	private Long userId;

}

