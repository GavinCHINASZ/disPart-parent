package com.dispart.feign;

import com.alibaba.fastjson.JSONObject;
import com.dispart.config.FeignConfig;
import com.dispart.request.Request;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "disPart-order",configuration = FeignConfig.class)
public interface DISP20210261Feign {
    @PostMapping("/securityCenter/DISP20210261")
    JSONObject DISP20210261(@RequestBody Request request);
}
