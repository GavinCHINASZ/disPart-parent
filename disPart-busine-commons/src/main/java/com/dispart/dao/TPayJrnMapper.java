package com.dispart.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dispart.dto.busineCommon.DISP20210333InDto;
import com.dispart.model.businessCommon.TPayJrn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.Map;

@Mapper
public interface TPayJrnMapper extends BaseMapper<TPayJrn> {

    @Select("select nextval('jrnlNum') as jrnlNum")
    Map queryJnrlNum();

    @Update("update t_card_return_task set STATUS = '9',CHARGE_TIME = #{date}, CHARGE_JRNL_NUM = #{jrnlNum} where TASK_ID = #{no}")
    int updateTCard(int no, Date date,String jrnlNum);

    Integer updatePayStatus(DISP20210333InDto inDto);
}