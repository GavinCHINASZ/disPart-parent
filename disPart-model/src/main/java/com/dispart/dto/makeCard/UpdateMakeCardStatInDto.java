package com.dispart.dto.makeCard;

import lombok.Data;

/**
 * @author:zhongfei
 * @date:Created in 2021/8/07 11:25
 * @description 制卡申请状态修改请求dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class UpdateMakeCardStatInDto {
    //单据号
    private String documentNum;
    //状态 0-申请 1-受理 2-制卡完成 3-寄送中 4-寄送结束 7-制卡故障 8-寄送故障 9-取消
    private String status;
    //寄送单号
    private String sendNo;
    //故障原因
    private String remark;

}
