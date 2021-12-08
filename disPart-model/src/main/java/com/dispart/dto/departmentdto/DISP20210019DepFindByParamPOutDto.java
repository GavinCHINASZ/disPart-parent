package com.dispart.dto.departmentdto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class DISP20210019DepFindByParamPOutDto {
    private String depId;
    private String depNm;
    //部门简称
    private String depShrtNm;
    //部门状态
    private String depSt;
    //所属机构号
    private String subOrg;
    //所属机构名称
    private String subOrgNm;
    //上级部门ID
    private String parentDepId;
    //上级部门名称
    private String parentDepNm;


    //备注
    private String remark;
    //创建日期
    private Date creatDt;
    //修改日期
    private Date updateDt;

    List<DISP20210019DepFindByParamPOutDto> depBranchList = new ArrayList<>();
}
