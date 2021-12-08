package com.dispart.model.businessCommon;

import lombok.Getter;

/**
 * 交易类型
 */
@Getter
public enum TxnTypeEnum {
    CHARGE("0","充值"),
    CASHOUT("1","提现"),
    ENTRYFEENTR("2","进场费"),
    CARFEE("3","出场费"),
    WITHDRAW("4","账单退款"),
    MARKET("5","交易"),
    BILL("6","账单缴费"),
    MCARD("7","月卡缴费"),
    SUPPLY_SUBSIDY("8","供应商补贴发放"),
    SUPPLY_CALCELSUBSIDY("9","供应商补贴撤回"),
    IN_WITHDAW("10","出场退费"),
    ENTRYFEENTR_PRE("12","出场退费代充值"),
    MCARD_WITHDRAW("13","月卡退费"),
    PURCH_SUBSIDY("14","采购商补贴发放"),
    PURCH_CALCELSUBSIDY("15","采购商补贴撤回"),
    CARFEE_PRE("16","进场退费"),
    FREEZE("17", "冻结"),
    UNFREEZE("18", "解冻"),
    ADJUST("19", "调账");

    private String txnType;
    private String desc;

    TxnTypeEnum(String txnType, String desc) {
        this.txnType = txnType;
        this.desc = desc;
    }

    public static String getDesc(String type) {
        TxnTypeEnum[] values = TxnTypeEnum.values();
        for (TxnTypeEnum txnTypeEnum:values) {

            if (txnTypeEnum.getTxnType().equals(type)) {
                return txnTypeEnum.getDesc();
            }
        }
        return "";
    }
}
