package com.dispart.result;

import lombok.Getter;

@Getter
public enum  ResultCodeOrderEnum {

    ORDER_GOOD_INSERT_ERROR(-250,"插入ORDERGOODS失败"),
    ACT_INSERT_ERROR(-251,"插入活动表失败"),

    ORDER_INSERT_ERROR(-200,"子订单新增失败"),
    ORDER_INSERT_EXCEPTION_ERROR(-201,"数据库子订单新增异常"),
    ORDER_SELECT_ERROR(-202,"子订单查询失败"),
    ORDER_UPDATE_ERROR(-203,"子订单更新失败"),
    ORDER_COUNT_ERROR(-204,"子订单列表为空"),
    ORDERID_NULL_ERROR(-205,"订单号为空"),
    ORDERID_PARAMETER_ERROE(-206,"参数错误"),

    PRODUCT_INVENTORY_ERROR(-207,"库存不足"),
    PRODUCT_INVENTORY_SELECT_ERROR(-208,"未查询到商品"),

    UNIT_SELECT_ERROE(-209,"查询不到单位"),

    PALCE_SELECT_ERROR(-210,"未查询下单模式"),
    PRODUCT_INVENTORY_NOTEXIST(-211,"库存不足"),
    ORDER_INVENTORY_UPDATE_ERROR(-212,"库存更新失败"),

    GET_WULIUYUANPROV_ERROR(-213,"找不到物流园id"),

    MAIN_ORDER_INSERT_ERROR(-214,"主订单新增失败"),
    MAIN_ORDER_UPDATE_ERROR(-215,"主订单更新失败"),
    MAIN_ORDER_SELECT_ERROR(-216,"主订单查询失败"),

    MAIN_ORDER_UPDATE_NOTNESS_ERROR(-217,"主订单不存在"),
    MAIN_ORDER_UPDATE_DONE_ERROR(-218,"主订单已完成"),
    MAIN_GET_MAINID_ERROR(-219,"生成主ID号失败"),
    ORDER_RELE_INSERT_ERROR(-220,"订单关联表新增失败"),
    ORDER_RELE_SELECT_ERROR(-221,"没找到关联表中的数据"),
    ORDER_RELE_UPDATE_ERROR(-222,"订单关联表更新失败"),
    FROM_HSB_OUT_ERROR(-223,"外链接口返回异常"),
    MONEY_TOO_BIG(-224,"分账金额太大"),
    BILI_TOO_BIG(-225,"分账比例太大"),

    ORDER_GETDETAIL(-226,"获取子订单失败"),
    ORDER_ERROR_MONEY(-227,"金额匹配失败"),
    ORDER_STATUS_ERROE(-228,"子订单状态错误"),

    ORDER_MERCHANT_NAME_NOT_EXIST(-229,"查不到商户姓名"),

    APO_DETAIL_FAIL(-230,"插入分账明细数据表失败"),
    APO_DETAIL_UPDATE_FAIL(-231,"更新分账明细数据表失败"),
    APO_SUM_FAIL(-232,"插入分账汇总数据表失败"),
    APO_SUM_UPDATE_FAIL(-233,"更新分账汇总数据表失败"),
    APO_CHK_FAIL(-234,"插入分账文件数据表失败"),
    APO_SUB_FAIL(-235,"插入分账文件数据表失败"),

    PART_MODEL_FAIL(-236,"分账模式查询错误"),
    ORDER_HAS_MAINORDER(-237,"该子订单已存在主订单"),
    DOFAIL(-238,"操作失败"),

    FILE_INSERT_FAIL(-239,"插入文件失败"),
    FILE_UPDATE_FAIL(-240,"更新文件失败"),
    FILE_SELECT_FAIL(-241,"查找文件失败"),

    PARA_TEL_EMPTY(-242,"手机号为空"),
    PARA_PROVID_EMPTY(-243,"商户ID为空"),
    SAME_ORDER_STATUS(-244,"订单状态相同不需要更新"),

    NOT_FOUND_MERCHANTCODE(-245,"商户关联表未查询到数据"),
    HAS_ORDER_IN_HALF_HOUR(-246,"已有重复订单"),
    REMOTE_INSERT_PAYJRN(-247,"调用新增流水失败"),
    WULIUYUAN2_SELECT_ERROR(-248,"查不到物流园收款账户"),

    CARD_STATUS_ERROR(-249,"找不到付款人卡号或付款人卡号状态错误"),
    ;

    private Integer code;
    private String message;

    private ResultCodeOrderEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
