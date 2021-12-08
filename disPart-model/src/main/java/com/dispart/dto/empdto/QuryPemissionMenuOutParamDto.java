package com.dispart.dto.empdto;

import java.util.ArrayList;

public class QuryPemissionMenuOutParamDto {
    //菜单ID
    private String menuId;
    //上级菜单ID
    private String parentMenuId;
    //菜单名称
    private String menuNm;
    //菜单类型
    private String menuType;
    //备注
    private String remark;
    //权限状态
    private String menuSt;
    //权限菜单集合
    private ArrayList<QuryPemissionMenuOutParamDto> menuArrayListPart;

}
