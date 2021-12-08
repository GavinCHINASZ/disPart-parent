package com.dispart.dao;

import com.dispart.dto.deviceManagerDto.TLoudspeakerInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TLoudspeakerInfoMapper {
    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(TLoudspeakerInfo record);

    /**
     * select by primary key
     * @param requestId primary key
     * @return object by primary key
     */
    TLoudspeakerInfo selectByPrimaryKey(String requestId);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(TLoudspeakerInfo record);
}