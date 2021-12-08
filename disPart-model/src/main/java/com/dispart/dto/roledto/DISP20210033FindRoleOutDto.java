package com.dispart.dto.roledto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DISP20210033FindRoleOutDto {
    private List<DISP20210033FindRolePOutDto> roleArray = new ArrayList<>();
    //总条数
    private Integer tolPageNum;
}
