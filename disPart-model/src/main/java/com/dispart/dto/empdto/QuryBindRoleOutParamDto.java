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
public class QuryBindRoleOutParamDto {
    //角色名称
    private String roleNm;
    //角色编号
    private String roleId;
    //所属机构
    private String orgId;
    //绑定状态
    private String stat;
    //渠道类型
    private String chanlNo;


}
