package com.dispart.controller;

import com.dispart.dto.customdto.ExportCustomInfoOutDto;
import com.dispart.dto.entrance.*;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.SubsidInfoService;
import com.dispart.vo.entrance.TSubsidInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 补贴申请
 *
 * @author 黄贵川
 * @date 2021/08/23
 */
@RestController
@RequestMapping("/securityCenter")
@Api(tags = "补贴申请")
@Slf4j
@CrossOrigin
public class SubsidInfoController {
    @Autowired
    SubsidInfoService subsidInfoService;

    /**
     * 补贴申请--供应商/采购商进出场信息查询
     *
     * @param record TSubsidInfo
     * @return Result
     * @author 黄贵川
     * @date 2021/08/24
     */
    @PostMapping("DISP20210297")
    @ApiOperation(value = "供应商/采购商进出场信息查询")
    public Result findEntranceMessage(@RequestBody Request<D_0297FindDto> record) {
        return subsidInfoService.findEntranceMessage(record);
    }

    /**
     * 补贴申请--新增
     *
     * @param record TSubsidInfo
     * @return Result
     * @author 黄贵川
     * @date 2021/08/23
     */
    @PostMapping("DISP20210298")
    @ApiOperation(value = "补贴申请--新增")
    public Result addSubsidInfo(@RequestBody Request<TSubsidInfo> record) {
        return subsidInfoService.addSubsidInfo(record);
    }

    /**
     * 补贴申请--查询
     *
     * @param record TSubsidInfo
     * @return Result
     * @author 黄贵川
     * @date 2021/08/23
     */
    @PostMapping("DISP20210299")
    @ApiOperation(value = "补贴申请--查询")
    public Result selectSubsidInfo(@RequestBody Request<TSubsidInfo> record) {
        return subsidInfoService.selectSubsidInfo(record.getBody());
    }

    /**
     * 补贴申请--修改
     *
     * @param record TSubsidInfo
     * @return Result
     * @author 黄贵川
     * @date 2021/08/23
     */
    @PostMapping("DISP20210300")
    @ApiOperation(value = "补贴申请--修改")
    public Result updateSubsidInfo(@RequestBody Request<TSubsidInfo> record) {
        return subsidInfoService.updateSubsidInfo(record.getBody());
    }

    /**
     * 补贴申请--作废
     *
     * @param record TSubsidInfo
     * @return Result
     * @author 黄贵川
     * @date 2021/08/24
     */
    @PostMapping("DISP20210301")
    @ApiOperation(value = "补贴申请--作废")
    public Result deleteSubsidInfo(@RequestBody Request<TSubsidInfo> record) {
        return subsidInfoService.deleteSubsidInfo(record.getBody());
    }

    /**
     * 补贴申请--发放
     *
     * @param record TSubsidInfo
     * @return Result
     * @author 黄贵川
     * @date 2021/08/24
     */
    @PostMapping("DISP20210302")
    @ApiOperation(value = "补贴申请--发放")
    public Result grantSubsidInfo(@RequestBody Request<TSubsidInfo> record) {
        return subsidInfoService.grantSubsidInfo(record);
    }

    /**
     * 补贴申请--撤销
     *
     * @param record TSubsidInfo
     * @return Result
     * @author 黄贵川
     * @date 2021/08/24
     */
    @PostMapping("DISP20210303")
    @ApiOperation(value = "补贴申请--撤销")
    public Result cancelSubsidInfo(@RequestBody Request<TSubsidInfo> record) {
        return subsidInfoService.cancelSubsidInfo(record);
    }

    /**
     * 查询品类
     *
     * @param record TSubsidInfo
     * @return Result
     * @author 黄贵川
     * @date 2021/10/25
     */
    @PostMapping("DISP20210350")
    @ApiOperation(value = "查询品类")
    public Result findProductTypeInfo(@RequestBody Request<TSubsidInfo> record) {
        return subsidInfoService.findProductTypeInfo(record);
    }

    /**
     * 进出场查询导出
     *
     * @author 黄贵川
     * @date 2021/10/28
     * @param inDto Request<D_0297FindDto>
     */
    @PostMapping("/DISP20210351")
    public Result<ExportCustomInfoOutDto> getExcel(@RequestBody Request<D_0297FindDto> inDto){
        return subsidInfoService.getExcel(inDto);
    }
}
