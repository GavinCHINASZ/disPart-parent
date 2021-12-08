package com.dispart.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 账户mac计算
 */
public class MacUtil {

    private static final Logger logger = LoggerFactory.getLogger(MacUtil.class);

    /**
     * 开户初始化账户mac
     *
     * @param proId   客户编号
     * @param account 客户账户
     * @return 返回计算的mac，如果mac计算出错返回null
     */
    public static String initMac(String proId, String account) {
        return getMac(proId, account, new BigDecimal("0.00"), new BigDecimal("0.00"), new BigDecimal("0.00"));
    }

    /**
     * 获取账户mac
     *
     * @param provId    客户编号
     * @param account   客户账户
     * @param acctBal   账户余额
     * @param availBal  账户可用余额
     * @param freezeAmt 账户冻结金额
     * @return 返回计算的mac，如果mac计算出错返回null
     */
    public static String getMac(String provId, String account, BigDecimal acctBal, BigDecimal availBal, BigDecimal freezeAmt) {
        if (provId == null || provId.length() == 0) {
            logger.error("MacUtil 计算mac,客户编号为空");
            return "";
        }

        if (account == null || account.length() == 0) {
            logger.error("MacUtil 计算mac,客户账户为空");
            return "";
        }

        if (acctBal == null || availBal == null || freezeAmt == null) {
            logger.error("MacUtil 计算mac, 客户账户余额|可用余额|冻结金额为空");
            return "";
        }

        StringBuilder builder = new StringBuilder();
        StringBuilder append = builder.append(provId).append(account).append(acctBal).append(availBal).append(freezeAmt);

        return getMac(append);
    }

    /**
     * 校验mac是否一致
     *
     * @param mac  新计算的mac
     * @param mac1 原记录mac
     * @return 一致返回true 其他返回false
     */
    public boolean verifyMac(String mac, String mac1) {
        if (mac == null || mac1 == null || mac.length() == 0 || mac1.length() == 0) {
            logger.error("mMacUtil ac校验传入参数为空");
            return false;
        }

        return mac.equals(mac1);
    }

    private static String getMac(StringBuilder builder) {
        String mac = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(builder.toString().getBytes());
            mac = Base64.getEncoder().withoutPadding().encodeToString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            logger.error("MacUtil 获取mac出现异常", e);
        }

        return mac;
    }
}
