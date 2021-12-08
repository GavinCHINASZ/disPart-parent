package com.dispart.service;

import com.dispart.dto.dataquery.Disp20210074InDto;
import com.dispart.dto.dataquery.Disp20210074OutDto;
import com.dispart.result.Result;

public interface CustomInfoQueryService {
    public Result<Disp20210074OutDto> quryCustomInfo(Disp20210074InDto param);
}
