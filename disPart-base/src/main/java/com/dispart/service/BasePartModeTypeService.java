package com.dispart.service;

import com.dispart.dto.partmodetype.DISP20210101SePMTInDto;
import com.dispart.dto.partmodetype.DISP20210101SePMTOutDto;
import com.dispart.result.Result;
import com.dispart.vo.basevo.PartModeTypeVo;

import java.util.List;

public interface BasePartModeTypeService {
    Result<DISP20210101SePMTOutDto> sePMT(DISP20210101SePMTInDto record);

    Result insert(PartModeTypeVo record);

    Result updateByPrimaryKey(PartModeTypeVo record);

    Result upStByPrimaryKey(PartModeTypeVo record);



}
