package com.dispart.vo.order;

import com.dispart.model.order.TOrderDetailInfo;
import lombok.Data;

//import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PayOrderVo {

    // 子订单订单号 其实只需要订单号
//    "orderList":[
//    {
//        "orderId":"Z202106170000059",
//            "purchId":"123",
//            "provId":"0002",
//            "prdctType":"333",
//            "prdctAmt":13,
//            "prdctId":"113",
//            "prdctNum":1
//    },
//    {
//        "orderId":"Z202106170000060",
//            "purchId":"123",
//            "provId":"0002",
//            "prdctType":"333",
//            "prdctAmt":16,
//            "prdctId":"114",
//            "prdctNum":1
//    }
//    ]
    private List<TOrderDetailInfo> orderList;

//    所有子订单总金额
    private BigDecimal amt;

    // 所有子订单交易金额
    private BigDecimal txnAmt;

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

    // 信用卡分期数 (支付方式为06时需要)
    private int periodsNum;

    private String purchId;

    // 优惠总金额
    private BigDecimal additAmt;

    // 优惠券ID
    private String antId;
}
