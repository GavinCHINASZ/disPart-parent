package com.dispart.config;

import com.dispart.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.dispart.result.ResultCodeEnum.SERVICE_ERROR;

@Configuration
@ControllerAdvice
@Slf4j
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody

    public Result error(Exception e) {
        log.error("全局异常，服务出现异常", e);
        return Result.build(SERVICE_ERROR.getCode(), e.getMessage());
    }
}
