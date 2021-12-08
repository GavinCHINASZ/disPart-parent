package com.dispart.dto.transactionDto;

import com.dispart.baseDto.BaseSelectionOutDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionSelectOutDto extends BaseSelectionOutDto {

    private String jrnlNum;

    private String businessNo;

    private String txnType;

    private String transMd;

    private Date txnTm;

    private BigDecimal txnAmt;

    private String payerNo;

    private String payName;

    private String payCard;

    private String payAcct;

    private String payeeNum;

    private String payee;

    private String payeeAcct;

    private String summary;

    private String status;

    private String remark;

    private String operId;

    private Date creatTime;

    private Date upTime;
}
