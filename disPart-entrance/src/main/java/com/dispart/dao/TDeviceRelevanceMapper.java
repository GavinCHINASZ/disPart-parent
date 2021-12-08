package com.dispart.dao;

import com.dispart.dto.entrance.D_0232FindDto;
import com.dispart.model.TDeviceRelevance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface TDeviceRelevanceMapper {

    ArrayList<TDeviceRelevance> selectIODevice(@Param("ioType") String ioType);

    TDeviceRelevance selectIOByInOutId(D_0232FindDto record);

}