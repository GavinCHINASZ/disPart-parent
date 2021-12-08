package com.dispart.service;

import com.dispart.dto.dataquery.Disp20210075InDto;
import com.dispart.dto.dataquery.Disp20210075OutDto;
import com.dispart.result.Result;

public interface ProductInventoryInfoQueryService {
    public Result<Disp20210075OutDto> quryProductInventory(Disp20210075InDto param);
}
