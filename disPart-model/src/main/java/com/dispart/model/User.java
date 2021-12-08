package com.dispart.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.dispart.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author wujie
 * @version 1.0.0:
 * @title User
 * @Description TODO 用户
 * @dateTime 2021/6/3 15:14
 * @Copyright 2020-2021
 */
@Data
@ApiModel(description = "用户")
@TableName("sys_user")
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户名")
	@TableField("username")
	private String username;

	@ApiModelProperty(value = "密码")
	@TableField("password")
	private String password;

	@ApiModelProperty(value = "昵称")
	@TableField("nick_name")
	private String nickName;

	@ApiModelProperty(value = "限制允许登录的IP集合")
	@TableField("limited_ip")
	private String limitedIp;

	@ApiModelProperty(value = "账号失效时间，超过时间将不能登录系统")
	@TableField("expired_time")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date expiredTime;

	@ApiModelProperty(value = "最近修改密码时间，超出时间间隔，提示用户修改密码")
	@TableField("last_change_pwd_time")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastChangePwdTime;

	@ApiModelProperty(value = "是否允许账号同一个时刻多人在线，Y/N")
	@TableField("limit_multi_login")
	private String limitMultiLogin;

}



