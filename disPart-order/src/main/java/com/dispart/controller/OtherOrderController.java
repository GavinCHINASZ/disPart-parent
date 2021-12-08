package com.dispart.controller;

import com.dispart.dto.busineCommon.FindAcctDTO;
import com.dispart.dto.orderdto.AddOrdersByParam;
import com.dispart.dto.orderdto.CashOutParam;
import com.dispart.model.order.ZOrderId;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.result.ResultToHSBOut;
import com.dispart.service.TOrderDetailInfoService;
import com.dispart.tmp.AddOrdersByParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author wujie
 * @version 1.0.0:
 */
@RestController
@Api(tags = "新订单管理")
@Slf4j
@RequestMapping("/securityCenter")
@CrossOrigin
public class OtherOrderController {

    @Autowired
    private TOrderDetailInfoService tOrderDetailInfoService;

    @PostMapping("/DISP20210260")
    @ApiOperation(value = "充值")
    public Result<Object> charge(@RequestBody Request<AddOrdersByParam> addOrdersByParamRequest){
        return tOrderDetailInfoService.payOrdersBySelect(addOrdersByParamRequest);
    }

    @PostMapping("/DISP20210261")
    @ApiOperation(value = "缴费")
    public Result<Object> pay(@RequestBody Request<AddOrdersByParam> addOrdersByParamRequest){
        return tOrderDetailInfoService.payOrdersBySelect2(addOrdersByParamRequest);
    }

    @PostMapping("/DISP20210263")
    @ApiOperation(value = "退款")
    public Result<Object> withdraw(@RequestBody Request<AddOrdersByParam> addOrdersByParamRequest){
        return tOrderDetailInfoService.payOrdersBySelect3(addOrdersByParamRequest);
    }

    @PostMapping("/DISP20210264")
    @ApiOperation(value = "线上退款结果通知")
    public ResultToHSBOut resultToHSBOnlineWithdraw(@RequestBody AddOrdersByParam addOrdersByParam){
        return tOrderDetailInfoService.resultToHSBOfflinePay(addOrdersByParam);
    }

    @PostMapping("/DISP20210265")
    @ApiOperation(value = "提现")
    public Result<Object> cashOut(@RequestBody Request<CashOutParam> addOrdersByParam){
        return tOrderDetailInfoService.cashOut(addOrdersByParam);
    }

    @PostMapping("/DISP20210262")
    @ApiOperation(value = "电子秤查询订单结果")
    public Result<Object> getZOrderStatus(@RequestBody Request<ZOrderId> params) {
        return tOrderDetailInfoService.getOrderStatus(params.getBody());
    }

    @PostMapping("/DISP20210266")
    @ApiOperation(value = "退款到一卡通")
    public Result<Object> withDrawToCard (@RequestBody Request<AddOrdersByParam> params) {
        return tOrderDetailInfoService.withDrawToCard(params);
    }

    @PostMapping("/DISP20210267")
    @ApiOperation(value = "更新posId状态接口")
    public Result<Object> updatePosOrderStatus (@RequestBody Request<FindAcctDTO> params) {
        return tOrderDetailInfoService.updatePosOrderStatus(params.getBody());
    }

    @PostMapping("/DISP20210268")
    @ApiOperation(value = "冲正接口")
    public Result<Object> chongzheng (@RequestBody Request<AddOrdersByParam> params) {
        return tOrderDetailInfoService.chongzheng(params);
    }

    @PostMapping("/DISP20210269")
    @ApiOperation(value = "代充值")
    public Result<Object> preCharge (@RequestBody Request<AddOrdersByParams> params) {
        return tOrderDetailInfoService.preCharge(params);
    }

    @PostMapping("/DISP20210270")
    @ApiOperation(value = "冲正申请")
    public Result<Object> applyChongzheng(@RequestBody Request<AddOrdersByParam> params){
        return tOrderDetailInfoService.applyChongzheng(params);
    }

    @PostMapping("/DISP20210271")
    @ApiOperation(value = "修改冲正")
    public Result<Object> updateChongzheng(@RequestBody Request<AddOrdersByParam> params){
        return tOrderDetailInfoService.updateChongzheng(params);
    }
}
