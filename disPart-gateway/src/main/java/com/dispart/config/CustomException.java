package com.dispart.config;


/**
 * 自定义异常类(运行时异常)
 * @author wunaozai
 * @date 2018-06-27
 */
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 6304501072268270030L;

    public CustomException(String msg) {
        this(500, msg);
    }
    public CustomException(int code, String msg) {
        this(code, msg, null);
    }
    public CustomException(String msg, Object data) {
        this(500, msg, data);
    }
    public CustomException(int code, String msg, Object data) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    /**
     * 异常代码
     */
    private int code;
    /**
     * 异常信息
     */
    private String msg;
    /**
     * 异常调试信息
     */
    private Object data;

    /**
     * 异常代码
     * @return code
     */
    public int getCode() {
        return code;
    }
    /**
     * 异常代码
     * @param code 异常代码
     */
    public void setCode(int code) {
        this.code = code;
    }
    /**
     * 异常信息
     * @return msg
     */
    public String getMsg() {
        return msg;
    }
    /**
     * 异常信息
     * @param msg 异常信息
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
    /**
     * 异常调试信息
     * @return data
     */
    public Object getData() {
        return data;
    }
    /**
     * 异常调试信息
     * @param data 异常调试信息
     */
    public void setData(Object data) {
        this.data = data;
    }
}