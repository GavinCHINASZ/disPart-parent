package com.dispart.controller;

import com.dispart.dto.MCardInfoDto.*;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.MCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/securityCenter")
@CrossOrigin
public class MCardInfoController {

    @Autowired
    private MCardService service;

    @PostMapping("/DISP20210219")
    public Result addMCardInfo(@RequestBody Request<AddMCardInfoInDto> info){
        return service.addMCardInfo(info.getBody());
    }

    @PostMapping("/DISP20210220")
    public Result selectMCardInfo(@RequestBody Request<MCardInfoSelectionInDto> inDto){
        return service.selectMCardInfo(inDto.getBody());
    }

    @PostMapping("/DISP20210221")
    public Result abolishMCardInfo(@RequestBody Request<MCardInfoInsertDto> inDto){
        return service.abolishMCardInfo(inDto.getBody());
    }

    @PostMapping("/DISP20210222")
    public Result updateMCardInfo(@RequestBody Request<McardPayDetailUpdateInDto> info){
        return service.updateMCardInfo(info.getBody());
    }

    @PostMapping("/DISP20210307")
    public Result selectMCardPayDetails(@RequestBody Request<MCardInfoSelectionInDto> info){
        return service.selectMCardPayDetails(info.getBody());
    }

    @PostMapping("/DISP20210331")
    public Result updateMCardPayDetailStatus(@RequestBody Request<DISP20210331InDto> inDto){
        return service.updateMCardPayDetailStatus(inDto.getBody());
    }

    @PostMapping("/DISP20210357")
    public Result exportMCardPayDetails(@RequestBody Request<MCardInfoSelectionInDto> inDto){
        return service.exportMCardPayDetails(inDto.getBody());
    }

}
