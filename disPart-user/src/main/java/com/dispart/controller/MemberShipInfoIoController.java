package com.dispart.controller;

import com.dispart.dto.customdto.ExportCustomInfoOutDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.MemberShipInfoIoService;
import com.dispart.vo.AccnoInfoDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 黄贵川
 * @date 2021/11/11
 */
@RestController
@RequestMapping("/securityCenter")
@Slf4j
@CrossOrigin
public class MemberShipInfoIoController {
    @Autowired
    MemberShipInfoIoService memberShipInfoIoService;

    /**
     * 调账申请查询导出
     *
     * @author 黄贵川
     * @date 2021/11/11
     * @param inDto Request<ExportCustomInfoOutDto>
     * @return Result<ExportCustomInfoOutDto>
     */
    @PostMapping("/DISP20210373")
    public Result<ExportCustomInfoOutDto> queryReconciliationByParmIo(@RequestBody Request<AccnoInfoDetailVo> inDto){
        return memberShipInfoIoService.queryReconciliationByParmIo(inDto);
    }

    /**
     * 调账申请查询导出
     *
     * @author 黄贵川
     * @date 2021/11/11
     * @param inDto Request<ExportCustomInfoOutDto>
     * @return Result<ExportCustomInfoOutDto>
     */
    @PostMapping("/DISP20210374")
    public Result<ExportCustomInfoOutDto> queryWithdrawByParamIo(@RequestBody Request<AccnoInfoDetailVo> inDto) {
        return memberShipInfoIoService.queryWithdrawByParamIo(inDto);
    }
}
