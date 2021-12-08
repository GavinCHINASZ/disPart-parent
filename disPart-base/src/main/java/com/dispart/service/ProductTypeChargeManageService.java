package com.dispart.service;

import com.dispart.model.base.ProductTypeChargeManage;
import com.dispart.result.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductTypeChargeManageService {
    /**
     * 品种进出场收费标准写入
     * @param proTypeChgeManage
     * @return
     */
    Result insertProductTypeCharge(ProductTypeChargeManage proTypeChgeManage);
    /**
     * 品种进出场收费标准修改
     * @param proTypeChgeManage
     * @param proTypeChgeManage
     * @return
     */
    Result updateProductTypeChargeByVarietyNo(ProductTypeChargeManage proTypeChgeManage);
    /**
     * 品种进出场收费标准查询
     * @param varietyNo
     * @param productNm
     * @return
     */
    Result findProductTypeChargeByVarietyNoOrProductNm(String varietyNo,String productNm);
    /**
     * 品种进出场收费标准删除
     * @param varietyNo
     * @return
     */
    Result deleteProductTypeChargeByVarietyNo(String varietyNo);
    Result selectAll(ProductTypeChargeManage proTypeChgeManage);
    Result selectAllTest(ProductTypeChargeManage proTypeChgeManage);
    Result selectAllTestByLevel(ProductTypeChargeManage proTypeChgeManage);
    Result selectbynm(ProductTypeChargeManage proTypeChgeManage);
}
