package com.dispart.dto.auth;

import lombok.Data;

import java.util.List;

/**
 * @author:xts
 * @date:Created in 2021/6/19 16:48
 * @description 角色权限推送
 * @modified by:
 * @version: 1.0
 */
@Data
public class RoleAuthDto {
    private String roleCode;
    private String roleName;
    private List<AuthDto> authList;
}