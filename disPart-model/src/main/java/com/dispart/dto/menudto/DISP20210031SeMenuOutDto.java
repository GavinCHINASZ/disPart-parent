package com.dispart.dto.menudto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DISP20210031SeMenuOutDto {
    //菜单名称
    private String title;
    //菜单ID
    private String menuId;
    //上级菜单ID
    private String parentMenuId;
    //菜单类型
    private String menuType;
    //菜单url
    private String path;

    //备注
    private String remark;

    //子菜单集合
    private List<DISP20210031SeMenuOutDto> children=new ArrayList<>();
}
