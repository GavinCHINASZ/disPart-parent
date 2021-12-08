package com.dispart.dao.mapper;

import com.dispart.vo.base.DiscountsActivityVo;
import com.dispart.vo.base.DiscountsDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 优惠活动详情
 *
 * @author 黄贵川
 * @date 2021-09-06
 */
@Mapper
public interface DiscountsDetailMapper {
    /**
     * 优惠活动详情 新增
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param discountsDetailVo DiscountsDetailVo
     * @return int
     */
    int insertDiscountsDetail(DiscountsDetailVo discountsDetailVo);

    /**
     * 优惠活动详情 删除
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param discountsDetailVo DiscountsDetailVo
     * @return int
     */
    int deleteDiscountsDetail(DiscountsDetailVo discountsDetailVo);

    /**
     * 根据 优惠活动 查询 优惠活动详情
     *
     * @author 黄贵川
     * @date 2021-09-07
     * @param list DiscountsActivityVo
     * @return List<DiscountsDetailVo>
     */
    List<DiscountsDetailVo> findByDiscountsActivityList(@Param("list") List<DiscountsActivityVo> list);

    /**
     * 查询优惠金额
     *
     * @author 黄贵川
     * @date 2021-09-08
     * @param body DiscountsDetailVo
     * @return Result
     */
    BigDecimal selectDiscountsDetailPrice(DiscountsDetailVo body);
}