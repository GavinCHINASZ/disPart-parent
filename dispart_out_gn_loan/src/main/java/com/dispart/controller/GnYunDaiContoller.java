package com.dispart.controller;

import com.dispart.service.GnYunDaiService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 提供给贵农云贷查询商户交易信息进行信用评估
 * @author  zhaoshihao
 * @date 2021/10/9
*/
@RestController
@RequestMapping("/outGnLoan")
public class GnYunDaiContoller {

    @Resource
    private GnYunDaiService service;

    @PostMapping("/DISP20210347")
    public String getBorrowerInfo(@RequestBody String data){
        return service.getBorrowerInfo(data);
    }

}
