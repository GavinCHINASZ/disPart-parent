package com.dispart.dto.transactionDto;

import com.dispart.baseDto.BaseSelectionInDto;
import lombok.Data;

import java.util.ArrayList;

@Data
public class AdjustTransInDto extends BaseSelectionInDto {
    private String cardNo;  //卡号

    private String provId;  //客户ID

    private String provNm;  //客户姓名

    private String operNm; //操作人姓名

    private String operTp;  //金额标识 0-加余额 1-减余额

    private String beginDate;  //操作开始时间

    private String endDate;  //操作结束时间

}
