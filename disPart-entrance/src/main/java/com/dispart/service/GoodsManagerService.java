package com.dispart.service;

import com.dispart.dto.ResultOutDto;
import com.dispart.dto.entrance.TProductInventoryInfoDto;
import com.dispart.dto.entrance.TProductInventoryInfoOutDto;
import com.dispart.request.Request;
import com.dispart.result.Result;

public interface GoodsManagerService {
    /**
     * 查询货物信息
     * @param param
     * @return
     */
    public Result<TProductInventoryInfoOutDto> quryGoodsInfo(String userNo,Request<TProductInventoryInfoDto> param) ;

    /**
     * 货物信息修改
     * @param param
     * @return
     */
    Result<ResultOutDto> updateGoodsInfo(Request<TProductInventoryInfoDto> param);

    /**
     * 货物信息新增
     * @param param
     * @return
     */
    Result<ResultOutDto> addGoodsInfo(Request<TProductInventoryInfoDto> param);


    /**
     * 货物统计查询
     * @param param
     * @return
     */
    Result<TProductInventoryInfoOutDto> countGoodsInfo(Request<TProductInventoryInfoDto> param);
}
