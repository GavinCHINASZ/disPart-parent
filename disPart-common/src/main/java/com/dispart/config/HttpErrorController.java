package com.dispart.config;

import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author:xts
 * @date:Created in 2021/6/16 4:13
 * @description 全局异常统一处理，避免暴露异常于前端页面
 * @modified by:
 * @version: 1.0
 */
@Slf4j
//@RestController
public class HttpErrorController implements ErrorController {
    private final static String ERROR_PATH="/error";
    @ResponseBody
    @RequestMapping(path=ERROR_PATH)
    public Result error(HttpServletRequest request , HttpServletResponse response){
        log.info("访问/error"+"错误代码："+response.getStatus());
        return Result.build(ResultCodeEnum.SYSTEM_ERROR.getCode(),ResultCodeEnum.SYSTEM_ERROR.getMessage());
    }
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}