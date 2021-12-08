package com.dispart.dao.mapper;

import com.dispart.vo.basevo.RoleMenu;
import com.dispart.vo.entrance.TCargoInfoReportDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//来货报备明细表
@Mapper
public interface TCargoInfoReportDetailsMapper {

    int insert(TCargoInfoReportDetails record);
    int updateByPrimaryKey(TCargoInfoReportDetails record);


    /**
     * 插入产品明细
     * @param tCargoInfoReportDetails
     * @return
     */
    int insertList(@Param("list") List<TCargoInfoReportDetails> tCargoInfoReportDetails);


    /**
     * 根据报备主ID查询报备明细
     * @param reportId primary key
     * @return object by primary key
     */
    List<TCargoInfoReportDetails> selectByPrimaryKey(@Param("reportId") String reportId);

    /**
     * 根据报备ID删除报备明细
     * @param reportId
     * @return
     */
    int deleteByReportId(@Param("reportId") String reportId);


}