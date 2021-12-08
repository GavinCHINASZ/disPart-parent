package com.dispart.service;

import com.dispart.dto.entrance.D_0223FindDto;
import com.dispart.dto.entrance.D_0224AddDto;
import com.dispart.dto.entrance.D_0225FindDto;
import com.dispart.dto.entrance.D_0226UpInDto;
import com.dispart.dto.entrance.D_0228FindInDto;
import com.dispart.dto.entrance.D_0238FindDto;
import com.dispart.dto.entrance.D_0281UpInDto;
import com.dispart.dto.entrance.D_0345FindDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
//报备

public interface EntranceCargoService {
    /**
     * 查询客户信息与车辆信息
     * @param record
     * @return
     */
    Result findCus(D_0223FindDto record);

    /**
     * 品种信息查询
     * @param record
     * @return
     */
    Result findProd(D_0228FindInDto record);

    /**
     * 添加报备信息
     * @param d_0224AddDtoRequest
     * @return
     */
    Result inCargo(Request<D_0224AddDto>  d_0224AddDtoRequest);

    /**
     * 查询报备信息
     * @param d_0225FindDtoRequest
     * @return
     */
    Result findCargo(Request<D_0225FindDto>  d_0225FindDtoRequest);



    /**
     * 审核来货报备信息
     * @param d_0226UpInDtoRequest
     * @return
     */
    Result updateByPrimaryKey(Request<D_0226UpInDto>  d_0226UpInDtoRequest);

    /**
     * 修改或作废报备信息
     * @param d_0281UpInDtoRequest
     * @return
     */
    Result upCargo(Request<D_0281UpInDto> d_0281UpInDtoRequest);

    /**
     * 查询车辆档案记录
     * @param d_0238FindDtoRequest
     * @return
     */
    Result findVeByVeNum(Request<D_0238FindDto> d_0238FindDtoRequest);

    /**
     * 报备商品产地查询
     * @param d_0345FindDtoRequest
     * @return
     */
    Result findProdPlace(Request<D_0345FindDto> d_0345FindDtoRequest);

}
