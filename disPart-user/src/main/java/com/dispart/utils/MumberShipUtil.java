package com.dispart.utils;
import com.dispart.dto.ResultOutDto;
import com.dispart.dto.empdto.QuryBindRoleOutDto;
import com.dispart.enums.CustomAccountEnum;
import com.dispart.result.Result;
import com.dispart.result.UserResultCodeEnum;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.dispart.result.ResultCodeEnum.SUCCESS;

/**
 * @author:xiejin
 * @date:Created in 2021/8/11 16:27
 * @description
 * @modified by:
 * @version: 1.0
 */

@Slf4j
public class MumberShipUtil {
    //地区区号
    public static final String ACCOUNT="52012300";
    //虚拟卡单据号
    public static final String virtual="000000";
    //是否是虚拟卡 卡类型 0-实体卡 1-虚拟卡
    public static final String virtualType="1";
    //虚拟卡是否使用 状态 0-未使用 1-已使用 2-申请中 3-已注销 4-已取消
    public static final String virtualStatus="1";

    public  static Result<ResultOutDto> getResultFailDto(CustomAccountEnum enums) {
        Result<ResultOutDto> resultOutDto = null;
        ResultOutDto outDto = new ResultOutDto();
        outDto.setResult(ResultOutDto.FAIL);
        resultOutDto = Result.build(outDto, enums.getCode(),enums.getMessage());
        return resultOutDto;
    }

    public  static Result<ResultOutDto> getResultSuccessDto() {
        Result<ResultOutDto> resultOutDto = null;
        ResultOutDto outDto = new ResultOutDto();
        outDto.setResult(ResultOutDto.SUCCESS);
        resultOutDto = Result.build(outDto, SUCCESS);
        return resultOutDto;
    }

    public  static  Result<QuryBindRoleOutDto> getResultFailDto(QuryBindRoleOutDto data, UserResultCodeEnum enums) {
        Result<QuryBindRoleOutDto> resultOutDto = null;
        resultOutDto = Result.build(null, enums.getCode(),enums.getMessage());
        return resultOutDto;
    }

    public  static Result<ResultOutDto> buildFail(UserResultCodeEnum enums) {
        Result<ResultOutDto> resultOutDto = null;
        ResultOutDto outDto = new ResultOutDto();
        outDto.setResult(ResultOutDto.FAIL);
        resultOutDto = Result.build(outDto, enums.getCode(),enums.getMessage());
        return resultOutDto;
    }
    public  static Result<ResultOutDto> buildFail(UserResultCodeEnum enums,String msg) {
        Result<ResultOutDto> resultOutDto = null;
        ResultOutDto outDto = new ResultOutDto();
        outDto.setResult(ResultOutDto.FAIL);
        resultOutDto = Result.build(outDto, enums.getCode(),msg);
        return resultOutDto;
    }


    /**
     * 返回时间戳
     *
     * @return
     */
    public static String getTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 三位随机数
     * @return
     */
    public static String getThreeRandom(){
        return Integer.toString((int)(Math.random()*900)+100);
    }

    /**
     * 两位随机数
     * @return
     */
    public static String getTwoRandom(){
        return Integer.toString((int)(Math.random()*90)+10);
    }
/*    public  static Result<ResultOutDto> paramFail(String msg) {
        ResultOutDto outDto = new ResultOutDto();
        outDto.setResult(ResultOutDto.FAIL);
        Result<ResultOutDto> resultOutDto = null;
        log.info("用户业务-"+msg);
        resultOutDto = Result.build(outDto, CUSTOM_ACCOUNT_ENUM_NULL.getCode(),msg);
        return resultOutDto;
    }*/
}
