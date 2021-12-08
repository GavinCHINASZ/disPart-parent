package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.dao.AmountQueryMapper;
import com.dispart.dto.dataquery.Disp20210067InDto;
import com.dispart.dto.dataquery.Disp20210067OutDto;
import com.dispart.dto.dataquery.Disp20210067OutMx;
import com.dispart.entity.DatabaseCount;
import com.dispart.enums.BaseEnum;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.AmountQuerySettlementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;


@Service
@Slf4j
public class AmountQuerySettlementServiceImpl implements AmountQuerySettlementService {

    @Resource
    AmountQueryMapper amountQueryMapper;

    @Override
    public Result<Disp20210067OutDto> qurySettlementAmount(Disp20210067InDto param) {
        log.info("已结算额查询开始执行，传入参数：{}", JSON.toJSONString(param));

        Result<Disp20210067OutDto> result = null;

        if(StringUtils.isEmpty(param.getCurPage())||StringUtils.isEmpty(param.getPageSize())){
            result = Result.build(null, ResultCodeEnum.PARAM_ERROR);
            log.info("分页参数不能为空");
            return result;
        }
        if (StringUtils.isEmpty(param.getProvId())){
            return Result.build(BaseEnum.PARAM_NULL.getCode(),"客户ID不能为空");
        }

        param.setStartIndex((param.getCurPage()-1)*param.getPageSize());
        DatabaseCount databaseCount = null;
        try{
            databaseCount = amountQueryMapper.querySettlementAmount_count(param);
        }catch (Exception e){
            log.error("查询已结算额总条数异常");
            throw new RuntimeException(e);
        }
        Disp20210067OutDto outDto = new Disp20210067OutDto();
        outDto.setRecNum(databaseCount.getCountRec().longValue());
        outDto.setTotalAmt(databaseCount.getTolAmt());
        if (databaseCount.getCountRec().longValue()<1){
            log.info("结算额查询执行成功，返回：{}", JSON.toJSONString(outDto));
            return Result.build(outDto, ResultCodeEnum.SUCCESS);
        }
        ArrayList<Disp20210067OutMx> outList;
        try{
            outList = amountQueryMapper.querySettlementAmount(param);
        }catch (Exception e){
            log.error("查询已结算额异常");
            throw new RuntimeException(e);
        }
        outDto.setList(outList);

        result= Result.build(outDto, ResultCodeEnum.SUCCESS);
        log.info("结算额查询执行成功，返回：{}", JSON.toJSONString(outDto));
        return result;
    }

}
