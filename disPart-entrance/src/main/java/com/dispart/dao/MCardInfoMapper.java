package com.dispart.dao;

import com.dispart.dto.MCardInfoDto.AddMCardInfoInDto;
import com.dispart.dto.MCardInfoDto.MCardInfoInsertDto;
import com.dispart.dto.MCardInfoDto.MCardInfoSelectionInDto;
import com.dispart.model.MCardInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface MCardInfoMapper {

    int insertSelective(MCardInfoInsertDto record);

    int updateByPrimaryKeySelective(MCardInfoInsertDto record);

    int deleteMcardInfo(@Param("mcardNum") String mcardNum);

    Integer selectMcardInfoCount(MCardInfoSelectionInDto inDto);

    ArrayList<MCardInfo> selectMcardInfo(MCardInfoSelectionInDto inDto);

    int getMcardNum();

    String getInOutNm(@Param("deviceNo") String deviceNo);

}