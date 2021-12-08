package com.dispart.dao.mapper;

import com.dispart.dto.prdctPriceDto.DISP20210311InDto;
import com.dispart.dto.prdctPriceDto.DISP20210312InDto;
import com.dispart.dto.prdctPriceDto.DISP20210356InDto;
import com.dispart.model.TProductPriceInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface TProductPriceInfoMapper {

    int insertSelective(DISP20210312InDto inDto);

    Integer productPriceQueryCount(DISP20210311InDto inDto);

   ArrayList<TProductPriceInfo> productPriceQuery(DISP20210311InDto inDto);

   int updateProductPrice(DISP20210312InDto inDto);

   int deleteProductPrice(DISP20210356InDto inDto);

}