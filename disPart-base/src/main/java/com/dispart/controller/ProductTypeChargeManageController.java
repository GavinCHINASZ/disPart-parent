package com.dispart.controller;

import com.dispart.model.base.ProductTypeChargeManage;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.ProductTypeChargeManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/securityCenter")
public class ProductTypeChargeManageController {
    @Autowired
    ProductTypeChargeManageService productTypeChargeManageService;

    /**
     * 品种进出场收费标准写入
     * @param proTypeChgeManage
     * @return
     */
    @PostMapping("/DISP20210155")
    public Result insertProductTypeCharge(@RequestBody Request<ProductTypeChargeManage> proTypeChgeManage){
        return productTypeChargeManageService.insertProductTypeCharge(proTypeChgeManage.getBody());
    }

    /**
     * 品种进出场收费标准修改
     * @param proTypeChgeManage
     * @param proTypeChgeManage
     * @return
     */
    @PostMapping("/DISP20210157")
    public Result updateProductTypeChargeByVarietyNo(@RequestBody Request<ProductTypeChargeManage> proTypeChgeManage){
        return productTypeChargeManageService.updateProductTypeChargeByVarietyNo(proTypeChgeManage.getBody());
    }

//    /**
//     * 品种进出场收费标准查询
//     * @param proTypeChgeManage
//     * @return
//     */
//    @PostMapping("/DISP20210156")
//    public Result findProductTypeChargeByVarietyNoOrProductNm(@RequestBody Request<ProductTypeChargeManage> proTypeChgeManage){
//        return productTypeChargeManageService.findProductTypeChargeByVarietyNoOrProductNm(proTypeChgeManage.getBody().getVarietyNo(),proTypeChgeManage.getBody().getPrdctNm());
//    }
    /**
     * 品种进出场收费标准删除
     * @param proTypeChgeManage
     * @return
     */
    @PostMapping("/DISP20210158")
    public Result deleteProductTypeChargeByVarietyNo(@RequestBody Request<ProductTypeChargeManage> proTypeChgeManage){
        return productTypeChargeManageService.deleteProductTypeChargeByVarietyNo(proTypeChgeManage.getBody().getVarietyNo());
    }
/*    @PostMapping("/DISP20210156")
    public Result selectAll(@RequestBody Request<ProductTypeChargeManage> proTypeChgeManage){
        return productTypeChargeManageService.selectAll(proTypeChgeManage.getBody());
    }*/

    /**
     * 品种进出场收费标准全部查询
     * @param proTypeChgeManage
     * @return
     */
    @PostMapping("/DISP20210156")
    public Result selectAllTest(@RequestBody Request<ProductTypeChargeManage> proTypeChgeManage){
       return productTypeChargeManageService.selectAllTest(proTypeChgeManage.getBody());
    }
    @PostMapping("/DISP20210305")
    public Result selectAllTestByLevel(@RequestBody Request<ProductTypeChargeManage> proTypeChgeManage){
        return productTypeChargeManageService.selectAllTestByLevel(proTypeChgeManage.getBody());
    }
    @PostMapping("/DISP20210317")
    public Result selectbynm(@RequestBody Request<ProductTypeChargeManage> proTypeChgeManage){
        return productTypeChargeManageService.selectbynm(proTypeChgeManage.getBody());
    }
}
