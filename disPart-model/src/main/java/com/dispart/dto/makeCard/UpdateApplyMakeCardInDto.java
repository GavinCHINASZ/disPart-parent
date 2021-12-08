package com.dispart.dto.makeCard;

import lombok.Data;

/**
 * @author:zhongfei
 * @date:Created in 2021/8/07 11:25
 * @description 制卡申请修改请求dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class UpdateApplyMakeCardInDto {
    //单据号
    private String documentNum;
    //张数
    private String num;
}
