package com.dispart.service;

import com.dispart.dto.busineCommon.FindAcctDTO;
import com.dispart.dto.orderdto.*;
import com.dispart.model.businessCommon.TPayJrn;
import com.dispart.model.order.OrderVo;
import com.dispart.model.order.ResultHSBCallBack;
import com.dispart.model.order.TOrderDetailInfo;
import com.dispart.model.order.ZOrderId;
import com.dispart.request.Request;
import com.dispart.result.ResultToHSBOut;
import com.dispart.tmp.AddOrdersByParams;
import com.dispart.vo.order.*;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dispart.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TOrderDetailInfoService extends IService<TOrderDetailInfo> {

    Result<PageOrderDTO> findByCondiction(SelectMainOrderAndOrderDetailVo selectMainOrderAndOrderDetailVo);

    Result<List<String>> comfirmOrders(AddOrdersByParam addOrdersByParam);

    Result<Object> payOrders(PayOrderVo payOrderVo);

    Result<ProvPductInfo> selectProductInventoryByProvId(SelectMerchantVo selectMerchantVo);

    ResultToHSBOut orderResultCallBack(ResultOrderCallBackVo resultOrderCallBackVo);

    ResultToHSBOut verifyAccountCallBack(MultipartFile file);

    Result<Object> cancelOrder(OrderVo orderId);

    Result<Object> checkhasOrder(CheckHasOrderVo checkHasOrderVo);

    Result<Object> payOrdersBySelect(Request<AddOrdersByParam> addOrdersByParam);

    Result<Object> payOrdersBySelect2(Request<AddOrdersByParam> addOrdersByParam);

    Result<Object> payOrdersBySelect3(Request<AddOrdersByParam> addOrdersByParam);

    Result<Object> payOrdersBySelect4(AddOrdersByParam addOrdersByParam);

    Result<Object> payOrdersByCash(AddOrdersByParam addOrdersByParam);

    Result<Object> payOrdersByCard(AddOrdersByParam addOrdersByParam);

    Result<Object> withDrawByOffline(AddOrdersByParam addOrdersByParam);

    ResultToHSBOut resultToHSBOfflinePay(AddOrdersByParam addOrdersByParam);

    Result<Object> resultToHSBOfflineWithDraw(AddOrdersByParam getOrderByParam);

    Result<Object> moneyWithDraw(AddOrdersByParam getOrderByParam);

    Result<Object> cashOut(Request<CashOutParam> getOrderByParam);

    Result<Object> getOrderStatus(ZOrderId zOrderId);

    Result<Object> withDrawToCard(Request<AddOrdersByParam> addOrdersByParam);

    Result<Object> updatePosOrderStatus(FindAcctDTO findAcctDTO);

    Result<Object> chongzheng(Request<AddOrdersByParam> params);

    Result<Object> preCharge(Request<AddOrdersByParams> params);

    Result<Object> applyChongzheng(Request<AddOrdersByParam> params);

    Result<Object> updateChongzheng(Request<AddOrdersByParam> params);
}

