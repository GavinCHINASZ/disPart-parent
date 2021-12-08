package com.dispart.enums.bill;

import lombok.Getter;

@Getter
public enum BillStatus {

    NORMAL("0","正常"),
    ABOLISHED("1","作废"),
    ;

    private String code;
    private String desc;

    private BillStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
