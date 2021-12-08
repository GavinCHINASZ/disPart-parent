package com.dispart.dto.entrance;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 进场货物核验-修改货物信息
 */
@Data
public class UpdateEntranceVeCheckInDto {
    /**
     * 进出场ID YYMMDD + 8位序列号
     */
    private String inId;
    /**
     * 是否退费
     */
    private String isReturn;

    private BigDecimal cargoWght;
    //商户id
    private String provId;
    //商户名称
    private String provNm;
    //商户手机号
    private String phone;
    //卡号
    private String cardNo;
    //核验实际收费
    private BigDecimal actAmt;

    private List<EntranceVeCheckOutDatilsDto> list;


}
