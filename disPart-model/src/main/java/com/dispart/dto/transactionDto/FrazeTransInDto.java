package com.dispart.dto.transactionDto;

import com.dispart.baseDto.BaseSelectionInDto;
import lombok.Data;

import java.util.ArrayList;

@Data
public class FrazeTransInDto extends BaseSelectionInDto {

    private String cardNo;  //卡号

    private String provNm;  //客户名称

    private String provId;  //客户ID

    private String operNm; //操作人姓名

    private String txnType;  //操作 1-冻结 10-解冻

    private String beginDate;  //操作开始时间

    private String endDate;  //操作结束时间

}
