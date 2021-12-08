package com.dispart.modle.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dispart.modle.vo.Disp20210347TransInfoOutVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Disp20210347OutDto {

    @JSONField(name = "Cst_Lgl_Nm")
    private String cstLglNm; //借款人名称

    @JSONField(name = "Crdt_No")
    private String crdtNo; //借款人证件号码

    @JSONField(name = "EntNm")
    private String entNm; //企业名称

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(name = "Sign_StDt")
    private String signStDt; //签约日期

    @JSONField(name = "TAmt")
    private String TAmt; //总金额

    @JSONField(name = "Oprt_Scop")
    private String oprtScop; //经营范围 蔬菜；水果；其他

    @JSONField(name = "Cst_Tp_Nm")
    private String cstTpNm; //客户类型名称，季节性、非季节性

    @JSONField(name = "CstRkGdCd")
    private String cstRkGdCd; //客户风险等级代码 1：高风险；2：中风险；3：低风险

    @JSONField(name = "Oprt_Sttn_Cd")
    private String oprtSttnCd; //经营情况代码 20:正常 16：停产或半停产

    @JSONField(name = "AR_ExDat")
    private String AR_ExDat;  //合约到期日期

    @JSONField(name = "Cst_Tp_Verf_Cd")
    private String cstTpVerfCd; //客户类型校验代码 04：是 05：否

    @JSONField(name = "Cur_Dt")
    private String curDt;  //当前日期

    @JSONField(name = "Txn_Inf")
    private List<Disp20210347TransInfoOutVo> txnInf; //交易信息

}

