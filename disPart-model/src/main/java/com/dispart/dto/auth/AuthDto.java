package com.dispart.dto.auth;

import lombok.Data;

/**
 * @author:xts
 * @date:Created in 2021/6/19 17:14
 * @description 权限信息
 * @modified by:
 * @version: 1.0
 */
@Data
public class AuthDto {
    private String id;
    private String pid;
    private String authCode;
    private String authUrl;
    private String authName;
    private String className;
    private String authType;
    private String authJson;
}