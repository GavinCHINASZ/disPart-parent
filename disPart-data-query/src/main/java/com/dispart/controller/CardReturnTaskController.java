package com.dispart.controller;

import com.dispart.dto.dataquery.Disp20210349InDto;
import com.dispart.dto.dataquery.Disp20210349OutDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.CardReturnTaskService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/securityCenter")
public class CardReturnTaskController {

    @Resource
    private CardReturnTaskService service;

    /**
     * 代冲值查询
     * @author  zhaoshihao
     * @date 2021/10/21
    */
    @PostMapping("DISP20210349")
    public Result<Disp20210349OutDto> cardReturnTaskQuery(@RequestBody Request<Disp20210349InDto> inDto){
        return service.CardReturnTaskQuery(inDto.getBody());
    }

}
