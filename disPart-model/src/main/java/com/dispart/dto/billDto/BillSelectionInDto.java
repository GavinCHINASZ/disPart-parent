package com.dispart.dto.billDto;

import com.dispart.baseDto.BaseSelectionInDto;
import lombok.Data;

@Data
public class BillSelectionInDto extends BaseSelectionInDto {

    private String billNum;  //账单号

    private String billSt;  //账单状态

    private String paymentSt;  //支付状态

    private String telephone;  //客户电话

    private String provId;  //客户ID

    private String provNm;  //客户姓名

    private String payId;  //缴费项目ID

    private String depId;  //部门编号

    private String isPayed; //是否支付

    private String roleId;  //角色ID

    private String menuId;  //菜单ID

    private String operId;  //操作人ID

    private String operNm;  //操作人名称

    private String dataRole; //数据权限

    private String txnStDate;  //交易查询开始时间

    private String txnEndDate;  //交易查询结束时间

}
