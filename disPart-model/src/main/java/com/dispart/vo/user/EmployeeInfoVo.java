package com.dispart.vo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhongfei
 * @version 1.0.0:
 * @title employeeInfo
 * @Description TODO 员工信息
 * @dateTime 2021/6/9 11:14
 * @Copyright 2020-2021
 */
@Data
@ApiModel(description = "员工信息表")
@TableName("t_employee_info")
public class EmployeeInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "员工id")
    @TableField("emp_id")
    private String empId;

    @ApiModelProperty(value = "登录账号")
    @TableField("login_acct")
    private String loginAcct;

    @ApiModelProperty(value = "员工姓名")
    @TableField("emp_nm")
    private String empNm;

    @ApiModelProperty(value = "性别")
    @TableField("sex")
    private String sex;

    @ApiModelProperty(value = "证件类型")
    @TableField("cert_type")
    private String certType;

    @ApiModelProperty(value = "证件号码")
    @TableField("cert_num")
    private String certNum;

    @ApiModelProperty(value = "电话")
    @TableField("telephone")
    private String telephone;

    @ApiModelProperty(value = "联系地址")
    @TableField("dedu_addr")
    private String deduAddr;

    @ApiModelProperty(value = "电子邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "邮编")
    @TableField("postcode")
    private String postcode;

    @ApiModelProperty(value = "所属部门")
    @TableField("sub_dep")
    private String subDep;

    @ApiModelProperty(value = "所属机构")
    @TableField("sub_org")
    private String subOrg;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "生效日期")
    @TableField("effe_dt")
    private Date effeDt;

    @ApiModelProperty(value = "修改日期")
    @TableField("update_dt")
    private Date updateDt;

    @ApiModelProperty(value = "失效日期")
    @TableField("exp_dt")
    private Date expDt;

    @ApiModelProperty(value = "密码")
    @TableField("passwd")
    private String passwd;

    @ApiModelProperty(value = "更新密码时间")
    @TableField("update_passwd_dt_tm")
    private Date updatePasswdDtTm;

    @ApiModelProperty(value = "员工状态")
    @TableField("emp_st")
    private String empSt;


    @ApiModelProperty(value = "操作员工 0-正常 1-锁定 ")
    @TableField("OPER_ID")
    private String operId;
}
