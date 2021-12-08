package com.dispart.controller;

import com.dispart.dto.DeviceRelevanceDto.Disp20210296InDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.TDeviceRelevanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("securityCenter")
@CrossOrigin
public class DeviceRelevanceController {

    @Autowired
    private TDeviceRelevanceService service;

    @PostMapping("/DISP20210296")
    public Result getIODeviceInfo(@RequestBody Request<Disp20210296InDto> inDtoRequest){
        return service.getIODeviceInfo(inDtoRequest.getBody());
    }
}
