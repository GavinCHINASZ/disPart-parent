package com.dispart.dto.empdto;

import lombok.Data;

import java.util.List;

/**
 * @author zhongfei
 * @version 1.0.0:
 * @title BundingRoleByParam
 * @Description TODO
 * @dateTime 2021/6/9 14:55
 * @Copyright 2020-2021
 */
@Data
public class BundingRoleInDto {
    //员工id
    private String empId;
    //角色id
    private List<BundingRoleParamInDto> list;
}
