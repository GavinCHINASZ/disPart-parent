package com.dispart.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.dispart.dto.producttypedto.AddProductType;
import com.dispart.dto.producttypedto.QueryPrdctTypeListDto;
import com.dispart.enums.PrdctTypeResultCodeEnum;
import com.dispart.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.dispart.dao.mapper.ProductTypeInfoMapper;
import com.dispart.model.ProductTypeInfo;
import com.dispart.service.ProductTypeInfoService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhaoshihao
 * @version 1.0.0
 * @title ProductTypeInfoServiceImpl
 * @creat 2021/6/16 14:47
 * @Copyright 2020-2021
 */
@Service
@Slf4j
public class ProductTypeInfoServiceImpl implements ProductTypeInfoService {

    @Resource
    private ProductTypeInfoMapper productTypeInfoMapper;

    /**
     * 删除单条记录
     *
     * @author zhaoshihao
     * @date 2021/6/16
     */
    @Transactional
    @Override
    public Result deleteByPrimaryKey(String prdctTypeId) {
        log.info("开始删除商品种类");
        if (StringUtils.isEmpty(prdctTypeId)) {
            return Result.build(PrdctTypeResultCodeEnum.PARAM_NULL.getCode(), "商品类型ID不能为空");
        }
        try {
            deleteChildType(prdctTypeId);
        } catch (Exception e) {
            log.error("删除异常",e);
            throw new RuntimeException("系统错误，删除异常");
        }
        log.info("删除商品种类成功");
        return Result.ok();
    }

    private void deleteChildType(String prdctTypeId){
        List<ProductTypeInfo> childList = productTypeInfoMapper.findChildList(prdctTypeId);
        productTypeInfoMapper.deleteByPrimaryKey(prdctTypeId);
        for (ProductTypeInfo info : childList) {
            deleteChildType(info.getPrdctTypeId());
        }
    }


    /**
     * 新增单条数据
     *
     * @author zhaoshihao
     * @date 2021/6/16
     */
    @Override
    public Result insert(AddProductType addProductType) {
        log.info("开始插入商品种类，传入参数{}", JSON.toJSONString(addProductType));
        if (StringUtils.isEmpty(addProductType.getPrdctType())) {
            return Result.build(PrdctTypeResultCodeEnum.PARAM_NULL.getCode(), "商品类型不能为空");
        }
        ProductTypeInfo productType = new ProductTypeInfo();
        BeanUtil.copyProperties(addProductType, productType);
        productType.setUpdateDt(new Date());
        productType.setPrdctSt("");
        ProductTypeInfo productTypeInfo = productTypeInfoMapper.selectByTypeNm(addProductType.getPrdctType());
        if (productTypeInfo!=null){
            log.info("分类名重复");
            return Result.build(PrdctTypeResultCodeEnum.INSERT_CONFLICT.getCode(),PrdctTypeResultCodeEnum.INSERT_CONFLICT.getMessage());
        }
        String prdctTypeId;
        try {
            prdctTypeId = String.format("%07d", productTypeInfoMapper.getInsertPrdctTypeId("prdctTypeId"));
        } catch (Exception e) {
            log.error("获取prdctTypeId失败，请检查序列表是否含该记录",e);
            throw new RuntimeException("系统错误，生成主键失败");
        }
        productType.setPrdctTypeId(prdctTypeId);
        int i;
        try {
            i = productTypeInfoMapper.insert(productType);
        } catch (Exception e) {
            log.error("数据插入异常",e);
            throw new RuntimeException("系统错误，数据插入异常");
        }
        if(i<1){
            return Result.build(PrdctTypeResultCodeEnum.INSERT_FAIL.getCode(),PrdctTypeResultCodeEnum.INSERT_FAIL.getMessage());
        }
        log.info("商品种类插入成功");
        return Result.ok();
    }

