package com.dispart.dao;

import com.dispart.dto.dataquery.Disp20210349InDto;
import com.dispart.dto.dataquery.Disp20210349OutDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CardReturnTaskMapper {

    ArrayList<Disp20210349OutDto> selectCardReturnTask(Disp20210349InDto inDto);

    Integer selectCardReturnTaskCount(Disp20210349InDto inDto);

}