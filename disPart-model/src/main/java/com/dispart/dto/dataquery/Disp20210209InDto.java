package com.dispart.dto.dataquery;

import com.dispart.baseDto.BaseSelectionInDto;
import lombok.Data;

@Data
public class Disp20210209InDto extends BaseSelectionInDto {

    private String customKey;

    private String queryType; //1-带帐户号基本信息 2-带帐户和卡号的基本信息

}
