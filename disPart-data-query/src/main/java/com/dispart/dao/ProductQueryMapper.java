package com.dispart.dao;

import com.dispart.dto.dataquery.Disp20210075InDto;
import com.dispart.dto.dataquery.Disp20210075OutMx;
import com.dispart.entity.DatabaseCount;
import com.dispart.model.ProductTypeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ProductQueryMapper {

    public ArrayList<Disp20210075OutMx> quryProductInventory(Disp20210075InDto inDto);

    public DatabaseCount quryProductInventory_count(Disp20210075InDto inDto);

}
