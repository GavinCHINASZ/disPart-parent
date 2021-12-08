package com.dispart.service;

import com.dispart.dto.MCardInfoDto.*;
import com.dispart.result.Result;

public interface MCardService {

    public Result addMCardInfo(AddMCardInfoInDto inDto);

    public Result selectMCardInfo(MCardInfoSelectionInDto inDto);

    public Result abolishMCardInfo(MCardInfoInsertDto inDto);

    public Result updateMCardInfo(McardPayDetailUpdateInDto info);

    public Result selectMCardPayDetails(MCardInfoSelectionInDto inDto);

    Result exportMCardPayDetails(MCardInfoSelectionInDto inDto);

    Result updateMCardPayDetailStatus(DISP20210331InDto inDto);

}
