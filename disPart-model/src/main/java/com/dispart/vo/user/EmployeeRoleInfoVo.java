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
 * @title employeeRoleInfoVo
 * @Description TODO 员工信息
 * @dateTime 2021/6/11 15:02
 * @Copyright 2020-2021
 */
@Data
@ApiModel(description = "员工角色绑定表")
@TableName("t_employee_role_info")
public class EmployeeRoleInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "员工id")
    @TableField("emp_id")
    private String empId;

    @ApiModelProperty(value = "修改日期")
    @TableField("update_dt")
    private Date updateDt;

    @ApiModelProperty(value = "角色编号")
    @TableField("role_id")
    private String roleId;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;


}
