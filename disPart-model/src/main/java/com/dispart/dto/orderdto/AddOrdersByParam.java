package com.dispart.dto.orderdto;


import com.dispart.dto.busineCommon.InsertPayJrnDTO;
import com.dispart.model.order.TOrderDetailInfo;
import com.dispart.model.order.TOrderGoodsInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AddOrdersByParam extends InsertPayJrnDTO {
    private List<TOrderDetailInfo> orderList;

    private List<TOrderGoodsInfo> goodList;

    private Integer id;

    private String depId;

    private String subOrg;

    private String chanlNo;

    private String newTxnType;

    // 订单支付方式 05微信小程序 06对私网银 07聚合二维码
    private String paymentMode;

    // 订单类型
//      02 消费券购买
//      03 在途订单
//      04 普通订单"
    private String orderType;

    // 币种 (156为人民币) (支付方式为06时需要)
    private String bankCd;

    // 当前调起支付的小程序APPID (支付方式为05时需要)
    private String appId;

    // 用户在小程序appid下的唯一标识 （支付方式为05时需要）
    private String userIdent;

    private String refNum;

    private String refundTraceId;

    private String refundSt;

    private String paymentTraceId;

    // 实际交易金额
    private BigDecimal txnAmt;

    // 订单金额
    private BigDecimal amt;

    // 交易模式
    private String mode;

    private String refundMainOrderId;

    private BigDecimal additAmt;

    private String purchId;

    private String provId;

    private String provNm;

    private String jrmlNum;

}
