package com.dispart.dto.auth;

import com.dispart.vo.commons.TUserInfo;
import lombok.Data;

/**
 * @author:xts
 * @date:Created in 2021/6/24 13:38
 * @description
 * @modified by:
 * @version: 1.0
 */
@Data
public class WeiXinResultDto {
    private String reg;//是否注册 0-未注册 1-已注册
    private TUserInfo userInfo;
}