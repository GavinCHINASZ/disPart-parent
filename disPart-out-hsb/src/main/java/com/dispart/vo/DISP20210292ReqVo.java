package com.dispart.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class DISP20210292ReqVo implements Serializable {

    private static final long serialVersionUID = -6727374342597601710L;
    //发起方时间戳
    @JSONField(name = "Ittparty_Tms")
    private String sndDtTm;
    //发起方流水号
    @JSONField(name = "Ittparty_Jrnl_No")
    private String sndTraceId;
    //市场编号
    @JSONField(name = "Mkt_Id")
    private String marketId;
    //支付流水号
    @JSONField(name = "Py_Trn_No")
    private String paymentTraceId;
    //退款金额
    @JSONField(name = "Rfnd_Amt")
    private BigDecimal refundAmt;

    //子订单列表
    @JSONField(name = "Sub_Ordr_List")
    private ArrayList<SubOrdr> list;

    //客户方退款流水号
    @JSONField(name = "Cust_Rfnd_Trcno")
    private String customRefundTraceId;

    //版本号
    @JSONField(name = "Vno")
    private String version;

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

    public String getPaymentTraceId() {
        return paymentTraceId;
    }

    public void setPaymentTraceId(String paymentTraceId) {
        this.paymentTraceId = paymentTraceId;
    }

    public BigDecimal getRefundAmt() {
        return refundAmt;
    }

    public void setRefundAmt(BigDecimal refundAmt) {
        this.refundAmt = refundAmt;
    }

    public ArrayList<SubOrdr> getList() {
        return list;
    }

    public void setList(ArrayList<SubOrdr> list) {
        this.list = list;
    }

    public String getCustomRefundTraceId() {
        return customRefundTraceId;
    }

    public void setCustomRefundTraceId(String customRefundTraceId) {
        this.customRefundTraceId = customRefundTraceId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static class SubOrdr implements Serializable {
        private static final long serialVersionUID = -7517414569047424986L;

        //子订单编号
        @JSONField(name = "Sub_Ordr_Id")
        private String subOrderNo;

        //退款金额
        @JSONField(name = "Rfnd_Amt")
        private BigDecimal refundAmt;

        //附加项列表
        @JSONField(name = "Prj_List")
        private ArrayList<PrjList> prjList;

        //退款分账方列表
        @JSONField(name = "Parlist")
        private ArrayList<ParList> parList;

        public String getSubOrderNo() {
            return subOrderNo;
        }

        public void setSubOrderNo(String subOrderNo) {
            this.subOrderNo = subOrderNo;
        }

        public BigDecimal getRefundAmt() {
            return refundAmt;
        }

        public void setRefundAmt(BigDecimal refundAmt) {
            this.refundAmt = refundAmt;
        }

        public ArrayList<PrjList> getPrjList() {
            return prjList;
        }

        public void setPrjList(ArrayList<PrjList> prjList) {
            this.prjList = prjList;
        }

        public ArrayList<ParList> getParList() {
            return parList;
        }

        public void setParList(ArrayList<ParList> parList) {
            this.parList = parList;
        }
    }

    public static class PrjList implements Serializable {
        private static final long serialVersionUID = -5405918577941358115L;
        //项目编号
        @JSONField(name = "Prj_Id")
        private String itemNo;

        //退款金额
        @JSONField(name = "Rfnd_Amt")
        private BigDecimal refundAmt;

        public String getItemNo() {
            return itemNo;
        }

        public void setItemNo(String itemNo) {
            this.itemNo = itemNo;
        }

        public BigDecimal getRefundAmt() {
            return refundAmt;
        }

        public void setRefundAmt(BigDecimal refundAmt) {
            this.refundAmt = refundAmt;
        }
    }

    public static class ParList implements Serializable {

        private static final long serialVersionUID = 4819321713092560399L;

        //商家编号
        @JSONField(name = "Mkt_Mrch_Id")
        private String provId;

        //金额
        @JSONField(name = "Rfnd_Amt")
        private BigDecimal amt;

        public String getProvId() {
            return provId;
        }

        public void setProvId(String provId) {
            this.provId = provId;
        }

        public BigDecimal getAmt() {
            return amt;
        }

        public void setAmt(BigDecimal amt) {
            this.amt = amt;
        }
    }
}
