package com.dispart.dto.makeCard;

import lombok.Data;

import java.util.Date;

/**
 * @author:zhongfei
 * @date:Created in 2021/8/07 11:25
 * @description 制卡申请请求dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class QuryMakeCardInfoOutParamDto {
    //单据号
    private String documentNum;
    //起始卡号
    private String startCard;
    //终止卡号
    private String endCard;
    //张数
    private String num;
    //申请时间
    private Date creatTime;
    //状态
    private String status;
    //寄送单号
    private String sendNo;
    //故障原因
    private String remark;

}
