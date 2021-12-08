package com.dispart.dto.userdto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmpRoleFindMenuOutDto {
    //菜单ID
    private String menuId;
    //备注
    private String remark;

    //上级菜单ID
    private String parentMenuId;

    //菜单名称
    private String title;

    //菜单类型
    private String menuType;

    //权限状态
    private String menuSt;
    //数据权限
    private String dataParm;
    //角色ID
    private String roleId;
    //渠道类型
    private String chnalNoType;
    //子菜单集合
    private List<EmpRoleFindMenuOutDto> children=new ArrayList<>();
}
