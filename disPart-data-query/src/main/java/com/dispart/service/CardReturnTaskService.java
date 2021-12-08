package com.dispart.service;

import com.dispart.dto.dataquery.Disp20210349InDto;
import com.dispart.dto.dataquery.Disp20210349OutDto;
import com.dispart.result.Result;

public interface CardReturnTaskService {

    Result<Disp20210349OutDto> CardReturnTaskQuery(Disp20210349InDto inDto);

}
