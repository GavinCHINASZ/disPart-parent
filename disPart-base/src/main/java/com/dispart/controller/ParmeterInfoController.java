package com.dispart.controller;

import com.dispart.dto.parmeterdto.ParmeterInfoDto;
import com.dispart.dto.parmeterdto.ParmeterSelectInVo;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.ParmeterInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/securityCenter")
public class ParmeterInfoController {

    @Resource
    private ParmeterInfoService service;

    @PostMapping("/DISP20210044")
    public Result selectParmeterInfo(@RequestBody Request<ParmeterSelectInVo> parmeterInfoKeys){
        return service.selectByPrimaryKey(parmeterInfoKeys.getBody());
    }

    @PostMapping("/DISP20210045")
    public Result initSecret(){
        return service.secretkeyInit();
    }

    @PostMapping("/DISP20210046")
    public Result<Object> updateParmeterInfo(@RequestBody Request<ParmeterInfoDto> inVo){
        return service.updateByPrimaryKey(inVo.getBody());
    }
}
