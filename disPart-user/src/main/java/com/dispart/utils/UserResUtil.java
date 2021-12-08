package com.dispart.utils;

import com.dispart.dto.ResultOutDto;
import com.dispart.dto.empdto.QuryBindRoleOutDto;
import com.dispart.result.Result;
import com.dispart.result.UserResultCodeEnum;
import lombok.extern.slf4j.Slf4j;

import static com.dispart.result.ResultCodeEnum.*;
import static com.dispart.result.UserResultCodeEnum.USER_PARAM_NULL;
@Slf4j
public class UserResUtil {
    public  static Result<ResultOutDto> getResultFailDto(UserResultCodeEnum enums) {
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

    public  static Result<ResultOutDto> paramFail(String msg) {
        ResultOutDto outDto = new ResultOutDto();
        outDto.setResult(ResultOutDto.FAIL);
        Result<ResultOutDto> resultOutDto = null;
        log.info("用户业务-"+msg);
        resultOutDto = Result.build(outDto, USER_PARAM_NULL.getCode(),msg);
        return resultOutDto;
    }
}
