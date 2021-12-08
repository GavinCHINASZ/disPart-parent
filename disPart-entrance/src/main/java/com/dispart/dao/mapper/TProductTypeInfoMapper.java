package com.dispart.dao.mapper;

import com.dispart.dto.entrance.D_0228FindInDto;
import com.dispart.dto.entrance.D_0228FindOutDto;
import com.dispart.dto.entrance.D_0228FindOutYDto;
import com.dispart.vo.entrance.TProductTypeInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TProductTypeInfoMapper {

    //查询品种名称集合
    List<D_0228FindOutYDto> findListByNm(D_0228FindInDto recode);
    //查询品种具体信息
    D_0228FindOutDto findByNm(D_0228FindInDto recode);

}