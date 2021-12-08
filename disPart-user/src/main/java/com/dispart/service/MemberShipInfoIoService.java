package com.dispart.service;

import com.dispart.dto.customdto.ExportCustomInfoOutDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.vo.AccnoInfoDetailVo;

public interface MemberShipInfoIoService {
    /**
     * 调账申请查询导出
     *
     * @author 黄贵川
     * @date 2021/11/11
     * @param inDto Request<ExportCustomInfoOutDto>
     * @return Result<ExportCustomInfoOutDto>
     */
    Result<ExportCustomInfoOutDto> queryReconciliationByParmIo(Request<AccnoInfoDetailVo> inDto);

    /**
     * 调账申请查询导出
     *
     * @author 黄贵川
     * @date 2021/11/11
     * @param inDto Request<ExportCustomInfoOutDto>
     * @return Result<ExportCustomInfoOutDto>
     */
    Result<ExportCustomInfoOutDto> queryWithdrawByParamIo(Request<AccnoInfoDetailVo> inDto);
}
