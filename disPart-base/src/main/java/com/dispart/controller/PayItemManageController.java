package com.dispart.controller;

import com.dispart.dto.departmentdto.DISP20210019DepFindByParamInDto;
import com.dispart.model.base.PayItemManage;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.PayItemManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/securityCenter")
public class PayItemManageController {
    @Autowired
    PayItemManageService payItemManageService;

    /**
     * 缴费项目数据写入
     * @param payItemManage
     * @return
     */
    @PostMapping("DISP20210150")
    public Result insertPayItem(@RequestBody Request<PayItemManage> payItemManage){
        return payItemManageService.insertPayItem(payItemManage.getBody());
    }
    /**
     * 缴费项目数据查询
     * @param payItemManage
     * @return
     */
    @PostMapping("/DISP20210151")
    public Result findPayItemByPayIdOrPayNm(@RequestBody Request<PayItemManage> payItemManage){
        return payItemManageService.findPayItemByPayIdOrPayNm(payItemManage.getBody());
    }
    /**
     * 缴费项目数据修改
     * @param payItemManage
     * @param payItemManage
     * @return
     */
    @PostMapping("/DISP20210152")
    public Result updatePayItem(@RequestBody Request<PayItemManage> payItemManage){
        return Result.build(ResultCodeEnum.FAIL.getCode(), "不支持该功能");
        //return payItemManageService.updatePayItem(payItemManage.getBody());
    }

    /**
     * 缴费项目数据删除
     * @param  payItemManage
     * @return
     */
    @PostMapping("/DISP20210153")
    public Result deletePayItemByPayId(@RequestBody Request<PayItemManage> payItemManage)
    {
        return payItemManageService.deletePayItemByPayId(payItemManage.getBody().getPayId());
    }
    /**
     * 机构编号及名称查询
     * @return
     */
    @PostMapping("/DISP20210286")
    public Result selectDepInfo(@RequestBody Request<DISP20210019DepFindByParamInDto> depID){
        return payItemManageService.selectDepInfo(depID.getBody());
    }
    @PostMapping("/DISP20210288")
    public Result selectPayItemSub(@RequestBody Request request){
        return payItemManageService.selectPayItemSub();
    }
}
