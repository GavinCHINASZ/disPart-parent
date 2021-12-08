package com.dispart.service.impl;

import com.dispart.dao.mapper.PayItemManageMapper;
import com.dispart.dto.departmentdto.DISP20210019DepFindByParamInDto;
import com.dispart.model.base.PageInfo;
import com.dispart.model.base.PayItemAndPageInfo;
import com.dispart.model.base.PayItemManage;
import com.dispart.model.base.PayItemSub;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeBaseEnum;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.PayItemManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class PayItemManageServiceImpl implements PayItemManageService {
    @Resource
    PayItemManageMapper payItemManageMapper;
    /**
     * 缴费项目数据写入
     * @param payItemManage
     * @return
     */
    @Override
    public Result insertPayItem(PayItemManage payItemManage) {
        log.info("开始插入数据====");
        List<PayItemManage> payItemManageIfExits  = payItemManageMapper.selectIfExitByPayId(payItemManage.getPayId());
        for(PayItemManage payItemManageIfExit:payItemManageIfExits){
            if(payItemManageIfExit.getDepId().equals(payItemManage.getDepId())){
                return Result.build(200,"已存在当前记录，插入失败");
            }
        }
            payItemManage.setDepNm(payItemManageMapper.selectDepInfo(payItemManage.getDepId()).getDepNm());
            payItemManage.setCreatTime(new Date());
            payItemManage.setUpTime(new Date());
            List<PayItemSub> payItemSubs = payItemManageMapper.selectPayItemSub1(payItemManage.getPayId());
            for(PayItemSub p:payItemSubs){
               payItemManage.setPayItem(p.getPayItem());
               break;
           }
            Integer row = payItemManageMapper.insertPayItem(payItemManage);
            if (row != null) {
                log.info("缴费项目数据成功写入!");
                return Result.build(ResultCodeEnum.SUCCESS.getCode(), "缴费项目数据成功写入!");
            } else {
                log.error("缴费项目数据写入失败!");
                return Result.build(200,"缴费项目数据写入失败!");
            }
        }
    /**
     * 缴费项目数据修改
     * @param payItemManage
     * @return
     */
    @Override
    public Result updatePayItem(PayItemManage payItemManage) {
        log.info("开始修改数据====");
        payItemManage.setUpTime(new Date());
        Integer row = payItemManageMapper.updatePayItem(payItemManage);
        if(row!=null){
            log.info("缴费项目数据修改成功!");
            return Result.ok("缴费项目数据修改成功!");
        }
        else{
            log.error("缴费项目数据修改失败!");
            return Result.build(200,"缴费项目数据修改失败!");
        }
    }
    @Override
    public Result findPayItemByPayIdOrPayNm(PayItemManage payItemManage) {
        payItemManage.setTolPageNum(payItemManageMapper.selectPayItemCountByByPayIdOrPayNm(payItemManage.getPayId(), payItemManage.getPayItem()));
        if(payItemManage.getTolPageNum()!=null&&payItemManage.getPageNum()!=null&&payItemManage.getPageSize()!=null){
            //分页处理
            payItemManage.setPageNum((payItemManage.getPageNum()-1)*payItemManage.getPageSize());
        }
        List<PayItemManage> payItemList= payItemManageMapper.findPayItemByPayIdOrPayNm(payItemManage);
        PayItemAndPageInfo payItemAndPageInfo=new PayItemAndPageInfo();
        payItemAndPageInfo.setPayItemManage(payItemList);
        payItemAndPageInfo.setTolPageNum(payItemManageMapper.selectPayItemCountByByPayIdOrPayNm(payItemManage.getPayId(), payItemManage.getPayItem()));
        if (payItemList.size()>0){
            log.info("缴费项目数据查询成功!");
            return Result.ok(payItemAndPageInfo);
        }
        else{
            log.error("未查询到相关数据！");
            return Result.build(200,"未查询到相关数据！");
        }
    }
    /**
     * 缴费项目数据删除
     * @param payId
     * @return
     */
    @Override
    public Result deletePayItemByPayId(String payId) {
        log.info("开始打印===");
        log.info(payId);
        Integer row=payItemManageMapper.deletePayItemByPayId(payId);
        if(row!=0){
            log.info("缴费项目数据删除成功!");
            return Result.ok("缴费项目数据删除成功!");
        }
        else{
            log.error("缴费项目数据删除失败!");
            return Result.build(200,"缴费项目数据删除失败!");
        }
    }

    /**
     * 机构编号及名称查询
     * @return
     */
    @Override
    public Result selectDepInfo(DISP20210019DepFindByParamInDto depID) {
        DISP20210019DepFindByParamInDto depInfo = payItemManageMapper.selectDepInfo(depID.getDepId());
        if(depInfo!=null){
            return Result.ok(depInfo);
        }
        else{
            return Result.build(200,"未查询到相关数据!");
        }
    }

    @Override
    public Result selectPayItemSub() {
        List<PayItemSub> payItemSubs = payItemManageMapper.selectPayItemSub();
        if(payItemSubs.size()!=0){
            return Result.ok(payItemSubs);
        }
        else{
            return Result.build(200,"未查询到相关数据！");
        }
    }
}
