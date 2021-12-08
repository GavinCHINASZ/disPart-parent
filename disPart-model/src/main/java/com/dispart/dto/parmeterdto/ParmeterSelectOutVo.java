package com.dispart.dto.parmeterdto;

import lombok.Data;

import java.util.List;

@Data
public class ParmeterSelectOutVo {

    private Integer tolPageNum;

    private List<ParmeterInfoDto> parmeterList;

}
