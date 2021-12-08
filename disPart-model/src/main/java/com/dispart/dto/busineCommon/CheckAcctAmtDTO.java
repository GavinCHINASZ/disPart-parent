package com.dispart.dto.busineCommon;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CheckAcctAmtDTO {

   // 卡号
   private String payCard;

   // 金额
   private BigDecimal txnAmt;
}
