package com.dispart.dto.empdto;

import lombok.Data;

/**
 * @author zhongfei
 * @version 1.0.0:
 * @title LockEmpByParam
 * @Description TODO
 * @dateTime 2021/6/9 14:38
 * @Copyright 2020-2021
 */
@Data
public class LockEmpInDto {
    //用户ID
    private String empId;
    //员工状态
    private String empSt;
}
