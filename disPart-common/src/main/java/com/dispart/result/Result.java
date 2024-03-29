package com.dispart.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * 全局统一返回结果类
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class Result<T> {

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public Result(){}

    protected static <T> Result<T> build(T data) {
        Result<T> result = new Result<T>();
        if (data != null)
            result.setData(data);
        return result;
    }

    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    public static <T> Result<T> build(T body, ResultCodeBaseEnum resultCodeBaseEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeBaseEnum.getCode());
        result.setMessage(resultCodeBaseEnum.getMessage());
        return result;
    }
    public static <T> Result<T> build(T body, EntranceResult_WEnum entranceResult_wEnum) {
        Result<T> result = build(body);
        result.setCode(entranceResult_wEnum.getCode());
        result.setMessage(entranceResult_wEnum.getMessage());
        return result;
    }

    public static <T> Result<T> build(T body, UserResultCodeEnum userResultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(userResultCodeEnum.getCode());
        result.setMessage(userResultCodeEnum.getMessage());
        return result;
    }

    public static <T> Result<T> build(Integer code, String message) {
        Result<T> result = build(null);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> build(T body, Integer code, String message){
        Result<T> result = build(body);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static<T> Result<T> ok(){
        return Result.ok(null);
    }

    /**
     * 操作成功
     * @param data
     * @param <T>
     * @return
     */
    public static<T> Result<T> ok(T data){
        Result<T> result = build(data);
        return build(data, ResultCodeEnum.SUCCESS);
    }

    /**
     * 返回String类型数据
     * @author  zhaoshihao
     * @date 2021/10/15
    */
    public static Result ok(String data){
        Result result = new Result();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    public static<T> Result<T> fail(){
        return Result.fail(null);
    }

    /**
     * 操作失败
     * @param <T>
     * @return
     */
    public static<T> Result<T> fail(T data){
        return build(data, ResultCodeEnum.FAIL);
    }



    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    public boolean isOk() {
        if(this.getCode().intValue() == ResultCodeEnum.SUCCESS.getCode().intValue()) {
            return true;
        }
        return false;
    }
}
