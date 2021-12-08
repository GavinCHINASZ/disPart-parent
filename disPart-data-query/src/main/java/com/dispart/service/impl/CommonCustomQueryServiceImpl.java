package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.dao.CustomInfoManagerMapper;
import com.dispart.dto.dataquery.Disp20210209InDto;
import com.dispart.dto.dataquery.Disp20210209OutDto;
import com.dispart.result.Result;
import com.dispart.service.CommonCustomQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

@Service
@Slf4j
public class CommonCustomQueryServiceImpl implements CommonCustomQueryService {

    @Autowired
    private CustomInfoManagerMapper mapper;

    @Override
    public Result customInfoCommonQuery(Disp20210209InDto inDto) {
        log.info("查询客户信息开始，传入参数：" + JSON.toJSONString(inDto));
        if (inDto.getPageNum() != null && inDto.getPageSize() != null){
            inDto.setPageNum((inDto.getPageNum()-1)*inDto.getPageSize());
        }else {
            inDto.setPageNum(0);
            inDto.setPageSize(10);
        }

        ArrayList<Disp20210209OutDto> out = new ArrayList<>();
        if (StringUtils.isEmpty(inDto.getQueryType())){
            out = mapper.queryCustomInfo(inDto);
        }else if(inDto.getQueryType().equals("1")){
            out = mapper.queryCustomInfoWithAccount(inDto);
        }else if(inDto.getQueryType().equals("2")){
            out = mapper.queryCustomInfoWithAccountAndCard(inDto);
        }else if(inDto.getQueryType().equals("3")){
            out = mapper.queryCustomInfoByCardNo(inDto);
        }else if (inDto.getQueryType().equals("4")){
            out = mapper.queryCustomInfoWithCard(inDto);
        }

        log.info("查询客户信息结束，返回：" + JSON.toJSONString(out));
        return Result.ok(out);
    }
}
