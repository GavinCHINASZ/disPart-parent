package com.dispart.dao.mapper;

import com.dispart.dto.entrance.D_0223FindDto;
import com.dispart.vo.entrance.TCustomInfoManager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

//客户表
@Mapper
public interface TCustomInfoManagerMapper {
    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(TCustomInfoManager record);

    /**
     * select by primary key
     * @param provId primary key
     * @return object by primary key
     */
    TCustomInfoManager selectByPrimaryKey(String provId);


    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(TCustomInfoManager record);

    /**
     * 根据电话号码查询客户信息与车辆信息
     * @param record
     * @return
     */
    D_0223FindDto findCustomerByPhone(D_0223FindDto record);

    /**
     * 根据电话号码查询客户信息
     * @param record
     * @return
     */
    TCustomInfoManager findCustomerInfoByPhone(D_0223FindDto record);

    /**
     * 根据会员卡号查询客户信息
     * @param cardNo
     * @return
     */
    TCustomInfoManager selectByCardNo(@Param("cardNo") String cardNo);
    /**
     * 根据电话或助记码查询客户信息
     * @param key
     * @return
     */
    TCustomInfoManager selectByPhoneOrMnmnCode(@Param("key") String key);
}