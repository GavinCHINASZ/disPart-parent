package com.dispart.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * @author:xts
 * @date:Created in 2021/6/26 0:58
 * @description
 * @modified by:
 * @version: 1.0
 */
@Slf4j
@Getter
@Setter
public class GlobalGatewayExceptionHandler  implements ErrorWebExceptionHandler {
    private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();
    private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();
    private List<ViewResolver> viewResolvers = Collections.emptyList();
    private ThreadLocal<Result> threadLocal=new ThreadLocal<>();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable throwable) {
        log.error(exchange.getRequest().getPath().value()+"請求異常，异常信息：{}",throwable.getMessage());
        //这里只是做个最简单的同一的异常结果输出，实际业务可根据throwable不同的异常处理不同的逻辑
        Result result=null;
        if(throwable instanceof CustomException){
            log.error("進入CustomException");
            result = Result.build(((CustomException) throwable).getCode(),throwable.getMessage());
        }
        if(throwable instanceof NotFoundException){
            log.error("進入NotFoundException");
            result = Result.build(ResultCodeEnum.NotFoundException.getCode(),ResultCodeEnum.NotFoundException.getMessage());
        }
        if(throwable instanceof IllegalArgumentException){
            log.error("進入IllegalArgumentException");
            result = Result.build(ResultCodeEnum.SYSTEM_ERROR.getCode(),ResultCodeEnum.SYSTEM_ERROR.getMessage());
        }
        threadLocal.set(result);
        ServerRequest newRequest = ServerRequest.create(exchange, this.messageReaders);
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse).route(newRequest)
                .switchIfEmpty(Mono.error(throwable))
                .flatMap((handler) -> handler.handle(newRequest))
                .flatMap((response) -> write(exchange, response));
    }

    /**
     * 统一返回指定异常信息(指定json格式输出)
     * @param request
     * @return
     */
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request){
        return ServerResponse.status(ResultCodeEnum.SYSTEM_ERROR.getCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(threadLocal.get()));
    }

    /**
     * 参考DefaultErrorWebExceptionHandler
     */
    private Mono<? extends Void> write(ServerWebExchange exchange, ServerResponse response) {
        exchange.getResponse().getHeaders().setContentType(response.headers().getContentType());
        return response.writeTo(exchange, new ResponseContext());
    }

    /**
     * 参考DefaultErrorWebExceptionHandler
     */
    private class ResponseContext implements ServerResponse.Context {
        private ResponseContext() {
        }

        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return GlobalGatewayExceptionHandler.this.messageWriters;
        }

        @Override
        public List<ViewResolver> viewResolvers() {
            return GlobalGatewayExceptionHandler.this.viewResolvers;
        }
    }
}
