package com.dispart.service;

import com.dispart.dto.prdctPriceDto.DISP20210311InDto;
import com.dispart.dto.prdctPriceDto.DISP20210312InDto;
import com.dispart.dto.prdctPriceDto.DISP20210356InDto;
import com.dispart.result.Result;

public interface ProductPriceService {

    public Result productPriceQuery(DISP20210311InDto inDto);

    public Result insertProductPrice(DISP20210312InDto inDto);

    Result delProductPrice(DISP20210356InDto inDto);
}
