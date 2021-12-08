package com.dispart.service;

import com.dispart.dto.billDto.*;
import com.dispart.request.Request;
import com.dispart.result.Result;

public interface BillService {

    public Result addBill(BillInsertInDto billingDetail);

    public Result selectBills(Request<BillSelectionInDto> selectionInDto);

    public Result abolishBill(BillUpdateInDto billUpdateInDto);

    public Result selectPayItems(BillSelectionInDto selectionInDto);

    Result updateBillPayStatus(DISP20210332InDto inDto);

    Result exportBills(Request<BillSelectionInDto> selectionInDto);

    Result noticeBills(Request<DISP20210375InDto> inDto);

}
