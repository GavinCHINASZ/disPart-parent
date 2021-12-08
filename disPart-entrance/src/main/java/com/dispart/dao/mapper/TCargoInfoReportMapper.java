package com.dispart.dao.mapper;

import com.dispart.dto.entrance.*;
import com.dispart.vo.entrance.TCargoInfoReport;
import feign.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//来货报备主表
@Mapper
public interface TCargoInfoReportMapper {


    /**
     * select by primary key
     * @param reportId primary key
     * @return object by primary key
     */
    TCargoInfoReport selectByPrimaryKey(String reportId);

    /**
     * 复核时修改状态
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(D_0226UpInDto record);

    /**
     * 修改报备信息中的车辆信息
     * @param record the updated record
     * @return update count
     */
    int updatVeByPrimaryKey(D_0281UpInDto record);

    /**
     * 作废报备记录
     * @param record the updated record
     * @return update count
     */
    int updatVeToNoByPrimaryKey(D_0281UpInDto record);

    /**
     * 添加来货报备主信息
     * @param record
     * @return
     */
    int insert0224(D_0224AddDto record);

    /**
     * 根据条件查询报备主要信息
     * @param d_0225FindDto
     * @return
     */
    List<D_0225FindOutYDto> findByParm(D_0225FindDto d_0225FindDto);
    /**
     * 根据条件查询报备主要信息总数
     * @param d_0225FindDto
     * @return
     */
    Integer findNumByParm(D_0225FindDto d_0225FindDto);

    /**
     * 大车进场时查询报备主要信息
     * @param d_0225FindDto
     * @return
     */
    D_0227FindOutDto findVeByParm(D_0225FindDto d_0225FindDto);

    /**
     * 大车进场时查询报备信息
     * @param d_0225FindDto
     * @return
     */
    TCargoInfoReport findAllVeByParm(D_0225FindDto d_0225FindDto);

    /**
     * 大车进场时根据车牌号查询报备信息
     * @param vehicleNum
     * @return
     */
    TCargoInfoReport findVeByVehicleNum(@Param("vehicleNum") String vehicleNum);

    /**
     * 报备申请时查询报备信息
     * @param vehicleNum
     * @return
     */
    TCargoInfoReport findByVehicleNum(@Param("vehicleNum") String vehicleNum);

    /**
     * 查询报备信息的审核状态
     * @param record
     * @return
     */
    String findCargoAuSt(@Param("reportId") String record);



    /**
     * 进场修改报备信息状态
     * @param reportId
     * @return
     */
    int updatAuStByPrimaryKey(@Param("reportId") String reportId);

    /**
     * 查询报备车辆信息
     * @param d_0225FindDto
     * @return
     */
    QueryVechicleInDto findeVchicleInfoByParm(D_0225FindDto d_0225FindDto);



}