package com.dispart.service;

import com.dispart.dto.busineCommon.FindAcctDTO;
import com.dispart.dto.busineCommon.InsertPayJrnDTO;
import com.dispart.model.businessCommon.TPayJrn;
import com.dispart.result.Result;

public interface WithDrawService {

    /**
     * 外部调用新增流水是否直接完成与是否记帐
     * @param params
     * @return
     */
    Result<Object> withdrawByHSB(InsertPayJrnDTO params);

    /**
     * 外部调用更新流水及是否记帐
     * @param params
     * @return
     */
    Result<Object> withdrawByCash(InsertPayJrnDTO params);

    /**
     * 外部调用更新流水及是否记帐
     * @param params
     * @return
     */
    Result<Object> withdrawByPos(InsertPayJrnDTO params);

    /**
     * 外部调用更新流水及是否记帐
     * @param params
     * @return
     */
    Result<Object> withdrawByCard(InsertPayJrnDTO params);

    /**
     * 外部调用更新流水及是否记帐
     * @param params
     * @return
     */
    Result<Object> callBackByHSBWithdraw(TPayJrn params);


    Result<Object> withdrawMoney(InsertPayJrnDTO params);

    /**
     * 外部调用更新流水及是否记帐
     * @param params
     * @return
     */
//    Result<Object> cashOut(InsertPayJrnDTO params);

}
