package com.dispart.service.impl;

import com.dispart.dao.mapper.ProductTypeChargeManageMapper;
import com.dispart.dto.menudto.DISP20210031SeMenuOutDto;
import com.dispart.dto.producttypedto.QueryPrdctTypeListDto;
import com.dispart.model.base.OrgIdAndDepId;
import com.dispart.model.base.ProductTypeChargeManage;
import com.dispart.result.Result;
import com.dispart.service.ProductTypeChargeManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.*;

@Service
@Slf4j
public class ProductTypeChargeManageServiceImpl implements ProductTypeChargeManageService {
    @Resource
    ProductTypeChargeManageMapper productTypeChargeManageMapper;

    /**
     * 品种进出场收费标准写入
     *
     * @param proTypeChgeManage
     * @return
     */
    @Override
    public Result insertProductTypeCharge(ProductTypeChargeManage proTypeChgeManage) {
        try {
            if(proTypeChgeManage.getPrdctNm()!=null&&proTypeChgeManage.getPrdctNm()!=""){
                proTypeChgeManage.setCreatTime(new Date());
                proTypeChgeManage.setUpTime(new Date());
                proTypeChgeManage.setVarietyNo(String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16)).substring(0, 4));
                if(proTypeChgeManage.getRate()!=null){
                    proTypeChgeManage.setRate(Double.parseDouble(proTypeChgeManage.getRate().toString()));
                }
                if(proTypeChgeManage.getPrice()>=0){
                    proTypeChgeManage.setPrice(Double.parseDouble(proTypeChgeManage.getPrice().toString()));
                }
                else{
                    return Result.fail("费率不能小于0!");
                }
                if(Integer.parseInt(proTypeChgeManage.getLevel())>1){
                    List<OrgIdAndDepId> orgIdAndDepIds = productTypeChargeManageMapper.selectPreOrgIdAndDepId(proTypeChgeManage.getParentPrdtId());
                    for(OrgIdAndDepId orgIdAndDepId:orgIdAndDepIds){
                        proTypeChgeManage.setDepId(orgIdAndDepId.getDepId());
                        proTypeChgeManage.setSubOrg(orgIdAndDepId.getSubOrg());
                        break;
                    }
                }
                Integer row = productTypeChargeManageMapper.insertProductTypeCharge(proTypeChgeManage);
                if (row != null) {
                    log.info("品种进出场收费标准成功写入!");
                    return Result.ok("品种进出场收费标准成功写入!");
                } else {
                    log.error("品种进出场收费标准写入失败!");
                    return Result.build(200,"品种进出场收费标准写入失败!");
                }
            }
            else
            {
                return Result.build(200,"品种进出场收费标准信息不能为空!");
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
      return null;
    }

    /**
     * 品种进出场收费标准修改
     *
     * @param proTypeChgeManage
     * @param proTypeChgeManage
     * @return
     */
    @Override
    public Result updateProductTypeChargeByVarietyNo(ProductTypeChargeManage proTypeChgeManage) {
        log.info("开始打印====="+proTypeChgeManage.getLevel());
        proTypeChgeManage.setUpTime(new Date());
        if(proTypeChgeManage.getRate()!=null){
            proTypeChgeManage.setRate(Double.parseDouble(proTypeChgeManage.getRate().toString()));
        }
        if(proTypeChgeManage.getPrice()!=null){
            proTypeChgeManage.setPrice(Double.parseDouble(proTypeChgeManage.getPrice().toString()));
        }
        if(Integer.parseInt(proTypeChgeManage.getLevel())>1){
            List<OrgIdAndDepId> orgIdAndDepIds = productTypeChargeManageMapper.selectPreOrgIdAndDepId(proTypeChgeManage.getParentPrdt());
            for(OrgIdAndDepId orgIdAndDepId:orgIdAndDepIds){
                proTypeChgeManage.setDepId(orgIdAndDepId.getDepId());
                proTypeChgeManage.setSubOrg(orgIdAndDepId.getSubOrg());
            }
        }
        else{
            List<ProductTypeChargeManage> productTypeChargeManages = productTypeChargeManageMapper.selectVnoByPId(proTypeChgeManage.getVarietyNo());
            for(ProductTypeChargeManage productTypeChargeManagea:productTypeChargeManages){
               productTypeChargeManageMapper.updateSubDepIdAndOrgId(productTypeChargeManagea.getVarietyNo(), proTypeChgeManage);
                List<ProductTypeChargeManage> productTypeChargeManagesub = productTypeChargeManageMapper.selectVnoByPId(productTypeChargeManagea.getVarietyNo());
                for(ProductTypeChargeManage productTypeChargeManagesc:productTypeChargeManagesub){
                    productTypeChargeManageMapper.updateSubDepIdAndOrgId(productTypeChargeManagesc.getVarietyNo(), proTypeChgeManage);
                }
           }
        }
        Integer row = productTypeChargeManageMapper.updateProductTypeChargeByVarietyNo(proTypeChgeManage.getVarietyNo(), proTypeChgeManage);
        if (row != null) {
            log.info("品种进出场收费标准修改成功!");
            return Result.ok("品种进出场收费标准修改成功!");
        } else {
            log.error("品种进出场收费标准修改失败!");
            return Result.build(200,"品种进出场收费标准修改失败!");
        }
    }

