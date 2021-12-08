package com.dispart.service;

import com.dispart.dto.dataquery.Disp20210073InDto;
import com.dispart.dto.dataquery.Disp20210073OutDto;
import com.dispart.result.Result;

public interface CustomRegistInfoQueryService {
    public Result<Disp20210073OutDto> quryCustomRegistInfo(Disp20210073InDto param);
}
