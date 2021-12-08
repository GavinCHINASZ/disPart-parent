package com.dispart.dto.empdto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhongfei
 * @version 1.0.0:
 * @title QuryBindRoleByParam
 * @Description TODO
 * @dateTime 2021/6/4 10:32
 * @Copyright 2020-2021
 */
@Data
public class QuryBindRoleOutDto {
    //角色列表
    private List<QuryBindRoleOutParamDto> list;

    private Integer tolPageNum;//总条数
}
