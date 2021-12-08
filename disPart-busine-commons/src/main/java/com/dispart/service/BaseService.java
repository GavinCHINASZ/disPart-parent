package com.dispart.service;

import com.dispart.dto.busineCommon.*;
import com.dispart.model.PayJrn;
import com.dispart.model.businessCommon.TAccnoInfo;
import com.dispart.model.businessCommon.TPayJrn;

import java.math.BigDecimal;

public interface BaseService {

    /**
     * 新增流水
     * @param params
     * @return
     */
    TPayJrn insertPayJrn(InsertPayJrnDTO params);

    /**
     * 查找流水
     * @param params
     * @return
     */
    TPayJrn findPayJrn(FindPayJrnDTO params);

    /**
     * 更新流水
     * @param params
     * @return
     */
    Boolean updatePayJrn(TPayJrn params);

    /**
     * 查找账户
     * @param params
     * @return
     */
    TAccnoInfo findAcct(FindAcctDTO params);

    /**
     * 记帐
     * @param params
     * @return
     */
    Boolean updateAcctAmt(TAccnoInfo params,BigDecimal txnAmt,TPayJrn tPayJrn);

    /**
     * 检查账户金额
     * @param params
     * @return
     */
    Boolean checkAcctAmt(TAccnoInfo params, BigDecimal txnAmt);

    /**
     * 查找账号
     * @param acct
     * @return
     */
    TAccnoInfo findAcctByAcct(String acct);


    public void payInc(PayJrn payJrn);


}
