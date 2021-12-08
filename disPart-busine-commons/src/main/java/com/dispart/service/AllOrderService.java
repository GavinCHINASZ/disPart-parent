package com.dispart.service;

import com.dispart.dto.busineCommon.DISP20210333InDto;
import com.dispart.dto.busineCommon.FindAcctDTO;
import com.dispart.dto.busineCommon.InsertPayJrnDTO;
import com.dispart.result.Result;
import com.dispart.model.businessCommon.TAccnoInfo;

import java.math.BigDecimal;

public interface AllOrderService {
    /**
     * 新增流水
     * @param params
     * @return
     */
    Result<Object> insertPayJrn(InsertPayJrnDTO params);

    /**
     * 更新流水以及是否需要记帐
     * @param params
     * @return
     */
    Result<Object> updateAcctAmt(FindAcctDTO params);

    /**
     * 未知状态下支付状态修改
     * @author  zhaoshihao
     * @date 2021/9/11
    */
    Result updatePayStatus(DISP20210333InDto inDto);
}
