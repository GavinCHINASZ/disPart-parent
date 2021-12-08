package com.dispart.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dispart.result.Result;
import com.dispart.service.TTraceIdService;
import com.dispart.utils.SHAUtils;
import com.dispart.vo.commons.TTraceId;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

/**
 * 拦截进入业务层面前处理
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

    @Pointcut("execution(* com.dispart.controller.*.*(..)) ")
    public  void handle(){
        //全局处理
    }
    @Before("handle()")
    public Result singBefore(JoinPoint joinPoint){
        log.info("车辆进出场服务-客户端请求地址URL，{}",req.getRequestURL());
        Object object=joinPoint.getArgs();
        if(object!=null){
            log.info("车辆进出场服务-客户端请求参数，{}" + JSONObject.toJSONString(joinPoint.getArgs()));
            JSONArray jsonArray=JSONArray.parseArray(JSONObject.toJSONString(joinPoint.getArgs()));
            JSONObject jsonObject;
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
            log.debug("车辆进出场服务-记录客户端请求日志记录开始......");
            //需增加表
            TTraceId tTraceId=new TTraceId();
            tTraceId.setChanlType(jsonObject1.get("chanlNo")!=null?jsonObject1.get("chanlNo").toString():"");
            tTraceId.setOperId(jsonObject1.get("operator")!=null?jsonObject1.get("operator").toString():"");
            tTraceId.setTxnCode(req.getRequestURI());
            tTraceId.setTxnDt(new Date(new java.util.Date().getTime()));
            tTraceId.setTxnTraceId(jsonObject1.get("reqSeqNo")!=null?jsonObject1.get("reqSeqNo").toString():"");
            tTraceId.setReqJson(jsonObject.toJSONString());
            tTraceIdService.insert(tTraceId);
        }
        //处理请求验签
        log.debug("车辆进出场服务-处理客户端请求验签开始.......");
        String node_ip=req.getHeader("node-ip");
        log.debug("车辆进出场服务-客户端请求签名参数node_ip,{}",node_ip);
        String node_name=req.getHeader("node-name");
        log.debug("车辆进出场服务-客户端请求签名参数node_name,{}",node_name);
        String request_time=req.getHeader("request-time");
        log.debug("车辆进出场服务-客户端请求签名参数request_time,{}",request_time);
        String sign=req.getHeader("sign");
        log.debug("车辆进出场服务-客户端签名值sign,{}",sign);
        try {
            log.debug("车辆进出场服务-业务端验签秘钥值sign_secret,{}",sign_secret);
            String newSign= SHAUtils.getSHA256Str(sign_secret,"node-ip="+node_ip+"&node-name="+node_name+"&request-time="+request_time);
            log.debug("车辆进出场服务-业务端验签值sign,{}",newSign);
        if(sign.equals(newSign)){
            log.info("车辆进出场服务-客户端请求验签成功");
        }else {
            log.error("车辆进出场服务-客户端请求验签失败");
        }
        } catch (Exception e) {
            log.error("车辆进出场服务-客户端请求出现异常", e);
        }
        log.debug("车辆进出场服务-处理客户端请求验签结束.......");

        return null;
    }
}