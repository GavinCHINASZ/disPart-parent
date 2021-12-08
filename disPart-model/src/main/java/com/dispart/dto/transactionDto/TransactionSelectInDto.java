package com.dispart.dto.transactionDto;

import com.dispart.baseDto.BaseSelectionInDto;
import com.dispart.model.businessCommon.TransMdEnum;
import lombok.Data;

import java.util.Date;

@Data
public class TransactionSelectInDto extends BaseSelectionInDto {

    private String txnType;  //交易类型 0-充值 1-提现 2-进场费 3-停车费 4-退款

    private String payCard;  //付款人卡号

    private String payeeCard;  //充值卡号

    private String payerNo;  //付款人编号

    private String payName;  //付款人名称

    private String operId;  //操作员

    private String operNm;  //操作员名称

    private String jrnlNum;  //流水号/自生成

    private String businessNum;  //业务号

    private String remark;  //备注

    private String transMd;  //支付方式

    private String status;  //状态 0-待支付 1-支付成功  2-支付失败

    private String beginDate;  //查询交易日期 开始时间

    private String endDate;  //查询交易日期 截止时间

    private String vehicleNum;  //车牌号
}
