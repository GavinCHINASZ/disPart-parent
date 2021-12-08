package com.dispart.dto.empdto;

import lombok.Data;

import java.util.Date;
@Data
public class UpdateEmpParamDto {
    //员工Id
    private String empId;
    //员工状态
    private String empSt;
    //原状态
    private String OrigSt;
    //更新时间
    private Date updateDt;
    //操作员
    private String operId;
    //失效日期
    private Date expDt;

}
