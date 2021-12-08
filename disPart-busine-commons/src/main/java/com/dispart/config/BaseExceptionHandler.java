package com.dispart.config;

import com.dispart.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import static com.dispart.result.BusineConmonCodeEnum.SYS_ERROR;

@ControllerAdvice
@Slf4j
public class BaseExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Result error(BusinessException e){
        return Result.build(SYS_ERROR.getCode(),e.getMessage());
    }
}
