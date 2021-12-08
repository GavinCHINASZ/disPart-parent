package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.dao.AmountQueryMapper;
import com.dispart.dto.dataquery.Disp20210066InDto;
import com.dispart.dto.dataquery.Disp20210066OutDto;
import com.dispart.dto.dataquery.Disp20210066OutMx;
import com.dispart.entity.DatabaseCount;
import com.dispart.enums.BaseEnum;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.AmountQueryTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;


@Service
@Slf4j
public class AmountQueryTransactionServiceImpl implements AmountQueryTransactionService {

    @Resource
    AmountQueryMapper amountQueryMapper;

    @Override
    public Result<Disp20210066OutDto> quryTransactionAmount(Disp20210066InDto param) {

        log.info("交易额查询开始执行，传入参数: {}",JSON.toJSONString(param));
        Result<Disp20210066OutDto> result = null;

        if(ObjectUtils.isEmpty(param)){
            log.info("参数列表不能为空");
            result = Result.build(null, ResultCodeEnum.PARAM_ERROR);
            return result;
        }
        if(StringUtils.isEmpty(param.getCurPage())||StringUtils.isEmpty(param.getPageSize())){
            log.info("分页参数不能为空");
            result = Result.build(null, ResultCodeEnum.PARAM_ERROR);
            return result;
        }
        if (StringUtils.isEmpty(param.getProvId())){
            return Result.build(BaseEnum.PARAM_NULL.getCode(),"客户ID不能为空");
        }

        param.setStartIndex((param.getCurPage()-1)*param.getPageSize());
        DatabaseCount databaseCount = null;
        try{
            databaseCount = amountQueryMapper.queryTransactionAmount_count(param);
        }catch (Exception e){
            e.printStackTrace();
        }

        Disp20210066OutDto outDto = new Disp20210066OutDto();
        outDto.setRecNum(databaseCount.getCountRec().longValue());
        outDto.setTotalAmt(databaseCount.getTolAmt());
        if (databaseCount.getCountRec().longValue()<1){
            log.info("交易额查询，未查询到数据，返回{}", JSON.toJSONString(outDto));
            return Result.build(outDto, ResultCodeEnum.SUCCESS);
        }
        ArrayList<Disp20210066OutMx> outList;
        try{
            outList = amountQueryMapper.queryTransactionAmount(param);
        }catch (Exception e){
            log.error("查询交易额异常");
            throw new RuntimeException(e);
        }
        outDto.setList(outList);

        result= Result.build(outDto, ResultCodeEnum.SUCCESS);
        log.info("交易额查询结束，返回{}", JSON.toJSONString(outDto));
        return result;
    }
}
