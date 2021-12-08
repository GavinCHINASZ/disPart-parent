package com.dispart.dao;

import com.dispart.dto.dataquery.Disp20210338InDto;import com.dispart.dto.dataquery.Disp20210338OutDto;import com.dispart.model.PayJrn;
import org.apache.ibatis.annotations.Mapper;import java.util.ArrayList;

@Mapper
public interface PayJrnMapper {

    Integer getPayJrnCount(Disp20210338InDto inDto);

    ArrayList<Disp20210338OutDto> getPayJrn(Disp20210338InDto inDto);

}