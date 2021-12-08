package com.dispart.controller;

import com.dispart.dto.busineCommon.DISP20210333InDto;
import com.dispart.dto.busineCommon.FindAcctDTO;
import com.dispart.dto.busineCommon.InsertPayJrnDTO;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.AllOrderService;
import com.dispart.service.ChargeService;
import com.dispart.service.HsbService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayJrnController {

    @Autowired
    private AllOrderService allOrderService;

    @PostMapping("/securityCenter/DISP20210275")
    @ApiOperation(value = "不同支付方式支付")
    public Result<Object> InsertPayJrn(@RequestBody InsertPayJrnDTO params ) {
        return allOrderService.insertPayJrn(params);
    }

    @PostMapping("/securityCenter/DISP20210276")
    @ApiOperation(value = "支付结果通知")
    public Result<Object> updatePayJrnAndInsertCardInfo(@RequestBody FindAcctDTO params ) {
        return allOrderService.updateAcctAmt(params);
    }

    @PostMapping("/securityCenter/DISP20210333")
    @ApiOperation(value = "未知状态下支付状态修改")
    public Result<Object> updatePayStatus(@RequestBody Request<DISP20210333InDto> params ) {
        return allOrderService.updatePayStatus(params.getBody());
    }
}
