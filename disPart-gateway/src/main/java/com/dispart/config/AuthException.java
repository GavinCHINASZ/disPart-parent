package com.dispart.config;

/**
 * @author:xts
 * @date:Created in 2021/6/26 1:36
 * @description
 * @modified by:
 * @version: 1.0
 */
public class AuthException extends RuntimeException {
    public AuthException(String message){
        super(message);
    }
}