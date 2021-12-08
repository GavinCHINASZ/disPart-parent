package com.dispart.dao.mapper;

import com.dispart.vo.entrance.TVehicleManager;
import org.apache.ibatis.annotations.Mapper;
//车辆类型管理
@Mapper
public interface TVehicleManagerMapper {
    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(TVehicleManager record);

    /**
     * select by primary key
     * @param num primary key
     * @return object by primary key
     */
    TVehicleManager selectByPrimaryKey(Integer num);

    /**
     * select by vehicleId
     * @param vehicleId
     * @return
     */
    TVehicleManager selectByVehicleId(String vehicleId);

    /**
     * select by tVehicleManager
     * @param tVehicleManager
     * @return
     */
    TVehicleManager selectByVeIdVeTpId(TVehicleManager tVehicleManager);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(TVehicleManager record);
}