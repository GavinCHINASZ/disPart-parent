package com.dispart.service;

import com.dispart.dto.ResultOutDto;
import com.dispart.dto.entrance.UpdateEntranceVeCheckInDto;
import com.dispart.dto.entrance.QuryEntranceVeCheckInDto;
import com.dispart.dto.entrance.QuryEntranceVeCheckOutDto;
import com.dispart.request.Request;
import com.dispart.result.Result;

public interface EntranceVeCheckService {
    /**
     * 查询货物进场信息
     * @param param
     * @return
     */
    Result<QuryEntranceVeCheckOutDto> quryVeCheckInfo(Request<QuryEntranceVeCheckInDto> param);

    /**
     * 进场货物核验
     * @param param
     * @return
     */
    Result<ResultOutDto> upDateVeCheckInfo(Request<UpdateEntranceVeCheckInDto> param);
}
