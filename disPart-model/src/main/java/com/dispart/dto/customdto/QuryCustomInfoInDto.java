package com.dispart.dto.customdto;

import lombok.Data;

import java.util.Date;

/**
 * @author:zhongfei
 * @date:Created in 2021/8/07 11:25
 * @description 查询客户信息请求dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class QuryCustomInfoInDto {
    //肋记码
    private String mnmnCode;
    //客户编号
    private String provId;
    //客户名称
    private String provNm;
    //客户类型
    private String customTp;
    //手机号码
    private String phone;
    //证件号码
    private String certNum;
    //客户状态
    private String status;
    //是否实名
    private String isReal;
    //银行卡号
    private String bankNo;
    //分页页数
    private Integer pageSize;
    //分页条数
    private Integer pageNum;
    //分页条数
    private Integer strNum;
    //查询字段
    private String value;

    /**
     * 客户简称
     */
    private String shrtNm;

    /**
     * 渠道类型：01-贵农购 02-微信小程序 03-支付宝小程序 04-农批系统 05-智慧贵农app 06-外联服务
     */
    private String chanlNo;

    /**
     * 操作人名称
     */
    private String operName;
    private String operId;

    /**
     * 创建时间(入驻时间)
     */
    private Date creatTime;

    private Date creatStTime;
    private Date creatEndTime;
}
