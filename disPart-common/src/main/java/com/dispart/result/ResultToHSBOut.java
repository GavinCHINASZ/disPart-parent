package com.dispart.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 全局统一返回结果类
 */
@Data
@ApiModel(value = "外链服务统一返回结果")
public class ResultToHSBOut {

    @ApiModelProperty(value = "交易状态")
    private String txnSt;

    @ApiModelProperty(value = "错误码")
    private String errCode;

    @ApiModelProperty(value = "错误描述")
    private String errMsg;

    @ApiModelProperty(value = "接收时间")
    private String date;

    public ResultToHSBOut(){}

    public static ResultToHSBOut build(String txnSt,String date,String errCode,String errMsg) {
        ResultToHSBOut result = new ResultToHSBOut();
        result.setTxnSt(txnSt);
        result.setDate(date);
        result.setErrCode(errCode);
        result.setErrMsg(errMsg);

        return result;
    }

//    public static  ResultToHSBOut build(, ResultCodeEnum resultCodeEnum) {
//        ResultToHSBOut<T> result = build(body);
//        result.setCode(resultCodeEnum.getCode());
//        result.setMessage(resultCodeEnum.getMessage());
//        return result;
//    }
//
//    public static <T> ResultToHSBOut<T> build(Integer code, String message) {
//        ResultToHSBOut<T> result = build(null);
//        result.setCode(code);
//        result.setMessage(message);
//        return result;
//    }
//
//    public static <T> ResultToHSBOut<T> build(T body, Integer code, String message){
//        ResultToHSBOut<T> result = build(body);
//        result.setCode(code);
//        result.setMessage(message);
//        return result;
//    }
//
//    public static<T> ResultToHSBOut<T> ok(){
//        return ResultToHSBOut.ok(null);
//    }
//
//    /**
//     * 操作成功
//     * @param data
//     * @param <T>
//     * @return
//     */
//    public static<T> ResultToHSBOut<T> ok(T data){
//        ResultToHSBOut<T> result = build(data);
//        return build(data, ResultCodeEnum.SUCCESS);
//    }
//
//    public static<T> ResultToHSBOut<T> fail(){
//        return ResultToHSBOut.fail(null);
//    }
//
//    /**
//     * 操作失败
//     * @param data
//     * @param <T>
//     * @return
//     */
//    public static<T> ResultToHSBOut<T> fail(T data){
//        ResultToHSBOut<T> result = build(data);
//        return build(data, ResultCodeEnum.FAIL);
//    }
//
//    public ResultToHSBOut<T> message(String msg){
//        this.setMessage(msg);
//        return this;
//    }
//
//    public ResultToHSBOut<T> code(Integer code){
//        this.setCode(code);
//        return this;
//    }
//
//    public boolean isOk() {
//        if(this.getCode().intValue() == ResultCodeEnum.SUCCESS.getCode().intValue()) {
//            return true;
//        }
//        return false;
//    }
}
