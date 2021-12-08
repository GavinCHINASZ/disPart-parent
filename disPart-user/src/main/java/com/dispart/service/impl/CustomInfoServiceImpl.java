package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.dispart.dao.CustomAccountDao;
import com.dispart.dao.ICustomInfoDao;
import com.dispart.dao.TCustomInfoManagerDao;
import com.dispart.dto.ResultOutDto;
import com.dispart.dto.empdto.UserPlaceOrderTypeInDto;
import com.dispart.dto.empdto.QrcCodeDto;
import com.dispart.dto.empdto.UserQuryOrderTypeInDto;
import com.dispart.dto.empdto.UserQuryOrderTypeOutDto;
import com.dispart.dto.userdto.*;

import static com.dispart.enums.UserStatEnum.*;
import static com.dispart.enums.OrderTpEnum.*;

import com.dispart.enums.IsRealEnum;
import com.dispart.enums.OrderTpEnum;
import com.dispart.parmeterdto.DISP20210181MemberShipInfoInDto;
import com.dispart.request.Request;
import com.dispart.request.RequestHead;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.CustomInfoService;
import com.dispart.utils.*;
import com.dispart.vo.user.PlaceOrderTypeVo;
import com.dispart.vo.user.TCustomInfoManagerVo;
import com.dispart.vo.user.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.dispart.result.ResultCodeEnum.SUCCESS;
import static com.dispart.result.UserResultCodeEnum.*;

@Service
@Slf4j
public class CustomInfoServiceImpl implements CustomInfoService {
    @Resource
    ICustomInfoDao iCustomInfoDao;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private ZhiFuBaoUtil zhiFuBaoUtil;

    @Resource
    TCustomInfoManagerDao tCustomInfoManagerDao;

    @Resource
    CustomAccountDao customAccountDao;

    @Value("${custom.publicId}")
    private String publicId;

    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate2;

    /**
     *
     * @param userNo userNo
     * @param param QueryUserInfoInDto
     * @return Result<QueryUserInfoOutDto>
     */
    @Override
    public Result<QueryUserInfoOutDto> quryUserInfo(String userNo, QueryUserInfoInDto param) {
        Result<QueryUserInfoOutDto> resultOutDto = null;
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(USER_PARAM_NULL.getCode(), "输入参数为为空");
        }
//        if (StringUtils.isEmpty(param.getUserPhone())) {
//            return Result.build(USER_PARAM_NULL.getCode(), "用户手机号为空");
//        }
        if (StringUtils.isEmpty(userNo)) {
            return Result.build(USER_PARAM_NULL.getCode(), "用户id为空");
        }

