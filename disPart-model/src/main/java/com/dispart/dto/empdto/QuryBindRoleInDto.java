package com.dispart.dto.empdto;

import lombok.Data;

/**
 * @author zhongfei
 * @version 1.0.0:
 * @title QuryBindRoleByParam
 * @Description TODO
 * @dateTime 2021/6/4 10:32
 * @Copyright 2020-2021
 */
@Data
public class QuryBindRoleInDto {
    //员工ID
    private String empId;
    //渠道类型
    private String chanlNo;
    //分页页数
    private Integer pageSize;
    //分页条数
    private Integer pageNum;
}
