package com.dispart.result;

import lombok.Getter;

/**
 * @author:zhongfei
 * @date:Created in 2021/6/16 22:57
 * @description 用户业务接口返回码
 * @modified by:
 * @version: 1.0
 */
@Getter
public enum UserResultCodeEnum {
    USER_PARAM_NULL(100,"输入参数为空"),
    USER_INSERT_ERROR(101,"新增用户失败"),
    USER_INFO_NULL(102,"用户信息不存在"),
    USER_UPDATE_FAIL(103,"用户信息更新失败"),
    USER_LOCK_ON_FAIL(104,"锁定解锁用户失败"),
    USER_LOGOFF_FAIL(105,"注销用户信息失败"),
    USER_INFO_LIST_NULL(106,"查询用户信息列表为空"),
    USER_EMP_ROLE_NULL1(107,"查询用户角色绑定信息为空"),
    USER_EMP_OLE_INSERT_FAIL(108,"新增员工角色绑定关系失败"),
    USER_PARAM_ERROR(109,"输入参数错误"),
    USER_NONSUPPORT_OPER_TP(110,"不支持的类型"),
    USER_UPDATE_PERSONCODE_FAIL(111,"修改个人名片信息失败"),
    USER_NO_REPEAT_REG(112,"手机号已注册不能重复注册"),
    USER_APP_INFO_NULL(113,"未查询到appId"),
    USER_PASSWD_SAME_ERROR(114,"原密码和新密码一样"),
    USER_REG_CODE_ERROR(115,"手机验证码错误"),
    USER_PASSWD_ERROR(116,"用户密码错误"),
    USER_SET_ORDER_TP_FAIL(117,"配置下单模式失败"),
    USER_QRY_ORDER_TP_NOEXIT(118,"查询客户下单模式不存在"),
    USER_VERIFY_PASSWD_FAIL(119,"确认密码失败"),
    USER_AUTH_MENU_NULL(120,"查询权限菜单为空"),
    USER_ORDER_TP_IDENT(121,"已存在相同类型下单模式配置"),
    USER_EMP_ROLE_ORG_NULL(122,"绑定失败，查询员工机构对应角色为空"),
    USER_EMP_ROLE_ORG_ERRR(123,"绑定失败，员工机构与角色机构不对应"),
    USER_EMP_ROLE_INFO_ISEXIT(124,"绑定失败，员工机构与角色机构不对应"),
    USER_QURY_COUNT_ISNULL(125,"未查询到记录"),
    USER_QURY_COUNT_ISZERO(126,"总记录数为0"),
    USER_EMP_LOG_OFF(127,"注销状态，不能进行绑定解绑操作"),
    USER_BIND_TEL_EXIT(128,"该手机号已被使用，请更换其他手机号进行登录！"),
    USER_LOGIN_FAIL(129,"登录失败，请稍后再试！"),
    USER_LOGIN_SUESS(130,"登录成功！"),
    USER_ID_NULL(131,"客户已绑定设备，不能再次绑定"),
    USER_NONSUPPORT_CHNAL_TP(132,"暂不支持的渠道类型"),
    USER_DEVICEMANEGER_NULL(133,"未找到该设备"),
    USER__CUSTOM_INFO_NULL(134,"客户管理信息不存在"),
    USER__CUSTOM_INFO_DISABLE(135,"更新客户状态失败"),
    USER__CUSTOM_INFO_UPDATE(136,"更新客户信息失败"),
    USER__CUSTOM_INFO_INSERT(137,"新增客户信息失败"),
    USER__CUSTOM_QRCODE_ISNULL(138,"溯源二维码url为空"),
    USER__CUSTOM_CERTNUM_ISEXIT(139,"身份证号已绑定其它客户"),

    USER_ACC_STATUS_ERROR(140,"银行卡状态异常"),
    USER_BANK_NAME_DISSIMILARITY(141,"客户名称与持卡人名称不一致"),
    USER_ADD_CUSTOM_BANKCARD_ERROR(142,"绑定银行卡失败"),
    USER_DELETE_CUSTOM_BANKCARD_ERROR(143,"解绑银行卡失败"),
    USER_MESSAGE_SELECT_ERROR(144,"账户信息查询异常"),
    USER_CUSTOM_BANK_CARD_EXIST(145,"客户账号下已绑定此银行卡"),
    USER_UPLOAD_FILE_FAIL(146,"上传文件失败"),
    USER_DATA_NO_ERROR(147, "未查询到相关数据！"),
    USER_CUSTOM_INFO_ISEXIT(148, "该手机号已被使用！");

    private Integer code;
    private String message;
    private UserResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
