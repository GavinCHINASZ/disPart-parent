package com.dispart.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dispart.service.GnYunDaiService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/outGnLoan")
public class GnYunDaiController {

    @Resource
    private GnYunDaiService service;

    @PostMapping("/DISP20210347")
    public String relayRequest(@RequestBody String param){
        return service.relayRequest(param);
    }
}
