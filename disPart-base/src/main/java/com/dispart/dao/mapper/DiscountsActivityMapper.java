package com.dispart.dao.mapper;

import com.dispart.model.CustomInfoManager;
import com.dispart.vo.base.DiscountsActivityVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 优惠活动
 *
 * @author 黄贵川
 * @date 2021-09-06
 */
@Mapper
public interface DiscountsActivityMapper {
    /**
     * 优惠活动 新增
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param body DiscountsActivityVo
     * @return int
     */
    int insertDiscountsActivity(DiscountsActivityVo body);

    /**
     * 优惠活动 查询list
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param body DiscountsActivityVo
     * @return DiscountsActivityVo
     */
    List<DiscountsActivityVo> selectDiscountsActivityList(DiscountsActivityVo body);

    /**
     * 优惠活动 查询数量
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param body DiscountsActivityVo
     * @return Integer
     */
    Integer selectDiscountsActivityCount(DiscountsActivityVo body);

    /**
     * 优惠活动 删除
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param body DiscountsActivityVo
     * @return int
     */
    int deleteDiscountsActivity(DiscountsActivityVo body);

    /**
     * 优惠活动 修改
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param body DiscountsActivityVo
     * @return int
     */
    int updateDiscountsActivity(DiscountsActivityVo body);

    /**
     * 查询活动状态数量
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param activityStatus 状态
     * @return int
     */
    int findStatusCount(@Param("activityStatus") String activityStatus);

    /**
     * 查询开启并且在有效时间内的活动
     *
     * @author 黄贵川
     * @date 2021-09-08
     * @return DiscountsActivityVo
     */
    DiscountsActivityVo findDiscountsActivityOpen();

    /**
     * 查询活动
     *
     * @author 黄贵川
     * @date 2021-09-17
     * @param actId actId
     * @return DiscountsActivityVo
     */
    DiscountsActivityVo findDiscountsActivityByActID(@Param("actId") Integer actId);

    /**
     * 查询用户
     *
     * @author 黄贵川
     * @date 2021-11-19
     * @param provId provId
     * @return CustomInfoManager
     */
    CustomInfoManager findCustomInfoManagerByProvId(@Param("provId") String provId);

    /**
     * 活动优惠总金额
     *
     * @author 黄贵川
     * @date 2021-11-19
     * @return String
     */
    String findDiscountsAmt();
}