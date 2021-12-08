package com.dispart.dto.menudto;

import com.dispart.vo.basevo.RoleMenu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DISP20210038RoleUpMenuInDto {
    //权限录入集合
    private List<RoleMenu> children=new ArrayList<>();
    //角色ID
    private String roleId;
}
