package com.dispart.vo.basevo;

import lombok.Data;

import java.util.Date;

/**
    * 卡片明细表
    */
@Data
public class TCardInfoVo {

    public static final String APPLY="2";//申请中
    public static final String NOTUSR="0";//未使用
    public static final String ISUSR="1";//使用中
    public static final String LOGOUT="3";//已注销
    public static final String CANCEL="4";//取消



    /**
    * 卡号
    */
    private String cardNo;

    /**
     * 卡类型
     */
    private String cardTp;

    /**
    * 单据号
    */
    private String documentNum;

    /**
    * 状态 0-未使用 1-已使用
    */
    private String status;

    /**
    * 备注(异常时填写)
    */
    private String remark;

    /**
    * 操作员
    */
    private String operId;

    /**
    * 创建时间/申请时间
    */
    private Date creatTime;

    /**
    * 更新时间
    */
    private Date upTime;
}