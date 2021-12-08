package com.dispart.dto.menudto;

import com.dispart.vo.basevo.DepOrgMenuVo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DISP20210023UpDepMenuInDto {
    //部门或者机构设置权限集合
    private List<DepOrgMenuVo> children = new ArrayList<>();

    //部门或机构ID
    private String id;

    //机构或部门判断（1-部门，其他-机构）
    private int isDepOrg;
}
