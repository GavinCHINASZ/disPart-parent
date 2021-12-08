package com.dispart.config;

import lombok.Getter;

/**
 * 统一返回结果状态信息类
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(201, "失败"),
    SYSTEM_ERROR(202, "系统异常"),
    NotFoundException(-804,"服务资源找不到！"),
    NotAuthException(-805,"客户端请求鉴权失败！"),
    NOTPASSWDException(-807,"客户端请求报文解密失败！"),
    NotTokenException(-806,"未登录，没有访问权限！");

    private Integer code;
    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
