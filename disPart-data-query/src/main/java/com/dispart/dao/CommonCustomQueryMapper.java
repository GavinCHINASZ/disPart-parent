package com.dispart.dao;

import com.dispart.dto.dataquery.Disp20210209InDto;
import com.dispart.dto.dataquery.Disp20210209OutDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CommonCustomQueryMapper {
    ArrayList<Disp20210209OutDto> queryCustomInfo(Disp20210209InDto inDto);
}
