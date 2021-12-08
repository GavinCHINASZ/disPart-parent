package com.dispart.dto.dataquery;

import com.dispart.baseDto.BaseSelectionInDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Disp20210336InDto extends BaseSelectionInDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dayDt;

}
