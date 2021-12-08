package com.dispart.vo.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dispart.vo.basevo.PageInfo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * 客户银行卡信息 TCustomBankcardVo
 *
 * @author 黄贵川
 * @version 1.0.0:
 * @date 2021/8/10
 * @Copyright 2020-2021
 */
@Data
@ApiModel(description = "客户银行卡信息")
@TableName("t_custom_bankcard")
public class TCustomBankcardVo extends PageInfo {
    /**
     * 客户编号+
     */
    private String provId;

    /**
     * 银行卡号
     */
    private String bankNo;

    /**
     * 银行联行号
     */
    private String bankPayNo;

    /**
     * 客户账号
     */
    private String provAcct;

    /**
     * 会员卡卡号
     */
    private String cardNo;

    /**
     * 客户名称/持卡人
     */
    private String provNm;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 是否建设卡 0-否  1-是
     */
    private String isCcb;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作员
     */
    private String operId;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 更新时间
     */
    private Date upTime;

    /**
     * 下单二维码url
     */
    private String qrcodeUrl;
}