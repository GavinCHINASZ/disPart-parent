package com.dispart.dao;


import com.dispart.dto.dataquery.Disp20210073InDto;
import com.dispart.dto.dataquery.Disp20210073OutMx;
import com.dispart.dto.dataquery.Disp20210074InDto;
import com.dispart.dto.dataquery.Disp20210074OutMx;
import com.dispart.entity.DatabaseCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CustomQueryMapper {

    public ArrayList<Disp20210073OutMx> quryCustomRegistInfo(Disp20210073InDto inDto);

    public ArrayList<Disp20210074OutMx> quryCustomInfo(Disp20210074InDto inDto);

    public DatabaseCount quryCustomRegistInfo_count(Disp20210073InDto inDto);

    public DatabaseCount quryCustomInfo_count(Disp20210074InDto inDto);

}
