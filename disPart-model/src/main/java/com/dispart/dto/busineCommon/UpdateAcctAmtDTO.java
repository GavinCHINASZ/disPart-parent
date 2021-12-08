package com.dispart.dto.busineCommon;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateAcctAmtDTO {

   // 卡号
   private String payCard;

   // 金额
   private BigDecimal txnAmt;
}
