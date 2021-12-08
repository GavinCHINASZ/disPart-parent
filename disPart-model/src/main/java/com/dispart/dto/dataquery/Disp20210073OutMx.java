package com.dispart.dto.dataquery;

import lombok.Data;


@Data
public class Disp20210073OutMx {
    private String provId;

    /* 用户ID */
    private String userId;

    /* 用户姓名 */
    private String userNm;

    /* 用户昵称 */
    private String userNickNm;

    /* 用户头像 */
    private String userIcon;

    /* 用户手机号 */
    private String userPhone;

    /* 注册时间 */
    private String regDt;

    /* 用户状态 */
    private String userSt;

    /* 首次登陆 */
    private String isFirstLand;

    /* 登陆状态 */
    private String loginSt;

    /* 更新时间戳 */
    private String updateDt;

    /* 微信小程序appID */
    private String wxpayId;

    /* 支付宝小程序 */
    private String alipayId;
    /**
     * 渠道类型：01-贵农购 02-微信小程序 03-支付宝小程序 04-农批系统 05-智慧贵农app 06-外联服务
     */
    private String chanlNo;
}
