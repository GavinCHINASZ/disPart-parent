package com.dispart.dto.customdto;

import lombok.Data;

/**
 * @author:zhongfei
 * @date:Created in 2021/8/07 11:25
 * @description 客户新增请求dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class DisableCustomInfoInDto {
    //客户编号
    private String provId;
    //状态
    private String status;
    //是否允许客户提现
    private String isWithdraw;
}
