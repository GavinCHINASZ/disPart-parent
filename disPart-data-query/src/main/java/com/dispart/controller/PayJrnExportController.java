package com.dispart.controller;

import com.dispart.dto.dataquery.Disp20210349InDto;
import com.dispart.dto.transactionDto.FrazeTransInDto;
import com.dispart.dto.transactionDto.TransactionSelectInDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.PayJrnExportService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/securityCenter")
public class PayJrnExportController {

    @Resource
    private PayJrnExportService service;

    /**
     * 进出场交易导出
     * @author  zhaoshihao
     * @date 2021/11/29
     */
    @PostMapping("/DISP20210522")
    Result exportInOutTrans(@RequestBody Request<TransactionSelectInDto> inDto){
        return service.exportInOutTrans(inDto.getBody());
    }

    /**
     * 充值交易导出
     * @author  zhaoshihao
     * @date 2021/11/29
     */
    @PostMapping("/DISP20210523")
    Result exportChargeTrans(@RequestBody Request<TransactionSelectInDto> inDto){
        return service.exportChargeTrans(inDto.getBody());
    }

    /**
     * 提现交易导出
     * @author  zhaoshihao
     * @date 2021/11/29
     */
    @PostMapping("/DISP20210524")
    Result exportWithdrawTrans(@RequestBody Request<TransactionSelectInDto> inDto){
        return service.exportWithdrawTrans(inDto.getBody());
    }

    /**
     * 冻结交易导出
     * @author  zhaoshihao
     * @date 2021/11/29
     */
    @PostMapping("/DISP20210525")
    Result exportFrazeTrans(@RequestBody Request<FrazeTransInDto> inDto){
        return service.exportFrazeTrans(inDto.getBody());
    }

    /**
     * 冲正交易导出
     * @author  zhaoshihao
     * @date 2021/11/29
     */
    @PostMapping("/DISP20210526")
    Result exportChargeRecheckTrans(@RequestBody Request<TransactionSelectInDto> inDto){
        return service.exportChargeRecheckTrans(inDto.getBody());
    }

    /**
     * 进场退费导出
     * @author  zhaoshihao
     * @date 2021/11/29
     */
    @PostMapping("/DISP20210527")
    Result exportOutRefundTrans(@RequestBody Request<Disp20210349InDto> inDto){
        return service.exportOutRefundTrans(inDto.getBody());
    }
}
