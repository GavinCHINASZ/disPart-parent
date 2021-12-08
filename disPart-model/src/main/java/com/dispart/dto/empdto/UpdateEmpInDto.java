package com.dispart.dto.empdto;

import lombok.Data;

import java.util.Date;

/**
 * @author zhongfei
 * @version 1.0.0:
 * @title UpdateEmpByParam
 * @Description TODO
 * @dateTime 2021/6/9 14:24
 * @Copyright 2020-2021
 */
@Data
public class UpdateEmpInDto {
    //用户ID
    private String empId;
    //用户姓名
    private String empNm;
    //用户账号
    private String loginAcct;
    //用户性别
    private String sex;
    //证件类型
    private String certTp;
    //证件号码
    private String certNum;
    //联系电话
    private String telePhone;
    //联系地址
    private String deduAddr;
    //电子邮箱
    private String email;
    //邮编
    private String postCode;
    //所属部门
    private String subDep;
    //所属机构
    private String subOrg;
    //备注
    private String remark;
    //员工状态
    private String empSt;
    //操作员
    private String operId;
    //密码
    private String usPaWd;
}