    /**
     * 品种进出场收费标准查询
     *
     * @param varietyNo
     * @param productNm
     * @return
     */
    @Override
    public Result findProductTypeChargeByVarietyNoOrProductNm(String varietyNo, String productNm) {
        List<ProductTypeChargeManage> productTypeChargeByVarietyNoOrProductNm = productTypeChargeManageMapper.findProductTypeChargeByVarietyNoOrProductNm(varietyNo, productNm);

        if (productTypeChargeByVarietyNoOrProductNm.size() != 0) {
            log.info("品种进出场收费标准查询成功!");
            return Result.ok(productTypeChargeByVarietyNoOrProductNm);
        } else {
            List<ProductTypeChargeManage> productTypeChargeByProductNm = productTypeChargeManageMapper.findProductTypeChargeByProductNm(varietyNo, productNm);
            if (productTypeChargeByProductNm != null) {
                log.info("品种进出场收费标准查询成功!");
                return Result.ok(productTypeChargeByProductNm);
            } else {
                log.error("品种进出场收费标准查询失败!");
                return Result.build(200,"未查询到相关数据！");
            }

        }
    }

    /**
     * 品种进出场收费标准删除
     *
     * @param varietyNo
     * @return
     */
    @Override
    public Result deleteProductTypeChargeByVarietyNo(String varietyNo) {
        Integer row = productTypeChargeManageMapper.deleteProductTypeChargeByVarietyNo(varietyNo);
        if (row != null) {
            log.info("品种进出场收费标准删除成功!");
            return Result.ok("品种进出场收费标准删除成功!");
        } else {
            log.error("品种进出场收费标准删除失败!");
            return Result.build(200,"品种进出场收费标准删除失败!");
        }
    }

    @Override
    public Result selectAll(ProductTypeChargeManage proTypeChgeManage) {
        List<ProductTypeChargeManage> productTypeChargeManages = productTypeChargeManageMapper.selectAll(proTypeChgeManage);
        if (productTypeChargeManages.size() > 0) {
            return Result.ok(productTypeChargeManages);
        } else {
            return Result.build(200,"未查询到相关数据！");
        }
    }

