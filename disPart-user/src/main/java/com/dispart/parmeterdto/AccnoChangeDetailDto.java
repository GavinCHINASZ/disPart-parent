package com.dispart.parmeterdto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 账户明细DTO
 */
@Data
public class AccnoChangeDetailDto {
    //流水号
    private String jrnlNum;
    //一卡通卡号
    private String cardNo;
    //客户编号
    private String provId;
    //客户名称
    private String provNm;
    //交易类型
    private String txnType;
    //交易方式
    private String transMd;
    //进账类型
    private String incomeTp;
    //账户余额
    private BigDecimal acctBal;
    //交易前余额
    private BigDecimal beforeAmt;
    //交易后余额
    private BigDecimal afterAmt;
    //交易金额
    private BigDecimal txnAmt;
    //交易时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date txnTm;
    //摘要
    private String summary;
    //备注
    private String remark;
    //操作人
    private String operId;
    private String operNm;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date upTime;

    //开始时间
    private Date startTime;
    //结束时间
    private Date endTime;
    //翻页参数
    private int pageSize;
    private int pageNum;

}
