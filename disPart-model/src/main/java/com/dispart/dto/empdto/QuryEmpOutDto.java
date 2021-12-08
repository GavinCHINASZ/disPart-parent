package com.dispart.dto.empdto;

import lombok.Data;

import java.util.List;

/**
 * @author zhongfei
 * @version 1.0.0:
 * @title QuryEmpByResult
 * @Description TODO
 * @dateTime 2021/6/9 14:12
 * @Copyright 2020-2021
 */
@Data
public class QuryEmpOutDto {
    //返回员工信息列表
    private List<QuryEmpOutParamDto> list;
    //总条数
    private Integer tolPageNum;


}
