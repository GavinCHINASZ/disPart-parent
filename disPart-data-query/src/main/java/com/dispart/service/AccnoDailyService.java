package com.dispart.service;

import com.dispart.dto.dataquery.Disp20210336InDto;
import com.dispart.result.Result;

public interface AccnoDailyService {

    Result getAccnoDaily(Disp20210336InDto inDto);

    Result exportAccnoDaily(Disp20210336InDto inDto);

}
