package com.dispart.dao.mapper;

import com.dispart.model.base.CarBlackList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CarBlackListMapper {
    Integer inserCarBlackList(@Param("carBlackList") CarBlackList carBlackList,@Param("operId") String operId);
    Integer deteCarBlackList(@Param("carBlackList") CarBlackList carBlackList);
    Integer updateCarBlackList(@Param("carBlackList") CarBlackList carBlackList,@Param("operId") String operId);
    List<CarBlackList> selectCarBlackList(@Param("carBlackList") CarBlackList carBlackList);
    Integer countCarBlackList(@Param("carBlackList") CarBlackList carBlackList);
}
