package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.dao.AmountQueryMapper;
import com.dispart.dto.dataquery.Disp20210068InDto;
import com.dispart.dto.dataquery.Disp20210068OutDto;
import com.dispart.dto.dataquery.Disp20210068OutMx;
import com.dispart.entity.DatabaseCount;
import com.dispart.enums.BaseEnum;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.AmountQueryUnSettlementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;


@Service
@Slf4j
public class AmountQueryUnSettlementServiceImpl implements AmountQueryUnSettlementService {


    @Resource
    AmountQueryMapper amountQueryMapper;

    @Override
    public Result<Disp20210068OutDto> quryUnSettlementAmount(Disp20210068InDto param) {

        log.info("未结算额查询开始执行，传入参数：{}", JSON.toJSONString(param));
        Result<Disp20210068OutDto> result = null;

        if(ObjectUtils.isEmpty(param)){
            result = Result.build(null, ResultCodeEnum.PARAM_ERROR);
            return result;
        }
        if(StringUtils.isEmpty(param.getCurPage())||StringUtils.isEmpty(param.getPageSize())){
            result = Result.build(null, ResultCodeEnum.PARAM_ERROR);
            log.info("分页参数不能为空");
            return result;
        }
        if (StringUtils.isEmpty(param.getProvId())){
            return Result.build(BaseEnum.PARAM_NULL.getCode(),"客户ID不能为空");
        }

        param.setStartIndex((param.getCurPage()-1)*param.getPageSize());
        DatabaseCount databaseCount;
        try{
            databaseCount= amountQueryMapper.queryUnSettlementAmount_count(param);
        }catch (Exception e){
            log.error("查询未结算额总条数异常");
            throw new RuntimeException(e);
        }

        Disp20210068OutDto outDto = new Disp20210068OutDto();
        outDto.setRecNum(databaseCount.getCountRec().longValue());
        outDto.setTotalAmt(databaseCount.getTolAmt());
        if (databaseCount.getCountRec().longValue()<1){
            log.info("未结算额查询执行成功，返回参数：{}",JSON.toJSONString(outDto));
            return Result.build(outDto, ResultCodeEnum.SUCCESS);
        }
        ArrayList<Disp20210068OutMx> outList;
        try {
            outList = amountQueryMapper.queryUnSettlementAmount(param);
        }catch (Exception e){
            log.error("查询未结算额异常");
            throw new RuntimeException(e);
        }
        outDto.setList(outList);

        result= Result.build(outDto, ResultCodeEnum.SUCCESS);
        log.info("未结算额查询执行成功，返回参数：{}",JSON.toJSONString(outDto));
        return result;
    }
}
