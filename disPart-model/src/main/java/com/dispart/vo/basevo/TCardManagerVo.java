package com.dispart.vo.basevo;

import lombok.Data;

import java.util.Date;

/**
    * 卡片管理
    */
@Data
public class TCardManagerVo {
    //申请
    public static final String APPLY="0";
    //受理
    public static final String ACCEPT="1";
    //制卡完成
    public static final String COMPLETE="2";
    //寄送中
    public static final String SENDING="3";
    //寄送结束
    public static final String SENDED="4";
    //制卡故障
    public static final String MAKEERR="7";
    //寄送故障
    public static final String SENDERR="8";
    //取消
    public static final String CANCEL="9";
    //正常
    public static final String NORMAL="5";
    //注销
    public static final String LOGOUT="6";
    /**
    * 单据号 YYMM + 4位序列号
    */
    private String documentNum;

    /**
    * 卡类型
    */
    private String cardTp;

    /**
    * 主副卡标识
    */
    private String deputy;

    /**
    * 起始卡号
    */
    private String startCard;

    /**
    * 终止卡号
    */
    private String endCard;

    /**
    * 张数
    */
    private Integer num;

    /**
    * 卡状态 0-申请 1-受理 2-制卡完成 3-寄送中 4-寄送结束 7-制卡故障 8-寄送故障 9-取消
    */
    private String status;

    /**
    * 寄送时间
    */
    private Date sendTm;

    /**
    * 寄送单号
    */
    private String sendNo;

    /**
    * 入库时间
    */
    private Date entryTm;

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