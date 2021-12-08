package com.dispart.dao.mapper;

import com.dispart.vo.base.DiscountsDetailVo;
import com.dispart.vo.base.DiscountsUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 用户优惠活动记录
 *
 * @author 黄贵川
 * @date 2021-09-08
 */
@Mapper
public interface DiscountsUserMapper {
    /**
     * 客户优惠活动记录 新增
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param discountsUserVo DiscountsUserVo
     * @return int
     */
    int insertDiscountsUser(DiscountsUserVo discountsUserVo);
    /**
     * 查找该用户在当天使用过
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param provId 客户编号
     * @return int
     */
    int findDiscountsUserNow(@Param("provId") String provId);

    /**
     * 查找该用户在周期内是否使用满次数
     *
     * @author 黄贵川
     * @date 2021-09-08
     * @param body DiscountsDetailVo
     * @return int
     */
    int selectUseNum(DiscountsDetailVo body);

    /**
     * 查询优惠金额
     *
     * @param actId 活动ID
     * @return BigDecimal
     */
    BigDecimal findDiscntAmt(@Param("actId") Integer actId);

}
