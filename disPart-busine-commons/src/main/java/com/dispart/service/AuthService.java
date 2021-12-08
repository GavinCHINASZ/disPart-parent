package com.dispart.service;

import com.dispart.dto.auth.LoginDto;
import com.dispart.dto.auth.WxCheck;
import com.dispart.request.Request;
import com.dispart.result.Result;

/**
 * @author:xts
 * @date:Created in 2021/6/19 19:19
 * @description 安全中心交易对接
 * @modified by:
 * @version: 1.0
 */
public interface AuthService {
    /**
     * 回调角色权限信息
     * @return
     */
    Result getRoleAuth();

    /**
     * 推送角色权限信息
     * @param roleId
     * @return
     */
    Result pushRoleAuth(String roleId);

    /**
     * 回调部门权限
     * @return
     */
    Result getDepAuth();

    /**
     * 推送部门权限
     * @param depId
     * @return
     */
    Result pushDepAuth(String depId);


    /**
     * 多渠道统一登录验证
     * @param param
     * @return
     */
    Result checkUserLogin(String param);

    /**
     * 检测微信是否注册
     * @param request
     * @return
     */
    Result wxCheck(Request<WxCheck> request);




    /**
     * 获取客户端用户信息
     * @param userId
     * @param chanlNo
     * @return
     */
    Result  getUserInfo( String userId,  String chanlNo);

    /**
     * 获取客户端登录的用户对应权限菜单
     * @param userId
     * @param chanlNo
     * @return
     */
    Result getUserAuthMenuInfo( String userId,String chanlNo);

    /**
     * 获取客户端用户的某个角色权限信息
     * @param userId
     * @param chanlNo
     * @param roleId
     * @return
     */
    Result getUserAuthMenuInfoByRoleId( String userId, String chanlNo,String roleId);
    /**
     * 获取客户端登录的用户对应的菜单下的按钮权限
     * @param userId
     * @param menuId
     * @return
     */
    Result getUserAuthButtonInfo(String userId,String chanlNo,String menuId,String roleId);

}