package com.dispart.dto.menudto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DISP20210022DepFindMenuOutDto {
    //子菜单集合
    private List<DISP20210022DepFindMenuPOutDto> childrenPc=new ArrayList<>();
    private List<DISP20210022DepFindMenuPOutDto> childrenYd=new ArrayList<>();
}
