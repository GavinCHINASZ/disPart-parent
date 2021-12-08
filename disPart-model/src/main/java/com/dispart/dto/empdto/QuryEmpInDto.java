package com.dispart.dto.empdto;

import lombok.Data;

import java.util.Date;

/**
 * @author zhongfei
 * @version 1.0.0:
 * @title QuryUserByParam
 * @Description TODO
 * @dateTime 2021/6/9 14:02
 * @Copyright 2020-2021
 */
@Data
public class QuryEmpInDto {
    //用户姓名
    private String empNm;
    //用户Id
    private String empId;
    //用户账号
    private String loginAcct;
    //用户状态
    private String empSt;
    //分页页数
    private Integer pageSize;
    //分页条数
    private Integer pageNum;
}
