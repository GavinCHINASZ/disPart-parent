package com.dispart.service;

import com.dispart.dto.ResultOutDto;
import com.dispart.dto.empdto.UserPlaceOrderTypeInDto;
import com.dispart.dto.empdto.UserQuryOrderTypeInDto;
import com.dispart.dto.empdto.UserQuryOrderTypeOutDto;
import com.dispart.dto.userdto.*;
import com.dispart.request.Request;
import com.dispart.result.Result;

public interface CustomInfoService {
   /**
    * 用户查询
    * @param param
    * @return
    */
   public Result<QueryUserInfoOutDto> quryUserInfo(String userNo,QueryUserInfoInDto param);

   /**
    * 小程校验
    * @param param
    * @return
    */
   public Result<ResultOutDto> quryAppIdCheck(Request<UserAppCheckInDto> param);

   /**
    * 用户下单码查询
    * @param param
    * @return
    */
   public Result<QuryOrderCodeOutDto>  quryOrderCode(QuryOrderCodeInDto param);

   /**
    * 用户个人名片设置
    * @param param
    * @return
    */
   public Result<ResultOutDto> setPersonCard(String userNo, UserCardSetInDto param);

   /**
    * 用户注册账号
    * @param param
    * @return
    */
    public Result<ResultOutDto>   userRegAcct(Request<UserRegAcctInDto> param);

   /**
    * 处理检测客户信息和账户信息
    * @param param
    * @return
    */
    public Result opraerCustomAndOpenAcount (Request<UserPhone> param);
   /**
    * 用户重置密码
    * @param param
    * @return
    */
   public Result<ResultOutDto>   userReSetPasswd(Request<ReSetPasswdInDto>  param);

   /**
    * 用户修改密码
    * @param param
    * @return
    */
   public Result<ResultOutDto>   userUpdatePasswd(String userNo,UpdatePasswdInDto param);

   /**
    * 用户设置下单模式
    * @param param
    * @return
    */
   public Result<ResultOutDto>   setPlaceOrderType(UserPlaceOrderTypeInDto param);

   /**
    * 查询客户下单模式
    * @param param
    * @return
    */
   public Result<UserQuryOrderTypeOutDto>  quryUserPlaceOrderType(UserQuryOrderTypeInDto param);
 }
