package com.dispart.service;

import com.dispart.dto.dataquery.Disp20210349InDto;
import com.dispart.dto.transactionDto.FrazeTransInDto;
import com.dispart.dto.transactionDto.TransactionSelectInDto;
import com.dispart.result.Result;

public interface PayJrnExportService {

    /**
     * 进出场交易导出
     * @author  zhaoshihao
     * @date 2021/11/29
    */
    Result exportInOutTrans(TransactionSelectInDto inDto);

    /**
     * 充值交易导出
     * @author  zhaoshihao
     * @date 2021/11/29
    */
    Result exportChargeTrans(TransactionSelectInDto inDto);

    /**
     * 提现交易导出
     * @author  zhaoshihao
     * @date 2021/11/29
    */
    Result exportWithdrawTrans(TransactionSelectInDto inDto);

    /**
     * 冻结交易导出
     * @author  zhaoshihao
     * @date 2021/11/29
    */
    Result exportFrazeTrans(FrazeTransInDto inDto);

    /**
     * 冲正交易导出
     * @author  zhaoshihao
     * @date 2021/11/29
    */
    Result exportChargeRecheckTrans(TransactionSelectInDto inDto);

    /**
     * 进场退费导出
     * @author  zhaoshihao
     * @date 2021/11/29
    */
    Result exportOutRefundTrans(Disp20210349InDto inDto);

}
