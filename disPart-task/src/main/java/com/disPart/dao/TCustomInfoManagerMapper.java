package com.disPart.dao;

import com.dispart.vo.entrance.TCustomInfoManager;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//客户表
@Mapper
public interface TCustomInfoManagerMapper {

    /**
     * select by primary key
     * @param certNum primary key
     * @return object by primary key
     */
    List<TCustomInfoManager> selectByCertNum(String certNum);

}