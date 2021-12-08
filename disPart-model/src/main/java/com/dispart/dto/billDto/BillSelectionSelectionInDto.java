package com.dispart.dto.billDto;

import com.dispart.baseDto.BaseSelectionInDto;
import lombok.Data;

@Data
public class BillSelectionSelectionInDto extends BaseSelectionInDto {

    private String billSt;  //账单状态

    private String paymentSt;  //支付状态

    private String telephone;  //客户电话

    private String provId;  //客户ID

    private String provNm;  //客户姓名

    private String payId;  //缴费项目ID

    private String depId;  //部门编号

}
