package com.dispart.dao;

import com.dispart.vo.user.TCustomInfoManagerVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TCustomInfoManagerDao {
    /**
     * 根据手机号或法人电话查询客户信息
     * @param phone
     * @return
     */
    List<TCustomInfoManagerVo> selectCustomInfoByPhone(@Param("phone") String phone);


    TCustomInfoManagerVo selectCustomInfoByProvId(@Param("provId") String provId);

}