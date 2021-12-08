package com.dispart.dao.mapper;

import com.dispart.dto.partmodetype.DISP20210101SePMTInDto;
import com.dispart.vo.basevo.PartModeTypeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BasePartModeTypeMapper {
    /**
     * 查询分账模式信息
     * @param disp20210101SePMTInDto
     * @return
     */
    List<PartModeTypeVo> sePMT(DISP20210101SePMTInDto disp20210101SePMTInDto);

    /**
     * 查询分账模式数量
     * @param disp20210101SePMTInDto
     * @return
     */
    Integer pMTNum(DISP20210101SePMTInDto disp20210101SePMTInDto);

    /**
     * 查询最大编号
     * @return
     */
    String seMaxId();

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(PartModeTypeVo record);

    /**
     * 根据ID修改模式信息
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(PartModeTypeVo record);

    /**
     * 全部修改为未选择状态，表中所有数据只允许有一条是选中状态
     * @param partModeTypeVo
     * @return
     */
    int upSt1ByPrimaryKey(PartModeTypeVo partModeTypeVo);

    /**
     * 修改指定ID数据为选中状态
     * @param partModeTypeVo
     * @return
     */
    int upSt0ByPrimaryKey(PartModeTypeVo partModeTypeVo);
}