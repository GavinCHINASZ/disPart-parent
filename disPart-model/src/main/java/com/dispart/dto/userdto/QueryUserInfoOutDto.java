package com.dispart.dto.userdto;

import lombok.Data;

@Data
public class QueryUserInfoOutDto {
    //用户姓名
    private String usrNm;
    //用户昵称
    private String userNickNm;
    //用户手机号
    private String userPhone;
    //用户头像
    private String userIcon;
    //用户编号
    private String userId;
}
