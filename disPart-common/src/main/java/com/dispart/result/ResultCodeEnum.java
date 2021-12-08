package com.dispart.result;

import lombok.Getter;

/**
 * 统一返回结果状态信息类
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(201, "失败"),
    SYSTEM_ERROR(202, "系统异常"),

    PARAM_ERROR( 202, "参数不正确"),
    SERVICE_ERROR(203, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    DATA_UPDATE_ERROR(205, "数据版本异常"),
    DATA_NO_ERROR(200, "未查询到相关数据！"),
    /****************/
    PARAM_NULL(-1, "请求参数为空"),
    FILE_UPLOAD_FAIL(-2,"文件上传失败"),
    FILE_DOWN_FAIL(-3,"文件下载失败"),
    SEC_FAIL(-3,"签名失败");
    private Integer code;
    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
