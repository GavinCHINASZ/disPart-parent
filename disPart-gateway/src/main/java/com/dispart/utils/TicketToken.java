package com.dispart.utils;

import lombok.Data;

/**
 * TOKEN 处理
 */
@Data
public class TicketToken {
    /**
     * 用户编号
     */
    private String userId;
    /**
     * token编号
     */
    private String tokenId;

    /**
     * 根据token与aes密钥构造凭证对象
     * @param token
     * @param aesKey
     * @return
     */
    public static TicketToken valueOf(String token,String aesKey){
        // 使用@分割密钥与向量
        String[] keyArr = aesKey.split("\\@");
        String str = AESUtils.decryptFromBase64(token,keyArr[0],keyArr[1]);
        // 使用@分割userId跟tokenId
        String[] arr = str.split("\\@");
        TicketToken ticket = new TicketToken();
        ticket.setUserId(arr[0]);
        ticket.setTokenId(arr[1]);
        return ticket;
    }

    public static void main(String[] args) {
        String aesKey = "7V5d3q7Xnh9W32e5";
        String toekn = "9OmTxrrB301+ZTmLigcBsA4cQM6aV+LjbCwyKNh1iLhB/waRF9XyuO/VmdRPcRRU5U6tP1M+7OPENsJRoO6ZoctfTjgLnSr9uloGKly03qffR2vwbJbAt7+atmJgKMPFCSgQgL2sQLP62dpGVPnLfw==";
        TicketToken t = TicketToken.valueOf(toekn,aesKey);
        System.out.println("user:"+t.getUserId());
        System.out.println("token:"+t.getTokenId());
    }
}
