package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.baseDto.BaseSelectionOutDto;
import com.dispart.dao.mapper.TProductPriceInfoMapper;
import com.dispart.dto.prdctPriceDto.DISP20210311InDto;
import com.dispart.dto.prdctPriceDto.DISP20210312InDto;
import com.dispart.dto.prdctPriceDto.DISP20210356InDto;
import com.dispart.model.TProductPriceInfo;
import com.dispart.result.Result;
import com.dispart.service.ProductPriceService;
import com.sun.org.apache.xpath.internal.operations.String;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static com.dispart.enums.BaseEnum.*;

@Slf4j
@Service
public class ProductPriceServiceImpl implements ProductPriceService {

    @Resource
    private TProductPriceInfoMapper mapper;
    
    @Override
    public Result productPriceQuery(DISP20210311InDto inDto) {
        BaseSelectionOutDto<TProductPriceInfo> outDto = new BaseSelectionOutDto();
        if (inDto.getPageNum()!=null && inDto.getPageSize()!=null){
            inDto.setPageNum((inDto.getPageNum()-1)*inDto.getPageSize());
        }
        int tolPageNum = mapper.productPriceQueryCount(inDto);
        outDto.setTolPageNum(tolPageNum);
        if (tolPageNum == 0){
            return Result.ok(outDto);
        }
        ArrayList<TProductPriceInfo> dataList = mapper.productPriceQuery(inDto);
        outDto.setDataList(dataList);
        return Result.ok(outDto);
    }

    @Override
    public Result insertProductPrice(DISP20210312InDto inDto) {
        log.info("货品采集价格录入开始，传入参数：" + JSON.toJSONString(inDto));
        if (StringUtils.isEmpty(inDto.getDate())){
            return Result.build(PARAM_NULL.getCode(),"日期不能为空");
        }
        if (StringUtils.isEmpty(inDto.getMaxPrice())){
            return Result.build(PARAM_NULL.getCode(),"最高价不能为空");
        }
        if (StringUtils.isEmpty(inDto.getMidPrice())){
            return Result.build(PARAM_NULL.getCode(),"均价不能为空");
        }
        if (StringUtils.isEmpty(inDto.getMinPrice())){
            return Result.build(PARAM_NULL.getCode(),"最低价不能为空");
        }
        if (StringUtils.isEmpty(inDto.getPrdctNm())){
            return Result.build(PARAM_NULL.getCode(),"商品名不能为空");
        }
        if (StringUtils.isEmpty(inDto.getUnit())){
            return Result.build(PARAM_NULL.getCode(),"单位不能为空");
        }
        if (StringUtils.isEmpty(inDto.getOperId())){
            return Result.build(PARAM_NULL.getCode(),"操作员ID不能为空");
        }
        try{
            mapper.insertSelective(inDto);
        }catch (Exception e){
            log.error("数据库异常");
            throw new RuntimeException(e);
        }
        return Result.ok();
    }

    @Override
    public Result delProductPrice(DISP20210356InDto inDto) {
        log.info("数据采集价格删除开始，参数：" + JSON.toJSONString(inDto));
        if (StringUtils.isEmpty(inDto.getId())){
            return Result.build(PARAM_NULL.getCode(),"ID不能为空");
        }
        if (StringUtils.isEmpty(inDto.getOperId())){
            return Result.build(PARAM_NULL.getCode(),"操作员ID不能为空");
        }
        Integer i;
        try{
            i = mapper.deleteProductPrice(inDto);
        }catch (Exception e){
            log.error("数据库异常");
            throw new RuntimeException(e);
        }
        if (i>0){
            log.info("数据采集价格删除成功");
            return Result.ok();
        }else {
            log.info("数据采集价格删除失败，操作人不一致");
            return Result.build(307,"删除失败，操作人不一致");
        }
    }
}
