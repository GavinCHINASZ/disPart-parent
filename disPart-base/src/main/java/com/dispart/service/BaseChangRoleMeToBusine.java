package com.dispart.service;

import com.dispart.dto.menudto.DISP20210038RoleUpMenuInDto;
import com.dispart.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name="disPart-busine-commons")
public interface BaseChangRoleMeToBusine {
    @GetMapping("/push/users/auth/role")
    Result busineCommonsRole(@RequestParam("roleId") Map<String,String> roleId);
    @GetMapping("/push/users/auth/org")
    Result busineCommonsOrg(@RequestParam("depId") Map<String,String> depId);
}
