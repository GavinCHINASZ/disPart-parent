package com.dispart.dao;


import com.dispart.dto.dataquery.Disp20210071InDto;
import com.dispart.dto.dataquery.Disp20210071OutMx;
import com.dispart.dto.dataquery.Disp20210072InDto;
import com.dispart.dto.dataquery.Disp20210072OutMx;
import com.dispart.entity.DatabaseCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CheckQueryMapper {

    public ArrayList<Disp20210071OutMx> quryCheckDetail(Disp20210071InDto inDto);

    public ArrayList<Disp20210072OutMx> quryCheckResult(Disp20210072InDto inDto);

    public DatabaseCount quryCheckDetail_count(Disp20210071InDto inDto);

    public DatabaseCount quryCheckResult_count(Disp20210072InDto inDto);

}
