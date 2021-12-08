package com.dispart.service;

import com.dispart.dto.customdto.ExportCustomInfoOutDto;
import com.dispart.dto.entrance.D_0297FindDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.vo.entrance.TSubsidInfo;

/**
 * 补贴申请
 *
 * @author 黄贵川
 * @date 2021/08/23
 */
public interface SubsidInfoIoService {
    /**
     * 供应商补贴导出
     *
     * @author 黄贵川
     * @date 2021/11/11
     * @param inDto Request<D_0297FindDto>
     * @return Result<ExportCustomInfoOutDto>
     */
    Result<ExportCustomInfoOutDto> supplySubsidInfoIoMethod(Request<D_0297FindDto> inDto);

    /**
     * 采购商补贴导出
     *
     * @author 黄贵川
     * @date 2021/11/11
     * @param inDto Request<TSubsidInfo>
     * @return Result<ExportCustomInfoOutDto>
     */
    Result<ExportCustomInfoOutDto> pruchaseSubsidInfoIoMethod(Request<D_0297FindDto> inDto);

    /**
     * 补贴申请查询导出
     *
     * @author 黄贵川
     * @date 2021/11/11
     * @param inDto Request<TSubsidInfo>
     * @return Result<ExportCustomInfoOutDto>
     */
    Result<ExportCustomInfoOutDto> subsidInfoIoMethod(Request<TSubsidInfo> inDto);
}