    @Override
    public Result selectAllTest(ProductTypeChargeManage proTypeChgeManage) {
        List<ProductTypeChargeManage> productTypeChargeManages = productTypeChargeManageMapper.selectAllTestByLevel(proTypeChgeManage);
        List<ProductTypeChargeManage> treeListHalf1 = new ArrayList<>();
        List<ProductTypeChargeManage> productTypeChargeManageListT=new ArrayList<>();
        List<ProductTypeChargeManage> productTypeChargeManageListTemp=new ArrayList<>();
        List<ProductTypeChargeManage> productTypeChargeManagesuba1=new ArrayList<>();
        List<ProductTypeChargeManage> productTypeChargeManages1 = productTypeChargeManageMapper.selectVnoByNm(proTypeChgeManage.getPrdctNm());
        for(ProductTypeChargeManage pro:productTypeChargeManages1){
            proTypeChgeManage.setVarietyNo(pro.getVarietyNo());
        }
        List<ProductTypeChargeManage> productTypeChargeManagesuba = productTypeChargeManageMapper.selectPreVno(proTypeChgeManage.getVarietyNo());
        //productTypeChargeManagesuba.addAll(productTypeChargeManagesuba1);
/*        for(ProductTypeChargeManage product:productTypeChargeManagesuba){
            proTypeChgeManage.setVarietyNo(product.getVarietyNo());
        }*/
        //将结果集转换为可嵌套的实体类
        List<ProductTypeChargeManage> productTypeChargeManageList = new ArrayList<>();
        for(ProductTypeChargeManage productTypeChargeManage:productTypeChargeManages){
            productTypeChargeManageList.add(productTypeChargeManage);
        }
        List<ProductTypeChargeManage> treeListHalf=new ArrayList<>();
        List<ProductTypeChargeManage> treeList= getTreeList(productTypeChargeManageList,proTypeChgeManage,productTypeChargeManagesuba);
        //log.info("hello=========");
        if(proTypeChgeManage!=null){
            if(proTypeChgeManage.getVarietyNo()!=null&&proTypeChgeManage.getVarietyNo()!="")
            {

                if(treeList!=null){
                    for(ProductTypeChargeManage productTypeChargeManage:treeList){
                        if (productTypeChargeManage != null) {
                            if(Objects.equals(productTypeChargeManage.getJudeLogic(),1)){
                                treeListHalf.add(productTypeChargeManage);
                                return Result.ok(treeListHalf);
                            }

                            if(Objects.equals(productTypeChargeManage.getJudeLogic(),2)){

                                //treeListHalf1.add(productTypeChargeManage);
                                for(ProductTypeChargeManage productTypeChargeManage2:productTypeChargeManage.getProductTypeChargeManageList()){
                                    List<ProductTypeChargeManage> productTypeChargeManageList2 = productTypeChargeManage2.getProductTypeChargeManageList();
                                    for(ProductTypeChargeManage productTypeChargeManage3:productTypeChargeManageList2){
                                        //List<ProductTypeChargeManage> productTypeChargeManageList2 = productTypeChargeManage2.getProductTypeChargeManageList();
                                        if(productTypeChargeManage3.getVarietyNo().equals(proTypeChgeManage.getVarietyNo())){
                                            //productTypeChargeManage3.setProductTypeChargeManageList(null);
                                            //productTypeChargeManageList2.clear();
                                            productTypeChargeManage2.setProductTypeChargeManageList(null);
                                            productTypeChargeManageListTemp.add(productTypeChargeManage3);
                                            productTypeChargeManage2.setProductTypeChargeManageList(productTypeChargeManageListTemp);
                                            //productTypeChargeManage2.setProductTypeChargeManageList(productTypeChargeManageList2);
                                            //productTypeChargeManageList1.clear();
                                            productTypeChargeManageListT.add(productTypeChargeManage2);
                                            break;
                                        }
                                    }
                                }
                                productTypeChargeManage.setProductTypeChargeManageList(null);
                                productTypeChargeManage.setProductTypeChargeManageList(productTypeChargeManageListT);
                                treeList.clear();
                                treeList.add(productTypeChargeManage);
                                return Result.ok(treeList);
                            }
                        }
                    }
                }
            }
        }
        if (treeList.size() > 0) {
            return Result.ok(treeList);
        } else {
            return Result.build(200,"未查询到相关数据！");
        }
    }
    private static List<ProductTypeChargeManage> getTreeList(List<ProductTypeChargeManage> entityList,ProductTypeChargeManage proTypeChgeManage,List<ProductTypeChargeManage> productTypeChargeManagesuba){
        List<ProductTypeChargeManage> productTypeChargeManagesL=new ArrayList<>();
        //根据特征找出所有跟节点
            for(ProductTypeChargeManage typeChargeManage:entityList){
                //log.info("开始打印==="+typeChargeManage.getParentPrdtId());
                if(typeChargeManage!=null){
                    if(typeChargeManage.getParentPrdtId()!=null)
                    {
                        if(Objects.equals(typeChargeManage.getParentPrdtId(),"000000000")&&(typeChargeManage.getLevel().equals("1"))){
                            if(!Objects.equals(proTypeChgeManage.getVarietyNo(),typeChargeManage.getVarietyNo())){
                                productTypeChargeManagesL.add(typeChargeManage);
                            }
                            //在一级被找到时直接返回
                            else{
                                List<ProductTypeChargeManage> productTypeChargeManagesHalf=new ArrayList<>();
                                productTypeChargeManagesHalf.add(typeChargeManage);
                                return  productTypeChargeManagesHalf;
                            }
                        }
                    }
                }
            }

        //获取每个顶层元素的子数据集合
        try {
             //一级展开
            for(ProductTypeChargeManage typeChargeManage1:productTypeChargeManagesL){
                typeChargeManage1.setProductTypeChargeManageList(getSubList(typeChargeManage1.getVarietyNo(), entityList,proTypeChgeManage,typeChargeManage1,productTypeChargeManagesuba));
                //判断该数据已在2级树枝被找到
                if(getSubList(typeChargeManage1.getVarietyNo(), entityList,proTypeChgeManage,typeChargeManage1,productTypeChargeManagesuba)!=null){
                    if(typeChargeManage1!=null)
                    {
                        if(Objects.equals(proTypeChgeManage.getJudeLogic(),1)){
                            typeChargeManage1.setJudeLogic(1);
                            break;
                        }
                    }
                }
               if(typeChargeManage1.getProductTypeChargeManageList()!=null){
                    for(ProductTypeChargeManage typeChargeManage2:typeChargeManage1.getProductTypeChargeManageList()){
                            if(Objects.equals(typeChargeManage2.getVarietyNo(),proTypeChgeManage.getVarietyNo())){
                                return productTypeChargeManagesL;
                        }
                    }
                }

            }
        }
        catch (Exception e){
            //throw new RuntimeException("查询异常");
            e.printStackTrace();
        }
        return productTypeChargeManagesL;
    }
    //嵌套获取子元素树
    private static List<ProductTypeChargeManage> getSubList(String VarietyNo, List<ProductTypeChargeManage> entityList,ProductTypeChargeManage proTypeChgeManage,ProductTypeChargeManage typeChargeManage1,List<ProductTypeChargeManage> productTypeChargeManagesuba){

        List<ProductTypeChargeManage> childList = new ArrayList<>();
        try{

                for(ProductTypeChargeManage child:entityList){
                    //判断是否在二级找到所需数据，为真时直接返回childListHalf
                    if(Objects.equals(proTypeChgeManage.getVarietyNo(),child.getVarietyNo())&&child.getParentPrdtId().equals(typeChargeManage1.getVarietyNo())){
                        List<ProductTypeChargeManage> childListHalf = new ArrayList<>();
                        childListHalf.add(child);
                        proTypeChgeManage.setJudeLogic(1);
                        return childListHalf;
                    }
                    else{
                        if(Objects.equals(child.getParentPrdtId(),VarietyNo)){
                            //往子节点添加数据（查找3级时2级所有数据都会在这里全部添加进去）
                            childList.add(child);
                        }
                    }
                }

            if(childList.size()==0){
                return null;
            }
                //二级展开
                for(ProductTypeChargeManage child1:childList){
                    child1.setProductTypeChargeManageList(getSubList(child1.getVarietyNo(),entityList,proTypeChgeManage,typeChargeManage1,productTypeChargeManagesuba));
                    if(proTypeChgeManage.getVarietyNo()!=null&&proTypeChgeManage.getVarietyNo()!=""){
                        for(ProductTypeChargeManage productTypeChargeManage:productTypeChargeManagesuba){
                            if(Objects.equals(productTypeChargeManage.getParentPrdtId(),child1.getVarietyNo()))
                            {
                                typeChargeManage1.setJudeLogic(2);
                                return childList;
                            }
                        }
                    }

                }

        }
        catch (Exception e){
            /*throw new RuntimeException("查询错误");*/
            e.printStackTrace();
        }
        return childList;
    }


