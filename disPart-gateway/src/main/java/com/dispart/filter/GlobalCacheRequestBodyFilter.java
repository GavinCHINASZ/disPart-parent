package com.dispart.filter;

import com.dispart.config.GlobalFinal;
import com.dispart.config.NotTokenException;
import com.sun.org.apache.bcel.internal.classfile.ConstantFieldref;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Constants;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;

/**
 * @author:xts
 * @date:Created in 2021/6/21 12:07
 * @description
 * @modified by:
 * @version: 1.0
 */
@Component
@Slf4j
public class GlobalCacheRequestBodyFilter implements  GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
       /* if(true){
            throw new NotTokenException("未登录");
        }*/
        String url=exchange.getRequest().getPath().value();
        log.info("客户端请求进入应用网关的URI:"+exchange.getRequest().getURI());
        log.info("客户端请求进入应用网关的URL:"+exchange.getRequest().getPath().value());
        log.info("应用网关对客户端请求进行缓存处理开始......");
        Object cachedRequestBodyObject =exchange.getAttributeOrDefault(GlobalFinal.cacheKey,null);
        //如果已经缓存过，忽略
        if(cachedRequestBodyObject!=null){
            return chain.filter(exchange);
        }
        log.info("应用网关对客户端请求参数进行缓存处理结束......");

        //如果没有缓存过，获取字节数组存入exchange 的自定义属性中
        return DataBufferUtils.join(exchange.getRequest().getBody())
                .map(dataBuffer -> {
                    byte[] bytes=new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    return bytes;
                }).defaultIfEmpty(new byte[0])
                .doOnNext(bytes -> exchange.getAttributes().put(GlobalFinal.cacheKey,bytes))
                .then(chain.filter(exchange));
    }

    @Override
    public int getOrder() {
        return -4;
    }


}