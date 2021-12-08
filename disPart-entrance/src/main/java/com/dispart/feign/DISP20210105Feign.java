package com.dispart.feign;

import com.alibaba.fastjson.JSONObject;
import com.dispart.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "disPart-files",configuration = FeignConfig.class)
public interface DISP20210105Feign {
    @PostMapping("/securityCenter/DISP20210105")
    JSONObject DISP20210105(@RequestBody MultiValueMap<String, HttpEntity<?>> multiValueBody);
}
