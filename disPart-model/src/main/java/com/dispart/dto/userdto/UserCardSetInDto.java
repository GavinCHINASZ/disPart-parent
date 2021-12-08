package com.dispart.dto.userdto;

import lombok.Data;

@Data
public class UserCardSetInDto {
    //用户呢称
    private String userNickNm;
    //用户头像
    private String userIcon;
    //用户手机号
    private String userPhone;
    //用户新手机号
    private String userNewPhone;
    //修改类型  0-修改呢称 1-修改头像 2-修改手机号
    private String updateTp;


}
