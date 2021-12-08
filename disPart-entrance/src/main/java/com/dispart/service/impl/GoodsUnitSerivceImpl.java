package com.dispart.service.impl;

import com.dispart.dao.mapper.TGoodsUnitMapper;
import com.dispart.dao.mapper.TGoodsUnitRelevanceMapper;
import com.dispart.dto.ResultOutDto;
import com.dispart.dto.entrance.TGoodsUnitInDto;
import com.dispart.dto.entrance.TGoodsUnitOutDto;
import com.dispart.dto.entrance.TGoodsUnitParamOutDto;
import com.dispart.dto.entrance.TGoodsUnitRelevanceDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.GoodsUnitSerivce;
import com.dispart.vo.entrance.TGoodsUnit;
import com.dispart.vo.entrance.TGoodsUnitRelevance;
import com.sun.net.httpserver.Authenticator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.dispart.result.ResultCodeEnum.SUCCESS;

@Service
@Slf4j
public class GoodsUnitSerivceImpl implements GoodsUnitSerivce {
    @Resource
    TGoodsUnitMapper tGoodsUnitMapper;
    @Resource
    TGoodsUnitRelevanceMapper tGoodsUnitRelevanceMapper;
    @Override
    public Result<TGoodsUnitOutDto> quryGoodsUnitInfo(Request<TGoodsUnitInDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(1, "输入参数为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(1, "输入参数为空");
        }
        if (StringUtils.isEmpty(param.getHead().getChanlNo())){
            log.info("货物管理业务-渠道号为空");
            return Result.build(1, "渠道号为空");
        }
        TGoodsUnitInDto inDto = param.getBody();
        if (StringUtils.isEmpty(inDto.getPrdctId())) {
            log.info("货物管理业务-商品类型id为空");
            return Result.build(1, "商品类型id为空");
        }
        if (StringUtils.isEmpty(inDto.getProvId())) {
            log.info("货物管理业务-客户id为空");
            return Result.build(1, "客户id为空");
        }
        List<TGoodsUnitParamOutDto> outDto =null;
        try {
            outDto =tGoodsUnitMapper.selectByProvIdPrDctIdOrAndUnit(inDto);
        } catch (Exception e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if(!"04".equals(param.getHead().getChanlNo())&&!"05".equals(param.getHead().getChanlNo())){
            if(ObjectUtils.isEmpty(outDto)||outDto.size()<=0){
                outDto = new ArrayList<>();
            }
            TGoodsUnitParamOutDto paramOutDto = new TGoodsUnitParamOutDto();
            paramOutDto.setPrdctId(inDto.getPrdctId());
            paramOutDto.setProvId(inDto.getProvId());
            paramOutDto.setUnit("千克");//kg
            TGoodsUnitRelevanceDto tGoodsUnitRelevanceDto = new TGoodsUnitRelevanceDto();
            tGoodsUnitRelevanceDto.setWeight(new BigDecimal(1));
            List<TGoodsUnitRelevanceDto>  relevances = new ArrayList<>();
            relevances.add(tGoodsUnitRelevanceDto);
            paramOutDto.setChildList(relevances);
            outDto.add(0,paramOutDto);
        }

        if(ObjectUtils.isEmpty(outDto)||outDto.size()<=0){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }
        log.info("货物管理业务-商品计量单位查询成功"+outDto.get(0).getUnit());
        TGoodsUnitOutDto outBody = new TGoodsUnitOutDto();
        outBody.setList(outDto);
        return Result.build(outBody, ResultCodeEnum.SUCCESS);
    }

    @Override
    public Result<ResultOutDto> setGoodUnit(Request<TGoodsUnitInDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(1, "输入参数为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(1, "输入参数为空");
        }
        TGoodsUnitInDto inDto = param.getBody();
        if (StringUtils.isEmpty(inDto.getPrdctId())) {
            log.info("货物管理业务-商品id为空");
            return Result.build(1, "商品类型id为空");
        }
        if (StringUtils.isEmpty(inDto.getProvId())) {
            log.info("货物管理业务-客户id为空");
            return Result.build(1, "客户id为空");
        }
        if (StringUtils.isEmpty(inDto.getUnit())) {
            log.info("货物管理业务-单位为空");
            return Result.build(1, "单位为空");
        }
        TGoodsUnitParamOutDto paramOutDto = null;
        try {
            paramOutDto =tGoodsUnitMapper.selectByProvIdPrDctIdUnit(inDto);
        } catch (Exception e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if(!ObjectUtils.isEmpty(paramOutDto)){
            return Result.build(1, "已存在相同单位配置，请确认");
        }
        TGoodsUnit tGoodsUnit = new TGoodsUnit();
        tGoodsUnit.setCreatTime(new Date());
        tGoodsUnit.setUpTime(new Date());
        tGoodsUnit.setPrdctId(inDto.getPrdctId());
        tGoodsUnit.setProvId(inDto.getProvId());
        tGoodsUnit.setRemark(inDto.getRemark());
        tGoodsUnit.setUnit(inDto.getUnit());
        int result =0;
        try {
            result =tGoodsUnitMapper.insert(tGoodsUnit);
        } catch (Exception e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if(result!=1){
            throw new RuntimeException("商品计量单位设置失败");
        }
        log.info("货物管理业务-商品计量单位设置成功");
        ResultOutDto resDto = new ResultOutDto();
        resDto.setResult(ResultOutDto.SUCCESS);
        return Result.build(resDto, SUCCESS);
    }

    @Override
    public Result<ResultOutDto> setGoodUnitRelevance(Request<TGoodsUnitInDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(1, "输入参数为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(1, "输入参数为空");
        }
        TGoodsUnitInDto inDto = param.getBody();
        if (StringUtils.isEmpty(inDto.getUnitId())) {
            log.info("货物管理业务-配置id为空");
            return Result.build(1, "商品类型id为空");
        }

        if (StringUtils.isEmpty(inDto.getWeight())) {
            log.info("货物管理业务-对应重量为空");
            return Result.build(1, "对应重量为空");
        }
        TGoodsUnitParamOutDto paramOutDto = null;
        try {
            paramOutDto =tGoodsUnitMapper.selectByPrimaryKey(param.getBody().getUnitId());
        } catch (Exception e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if(ObjectUtils.isEmpty(paramOutDto)){
            return Result.build(1, "未查询到商品计量单位配置");
        }
        TGoodsUnitRelevance tGoodsUnitRelevance = new TGoodsUnitRelevance();
        tGoodsUnitRelevance.setWeight(param.getBody().getWeight());
        tGoodsUnitRelevance.setUnitId(paramOutDto.getUnitId());
        tGoodsUnitRelevance.setCreatTime(new Date());
        tGoodsUnitRelevance.setUpTime(new Date());
        tGoodsUnitRelevance.setRemark(param.getBody().getRemark());
        int count =0;
        try {
            count =tGoodsUnitRelevanceMapper.selectByUnitIdAndWeightCount(tGoodsUnitRelevance);
        } catch (Exception e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if(count>=1){
            return Result.build(1, "已存在相同单位规格设置,请确认");
        }
        int result = 0;
        try {
            result =tGoodsUnitRelevanceMapper.insert(tGoodsUnitRelevance);
        } catch (Exception e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }

        if(result != 1){
            return Result.build(1, "商品计量单位规格设置失败");
        }
        log.info("货物管理业务-商品计量单位规格设置成功");
        ResultOutDto resDto = new ResultOutDto();
        resDto.setResult(ResultOutDto.SUCCESS);
        return Result.build(resDto, SUCCESS);
    }

    @Override
    public Result<ResultOutDto> deleteGoodUnitRelevance(Request<TGoodsUnitInDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(1, "输入参数为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(1, "输入参数为空");
        }
        TGoodsUnitInDto inDto = param.getBody();
        if (StringUtils.isEmpty(inDto.getUnitId())) {
            log.info("货物管理业务-配置id为空");
            return Result.build(1, "商品类型id为空");
        }

        if (StringUtils.isEmpty(inDto.getWeight())) {
            log.info("货物管理业务-对应重量为空");
            return Result.build(1, "对应重量为空");
        }
        int result = 0;
        try {
            result =tGoodsUnitRelevanceMapper.deleteByUnitIdAndWeight(inDto);
        } catch (Exception e) {
            log.error("数据库删除异常", e);
            throw new RuntimeException("数据库删除异常");
        }
        log.info("货物管理业务-商品计量单位规格删除成功");
        ResultOutDto resDto = new ResultOutDto();
        resDto.setResult(ResultOutDto.SUCCESS);
        return Result.build(resDto, SUCCESS);
    }

    @Override
    @Transactional
    public Result<ResultOutDto> deleteGoodUnit(Request<TGoodsUnitInDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(1, "输入参数为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(1, "输入参数为空");
        }
        TGoodsUnitInDto inDto = param.getBody();
        if (StringUtils.isEmpty(inDto.getUnitId())) {
            log.info("货物管理业务-配置id为空");
            return Result.build(1, "商品类型id为空");
        }
        inDto.setWeight(null);
        int result =0;
        try {
            result =tGoodsUnitMapper.deleteByPrimaryKey(param.getBody().getUnitId());
        } catch (Exception e) {
            log.error("数据库删除异常", e);
            throw new RuntimeException("数据库删除异常");
        }

        result = 0;
        try {
            result =tGoodsUnitRelevanceMapper.deleteByUnitIdAndWeight(inDto);
        } catch (Exception e) {
            log.error("数据库删除异常", e);
            throw new RuntimeException("数据库删除异常");
        }

        log.info("货物管理业务-商品计量单位删除成功");
        ResultOutDto resDto = new ResultOutDto();
        resDto.setResult(ResultOutDto.SUCCESS);
        return Result.build(resDto, SUCCESS);    }
}
