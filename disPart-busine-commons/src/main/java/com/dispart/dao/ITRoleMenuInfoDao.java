package com.dispart.dao;

import com.dispart.vo.commons.TMenuInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * (TRoleMenuInfo)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-19 19:46:03
 */
@Mapper
public interface ITRoleMenuInfoDao {



    /**
     * @return 对象列表
     */
    List<TMenuInfo> queryAll(Map map);
    List<TMenuInfo> queryByOne(Map map);
    List<TMenuInfo> queryByRoleToMenu(Map map);


}