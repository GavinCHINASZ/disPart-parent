package com.dispart.service;

import com.dispart.dto.empdto.QuryEmpInDto;
import com.dispart.dto.empdto.QuryEmpOutDto;
import com.dispart.result.Result;

public interface QuryEmployeeService {
    public Result<QuryEmpOutDto> quryEmployeeInfo(QuryEmpInDto param);
}
