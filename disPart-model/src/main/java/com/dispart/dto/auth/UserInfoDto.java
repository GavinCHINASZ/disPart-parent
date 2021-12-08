package com.dispart.dto.auth;

import lombok.Data;

/**
 * @author:xts
 * @date:Created in 2021/6/20 17:17
 * @description 移动端用户信息
 * @modified by:
 * @version: 1.0
 */
@Data
public class UserInfoDto {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户姓名
     */
    private String userNm;
    /**
     * 用户昵称
     */
    private String userNickNm;
    /**
     * 用户头像
     */
    private String userIcon;
    /**
     * 用户密码
     */
    private String userPasswd;
    /**
     * 用户手机号
     */
    private String userPhone;
    /**
     * 注册时间
     */
    private Object regDt;
    /**
     * 用户状态
     */
    private String userSt;
    /**
     * 首次登陆
     */
    private String isFirstLand;
    /**
     * 登陆状态
     */
    private String loginSt;
    /**
     * 微信小程序ID
     */
    private String wxpayId;
    /**
     * 支付宝小程序ID
     */
    private String alipayId;
    /**
     * 更新时间戳
     */
    private Object updateDt;

}