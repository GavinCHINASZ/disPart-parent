package com.dispart.service;

import com.dispart.baseDto.BaseSelectionOutDto;
import com.dispart.dto.dataquery.Disp20210069InDto;
import com.dispart.dto.dataquery.Disp20210069OutDto;
import com.dispart.dto.dataquery.Disp20210335InDto;
import com.dispart.dto.dataquery.Disp20210335OutDto;
import com.dispart.result.Result;

public interface OrderInfoQueryService {

    Result<Disp20210069OutDto> quryOrderInfo(Disp20210069InDto param);

    Result<BaseSelectionOutDto> getOrderGoods(Disp20210335InDto inDto);

    Result exportData(Disp20210069InDto param);

}
