package com.dispart.dto.dataquery;

import com.dispart.baseDto.BaseSelectionInDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Disp20210334InDto extends BaseSelectionInDto {

    private String txnDt;

    private String beginDate;

    private String endDate;

}
