package com.dispart.controller;

import com.dispart.dto.ResultOutDto;
import com.dispart.dto.customdto.*;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.CustomManagerService;
import com.dispart.vo.user.TCustomBankcardVo;
import com.dispart.vo.user.TCustomInfoManagerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhongfei
 * @version 1.0.0:
 * @title CustomManagerController
 * @Description 客户信息管理
 * @dateTime 2021/8/9 15:41
 * @Copyright 2020-2021
 */
@RestController
@Api(tags = "客户信息管理")
@RequestMapping("/securityCenter")
@Slf4j
@CrossOrigin
public class CustomManagerController {

    @Autowired
    CustomManagerService customManagerService;

    @PostMapping("/DISP20210167")
    @ApiOperation(value = "新增客户信息")
    public Result<ResultOutDto> addCustomInfo(@RequestBody Request<AddCustomInfoInDto> param){
        return customManagerService.addCustomInfo(param);
    }

    @PostMapping("/DISP20210170")
    @ApiOperation(value = "修改客户信息")
    public Result<ResultOutDto> updateCustomInfo(@RequestBody Request<UpdateCustomInfoInDto> param) {
        return customManagerService.updateCustomInfo(param);
    }

    @PostMapping("/DISP20210171")
    @ApiOperation(value = "禁用客户")
    public Result<ResultOutDto> disableCustomInfo(@RequestBody Request<DisableCustomInfoInDto> param) {
        return customManagerService.disableCustomInfo(param);
    }

    @PostMapping("/DISP20210172")
    @ApiOperation(value = "下载溯源码")
    public Result<DownLoadqrcodeOutDto> downLoadqrcod(@RequestBody Request<DownLoadqrcodeInDto> param) {
        return customManagerService.downLoadqrcod(param);
    }

    @PostMapping("/DISP20210168")
    @ApiOperation(value = "查询客户信息")
    public Result<QuryCustomInfoOutDto> quryCustomInfo(@RequestHeader  String userNo,@RequestBody Request<QuryCustomInfoInDto> param) {
        return customManagerService.quryCustomInfo(userNo,param);
    }

    @PostMapping("/DISP20210169")
    @ApiOperation(value = "导出客户信息")
    public Result<ExportCustomInfoOutDto> exportCustomInfo(@RequestBody Request<ExportCustomInfoInDto> param) {
        return customManagerService.exportCustomInfo(param);
    }

    @PostMapping("/DISP20210289")
    @ApiOperation(value = "查询客户详情")
    public Result<QuryCustomInfoOutParamDto> quryCustomInfoDatils(@RequestBody Request<ExportCustomInfoInParamDto> param) {
        return customManagerService.quryCustomInfoDatils(param);
    }

    @PostMapping("/DISP20210337")
    @ApiOperation(value = "设置客户是否允许提现")
    public Result<ResultOutDto> setIsWithdraw(@RequestBody Request<DisableCustomInfoInDto> param) {
        return customManagerService.setIsWithdraw(param);
    }

    /**
     * 常用银行信息查询
     *
     * @author 黄贵川
     * @date 2021/08/18
     * @return TCommonBankNameVo 银行常用列表
     */
    @PostMapping("/DISP20210295")
    @ApiOperation(value = "常用银行信息查询")
    public Result<QueryTCommonBanNameListDto> selectTCommonBankNameVoList(@RequestBody Request<Object> request) {
        return customManagerService.selectTCommonBankNameVoList();
    }

    /**
     * 客户卡片绑定银行卡
     *
     * @author 黄贵川
     * @date 2021/08/11
     * @param param TCustomBankcardVo
     * @return ResultOutDto
     */
    @PostMapping("/DISP20210203")
    @ApiOperation(value = "客户卡片绑定银行卡")
    public Result<ResultOutDto> addCustomBankcard(@RequestBody Request<TCustomBankcardVo> param) {
        return customManagerService.addCustomBankcard(param);
    }

    /**
     * 客户已绑定银行卡查询
     *
     * @author 黄贵川
     * @date 2021/08/25
     * @param param TCustomBankcardVo
     * @return ResultOutDto
     */
    @PostMapping("/DISP20210306")
    @ApiOperation(value = "客户已绑定银行卡查询")
    public Result findCustomBankcardList(@RequestBody Request<TCustomBankcardVo> param) {
        return customManagerService.findCustomBankcardList(param);
    }

    /**
     * 客户卡片解绑银行卡
     *
     * @author 黄贵川
     * @date 2021/08/11
     * @param param TCustomBankcardVo
     * @return ResultOutDto
     */
    @PostMapping("/DISP20210204")
    @ApiOperation(value = "客户卡片解绑银行卡")
    public Result<ResultOutDto> deleteCustomBankcard(@RequestBody Request<TCustomBankcardVo> param) {
        return customManagerService.deleteCustomBankcard(param);
    }

    /**
     * 会员身份证信息实名认证
     *
     * @author 黄贵川
     * @date 2021/08/11
     * @param param TCustomInfoManagerVo
     * @return ResultOutDto
     */
    @PostMapping("/DISP20210252")
    @ApiOperation(value = "会员身份证信息实名认证")
    public Result<ResultOutDto> realNameTCustomInfoManager(@RequestBody Request<TCustomInfoManagerVo> param) {
        return customManagerService.realNameTCustomInfoManager(param);
    }

    /**
     * 会员信息补充
     *
     * @author 黄贵川
     * @date 2021/08/17
     * @param param 客户信息管理
     * @return TCustomInfoManagerVo 客户信息管理
     */
    @PostMapping("/DISP20210253")
    @ApiOperation(value = "会员信息补充")
    public Result<ResultOutDto> updateTCustomInfoManagerVo(@RequestBody Request<TCustomInfoManagerVo> param) {
        return customManagerService.updateTCustomInfoManagerVo(param);
    }
}
