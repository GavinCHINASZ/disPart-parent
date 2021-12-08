package com.dispart.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.TTraceIdService;
import com.dispart.utils.SHAUtils;
import com.dispart.vo.commons.TTraceId;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

/**
 * @author:xts
 * @date:Created in 2021/6/16 3:36
 * @description 拦截进入业务层面前处理
 * @modified by:
 * @version: 1.0
 */
@Aspect
@Component
@Slf4j
public class RequestAspect {
    @Value("${sign.secret}")
    private String sign_secret;
    @Resource
    private HttpServletRequest req;
    @Resource
    private TTraceIdService tTraceIdService;

    @Pointcut("execution(* com.dispart.controller.*.*(..))")
    public  void handle(){
        //全局处理
    }
    @Before("handle()")
    public Result singBefore(JoinPoint joinPoint){
        log.info("用户业务-客户端请求地址URL，{}",req.getRequestURL());
        String url=req.getRequestURI().toString();
        if(!url.contains("/test/down")
                &&!url.contains("/securityCenter/DISP20210105")
                &&!url.contains("/securityCenter/DISP20210106")
                &&!url.contains("/users/auth/org")
                &&!url.contains("/users/auth/role")
                &&!url.contains("/push/users/auth/role")
                &&!url.contains("/push/users/auth/org")
                &&!url.contains("/users/login")){
            Object object=joinPoint.getArgs();
            if(object!=null){
                log.info("用户业务-客户端请求参数，{}" + JSONObject.toJSONString(joinPoint.getArgs()));
                JSONArray jsonArray=JSONArray.parseArray(JSONObject.toJSONString(joinPoint.getArgs()));
                JSONObject jsonObject=null;
                if(jsonArray.size()>1){
                    jsonObject=jsonArray.getJSONObject(1);
                }else{
                    jsonObject=jsonArray.getJSONObject(0);
                }
                JSONObject jsonObject1=JSONObject.parseObject(jsonObject.get("head").toString());
                String reqSeqNo =jsonObject1.get("reqSeqNo").toString();
                MDC.put("traceId",reqSeqNo);//流水号
                //处理请求全局流水号，注入日志
                MDC.put("serviceId",req.getRequestURL().toString());//交易码
                log.info("用户业务-记录客户端请求日志记录开始......");
                //需增加表
                TTraceId tTraceId=new TTraceId();
                tTraceId.setChanlType(jsonObject1.get("chanlNo")!=null?jsonObject1.get("chanlNo").toString():"");
                tTraceId.setOperId(jsonObject1.get("operator")!=null?jsonObject1.get("operator").toString():"");
                tTraceId.setTxnCode(req.getRequestURI().toString());
                tTraceId.setTxnDt(new Date(new java.util.Date().getTime()));
                tTraceId.setTxnTraceId(jsonObject1.get("reqSeqNo")!=null?jsonObject1.get("reqSeqNo").toString():"");
                tTraceId.setReqJson(jsonObject.toJSONString());
                tTraceIdService.insert(tTraceId);
            }
            //处理请求验签
            log.info("用户业务-处理客户端请求验签开始.......");
            String node_ip=req.getHeader("node-ip");
            log.info("用户业务-客户端请求签名参数node_ip,{}",node_ip);
            String node_name=req.getHeader("node-name");
            log.info("用户业务-客户端请求签名参数node_name,{}",node_name);
            String request_time=req.getHeader("request-time");
            log.info("用户业务-客户端请求签名参数request_time,{}",request_time);
            String sign=req.getHeader("sign");
            log.info("用户业务-客户端签名值sign,{}",sign);
            try {
                log.info("用户业务-业务端验签秘钥值sign_secret,{}",sign_secret);
                String newSign= SHAUtils.getSHA256Str(sign_secret,"node-ip="+node_ip+"&node-name="+node_name+"&request-time="+request_time);
                log.info("用户业务-业务端验签值sign,{}",newSign);
            if(sign.equals(newSign)){
                log.info("用户业务-客户端请求验签成功");
            }else {
                log.info("用户业务-客户端请求验签失败");
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("用户业务-处理客户端请求验签结束.......");
        }
        return null;
    }
}