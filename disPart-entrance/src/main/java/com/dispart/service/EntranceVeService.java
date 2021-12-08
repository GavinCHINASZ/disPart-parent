package com.dispart.service;

import com.dispart.dto.entrance.D_0227FindDto;
import com.dispart.dto.entrance.D_0229addInDto;
import com.dispart.dto.entrance.D_0230addInDto;
import com.dispart.dto.entrance.D_0231upInDto;
import com.dispart.dto.entrance.D_0232FindDto;
import com.dispart.dto.entrance.D_0233Dto;
import com.dispart.dto.entrance.D_0234findInDto;
import com.dispart.dto.entrance.D_0235UpInDto;
import com.dispart.dto.entrance.D_0236Dto;
import com.dispart.dto.entrance.D_0237FindDto;
import com.dispart.dto.entrance.D_0346FindDto;
import com.dispart.dto.entrance.D_0365FindDto;
import com.dispart.dto.entrance.D_0503FindDto;
import com.dispart.request.Request;
import com.dispart.result.Result;

//进出场
public interface EntranceVeService {
    /**
     * 大车进场查询报备信息
     * @param d_0227FindDtoRequest
     * @return
     */
    Result findVePro(Request<D_0227FindDto> d_0227FindDtoRequest);

    /**
     * 添加大车进场记录
     * @param d_0229addInDtoRequest
     * @return
     */
    Result addVeDate(Request<D_0229addInDto> d_0229addInDtoRequest);

    /**
     * 添加空车进场记录
     * @param d_0230addInDtoRequest
     * @return
     */
    Result addMinVeDate(Request<D_0230addInDto> d_0230addInDtoRequest);

    /**
     * 收款后变更记录状态
     * @param d_0231upInDtoRequest
     * @return
     */
    Result upVeSt(Request<D_0231upInDto>  d_0231upInDtoRequest);

    /**
     * 根据设备编号查询进出场口信息
     * @param d_0232FindDtoRequest
     * @return
     */
    Result findVeInOut(Request<D_0232FindDto>  d_0232FindDtoRequest);
    /**
     * 无人工进出场
     * @param d_0233DtoRequest
     * @return
     */
    Result upInOut(Request<D_0233Dto>  d_0233DtoRequest);
    /**
     * 查询空车进场记录
     * @param d_0234findInDtoRequest
     * @return
     */
    Result findInVe(Request<D_0234findInDto>  d_0234findInDtoRequest);

    /**
     * 变更进场记录
     * @param d_0235UpInDtoRequest
     * @return
     */
    Result upInVe(Request<D_0235UpInDto> d_0235UpInDtoRequest);

    /**
     * 出场缴费
     * @param d_0236DtoRequest
     * @return
     */
    Result outPay(Request<D_0236Dto>  d_0236DtoRequest);

    /**
     * 查询出场支付结果
     * @param d_0237FindDtoRequest
     * @return
     */
    Result finOutPayInfo(Request<D_0237FindDto> d_0237FindDtoRequest);

    /**
     * 大车出场-货物去向查询
     * @param d_0346FindDtoRequest
     * @return
     */
    Result findWhither(Request<D_0346FindDto> d_0346FindDtoRequest);

    /**
     * 进出场管理-收费员收费统计
     * @param d_0365FindDtoRequest
     * @return
     */
    Result chargeStatistics(Request<D_0365FindDto> d_0365FindDtoRequest);

    /**
     * 进出场管理-收费员收费统计导出
     * @param d_0365FindDtoRequest
     * @return
     */
    Result getStatisticsExcel(Request<D_0365FindDto> d_0365FindDtoRequest);

    /**
     * 进出场管理-进出场停车费查询
     * @param d_0503FindDtoRequest
     * @return
     */
    Result getParkingInfo(Request<D_0503FindDto> d_0503FindDtoRequest);

    /**
     * 进出场管理-进出场停车费查询导出
     * @param d_0503FindDtoRequest
     * @return
     */
    Result getParkingExcel(Request<D_0503FindDto> d_0503FindDtoRequest);

}
