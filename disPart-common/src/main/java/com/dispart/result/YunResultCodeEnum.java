package com.dispart.result;

import lombok.Getter;

@Getter
public enum YunResultCodeEnum {

    REQ_YUN_FAIL1(901,"报⽂签名错误"),
    REQ_YUN_FAIL2(902,"报⽂设备离线或关机"),
    REQ_YUN_FAIL3(908,"报⽂设备离线或关机"),
    REQ_YUN_FAIL4(909,"报⽂超时，平台可查询后再重发"),
    REQ_YUN_FAIL5(801,"报⽂播报队列满，在同⼀时间内发送了⼏⼗条播报消息的情况下返回，平台可延时重发"),
    REQ_YUN_FAIL6(808,"平台发送了重复的播报消息"),
    REQ_YUN_FAIL7(809,"其它未知错误"),
    REQ_YUN_SUCCESS(200,"播报成功");



    private final Integer code;
    private final String message;

    YunResultCodeEnum(Integer code, String message) {
        this.code=code;
        this.message=message;
    }
}
