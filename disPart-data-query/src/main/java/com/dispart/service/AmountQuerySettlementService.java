package com.dispart.service;

import com.dispart.dto.dataquery.Disp20210067InDto;
import com.dispart.dto.dataquery.Disp20210067OutDto;
import com.dispart.result.Result;

public interface AmountQuerySettlementService {
    public Result<Disp20210067OutDto> qurySettlementAmount(Disp20210067InDto param);
}
