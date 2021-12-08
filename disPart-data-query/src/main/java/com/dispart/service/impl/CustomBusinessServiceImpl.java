package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.dao.CustomBusinessMapper;
import com.dispart.dto.dataquery.Disp20210353InDto;
import com.dispart.dto.dataquery.Disp20210353OutDto;
import com.dispart.enums.BaseEnum;
import com.dispart.result.Result;
import com.dispart.service.CustomBusinessService;
import com.dispart.vo.dataQuery.Disp20210353InfoOutVo;
import com.dispart.vo.dataQuery.Disp20210353OutVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
@Slf4j
public class CustomBusinessServiceImpl implements CustomBusinessService {

    @Resource
    private CustomBusinessMapper mapper;

    @Override
    public Result getBusinessReport(Disp20210353InDto inDto) {
        log.info("客户经营交易报表查询交易开始，参数：" + JSON.toJSONString(inDto));
        if (StringUtils.isEmpty(inDto.getProvId())){
            return Result.build(BaseEnum.PARAM_NULL.getCode(),"客户ID为空");
        }
        Disp20210353OutDto outDto = new Disp20210353OutDto();
        try{
            Disp20210353InfoOutVo txnInfo = mapper.getTolTxnInfo(inDto);
            ArrayList<Disp20210353OutVo> txnInfoMx = mapper.getTxnInfoByPaymentMode(inDto);
            Disp20210353InfoOutVo txnInfoWithServChrg = mapper.getTxnInfoWithServChrg(inDto);
            txnInfo.setCommissionCount(txnInfoWithServChrg.getCommissionCount());
            txnInfo.setAvrCommission(txnInfoWithServChrg.getAvrCommission());
            ArrayList<Disp20210353InfoOutVo> outTxnInfo = new ArrayList();
            outTxnInfo.add(txnInfo);
            outDto.setTxnInfo(outTxnInfo);
            outDto.setTxnInfoMx(txnInfoMx);
        }catch (Exception e){
            log.error("数据库查询异常");
            throw new RuntimeException(e);
        }
        log.info("客户经营交易报表查询交易结束，返回：" + JSON.toJSONString(outDto));
        return Result.ok(outDto);
    }
}
