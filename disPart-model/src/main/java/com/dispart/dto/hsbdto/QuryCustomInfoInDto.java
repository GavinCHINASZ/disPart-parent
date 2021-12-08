package com.dispart.dto.hsbdto;

import lombok.Data;

import java.util.Date;

/**
 * @author:zhongfei
 * @date:Created in 2021/6/13 21:24
 * @description 惠市宝商家信息查询
 * @modified by:
 * @version: 1.0
 */
@Data
public class QuryCustomInfoInDto {
    //开始日期
    private String startDt;
    //结束日期
    private String endDt;
    //市场编号
    private String marketId;
    //删除标识
    private String delFlag;
    //商家自定义编号
    private String provCustId;
    //版本号
    private String version;
}
