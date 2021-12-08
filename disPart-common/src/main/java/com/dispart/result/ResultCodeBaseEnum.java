package com.dispart.result;

import lombok.Getter;

@Getter
public enum ResultCodeBaseEnum {


    PAGE_NUM_0(1,"当前页数不能为0"),
//    DEPID_NULL(2,"部门ID不能为空"),
//    DEPID_ORGID_NULL(3,"部门ID或机构ID不能为空"),
//    ORGID_NULL(4,"机构ID不能为空"),
//    SUBORG_NULL(5,"未上送所属机构号"),
    PARTDEPID_SUBORG_NULL(2,"因该部门没有上级部门也没有所属机构,找不到对应权限"),
    PARTORGID_ORG_NULL(3,"无对应部门"),
    PARTMDVAL_0(4,"比例模式的分账数值必须小于1"),
    ORG_HAVE_NEXTORG(5,"该机构存在子机构，删除本机构前请先删除机构名下子机构"),
    ORG_HAVE_NEXTDEP(6,"该机构存在部门，删除本机构前请先删除机构名下部门"),
    ORG_HAVE_Role(7,"存在在用角色，删除本机构前请先删除机构名下角色"),
    DEP_HAVE_NEXTDEP(8,"该部门存在子部门，删除当前部门前请先删除部门名下子部门"),
    DEP_HAVE_EMP(9,"该部门存在正常员工，删除当前部门前请先删除部门名下正常员工"),
    ROLE_HAVE_EMP(10,"该角色存在关联用户，删除当前角色前请先解绑相关用户"),

    ROLE_MENU_PARTSUCCESS(200,"权限变更失败--通知安全中心失败"),
    SUCCESS(200,"操作成功"),
    FAIL(201,"操作失败");



    private final Integer code;
    private final String message;

    ResultCodeBaseEnum(Integer code, String message) {
        this.code=code;
        this.message=message;
    }
}
