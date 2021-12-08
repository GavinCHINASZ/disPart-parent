package com.dispart.service;

import com.dispart.dto.ResultOutDto;
import com.dispart.dto.makeCard.*;
import com.dispart.request.Request;
import com.dispart.result.Result;

/**
 * @author zhongfei
 * @version 1.0.0
 * @title MakeCardManagerService
 * @creat 2021/8/10 17:50
 * @Copyright 2020-2021
 */
public interface MakeCardManagerService {
    /**
     * 查询制卡申请信息
     * @param param
     * @return
     */
    Result<QuryMakeCardInfoOutDto> quryMakeCardApply(Request<QuryMakeCardInfoInDto> param);

    /**
     * 申请制卡信息
     * @param param
     * @return
     */
    Result<ResultOutDto> addMakeCardApply(Request<ApplyMakeCardInDto> param);

    /**
     * 修改制卡申请信息
     * @param param
     * @return
     */
    Result<ResultOutDto> updateMakeCardApply(Request<UpdateApplyMakeCardInDto> param);

    /**
     * 制卡申请取消
     * @param param
     * @return
     */
    Result<ResultOutDto> cancelMakeCardApply(Request<UpdateMakeCardStatInDto> param);


    /**
     * 制卡申请入库
     * @param param
     * @return
     */
    Result<ResultOutDto> warehousingMakeCardApply(Request<WarehousingInDto> param);

    /**
     * 查询入库信息
     * @param param
     * @return
     */
    Result<QuryWarehousingOutDto> quryWarehousingCard(Request<QuryWarehousingInDto> param);

    /**
     * 查询入库信息详情
     * @param param
     * @return
     */
    Result<QuryWarehousingDetailsOutDto> quryWarehousingCardDetails(Request<QuryWarehousingDetailsInDto> param);

    /**
     * 入库登记查询
     * @param param
     * @return
     */
    Result<QuryWarehousingOutDto> quryCanWarehousingCardInfo(Request<QuryWarehousingInDto> param);


    /**
     *  制卡状态变更
     * @param param
     * @return
     */
    Result<ResultOutDto> updateCardInfoStatus(Request<UpdateMakeCardStatInDto> param);

    /**
     * 入库制卡信息修改
     * @param param
     * @return
     */
    Result<ResultOutDto> updateWarehousingCardInfo(Request<QuryWarehousingInDto> param);
}
