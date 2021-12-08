package com.dispart.dao.mapper;

import com.dispart.dto.entrance.D_0297FindDto;
import com.dispart.dto.entrance.EntranceVeCheckOutDatilsDto;
import com.dispart.vo.entrance.TVechicleProcurerDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//进场货品明细表
@Mapper
public interface TVechicleProcurerDetailsMapper {
    /**
     * 添加进场货物明细
     * @param record the record
     * @return insert count
     */
    int insert(@Param("list") List<TVechicleProcurerDetails>  record);



    /**
     * select by primary key
     * @param inId primary key
     * @return object by primary key
     */
    TVechicleProcurerDetails selectByPrimaryKey(@Param("inId") String inId, @Param("varietyId") String varietyId,@Param("unit") String unit);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(TVechicleProcurerDetails record);

    /**
     *  update record selective
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(TVechicleProcurerDetails record);


    /**
     * select by primary key
     * @param inId primary key
     * @return object by primary key
     */
    List<EntranceVeCheckOutDatilsDto> selectByInId(@Param("inId") String inId);

    /**
     * 删除核验后没有的进场货物明细
     * @param list
     * @return
     */
    int deleteDetails(@Param("list")List<EntranceVeCheckOutDatilsDto> list,@Param("inId") String inId);

    /**
     * 根据 进出场查询 采购商进场货品明细
     *
     * @param d_0297FindDtos D_0297FindListDto
     * @return List<TVechicleProcurerDetails>
     */
    List<TVechicleProcurerDetails> findByD0297FindDtoList(List<D_0297FindDto> d_0297FindDtos);

    /**
     * 根据inId查询进出场明细
     * @param inId
     * @return
     */
    List<TVechicleProcurerDetails> findDetailsListByInId(@Param("inId") String inId);
}