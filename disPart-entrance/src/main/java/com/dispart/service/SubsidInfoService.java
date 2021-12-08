package com.dispart.service;

import com.dispart.dto.customdto.ExportCustomInfoOutDto;
import com.dispart.dto.entrance.D_0297FindDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.vo.entrance.TSubsidInfo;

import javax.servlet.http.HttpServletResponse;

/**
 * 补贴申请
 *
 * @author 黄贵川
 * @date 2021/08/23
 */
public interface SubsidInfoService {
    /**
     * 补贴申请--供应商/采购商进出场信息查询
     *
     * @param record Request<D_0297FindDto>
     * @return Result
     * @author 黄贵川
     * @date 2021/08/24
     */
    Result findEntranceMessage(Request<D_0297FindDto> record);

    /**
     * 补贴申请--新增
     *
     * @author 黄贵川
     * @date 2021/08/23
     * @param record TSubsidInfo
     * @return Result
     */
    Result addSubsidInfo(Request<TSubsidInfo> record);

    /**
     * 补贴申请--查询
     *
     * @author 黄贵川
     * @date 2021/08/23
     * @param body TSubsidInfo
     * @return Result
     */
    Result selectSubsidInfo(TSubsidInfo body);

    /**
     * 补贴申请--修改
     *
     * @author 黄贵川
     * @date 2021/08/23
     * @param body TSubsidInfo
     * @return Result
     */
    Result updateSubsidInfo(TSubsidInfo body);

    /**
     * 补贴申请--作废
     *
     * @param body TSubsidInfo
     * @return Result
     * @author 黄贵川
     * @date 2021/08/24
     */
    Result deleteSubsidInfo(TSubsidInfo body);

    /**
     * 补贴申请--发放
     *
     * @param record TSubsidInfo
     * @return Result
     * @author 黄贵川
     * @date 2021/08/24
     */
    Result grantSubsidInfo(Request<TSubsidInfo> record);

    /**
     * 补贴申请--撤销
     *
     * @param record TSubsidInfo
     * @return Result
     * @author 黄贵川
     * @date 2021/08/24
     */
    Result cancelSubsidInfo(Request<TSubsidInfo> record);

    /**
     * 查询品类
     *
     * @param record TSubsidInfo
     * @return Result
     * @author 黄贵川
     * @date 2021/20/25
     */
    Result findProductTypeInfo(Request<TSubsidInfo> record);

    /**
     * 进出场查询导出
     *
     * @author 黄贵川
     * @date 2021/10/28
     * @param inDto Request<D_0297FindDto>
     */
    Result<ExportCustomInfoOutDto> getExcel(Request<D_0297FindDto> inDto);
}