        UserInfoVo vo = null;
        try {
            vo = iCustomInfoDao.quryUserInfoByUserId(userNo);
        } catch (DataAccessException e) {
            log.error("数据查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }

        if (ObjectUtils.isEmpty(vo)) {
            log.info("未查询到该用户");
            return Result.build(null, USER_DATA_NO_ERROR.getCode(), USER_DATA_NO_ERROR.getMessage());
        }
        QueryUserInfoOutDto outDto = new QueryUserInfoOutDto();
        outDto.setUserId(vo.getUserId());
        outDto.setUsrNm(vo.getUserNm());
        outDto.setUserIcon(vo.getUserIcon());
        outDto.setUserPhone(vo.getUserPhone());
        outDto.setUserNickNm(vo.getUserNickNm());
        resultOutDto = Result.build(outDto, ResultCodeEnum.SUCCESS);
        log.info("用户业务-查询用户信息成功");
        return resultOutDto;
    }

    /**
     *
     * @param param Request<UserAppCheckInDto>
     * @return Result<ResultOutDto>
     */
    @Override
    public Result<ResultOutDto> quryAppIdCheck(Request<UserAppCheckInDto> param) {
        int result = 0;
        if (ObjectUtils.isEmpty(param) || ObjectUtils.isEmpty(param.getBody())) {
            return UserResUtil.paramFail("输入参数为为空");
        }

        String chalNo = param.getHead().getChanlNo();

        if (chalNo.equals(WXCNO.getCode())) {
            // 微信小程序
            if (StringUtils.isEmpty(param.getBody().getWxpayId())) {
                return UserResUtil.paramFail("微信小程序唯一标识为空");
            }
            try {
                result = iCustomInfoDao.queryUserInfoByWxAppid(param.getBody().getWxpayId());
            } catch (DataAccessException e) {
                log.error("数据查询异常", e);
                throw new RuntimeException("数据库查询异常");
            }
        } else if (chalNo.equals(ZFBCNO.getCode())) {
            // 支付宝小程序
            if (StringUtils.isEmpty(param.getBody().getZfbpayId())) {
                return UserResUtil.paramFail("支付宝小程序唯一标识为空");
            }
            try {
                result = iCustomInfoDao.queryUserInfoByZfbAppid(param.getBody().getZfbpayId());
            } catch (DataAccessException e) {
                log.error("数据查询异常", e);
                throw new RuntimeException("数据库查询异常");
            }
        } else {
            return UserResUtil.paramFail("渠道不正确");
        }
        if (result < 1) {
            return UserResUtil.buildFail(USER_APP_INFO_NULL);
        }
        log.info("用户业务-小程序校验成功");
        return UserResUtil.getResultSuccessDto();
    }


    @Override
    public Result<QuryOrderCodeOutDto> quryOrderCode(QuryOrderCodeInDto param) {
        Result<QuryOrderCodeOutDto> resultOutDto = null;
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(USER_PARAM_NULL.getCode(), "输入参数为为空");
        }

        if (StringUtils.isEmpty(param.getProvId())&&StringUtils.isEmpty(param.getUserPhone())) {
            return Result.build(USER_PARAM_NULL.getCode(), "输入参数为为空");
        }
        QrcCodeDto rqcCodeDto = null;

        try {
            rqcCodeDto = iCustomInfoDao.selectQrcCOdeBylegaltel(param.getProvId(),param.getUserPhone());
        } catch (DataAccessException e) {
            log.error("数据查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (ObjectUtils.isEmpty(rqcCodeDto) || StringUtils.isEmpty(rqcCodeDto.getQrcodeUrl())) {
            return Result.build(USER_DATA_NO_ERROR.getCode(),USER_DATA_NO_ERROR.getMessage());
        }
        QuryOrderCodeOutDto outDto = new QuryOrderCodeOutDto();
        log.info("用户业务-查询单码成功");
        outDto.setPlaceOrderCode(rqcCodeDto.getQrcodeUrl());
        resultOutDto = Result.build(outDto, ResultCodeEnum.SUCCESS);
        return resultOutDto;
    }

    @Override
    @Transactional
    public Result<ResultOutDto> setPersonCard(String userNo, UserCardSetInDto param) {
        Result<ResultOutDto> resultOutDto = null;
        ResultOutDto outDto = new ResultOutDto();
        int result = 0;
        if (ObjectUtils.isEmpty(param)) {
            return UserResUtil.paramFail("输入参数为空");
        }
        if (StringUtils.isEmpty(param.getUpdateTp())) {
            return UserResUtil.paramFail("修改类型为空");
        }
        /*if (StringUtils.isEmpty(param.getUserPhone())) {
            return UserResUtil.paramFail("手机号码为空");
        }*/
        switch (param.getUpdateTp()) {
            case "0":// 修改昵称
                if (StringUtils.isEmpty(param.getUserNickNm())) {
                    return UserResUtil.paramFail("昵称为空");
                }
                try {
                    result = iCustomInfoDao.updateNickNm(param.getUserNickNm(), userNo);
                } catch (DataAccessException e) {
                    log.error("数据更新异常", e);
                    throw new RuntimeException("数据库更新异常");
                }
                break;
            case "1":// 修改头像
                if (StringUtils.isEmpty(param.getUserIcon())) {
                    return UserResUtil.paramFail("头像为空");
                }
                try {
                    result = iCustomInfoDao.updateIcon(param.getUserIcon(), userNo);
                } catch (DataAccessException e) {
                    log.error("数据更新异常", e);
                    throw new RuntimeException("数据库更新异常");
                }
                break;
//            case "2":// 修改手机号
//                if (StringUtils.isEmpty(param.getUserNewPhone())){
//                     return UserResUtil.paramFail(outDto,,"新手机号为空");
//                }
//                try {
//                result=  iCustomInfoDao.updatePhone(param.getUserNewPhone(),userNo);
//                } catch (DataAccessException e) {
//                   log.error("数据更新异常",e);
//                    throw new RuntimeException("数据库更新异常");
//                }
//                break;
            default:
                return UserResUtil.buildFail(USER_NONSUPPORT_OPER_TP);
        }

        if (result < 0) {
            return UserResUtil.buildFail(USER_UPDATE_PERSONCODE_FAIL);
        }
        log.info("修改个人名片信息成功");
        return UserResUtil.getResultSuccessDto();
    }

    /**
     * DISP20210003 用户注册账号
     *
     * @param request Request<UserRegAcctInDto>
     * @return Result<ResultOutDto>
     */
    @Override
    @Transactional
    public Result<ResultOutDto> userRegAcct(Request<UserRegAcctInDto> request) {
        ResultOutDto outDto = new ResultOutDto();
        if (request == null) {
            return UserResUtil.paramFail("输入参数为为空");
        }
        if (request.getBody() == null || request.getHead() == null) {
            return UserResUtil.paramFail("输入参数为为空");
        }
        if (StringUtils.isEmpty(request.getHead().getChanlNo())) {
            return UserResUtil.paramFail("渠道参数信息为空");
        }

        UserRegAcctInDto body = request.getBody();
        log.info("DISP20210003 用户注册账号body=" + body);

        // 渠道类型：01-贵农购 02-微信小程序 03-支付宝小程序 04-农批系统 05-智慧贵农app 06-外联服务
        if (request.getHead().getChanlNo().equals("02")) {
            // 微信注册
            log.info("用户业务-微信小程序注册");
            // 微信密文方式注册
            if (!StringUtils.isEmpty(body.getEncrypted())) {
                log.info("用户业务-微信小程序密文注册");
                if (StringUtils.isEmpty(body.getWxOpenId())) {
                    return UserResUtil.paramFail("登录标识错误");
                }
                if (StringUtils.isEmpty(body.getWxIv())) {
                    return UserResUtil.paramFail("输入参数为null");
                }
                //通过openId获取session_key
                String session_key = redisUtil.get(body.getWxOpenId());
                if (StringUtils.isEmpty(session_key)) {
                    return UserResUtil.buildFail(USER_LOGIN_FAIL);
                }

                // 解密微信密文
                String response = null;
                try {
                    response = WechatUtil.wxDecrypt(body.getEncrypted(), session_key, body.getWxIv());
                } catch (Exception e) {
                    log.error("解密异常", e);
                    return UserResUtil.buildFail(USER_LOGIN_FAIL);
                }
                if (null == response) {
                    return UserResUtil.buildFail(USER_LOGIN_FAIL);
                }

                Map<String, String> openapiResult = JSON.parseObject(response, new TypeReference<Map<String, String>>() {
                }, Feature.OrderedField);
                // 获取用户手机号
                String phoneNumber = openapiResult.get("phoneNumber");
                log.info("用户业务-微信密文解密手机号："+phoneNumber);
                if (StringUtils.isEmpty(phoneNumber)) {
                    log.info("用户业务-微信密文注册失败,解密手机号为空");
                    return UserResUtil.buildFail(USER_LOGIN_FAIL);
                }

                // 新增客户信息并开户,并获取客户编号
                String provId = "";
                try {
                    provId = this.addCustomAndOpenAcount(phoneNumber, request.getHead());
                } catch (Exception e) {
                    log.error("客户信息异常", e);
                    throw new RuntimeException("登录失败，请稍后再试！");
                }

                // 插入数据库表
                int result = insertUserInfo(phoneNumber, body.getWxOpenId(), request.getHead().getChanlNo(), null, provId);
                if (result != 1) {
                    return UserResUtil.buildFail(USER_LOGIN_FAIL);
                }
                log.info("用户业务-微信用户注册成功");
                return Result.ok();
            }

            log.info("用户业务-微信小程序手机号码验证码注册");
            if (StringUtils.isEmpty(body.getUserPhone())) {
                return UserResUtil.paramFail("手机号码输入错误");
            }
            if (StringUtils.isEmpty(body.getRegCode())) {
                return UserResUtil.paramFail("验证码输入错误");
            }
            if (StringUtils.isEmpty(body.getWxOpenId())) {
                return UserResUtil.paramFail("登录标识错误");
            }

            String smsCode = redisUtil.get(body.getUserPhone());
//            String smsCode="666666";
            if (StringUtils.isEmpty(smsCode) || Integer.parseInt(smsCode) != Integer.parseInt(body.getRegCode())) {
                return UserResUtil.buildFail(USER_REG_CODE_ERROR);
            }
//            UserInfoVo userInfoVo = null;
//            try {
//                //result = iCustomInfoDao.quryCountByPhone(body.getUserPhone());
//                userInfoVo = iCustomInfoDao.quryUserInfoByPhoneAndChanlNo(body.getUserPhone(),request.getHead().getChanlNo());
//            } catch (DataAccessException e) {
//                log.error("数据查询异常", e);
//                throw new RuntimeException("数据库查询异常");
//            }
//            if (userInfoVo != null) {
//                //验证手机号存在
//                log.info("用户业务-" + USER_BIND_TEL_EXIT.getMessage());
//                //验证是否绑定openid
//                if (!StringUtils.isEmpty(userInfoVo.getWxpayId())) {
//                    //openid 存在，该手机号已被使用
//                    return Result.build(USER_BIND_TEL_EXIT.getCode(), USER_BIND_TEL_EXIT.getMessage());
//                }
//                int falg = iCustomInfoDao.updateUserOpenId(body.getWxOpenId(), userInfoVo.getUserId(), new Date());
//                if (falg <= 0) {
//                    return UserResUtil.buildFail(USER_LOGIN_FAIL);
//                } else {
//                    return Result.ok();
//                }
//            }

            // 新增客户信息并开户,获取客户编号
            String provId = "";
            try {
                provId = this.addCustomAndOpenAcount(body.getUserPhone(), request.getHead());
            } catch (Exception e) {
                log.error("客户信息异常", e);
                throw new RuntimeException("登录失败，请稍后再试！");
            }

            // 插入数据库表
            int result = insertUserInfo(body.getUserPhone(), body.getWxOpenId(), request.getHead().getChanlNo(), null, provId);
            if (result != 1) {
                return UserResUtil.buildFail(USER_LOGIN_FAIL);
            }
            log.info("用户业务-微信用户注册成功");
            return Result.ok();
        }

        if (request.getHead().getChanlNo().equals("03")) {
            log.info("用户业务-支付宝小程序注册");
            if (!StringUtils.isEmpty(body.getResponse())) {
                log.info("用户业务-支付宝小程序密文注册");
                if (StringUtils.isEmpty(body.getZfbOpenId())) {
                    return UserResUtil.paramFail("支付宝唯一标示为空");
                }
                String response = null;
                try {
                    //解密支付宝返回报文
                    response = zhiFuBaoUtil.decode(body.getResponse());
                } catch (Exception e) {
                    log.error("解密异常", e);
                    return UserResUtil.buildFail(USER_LOGIN_FAIL);
                }
                if (null == response) {
                    return UserResUtil.buildFail(USER_LOGIN_FAIL);
                }
                Map<String, String> openapiResult = JSON.parseObject(response, new TypeReference<Map<String, String>>() {
                }, Feature.OrderedField);
                //获取用户手机号
                String mobile = openapiResult.get("mobile");
                if (StringUtils.isEmpty(mobile)) {
                    log.info("用户业务-支付宝密文注册失败,解密手机号为空");
                    return UserResUtil.buildFail(USER_LOGIN_FAIL);
                }

                // 新增客户信息并开户,获取客户编号
                String provId = "";
                try {
                    provId = this.addCustomAndOpenAcount(mobile, request.getHead());
                } catch (Exception e) {
                    log.error("客户信息异常", e);
                    throw new RuntimeException("登录失败，请稍后再试！");
                }

                // 插入数据库表
                int result = insertUserInfo(mobile, body.getZfbOpenId(), request.getHead().getChanlNo(), null, provId);
                if (result != 1) {
                    return UserResUtil.buildFail(USER_LOGIN_FAIL);
                }
                log.info("用户业务-微信用户注册成功");
                return Result.ok();

            }

            log.info("用户业务-支付宝小程序手机号码验证码注册");
            //支付宝小程序注册
            if (StringUtils.isEmpty(body.getUserPhone())) {
                return UserResUtil.paramFail("手机号码为空");
            }
            if (StringUtils.isEmpty(body.getRegCode())) {
                return UserResUtil.paramFail("验证码为空");
            }
            if (StringUtils.isEmpty(body.getZfbOpenId())) {
                return UserResUtil.paramFail("支付宝唯一标示为空");
            }
            String smsCode = redisUtil.get(body.getUserPhone());
//            String smsCode="666666";
            if (StringUtils.isEmpty(smsCode) || Integer.parseInt(smsCode) != Integer.parseInt(body.getRegCode())) {
                return UserResUtil.buildFail(USER_REG_CODE_ERROR);
            }
//            try {
//                result = iCustomInfoDao.quryCountByPhoneAndChanlNo(body.getUserPhone(),request.getHead().getChanlNo());
//            } catch (DataAccessException e) {
//                log.error("数据查询异常", e);
//                throw new RuntimeException("数据库查询异常");
//            }
//            if (result > 0) {
//                log.info("用户业务-手机号码重复注册");
//                return UserResUtil.buildFail(USER_NO_REPEAT_REG);
//            }

            // 新增客户信息并开户,获取客户编号
            String provId = "";
            try {
                provId = this.addCustomAndOpenAcount(body.getUserPhone(), request.getHead());
            } catch (Exception e) {
                log.error("客户信息异常", e);
                throw new RuntimeException("登录失败，请稍后再试！");
            }

            // 插入数据库表
            int result = insertUserInfo(body.getUserPhone(), body.getZfbOpenId(), request.getHead().getChanlNo(), null, provId);
            if (result != 1) {
                return UserResUtil.buildFail(USER_INSERT_ERROR);
            }
            log.info("用户业务-注册用户成功");
            return UserResUtil.getResultSuccessDto();

        } else if (request.getHead().getChanlNo().equals("01")) {
            log.info("用户业务-手机app端注册");
            //手机app端注册
            if (StringUtils.isEmpty(body.getRegCode())) {
                return UserResUtil.paramFail("验证码为空");
            }
            if (StringUtils.isEmpty(body.getUserPhone())) {
                return UserResUtil.paramFail("手机号码为空");
            }
            if (StringUtils.isEmpty(body.getUsPaWd())) {
                return UserResUtil.paramFail("密码为空");
            }
            if (StringUtils.isEmpty(body.getVerifyUsPaWd())) {
                return UserResUtil.paramFail("确认密码为空");
            }
            //确认密码失败
            if (!body.getUsPaWd().equals(body.getVerifyUsPaWd())) {
                log.info("用户业务-确认密码失败");
                return UserResUtil.buildFail(USER_VERIFY_PASSWD_FAIL);
            }
            Integer result = 0;
            try {
                result = iCustomInfoDao.quryCountByPhoneAndChanlNo(body.getUserPhone(), request.getHead().getChanlNo());
            } catch (DataAccessException e) {
                log.error("数据查询异常", e);
                throw new RuntimeException("数据库查询异常");
            }
            if (result > 0) {
                log.info("用户业务-手机号码重复注册");
                return UserResUtil.buildFail(USER_BIND_TEL_EXIT);
            }
            String backRegCode = redisUtil.get(body.getUserPhone());
            log.info("用户业务-验证码："+backRegCode);
//            String backRegCode = "666666"; //to do Redis获取
            if (StringUtils.isEmpty(backRegCode) || Integer.parseInt(body.getRegCode()) != Integer.parseInt(backRegCode)) {
                log.info("注册输入验证码校验失败！");
                return UserResUtil.buildFail(USER_REG_CODE_ERROR);
            }
            //Md5加密密码
            String passwd = null;
            try {
                passwd = Md5Util.getMd5(body.getUsPaWd());
            } catch (Exception e) {
                log.error("用户业务-密码加密失败", e);
                return Result.fail(outDto);
            }

            // 新增客户信息并开户,获取客户编号
            String provId = "";
            try {
                provId = this.addCustomAndOpenAcount(body.getUserPhone(), request.getHead());
            } catch (Exception e) {
                log.error("客户信息异常", e);
                throw new RuntimeException("注册失败！");
            }

            // 插入数据库表
            result = insertUserInfo(body.getUserPhone(), null, request.getHead().getChanlNo(), passwd, provId);
            if (result != 1) {
                return UserResUtil.buildFail(USER_INSERT_ERROR);
            }
            log.info("用户业务-注册用户成功");
            return UserResUtil.getResultSuccessDto();
        }
        return UserResUtil.buildFail(USER_NONSUPPORT_CHNAL_TP);
    }

    @Override
    @Transactional
    public Result<ResultOutDto> userReSetPasswd(Request<ReSetPasswdInDto> param) {
        Result<ResultOutDto> resultOutDto = null;
        ResultOutDto outDto = new ResultOutDto();
        outDto.setResult(ResultOutDto.FAIL);
        int result = 0;
        if (ObjectUtils.isEmpty(param)) {
            return UserResUtil.paramFail("输入参数为为空");
        }
        if (ObjectUtils.isEmpty(param.getHead())) {
            return UserResUtil.paramFail("输入参数为为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return UserResUtil.paramFail("输入参数为为空");
        }
        if (StringUtils.isEmpty(param.getBody().getNewUsPaWd())) {
            return UserResUtil.paramFail("新密码为空");
        }
        if (StringUtils.isEmpty(param.getBody().getUserPhone())) {
            return UserResUtil.paramFail("手机号码为空");

        }
        if (StringUtils.isEmpty(param.getBody().getVerifyUsPaWd())) {
            return UserResUtil.paramFail("确认密码为空");
        }
        if (StringUtils.isEmpty(param.getBody().getRegCode())) {
            return UserResUtil.paramFail("验证码为空");
        }
        //确认密码失败
        if (!param.getBody().getNewUsPaWd().equals(param.getBody().getVerifyUsPaWd())) {
            return UserResUtil.buildFail(USER_VERIFY_PASSWD_FAIL);
        }
        String backRegCode = redisUtil.get(param.getBody().getUserPhone());
//        log.info("用户业务-验证码："+backRegCode);
//        String backRegCode = "666666"; //to do Redis获取
        if (StringUtils.isEmpty(backRegCode) || Integer.parseInt(param.getBody().getRegCode()) != Integer.parseInt(backRegCode)) {
            return UserResUtil.buildFail(USER_REG_CODE_ERROR);
        }
        //Md5加密密码
        String newPasswd = null;
        try {
            newPasswd = Md5Util.getMd5(param.getBody().getNewUsPaWd());
        } catch (Exception e) {
            log.info("用户业务-密码加密失败");
            log.error("密码加密失败", e);
            return Result.fail(outDto);
        }

        try {
            result = iCustomInfoDao.quryCountByPhoneAndChanlNo(param.getBody().getUserPhone(), param.getHead().getChanlNo());
        } catch (DataAccessException e) {
            log.error("数据查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (result <= 0) {
            log.info("用户业务-查询用户信息不存在");
            return UserResUtil.buildFail(USER_INFO_NULL);
        }
//        if (newPasswd.equals(vo.getUserPasswd())) {
//            log.info("用户业务-原密码和新密码一样");
//            return UserResUtil.buildFail(USER_PASSWD_SAME_ERROR);
//        }
        //更新密码
        try {
            result = iCustomInfoDao.updateUserPasswdByPhoneAndChanlNo(param.getBody().getUserPhone(), newPasswd, new Date(), param.getHead().getChanlNo());
        } catch (DataAccessException e) {
            log.error("数据更新异常", e);
            throw new RuntimeException("数据库更新异常");
        }
        if (result != 1) {
            log.info("用户业务-重置密码失败");
            return UserResUtil.buildFail(USER_UPDATE_FAIL);
        }
        log.info("用户业务-重置密码成功");
        return UserResUtil.getResultSuccessDto();
    }

    @Override
    @Transactional
    public Result<ResultOutDto> userUpdatePasswd(String userNo, UpdatePasswdInDto param) {
        Result<ResultOutDto> resultOutDto = null;
        ResultOutDto outDto = new ResultOutDto();
        outDto.setResult(ResultOutDto.FAIL);
        int result = 0;
        if (ObjectUtils.isEmpty(param)) {
            return UserResUtil.paramFail("输入参数为为空");
        }
        if (StringUtils.isEmpty(param.getNewUsPaWd())) {
            return UserResUtil.paramFail("用户新密码为空");
        }
        if (StringUtils.isEmpty(param.getOldUsPaWd())) {
            return UserResUtil.paramFail("用户原密码为空");
        }
//        if (StringUtils.isEmpty(param.getUserPhone())) {
//            return UserResUtil.paramFail("手机号码为空");
//        }
        if (StringUtils.isEmpty(param.getVerifyUsPaWd())) {
            return UserResUtil.paramFail("确认密码为空");
        }

        //确认密码失败
        if (!param.getNewUsPaWd().equals(param.getVerifyUsPaWd())) {
            log.info("用户业务-确认密码失败");
            return UserResUtil.buildFail(USER_VERIFY_PASSWD_FAIL);
        }

        //Md5加密密码
        String oldPasswd = null;
        try {
            oldPasswd = Md5Util.getMd5(param.getOldUsPaWd());
        } catch (Exception e) {
            log.error("用户业务-密码加密失败", e);
            return Result.fail(outDto);
        }

        UserInfoVo vo = null;
        try {
            vo = iCustomInfoDao.quryUserInfoByUserId(userNo);
        } catch (DataAccessException e) {
            log.error("数据查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }

        if (ObjectUtils.isEmpty(vo)) {
            log.info("用户业务-用户信息不存在");
            return UserResUtil.buildFail(USER_INFO_NULL);
        }
        if (!oldPasswd.equals(vo.getUserPasswd())) {
            log.info("用户业务-用户密码错误");
            return UserResUtil.buildFail(USER_PASSWD_ERROR);
        }

        //Md5加密密码
        String newPasswd = null;
        try {
            newPasswd = Md5Util.getMd5(param.getNewUsPaWd());
        } catch (Exception e) {
            log.error("用户业务-密码加密失败", e);
            return Result.fail(outDto);
        }

        if (newPasswd.equals(oldPasswd)) {
            log.info("用户业务-原密码和新密码一样");
            return UserResUtil.buildFail(USER_PASSWD_SAME_ERROR);
        }
        try {
            result = iCustomInfoDao.updateUserPasswd(userNo, newPasswd, new Date());
        } catch (DataAccessException e) {
            log.error("数据更新异常", e);
            throw new RuntimeException("数据库更新异常");
        }
        if (result < 1) {
            log.info("用户业务-修改密码失败");
            return UserResUtil.buildFail(USER_UPDATE_FAIL);
        }
        log.info("用户业务-修改密码成功");
        return UserResUtil.getResultSuccessDto();
    }


    @Override
    @Transactional
    public Result<ResultOutDto> setPlaceOrderType(UserPlaceOrderTypeInDto param) {
        StringBuffer updateFlg = new StringBuffer("0");//更新标志  0未更新，不用更新 1已更新
        Result<ResultOutDto> resultOutDto = null;
        int result = 0;
        if (ObjectUtils.isEmpty(param)) {
            return UserResUtil.paramFail("输入参数为为空");
        }
        if (StringUtils.isEmpty(param.getProvId())) {
            return UserResUtil.paramFail("供货商id为空");
        }
        if (StringUtils.isEmpty(param.getEasyTpSt())) {
            return UserResUtil.paramFail("简易模式状态为空");
        }
        if (StringUtils.isEmpty(param.getDatilTpSt())) {
            return UserResUtil.paramFail("明细模式状态为空");
        }
        //处理判断
        String combineTp = param.getEasyTpSt() + param.getDatilTpSt();
        switch (combineTp) {
            case "00"://不能两种模式都无效
                return UserResUtil.buildFail(USER_PARAM_ERROR, "两种模式不能都无效");
            case "01"://简易无效，明细生效
                resultOutDto = this.updateOrderTp(param, DATIL, updateFlg);
                if (!updateFlg.toString().equals("1")) {
                    log.info("用户业务-已存在相同类型下单模式配置");
                    return UserResUtil.buildFail(USER_ORDER_TP_IDENT);
                }
                log.info("用户业务-配置下单模式成功");
                return resultOutDto;
            case "10"://简易有效，明细无效
                resultOutDto = this.updateOrderTp(param, EASY, updateFlg);
                if (!updateFlg.toString().equals("1")) {
                    log.info("用户业务-已存在相同类型下单模式配置");
                    return UserResUtil.buildFail(USER_ORDER_TP_IDENT);
                }
                log.info("用户业务-配置下单模式成功");
                return resultOutDto;
            case "11"://简易明细都有效
                resultOutDto = this.updateOrderTp(param, ALL, updateFlg);
                if (!updateFlg.toString().equals("1")) {
                    log.info("用户业务-已存在相同类型下单模式配置");
                    return UserResUtil.buildFail(USER_ORDER_TP_IDENT);
                }
                log.info("用户业务-配置下单模式成功");
                return resultOutDto;
            default:
                log.info("用户业务-不支持的操作类型");
                return UserResUtil.buildFail(USER_NONSUPPORT_OPER_TP);
        }
    }

    @Override
    public Result<UserQuryOrderTypeOutDto> quryUserPlaceOrderType(UserQuryOrderTypeInDto param) {
        UserQuryOrderTypeOutDto outDto = new UserQuryOrderTypeOutDto();
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(USER_PARAM_NULL.getCode(), "输入参数为为空");
        }
        if (StringUtils.isEmpty(param.getProvId())) {
            return Result.build(USER_PARAM_NULL.getCode(), "供货商id为空");
        }
        PlaceOrderTypeVo vo = null;
        //通过商户id和下单模式查询商户下单模式信息
        try {
            vo = iCustomInfoDao.selectTpByProvID(param.getProvId(), EASY.getCode());
        } catch (DataAccessException e) {
            log.error("数据查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }

        if (!ObjectUtils.isEmpty(vo)) {
            outDto.setEasyTpSt(vo.getStatus());
        } else {
            outDto.setEasyTpSt(AVAIL.getCode());//未设置，反回为无效
        }

        //通过商户id和下单模式查询商户下单模式信息
        try {
            vo = iCustomInfoDao.selectTpByProvID(param.getProvId(), DATIL.getCode());
        } catch (DataAccessException e) {
            log.error("数据查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (!ObjectUtils.isEmpty(vo)) {
            outDto.setDatilTpSt(vo.getStatus());
        } else {
            outDto.setDatilTpSt(AVAIL.getCode());//未设置，反回为无效
        }
        log.info("用户业务-查询下单模式成功");
        return Result.build(outDto, SUCCESS);
    }

    /**
     * @param param
     * @param type
     * @return
     */
    public Result<ResultOutDto> updateOrderTp(UserPlaceOrderTypeInDto param, OrderTpEnum type, StringBuffer updateFlg) {
        if (!type.equals(ALL)) {//只生效其中一种，另一种为效
            updatOrderTpByProvid(param, type, updateFlg);//生效下单模式
            updateOhterTp(param, type, updateFlg);//设置另一种模式无效
        } else {//两种模式都生效
            updatOrderTpByProvid(param, DATIL, updateFlg);//生效明细下单模式
            updatOrderTpByProvid(param, EASY, updateFlg);//生效简易下单模式
        }
        return UserResUtil.getResultSuccessDto();
    }

    /**
     * 根据设置类型进行更新下单模式
     *
     * @param param
     * @param type
     * @return
     */
    public void updateOhterTp(UserPlaceOrderTypeInDto param, OrderTpEnum type, StringBuffer updateFlg) {
        int result = 0;
        OrderTpEnum tpEnum = type.equals(EASY) ? DATIL : EASY;
        PlaceOrderTypeVo effeVo = null;
        PlaceOrderTypeVo availVo = null;
        //查询另一种下单模式条件为无效
        try {
            effeVo = iCustomInfoDao.selectTpByProvIDAndStat(param.getProvId(), tpEnum.getCode(), EFFE.getCode());
        } catch (DataAccessException e) {
            log.error("数据查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (!ObjectUtils.isEmpty(effeVo)) {
            //更新为失效
            try {
                result = iCustomInfoDao.updateStatusByProvID(param.getProvId(), tpEnum.getCode(), AVAIL.getCode(), new Date());//更新为无效
            } catch (DataAccessException e) {
                log.error("数据更新异常", e);
                throw new RuntimeException("数据库更新异常");
            }
            if (result != 1) {
                log.info("用户业务-更新下单模式失败");
                throw new RuntimeException("更新下单模式状态失败");
            }
            updateFlg.setLength(0);
            updateFlg.append("1");
        } else if (ObjectUtils.isEmpty(effeVo)) {
            //查询另一种下单模式条件为无效
            try {
                availVo = iCustomInfoDao.selectTpByProvIDAndStat(param.getProvId(), tpEnum.getCode(), AVAIL.getCode());
            } catch (DataAccessException e) {
                log.error("数据查询异常", e);
                throw new RuntimeException("数据库查询异常");
            }
            if (ObjectUtils.isEmpty(availVo)) {
                //插入无效的模式
                PlaceOrderTypeVo vo = new PlaceOrderTypeVo();
                vo.setPlaceOrderMd(tpEnum.getCode());
                vo.setProvId(param.getProvId());
                vo.setStatus(AVAIL.getCode());//有效
                vo.setPlaceOrderNm(tpEnum.getDesc());
                vo.setUpdateDt(new Date());
                try {
                    result = iCustomInfoDao.insertPlaceOrderTp(vo);
                } catch (DataAccessException e) {
                    log.error("数据插入异常", e);
                    throw new RuntimeException("数据库插入异常");
                }
                if (result != 1) {
                    log.info("用户业务-配置下单模式，插入数据库失败");
                    throw new RuntimeException("数据库插入异常");
                }
            }
        }

    }

    /**
     * 更新操作
     *
     * @param param
     * @param type
     * @return
     */
    public void updatOrderTpByProvid(UserPlaceOrderTypeInDto param, OrderTpEnum type, StringBuffer updateFlg) {
        int result = 0;
        PlaceOrderTypeVo vo = null;
        //通过商户id和下单模式查询商户下单模式信息
        try {
            vo = iCustomInfoDao.selectTpByProvID(param.getProvId(), type.getCode());
        } catch (DataAccessException e) {
            log.error("数据插入异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        //为空配置插入数据库
        if (ObjectUtils.isEmpty(vo)) {
            vo = new PlaceOrderTypeVo();
            vo.setPlaceOrderMd(type.getCode());
            vo.setProvId(param.getProvId());
            vo.setStatus(EFFE.getCode());//有效
            vo.setPlaceOrderNm(type.getDesc());
            vo.setUpdateDt(new Date());
            try {
                result = iCustomInfoDao.insertPlaceOrderTp(vo);
            } catch (DataAccessException e) {
                log.error("数据更新异常", e);
                throw new RuntimeException("数据库插入异常");
            }
            if (result != 1) {
                log.info("用户业务-配置下单模式，插入数据库失败");
                throw new RuntimeException("数据库插入异常");
            }
            updateFlg.setLength(0);
            updateFlg.append("1");

        } else {
            //状态不为有效，更新为有效
            if (!EFFE.getCode().equals(vo.getStatus())) {
                try {
                    result = iCustomInfoDao.updateStatusByProvID(param.getProvId(), type.getCode(), EFFE.getCode(), new Date());//更新为有效
                } catch (DataAccessException e) {
                    log.error("数据插入异常", e);
                    throw new RuntimeException("数据库插入异常");
                }
                if (result != 1) {
                    throw new RuntimeException("更新下单模式状态失败");
                }
                updateFlg.setLength(0);
                updateFlg.append("1");
            }

        }
    }

    public int insertUserInfo(String phone, String openId, String chanlNo, String passwd, String provId) {
        UserInfoVo vo = new UserInfoVo();
        // 用户编号
        Integer userIdseq = iCustomInfoDao.selectUserIdSeq();
        String userId = String.format("%0" + 8 + "d", userIdseq);
        vo.setUserId(userId);
        vo.setRegDt(new Date());
        vo.setUserPhone(phone);
        vo.setUpdateDt(new Date());
        vo.setProvId(provId);// 客户编号
        vo.setUserSt(NORMAR.getCode());// 正常
        if (chanlNo.equals("02")) {
            // 微信
            vo.setWxpayId(openId);
        }
        if (chanlNo.equals("03")) {
            // 支付宝
            vo.setAlipayId(openId);
        }
        if (!StringUtils.isEmpty(passwd)) {
            vo.setUserPasswd(passwd);
        }
        //渠道号
        vo.setChanlNo(chanlNo);
        int result = 0;
        try {
            result = iCustomInfoDao.insertUserInfo(vo);
        } catch (DataAccessException e) {
            log.error("数据插入异常", e);
            throw new RuntimeException("数据库插入异常");
        }
        return result;
    }

    @Override
    public Result opraerCustomAndOpenAcount (Request<UserPhone> param){
        return  Result.ok(this.addCustomAndOpenAcount(param.getBody().getUserPhone(), param.getHead()));
    }

    /**
     * 新增客户信息并开户
     *
     * @param userPhone 电话号码
     * @param requestHead RequestHead
     * @return String
     */
    private String addCustomAndOpenAcount(String userPhone, RequestHead requestHead) {
        // 查询客户信息是否存在
        List<TCustomInfoManagerVo> customVoList = null;
        try {
            customVoList = tCustomInfoManagerDao.selectCustomInfoByPhone(userPhone);
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        int findAcctInfo = 0;

        TCustomInfoManagerVo tCustomInfoManagerVo = null;
        // 为空新增客户信息
        if (null == customVoList || customVoList.size() <= 0) {
            tCustomInfoManagerVo = new TCustomInfoManagerVo();

            int seq = tCustomInfoManagerDao.selectCustomIdSeq();
            // 客户编号 6005000 +8位顺序号
            String customId = publicId + String.format("%0" + 8 + "d", seq);
            tCustomInfoManagerVo.setProvId(customId);
            tCustomInfoManagerVo.setPhone(userPhone);
            tCustomInfoManagerVo.setLegalPhone(userPhone);
            tCustomInfoManagerVo.setCreatTime(new Date());
            tCustomInfoManagerVo.setUpTime(new Date());
            tCustomInfoManagerVo.setOperId(requestHead.getOperator());
            tCustomInfoManagerVo.setIsReal(IsRealEnum.NOREAL.getCode());
            tCustomInfoManagerVo.setIsReal("1");//未实名
            tCustomInfoManagerVo.setStatus("0");//启用
            tCustomInfoManagerVo.setChanlNo(requestHead.getChanlNo());

            try {
                int result = tCustomInfoManagerDao.insert(tCustomInfoManagerVo);
                if (result != 1) {
                    throw new RuntimeException("数据库插入异常");
                }
            } catch (DataAccessException e) {
                log.error("数据库插入异常", e);
                throw new RuntimeException("数据库插入异常");
            }
        } else {
            tCustomInfoManagerVo = customVoList.get(0);
            try {
                findAcctInfo = customAccountDao.queryCountByProvId(tCustomInfoManagerVo.getProvId());
            } catch (DataAccessException e) {
                log.error("数据库查询异常", e);
                throw new RuntimeException("数据库查询异常");
            }
        }

        if (findAcctInfo <= 0) {//该客户未开户
            //请求参数
            //开户默认密码，手机号后六位
            String passwd = userPhone.substring(userPhone.length() - 6);
            //Md5加密密码
//            try {
//                passwd = Md5Util.getMd5(passwd);
//            } catch (Exception e) {
//                log.error("用户业务-密码加密失败", e);
//                throw new RuntimeException("密码加密失败");
//            }

            DISP20210181MemberShipInfoInDto inDto = new DISP20210181MemberShipInfoInDto();
            inDto.setProvId(tCustomInfoManagerVo.getProvId());//客户编号
            inDto.setCardTp("1");//虚拟卡
            inDto.setCashIndent("00");//00-无押金
            inDto.setPhone(userPhone);//手机号
            inDto.setPasswd(passwd);//密码
            Request<DISP20210181MemberShipInfoInDto> findDepByParam = new Request<>();
            findDepByParam.setHead(null);
            findDepByParam.setBody(inDto);

            // 请求头
            RequestHead head = new RequestHead();
            head.setChanlNo(requestHead.getChanlNo());
            head.setOperator(requestHead.getOperator());
            head.setReqSeqNo(DateUtil.getReqSeqNo());
            head.setReqDate(requestHead.getReqDate());
            head.setVersionNo(requestHead.getVersionNo());
            findDepByParam.setHead(head);
            log.info("用户业务-请求开户服务流水号reqSeqNo："+DateUtil.getReqSeqNo());

            // 调用开户服务
            try {
                String result = restTemplate2.postForObject("http://" + "disPart-user" + "/securityCenter/DISP20210181",
                        findDepByParam, String.class);

                log.info("用户业务-返回报文：{}", result);
                Map<String, String> resultMap = JSON.parseObject(result, new TypeReference<Map<String, String>>() {
                }, Feature.OrderedField);
                String code = resultMap.get("code");
                String data = resultMap.get("data");
                Map<String, String> dataMap = JSON.parseObject(data, new TypeReference<Map<String, String>>() {
                }, Feature.OrderedField);
                if (!code.equals("200")) {
                    throw new RuntimeException("开户失败");
                }
                String resultStat = dataMap.get("result");
                log.info("用户业务-开户返回结果0-成功，1-失败：{}", resultStat);
                if (!resultStat.equals("0")) {
                    throw new RuntimeException("开户失败");
                }
            } catch (Exception e) {
                log.error("调用开户服务失败", e);
                throw new RuntimeException("调用开户服务失败");
            }

        }
        return tCustomInfoManagerVo.getProvId();
    }

}
