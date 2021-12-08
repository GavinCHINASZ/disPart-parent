package com.dispart.service;

import com.dispart.dto.busineCommon.InsertPayJrnDTO;
import com.dispart.model.businessCommon.TPayJrn;
import com.dispart.result.Result;

public interface ChargeService {

    /**
     * 外部调用新增流水是否直接完成与是否记帐
     * @param params
     * @return
     */
    Result<Object> chargeByHSB(InsertPayJrnDTO params);

    /**
     * 外部调用更新流水及是否记帐
     * @param params
     * @return
     */
    Result<Object> chargeByCash(InsertPayJrnDTO params);

    /**
     * 外部调用更新流水及是否记帐
     * @param params
     * @return
     */
    Result<Object> chargeByPos(InsertPayJrnDTO params);

    /**
     * 回调
     * @param tPayJrn
     * @return
     */
    Result<Object> callBackByHSBCharge(TPayJrn tPayJrn);

    /**
     * 代充值
     * @param params
     * @return
     */
    Result<Object> chargeByPrePay(InsertPayJrnDTO params);

    /**
     * 冲正
     * @param params
     * @return
     */
    Result<Object> chongzheng(InsertPayJrnDTO params);


}
