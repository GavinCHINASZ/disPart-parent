package com.dispart.service;

import com.dispart.dto.dataquery.Disp20210071InDto;
import com.dispart.dto.dataquery.Disp20210071OutDto;
import com.dispart.result.Result;

public interface CheckDetailQueryService {
    public Result<Disp20210071OutDto> quryCheckDetail(Disp20210071InDto param);

    Result exportCheckDetail(Disp20210071InDto param);
}
