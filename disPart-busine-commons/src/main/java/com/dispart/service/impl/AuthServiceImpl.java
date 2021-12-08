package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dispart.controller.AuthController;
import com.dispart.dao.*;
import com.dispart.dto.auth.*;
import com.dispart.dto.smsdto.SmsDto;
import com.dispart.dto.userdto.UserPhone;
import com.dispart.request.Request;
import com.dispart.request.RequestHead;
import com.dispart.result.BusineConmonCodeEnum;
import com.dispart.result.Result;
import com.dispart.service.AuthService;
import com.dispart.utils.*;
import com.dispart.vo.commons.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author:xts
 * @date:Created in 2021/6/19 19:23
 * @description 对接安全中心接口
 * @modified by:
 * @version: 1.0
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate;
    @Value("${myAuth.pushRoleUrl}")
    private String pushRoleUrl;
    @Value("${myAuth.pushDepUrl}")
    private String pushDepUrl;
    @Value("${out-common.wx_zfb.loginAuthUrl}")
    private String loginAuthUrl;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private TRoleInfoDao tRoleInfoDao;
    @Resource
    private TRoleMenuInfoDao tRoleMenuInfoDao;
    @Resource
    private TMenuAuthInfoDao tMenuAuthInfoDao;
    @Resource
    private TMenuInfoDao tMenuInfoDao;
    @Resource
    private TDepartmentInfoDao tDepartmentInfoDao;
    @Resource
    private TDepOrgMenuInfoDao tDepOrgMenuInfoDao;
    @Resource
    private TUserInfoDao tUserInfoDao;
    @Resource
    private TEmployeeInfoDao tEmployeeInfoDao;
    @Resource
    private TEmployeeRoleInfoDao tEmployeeRoleInfoDao;
    @Resource
    private ITEmployeeInfoDao itEmployeeInfoDao;
    @Resource
    private ITEmployeeRoleInfoDao itEmployeeRoleInfoDao;
    @Resource
    private ITRoleMenuInfoDao itRoleMenuInfoDao;

    /**
     * 推送角色权限信息
     *
     * @return
     */
    @Override
    public Result getRoleAuth() {
        List<RoleAuthDto> roleAuthDtos = new ArrayList<>();
        //获取所有的角色信息（pc管理端和移动管理端的角色）
        List<TRoleInfo> tRoleInfos = tRoleInfoDao.queryAll(new TRoleInfo());
        if (tRoleInfos == null || tRoleInfos.size() == 0) {
            log.info("业务区-公共服务-未查询到角色相关信息");
            return Result.build(BusineConmonCodeEnum.NOT_SELECT_INFO.getCode(), BusineConmonCodeEnum.NOT_SELECT_INFO.getMessage());
        }
        for (TRoleInfo tRoleInfo : tRoleInfos) {
            //设置角色信息
            RoleAuthDto roleAuthDto = new RoleAuthDto();
            roleAuthDto.setRoleCode(tRoleInfo.getRoleId());
            roleAuthDto.setRoleName(tRoleInfo.getRoleNm());
            List<AuthDto> auths = new ArrayList<>();
            String baseAuthList[] = this.getBaseAuthUrl().split(",");
            for (int i = 0; i < baseAuthList.length; i++) {
                AuthDto authDto1 = new AuthDto();
                authDto1.setAuthType("3");//权限点
                authDto1.setAuthCode("00" + i + 1 + "");
                authDto1.setAuthUrl(baseAuthList[i]);
                authDto1.setId(i + 1 + "");
                authDto1.setPid("0" + i + 1 + "");
                auths.add(authDto1);
            }
            //处理角色对于的权限集合
            //先获取角色对于的菜单
           /* TRoleMenuInfo tRoleMenuInfo=new TRoleMenuInfo();
            tRoleMenuInfo.setRoleId(tRoleInfo.getRoleId());
            List<TRoleMenuInfo> tRoleMenuInfos=tRoleMenuInfoDao. queryAll( tRoleMenuInfo);
            if(tRoleMenuInfos!=null&&tRoleMenuInfos.size()>0){
                for(TRoleMenuInfo tRoleMenuInfo1:tRoleMenuInfos){
                    //设置菜单对应的权限信息
                    AuthDto authDto=new AuthDto();
                    TMenuInfo tMenuInfo=tMenuInfoDao. queryById(tRoleMenuInfo1.getMenuId());
                    if(tMenuInfo.getMenuType().equals("0")){
                        //按钮菜单
                        authDto.setAuthType("2");
                    }else if (tMenuInfo.getMenuType().equals("1")){
                        //目录菜单
                        authDto.setAuthType("4");
                    }else{
                        //导航菜单
                        authDto.setAuthType("1");
                    }
                    authDto.setAuthCode(tMenuInfo.getMenuId());
                    authDto.setAuthUrl(tMenuInfo.getUrl());
                    authDto.setId(tRoleMenuInfo1.getMenuId());
                    authDto.setPid(tMenuInfo.getParentMenuId());
                    auths.add(authDto);
                    //获取菜单对应的权限点信息
                    TMenuAuthInfo tMenuAuthInfo=new TMenuAuthInfo();
                    tMenuAuthInfo.setMenuId(tRoleMenuInfo1.getMenuId());
                    List<TMenuAuthInfo> tMenuAuthInfos=tMenuAuthInfoDao. queryAll( tMenuAuthInfo);
                    if(tMenuAuthInfos!=null&&tMenuAuthInfos.size()>0){
                        //设置菜单对应的权限点信息
                        for(TMenuAuthInfo tMenuAuthInfo1:tMenuAuthInfos){
                            AuthDto authDto1=new AuthDto();
                            authDto1.setAuthType("3");//权限点
                            authDto1.setAuthCode(tMenuAuthInfo1.getAuthId()+"");
                            authDto1.setAuthUrl(tMenuAuthInfo1.getUrl());
                            authDto1.setId(tRoleMenuInfo1.getMenuId());
                            authDto1.setPid(tMenuInfo.getParentMenuId());
                            auths.add(authDto1);
                        }
                    }
                }
            }*/
            roleAuthDto.setAuthList(auths);
            roleAuthDtos.add(roleAuthDto);
        }
        //设置用户的权限
        roleAuthDtos.add(getUserRoleAuthDto());
        log.info("业务公共服务-回调角色权限信息给安全中心，推送数据，{}", JSONObject.toJSONString(roleAuthDtos));
        return Result.ok(roleAuthDtos);
    }

    @Override
    public Result pushRoleAuth(String roleId) {
        RoleAuthDto resultRoleAuthDto = new RoleAuthDto();
        //获取所有的角色信息（pc管理端和移动管理端的角色）
        TRoleInfo role = new TRoleInfo();
        role.setRoleId(roleId);
        List<TRoleInfo> tRoleInfos = tRoleInfoDao.queryAll(role);
        if (tRoleInfos == null || tRoleInfos.size() == 0) {
            log.info("业务区-公共服务-未查询到角色相关信息");
            return Result.build(BusineConmonCodeEnum.NOT_SELECT_INFO.getCode(), BusineConmonCodeEnum.NOT_SELECT_INFO.getMessage());
        }
        for (TRoleInfo tRoleInfo : tRoleInfos) {
            //设置角色信息
            RoleAuthDto roleAuthDto = new RoleAuthDto();
            roleAuthDto.setRoleCode(tRoleInfo.getRoleId());
            roleAuthDto.setRoleName(tRoleInfo.getRoleNm());
            List<AuthDto> auths = new ArrayList<>();
            String baseAuthList[] = this.getBaseAuthUrl().split(",");
            for (int i = 0; i < baseAuthList.length; i++) {
                AuthDto authDto1 = new AuthDto();
                authDto1.setAuthType("3");//权限点
                authDto1.setAuthCode("00" + i + 1 + "");
                authDto1.setAuthUrl(baseAuthList[i]);
                authDto1.setId(i + 1 + "");
                authDto1.setPid("0" + i + 1 + "");
                auths.add(authDto1);
            }


            /*List<AuthDto> auths=new ArrayList<>();
            //处理角色对于的权限集合
            //先获取角色对于的菜单
            TRoleMenuInfo tRoleMenuInfo=new TRoleMenuInfo();
            tRoleMenuInfo.setRoleId(tRoleInfo.getRoleId());
            List<TRoleMenuInfo> tRoleMenuInfos=tRoleMenuInfoDao. queryAll( tRoleMenuInfo);
            if(tRoleMenuInfos!=null&&tRoleMenuInfos.size()>0){
                for(TRoleMenuInfo tRoleMenuInfo1:tRoleMenuInfos){
                    //设置菜单对应的权限信息
                    AuthDto authDto=new AuthDto();
                    TMenuInfo tMenuInfo=tMenuInfoDao. queryById(tRoleMenuInfo1.getMenuId());
                    if(tMenuInfo.getMenuType().equals("0")){
                        //按钮菜单
                        authDto.setAuthType("2");
                    }else if (tMenuInfo.getMenuType().equals("1")){
                        //目录菜单
                        authDto.setAuthType("4");
                    }else{
                        //导航菜单
                        authDto.setAuthType("1");
                    }
                    authDto.setAuthCode(tMenuInfo.getMenuId());
                    authDto.setAuthUrl(tMenuInfo.getUrl());
                    authDto.setId(tRoleMenuInfo1.getMenuId());
                    authDto.setPid(tMenuInfo.getParentMenuId());
                    auths.add(authDto);
                    //获取菜单对应的权限点信息
                    TMenuAuthInfo tMenuAuthInfo=new TMenuAuthInfo();
                    tMenuAuthInfo.setMenuId(tRoleMenuInfo1.getMenuId());
                    List<TMenuAuthInfo> tMenuAuthInfos=tMenuAuthInfoDao. queryAll( tMenuAuthInfo);
                    if(tMenuAuthInfos!=null&&tMenuAuthInfos.size()>0){
                        //设置菜单对应的权限点信息
                        for(TMenuAuthInfo tMenuAuthInfo1:tMenuAuthInfos){
                            AuthDto authDto1=new AuthDto();
                            authDto1.setAuthType("3");//权限点
                            authDto1.setAuthCode(tMenuAuthInfo1.getAuthId()+"");
                            authDto1.setAuthUrl(tMenuAuthInfo1.getUrl());
                            authDto1.setId(tRoleMenuInfo1.getMenuId());
                            authDto1.setPid(tMenuInfo.getParentMenuId());
                            auths.add(authDto1);
                        }
                    }
                }
            }*/
            roleAuthDto.setAuthList(auths);
            resultRoleAuthDto = roleAuthDto;
            break;
        }
        //请求安全中心
        log.info("业务公共服务-推送增量角色权限信息给安全中心，推送数据，{}", JSONObject.toJSONString(resultRoleAuthDto));
        try {
            String resultStr = HttpRequest.httpPost(pushRoleUrl, JSONObject.toJSONString(resultRoleAuthDto));
            if (StringUtils.isBlank(resultStr)) {
                return Result.fail();
            }
            JSONObject jsonObject = JSONObject.parseObject(resultStr);
            if (jsonObject.getString("code").equals("200")) {
                return Result.ok();
            } else {
                return Result.build(Integer.parseInt(jsonObject.getString("code")), jsonObject.getString("msg"));
            }
        } catch (Exception e) {
            log.info("业务公共服务-推送增量角色权限信息给安全中心,请求异常,{}", e);
        }
        return Result.ok();
    }

    /**
     * 推送部门权限信息
     *
     * @return
     */
    @Override
    public Result getDepAuth() {
        List<OrgAuthDto> orgAuthDtos = new ArrayList<>();
        //获取所有部门信息
        List<TDepartmentInfo> tDepartmentInfos = tDepartmentInfoDao.queryAllObj();
        if (tDepartmentInfos == null || tDepartmentInfos.size() == 0) {
            log.info("业务区-公共服务-未查询到部门相关信息");
            return Result.build(BusineConmonCodeEnum.NOT_SELECT_INFO.getCode(), BusineConmonCodeEnum.NOT_SELECT_INFO.getMessage());
        }
        for (TDepartmentInfo tDepartmentInfo : tDepartmentInfos) {
            //设置部门信息
            OrgAuthDto orgAuthDto = new OrgAuthDto();
            orgAuthDto.setOrgCode(tDepartmentInfo.getDepId());
            orgAuthDto.setOrgName(tDepartmentInfo.getDepNm());
            List<AuthDto> auths = new ArrayList<>();
            String baseAuthList[] = this.getBaseAuthUrl().split(",");
            for (int i = 0; i < baseAuthList.length; i++) {
                AuthDto authDto1 = new AuthDto();
                authDto1.setAuthType("3");//权限点
                authDto1.setAuthCode("00" + i + 1 + "");
                authDto1.setAuthUrl(baseAuthList[i]);
                authDto1.setId(i + 1 + "");
                authDto1.setPid("0" + i + 1 + "");
                auths.add(authDto1);
            }

           /* //获取部门对应的菜单信息
            TDepOrgMenuInfo tDepOrgMenuInfo=new TDepOrgMenuInfo();
            tDepOrgMenuInfo.setId(tDepartmentInfo.getDepId());
            List<TDepOrgMenuInfo> tDepOrgMenuInfos=tDepOrgMenuInfoDao. queryAll( tDepOrgMenuInfo);
            if(tDepOrgMenuInfos!=null&&tDepOrgMenuInfos.size()>0){
                for(TDepOrgMenuInfo tDepOrgMenuInfo1:tDepOrgMenuInfos){
                    //设置菜单对应的权限信息
                    AuthDto authDto=new AuthDto();
                    TMenuInfo tMenuInfo=tMenuInfoDao. queryById(tDepOrgMenuInfo1.getMenuId());
                    if(tMenuInfo==null){
                        continue;
                    }
                    if(tMenuInfo.getMenuType().equals("0")){
                        //按钮菜单
                        authDto.setAuthType("2");
                    }else if (tMenuInfo.getMenuType().equals("1")){
                        //目录菜单
                        authDto.setAuthType("4");
                    }else{
                        //导航菜单
                        authDto.setAuthType("1");
                    }
                    authDto.setAuthCode(tMenuInfo.getMenuId());
                    authDto.setAuthUrl(tMenuInfo.getUrl());
                    authDto.setId(tDepOrgMenuInfo1.getMenuId());
                    authDto.setPid(tMenuInfo.getParentMenuId());
                    auths.add(authDto);
                    //获取菜单对应的权限点信息
                    TMenuAuthInfo tMenuAuthInfo=new TMenuAuthInfo();
                    tMenuAuthInfo.setMenuId(tDepOrgMenuInfo1.getMenuId());
                    List<TMenuAuthInfo> tMenuAuthInfos=tMenuAuthInfoDao. queryAll( tMenuAuthInfo);
                    if(tMenuAuthInfos!=null&&tMenuAuthInfos.size()>0){
                        //设置菜单对应的权限点信息
                        for(TMenuAuthInfo tMenuAuthInfo1:tMenuAuthInfos){
                            AuthDto authDto1=new AuthDto();
                            authDto1.setAuthType("3");//权限点
                            authDto1.setAuthCode(tMenuAuthInfo1.getAuthId()+"");
                            authDto1.setAuthUrl(tMenuAuthInfo1.getUrl());
                            authDto1.setId(tDepOrgMenuInfo1.getMenuId());
                            authDto1.setPid(tMenuInfo.getParentMenuId());
                            auths.add(authDto1);
                        }
                    }
                }
            }*/
            orgAuthDto.setAuthList(auths);
            orgAuthDtos.add(orgAuthDto);
        }
        //设置移动客户端的机构权限信息
        orgAuthDtos.add(getUserOrgAuthDto());
        return Result.ok(orgAuthDtos);
    }

    /**
     * 推送部门权限给安全中心
     *
     * @param depId
     * @return
     */
    @Override
    public Result pushDepAuth(String depId) {
        OrgAuthDto resultOrgAuthdtos = new OrgAuthDto();
        //获取所有部门信息
        List<TDepartmentInfo> tDepartmentInfos = tDepartmentInfoDao.queryById(depId);
        if (tDepartmentInfos == null || tDepartmentInfos.size() == 0) {
            log.info("业务区-公共服务-未查询到部门相关信息");
            return Result.build(BusineConmonCodeEnum.NOT_SELECT_INFO.getCode(), BusineConmonCodeEnum.NOT_SELECT_INFO.getMessage());
        }
        for (TDepartmentInfo tDepartmentInfo : tDepartmentInfos) {
            //设置部门信息
            OrgAuthDto orgAuthDto = new OrgAuthDto();
            orgAuthDto.setOrgCode(tDepartmentInfo.getDepId());
            orgAuthDto.setOrgName(tDepartmentInfo.getDepNm());
            List<AuthDto> auths = new ArrayList<>();
            String baseAuthList[] = this.getBaseAuthUrl().split(",");
            for (int i = 0; i < baseAuthList.length; i++) {
                AuthDto authDto1 = new AuthDto();
                authDto1.setAuthType("3");//权限点
                authDto1.setAuthCode("00" + i + 1 + "");
                authDto1.setAuthUrl(baseAuthList[i]);
                authDto1.setId(i + 1 + "");
                authDto1.setPid("0" + i + 1 + "");
                auths.add(authDto1);
            }

           /* List<AuthDto> auths=new ArrayList<>();
            //获取部门对应的菜单信息
            TDepOrgMenuInfo tDepOrgMenuInfo=new TDepOrgMenuInfo();
            tDepOrgMenuInfo.setId(tDepartmentInfo.getDepId());
            List<TDepOrgMenuInfo> tDepOrgMenuInfos=tDepOrgMenuInfoDao. queryAll( tDepOrgMenuInfo);
            if(tDepOrgMenuInfos!=null&&tDepOrgMenuInfos.size()>0){
                for(TDepOrgMenuInfo tDepOrgMenuInfo1:tDepOrgMenuInfos){
                    //设置菜单对应的权限信息
                    AuthDto authDto=new AuthDto();
                    TMenuInfo tMenuInfo=tMenuInfoDao. queryById(tDepOrgMenuInfo1.getMenuId());
                    if(tMenuInfo==null){
                        continue;
                    }
                    if(tMenuInfo.getMenuType().equals("0")){
                        //按钮菜单
                        authDto.setAuthType("2");
                    }else if (tMenuInfo.getMenuType().equals("1")){
                        //目录菜单
                        authDto.setAuthType("4");
                    }else{
                        //导航菜单
                        authDto.setAuthType("1");
                    }
                    authDto.setAuthCode(tMenuInfo.getMenuId());
                    authDto.setAuthUrl(tMenuInfo.getUrl());
                    authDto.setId(tDepOrgMenuInfo1.getMenuId());
                    authDto.setPid(tMenuInfo.getParentMenuId());
                    auths.add(authDto);
                    //获取菜单对应的权限点信息
                    TMenuAuthInfo tMenuAuthInfo=new TMenuAuthInfo();
                    tMenuAuthInfo.setMenuId(tDepOrgMenuInfo1.getMenuId());
                    List<TMenuAuthInfo> tMenuAuthInfos=tMenuAuthInfoDao. queryAll( tMenuAuthInfo);
                    if(tMenuAuthInfos!=null&&tMenuAuthInfos.size()>0){
                        //设置菜单对应的权限点信息
                        for(TMenuAuthInfo tMenuAuthInfo1:tMenuAuthInfos){
                            AuthDto authDto1=new AuthDto();
                            authDto1.setAuthType("3");//权限点
                            authDto1.setAuthCode(tMenuAuthInfo1.getAuthId()+"");
                            authDto1.setAuthUrl(tMenuAuthInfo1.getUrl());
                            authDto1.setId(tDepOrgMenuInfo1.getMenuId());
                            authDto1.setPid(tMenuInfo.getParentMenuId());
                            auths.add(authDto1);
                        }
                    }
                }
            }*/
            orgAuthDto.setAuthList(auths);
            resultOrgAuthdtos = orgAuthDto;
            break;
        }
        //请求安全中心
        log.info("业务公共服务-推送增量部门权限信息给安全中心，推送数据，{}", JSONObject.toJSONString(resultOrgAuthdtos));
        try {
            String resultStr = HttpRequest.httpPost(pushDepUrl, JSONObject.toJSONString(resultOrgAuthdtos));
            if (StringUtils.isBlank(resultStr)) {
                return Result.fail();
            }
            JSONObject jsonObject = JSONObject.parseObject(resultStr);
            if (jsonObject.getString("code").equals("200")) {
                return Result.ok();
            } else {
                return Result.build(Integer.parseInt(jsonObject.getString("code")), jsonObject.getString("msg"));
            }
        } catch (Exception e) {
            log.info("业务公共服务-推送增量部门权限信息给安全中心,请求异常,{}", e);
        }
        return Result.ok();
    }

    /**
     * 多渠道登录验证接口
     *
     * @param param：请求头包含渠道 请求体：userAccount
     *                      userPass
     *                      orgCode
     *                      phoneTel
     *                      smsCode
     *                      loginType (0-账号登录 1-验证码登录)
     *                      sfCode(第三方COde)
     * @return
     */
    @Override
    public Result checkUserLogin(String param) {
        log.info("业务区-公共服务-多渠道登录请求参数为，{}", param.toString());
        if (param == null) {
            log.info("业务区-公共服务-" + BusineConmonCodeEnum.PARAM_NULL.getMessage());
            return Result.build(BusineConmonCodeEnum.PARAM_NULL.getCode(), BusineConmonCodeEnum.PARAM_NULL.getMessage());
        }
        //解析JSON
        userJsonDto userJsonDto = JSON.parseObject(param, userJsonDto.class);
        String chanlNo = userJsonDto.getChanlNo();
        if (StringUtils.isBlank(chanlNo)) {
            log.info("业务区-公共服务-" + BusineConmonCodeEnum.PARAM_NULL.getMessage());
            return Result.build(BusineConmonCodeEnum.PARAM_NULL.getCode(), BusineConmonCodeEnum.PARAM_NULL.getMessage());
        }
        if (chanlNo.equals("01")) {
            //贵农购登录
            if (userJsonDto.getLoginType().equals("0")) {
                //账号-密码登录
                if (StringUtils.isBlank(userJsonDto.getUserAccount()) || StringUtils.isBlank(userJsonDto.getUsPaWD())) {
                    log.info("业务区-公共服务-" + BusineConmonCodeEnum.PARAM_NULL.getMessage());
                    return Result.build(BusineConmonCodeEnum.PARAM_NULL.getCode(), BusineConmonCodeEnum.PARAM_NULL.getMessage());
                }
                //校验手机号和密码是否正确
                TUserInfo tUserInfo = new TUserInfo();
                tUserInfo.setUserPhone(userJsonDto.getUserAccount());
                //渠道号
                tUserInfo.setChanlNo(chanlNo);
                List<TUserInfo> tUserInfos = tUserInfoDao.queryAll(tUserInfo);
                if (tUserInfos == null || tUserInfos.size() == 0) {
                    //用户登录失败
                    return Result.build(BusineConmonCodeEnum.ACCOUNT_NOT_EXIT.getCode(), BusineConmonCodeEnum.ACCOUNT_NOT_EXIT.getMessage());
                }
                try {
                    tUserInfo.setUserPasswd(Md5Util.getMd5(userJsonDto.getUsPaWD()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                List<TUserInfo> tUserInfos1 = tUserInfoDao.queryAll(tUserInfo);
                if (tUserInfos1 == null || tUserInfos1.size() == 0) {
                    //用户登录失败
                    return Result.build(BusineConmonCodeEnum.PASSWORD_NOT_ERROR.getCode(), BusineConmonCodeEnum.PASSWORD_NOT_ERROR.getMessage());
                }
                //登录成功
                //更新设备id
                tUserInfos.get(0).setClientId(userJsonDto.getClientId());
                tUserInfoDao.updateClientId(tUserInfos.get(0));
                //获取用户信息
                TUserInfoDto tUserInfoDto = new TUserInfoDto();
                tUserInfoDto.setMobile(tUserInfos.get(0).getUserPhone());
                tUserInfoDto.setUserAccount(tUserInfos.get(0).getUserId());
                tUserInfoDto.setUserId(tUserInfos.get(0).getUserId());
                tUserInfoDto.setUserName(tUserInfos.get(0).getUserNickNm());
                //获取用户的角色信息
                List<String> roles = new ArrayList<>();
                roles.add("000000");
                tUserInfoDto.setRoleList(roles);
                tUserInfoDto.setOrgCode("00000000");
                boolean flag=this.checkCustomOrAcount(tUserInfoDto.getUserId(),tUserInfoDto.getMobile(),chanlNo,tUserInfoDto.getUserId());
                if(!flag){
                    return Result.build(BusineConmonCodeEnum.CHECK_CUSTOM_ACOUNT_EXC.getCode(), BusineConmonCodeEnum.CHECK_CUSTOM_ACOUNT_EXC.getMessage());
                }
                return Result.ok(tUserInfoDto);
            } else if (userJsonDto.getLoginType().equals("1")) {
                //账号-短信验证码登录
                if (StringUtils.isBlank(userJsonDto.getPhoneTel()) || StringUtils.isBlank(userJsonDto.getSmsCode())) {
                    log.info("业务区-公共服务-" + BusineConmonCodeEnum.PARAM_NULL.getMessage());
                    return Result.build(BusineConmonCodeEnum.PARAM_NULL.getCode(), BusineConmonCodeEnum.PARAM_NULL.getMessage());
                }
                //校验手机号是否正确
                TUserInfo tUserInfo = new TUserInfo();
                tUserInfo.setUserPhone(userJsonDto.getPhoneTel());
                //渠道号
                tUserInfo.setChanlNo(chanlNo);
                List<TUserInfo> tUserInfos = tUserInfoDao.queryAll(tUserInfo);
                if (tUserInfos == null || tUserInfos.size() == 0) {
                    //当前手机号未注册
                    return Result.build(BusineConmonCodeEnum.PHONETEL_NOT_REGIST.getCode(), BusineConmonCodeEnum.PHONETEL_NOT_REGIST.getMessage());
                }
                //验证验证码是否正确
                String smsCode = redisUtil.get(userJsonDto.getPhoneTel());
//                String smsCode="666666";
                if (Integer.parseInt(userJsonDto.getSmsCode()) != Integer.parseInt(smsCode)) {
                    return Result.build(BusineConmonCodeEnum.SMSCODE_CHECK_ERROR.getCode(), BusineConmonCodeEnum.SMSCODE_CHECK_ERROR.getMessage());
                }
                //登录成功
                //更新设备id
                tUserInfos.get(0).setClientId(userJsonDto.getClientId());
                tUserInfoDao.updateClientId(tUserInfos.get(0));
                //获取用户信息
                TUserInfoDto tUserInfoDto = new TUserInfoDto();
                tUserInfoDto.setMobile(tUserInfos.get(0).getUserPhone());
                tUserInfoDto.setUserAccount(tUserInfos.get(0).getUserId());
                tUserInfoDto.setUserId(tUserInfos.get(0).getUserId());
                tUserInfoDto.setUserName(tUserInfos.get(0).getUserNickNm());
                //获取用户的角色信息
                List<String> roles = new ArrayList<>();
                roles.add("000000");
                tUserInfoDto.setRoleList(roles);
                //获取用户的机构信息
                tUserInfoDto.setOrgCode("00000000");
                boolean flag=this.checkCustomOrAcount(tUserInfoDto.getUserId(),tUserInfoDto.getMobile(),chanlNo,tUserInfoDto.getUserId());
                if(!flag){
                    return Result.build(BusineConmonCodeEnum.CHECK_CUSTOM_ACOUNT_EXC.getCode(), BusineConmonCodeEnum.CHECK_CUSTOM_ACOUNT_EXC.getMessage());
                }
                return Result.ok(tUserInfoDto);
            }
        } else if (chanlNo.equals("04") || chanlNo.equals("05")) {
            //农批系统和智慧贵农登录
            if (StringUtils.isBlank(userJsonDto.getUserAccount()) || StringUtils.isBlank(userJsonDto.getUsPaWD())) {
                log.info("业务区-公共服务-" + BusineConmonCodeEnum.PARAM_NULL.getMessage());
                return Result.build(BusineConmonCodeEnum.PARAM_NULL.getCode(), BusineConmonCodeEnum.PARAM_NULL.getMessage());
            }
            //校验账号是否存在
            TEmployeeInfo tEmployeeInfo = tEmployeeInfoDao.queryById(userJsonDto.getUserAccount());
            if (tEmployeeInfo == null) {
                return Result.build(BusineConmonCodeEnum.ACCOUNT_NOT_EXIT.getCode(), BusineConmonCodeEnum.ACCOUNT_NOT_EXIT.getMessage());
            }
            //验证密码是否正确
            TEmployeeInfo tEmployeeInfo1 = new TEmployeeInfo();
            tEmployeeInfo1.setEmpId(userJsonDto.getUserAccount());
            try {
                tEmployeeInfo1.setPasswd(Md5Util.getMd5(userJsonDto.getUsPaWD()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<TEmployeeInfo> tEmployeeInfos = tEmployeeInfoDao.queryAll(tEmployeeInfo1);
            if (tEmployeeInfos == null || tEmployeeInfos.size() == 0) {
                return Result.build(BusineConmonCodeEnum.PASSWORD_NOT_ERROR.getCode(), BusineConmonCodeEnum.PASSWORD_NOT_ERROR.getMessage());
            }
            if (tEmployeeInfos.get(0).getEmpSt().equals("1")) {
                return Result.build(BusineConmonCodeEnum.ACCOUNT_LOCK.getCode(), BusineConmonCodeEnum.ACCOUNT_LOCK.getMessage());
            }
            if (tEmployeeInfos.get(0).getEmpSt().equals("2")) {
                return Result.build(BusineConmonCodeEnum.ACCOUNT_ZHUXIAO.getCode(), BusineConmonCodeEnum.ACCOUNT_ZHUXIAO.getMessage());
            }
            //获取用户信息
            TUserInfoDto tUserInfoDto = new TUserInfoDto();
            tUserInfoDto.setMobile(tEmployeeInfo.getTelephone());
            tUserInfoDto.setUserAccount(tEmployeeInfo.getEmpId());
            tUserInfoDto.setUserId(tEmployeeInfo.getEmpId());
            tUserInfoDto.setUserName(tEmployeeInfo.getEmpNm());
            //获取用户的角色信息
            TEmployeeRoleInfo tEmployeeRoleInfo = new TEmployeeRoleInfo();
            tEmployeeRoleInfo.setEmpId(tEmployeeInfo.getEmpId());
            List<TEmployeeRoleInfo> tEmployeeRoleInfos = tEmployeeRoleInfoDao.queryAll(tEmployeeRoleInfo);
            List<String> roles = new ArrayList<>();
            if (tEmployeeRoleInfos != null) {
                for (TEmployeeRoleInfo tEmployeeRoleInfo1 : tEmployeeRoleInfos) {
                    roles.add(tEmployeeRoleInfo1.getRoleId());
                }
            }
            tUserInfoDto.setRoleList(roles);
            //获取用户的部门信息
            tUserInfoDto.setOrgCode(tEmployeeInfo.getSubDep());
            log.info("业务区-公共服务-农批平台登录成功.....");
            return Result.ok(tUserInfoDto);
        } else if (chanlNo.equals("02")) {
            //微信小程序登录
            if (StringUtils.isBlank(userJsonDto.getWxOpenId())) {
                log.info("业务区-公共服务-" + BusineConmonCodeEnum.PARAM_NULL.getMessage());
                return Result.build(BusineConmonCodeEnum.PARAM_NULL.getCode(), BusineConmonCodeEnum.PARAM_NULL.getMessage());
            }
            //验证该微信号注册信息
            TUserInfo tUserInfo = tUserInfoDao.queryBywxId(userJsonDto.getWxOpenId());
            if (tUserInfo == null) {
                //登录失败
                return Result.build(BusineConmonCodeEnum.GET_WEIXIN_LOGIN_FAIL.getCode(), BusineConmonCodeEnum.GET_WEIXIN_LOGIN_FAIL.getMessage());
            }
            //登录成功
            //更新设备id
            tUserInfo.setClientId(userJsonDto.getClientId());
            tUserInfoDao.updateClientId(tUserInfo);
            //获取用户信息
            TUserInfoDto tUserInfoDto = new TUserInfoDto();
            tUserInfoDto.setMobile(tUserInfo.getUserPhone());
            tUserInfoDto.setUserAccount(tUserInfo.getUserId());
            tUserInfoDto.setUserId(tUserInfo.getUserId());
            tUserInfoDto.setUserName(tUserInfo.getUserNickNm());
            //获取用户的角色信息
            List<String> roles = new ArrayList<>();
            roles.add("000000");
            tUserInfoDto.setRoleList(roles);
            //获取用户的机构信息
            tUserInfoDto.setOrgCode("00000000");
            boolean flag=this.checkCustomOrAcount(tUserInfoDto.getUserId(),tUserInfoDto.getMobile(),chanlNo,tUserInfoDto.getUserId());
            if(!flag){
                return Result.build(BusineConmonCodeEnum.CHECK_CUSTOM_ACOUNT_EXC.getCode(), BusineConmonCodeEnum.CHECK_CUSTOM_ACOUNT_EXC.getMessage());
            }
            return Result.ok(tUserInfoDto);
        } else if (chanlNo.equals("03")) {
            //支付宝小程序登录
            if (StringUtils.isBlank(userJsonDto.getZfbOpenId())) {
                log.info("业务区-公共服务-" + BusineConmonCodeEnum.PARAM_NULL.getMessage());
                return Result.build(BusineConmonCodeEnum.PARAM_NULL.getCode(), BusineConmonCodeEnum.PARAM_NULL.getMessage());
            }
            //验证该支付宝注册信息
            TUserInfo tUserInfo = tUserInfoDao.queryByZfbId(userJsonDto.getZfbOpenId());
            if (tUserInfo == null) {
                //登录失败
                return Result.build(BusineConmonCodeEnum.GET_ZHIFUBAO_LOGIN_FAIL.getCode(), BusineConmonCodeEnum.GET_ZHIFUBAO_LOGIN_FAIL.getMessage());
            }
            //登录成功
            tUserInfo.setClientId(userJsonDto.getClientId());
            tUserInfoDao.updateClientId(tUserInfo);
            //获取用户信息
            TUserInfoDto tUserInfoDto = new TUserInfoDto();
            tUserInfoDto.setMobile(tUserInfo.getUserPhone());
            tUserInfoDto.setUserAccount(tUserInfo.getUserId());
            tUserInfoDto.setUserId(tUserInfo.getUserId());
            tUserInfoDto.setUserName(tUserInfo.getUserNickNm());
            //获取用户的角色信息
            List<String> roles = new ArrayList<>();
            roles.add("000000");
            tUserInfoDto.setRoleList(roles);
            //获取用户的机构信息
            tUserInfoDto.setOrgCode("00000000");
            boolean flag=this.checkCustomOrAcount(tUserInfoDto.getUserId(),tUserInfoDto.getMobile(),chanlNo,tUserInfoDto.getUserId());
            if(!flag){
                return Result.build(BusineConmonCodeEnum.CHECK_CUSTOM_ACOUNT_EXC.getCode(), BusineConmonCodeEnum.CHECK_CUSTOM_ACOUNT_EXC.getMessage());
            }
            return Result.ok(tUserInfoDto);
        }
        return Result.fail();
    }

    /**
     * 微信检测是否注册
     *
     * @param request
     * @return
     */
    @Override
    public Result wxCheck(Request<WxCheck> request) {
        if (request == null) {
            log.info("业务区-公共服务-" + BusineConmonCodeEnum.PARAM_NULL.getMessage());
            return Result.build(BusineConmonCodeEnum.PARAM_NULL.getCode(), BusineConmonCodeEnum.PARAM_NULL.getMessage());
        }
        TUserInfo tUserInfo = null;
        if (request.getHead().getChanlNo().equals("02")) {
            //调用微信获取openid
            String param = "code=" + request.getBody().getCode() + "&chanlNo=" + request.getHead().getChanlNo();
            String result = null;
            try {
                result = HttpRequest.httpPost(loginAuthUrl, param);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("开放区-公共服务-调用微信授权交易，获得的返回报文，{}", result);
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (!jsonObject.getString("code").equals("200")) {
                log.info("公共区-公共服务-", jsonObject.getString("message"));
                return Result.build(Integer.parseInt(jsonObject.getString("code")), jsonObject.getString("message"));
            }
            String data = jsonObject.getString("data");
            WeiXinDto weiXinDto = JSON.parseObject(data, WeiXinDto.class);
            //验证该微信号是否已注册平台、
            //openId= SmsUtil.createSmsCode();
            tUserInfo = tUserInfoDao.queryBywxId(weiXinDto.getOpenid());
            //调用微信获取openid 结束
            if (tUserInfo == null) {
                //该微信号或支付宝账号未注册
                /**
                 * 若未注册、则返回未注册信息，前端进行调用微信授权登录
                 * 若授权成功，则跳转注册页面，进行引导注册
                 */
                //session_key 存入redis 注册时使用
                redisUtil.setEx(weiXinDto.getOpenid(), weiXinDto.getSession_Key(), 60 * 30, TimeUnit.SECONDS);
                WeiXinResultDto weiXinResultDto = new WeiXinResultDto();
                TUserInfo tUserInfo1 = new TUserInfo();
                tUserInfo1.setWxpayId(weiXinDto.getOpenid());
                weiXinResultDto.setUserInfo(tUserInfo1);
                weiXinResultDto.setReg("0");
                return Result.ok(weiXinResultDto);
            }
        } else if (request.getHead().getChanlNo().equals("03")) {
            String openId = "";
            String param = "code=" + request.getBody().getCode() + "&chanlNo=" + request.getHead().getChanlNo();
            String result = RequestUtil.sendPost(loginAuthUrl, param);
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (!jsonObject.getString("code").equals("200")) {
                log.info("公共区-公共服务-", jsonObject.getString("message"));
                return Result.build(Integer.parseInt(jsonObject.getString("code")), jsonObject.getString("message"));
            }
            String data = jsonObject.getString("data");
            ZfbDto zfbDto = JSONObject.parseObject(data, ZfbDto.class);
            //验证该微信号是否已注册平台、
            openId = zfbDto.getUserId();
            tUserInfo = tUserInfoDao.queryByZfbId(openId);
            if (tUserInfo == null) {
                //该支付宝账号未注册
                /**
                 * 若未注册、则返回未注册信息，前端进行调用微信授权登录
                 * 若授权成功，则跳转注册页面，进行引导注册
                 */
                WeiXinResultDto weiXinResultDto = new WeiXinResultDto();
                TUserInfo tUserInfo1 = new TUserInfo();
                tUserInfo1.setAlipayId(openId);
                weiXinResultDto.setUserInfo(tUserInfo1);
                weiXinResultDto.setReg("0");
                return Result.ok(weiXinResultDto);
            }
        }
        //该微信号或支付宝账号已注册
        /**
         * 若已注册，则返回用户信息，前端使用用户信息进行请求安全中心进行登录获取正式token
         * 若拿到正式token ,则进行跳转到指定页
         */
        WeiXinResultDto weiXinResultDto = new WeiXinResultDto();
        weiXinResultDto.setReg("1");
        weiXinResultDto.setUserInfo(tUserInfo);
        return Result.ok(weiXinResultDto);
    }

    /**
     * 获取客户端的用户信息
     *
     * @param userId
     * @param chanlNo
     * @return
     */
    @Override
    public Result getUserInfo(String userId, String chanlNo) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(chanlNo)) {
            log.info("业务区-公共服务-" + BusineConmonCodeEnum.PARAM_NULL.getMessage());
            return Result.build(BusineConmonCodeEnum.PARAM_NULL.getCode(), BusineConmonCodeEnum.PARAM_NULL.getMessage());
        }
        if (chanlNo.equals("01") || chanlNo.equals("02") || chanlNo.equals("03")) {
            //获取移动客户端用户信息
            return Result.ok(tUserInfoDao.queryById(userId));
        } else if (chanlNo.equals("04") || chanlNo.equals("05")) {
            //获取员工的信息
            Map map = new HashMap<>();
            map.put("userId", userId);
            List<TEmployeeInfoDto> tEmployeeInfoDtos = itEmployeeInfoDao.queryByUserId(map);
            if (tEmployeeInfoDtos != null && tEmployeeInfoDtos.size() > 0) {
                //获取管理客户端用户的角色信息
                Map roleMap = new HashMap();
                roleMap.put("empId", userId);
                List<TRoleInfoDto> tRoleInfoDtos = itEmployeeRoleInfoDao.queryAll(roleMap);
                tEmployeeInfoDtos.get(0).setRoles(tRoleInfoDtos);
                return Result.ok(tEmployeeInfoDtos.get(0));
            }
        }
        return Result.fail();
    }

    /**
     * 获取客户端登录的用户权限菜单
     *
     * @param userId
     * @param chanlNo
     * @return
     */
    @Override
    public Result getUserAuthMenuInfo(String userId, String chanlNo) {
        log.info("业务区-公共服务-获取菜单请求参数，{}", "chanlNo=" + chanlNo, "userId=" + userId);
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(chanlNo)) {
            log.info("业务区-公共服务-" + BusineConmonCodeEnum.PARAM_NULL.getMessage());
            return Result.build(BusineConmonCodeEnum.PARAM_NULL.getCode(), BusineConmonCodeEnum.PARAM_NULL.getMessage());
        }
        MenuTreeDto menuTreeDto = new MenuTreeDto();
        if (chanlNo.equals("04") || chanlNo.equals("05")) {
            //获取管理客户端用户的角色权限
            TEmployeeRoleInfo tEmployeeRoleInfo = new TEmployeeRoleInfo();
            if (chanlNo.equals("04")) {
                tEmployeeRoleInfo.setChanlNo("0");
            } else if (chanlNo.equals("05")) {
                tEmployeeRoleInfo.setChanlNo("1");
            }
            tEmployeeRoleInfo.setEmpId(userId);
            log.info("业务区-公共服务-请求获取当前员工的角色权限信息-请求参数，{}", JSONObject.toJSONString(tEmployeeRoleInfo));
            List<TRoleInfoDto> tEmployeeRoleInfos = tEmployeeRoleInfoDao.queryAllByChanlNo(tEmployeeRoleInfo);
            log.info("业务区-公共服务-查询获取当前员工的角色权限信息，{}", JSONObject.toJSONString(tEmployeeRoleInfos));
            if (tEmployeeRoleInfos == null || tEmployeeRoleInfos.size() == 0) {
                log.info("业务区-公共服务-" + BusineConmonCodeEnum.NOT_AUTH.getMessage());
                return Result.build(BusineConmonCodeEnum.NOT_AUTH.getCode(), BusineConmonCodeEnum.NOT_AUTH.getMessage());
            }
            //获取角色对应的权限非按钮菜单 ,获取默认角色菜单
            menuTreeDto.setRole(tRoleInfoDao.queryById(tEmployeeRoleInfos.get(0).getRoleId()));
            Map map = new HashMap();
            map.put("roleId", tEmployeeRoleInfos.get(0).getRoleId());
            if (chanlNo.equals("04")) {
                map.put("chanlNoType", "0");
            } else if (chanlNo.equals("05")) {
                map.put("chanlNoType", "1");
            }
            List<TMenuInfo> tRoleMenuInfos = itRoleMenuInfoDao.queryAll(map);
            //获取用户对应的部门权限信息
            TEmployeeInfo tEmployeeInfo = tEmployeeInfoDao.queryById(userId);
            TDepOrgMenuInfo tDepOrgMenuInfo = new TDepOrgMenuInfo();
            tDepOrgMenuInfo.setId(tEmployeeInfo.getSubDep());
            List<TDepOrgMenuInfo> tDepOrgMenuInfos = tDepOrgMenuInfoDao.queryAll(tDepOrgMenuInfo);
            //处理角色权限菜单和部门权限菜单，取两方交集部分
            List<TMenuInfo> tRoleMenus = new ArrayList<>();
            if (tDepOrgMenuInfos != null && tDepOrgMenuInfos.size() > 0) {
                for (TMenuInfo tRoleMenuInfo1 : tRoleMenuInfos) {
                    for (TDepOrgMenuInfo tDepOrgMenuInfo1 : tDepOrgMenuInfos) {
                        if (tRoleMenuInfo1.getMenuId().equals(tDepOrgMenuInfo1.getMenuId())) {
                            tRoleMenus.add(tRoleMenuInfo1);
                            break;
                        }
                    }
                }
            }
            for (TMenuInfo tMenuInfo : tRoleMenus) {
                //查找第一级菜单
                if (tMenuInfo.getParentMenuId().equals("0000")) {
                    for (TMenuInfo tMenuInfo1 : tRoleMenus) {
                        if (tMenuInfo1.getParentMenuId().equals(tMenuInfo.getMenuId())) {
                            for (TMenuInfo tMenuInfo2 : tRoleMenus) {
                                if (tMenuInfo2.getParentMenuId().equals(tMenuInfo1.getMenuId())) {
                                    if (StringUtils.isBlank(tMenuInfo.getMenuUrl())) {
                                        tMenuInfo.setMenuUrl(tMenuInfo2.getMenuUrl());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            List<MenuTree> treeList = tRoleMenus.stream()
                    .filter(menu -> Integer.parseInt(menu.getMenuId()) != Integer.parseInt(menu.getParentMenuId()))
                    .map(menu -> {
                        MenuTree node = new MenuTree();
                        node.setIcon(menu.getIconId());
                        node.setMenuType(menu.getMenuType());
                        node.setPath(menu.getMenuUrl());
                        node.setRemark(menu.getRemark());
                        node.setUrl(menu.getUrl());
                        node.setId(menu.getMenuId());
                        node.setTitle(menu.getMenuNm());
                        node.setParentId(menu.getParentMenuId());
                        return node;
                    }).collect(Collectors.toList());
            menuTreeDto.setMenuTrees(TreeUtil.bulid(treeList, "0000"));
            return Result.ok(menuTreeDto);
        }
        return Result.ok();
    }

    /**
     * 获取客户端用户的某个角色权限信息
     *
     * @param userId
     * @param chanlNo
     * @param roleId
     * @return
     */
    @Override
    public Result getUserAuthMenuInfoByRoleId(String userId, String chanlNo, String roleId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(chanlNo) || StringUtils.isBlank(roleId)) {
            log.info("业务区-公共服务-" + BusineConmonCodeEnum.PARAM_NULL.getMessage());
            return Result.build(BusineConmonCodeEnum.PARAM_NULL.getCode(), BusineConmonCodeEnum.PARAM_NULL.getMessage());
        }
        MenuTreeDto menuTreeDto = new MenuTreeDto();
        if (chanlNo.equals("04") || chanlNo.equals("05")) {
            //获取管理客户端用户的角色权限
            menuTreeDto.setRole(tRoleInfoDao.queryById(roleId));
            //获取角色对应的权限非按钮菜单
            Map map = new HashMap();
            map.put("roleId", roleId);
            if (chanlNo.equals("04")) {
                map.put("chanlNoType", "0");
            } else if (chanlNo.equals("05")) {
                map.put("chanlNoType", "1");
            }
            List<TMenuInfo> tRoleMenuInfos = itRoleMenuInfoDao.queryAll(map);
            //获取用户对应的部门权限信息
            TEmployeeInfo tEmployeeInfo = tEmployeeInfoDao.queryById(userId);
            TDepOrgMenuInfo tDepOrgMenuInfo = new TDepOrgMenuInfo();
            tDepOrgMenuInfo.setId(tEmployeeInfo.getSubDep());
            List<TDepOrgMenuInfo> tDepOrgMenuInfos = tDepOrgMenuInfoDao.queryAll(tDepOrgMenuInfo);
            //处理角色权限菜单和部门权限菜单，取两方交集部分
            List<TMenuInfo> tRoleMenus = new ArrayList<>();
            if (tDepOrgMenuInfos != null && tDepOrgMenuInfos.size() > 0) {
                for (TMenuInfo tRoleMenuInfo1 : tRoleMenuInfos) {
                    for (TDepOrgMenuInfo tDepOrgMenuInfo1 : tDepOrgMenuInfos) {
                        if (tRoleMenuInfo1.getMenuId().equals(tDepOrgMenuInfo1.getMenuId())) {
                            tRoleMenus.add(tRoleMenuInfo1);
                            break;
                        }
                    }
                }
            }
            for (TMenuInfo tMenuInfo : tRoleMenus) {
                //查找第一级菜单
                if (tMenuInfo.getParentMenuId().equals("0000")) {
                    for (TMenuInfo tMenuInfo1 : tRoleMenus) {
                        if (tMenuInfo1.getParentMenuId().equals(tMenuInfo.getMenuId())) {
                            for (TMenuInfo tMenuInfo2 : tRoleMenus) {
                                if (tMenuInfo2.getParentMenuId().equals(tMenuInfo1.getMenuId())) {
                                    if (StringUtils.isBlank(tMenuInfo.getMenuUrl())) {
                                        tMenuInfo.setMenuUrl(tMenuInfo2.getMenuUrl());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            List<MenuTree> treeList = tRoleMenus.stream()
                    .filter(menu -> menu.getMenuId() != menu.getParentMenuId())
                    .map(menu -> {
                        MenuTree node = new MenuTree();
                        node.setIcon(menu.getIconId());
                        node.setMenuType(menu.getMenuType());
                        node.setPath(menu.getMenuUrl());
                        node.setRemark(menu.getRemark());
                        node.setUrl(menu.getUrl());
                        node.setId(menu.getMenuId());
                        node.setTitle(menu.getMenuNm());
                        node.setParentId(menu.getParentMenuId());
                        return node;
                    }).collect(Collectors.toList());
            menuTreeDto.setMenuTrees(TreeUtil.bulid(treeList, "0000"));
            return Result.ok(menuTreeDto);
        }
        return Result.ok();
    }

    /**
     * 获取客户端登录的用户的权限菜单下的按钮菜单权限信息
     *
     * @param userId
     * @param menuId
     * @return
     */
    @Override
    public Result getUserAuthButtonInfo(String userId, String chanlNo, String menuId, String roleId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(menuId) || StringUtils.isBlank(chanlNo) || StringUtils.isBlank(roleId)) {
            log.info("业务区-公共服务-" + BusineConmonCodeEnum.PARAM_NULL.getMessage());
            return Result.build(BusineConmonCodeEnum.PARAM_NULL.getCode(), BusineConmonCodeEnum.PARAM_NULL.getMessage());
        }
        List<String> resultList = new ArrayList<>();
        if (chanlNo.equals("04") || chanlNo.equals("05")) {
            //查询菜单下的按钮菜单
            TMenuInfo tMenuInfo = new TMenuInfo();
            tMenuInfo.setParentMenuId(menuId);
            tMenuInfo.setMenuType("0");
            List<TMenuInfo> tMenuInfos = tMenuInfoDao.queryAll(tMenuInfo);
            if (tMenuInfos != null && tMenuInfos.size() > 0) {
                //查询用户角色对应的菜单
                TRoleMenuInfo tRoleMenuInfo = new TRoleMenuInfo();
                tRoleMenuInfo.setRoleId(roleId);
                List<TRoleMenuInfo> tRoleMenuInfos = tRoleMenuInfoDao.queryAll(tRoleMenuInfo);
                //获取当前用户对应的部门权限
                TEmployeeInfo tEmployeeInfo = tEmployeeInfoDao.queryById(userId);
                TDepOrgMenuInfo tDepOrgMenuInfo = new TDepOrgMenuInfo();
                tDepOrgMenuInfo.setId(tEmployeeInfo.getSubDep());
                List<TDepOrgMenuInfo> tDepOrgMenuInfos = tDepOrgMenuInfoDao.queryAll(tDepOrgMenuInfo);
                List<TMenuInfo> tMenus = new ArrayList<>();
                for (TMenuInfo tMenuInfo1 : tMenuInfos) {
                    for (TRoleMenuInfo tRoleMenuInfo1 : tRoleMenuInfos) {
                        //取角色权限交集
                        if (tMenuInfo1.getMenuId().equals(tRoleMenuInfo1.getMenuId())) {
                            for (TDepOrgMenuInfo tDepOrgMenuInfo1 : tDepOrgMenuInfos) {
                                //取部门权限交集
                                if (tMenuInfo1.getMenuId().equals(tDepOrgMenuInfo1.getMenuId())) {
                                    //tMenus.add(tMenuInfo1);
                                    resultList.add(tMenuInfo1.getMenuId());
                                    break;
                                }
                            }

                        }
                    }
                }
                return Result.ok(resultList);
            }else{
                return Result.ok(resultList);
            }
        }
        return null;
    }

    /**
     * 获取移动客户端用户的角色权限信息(手动初始化)
     *
     * @return
     */
    public RoleAuthDto getUserRoleAuthDto() {
        RoleAuthDto roleAuthDto = new RoleAuthDto();
        roleAuthDto.setRoleName("移动客户端用户");
        roleAuthDto.setRoleCode("888888");
        List<AuthDto> authDtos = new ArrayList<>();
        String baseAuthList[] = this.getBaseAuthUrl().split(",");
        for (int i = 0; i < baseAuthList.length; i++) {
            AuthDto authDto1 = new AuthDto();
            authDto1.setAuthType("3");//权限点
            authDto1.setAuthCode("00" + i + 1 + "");
            authDto1.setAuthUrl(baseAuthList[i]);
            authDto1.setId(i + 1 + "");
            authDto1.setPid("0" + i + 1 + "");
            authDtos.add(authDto1);
        }
        roleAuthDto.setAuthList(authDtos);
        return roleAuthDto;
    }

    public OrgAuthDto getUserOrgAuthDto() {
        OrgAuthDto orgAuthDto = new OrgAuthDto();
        orgAuthDto.setOrgName("贵阳农产品物流发展有限公司");
        orgAuthDto.setOrgCode("52012301");
        List<AuthDto> authDtos = new ArrayList<>();
        String baseAuthList[] = this.getBaseAuthUrl().split(",");
        for (int i = 0; i < baseAuthList.length; i++) {
            AuthDto authDto1 = new AuthDto();
            authDto1.setAuthType("3");//权限点
            authDto1.setAuthCode("00" + i + 1 + "");
            authDto1.setAuthUrl(baseAuthList[i]);
            authDto1.setId(i + 1 + "");
            authDto1.setPid("0" + i + 1 + "");
            authDtos.add(authDto1);
        }
        orgAuthDto.setAuthList(authDtos);
        return orgAuthDto;
    }

    public static String getBaseAuthUrl() {
        String baseAuthUrl = "";
        for (int i = 0; i < 700; i++) {
            if (i == 0) {
                baseAuthUrl = "/securityCenter/DISP2021" + String.format("%04d", i + 1);
            } else {
                baseAuthUrl = baseAuthUrl + "," + "/securityCenter/DISP2021" + String.format("%04d", i + 1);
            }
        }
        return baseAuthUrl;
    }

    public static void main(String[] args) {
        System.out.println(AuthServiceImpl.getBaseAuthUrl());
    }

    /*private String baseAuthUrl="/securityCenter/DISP20210001" +
            ",/securityCenter/DISP20210002" +
            ",/securityCenter/DISP20210003" +
            ",/securityCenter/DISP20210004" +
            ",/securityCenter/DISP20210005" +
            ",/securityCenter/DISP20210006" +
            ",/securityCenter/DISP20210007" +
            ",/securityCenter/DISP20210008" +
            ",/securityCenter/DISP20210009" +
            ",/securityCenter/DISP20210010" +
            ",/securityCenter/DISP20210011" +
            ",/securityCenter/DISP20210012" +
            ",/securityCenter/DISP20210013" +
            ",/securityCenter/DISP20210014" +
            ",/securityCenter/DISP20210015" +
            ",/securityCenter/DISP20210016" +
            ",/securityCenter/DISP20210017" +
            ",/securityCenter/DISP20210018" +
            ",/securityCenter/DISP20210019" +
            ",/securityCenter/DISP20210020" +
            ",/securityCenter/DISP20210021" +
            ",/securityCenter/DISP20210022" +
            ",/securityCenter/DISP20210023" +
            ",/securityCenter/DISP20210024" +
            ",/securityCenter/DISP20210025" +
            ",/securityCenter/DISP20210026" +
            ",/securityCenter/DISP20210027" +
            ",/securityCenter/DISP20210028" +
            ",/securityCenter/DISP20210029" +
            ",/securityCenter/DISP20210030" +
            ",/securityCenter/DISP20210031" +
            ",/securityCenter/DISP20210032" +
            ",/securityCenter/DISP20210033" +
            ",/securityCenter/DISP20210034" +
            ",/securityCenter/DISP20210035" +
            ",/securityCenter/DISP20210036" +
            ",/securityCenter/DISP20210037" +
            ",/securityCenter/DISP20210038" +
            ",/securityCenter/DISP20210039" +
            ",/securityCenter/DISP20210040" +
            ",/securityCenter/DISP20210041" +
            ",/securityCenter/DISP20210042" +
            ",/securityCenter/DISP20210043" +
            ",/securityCenter/DISP20210044" +
            ",/securityCenter/DISP20210045" +
            ",/securityCenter/DISP20210046" +
            ",/securityCenter/DISP20210047" +
            ",/securityCenter/DISP20210048" +
            ",/securityCenter/DISP20210049" +
            ",/securityCenter/DISP20210050" +
            ",/securityCenter/DISP20210051" +
            ",/securityCenter/DISP20210052" +
            ",/securityCenter/DISP20210053" +
            ",/securityCenter/DISP20210054" +
            ",/securityCenter/DISP20210055" +
            ",/securityCenter/DISP20210056" +
            ",/securityCenter/DISP20210057" +
            ",/securityCenter/DISP20210058" +
            ",/securityCenter/DISP20210059" +
            ",/securityCenter/DISP20210060" +
            ",/securityCenter/DISP2021006" +
            ",/securityCenter/DISP20210062" +
            ",/securityCenter/DISP20210063" +
            ",/securityCenter/DISP20210064" +
            ",/securityCenter/DISP20210065" +
            ",/securityCenter/DISP20210066" +
            ",/securityCenter/DISP20210067" +
            ",/securityCenter/DISP20210068" +
            ",/securityCenter/DISP20210069" +
            ",/securityCenter/DISP20210070" +
            ",/securityCenter/DISP20210071" +
            ",/securityCenter/DISP20210072" +
            ",/securityCenter/DISP20210073" +
            ",/securityCenter/DISP20210074" +
            ",/securityCenter/DISP20210075" +
            ",/securityCenter/DISP20210076" +
            ",/securityCenter/DISP20210077" +
            ",/securityCenter/DISP20210078" +
            ",/securityCenter/DISP20210079" +
            ",/securityCenter/DISP20210080" +
            ",/securityCenter/DISP20210081" +
            ",/securityCenter/DISP20210082" +
            ",/securityCenter/DISP20210083" +
            ",/securityCenter/DISP20210084" +
            ",/securityCenter/DISP20210085" +
            ",/securityCenter/DISP20210086" +
            ",/securityCenter/DISP20210087" +
            ",/securityCenter/DISP20210088" +
            ",/securityCenter/DISP20210089" +
            ",/securityCenter/DISP20210090" +
            ",/securityCenter/DISP20210091" +
            ",/securityCenter/DISP20210092" +
            ",/securityCenter/DISP20210093" +
            ",/securityCenter/DISP20210094" +
            ",/securityCenter/DISP20210095" +
            ",/securityCenter/DISP20210096" +
            ",/securityCenter/DISP20210097" +
            ",/securityCenter/DISP20210098" +
            ",/securityCenter/DISP20210099" +
            ",/securityCenter/DISP20210100" +
            ",/securityCenter/DISP20210101" +
            ",/securityCenter/DISP20210102" +
            ",/securityCenter/DISP20210103" +
            ",/securityCenter/DISP20210104" +
            ",/securityCenter/DISP20210105" +
            ",/securityCenter/DISP20210106" +
            ",/securityCenter/DISP20210107" +
            ",/securityCenter/DISP20210108" +
            ",/securityCenter/DISP20210109" +
            ",/securityCenter/DISP20210110" +
            ",/securityCenter/DISP20210111" +
            ",/securityCenter/DISP20210112" +
            ",/securityCenter/DISP20210113" +
            ",/securityCenter/DISP20210114" +
            ",/securityCenter/DISP20210115" +
            ",/securityCenter/DISP20210116" +
            ",/securityCenter/DISP20210117" +
            ",/securityCenter/DISP20210118" +
            ",/securityCenter/DISP20210119" +
            ",/securityCenter/DISP20210120" +
            ",/securityCenter/DISP20210121" +
            ",/securityCenter/DISP20210122" +
            ",/securityCenter/DISP20210123" +
            ",/securityCenter/DISP20210124" +
            ",/securityCenter/DISP2021012" +
            ",/securityCenter/DISP20210126" +
            ",/securityCenter/DISP20210127" +
            ",/securityCenter/DISP20210128" +
            ",/securityCenter/DISP20210129";*/

    /**
     * 登录时验证名下客户信息和账户信息情况
     * @param userPhone
     * @return
     */
    public boolean checkCustomOrAcount(String userId,String userPhone,String chanlNo,String operator){
        RequestHead head =new RequestHead();
        head.setChanlNo(chanlNo);
        head.setOperator(operator);
        head.setReqDate(DateUtil.getDateToString("YYYYMMddHHmmss"));
        head.setReqSeqNo(DateUtil.getDateToStringx("YYYYMMddHHmmss"));
        head.setVersionNo("02");
        Request<UserPhone> userPhoneRequest=new Request<>();
        log.info("远程调用用户服务手机号为："+userPhone);
        UserPhone userPhone1=new UserPhone();
        userPhone1.setUserPhone(userPhone);
        userPhoneRequest.setBody(userPhone1);
        userPhoneRequest.setHead(head);
        try {
            Result result = restTemplate.postForObject("http://" + "disPart-user" + "/securityCenter/checkCustomOrAcount", userPhoneRequest, Result.class);
            if(result==null){
                log.info("客户端用户登录检测名下客户和账户信息异常");
                return  false;
            }
            if(result.getCode()!=200){
                log.info("客户端用户登录检测名下客户和账户信息异常");
                return  false;
            }

        //更新用户信息表
        TUserInfo tUserInfo=new TUserInfo();
        tUserInfo.setUserId(userId);
        tUserInfo.setProvId(result.getData()+"");
        log.info("请求远程用户服务返回的客户编号："+result.getData());
        int fl=tUserInfoDao.updateProvId(tUserInfo);
        if(fl<=0){
            log.info("客户端用户登录时，处理更新客户编号信息异常");
            return  false;
        }
        }catch (Exception e){
            log.info(e.getMessage());
        }
        log.info("客户端用户登录时，处理更新客户编号信息更新成功");
        return  true;
    }


}