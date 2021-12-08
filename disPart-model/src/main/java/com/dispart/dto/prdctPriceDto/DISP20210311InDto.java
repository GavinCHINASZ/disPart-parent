package com.dispart.dto.prdctPriceDto;

import com.dispart.baseDto.BaseSelectionInDto;
import lombok.Data;

@Data
public class DISP20210311InDto extends BaseSelectionInDto {

    private String prdctFullNm;

    private String prdctNm;

    private String beginDate;

    private String endDate;

    private String date;

}
