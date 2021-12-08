package com.disPart.dao;

import com.dispart.dto.AccnoInfoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TAccnoInfoMapper {

    void updateAccnoInfo();

    void lock();

    void unlock();

    void updateAccnoMac(AccnoInfoVo accnoInfoVo);

    List<AccnoInfoVo> findByMac();

    int count();

    /**
     * 查找mac未初始化的条数
     * @return 条数
     */
    int countMac();


}