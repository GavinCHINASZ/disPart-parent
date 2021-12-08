package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.dao.CustomQueryMapper;
import com.dispart.dto.dataquery.Disp20210074InDto;
import com.dispart.dto.dataquery.Disp20210074OutDto;
import com.dispart.dto.dataquery.Disp20210074OutMx;
import com.dispart.entity.DatabaseCount;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.CustomInfoQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;


@Service
@Slf4j
public class CustomInfoQueryServiceImpl implements CustomInfoQueryService {



    @Resource
    CustomQueryMapper customQueryMapper;

    @Override
    public Result<Disp20210074OutDto> quryCustomInfo(Disp20210074InDto param) {

        log.debug("客户信息查询开始，传入参数：{}", JSON.toJSONString(param));
        Result<Disp20210074OutDto> result;

        if (ObjectUtils.isEmpty(param)) {
            result = Result.build(null, ResultCodeEnum.PARAM_ERROR);
            return result;
        }

        if (StringUtils.isEmpty(param.getCurPage()) || StringUtils.isEmpty(param.getPageSize())) {
            result = Result.build(null, ResultCodeEnum.PARAM_ERROR);
            log.info("分页参数不能为空");
            return result;
        }

        param.setStartIndex((param.getCurPage()-1) * param.getPageSize());
        DatabaseCount databaseCount;
        try{
            databaseCount = customQueryMapper.quryCustomInfo_count(param);
        }catch (Exception e){
            log.error("查询客户信息总条数异常");
            throw new RuntimeException(e);
        }
        Disp20210074OutDto outDto = new Disp20210074OutDto();
        outDto.setRecNum(databaseCount.getCountRec().longValue());
        if (databaseCount.getCountRec().longValue() < 1) {
            return Result.build(outDto, ResultCodeEnum.SUCCESS);
        }
        ArrayList<Disp20210074OutMx> outList;
        try {
            outList = customQueryMapper.quryCustomInfo(param);
        }catch (Exception e){
            log.error("查询客户信息异常");
            throw new RuntimeException(e);
        }
        for (Disp20210074OutMx mx : outList){
            mx.setCertType("01");
        }
        outDto.setList(outList);
        log.debug("客户信息查询结束，返回：{}", JSON.toJSONString(outDto));
        result = Result.build(outDto, ResultCodeEnum.SUCCESS);
        return result;
    }
}
