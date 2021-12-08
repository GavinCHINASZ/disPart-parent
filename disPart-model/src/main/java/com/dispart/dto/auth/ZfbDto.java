package com.dispart.dto.auth;

import lombok.Data;

/**
 * @author:xts
 * @date:Created in 2021/6/30 23:10
 * @description
 * @modified by:
 * @version: 1.0
 */
@Data
public class ZfbDto {
    private String userId;
    private String accessToken;
    private String refreshToken;
    private String expiresIn;
    private String reExpiresIn;
}