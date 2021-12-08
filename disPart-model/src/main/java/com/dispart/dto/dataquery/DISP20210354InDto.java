package com.dispart.dto.dataquery;

import com.dispart.baseDto.BaseSelectionInDto;
import lombok.Data;

@Data
public class DISP20210354InDto extends BaseSelectionInDto {

    private String provId;

    private String provNm;

    private String cardNo;

    private String txnType;

    private String txnStTime;

    private String txnEndTime;

}
