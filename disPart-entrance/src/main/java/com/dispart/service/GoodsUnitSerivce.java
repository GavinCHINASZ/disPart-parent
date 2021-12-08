package com.dispart.service;

import com.dispart.dto.ResultOutDto;
import com.dispart.dto.entrance.TGoodsUnitInDto;
import com.dispart.dto.entrance.TGoodsUnitOutDto;
import com.dispart.request.Request;
import com.dispart.result.Result;

public interface GoodsUnitSerivce {
    Result<TGoodsUnitOutDto> quryGoodsUnitInfo(Request<TGoodsUnitInDto> param);

    Result<ResultOutDto> setGoodUnit(Request<TGoodsUnitInDto> param);

    Result<ResultOutDto> setGoodUnitRelevance(Request<TGoodsUnitInDto> param);

    Result<ResultOutDto> deleteGoodUnitRelevance(Request<TGoodsUnitInDto> param);

    Result<ResultOutDto> deleteGoodUnit(Request<TGoodsUnitInDto> param);
}
