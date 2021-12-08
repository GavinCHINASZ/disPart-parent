package com.dispart.dao.mapper;

import com.dispart.dto.entrance.D_0345FindDto;
import com.dispart.vo.entrance.THopmmProducingarea;

import java.util.List;

/**
 * 产地信息
 */
public interface THopmmProducingareaMapper {
    public List<THopmmProducingarea> getList(D_0345FindDto d_0345FindDto);
}
