package com.dispart.service;

import com.dispart.baseDto.BaseSelectionOutDto;
import com.dispart.dto.dataquery.Disp20210072InDto;
import com.dispart.dto.dataquery.Disp20210072OutDto;
import com.dispart.dto.dataquery.Disp20210334InDto;
import com.dispart.model.TReconciliationResult;
import com.dispart.result.Result;

public interface CheckResultQueryService {

    public Result<Disp20210072OutDto> quryCheckResult(Disp20210072InDto param);

    Result<BaseSelectionOutDto> quryMainCheckResult(Disp20210334InDto inDto);

    Result exportMainCheckResult(Disp20210334InDto inDto);

}
