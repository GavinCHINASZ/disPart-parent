package com.dispart.dto.menudto;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class DISP20210022DepFindMenuInDto {
    //部门或机构ID
    private String id;

    //部门或机构标识(0-部门，1-机构)
    private Integer DOSt;

    //菜单渠道类型
    //private String  chanlNoType;
}
