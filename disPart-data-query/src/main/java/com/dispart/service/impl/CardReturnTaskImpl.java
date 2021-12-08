package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.baseDto.BaseSelectionOutDto;
import com.dispart.dao.CardReturnTaskMapper;
import com.dispart.dto.dataquery.Disp20210349InDto;
import com.dispart.dto.dataquery.Disp20210349OutDto;
import com.dispart.result.Result;
import com.dispart.service.CardReturnTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CardReturnTaskImpl implements CardReturnTaskService {

    @Resource
    private CardReturnTaskMapper mapper;

    @Override
    public Result CardReturnTaskQuery(Disp20210349InDto inDto) {
        log.info("代充值查询交易开始，传入参数：" + JSON.toJSONString(inDto));
        if (inDto.getPageNum() != null && inDto.getPageSize()!= null){
            inDto.setPageNum((inDto.getPageNum() - 1) * inDto.getPageSize());
        }
        BaseSelectionOutDto<Disp20210349OutDto> outDto = new BaseSelectionOutDto<>();
        Integer tolPageNum;
        try{
            tolPageNum = mapper.selectCardReturnTaskCount(inDto);
            outDto.setTolPageNum(tolPageNum);
            if (tolPageNum==0){
                return Result.ok();
            }
            List dataList = mapper.selectCardReturnTask(inDto);
            outDto.setDataList(dataList);
        }catch (Exception e){
            log.error("数据库异常");
            throw new RuntimeException(e);
        }
        log.info("待充值业务查询结束，返回：" + JSON.toJSONString(outDto));
        return Result.ok(outDto);
    }
}
