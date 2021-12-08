package com.dispart.dao;

import com.dispart.dto.auth.TEmployeeInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author:xts
 * @date:Created in 2021/6/20 17:28
 * @description 员工接口
 * @modified by:
 * @version: 1.0
 */
@Mapper
public interface ITEmployeeInfoDao {
    //根据用户Id获取管理客户端用户信息
    List<TEmployeeInfoDto> queryByUserId(Map map);
}