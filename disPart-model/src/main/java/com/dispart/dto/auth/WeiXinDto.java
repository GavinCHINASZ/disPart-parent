package com.dispart.dto.auth;

import lombok.Data;

/**
 * @author:xts
 * @date:Created in 2021/6/24 13:19
 * @description
 * @modified by:
 * @version: 1.0
 */
@Data
public class WeiXinDto {
    private String openid;
    private String session_Key;
}