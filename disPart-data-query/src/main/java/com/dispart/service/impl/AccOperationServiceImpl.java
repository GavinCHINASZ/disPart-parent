package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.baseDto.BaseSelectionOutDto;
import com.dispart.dao.AccnoInfoDetailsMapper;
import com.dispart.dto.dataquery.DISP20210354InDto;
import com.dispart.dto.dataquery.DISP20210354OutDto;
import com.dispart.model.AccnoInfoDetails;
import com.dispart.result.Result;
import com.dispart.service.AccOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
@Slf4j
public class AccOperationServiceImpl implements AccOperationService {

    @Resource
    private AccnoInfoDetailsMapper mapper;

    @Override
    public Result getAccOperation(DISP20210354InDto inDto) {
        log.info("账户操作记录查询开始，参数：" + JSON.toJSONString(inDto));
        if (!StringUtils.isEmpty(inDto.getPageNum()) && !StringUtils.isEmpty(inDto.getPageSize())){
            inDto.setPageNum((inDto.getPageNum()-1) * inDto.getPageSize());
        }
        BaseSelectionOutDto<DISP20210354OutDto> outDto = new BaseSelectionOutDto<>();
        Integer tolpageNum = 0;
        ArrayList<DISP20210354OutDto> dataList;
        try{
            tolpageNum = mapper.getAccOperationCount(inDto);
        }catch (Exception e){
            log.error("账户操作记录查询--数据库查询总条数异常");
            throw new RuntimeException(e);
        }
        if (tolpageNum == 0){
            outDto.setTolPageNum(tolpageNum);
            log.info("账户操作记录查询结束，未查询到符合条件的数据");
            return Result.ok();
        }
        try{
            dataList= mapper.getAccOperation(inDto);
        }catch (Exception e){
            log.error("账户操作记录查询--数据库查询记录异常");
            throw new RuntimeException(e);
        }
        outDto.setTolPageNum(tolpageNum);
        outDto.setDataList(dataList);
        log.info("账户操作记录查询结束，返回：" + JSON.toJSONString(outDto));
        return Result.ok(outDto);
    }
}
