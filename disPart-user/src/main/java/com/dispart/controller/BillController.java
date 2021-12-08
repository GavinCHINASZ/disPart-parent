package com.dispart.controller;

import com.dispart.dto.billDto.*;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/securityCenter")
@CrossOrigin
public class BillController {

    @Autowired
    BillService billService;

    @PostMapping("/DISP20210205")
    public Result addBill(@RequestBody Request<BillInsertInDto> detail){ return billService.addBill(detail.getBody()); }

    @PostMapping("/DISP20210206")
    public Result selectBills(@RequestBody Request<BillSelectionInDto> inDto){
        return billService.selectBills(inDto);
    }

    @PostMapping("/DISP20210211")
    public Result abolishBill(@RequestBody Request<BillUpdateInDto> inDto){
        return billService.abolishBill(inDto.getBody());
    }

    @PostMapping("/DISP20210210")
    public Result selectPayItems(@RequestBody Request<BillSelectionInDto> inDto){
        return billService.selectPayItems(inDto.getBody());
    }

    @PostMapping("/DISP20210332")
    public Result updateBillPayStatus(@RequestBody Request<DISP20210332InDto> inDto){
        return billService.updateBillPayStatus(inDto.getBody());
    }

    @PostMapping("/DISP20210361")
    public Result exportBills(@RequestBody Request<BillSelectionInDto> inDto){
        return billService.exportBills(inDto);
    }

    @PostMapping("/DISP20210375")
    public Result noticeBills(@RequestBody Request<DISP20210375InDto> inDto){
        return billService.noticeBills(inDto);
    }

}
