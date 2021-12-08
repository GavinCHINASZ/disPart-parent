package com.dispart.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 惠市宝支付下单 请求Vo
 * DISP20210096ReqVo
 */
public class DISP20210061ReqVo implements Serializable {

    private static final long serialVersionUID = 5770640970572828740L;
    // 发起方时间戳
    private String sndDtTm;
    // 发起方流水号
    private String sndTraceId;
    // 市场编号
    private String marketId;
    // 主订单编号
    private String mainOrderId;
    // 支付方式代码
    private String paymentMode;
    // 码信息
    private String qrCode;
    // 建行钱包合约号
    private String ccbWalletNo;
    // 第三方APP平台号
    private String appId;
    // 钱包名称
    private String walletNm;
    // 订单类型
    private String orderType;
    // 支付结果通知序号
    private String paymentRslt;
    // 银行编码
    private String bankCd;
    // 操作员
    private String operId;
    // 用户ID
    private String userId;
    // 币种
    private String ccyCd;
    // 页面返回URL地址
    private String returnURL;
    // 订单总金额
    private BigDecimal orderTotAmt;
    // 交易总金额
    private BigDecimal txnTotAmt;
    // 用户子标识
    private String userIdent;
    // 分期期数
    private Integer periodsNum;
    // 手续费承担方编号
    private String servChrgBearNo;
    // 确认收货日期
    private String confirmDt;
    // 支付描述
    private String paymentDesc;
    // 订单超时时间
    private String orderTimeout;
    // 版本号
    private String version;
    //子订单
    private ArrayList<Order> list;

    // 子订单列表
    public static class Order implements Serializable {
        private static final long serialVersionUID = 7283533847440753710L;
        // 商家编号
        private String provId;
        // 商家自定义编号
        private String provCustId;
        // 客户方子订单编号
        private String subOrderId;
        // 订单金额
        private BigDecimal orderAmt;
        // 交易金额
        private BigDecimal txnAmt;
        // 附加项金额
        private BigDecimal additAmt;
        // 商品描述
        private String prdctDsc;
        // 商品类型
        private String prdctType;
        // 分账规则编号
        private String partRuleNo;
        //附加项列表
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
        private static final long serialVersionUID = 4568756643088813178L;
        //项目编号
        private String itemNo;
        //项目名称
        private String itemNm;
        //项目类型
        private String itemTp;
        //金额
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

    public ArrayList<Order> getList() {
        return list;
    }

    public void setList(ArrayList<Order> list) {
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
}
