package com.dispart.service;

import com.dispart.dto.entrance.QuryEntranceVeCheckInDto;
import com.dispart.dto.entrance.QuryEntranceVeCheckOutDto;
import com.dispart.request.Request;
import com.dispart.result.Result;

public interface EntranceTurnOverService {

    Result<QuryEntranceVeCheckOutDto> quryInOutInfo(Request<QuryEntranceVeCheckInDto> param);
}
