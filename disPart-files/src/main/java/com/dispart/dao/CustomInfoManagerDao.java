package com.dispart.dao;

import com.dispart.vo.entrance.TCustomInfoManager;
import com.dispart.vo.entrance.TVechicleProcurer;
import com.dispart.vo.user.TCustomInfoManagerVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomInfoManagerDao {

    /**
     * 查询客户信息列表
     *
     * @author 黄贵川
     * @date 2021-10-08
     * @return List<TCustomInfoManager>
     */
    List<TCustomInfoManager> selectBaseMerchantFileList(TCustomInfoManagerVo tCustomInfoManagerVo);

    /**
     * 修改客户信息列表
     *
     * @author 黄贵川
     * @date 2021-10-08
     * @param list List<TCustomInfoManager>
     * @return Integer
     */
    int updateCustomInfoManagerList(List<TCustomInfoManager> list);

    /**
     * 查询 车辆进出管理
     *
     * @author 黄贵川
     * @date 2021-10-09
     * @return List<TCustomInfoManager>
     */
    List<TVechicleProcurer> selectVechicleProcurerList(TVechicleProcurer tVechicleProcurer);

    /**
     * 修改 车辆进出管理
     *
     * @author 黄贵川
     * @date 2021-10-09
     * @param list List<TVechicleProcurer>
     * @return Integer
     */
    int updateVechicleProcurerList(List<TVechicleProcurer> list);
}