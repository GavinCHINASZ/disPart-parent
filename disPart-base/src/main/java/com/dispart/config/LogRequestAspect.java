package com.dispart.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:xts
 * @date:Created in 2021/6/16 3:36
 * @description 拦截进入业务层面前处理打印请求日志
 * @modified by:
 * @version: 1.0
 */
@Aspect
@Component
@Slf4j
public class LogRequestAspect {

    @Autowired
    private HttpServletRequest req;

    @Pointcut("execution(public * com.dispart.controller.*.*(..))")
    public  void handle(){
        //全局处理
    }
    @Before("handle()")
    public  void singBefore(JoinPoint joinPoint)  {
       try {
           //获取请求信息url
           String url = req.getRequestURI();
           log.info("组织权限管理-请求url" + url);

//           Enumeration headerNames = req.getHeaderNames();
//           while (headerNames.hasMoreElements()) {
//               String headerName = (String) headerNames.nextElement();
//               log.info("用户业务-请求头" + headerName + ":" + req.getHeader(headerName));
//           }
           log.info("组织权限管理-请求数据" + JSONObject.toJSONString(joinPoint.getArgs()));

       }catch(Exception e){
           e.printStackTrace();
       }
    }
}