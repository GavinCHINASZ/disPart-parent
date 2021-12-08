package com.dispart.service;

import com.dispart.dto.busineCommon.FindAcctDTO;
import com.dispart.dto.busineCommon.InsertPayJrnDTO;
import com.dispart.result.Result;

public interface CashOutService {

    /**
     * 外部调用新增流水是否直接完成与是否记帐
     * @param params
     * @return
     */
    Result<Object> cashout(InsertPayJrnDTO params);


    /**
     * 现金提现
     */
    Result<Object> cashoutByCash(InsertPayJrnDTO params);
}
