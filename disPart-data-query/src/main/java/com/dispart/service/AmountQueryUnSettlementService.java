package com.dispart.service;

import com.dispart.dto.dataquery.Disp20210068InDto;
import com.dispart.dto.dataquery.Disp20210068OutDto;
import com.dispart.result.Result;

public interface AmountQueryUnSettlementService {
    public Result<Disp20210068OutDto> quryUnSettlementAmount(Disp20210068InDto param);
}
