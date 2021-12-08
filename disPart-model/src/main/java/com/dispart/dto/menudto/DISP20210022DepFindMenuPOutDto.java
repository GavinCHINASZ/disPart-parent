package com.dispart.dto.menudto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DISP20210022DepFindMenuPOutDto {
    //ID
    private String id;
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

    //菜单渠道类型
    private String  chnalNoType;

    private List<DISP20210022DepFindMenuPOutDto> children=new ArrayList<>();
}
