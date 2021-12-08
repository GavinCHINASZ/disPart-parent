package com.dispart.controller;

import com.dispart.dto.customdto.ExportCustomInfoOutDto;
import com.dispart.dto.entrance.D_0297FindDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.SubsidInfoIoService;
import com.dispart.vo.entrance.TSubsidInfo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 补贴申请导出
 *
 * @author 黄贵川
 * @date 2021/11/11
 */
@RestController
@RequestMapping("/securityCenter")
@Api(tags = "补贴申请导出")
@Slf4j
@CrossOrigin
public class SubsidInfoIoController {
    @Autowired
    SubsidInfoIoService subsidInfoIoService;

    /**
     * 供应商补贴导出
     *
     * @author 黄贵川
     * @date 2021/11/11
     * @param inDto Request<D_0297FindDto>
     * @return Result<ExportCustomInfoOutDto>
     */
    @PostMapping("/DISP20210367")
    public Result<ExportCustomInfoOutDto> supplySubsidInfoIoMethod(@RequestBody Request<D_0297FindDto> inDto){
        return subsidInfoIoService.supplySubsidInfoIoMethod(inDto);
    }

    /**
     * 采购商补贴导出
     *
     * @author 黄贵川
     * @date 2021/11/11
     * @param inDto Request<TSubsidInfo>
     * @return Result<ExportCustomInfoOutDto>
     */
    @PostMapping("/DISP20210368")
    public Result<ExportCustomInfoOutDto> pruchaseSubsidInfoIoMethod(@RequestBody Request<D_0297FindDto> inDto){
        return subsidInfoIoService.pruchaseSubsidInfoIoMethod(inDto);
    }

    /**
     * 补贴申请查询导出
     *
     * @author 黄贵川
     * @date 2021/11/11
     * @param inDto Request<TSubsidInfo>
     * @return Result<ExportCustomInfoOutDto>
     */
    @PostMapping("/DISP20210369")
    public Result<ExportCustomInfoOutDto> subsidInfoIoMethod(@RequestBody Request<TSubsidInfo> inDto){
        return subsidInfoIoService.subsidInfoIoMethod(inDto);
    }
}
