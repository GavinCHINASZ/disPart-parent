package com.dispart.dto.MCardInfoDto;

import com.dispart.baseDto.BaseSelectionInDto;
import lombok.Data;

@Data
public class MCardInfoSelectionInDto extends BaseSelectionInDto {

    private String provId;  //用户ID

    private String mcardNum;  //月卡单号

    private String vehicleNum;  //车牌号

    private String paymentMode;  //支付方式

    private String paymentSt;  //支付状态

    private String payStatus;  //特殊支付状态搜索条件

    private String cardNo;  //卡号

    private String provNm; //客户姓名

    private String payOrder; //月卡账单号

    private String mcardTp;  //月卡类型（0-普通月卡 1-超级月卡 2-免费月卡）

    private String beginDate;  //开卡时间查询 开始日期

    private String endDate;  //开卡时间查询 截止日期

    private String operNm;  //操作员名称

}
