package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.dao.CustomQueryMapper;
import com.dispart.dto.dataquery.Disp20210073InDto;
import com.dispart.dto.dataquery.Disp20210073OutDto;
import com.dispart.dto.dataquery.Disp20210073OutMx;
import com.dispart.entity.DatabaseCount;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.CustomRegistInfoQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;


@Service
@Slf4j
public class CustomRegistInfoQueryServiceImpl implements CustomRegistInfoQueryService {
    @Resource
    CustomQueryMapper customQueryMapper;

    /**
     * 客户注册信息查询
     *
     * @param param Disp20210073InDto
     * @return Disp20210073InDto
     */
    @Override
    public Result<Disp20210073OutDto> quryCustomRegistInfo(Disp20210073InDto param) {
        log.info("Disp20210073 客户注册信息查询开始执行，传入参数: {}", JSON.toJSONString(param));
        Result<Disp20210073OutDto> result = null;

        if (ObjectUtils.isEmpty(param)) {
            result = Result.build(null, ResultCodeEnum.PARAM_ERROR);
            return result;
        }

        if (StringUtils.isEmpty(param.getCurPage()) || StringUtils.isEmpty(param.getPageSize())) {
            result = Result.build(null, ResultCodeEnum.PARAM_ERROR);
            log.info("分页参数不能为空");
            return result;
        }

        param.setStartIndex((param.getCurPage() - 1) * param.getPageSize());

        DatabaseCount databaseCount;
        try {
            databaseCount = customQueryMapper.quryCustomRegistInfo_count(param);
        } catch (Exception e) {
            log.error("查询注册用户信息总条数异常");
            throw new RuntimeException(e);
        }

        Disp20210073OutDto outDto = new Disp20210073OutDto();
        outDto.setRecNum(databaseCount.getCountRec().longValue());
        outDto.setTotalAmt(databaseCount.getTolAmt());
        if (databaseCount.getCountRec().longValue() < 1) {
            log.info("客户注册信息查询，返回： {}", JSON.toJSONString(outDto));
            return Result.build(outDto, ResultCodeEnum.SUCCESS);
        }

        ArrayList<Disp20210073OutMx> outList;
        try {
            outList = customQueryMapper.quryCustomRegistInfo(param);
        } catch (Exception e) {
            log.error("查询注册用户信息异常");
            throw new RuntimeException(e);
        }
        outDto.setList(outList);

        result = Result.build(outDto, ResultCodeEnum.SUCCESS);
        log.info("客户注册信息查询，返回： {}", JSON.toJSONString(outDto));
        return result;
    }
}
