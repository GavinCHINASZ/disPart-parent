package com.dispart.vo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhongfei
 * @version 1.0.0:
 * @title UserInfo
 * @Description TODO 用户信息
 * @dateTime 2021/6/9 11:27
 * @Copyright 2020-2021
 */
@Data
@ApiModel(description = "用户信息表")
@TableName("t_user_info")
public class UserInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "客户id")
    @TableField("prov_id")
    private String provId;

    @ApiModelProperty(value = "用户姓名")
    @TableField("user_nm")
    private String userNm;

    @ApiModelProperty(value = "用户昵称")
    @TableField("user_nick_nm")
    private String userNickNm;

    @ApiModelProperty(value = "用户头像")
    @TableField("user_icon")
    private String userIcon;

    @ApiModelProperty(value = "用户头手机号")
    @TableField("user_phone")
    private String userPhone;

    @ApiModelProperty(value = "用户密码")
    @TableField("user_passwd")
    private String userPasswd;

    @ApiModelProperty(value = "注册时间")
    @TableField("reg_dt")
    private Date regDt;

    @ApiModelProperty(value = "用户状态")
    @TableField("user_st")
    private String userSt;

    @ApiModelProperty(value = "首次登录，0-首次登录，1-非首次登录")
    @TableField("is_first_land")
    private String isFirstLand;

    @ApiModelProperty(value = "登录状态，0-离线状态，1-登录状态")
    @TableField("login_st")
    private String loginSt;

    @ApiModelProperty(value = "更新时间戳")
    @TableField("update_dt")
    private Date updateDt;

    @ApiModelProperty(value = "微信小程appID")
    @TableField("wxpay_id")
    private String wxpayId;

    @ApiModelProperty(value = "微信小程appID")
    @TableField("alipay_id")
    private String alipayId;

    @ApiModelProperty(value = "渠道类型：01-贵农购 02-微信小程序 03-支付宝小程序 04-农批系统 05-智慧贵农app 06-外联服务")
    @TableField("CHANL_NO")
    private String chanlNo;
}
