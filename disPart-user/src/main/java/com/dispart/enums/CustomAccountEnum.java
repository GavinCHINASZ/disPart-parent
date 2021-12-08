package com.dispart.enums;

import lombok.Getter;


/**
 * @author:xiejin
 * @date:Created in 2021/8/10 20:32
 * @description
 * @modified by:
 * @version: 1.0
 */
@Getter
public enum CustomAccountEnum {
    CUSTOM_ACCOUNT_ENUM_NULL(-1,"没有查询到数据"),
    OPEN_ACCOUNT_FAIL(-2,"开户失败"),
    CANCCLELATION_ACCOUNT_FAIL(-3,"销户失败"),
    FROZEN_ACCOUNT_FAIL(-4,"冻结卡失败"),
    UNFREEZE_ACCOUNT_FAIL(-5,"解冻卡失败"),
    RECONCILIATION_APPLY_FAIL(-6,"调账申请失败"),
    RECONCILIATION_PROCESSING_FAIL(-7,"调账处理失败"),
    WITHDRAWAL_APPLY_FAIL(-8,"提现申请失败"),
    WITHDRAWAL_REVIEW_FAIL(-9,"提现复核失败"),
    WITHDRAWAL_PROCESSING_FAIL(-10,"提现处理失败"),
    CARD_REPORTLOSS_FAIL(-11,"客户卡挂失失败"),
    CARD_SOLUTIONS_FAIL(-12,"客户卡解挂失败"),
    CARD_RECHANGE_FAIL(-13,"客户卡补卡换卡失败"),
    CARD_CHANGEPASS_FAIL(-14,"客户卡修改密码失败"),
    CARD_RESET_FAIL(-15,"客户卡重置失败"),
    CARD_IS_FAIL(-16,"没有查询到你输入的实体卡"),
    CARD_IS_USER(-17,"你输入的实体卡已经使用"),
    CARD_IS_OPENACCOUNT(-18,"此卡已经被开户，请重新换卡"),
    AVAILBAL_IS_ZERO(-19,"用户可用余额为0,不能冻结"),
    FREEZEAMT_IS_BEYONGD_AVAILBAL(-20,"冻结金额大于可用余额，不能冻结"),
    FREEZEAMT_IS_ZERO(-21,"冻结金额为零，不能调账"),
    FREEZEAMT_IS_NEGATIVE(-22,"冻结金额为负，不能调账"),
    UNFREEZEAMT_IS_BEYONGD_AVAILBAL(-23,"解冻金额大于可用余额，不能解冻"),
    UNFREEZEAMT_IS_ZERO(-24,"解冻金额为零，不能解冻"),
    UNFREEZEAMT_IS_NEGATIVE(-25,"解冻金额为负，不能解冻"),
    UNFREEZEAMT_AVAILBAL_IS_ZERO(-31,"用户可用余额为0,不能解冻"),
    RECONCILIATION_IS_ZERO(-26,"调账金额为零，不能调账"),
    RECONCILIATION_IS_NEGATIVE(-27,"调账金额为负，不能调账"),
    RECONCILIATION_IS_BEYONGD_AVAILBAL(-28,"调账金额大于可用余额，不能调账"),
    RECONCILIATION_IS_USER(-31," 你的这笔交易已经调账，不能再次进行调账"),
    WITHDRAWAL_IS_ZERO(-29,"提现金额为零，不能提现"),
    WITHDRAWAL_IS_NEGATIVE(-30,"提现金额为负，不能提现"),
    WITHDRAWALAPPLI_IS_USER(-32,"你的这笔提现交易已经复核，不能再次进行复核"),
    ADD_FROZEN_RECORD_ERROR(-33,"插入冻结记录失败"),
    UPDATE_ACCNO_INFO_ERROR(-34,"更新客户账户信息失败"),
    CASH_WITHDRAW_ERROR(-35,"提现金额加手续费大于可用余额"),
    SAVE_CASH_WITHDRAW_ERROR(-36,"保存现金提现申请记录失败");

    private Integer code;
    private String message;

    private CustomAccountEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
