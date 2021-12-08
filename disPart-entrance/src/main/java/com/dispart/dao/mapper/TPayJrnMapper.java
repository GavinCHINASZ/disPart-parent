package com.dispart.dao.mapper;

import com.dispart.dto.entrance.D_0365FindDto;
import com.dispart.model.businessCommon.TPayJrn;
import com.dispart.vo.entrance.D_0365OutVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 进出场管理-收费员收费统计查询
 */
@Mapper
public interface TPayJrnMapper {
    /**
     * 进出场管理-收费员收费统计查询
     * @param dto
     * @return
     */
    List<D_0365OutVo> selectByDto(D_0365FindDto dto);
    /**
     * 进出场管理-收费员收费统计总条数查询
     * @param dto
     * @return
     */
    Integer selectCountByDto(D_0365FindDto dto);

    /**
     * 根据进出场ID查询流水信息
     * @param inId
     * @return
     */
    TPayJrn selectByInId(String inId);

}