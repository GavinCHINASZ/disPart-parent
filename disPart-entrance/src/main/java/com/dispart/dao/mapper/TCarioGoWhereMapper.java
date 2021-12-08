package com.dispart.dao.mapper;

import com.dispart.dto.entrance.D_0346FindDto;
import com.dispart.vo.entrance.TCarioGoWhere;

import java.util.List;

/**
 * 去向地信息管理
 */
public interface TCarioGoWhereMapper {
    public List<TCarioGoWhere> getList(D_0346FindDto d_0346FindDto);
}
