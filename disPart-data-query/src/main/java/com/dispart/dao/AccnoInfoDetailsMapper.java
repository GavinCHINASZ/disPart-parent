package com.dispart.dao;

import com.dispart.dto.dataquery.DISP20210354InDto;
import com.dispart.dto.dataquery.DISP20210354OutDto;
import com.dispart.model.AccnoInfoDetails;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface AccnoInfoDetailsMapper {

    ArrayList<DISP20210354OutDto> getAccOperation(DISP20210354InDto inDto);

    Integer getAccOperationCount(DISP20210354InDto inDto);

}