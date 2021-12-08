package com.dispart.dao;

import com.dispart.vo.user.CustomWithdrawTmpVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 客户提现累加金额dao
 *
 * @author 黄贵川
 * @date 2021-09-22
 */
@Mapper
public interface CustomWithdrawTmpDao {

    /**
     *
     * @param provId 客户编号
     * @return CustomWithdrawTmpVo
     */
    CustomWithdrawTmpVo findByProvId(@Param("provId") String provId);
}