    /**
     * 根据主键ID更新记录
     *
     * @author zhaoshihao
     * @date 2021/6/16
     */
    @Override
    public Result updateByPrimaryKey(ProductTypeInfo productTypeUpdate) {
        log.info("开始更新商品类型，传入参数：{}",JSON.toJSONString(productTypeUpdate));
        if (StringUtils.isEmpty(productTypeUpdate.getPrdctType())) {
            return Result.build(PrdctTypeResultCodeEnum.PARAM_NULL.getCode(), "商品类型名不能为空");
        }
        productTypeUpdate.setUpdateDt(new Date());

        int i;
        try {
            i = productTypeInfoMapper.updateByPrimaryKey(productTypeUpdate);
        } catch (Exception e) {
            log.error("更新数据库异常,主键冲突",e);
            return Result.build(PrdctTypeResultCodeEnum.UPDATE_CONFLICT.getCode(),PrdctTypeResultCodeEnum.UPDATE_CONFLICT.getMessage());
        }
        if(i<1){
            return Result.build(PrdctTypeResultCodeEnum.UPDATE_NULL.getCode(),PrdctTypeResultCodeEnum.UPDATE_NULL.getMessage());
        }
        log.info("更新商品类型成功");
        return Result.ok();
    }

//    /**
//     * 当传入productTypeId时，查询单条记录
//     * 当productTypeId为空时，查询所有记录，以树状结构列表返回
//     *
//     * @author zhaoshihao
//     * @date 2021/6/16
//     */
//    @Override
//    public Result findProductTypeInfo(String isTop) {
//        log.info("开始查询商品种类信息，传入参数:{}",JSON.toJSONString(isTop));
//        if (StringUtils.isEmpty(isTop)) {
//            log.info("传入参数isTop为空，开始查询所有记录");
//            List<ProductTypeInfo> productTypeInfoList;
//            try{
//                productTypeInfoList = productTypeInfoMapper.findList();
//            }catch (Exception e){
//                log.error("数据查询异常");
//                throw new RuntimeException("系统错误，数据查询异常");
//            }
//            //将结果集转换为可嵌套的实体
//            List<QueryPrdctTypeListDto> typeDtoList = new ArrayList<>();
//            for (ProductTypeInfo typeInfo : productTypeInfoList) {
//                QueryPrdctTypeListDto typeDto = new QueryPrdctTypeListDto();
//                typeDto.setPrdctTypeId(typeInfo.getPrdctTypeId());
//                typeDto.setPrdctType(typeInfo.getPrdctType());
//                typeDto.setParentTypeId(typeInfo.getParentTypeId());
//                typeDto.setRemark(typeInfo.getRemark());
//                typeDtoList.add(typeDto);
//            }
//            log.info("开始生成树状结构");
//            List<QueryPrdctTypeListDto> treeList = getTreeList("0", typeDtoList);
//            log.info("商品种类查询成功，返回数据:{}",JSON.toJSONString(treeList));
//            return Result.ok(treeList);
//        } else {
//            log.info("查询一级分类");
//            List<ProductTypeInfo> productTypeInfo;
//            try{
//                productTypeInfo = productTypeInfoMapper.selectTopType();
//            }catch (Exception e){
//                log.error("查询一级分类异常");
//                throw new RuntimeException("系统错误，数据查询异常");
//            }
//            log.info("查询一级分类查询成功，返回数据:{}",JSON.toJSONString(productTypeInfo));
//            return Result.ok(productTypeInfo);
//        }
//    }

    @Override
    public Result findProductTypeInfo(String isTop) {
        log.info("开始查询商品一级种类信息");
        List<ProductTypeInfo> list = productTypeInfoMapper.findTopPrdctType();
        log.info("查询商品一级种类信息结束，返回：" + JSON.toJSONString(list));
        return Result.ok(list);
    }


    /**
     * 生成树形数据
     * @param topId  树状结构第一层数据的Id
     * @param entityList  数据实体集合
     * @author zhaoshihao
     * @date 2021/6/12
     */
    private static List<QueryPrdctTypeListDto> getTreeList(String topId, List<QueryPrdctTypeListDto> entityList) {

        List<QueryPrdctTypeListDto> resultList = new ArrayList<>();
        //获取顶层元素集合
        log.info("开始生成树状顶层元素");
        String parentTypeId;
        for (QueryPrdctTypeListDto entity : entityList) {
            parentTypeId = entity.getParentTypeId();
            if (StringUtils.isEmpty(parentTypeId)== true || topId.equals(parentTypeId)) {
                resultList.add(entity);
            }
        }
        log.info("获取每个顶层元素的子数据集合");
        for (QueryPrdctTypeListDto entity : resultList) {
            entity.setChildTypeList(getSubList(entity.getPrdctTypeId(), entityList));
        }
        return resultList;
    }

    /**
     * 嵌套获取子元素树
     *
     * @author zhaoshihao
     * @date 2021/6/16
     */
    private static List<QueryPrdctTypeListDto> getSubList(String prdctTypeId, List<QueryPrdctTypeListDto> entityList) {
        List<QueryPrdctTypeListDto> childList = new ArrayList<>();
        String parentTypeId;
        for (QueryPrdctTypeListDto entity : entityList) {
            parentTypeId = entity.getParentTypeId();
            if (prdctTypeId.equals(parentTypeId)) {
                childList.add(entity);
            }
        }
        if (childList.size() == 0) {
            return null;
        }
        for (QueryPrdctTypeListDto entity : childList) {
            entity.setChildTypeList(getSubList(entity.getPrdctTypeId(), entityList));
        }
        return childList;
    }

}
