package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.baseDto.BaseSelectionOutDto;
import com.dispart.dao.BillTransInfoMapper;
import com.dispart.dto.dataquery.DISP20210355InDto;
import com.dispart.dto.dataquery.DISP20210355OutDto;
import com.dispart.enums.BaseEnum;
import com.dispart.result.Result;
import com.dispart.service.BillTransInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
@Slf4j
public class BillTransInfoServiceImpl implements BillTransInfoService {

    @Resource
    private BillTransInfoMapper mapper;

    @Override
    public Result getBillTransInfo(DISP20210355InDto inDto) {
        log.info("账单交易明显查询开始，参数： " + JSON.toJSONString(inDto));
        if (inDto.getPageNum()!= null && inDto.getPageSize()!= null){
            inDto.setPageNum((inDto.getPageNum()-1) * inDto.getPageSize());
        }
        if (!StringUtils.isEmpty(inDto.getTxnType())){
            if (!inDto.getTxnType().equals("4") || !inDto.getTxnType().equals("6")){
                return Result.build(BaseEnum.PARAM_NULL.getCode(),"交易类型错误");
            }
        }

        BaseSelectionOutDto<DISP20210355OutDto> outDto = new BaseSelectionOutDto();
        Integer tolPageNum = mapper.getBillTransInfoCount(inDto);
        outDto.setTolPageNum(tolPageNum);
        if (tolPageNum == 0){
            log.info("账单交易明显查询结束，未查询到数据，返回： " + JSON.toJSONString(outDto));
            return Result.ok(outDto);
        }
        ArrayList<DISP20210355OutDto> dataList = mapper.getBillTransInfo(inDto);
        outDto.setDataList(dataList);
        log.info("账单交易明显查询结束，返回： " + JSON.toJSONString(outDto));
        return Result.ok(outDto);
    }
}
