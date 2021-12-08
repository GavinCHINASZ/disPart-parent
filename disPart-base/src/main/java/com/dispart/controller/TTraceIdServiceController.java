package com.dispart.controller;

import com.dispart.dto.prdctPriceDto.DISP20210311InDto;
import com.dispart.dto.prdctPriceDto.DISP20210312InDto;
import com.dispart.dto.prdctPriceDto.DISP20210356InDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.ProductPriceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/securityCenter")
public class TTraceIdServiceController {

    @Resource
    private ProductPriceService service;

    @PostMapping("/DISP20210311")
    public Result productPriceQuery(@RequestBody Request<DISP20210311InDto> inDto){
        return service.productPriceQuery(inDto.getBody());
    }

    @PostMapping("/DISP20210312")
    public Result insertProductPrice(@RequestBody Request<DISP20210312InDto> inDto){
        return service.insertProductPrice(inDto.getBody());
    }

    @PostMapping("/DISP20210356")
    public Result delProductPrice(@RequestBody Request<DISP20210356InDto> inDto){
        return service.delProductPrice(inDto.getBody());
    }

}
