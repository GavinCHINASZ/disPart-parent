package com.dispart.dao;

import com.dispart.dto.MCardInfoDto.*;
import com.dispart.model.VechicleMonthPayDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface VechicleMonthPayDetailsMapper {

    int insertSelective(McardPayDetailInsertDto record);

    int updateByPrimaryKeySelective(McardPayDetailUpdateInDto record);

    Integer getMonthPayOrder();

    Integer getMonthPayDetailsCount(MCardInfoSelectionInDto inDto);

    ArrayList<DISP20210307OutDto> getMonthPayDetails(MCardInfoSelectionInDto inDto);

    VechicleMonthPayDetails getNewMonthDetail(@Param("mcardNum") String mcardNum);

    Integer updateMCardPayDetailStatus(DISP20210331InDto inDto);

}