    @Override
    public Result selectAllTestByLevel(ProductTypeChargeManage proTypeChgeManage) {
        List<ProductTypeChargeManage> productTypeChargeManages = productTypeChargeManageMapper.selectAllTestByLevel(proTypeChgeManage);
        List<ProductTypeChargeManage> treeListHalf1 = new ArrayList<>();
        List<ProductTypeChargeManage> productTypeChargeManagesuba1=new ArrayList<>();
        List<ProductTypeChargeManage> productTypeChargeManageListT=new ArrayList<>();
        List<ProductTypeChargeManage> productTypeChargeManageListTemp=new ArrayList<>();
        List<ProductTypeChargeManage> productTypeChargeManages1 = productTypeChargeManageMapper.selectVnoByNm(proTypeChgeManage.getPrdctNm());
        for(ProductTypeChargeManage pro:productTypeChargeManages1){
            proTypeChgeManage.setVarietyNo(pro.getVarietyNo());
        }
        List<ProductTypeChargeManage> productTypeChargeManagesuba = productTypeChargeManageMapper.selectPreVno(proTypeChgeManage.getVarietyNo());
        //productTypeChargeManagesuba.addAll(productTypeChargeManagesuba1);
/*        for(ProductTypeChargeManage product:productTypeChargeManagesuba){
            proTypeChgeManage.setVarietyNo(product.getVarietyNo());
        }*/
        //将结果集转换为可嵌套的实体类
        List<ProductTypeChargeManage> productTypeChargeManageList = new ArrayList<>();
        for(ProductTypeChargeManage productTypeChargeManage:productTypeChargeManages){
            productTypeChargeManageList.add(productTypeChargeManage);
        }
        List<ProductTypeChargeManage> treeListHalf=new ArrayList<>();
        List<ProductTypeChargeManage> treeList= getTreeList1(productTypeChargeManageList,proTypeChgeManage.getSearchLevel(),proTypeChgeManage,productTypeChargeManagesuba);
        //log.info("hello=========");
        if(proTypeChgeManage!=null){
            if(proTypeChgeManage.getVarietyNo()!=null&&proTypeChgeManage.getVarietyNo()!="")
            {

                if(treeList!=null){
                    for(ProductTypeChargeManage productTypeChargeManage:treeList){
                        if (productTypeChargeManage != null) {
                            if(Objects.equals(productTypeChargeManage.getJudeLogic(),1)){
                                treeListHalf.add(productTypeChargeManage);
                                return Result.ok(treeListHalf);
                            }

                            if(Objects.equals(productTypeChargeManage.getJudeLogic(),2)){

                                //treeListHalf1.add(productTypeChargeManage);
                                for(ProductTypeChargeManage productTypeChargeManage2:productTypeChargeManage.getProductTypeChargeManageList()){
                                    List<ProductTypeChargeManage> productTypeChargeManageList2 = productTypeChargeManage2.getProductTypeChargeManageList();
                                    for(ProductTypeChargeManage productTypeChargeManage3:productTypeChargeManageList2){
                                        //List<ProductTypeChargeManage> productTypeChargeManageList2 = productTypeChargeManage2.getProductTypeChargeManageList();
                                            if(productTypeChargeManage3.getVarietyNo().equals(proTypeChgeManage.getVarietyNo())){
                                                //productTypeChargeManage3.setProductTypeChargeManageList(null);
                                                //productTypeChargeManageList2.clear();
                                                productTypeChargeManage2.setProductTypeChargeManageList(null);
                                                productTypeChargeManageListTemp.add(productTypeChargeManage3);
                                                productTypeChargeManage2.setProductTypeChargeManageList(productTypeChargeManageListTemp);
                                                //productTypeChargeManage2.setProductTypeChargeManageList(productTypeChargeManageList2);
                                                //productTypeChargeManageList1.clear();
                                                productTypeChargeManageListT.add(productTypeChargeManage2);
                                                break;
                                            }
                                    }
                                }
                                productTypeChargeManage.setProductTypeChargeManageList(null);
                                productTypeChargeManage.setProductTypeChargeManageList(productTypeChargeManageListT);
                                treeList.clear();
                                treeList.add(productTypeChargeManage);
                                return Result.ok(treeList);
                            }
                        }
                    }
                }
            }
        }
        if (treeList.size() > 0) {
            return Result.ok(treeList);
        } else {
            return Result.build(200,"未查询到相关数据！");
        }
    }
    private static List<ProductTypeChargeManage> getTreeList1(List<ProductTypeChargeManage> entityList,Integer level,ProductTypeChargeManage proTypeChgeManage,List<ProductTypeChargeManage> productTypeChargeManagesuba){
        List<ProductTypeChargeManage> productTypeChargeManagesL=new ArrayList<>();
        //根据特征找出所有跟节点
        if(level>=1){
            for(ProductTypeChargeManage typeChargeManage:entityList){
                //log.info("开始打印==="+typeChargeManage.getParentPrdtId());
                if(Objects.equals(typeChargeManage.getParentPrdtId(),"000000000")){
                    if(!Objects.equals(proTypeChgeManage.getVarietyNo(),typeChargeManage.getVarietyNo())){
                        productTypeChargeManagesL.add(typeChargeManage);
                    }
                    //在一级被找到时直接返回
                    else{
                        List<ProductTypeChargeManage> productTypeChargeManagesHalf=new ArrayList<>();
                        productTypeChargeManagesHalf.add(typeChargeManage);
                        return  productTypeChargeManagesHalf;
                    }
                }
            }
        }
        //获取每个顶层元素的子数据集合
        try {
            for(ProductTypeChargeManage typeChargeManage1:productTypeChargeManagesL){
                typeChargeManage1.setProductTypeChargeManageList(getSubList1(typeChargeManage1.getVarietyNo(), entityList,level,proTypeChgeManage,typeChargeManage1,productTypeChargeManagesuba));
               //判断返回的数据是否为一条数据，为真时表示该数据已在2级树枝被找到
                if(getSubList1(typeChargeManage1.getVarietyNo(), entityList,level,proTypeChgeManage,typeChargeManage1,productTypeChargeManagesuba)!=null){
                    if(typeChargeManage1!=null)
                    {
                        if(Objects.equals(proTypeChgeManage.getJudeLogic(),1)){
                            typeChargeManage1.setJudeLogic(1);
                            break;
                        }
                    }
                }
                if(typeChargeManage1.getProductTypeChargeManageList()!=null){
                    for(ProductTypeChargeManage typeChargeManage2:typeChargeManage1.getProductTypeChargeManageList()){
                            if(Objects.equals(typeChargeManage2.getVarietyNo(),proTypeChgeManage.getVarietyNo())){
                                return productTypeChargeManagesL;
                        }
                    }
                }

            }
        }
        catch (Exception e){
            //throw new RuntimeException("查询异常");
            e.printStackTrace();
        }
        return productTypeChargeManagesL;
    }
    //嵌套获取子元素树
    private static List<ProductTypeChargeManage> getSubList1(String VarietyNo, List<ProductTypeChargeManage> entityList,Integer level,ProductTypeChargeManage proTypeChgeManage,ProductTypeChargeManage typeChargeManage1,List<ProductTypeChargeManage> productTypeChargeManagesuba){
        Integer i=1;
        List<ProductTypeChargeManage> childListHalf = new ArrayList<>();
        List<ProductTypeChargeManage> childList = new ArrayList<>();
        try{
            if(level>=2){
                i++;
                for(ProductTypeChargeManage child:entityList){
                    if(Objects.equals(proTypeChgeManage.getVarietyNo(),child.getVarietyNo())&&child.getParentPrdtId().equals(typeChargeManage1.getVarietyNo())){
                        childListHalf.add(child);
                        proTypeChgeManage.setJudeLogic(1);
                        return childListHalf;
                    }
                    else{
                        if(Objects.equals(child.getParentPrdtId(),VarietyNo)){
                            childList.add(child);
                        }
                    }
                }
            }
            if(childList.size()==0){
                return null;
            }
            if(level>=3){
                for(ProductTypeChargeManage child1:childList){
                    child1.setProductTypeChargeManageList(getSubList1(child1.getVarietyNo(),entityList,level,proTypeChgeManage,typeChargeManage1,productTypeChargeManagesuba));
                    i++;
                    if(level<=i){
                        break;
                    }
                    if(proTypeChgeManage.getVarietyNo()!=null&&proTypeChgeManage.getVarietyNo()!=""&&proTypeChgeManage.getVarietyNo()!=""){
                        //productTypeChargeManagesuba的作用在于第三层查找时找到当前VarietyNo的父ParentPrdtId
                        for(ProductTypeChargeManage productTypeChargeManage:productTypeChargeManagesuba){
                            if(Objects.equals(productTypeChargeManage.getParentPrdtId(),child1.getVarietyNo()))
                            {
                                typeChargeManage1.setJudeLogic(2);
                                return childList;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e){
            //throw new RuntimeException("查询错误");
            e.printStackTrace();
        }
        return childList;
    }

    @Override
    public Result selectbynm(ProductTypeChargeManage proTypeChgeManage) {
        try{
            List<ProductTypeChargeManage> selectbynm = productTypeChargeManageMapper.selectbynm(proTypeChgeManage);
            if(selectbynm.size()!=0){
                return Result.ok(selectbynm);
            }
            else{
                return Result.build(200,"未查询到相关数据！");
            }
        }
       catch (Exception e){
            e.printStackTrace();
       }

        return null;
    }
}
