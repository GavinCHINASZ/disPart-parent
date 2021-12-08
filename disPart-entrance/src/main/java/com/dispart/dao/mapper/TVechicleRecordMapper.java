package com.dispart.dao.mapper;

import com.dispart.vo.entrance.TVechicleRecord;
import org.apache.ibatis.annotations.Mapper;
//车辆档案表
@Mapper
public interface TVechicleRecordMapper {
    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(TVechicleRecord record);

    /**
     * select by 车牌号
     * @param vehicelNum primary key
     * @return object by primary key
     */
    TVechicleRecord selectByPrimaryKey(String vehicelNum);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(TVechicleRecord record);
}