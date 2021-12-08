package com.dispart.service.impl;

import com.dispart.dao.mapper.TVechicleProcurerMapper;
import com.dispart.dto.entrance.QuryEntranceCheckParamOutDto;
import com.dispart.dto.entrance.QuryEntranceVeCheckInDto;
import com.dispart.dto.entrance.QuryEntranceVeCheckOutDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.EntranceTurnOverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.dispart.result.ResultCodeEnum.SUCCESS;

@Service
@Slf4j
public class EntranceTurnOverServiceImpl implements EntranceTurnOverService {

    @Resource
    TVechicleProcurerMapper tVechicleProcurerMapper;
    /**
     * 查询业务人员办理进出场数据
     * @param param
     * @return
     */
    @Override
    public Result<QuryEntranceVeCheckOutDto> quryInOutInfo(Request<QuryEntranceVeCheckInDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(1, "输入参数为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(1, "输入参数为空");
        }
        QuryEntranceVeCheckInDto inDto = param.getBody();

        int tolNum = 0;//总记录数
        QuryEntranceVeCheckOutDto outBody = new QuryEntranceVeCheckOutDto();

        if(StringUtils.isEmpty(inDto.getInId())) {
            if (inDto.getPageNum() == null || inDto.getPageSize() == null) {
                log.info("进出场业务-分页参数不能为空");
                return Result.build(1, "分页参数为空");
            }
            if (inDto.getPageSize() <= 0) {
                log.info("进出场业务-分页条数输入错误");
                return Result.build(1, "分页条数输入错误");
            }
            if (inDto.getPageNum() <= 0) {
                log.info("进出场业务-分页页数输入错误");
                return Result.build(1, "分页页数输入错误");
            }
            int strNum = (inDto.getPageNum() - 1) * inDto.getPageSize();
            inDto.setStrNum(strNum);//起始记录数

            try {
                if (param.getHead().getChanlNo().equals("05")) {//智慧贵农app
                    tolNum = tVechicleProcurerMapper.selcetInOutOrValueCount(inDto);
                } else {
                    tolNum = tVechicleProcurerMapper.selectInOutCount(inDto);
                }
            } catch (DataAccessException e) {
                log.error("数据库查询异常", e);
                throw new RuntimeException("数据库查询异常");
            }
            outBody.setTolPageNum(tolNum);//总条数
            if (tolNum <= 0) {
                log.info("进出场业务-查询记录数为0");
                return Result.build(outBody, SUCCESS);
            }
            List<QuryEntranceCheckParamOutDto> paramList = null;
            try {
                if (param.getHead().getChanlNo().equals("05")) {//智慧贵农app
                    paramList = tVechicleProcurerMapper.selcetInOutOrValueWhere(inDto);
                } else {
                    paramList = tVechicleProcurerMapper.selectInOutWhere(inDto);
                }
            } catch (DataAccessException e) {
                log.error("数据库查询异常", e);
                throw new RuntimeException("数据库查询异常");
            }
            //进场实际收费总金额
            BigDecimal tolInActAmt = paramList.stream().filter(p -> p.getInOutTp().equals("0")).map(QuryEntranceCheckParamOutDto::getInActAmt).filter(getInActAmt -> Objects.nonNull(getInActAmt)).reduce(BigDecimal.ZERO, BigDecimal::add);
            //出场实际收费总金额
            BigDecimal tolOutActAmt = paramList.stream().filter(p -> p.getInOutTp().equals("1")).map(QuryEntranceCheckParamOutDto::getActRecevAmt).filter(getActRecevAmt -> Objects.nonNull(getActRecevAmt)).reduce(BigDecimal.ZERO, BigDecimal::add);

            log.info("进出场业务-查询业务人员办理进出场数据成功");
            outBody.setList(paramList);
            outBody.setTolInActAmt(tolInActAmt);
            outBody.setTolOutActAmt(tolOutActAmt);
            return Result.build(outBody, SUCCESS);
        }else{//查询详情
            try {
                    tolNum = tVechicleProcurerMapper.selectInOutCount(inDto);
            } catch (DataAccessException e) {
                log.error("数据库查询异常", e);
                throw new RuntimeException("数据库查询异常");
            }
            outBody.setTolPageNum(tolNum);//总条数
            if (tolNum <= 0) {
                log.info("进出场业务-查询记录数为0");
                return Result.build(outBody, SUCCESS);
            }
            List<QuryEntranceCheckParamOutDto> paramList = null;
            try {
                paramList = tVechicleProcurerMapper.selectInOutByInid(inDto);
            } catch (DataAccessException e) {
                log.error("数据库查询异常", e);
                throw new RuntimeException("数据库查询异常");
            }
            log.info("进出场业务-查询业务人员办理进出场数据成功");
            outBody.setList(paramList);
            outBody.setTolInActAmt(paramList.get(0).getInActAmt());
            outBody.setTolOutActAmt(paramList.get(0).getActRecevAmt());
            return Result.build(outBody, SUCCESS);
        }
    }

}
