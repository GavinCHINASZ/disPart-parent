package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.dao.TDeviceRelevanceMapper;
import com.dispart.dto.DeviceRelevanceDto.Disp20210296InDto;
import com.dispart.enums.BaseEnum;
import com.dispart.model.TDeviceRelevance;
import com.dispart.result.Result;
import com.dispart.service.TDeviceRelevanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Slf4j
public class TDeviceRelevanceServiceImpl implements TDeviceRelevanceService {

    @Autowired
    private TDeviceRelevanceMapper mapper;

    @Override
    public Result getIODeviceInfo(Disp20210296InDto inDto) {
        log.info("进出口查询开始，参数：" + JSON.toJSONString(inDto));

        if(StringUtils.isEmpty(inDto.getIoType())){
            return Result.build(BaseEnum.PARAM_NULL.getCode(),"进出口类型不能为空");
        }
        List<TDeviceRelevance> dataList = mapper.selectIODevice(inDto.getIoType());

        log.info("进出口查询结束，返回：" + JSON.toJSONString(dataList));
        return Result.ok(dataList);
    }
}
