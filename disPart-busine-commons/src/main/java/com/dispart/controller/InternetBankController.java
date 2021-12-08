package com.dispart.controller;

import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.bank.DISP20210282ServiceImpl;
import com.dispart.service.bank.DISP20210283ServiceImpl;
import com.dispart.service.bank.DISP20210284ServiceImpl;
import com.dispart.vo.DISP20210282ReqVo;
import com.dispart.vo.DISP20210283ReqVo;
import com.dispart.vo.DISP20210284RepVo;
import com.dispart.vo.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InternetBankController {

    @Autowired
    DISP20210282ServiceImpl disp20210282Service;

    @Autowired
    DISP20210283ServiceImpl disp20210283Service;

    @Autowired
    DISP20210284ServiceImpl disp20210284Service;

    /**
     * 提现
     */
    @PostMapping("/securityCenter/DISP20210282")
    public Result<ResponseBody> ccbWithdraw(@RequestBody Request<DISP20210282ReqVo> reqVo) {
        return disp20210282Service.service(reqVo.getBody());
    }
    /**
     * 提现结果查询
     */
    @PostMapping("/securityCenter/DISP20210283")
    public Result<ResponseBody> ccbQueryWithdrawResult(@RequestBody Request<DISP20210283ReqVo> reqVo) {

        return disp20210283Service.service(reqVo.getBody());
    }
    /**
     * 账户信息查询
     */
    @PostMapping("/securityCenter/DISP20210284")
    public Result<ResponseBody> withdraw(@RequestBody Request<DISP20210284RepVo> reqVo) {
        return disp20210284Service.service(reqVo.getBody());
    }

}
