package com.dispart.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 惠市宝支付下单 请求Vo
 */
public class DISP20210096ReqVo implements Serializable {
    private static final long serialVersionUID = 3719998423873797737L;

    // 发起方时间戳
    @JSONField(name = "Ittparty_Tms")
    private String sndDtTm;
    // 发起方流水号
    @JSONField(name = "Ittparty_Jrnl_No")
    private String sndTraceId;
    // 市场编号
    @JSONField(name = "Mkt_Id")
    private String marketId;
    // 主订单编号
    @JSONField(name = "Main_Ordr_No")
    private String mainOrderId;
    // 支付方式代码
    @JSONField(name = "Pymd_Cd")
    private String paymentMode;
    // 码信息
    @JSONField(name = "QRCODE")
    private String qrCode;
    // 建行钱包合约号
    @JSONField(name = "SVCID")
    private String ccbWalletNo;
    // 第三方APP平台号
    @JSONField(name = "Sub_Appid")
    private String appId;
    // 钱包名称
    @JSONField(name = "WALLETNAME")
    private String walletNm;
    // 订单类型
    @JSONField(name = "Py_Ordr_Tpcd")
    private String orderType;
    // 支付结果通知序号
    @JSONField(name = "Py_Rslt_Ntc_Sn")
    private String paymentRslt;
    // 银行编码
    @JSONField(name = "Bnk_Cd")
    private String bankCd;
    // 操作员
    @JSONField(name = "Opr_No")
    private String operId;
    // 用户ID
    @JSONField(name = "Usr_Id")
    private String userId;
    // 币种
    @JSONField(name = "Ccy")
    private String ccyCd;
    // 页面返回URL地址
    @JSONField(name = "Pgfc_Ret_Url_Adr")
    private String returnURL;
    // 订单总金额
    @JSONField(name = "Ordr_Tamt")
    private BigDecimal orderTotAmt;
    // 交易总金额
    @JSONField(name = "Txn_Tamt")
    private BigDecimal txnTotAmt;
    // 用户子标识
    @JSONField(name = "Sub_Openid")
    private String userIdent;
    // 分期期数
    @JSONField(name = "Install_Num")
    private Integer periodsNum;
    // 手续费承担方编号
    @JSONField(name = "Hdcg_Brs_Id")
    private String servChrgBearNo;
    // 确认收货日期
    @JSONField(name = "Clrg_Dt")
    private String confirmDt;
    // 支付描述
    @JSONField(name = "Pay_Dsc")
    private String paymentDesc;
    // 订单超时时间
    @JSONField(name = "Order_Time_Out")
    private String orderTimeout;
    // 版本号
    @JSONField(name = "Vno")
    private String version;
    // 子订单列表
    @JSONField(name = "Orderlist")
    private List<OrderList> list;

    public static class OrderList implements Serializable {
        private static final long serialVersionUID = 5690958355203115106L;
        // 商家编号
        @JSONField(name = "Mkt_Mrch_Id")
        private String provId;
        // 商家自定义编号
        @JSONField(name = "Udf_Id")
        private String provCustId;
        // 客户方子订单编号
        @JSONField(name = "Cmdty_Ordr_No")
        private String subOrderId;
        // 订单金额
        @JSONField(name = "Ordr_Amt")
        private BigDecimal orderAmt;
        // 交易金额
        @JSONField(name = "Txnamt")
        private BigDecimal txnAmt;
        // 附加项金额
        @JSONField(name = "Apd_Tamt")
        private BigDecimal additAmt;
        // 商品描述
        @JSONField(name = "Cmdty_Dsc")
        private String prdctDsc;
        // 商品类型
        @JSONField(name = "Cmdty_Tp")
        private String prdctType;
        // 分账规则编号
        @JSONField(name = "Clrg_Rule_Id")
        private String partRuleNo;
        //附加项列表
        @JSONField(name = "Prjlist")
        private List<PrjList> prjList;

        public List<PrjList> getPrjList() {
            return prjList;
        }

        public void setPrjList(List<PrjList> prjList) {
            this.prjList = prjList;
        }

        public String getProvId() {
            return provId;
        }

        public void setProvId(String provId) {
            this.provId = provId;
        }

        public String getProvCustId() {
            return provCustId;
        }

        public void setProvCustId(String provCustId) {
            this.provCustId = provCustId;
        }

        public String getSubOrderId() {
            return subOrderId;
        }

        public void setSubOrderId(String subOrderId) {
            this.subOrderId = subOrderId;
        }

        public BigDecimal getOrderAmt() {
            return orderAmt;
        }

        public void setOrderAmt(BigDecimal orderAmt) {
            this.orderAmt = orderAmt;
        }

        public BigDecimal getTxnAmt() {
            return txnAmt;
        }

        public void setTxnAmt(BigDecimal txnAmt) {
            this.txnAmt = txnAmt;
        }

