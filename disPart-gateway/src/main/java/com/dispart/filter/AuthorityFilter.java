package com.dispart.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dispart.config.*;
import com.dispart.utils.*;
import com.netflix.loadbalancer.Server;
import com.netflix.ribbon.proxy.annotation.Http;
import com.sun.org.apache.bcel.internal.classfile.Attribute;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.ServerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author:xts
 * @date:Created in 2021/6/21 1:20
 * @description 客户端请求鉴权
 * @modified by:
 * @version: 1.0
 */
@Component
@Slf4j
public class AuthorityFilter implements GlobalFilter, Ordered {
    @Value("${server.port}")
    private String port;
    @Value("${auth.reqAuthUrl}")
    private String reqAuthUrl;
    @Value("${auth.token.aesKey}")
    private String aesKey;
    @Value("${sign.nodename}")
    private String node_name;
    @Value("${sign.secret}")
    private String secret;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url=exchange.getRequest().getPath().value();
        //不需要鉴权和解密的请求
        Map<String ,String > map=getNotAuthToNotPwdRequest();
        if(!StringUtils.isBlank(map.get(url))){
            //不需要鉴权和解密的请求进行缓存后的请求进行重构处理
            log.info(url+"应用网关对客户端请求进行不需要鉴权和解密处理開始......");
            //建构token

            this.getTokenByUserNo(exchange);
            //加签
            this.checkAuthUrl(exchange);
            //重构
            ServerHttpRequest httpRequest=this.restructure(exchange);
            log.info(url+"应用网关对客户端请求进行不需要鉴权和解密处理結束......");
            if(httpRequest!=null){
                return chain.filter(exchange.mutate().request(httpRequest).build());
            }else{
                return chain.filter(exchange);
            }
        }

        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        exchange.getAttribute(ServerWebExchangeUtils.CLIENT_RESPONSE_CONN_ATTR);
        HttpHeaders httpHeaders = serverHttpRequest.getHeaders();
        String chanlNo=null;
        if(httpHeaders.get("chanlNo")!=null){
            chanlNo=httpHeaders.get("chanlNo").toString();
        }
        log.info(url+"应用网关接收客戶端請求渠道編碼，{}",chanlNo);
        //過濾移動端請求
        if(!StringUtils.isBlank(chanlNo)){
            log.info(url+"应用网关收到移动客户端请求進行處理開始......");
            //不做解密的请求进行缓存后的请求进行重构处理
            //建构token
            this.getTokenByUserNo(exchange);
            //加签
            this.checkAuthUrl(exchange);
            //重构
            ServerHttpRequest httpRequest=this.restructure(exchange);
            log.info(url+"应用网关收到移动客户端请求進行處理結束......");
            if(httpRequest!=null){
                return chain.filter(exchange.mutate().request(httpRequest).build());
            }else{
                return chain.filter(exchange);
            }
        }
        log.info(url+"应用网关对客户端请求进行鉴权和解密处理开始......");
        //进行处理鉴权
        String methed=exchange.getRequest().getMethodValue();
        try {
            //过滤请求
            Map<String ,String > pwdMap=getNotAuthToPwdRequest();
            if(!StringUtils.isBlank(pwdMap.get(url))){

                log.info(url+"应用网关对客户端不需要鉴权的请求进行解密处理......");
                /***
                 * 处理需要解密和不需要鉴权的请求
                 */
                //验证是客户端请求方式
                if(methed.equals("POST")){
                    //解密处理和重构处理
                    return chain.filter(exchange.mutate().request(this.decrypt(exchange)).build());
                }else if(methed.equals("GET")){
                    //log.info(url+"应用网关接收到客户端GET请求，直接跳过解密处理，进行请求重构处理,{}",methed);
                    //不做解密的请求进行缓存后的请求进行重构处理
                    //建构token
                    this.getTokenByUserNo(exchange);
                    //加签
                    this.checkAuthUrl(exchange);
                    //重构
                    ServerHttpRequest httpRequest=this.restructure(exchange);
                    log.info(url+"应用网关向安全中心发起鉴权请求和解密处理结束......");
                    if(httpRequest!=null){
                        return chain.filter(exchange.mutate().request(httpRequest).build());
                    }else{
                        return chain.filter(exchange);
                    }
                }
            }else{
                log.info(url+"应用网关对客户端需求解密和鉴权的请求进行处理......");
                /**
                 * 处理需要鉴权和解密的请求
                 */
                String authUrl = exchange.getRequest().getPath().value();
                String authArray[]=authUrl.split("/");
                String authPathUrl="";
                for (int i=2;i<authArray.length;i++){
                    if(i==2){
                        authPathUrl="/"+authArray[i];
                    }else{
                        authPathUrl=authPathUrl+"/"+authArray[i];
                    }
                }
                //log.info("应用网关对客户端请求进行鉴权处理，发起鉴权请求接口URL，{}",reqAuthUrl);
                log.info(url+"应用网关向安全中心发起鉴权请求，请求url，{}",authPathUrl);
                String tokenStr = httpHeaders.get("Authorization").toString();
                String token="";
                if(!StringUtils.isBlank(tokenStr)){
                    String token1 =tokenStr.substring(1);
                    token =token1.substring(0,token1.length()-1);
                }
                //log.info("应用网关对客户端请求进行鉴权处理，发起鉴权请求参数TOKEN，{}",token);
                String ip=LocalHostUtil.getLocalIp();
                String responseStr = HttpRequest.authPost(reqAuthUrl, token, authPathUrl);
                log.info(url+"应用网关向安全中心发起鉴权请求，返回报文，{}",responseStr);
                JSONObject jsonObject=JSONObject.parseObject(responseStr)     ;
                if (!jsonObject.getString("code").equals("200")) {
                    //鉴权失败
                    //throw new AuthException(ResultCodeEnum.NotAuthException.getMessage());
                    throw new CustomException(ResultCodeEnum.NotAuthException.getCode(),ResultCodeEnum.NotAuthException.getMessage(),null);

                }
                log.info(url+"应用网关向安全中心发起鉴权请求，鉴权成功，{}",responseStr);
                //进行解密
                //验证是客户端请求方式
                if(methed.equals("POST")){
                    //解密处理和重构处理
                    return chain.filter(exchange.mutate().request(this.decrypt(exchange)).build());
                }else if(methed.equals("GET")){
                    // log.info("应用网关接收到客户端GET请求，直接跳过解密处理，进行请求重构处理,{}",methed);
                    //不做解密的请求进行缓存后的请求进行重构处理
                    //建构token
                    this.getTokenByUserNo(exchange);
                    //加签
                    this.checkAuthUrl(exchange);
                    //重构
                    ServerHttpRequest httpRequest=this.restructure(exchange);
                    log.info(url+"应用网关向安全中心发起鉴权请求和解密处理结束......");
                    if(httpRequest!=null){
                        return chain.filter(exchange.mutate().request(httpRequest).build());
                    }else{
                        return chain.filter(exchange);
                    }
                }
            }

        } catch (Exception e) {
            log.info("应用网关向安全中心发起鉴权请求和解密处理異常，{}",e);
            //throw new NotTokenException(ResultCodeEnum.NotTokenException.getMessage());
            throw new CustomException(ResultCodeEnum.NotTokenException.getCode(),ResultCodeEnum.NotTokenException.getMessage(),null);

        }
        return  chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -3;
    }
    @Data
    private class Result implements Serializable {
        private String success;
        private String code;
        private String msg;
        private DataVo data;
    }

    /**
     * 解密处理
     * @param exchange
     * @return
     */
    private ServerHttpRequest  decrypt(ServerWebExchange exchange) {
        //建构token
        this.getTokenByUserNo(exchange);
        //加签
        this.checkAuthUrl(exchange);
        //解密处理
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        HttpHeaders httpHeaders = serverHttpRequest.getHeaders();
        if(httpHeaders.get("Authorization")!=null&&!httpHeaders.get("Authorization").equals("")){
            String  tokenStr = httpHeaders.get("Authorization").toString();
            String token="";
            if(!StringUtils.isBlank(tokenStr)){
                String token1 =tokenStr.substring(8);
                token =token1.substring(0,token1.length()-1);
            }
            String authUrl = exchange.getRequest().getPath().value();
            URI uri=exchange.getRequest().getURI();
            log.info(exchange.getRequest().getPath().value()+"应用网关对客户端请求报文进行解密处理开始......");
            //执行解密
            //客户端输出过来安全中心公钥加密的的业务平台私钥
            String requestSecret=httpHeaders.get("requestSecret").toString();
            requestSecret=requestSecret.substring(1,requestSecret.length()-1);
            //log.info(exchange.getRequest().getPath().value()+"应用网关接收到客户端请求的加密秘钥为，{}",requestSecret);
            //获取安全中心私钥、
            String privateKey=redisUtil.get(token);
            if(StringUtils.isBlank(privateKey)){
                //获取不到安全中心私钥
                log.info(exchange.getRequest().getPath().value()+"应用网关获取存储的私钥失败：{}",privateKey);
                //throw new PasswdException(ResultCodeEnum.NOTPASSWDException.getMessage());
                throw new CustomException(ResultCodeEnum.NOTPASSWDException.getCode(),ResultCodeEnum.NOTPASSWDException.getMessage(),null);

            }
            //log.info(exchange.getRequest().getPath().value()+"应用网关获取到存储的私钥为：{}",privateKey);
            //客户端端请求的秘钥

            String clientKeySt=RSAStaticUtils.decryptPrivateKey(privateKey,requestSecret);
            try {
                clientKeySt = URLDecoder.decode(clientKeySt,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //log.info(exchange.getRequest().getPath().value()+"应用网关接收到客户端请求的解密后的解密秘钥，{}",clientKeySt);
            String[] clientKeys = clientKeySt.split("\\@");
            //尝试从exchange的自定义属性中取出缓存到的BOdy
            Object cacheRequestBodyObject=exchange.getAttributeOrDefault(GlobalFinal.cacheKey,null);
            if(cacheRequestBodyObject==null) {
                //尝试从exchange的自定义属性中没有取出缓存到的BOdy
                log.info(exchange.getRequest().getPath().value()+"应用网关从exchange缓存中没有获取到请求报文，导致解密失败");
               // throw new PasswdException(ResultCodeEnum.NOTPASSWDException.getMessage());
                throw new CustomException(ResultCodeEnum.NOTPASSWDException.getCode(),ResultCodeEnum.NOTPASSWDException.getMessage(),null);

            }
            byte[] decrypBytes = null;
            try {
                byte[] body = (byte[]) cacheRequestBodyObject;
                //客户端传过来的数据
                String rootData = new String(body);
                //log.info(exchange.getRequest().getPath().value()+"应用网关接收客户端请求的参数，{}", rootData);
                decrypBytes = body;
                JSONObject jsonObject1 = JSON.parseObject(rootData);
                String text = jsonObject1.getString("text");
                String bodyStr = AESUtils.decryptFromBase64(text, clientKeys[0], clientKeys[1]);
                log.info(exchange.getRequest().getPath().value()+"应用网关对客户端请求报文进行解密后的报文，{}", bodyStr);
                decrypBytes = bodyStr.getBytes();
            } catch (Exception e) {
                log.info(exchange.getRequest().getPath().value()+"应用网关对客户端请求报文解析异常，{}", e.toString());
                //throw new PasswdException(ResultCodeEnum.NOTPASSWDException.getMessage());
                throw new CustomException(ResultCodeEnum.NOTPASSWDException.getCode(),ResultCodeEnum.NOTPASSWDException.getMessage(),null);

            }
            //根据解密后的参数重新构建请求
            DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
            Flux<DataBuffer> bodyFlux = Flux.just(dataBufferFactory.wrap(decrypBytes));
            ServerHttpRequest newRequest = serverHttpRequest.mutate().uri(uri).build();
            newRequest = new ServerHttpRequestDecorator(newRequest) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return bodyFlux;
                }
            };
            //建构新的请求头
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(exchange.getRequest().getHeaders());
            //由于修改了传递参数，需要重新设置CONTENT_LENGTH,长度是字节长度，不是字符串长度
            int length = decrypBytes.length;
            headers.remove(HttpHeaders.CONTENT_LENGTH);
            headers.setContentLength(length);
            //headers.set(HttpHeaders.CONTENT_TYPE,"application/json");
            newRequest = new ServerHttpRequestDecorator(newRequest) {
                @Override
                public HttpHeaders getHeaders() {
                    return headers;
                }
            };
            log.info(exchange.getRequest().getPath().value()+"应用网关对客户端请求报文解密成功,{}");
            //将解密后的数据重置到EXCHANGe自定义的属性中，再之后的日志记录需求中，可从这里获取请求参数
            exchange.getAttributes().put(GlobalFinal.cacheKey, decrypBytes);
            return newRequest;
        }else{
            //建构token
            this.getTokenByUserNo(exchange);
            //加签
            this.checkAuthUrl(exchange);
            //重构
            ServerHttpRequest httpRequest=this.restructure(exchange);
            return httpRequest;
        }
    }

    private void  getTokenByUserNo(ServerWebExchange exchange) {
        HttpHeaders httpHeaders=exchange.getRequest().getHeaders();
        log.info(exchange.getRequest().getPath().value()+"应用网关对客户端请求用户凭证解析开始......");
        if(httpHeaders.get("Authorization")!=null&&!httpHeaders.get("Authorization").equals("")){
            log.info(exchange.getRequest().getPath().value()+"应用网关对客户端请求用户凭证，{}",httpHeaders.get("Authorization"));
            log.info("应用网关对客户端请求用户凭证解析开始......");
            String tokenStr = httpHeaders.get("Authorization").toString();
            //log.info(exchange.getRequest().getPath().value()+"应用网关对客户端请求的TOKEN解析处理开始,请求参数Authorization，",tokenStr);
            if (tokenStr.length()!=8) {
                String token1 = tokenStr.substring(8);
                String token2 = token1.substring(0, token1.length() - 1);
                //log.info(exchange.getRequest().getPath().value()+"应用网关对客户端请求的TOKEN解析后得到的TOKEN,{}", token2);
                TicketToken ticketToken = TicketToken.valueOf(token2, aesKey);
                log.info(exchange.getRequest().getPath().value()+"应用网关对客户端请求用户凭证解析得到的USERID,{}", ticketToken.getUserId());
                ServerHttpRequest host=exchange.getRequest().mutate().header("userNo",ticketToken.getUserId())
                        .build();
                exchange.mutate().request(host).build();
                //log.info(exchange.getRequest().getPath().value()+"应用网关对客户端请求进行TOKEN解析，USERID:{}",ticketToken.getUserId());
                log.info(exchange.getRequest().getPath().value()+"应用网关对客户端请求用户凭证解析成功.....");
            }else{
                log.info(exchange.getRequest().getPath().value()+"应用网关对客户端请求用户凭证解析失败......");
                //throw new NotTokenException(ResultCodeEnum.NotTokenException.getMessage());
                throw new CustomException(ResultCodeEnum.NotTokenException.getCode(),ResultCodeEnum.NotTokenException.getMessage(),null);

            }
            log.info(exchange.getRequest().getPath().value()+"应用网关对客户端请求用户凭证解析结束......");
        }
    }
    private void  checkAuthUrl(ServerWebExchange exchange) {
        log.info(exchange.getRequest().getPath().value()+"应用网关对客户端请求进行加签处理开始.......");
        String request_time =String.valueOf(System.currentTimeMillis()/1000);
        String node_ip= null;
        try {
            node_ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String sgin="";
        try {
            sgin=SHAUtils.getSHA256Str(secret,"node-ip="+node_ip+"&node-name="+node_name+"&request-time="+request_time);
            //log.info(exchange.getRequest().getPath().value()+"应用网关对客户端请求进行签名的node-name:{}",node_name);
            //log.info(exchange.getRequest().getPath().value()+"应用网关对客户端请求进行签名的node-ip:{}",node_ip);
            //log.info(exchange.getRequest().getPath().value()+"应用网关对客户端请求进行签名的request-time:{}",request_time);
            //log.info(exchange.getRequest().getPath().value()+"应用网关对客户端请求进行签名的sgin:{}",sgin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ServerHttpRequest sect=exchange.getRequest().mutate()
                .header("node-name",node_name)
                .header("node-ip",node_ip)
                .header("request-time",request_time)
                .header("sign", sgin)
                .build();
        exchange.mutate().request(sect).build();
        log.info(exchange.getRequest().getPath().value()+"应用网关对客户端请求进行加签处理结束.......");
    }
    /**
     * 客户端请求重构
     */
    private ServerHttpRequest  restructure(ServerWebExchange exchange) {
        log.info(exchange.getRequest().getPath().value()+"应用网关对客户端请求进行重构处理开始.......");
        //尝试从exchange的自定义属性中取出缓存到的BOdy
        Object cacheRequestBodyObject = exchange.getAttributeOrDefault(GlobalFinal.cacheKey, null);
        if (cacheRequestBodyObject != null) {
            byte[] decrypBytes = null;
            try {
                byte[] body = (byte[]) cacheRequestBodyObject;
                //客户端传过来的数据
                String rootData = new String(body);
                //log.info(exchange.getRequest().getPath().value()+"应用网关对客户端请求进行重构处理，请求参数为，{}", rootData.toString());
                decrypBytes = body;
            } catch (Exception e) {
                log.debug(exchange.getRequest().getPath().value()+"应用网关对客户端请求进行重构失败，解析报文异常，{}", e.toString());
            }
            //根据缓存后的相关处理的参数进行重构
            DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
            Flux<DataBuffer> bodyFlux = Flux.just(dataBufferFactory.wrap(decrypBytes));
            ServerHttpRequest newRequest = exchange.getRequest().mutate().uri(exchange.getRequest().getURI()).build();
            newRequest = new ServerHttpRequestDecorator(newRequest) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return bodyFlux;
                }
            };
            //建构新的请求头
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(exchange.getRequest().getHeaders());
            //由于修改了传递参数，需要重新设置CONTENT_LENGTH,长度是字节长度，不是字符串长度
            int length = decrypBytes.length;
            headers.remove(HttpHeaders.CONTENT_LENGTH);
            headers.setContentLength(length);
            //headers.set(HttpHeaders.CONTENT_TYPE,"application/json");
            newRequest = new ServerHttpRequestDecorator(newRequest) {
                @Override
                public HttpHeaders getHeaders() {
                    return headers;
                }
            };
            //将解密后的数据重置到EXCHANGe自定义的属性中，再之后的日志记录需求中，可从这里获取请求参数
            exchange.getAttributes().put(GlobalFinal.cacheKey, decrypBytes);
            log.info(exchange.getRequest().getPath().value()+"应用网关对客户端请求进行重构处理结束.......");
            return newRequest;
        } else {
            return null;
        }
    }

    /**
     * 不需要鑒權和不需要解密的請求
     */
    private Map<String ,String >  getNotAuthToNotPwdRequest(){
        Map<String ,String > map=new HashMap<>();
        map.put("/securityCenter/authority/getTempToken","0");
        map.put("/securityCenter/ca/rsaKey","0");
        map.put("/securityCenter/users/login","0");
        map.put("/securityCenter/authority/authentication","0");
        map.put("/securityCenter/authority/syncRoleAuth","0");
        map.put("/securityCenter/authority/syncOrgAuth","0");
        map.put("/busineCommon/users/login","0");
        map.put("/busineCommon/users/auth/org","0");
        map.put("/busineCommon/users/auth/role","0");
        map.put("/busineCommon/push/users/auth/role","0");
        map.put("/busineCommon/push/users/auth/org","0");
        map.put("/files/securityCenter/DISP20210105","0");
        map.put("/files/securityCenter/DISP20210106","0");
        map.put("/base/securityCenter/DISP20210043","0");
        map.put("/entrance/securityCenter/DISP20210233","0");
        map.put("/entrance/securityCenter/DISP20210237","0");
        map.put("/files/securityCenter/DISP20210344","0");
        map.put("/files/securityCenter/DISP20210348","0");
        return map;
    }

    /**
     * 不需要鑒權和需要解密的請求
     */
    private Map<String ,String >  getNotAuthToPwdRequest(){
        Map<String ,String> map=new HashMap<>();
        map.put("/securityCenter/users/userLogin","0");
        map.put("/busineCommon/securityCenter/DISP20210107","0");
        map.put("/busineCommon/securityCenter/DISP20210108","0");
        map.put("/busineCommon/securityCenter/DISP20210109","0");
        return map;
    }
}