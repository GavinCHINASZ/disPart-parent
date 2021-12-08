package com.dispart.dto;

import lombok.Data;

/**
 * @author zhongfei
 * @version 1.0.0:
 * @title ResultValue
 * @Description TODO
 * @dateTime 2021/6/9 14:29
 * @Copyright 2020-2021
 */
@Data
public class ResultOutDto {
    public static final String SUCCESS ="0";
    public static final String FAIL ="1";

    //返回结果 0-成功 1-失败
    private String result;

}
