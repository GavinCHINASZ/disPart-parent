package com.dispart.dto.departmentdto;

import lombok.Data;

@Data
public class FindDepByParam {
    private String depNm;
    private String subOrg;
    private String depId;
}
