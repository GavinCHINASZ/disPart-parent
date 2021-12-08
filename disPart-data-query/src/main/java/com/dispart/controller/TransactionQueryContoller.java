package com.dispart.controller;

import com.dispart.dto.dataquery.Disp20210336InDto;
import com.dispart.dto.dataquery.Disp20210338InDto;
import com.dispart.dto.transactionDto.AdjustTransInDto;
import com.dispart.dto.transactionDto.FrazeTransInDto;
import com.dispart.dto.transactionDto.TransactionSelectInDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.AccnoDailyService;
import com.dispart.service.TransactionQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/securityCenter")
@CrossOrigin
public class TransactionQueryContoller {

    @Autowired
    private TransactionQueryService service;

    @Autowired
    private AccnoDailyService accnoDailyService;

    @PostMapping("/DISP20210214")
    public Result chargeTransactionQuery(@RequestBody Request<TransactionSelectInDto> inDto){
        return service.chargeTransactionQuery(inDto.getBody());
    }

    @PostMapping("/DISP20210215")
    public Result withdrawTransactionQuery(@RequestBody Request<TransactionSelectInDto> inDto){
        return service.withdrawTransactionQuery(inDto.getBody());
    }

    @PostMapping("/DISP20210216")
    public Result areaInOutTransactionQuery(@RequestBody Request<TransactionSelectInDto> inDto){
        return service.areaInOutTransactionQuery(inDto.getBody());
    }

    @PostMapping("/DISP20210217")
    public Result frazeTransQuery(@RequestBody Request<FrazeTransInDto> inDto){
        return service.frazeTransQuery(inDto.getBody());
    }

    @PostMapping("/DISP20210218")
    public Result adjustTransQuery(@RequestBody Request<AdjustTransInDto> inDto){
        return service.adjustTransQuery(inDto.getBody());
    }

    @PostMapping("/DISP20210372")
    public Result exportAdjuestTrans(@RequestBody Request<AdjustTransInDto> inDto){
        return service.exportAdjuestTrans(inDto.getBody());
    }

    @PostMapping("/DISP20210336")
    public Result getAccnoDaily(@RequestBody Request<Disp20210336InDto> inDto){
        return accnoDailyService.getAccnoDaily(inDto.getBody());
    }

    @PostMapping("/DISP20210371")
    public Result exportAccnoDaily(@RequestBody Request<Disp20210336InDto> inDto){
        return accnoDailyService.exportAccnoDaily(inDto.getBody());
    }


    @PostMapping("/DISP20210338")
    public Result getPayJrn(@RequestBody Request<Disp20210338InDto> inDto){
        return service.getPayJrn(inDto.getBody());
    }

    @PostMapping("/DISP20210340")
    public void getExcel(@RequestBody Disp20210338InDto inDto, HttpServletResponse response){
        service.getExcel(inDto,response);
    }

    @PostMapping("/DISP20210366")
    public Result getAccnoRevereApplyInfo(@RequestBody Request<TransactionSelectInDto> inDto){
        return service.getAccnoRevereApplyInfo(inDto.getBody());
    }

}
