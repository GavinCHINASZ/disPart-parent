package com.dispart.dao.mapper;

import com.dispart.vo.basevo.TCardInfoVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TCardInfoMapper {
    int deleteByPrimaryKey(String cardNo);

    int insert(TCardInfoVo record);

    int insertSelective(TCardInfoVo record);

    TCardInfoVo selectByPrimaryKey(String cardNo);

    int updateByPrimaryKeySelective(TCardInfoVo record);

    int updateByPrimaryKey(TCardInfoVo record);

    int deleteDocumentNum(String documentNum);

    int updateByDocumentNumSelective(TCardInfoVo record);


}