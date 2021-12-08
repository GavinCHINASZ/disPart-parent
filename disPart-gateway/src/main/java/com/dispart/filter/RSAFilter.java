package com.dispart.filter;

import com.alibaba.fastjson.JSON;
import com.dispart.config.DataVo;
import com.dispart.config.RsaKeyVo;
import com.dispart.utils.RedisUtil;
import com.dispart.utils.TicketToken;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author:xts
 * @date:Created in 2021/6/20 21:54
 * @description 处理RSA秘钥信息
 * @modified by:
 * @version: 1.0
 */
@Component
@Slf4j
public class RSAFilter  implements GlobalFilter, Ordered {
    @Value("${auth.rasKeyUrl}")
    private String rasKey;
    @Autowired
    private RedisUtil redisUtil;
    private static Joiner joiner=Joiner.on("");
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url=exchange.getRequest().getPath().value();
        if(url.equals("/securityCenter/ca/rsaKey")){
            log.info("应用网关对客户端请求获取安全中心秘钥处理开始......");
            ServerHttpRequest serverHttpRequest=exchange.getRequest();
            HttpHeaders httpHeaders=serverHttpRequest.getHeaders();
            String tokenStr = httpHeaders.get("Authorization").toString();
            //log.info("应用网关对客户端请求的TOKEN，{}",tokenStr);
            String token="";
            if(!StringUtils.isBlank(tokenStr)){
                String token1 =tokenStr.substring(8);
                token =token1.substring(0,token1.length()-1);
            }
            ServerHttpResponse originalResponse = exchange.getResponse();
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            String finalToken = token;
            ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                    if (body instanceof Flux) {
                        Flux<? extends DataBuffer> fluxBody = Flux.from(body) ;
                        return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                            List<String> list= Lists.newArrayList();
                            dataBuffers.forEach(dataBuffer ->{
                                byte[] content =new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);
                                try {
                                        list.add(new String(content,"UTF-8"));
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            });
                            String responseData=joiner.join(list);

                            if(url.equals(rasKey)){
                                //进行处理
                                //log.info("应用网关对客户端请求安全中心获取秘钥的请求参数为,{}",responseData );
                                Result result=cacheKey(finalToken,originalResponse,responseData);
                                if(result!=null){
                                    log.info("应用网关向安全中心获取秘钥对成功：{}",result);
                                    responseData= com.alibaba.fastjson.JSONObject.toJSONString(result);
                                }else{
                                    log.info("应用网关向安全中心获取秘钥对失败：{}",result);
                                }
                            }
                            //byte[] uppedContent = new String(lastStr, Charset.forName("UTF-8")).getBytes();
                            String str =new String(responseData.getBytes(),Charset.forName("UTF-8"));
                            byte[] contenBytes=str.getBytes();
                            exchange.getResponse().getHeaders().setContentLength(contenBytes.length);
                            return exchange.getResponse().bufferFactory().wrap(contenBytes);
                        }));
                    }
                    // if body is not a flux. never got there.
                    return super.writeWith(body);
                }
            };
            log.info("应用网关对客户端请求获取安全中心秘钥处理结束......");
            return chain.filter(exchange.mutate().response(decoratedResponse).build());
        }else{
            // replace response with decorator
            return chain.filter(exchange);
        }
    }
    public Result cacheKey(String token,ServerHttpResponse serverHttpResponse ,String resultStr){
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(resultStr);
        if(Integer.parseInt(jsonObject.getString("code"))!=200){
            return null;
        }
        String data=jsonObject.get("data").toString();
        JSONObject dataJson=JSONObject.fromObject(data);
        String publicKey=dataJson.getString("publicKey");
        String privateKey=dataJson.getString("privateKey");
        //log.info("应用网关对客户端请求安全中获取秘钥处理，公钥：publicKey：{}",publicKey);
        //log.info("应用网关对客户端请求安全中获取秘钥处理，私钥：privateKey：{}",privateKey);
        //log.info("应用网关对客户端请求安全中存储私钥的KEY-token，{}",token);
        redisUtil.setEx(token,privateKey,60*60*24*30, TimeUnit.SECONDS);
       // log.info("应用网关存储进入redis的私钥KEY-privateKey，{}",redisUtil.get(token));
        Result result=new Result();
        result.setCode(jsonObject.getString("code"));
        DataVo data1=new DataVo();
        data1.setPublicKey(publicKey);
        result.setData(data1);
        result.setMsg(jsonObject.getString("msg"));
        result.setSuccess(jsonObject.getString("success"));
        return result;
    }

    @Override
    public int getOrder() {
        return -2;
    }
    @Data
    private class Result implements Serializable{
        private String success;
        private String code;
        private String msg;
        private DataVo data;
    }

}