        public BigDecimal getAdditAmt() {
            return additAmt;
        }

        public void setAdditAmt(BigDecimal additAmt) {
            this.additAmt = additAmt;
        }

        public String getPrdctDsc() {
            return prdctDsc;
        }

        public void setPrdctDsc(String prdctDsc) {
            this.prdctDsc = prdctDsc;
        }

        public String getPrdctType() {
            return prdctType;
        }

        public void setPrdctType(String prdctType) {
            this.prdctType = prdctType;
        }

        public String getPartRuleNo() {
            return partRuleNo;
        }

        public void setPartRuleNo(String partRuleNo) {
            this.partRuleNo = partRuleNo;
        }
    }

    public static class PrjList implements Serializable {
        private static final long serialVersionUID = -7754604078709457829L;
        //项目编号
        @JSONField(name = "Prj_Id")
        private String itemNo;
        //项目名称
        @JSONField(name = "Prj_Nm")
        private String itemNm;
        //项目类型
        @JSONField(name = "Pjcy_Tp")
        private String itemTp;
        //金额
        @JSONField(name = "Amt")
        private BigDecimal amt;

        public String getItemNo() {
            return itemNo;
        }

        public void setItemNo(String itemNo) {
            this.itemNo = itemNo;
        }

        public String getItemNm() {
            return itemNm;
        }

        public void setItemNm(String itemNm) {
            this.itemNm = itemNm;
        }

        public String getItemTp() {
            return itemTp;
        }

        public void setItemTp(String itemTp) {
            this.itemTp = itemTp;
        }

        public BigDecimal getAmt() {
            return amt;
        }

        public void setAmt(BigDecimal amt) {
            this.amt = amt;
        }
    }

    public List<OrderList> getList() {
        return list;
    }

    public void setList(ArrayList<OrderList> list) {
        this.list = list;
    }

    public String getSndDtTm() {
        return sndDtTm;
    }

    public void setSndDtTm(String sndDtTm) {
        this.sndDtTm = sndDtTm;
    }

    public String getSndTraceId() {
        return sndTraceId;
    }

    public void setSndTraceId(String sndTraceId) {
        this.sndTraceId = sndTraceId;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getMainOrderId() {
        return mainOrderId;
    }

    public void setMainOrderId(String mainOrderId) {
        this.mainOrderId = mainOrderId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getCcbWalletNo() {
        return ccbWalletNo;
    }

    public void setCcbWalletNo(String ccbWalletNo) {
        this.ccbWalletNo = ccbWalletNo;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getWalletNm() {
        return walletNm;
    }

    public void setWalletNm(String walletNm) {
        this.walletNm = walletNm;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPaymentRslt() {
        return paymentRslt;
    }

    public void setPaymentRslt(String paymentRslt) {
        this.paymentRslt = paymentRslt;
    }

    public String getBankCd() {
        return bankCd;
    }

    public void setBankCd(String bankCd) {
        this.bankCd = bankCd;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCcyCd() {
        return ccyCd;
    }

    public void setCcyCd(String ccyCd) {
        this.ccyCd = ccyCd;
    }

    public String getReturnURL() {
        return returnURL;
    }

    public void setReturnURL(String returnURL) {
        this.returnURL = returnURL;
    }

    public BigDecimal getOrderTotAmt() {
        return orderTotAmt;
    }

    public void setOrderTotAmt(BigDecimal orderTotAmt) {
        this.orderTotAmt = orderTotAmt;
    }

    public BigDecimal getTxnTotAmt() {
        return txnTotAmt;
    }

    public void setTxnTotAmt(BigDecimal txnTotAmt) {
        this.txnTotAmt = txnTotAmt;
    }

    public String getUserIdent() {
        return userIdent;
    }

    public void setUserIdent(String userIdent) {
        this.userIdent = userIdent;
    }

    public Integer getPeriodsNum() {
        return periodsNum;
    }

    public void setPeriodsNum(Integer periodsNum) {
        this.periodsNum = periodsNum;
    }

    public String getServChrgBearNo() {
        return servChrgBearNo;
    }

    public void setServChrgBearNo(String servChrgBearNo) {
        this.servChrgBearNo = servChrgBearNo;
    }

    public String getConfirmDt() {
        return confirmDt;
    }

    public void setConfirmDt(String confirmDt) {
        this.confirmDt = confirmDt;
    }

    public String getPaymentDesc() {
        return paymentDesc;
    }

    public void setPaymentDesc(String paymentDesc) {
        this.paymentDesc = paymentDesc;
    }

    public String getOrderTimeout() {
        return orderTimeout;
    }

    public void setOrderTimeout(String orderTimeout) {
        this.orderTimeout = orderTimeout;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setList(List<OrderList> list) {
        this.list = list;
    }
}
