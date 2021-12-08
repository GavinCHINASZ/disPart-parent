package com.disPart.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dispart.model.order.TFileInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TFileInfoMapper extends BaseMapper<TFileInfo> {
    List<TFileInfo> selectByFileStatus(@Param("fileStatus") String fileStatus);

    @Select("select param_val from t_parmeter_info where param_type = '02' and param_nm = 'hsb.marketNumber'")
    String getMarketId();
}