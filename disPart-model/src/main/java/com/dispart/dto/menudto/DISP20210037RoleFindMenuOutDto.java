package com.dispart.dto.menudto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DISP20210037RoleFindMenuOutDto {


    //PC端集合
    private List<DISP20210037RoleFindMenuPOutDto> childrenPc = new ArrayList<>();
    //移动端集合
    private List<DISP20210037RoleFindMenuPOutDto> childrenYd = new ArrayList<>();
}
