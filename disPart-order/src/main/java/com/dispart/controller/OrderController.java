package com.dispart.controller;

import com.dispart.dto.orderdto.*;
import com.dispart.model.order.OrderVo;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.result.ResultToHSBOut;
import com.dispart.service.TOrderDetailInfoService;
import com.dispart.vo.order.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * @author wujie
 * @version 1.0.0:
 * @title UserController
 * @Description TODO
 * @dateTime 2021/6/3 15:24
 * @Copyright 2020-2021
 */
@RestController
@Api(tags = "订单管理")
@Slf4j
@RequestMapping("/securityCenter")
@CrossOrigin
public class OrderController {

    @Autowired
    private TOrderDetailInfoService tOrderDetailInfoService;

    @PostMapping("/DISP20210053")
    @ApiOperation(value = "分页条件查询买入与卖出订单")
    public Result<PageOrderDTO> findByCondiction(@RequestBody Request<SelectMainOrderAndOrderDetailVo> selectMainOrderAndOrderDetailVo){
        return tOrderDetailInfoService.findByCondiction(selectMainOrderAndOrderDetailVo.getBody());
    }

    @PostMapping("/DISP20210061")
    @ApiOperation(value = "订单支付")
    public Result<Object>payOrders(@RequestBody Request<PayOrderVo> payOrderVo){
        return tOrderDetailInfoService.payOrders(payOrderVo.getBody());
    }

    @PostMapping("/DISP20210062")
    @ApiOperation(value = "查询商户的产品")
    public Result<ProvPductInfo>selectPrdCTByProVId(@RequestBody Request<SelectMerchantVo> selectMerchantVo){
        return tOrderDetailInfoService.selectProductInventoryByProvId(selectMerchantVo.getBody());
    }

    @PostMapping("/DISP20210063")
    @ApiOperation(value = "确认商品")
    public Result<List<String>>comfireOrders(@RequestBody Request<AddOrdersByParam> addOrdersByParam){
        return tOrderDetailInfoService.comfirmOrders(addOrdersByParam.getBody());
    }

    @PostMapping("/DISP20210065")
    @ApiOperation(value = "支付结果回调接口")
    public ResultToHSBOut orderResultCallBack(@RequestBody ResultOrderCallBackVo resultOrderCallBackVo){
        return tOrderDetailInfoService.orderResultCallBack(resultOrderCallBackVo);
    }

    @PostMapping("/DISP20210064")
    @ApiOperation(value = "对账明细入库回调接口")
    public ResultToHSBOut verifyAccountCallBack(@RequestParam("file") MultipartFile file){
        return tOrderDetailInfoService.verifyAccountCallBack(file);
    }

    @PostMapping("/DISP20210115")
    @ApiOperation(value = "修改订单状态")
    public Result<Object> cancelOrderStatus(@RequestBody Request<OrderVo> orderId) {
        return tOrderDetailInfoService.cancelOrder(orderId.getBody());
    }

    @PostMapping("/DISP20210116")
    @ApiOperation(value = "查询是否有重复订单")
    public Result<Object> checkHasOrder(@RequestBody Request<CheckHasOrderVo> checkHasOrder) {
        return tOrderDetailInfoService.checkhasOrder(checkHasOrder.getBody());
    }
}
