package com.dispart.dto.partmodetype;

import com.dispart.vo.basevo.PartModeTypeVo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class DISP20210101SePMTOutDto {
    List<PartModeTypeVo> modelList = new ArrayList<>();
    //总条数
    private Integer tolPageNum;
}
