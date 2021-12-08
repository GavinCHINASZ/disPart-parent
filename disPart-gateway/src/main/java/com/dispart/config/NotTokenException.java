package com.dispart.config;

/**
 * @author:xts
 * @date:Created in 2021/6/26 1:40
 * @description
 * @modified by:
 * @version: 1.0
 */
public class NotTokenException  extends  RuntimeException{
    public NotTokenException(String message){
        super(message);
    }
}