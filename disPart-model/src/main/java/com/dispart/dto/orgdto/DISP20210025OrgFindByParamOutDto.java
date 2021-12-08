package com.dispart.dto.orgdto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DISP20210025OrgFindByParamOutDto {
    private List<DISP20210025OrgFindByParamPOutDto> orgArrayList = new ArrayList<>();
    //总条数
    private Integer tolPageNum;
}
