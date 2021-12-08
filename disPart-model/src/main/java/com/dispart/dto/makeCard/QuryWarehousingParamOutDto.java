package com.dispart.dto.makeCard;

import lombok.Data;

/**
 * @author:zhongfei
 * @date:Created in 2021/8/07 11:25
 * @description 入库制卡信息查询响应dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class QuryWarehousingParamOutDto {
    //单据号
    private String documentNum;
    //寄送单号
    private String sendNum;
    //起始卡号
    private String startCard;
    //终止卡号
    private String endCard;
    //张数
    private String num;
    //状态
    private String status;
    //寄送单号
    private String sendNo;
    //故障原因
    private String remark;
}
