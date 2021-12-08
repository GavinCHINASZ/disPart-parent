package com.dispart.vo.commons;

import java.io.Serializable;
import java.util.Date;

/**
 * (TEmployeeRoleInfo)实体类
 *
 * @author makejava
 * @since 2021-06-20 15:17:10
 */
public class TEmployeeRoleInfo implements Serializable {
    private static final long serialVersionUID = -93355559380328781L;
    /**
    * 员工id
    */
    private String empId;
    /**
    * 角色id
    */
    private String roleId;
    /**
    * 备注
    */
    private String remark;
    /**
    * 更新时间戳
    */
    private Date updateDt;

    private String chanlNo;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getChanlNo() {
        return chanlNo;
    }

    public void setChanlNo(String chanlNo) {
        this.chanlNo = chanlNo;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

}