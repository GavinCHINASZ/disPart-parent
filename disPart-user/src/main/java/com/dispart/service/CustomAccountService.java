package com.dispart.service;

import com.dispart.dto.dataquery.Disp20210066InDto;
import com.dispart.dto.dataquery.Disp20210066OutDto;
import com.dispart.dto.userdto.QueryUserInfoInDto;
import com.dispart.dto.userdto.QueryUserInfoOutDto;
import com.dispart.parmeterdto.DISP20210183OutDto;
import com.dispart.parmeterdto.DISP20210184CusAccDetailOutDto;
import com.dispart.parmeterdto.DISP20210184CusAccountDto;
import com.dispart.result.Result;
import com.dispart.vo.AccnoInfoVo;

/**
 * @author:xiejin
 * @date:Created in 2021/8/9 16:53
 * @description
 * @modified by:
 * @version: 1.0
 */
public interface CustomAccountService {
    /**
     * 客户账户查询
     *
     * @param into 传入参数
     * @return Result<QueryUserInfoOutDto>
     */
    public Result<DISP20210183OutDto> quryCustomAccount(DISP20210184CusAccountDto into);

    /**
     * 客户账户详情
     *  @param into 传入参数
     *   @return Result<DISP20210184CusAccDetailOutDto>
     */
    public Result<AccnoInfoVo> queryCustomAccountDetail(DISP20210184CusAccountDto into);
}
