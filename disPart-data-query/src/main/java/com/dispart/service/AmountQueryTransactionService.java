package com.dispart.service;

import com.dispart.dto.dataquery.Disp20210066InDto;
import com.dispart.dto.dataquery.Disp20210066OutDto;
import com.dispart.result.Result;

public interface AmountQueryTransactionService {

    public Result<Disp20210066OutDto> quryTransactionAmount(Disp20210066InDto param);

}
