package com.dispart.dto.auth;

import lombok.Data;

/**
 * @author:xts
 * @date:Created in 2021/6/24 15:00
 * @description
 * @modified by:
 * @version: 1.0
 */
@Data
public class userJsonDto {
    //userAccount  userPass orgCode phoneTel smsCode loginType (0-账号登录 1-验证码登录) sfCode(第三方COde)
    private String userAccount;
    private String usPaWD;
    private String orgCode;
    private String phoneTel;
    private String smsCode;
    private String loginType;
    private String sfCode;
    private String versionNo;
    private String reqSeqNo;
    private String chanlNo;
    private String operator;
    private String roleType;
    private String wxOpenId;//微信openid
    private String zfbOpenId;//支付宝openid
    private String clientId;//设备id

}