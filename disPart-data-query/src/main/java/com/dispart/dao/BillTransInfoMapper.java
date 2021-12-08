package com.dispart.dao;

import com.dispart.dto.dataquery.DISP20210355InDto;
import com.dispart.dto.dataquery.DISP20210355OutDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface BillTransInfoMapper {

    ArrayList<DISP20210355OutDto> getBillTransInfo(DISP20210355InDto inDto);

    Integer getBillTransInfoCount(DISP20210355InDto inDto);

}
