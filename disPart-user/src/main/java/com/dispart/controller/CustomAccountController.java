package com.dispart.controller;

import com.dispart.dto.customdto.ExportCustomInfoOutDto;
import com.dispart.parmeterdto.AccnoChangeDetailDto;
import com.dispart.parmeterdto.DISP20210181MemberShipInfoInDto;
import com.dispart.parmeterdto.DISP20210184CusAccountDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.CustomAccountService;
import com.dispart.service.MemberShipInfoService;
import com.dispart.service.impl.DISP20210364ServiceImpl;
import com.dispart.vo.AccnoInfoDetailVo;
import com.dispart.vo.AccnoInfoVo;
import com.dispart.vo.DISP20210364RespVo;
import com.dispart.vo.user.MemberShipInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author:xiejin
 * @date:Created in 2021/8/9 16:53
 * @description
 * @modified by:
 * @version: 1.0
 */
@RestController
@Api(tags = "客户账查询")
@RequestMapping("/securityCenter")
@Slf4j
@CrossOrigin
public class CustomAccountController {
    @Autowired
    CustomAccountService customAccountService;
    @Autowired
    MemberShipInfoService memberShipInfoService;
    @Resource
    DISP20210364ServiceImpl disp20210364Service;

    @PostMapping("/DISP20210181")
    @ApiOperation(value = "开户")
    public Result openAcountByParam(@RequestBody Request<DISP20210181MemberShipInfoInDto> findDepByParam) {
        return memberShipInfoService.openAccountInfo(findDepByParam.getBody(),
                findDepByParam.getHead().getOperator(), findDepByParam.getHead().getChanlNo());
    }

    @PostMapping("/DISP20210182")
    @ApiOperation(value = "销户")
    public Result cancelllationAccountByParam(@RequestBody Request<DISP20210181MemberShipInfoInDto> findDepByParam) {
        return memberShipInfoService.cancelllationAccount(findDepByParam.getBody(),
                findDepByParam.getHead().getOperator(), findDepByParam.getHead().getChanlNo());
    }

    @PostMapping("/DISP20210183")
    @ApiOperation(value = "客户账查询")
    public Result customAccountByParam(@RequestBody Request<DISP20210184CusAccountDto> findDepByParam) {
        return customAccountService.quryCustomAccount(findDepByParam.getBody());
    }

    @PostMapping("/DISP20210184")
    @ApiOperation(value = "客户账详情查询")
    public Result customAccountDetailByParam(@RequestBody Request<DISP20210184CusAccountDto> findDepByParam) {
        return customAccountService.queryCustomAccountDetail(findDepByParam.getBody());
    }

    @PostMapping("/DISP20210185")
    @ApiOperation(value = "冻结卡金额")
    public Result frozenAccountByParam(@RequestBody Request<DISP20210181MemberShipInfoInDto> findDepByParam) {
        return memberShipInfoService.frozenAccount(findDepByParam.getBody(),
                findDepByParam.getHead().getOperator(), findDepByParam.getHead().getChanlNo());
    }

    @PostMapping("/DISP20210186")
    @ApiOperation(value = "解冻卡金额")
    public Result unfreezeAccountByParam(@RequestBody Request<DISP20210181MemberShipInfoInDto> findDepByParam) {
        return memberShipInfoService.unfreezeAccount(findDepByParam.getBody(),
                findDepByParam.getHead().getOperator(), findDepByParam.getHead().getChanlNo());
    }

    @PostMapping("/DISP20210187")
    @ApiOperation(value = "调账申请")
    public Result applyReconciliationByParam(@RequestBody Request<AccnoInfoDetailVo> findDepByParam) {
        return memberShipInfoService.applyReconciliationByParam(findDepByParam.getBody(),
                findDepByParam.getHead().getOperator(), findDepByParam.getHead().getChanlNo());
    }

    @PostMapping("/DISP20210188")
    @ApiOperation(value = "调账申请查询")
    public Result queryReconciliationByParm(@RequestBody Request<AccnoInfoDetailVo> findDepByParam) {
        return memberShipInfoService.queryReconciliationByParm(findDepByParam.getBody());
    }

    @PostMapping("/DISP20210189")
    @ApiOperation(value = "调账申请处理")
    public Result handleApplyReconcliByParam(@RequestBody Request<AccnoInfoDetailVo> findDepByParam) {
        return memberShipInfoService.handleApplyReconcliByParam(findDepByParam.getBody(),
                findDepByParam.getHead().getOperator(), findDepByParam.getHead().getChanlNo());
    }

    @PostMapping("/DISP20210190")
    @ApiOperation(value = "提现申请")
    public Result applyWithdrawByParam(@RequestBody Request<AccnoInfoDetailVo> findDepByParam) {
        return memberShipInfoService.applyWithdrawByParam(findDepByParam.getBody(),
                findDepByParam.getHead().getOperator(), findDepByParam.getHead().getChanlNo());
    }

