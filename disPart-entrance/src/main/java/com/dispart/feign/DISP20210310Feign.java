package com.dispart.feign;

import com.alibaba.fastjson.JSONObject;
import com.dispart.config.FeignConfig;
import com.dispart.request.Request;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "disPart-busine-commons",configuration = FeignConfig.class)
public interface DISP20210310Feign {
    @PostMapping("/securityCenter/DISP20210310")
    JSONObject DISP20210310(@RequestBody Request request);
}
