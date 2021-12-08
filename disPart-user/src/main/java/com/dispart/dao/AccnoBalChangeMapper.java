package com.dispart.dao;

import com.dispart.parmeterdto.AccnoChangeDetailDto;

import java.util.List;

/**
 * 账户明细表 记录账户变动明晰
 */
public interface AccnoBalChangeMapper {

    int add(AccnoChangeDetailDto dto);

    int count(AccnoChangeDetailDto dto);

    List<AccnoChangeDetailDto> queryList(AccnoChangeDetailDto dto);
}
