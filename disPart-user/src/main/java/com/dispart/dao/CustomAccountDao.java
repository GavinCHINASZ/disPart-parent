package com.dispart.dao;

import com.dispart.parmeterdto.DISP20210184CusAccDetailOutDto;
import com.dispart.parmeterdto.DISP20210184CusAccountDto;
import com.dispart.result.Result;
import com.dispart.vo.AccnoInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author:xiejin
 * @date:Created in 2021/8/9 16:07
 * @description
 * @modified by:
 * @version: 1.0
 */
@Mapper
public interface CustomAccountDao {
    /**
     * 客户账户查询
     */
    public List<DISP20210184CusAccountDto> queryCustomAccount(DISP20210184CusAccountDto into);
    /**
     * 查询客户账户条数
     */
    public Integer countCustomAccount(DISP20210184CusAccountDto into);
    /**
     * 客户账户详情
     */
    public AccnoInfoVo queryCustomAccountDetail(DISP20210184CusAccountDto into);

    /**
     *
     */
    public Integer queryCountByProvId(@Param("provId") String provId);
}
