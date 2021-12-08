package com.dispart.service;

import com.dispart.dto.busineCommon.FindAcctDTO;
import com.dispart.dto.busineCommon.InsertPayJrnDTO;
import com.dispart.model.businessCommon.TPayJrn;
import com.dispart.result.Result;

public interface PayService {

    /**
     * 外部调用新增流水是否直接完成与是否记帐
     * @param params
     * @return
     */
    Result<Object> payByHSB(InsertPayJrnDTO params);

    /**
     * 外部调用更新流水及是否记帐
     * @param params
     * @return
     */
    Result<Object> payByCash(InsertPayJrnDTO params);

    /**
     * 外部调用更新流水及是否记帐
     * @param params
     * @return
     */
    Result<Object> payByPos(InsertPayJrnDTO params);

    /**
     * 外部调用更新流水及是否记帐
     * @param params
     * @return
     */
    Result<Object> payByCard(InsertPayJrnDTO params);

    /**
     * 外部调用更新流水及是否记帐
     * @param params
     * @return
     */
    Result<Object> callBackByHSBPay(TPayJrn params);

    /**
     * 外部调用更新流水及是否记帐
     * @param params
     * @return
     */
    Result<Object> subsidy(InsertPayJrnDTO params);

}
