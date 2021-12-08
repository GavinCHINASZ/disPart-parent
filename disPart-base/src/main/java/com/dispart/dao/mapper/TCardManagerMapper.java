package com.dispart.dao.mapper;

import com.dispart.dto.makeCard.QuryMakeCardInfoInDto;
import com.dispart.dto.makeCard.QuryMakeCardInfoOutParamDto;
import com.dispart.dto.makeCard.QuryWarehousingInDto;
import com.dispart.dto.makeCard.QuryWarehousingParamOutDto;
import com.dispart.vo.basevo.TCardInfoVo;
import com.dispart.vo.basevo.TCardManagerVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TCardManagerMapper {
    int insert(TCardManagerVo record);

    int insertSelective(TCardManagerVo record);

    TCardManagerVo selectByDocumentNum(@Param("docementNum") String docementNum);

    List<QuryMakeCardInfoOutParamDto>  selectAll(QuryMakeCardInfoInDto inDto);

    int queryCount(QuryMakeCardInfoInDto inDto);

    int updateByPrimaryKeySelective(TCardManagerVo record);

    int updateByPrimaryKeyForSatus(@Param("vo") TCardManagerVo record, @Param("status") String Status);

    List<QuryWarehousingParamOutDto>   selectAllByStatus(QuryWarehousingInDto inDto);

    int queryCountByStatus(QuryWarehousingInDto inDto);

    int queryCountByWarehousingStatus(QuryWarehousingInDto inDto);

    List<QuryWarehousingParamOutDto> queryAllByWarehousing(QuryWarehousingInDto inDto);


    int updateStatusByDocumentNum(@Param("tcardVo") TCardManagerVo record,@Param("origStatus") String origStatus);

}