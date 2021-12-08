package com.dispart.dto.auth;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * (TEmployeeInfo)实体类
 *
 * @author makejava
 * @since 2021-06-20 15:07:32
 */
@Data
public class TEmployeeInfoDto  {
    /**
    * 员工ID
    */
    private String empId;
    /**
    * 登陆账号
    */
    private String loginAcct;
    /**
    * 员工姓名
    */
    private String empNm;
    /**
    * 性别
    */
    private String sex;
    /**
    * 证件类型
    */
    private String certType;
    /**
    * 证件号码
    */
    private String certNum;
    /**
    * 电话
    */
    private String telephone;
    /**
    * 电子邮箱
    */
    private String email;
    /**
    * 邮编
    */
    private String postcode;
    /**
    * 所属部门
    */
    private String subDep;
    //部门名称
    private String subDepName;
    /**
    * 所属机构
    */
    private String subOrg;
    //机构名称
    private String subOrgName;
    /**
    * 备注
    */
    private String remark;
    /**
    * 生效日期
    */
    private Object effeDt;
    /**
    * 失效日期
    */
    private Object expDt;
    /**
    * 密码
    */
    private String passwd;
    /**
    * 更新密码时间
    */
    private Object updatePasswdDtTm;
    /**
    * 员工状态
    */
    private String empSt;
    /**
    * 操作员
    */
    private String operId;
    /**
    * 时间戳
    */
    private Object updateDt;

    private List<TRoleInfoDto> roles;
}