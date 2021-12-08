package com.dispart.dto.auth;

import lombok.Data;

import java.util.List;

/**
 * @author:xts
 * @date:Created in 2021/6/19 20:28
 * @description 部门的权限信息
 * @modified by:
 * @version: 1.0
 */
@Data
public class OrgAuthDto {
    private String orgCode;
    private String orgName;
    private List<AuthDto> authList;
}