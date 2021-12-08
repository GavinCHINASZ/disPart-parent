package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.dao.CustomAccountDao;
import com.dispart.parmeterdto.DISP20210183OutDto;
import com.dispart.parmeterdto.DISP20210184CusAccountDto;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.CustomAccountService;
import com.dispart.vo.AccnoInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客户账查询
 *
 * @author xiejin
 * @version 1.0
 * @date in 2021/8/9 17:12
 */
@Service
@Slf4j
@Transactional
public class CustomAccountServiceImpl implements CustomAccountService {

    @Resource
    CustomAccountDao customAccountDao;

    @Transactional
    @Override
    public Result<DISP20210183OutDto> quryCustomAccount(DISP20210184CusAccountDto into) {
        log.info("DISP20210183 查询客户账户-查询参数： " + into);
        DISP20210183OutDto dto = new DISP20210183OutDto();
        // 有分页参数才做分页查询
        if (into.getPageNum() != null && into.getPageNum() > 0 && into.getPageSize() > 0) {
            Long pageNum = (into.getPageNum() - 1) * into.getPageSize();
            into.setStartIndex(pageNum);

            try {
                Integer TolPageNum = customAccountDao.countCustomAccount(into);
                if (TolPageNum > 0) {
                    dto.setTolPageNum(TolPageNum);
                    dto.setCusAccountList(customAccountDao.queryCustomAccount(into));
                } else {
                    return Result.build(ResultCodeEnum.DATA_NO_ERROR.getCode(), ResultCodeEnum.DATA_NO_ERROR.getMessage());
                }
            } catch (Exception e) {
                log.error("客户查询异常", e);
                //return Result.build(null, 1,"客户查询异常");
            }
        } else {
            try {
                List<DISP20210184CusAccountDto> arr = customAccountDao.queryCustomAccount(into);
                dto.setCusAccountList(arr);
                log.debug(JSON.toJSONString(arr));
            } catch (Exception e) {
                log.error("客户查询异常", e);
                //return Result.build(null, 1,"客户查询异常");
            }
        }
        return Result.ok(dto);
        /*  return customAccountDao.queryCustomAccount(provNm,shrtNm,account);*/
    }

    /**
     * 客户账详情查询
     *
     * @param into DISP20210184CusAccountDto传入参数
     * @return Result<AccnoInfoVo>
     */
    @Override
    public Result<AccnoInfoVo> queryCustomAccountDetail(DISP20210184CusAccountDto into) {
        AccnoInfoVo dto;
        try {
            log.info("DISP20210184查询客户账户详细-查询参数： " + into);
            dto = customAccountDao.queryCustomAccountDetail(into);
            log.info("查询客户账户详细-返回参数： " + dto);
        } catch (Exception e) {
            log.error("查询客户账户详细");
            return Result.build(null, 1, "客户查询异常");
        }
        return Result.ok(dto);
    }
}
