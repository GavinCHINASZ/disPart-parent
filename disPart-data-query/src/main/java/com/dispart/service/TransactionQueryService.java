package com.dispart.service;

import com.dispart.baseDto.BaseSelectionOutDto;
import com.dispart.dto.dataquery.Disp20210336InDto;
import com.dispart.dto.dataquery.Disp20210338InDto;
import com.dispart.dto.transactionDto.AdjustTransInDto;
import com.dispart.dto.transactionDto.FrazeTransInDto;
import com.dispart.dto.transactionDto.TransactionSelectInDto;
import com.dispart.result.Result;

import javax.servlet.http.HttpServletResponse;

public interface TransactionQueryService {

    public Result chargeTransactionQuery(TransactionSelectInDto inDto);

    public Result withdrawTransactionQuery(TransactionSelectInDto inDto);

    public Result areaInOutTransactionQuery(TransactionSelectInDto inDto);

    public Result frazeTransQuery(FrazeTransInDto inDto);

    public Result adjustTransQuery(AdjustTransInDto inDto);

    Result exportAdjuestTrans(AdjustTransInDto inDto);

    Result<BaseSelectionOutDto> getPayJrn(Disp20210338InDto inDto);

    void getExcel(Disp20210338InDto inDto, HttpServletResponse response);

    Result getAccnoRevereApplyInfo(TransactionSelectInDto inDto);

}
