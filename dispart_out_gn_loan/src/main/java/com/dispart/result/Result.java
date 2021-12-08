package com.dispart.result;


import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
public class Result<T> {

    private Integer code;

    private String message;

    private T data;

    private Boolean ok;

    public Result(Integer code, String message, T data, Boolean ok) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.ok = ok;
    }
}
