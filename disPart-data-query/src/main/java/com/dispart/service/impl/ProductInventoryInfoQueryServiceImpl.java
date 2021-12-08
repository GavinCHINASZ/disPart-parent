package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.dao.OrderQueryMapper;
import com.dispart.dao.ProductQueryMapper;
import com.dispart.dto.dataquery.Disp20210075InDto;
import com.dispart.dto.dataquery.Disp20210075OutDto;
import com.dispart.dto.dataquery.Disp20210075OutMx;
import com.dispart.entity.DatabaseCount;
import com.dispart.model.ProductTypeInfo;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.ProductInventoryInfoQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class ProductInventoryInfoQueryServiceImpl implements ProductInventoryInfoQueryService {


    @Resource
    ProductQueryMapper productQueryMapper;

    @Resource
    OrderQueryMapper orderQueryMapper;


    @Override
    public Result<Disp20210075OutDto> quryProductInventory(Disp20210075InDto param) {

        log.info("货品信息查询开始执行，传入参数：{}", JSON.toJSONString(param));
        Result<Disp20210075OutDto> result = null;

        if (ObjectUtils.isEmpty(param)) {
            result = Result.build(null, ResultCodeEnum.PARAM_ERROR);
            return result;
        }

        if (StringUtils.isEmpty(param.getCurPage()) || StringUtils.isEmpty(param.getPageSize())) {
            result = Result.build(null, ResultCodeEnum.PARAM_ERROR);
            log.info("分页参数不能为空");
            return result;
        }

        //若按一级货品ID作为查询条件,生成查询条件集
        if(StringUtils.isEmpty(param.getPrdctTypeId())){
            //查询二级分类
            List<String> infos;
            try{
               infos = orderQueryMapper.queryDownLevelPrdctType(param.getPrdctTypeId());
            }catch (Exception e){
                log.error("查询分类异常");
                throw new RuntimeException(e);
            }
            if (infos.size()>0){
                param.setPrdctTypeIdList(infos);
            }
        }

        param.setStartIndex((param.getCurPage()-1) * param.getPageSize());
        DatabaseCount databaseCount = null;
        try{
            databaseCount = productQueryMapper.quryProductInventory_count(param);
        }catch (Exception e){
            log.error("获取货品信息总条数异常");
            throw new RuntimeException(e);
        }

        Disp20210075OutDto outDto = new Disp20210075OutDto();
        outDto.setRecNum(databaseCount.getCountRec().longValue());
        outDto.setTotalAmt(databaseCount.getTolAmt());
        if (databaseCount.getCountRec().longValue() < 1) {
            log.info("货品信息查询执行成功，返回:{}",JSON.toJSONString(outDto));
            return Result.build(outDto, ResultCodeEnum.SUCCESS);
        }
        ArrayList<Disp20210075OutMx> outList = null;
        try {
            outList = productQueryMapper.quryProductInventory(param);
        }catch (Exception e){
            log.error("获取货品信息异常");
            throw new RuntimeException(e);
        }
        outDto.setList(outList);
        result = Result.build(outDto, ResultCodeEnum.SUCCESS);
        log.info("货品信息查询执行成功，返回:{}",JSON.toJSONString(outDto));
        return result;

    }
}
