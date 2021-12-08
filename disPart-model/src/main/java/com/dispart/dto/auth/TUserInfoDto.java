package com.dispart.dto.auth;

import lombok.Data;

import java.util.List;

/**
 * @author:xts
 * @date:Created in 2021/6/20 13:48
 * @description 用户及对应的角色和部门信息
 * @modified by:
 * @version: 1.0
 */
@Data
public class TUserInfoDto {
    private String userName;
    private String mobile;
    private String userId;
    private String userAccount;
    private List<String > roleList;
    private String orgCode;
    private String userJson;
    private String userToken;
}