package com.dispart.dto.hsbdto;

import lombok.Data;
import lombok.ToString;

/**
 * @author:zhongfei
 * @date:Created in 2021/6/13 21:24
 * @description 惠市宝商家信息查询
 * @modified by:
 * @version: 1.0
 */
@Data
@ToString
public class QuryCustomInfoHsbReqDto {
    //发起方时间戳
    private String sndDtTm;
    //发起方流水号
    private String sndTraceId;
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