    @PostMapping("/DISP20210191")
    @ApiOperation(value = "提现查询")
    public Result queryWithdrawByParam(@RequestBody Request<AccnoInfoDetailVo> findDepByParam) {
        return memberShipInfoService.queryWithdrawByParam(findDepByParam.getBody());
    }

    @PostMapping("/DISP20210192")
    @ApiOperation(value = "提现复核")
    public Result reviewAndHandleWithdrawByParam(@RequestBody Request<AccnoInfoDetailVo> findDepByParam) {
        return memberShipInfoService.reviewAndHandleWithdrawByParam(findDepByParam.getBody());
    }

    @PostMapping("/DISP20210193")
    @ApiOperation(value = "提现转账")
    public Result transferyParam(@RequestBody Request<AccnoInfoDetailVo> findDepByParam) {
        return memberShipInfoService.transferyParam(findDepByParam.getBody());
    }

    @PostMapping("/DISP20210197")
    @ApiOperation(value = "客户卡片查询")
    public Result queryCardByParam(@RequestBody Request<DISP20210181MemberShipInfoInDto> findDepByParam) {
        return memberShipInfoService.queryCardListDetail(findDepByParam.getBody());
    }

    @PostMapping("/DISP20210198")
    @ApiOperation(value = "挂失卡片")
    public Result reportLossCardByParam(@RequestBody Request<DISP20210181MemberShipInfoInDto> findDepByParam) {
        return memberShipInfoService.reportLossCard(findDepByParam.getBody(),
                findDepByParam.getHead().getOperator(), findDepByParam.getHead().getChanlNo());
    }

    @PostMapping("/DISP20210199")
    @ApiOperation(value = "解挂")
    public Result untieCardByParam(@RequestBody Request<DISP20210181MemberShipInfoInDto> findDepByParam) {
        return memberShipInfoService.untieCard(findDepByParam.getBody(), findDepByParam.getHead().getOperator(), findDepByParam.getHead().getChanlNo());
    }

    @PostMapping("/DISP20210200")
    @ApiOperation(value = "补卡换卡")
    public Result reChangeCardByParam(@RequestBody Request<DISP20210181MemberShipInfoInDto> findDepByParam) {
        return memberShipInfoService.reChangeCard(findDepByParam.getBody(),
                findDepByParam.getHead().getOperator(), findDepByParam.getHead().getChanlNo());
    }

    @PostMapping("/DISP20210201")
    @ApiOperation(value = "密码修改")
    public Result modifyCardPassByParam(@RequestBody Request<DISP20210181MemberShipInfoInDto> findDepByParam) {
        return memberShipInfoService.modifyCardPass(findDepByParam.getBody(), findDepByParam.getHead().getOperator(), findDepByParam.getHead().getChanlNo());
    }

    @PostMapping("/DISP20210202")
    @ApiOperation(value = "密码重置")
    public Result resetCardByParam(@RequestBody Request<DISP20210181MemberShipInfoInDto> findDepByParam) {
        return memberShipInfoService.resetCard(findDepByParam.getBody(), findDepByParam.getHead().getOperator(), findDepByParam.getHead().getChanlNo());
    }

    /**
     * 一卡通密码校验
     *
     * @param param
     * @return Result
     * @author 黄贵川
     */
    @PostMapping("/DISP20210314")
    @ApiOperation(value = "一卡通密码校验")
    public Result memberShipInfoPasswdCheck(@RequestBody Request<MemberShipInfoVo> param) {
        return memberShipInfoService.memberShipInfoPasswdCheck(param);
    }

    /**
     * 提现校验
     *
     * @param param
     * @return Result
     * @author 黄贵川
     * @date 2021-09-18
     */
    @PostMapping("/DISP20210339")
    @ApiOperation(value = "提现校验")
    public Result withdrawCheck(@RequestBody Request<AccnoInfoVo> param) {
        return memberShipInfoService.withdrawCheck(param);
    }


    /**
     * 账户明晰查询
     */
    @PostMapping("/DISP20210364")
    public Result<DISP20210364RespVo> queryAccnoDetails(@RequestBody Request<AccnoChangeDetailDto> request) {

        return disp20210364Service.queryAccnoDetails(request.getBody());
    }

    /**
     * 账户明晰查询 导出表格
     *
     * @date 2021/11/12
     */
    @PostMapping("/DISP20210376")
    public Result<ExportCustomInfoOutDto> queryAccnoDetailsIo(@RequestBody Request<AccnoChangeDetailDto> request) {
        return disp20210364Service.queryAccnoDetailsIo(request);
    }
}
