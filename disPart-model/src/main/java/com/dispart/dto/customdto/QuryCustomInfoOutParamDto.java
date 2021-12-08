package com.dispart.dto.customdto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.dispart.vo.user.TCustomBankcardVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author:zhongfei
 * @date:Created in 2021/8/07 11:25
 * @description 查询客户信息响应paramdto
 * @modified by:
 * @version: 1.0
 */
@Data
public class QuryCustomInfoOutParamDto {

    private String provId;
    // 税务登记号
    private String taxNum;
    // 客户类型
    private String customTp;
    // 扶贫类型
    private String pvtyTp;
    // 客户名称
    private String provNm;
    // 客户简称
    private String shrtNm;
    // 证件类型
    private String certType;
    //证件号码
    private String certNum;
    // 证件地址
    private String certAddr;
    // 证件有效期
    @JsonFormat(pattern ="yyyy-MM-dd",timezone = "GMT+8")
    private Date certPrd;
    private String phone;
    // 法人名称
    private String legalName;
    private String legalPhone;
    // 法人证件类型
    private String legalCertTp;
    private String legalCertNum;
    private String agentName;
    private String agentPhone;
    private String agentCertNo;
    private String agentAddr;
    // 代办人身份证正面url
    private String agentFrontUrl;
    // 代办人身份证反面url
    private String agentReverserUrl;
    // 代办委托书 AGENT_TERM_BOOK_URL
    private String agentTermBookUrl;

    private String termPrctn;
    @JsonFormat(pattern ="yyyy-MM-dd",timezone = "GMT+8")
    private Date termDueDt;
    private String frontUrl;
    private String reverserUrl;
    private String businessUrl;
    /**
     * 客户状态 0-启用 1-禁用 3-待审核 4-审核通过 5-审核未通过
     */
    private String status;

    /**
     * 是否实名 0-实名  1-未实名
     */
    private String isReal;
    /**
     * 助记码
     */
    private String mnmnCode;
    /**
     * 下单二维码url
     */
    private String qrcodeUrl;
    /**
     * 审核人
     */
    private String auditor;
    /**
     * 审核时间
     */
    private Date auditorTm;
    private String remark;

    private String operId;
    private String operName;

    private Date creatTime;
    private Date upTime;
    /**
     * 经营范围
     */
    private String  businessScope;

    /**
     * 经营范围
     */
    private String  businessRange;

    /**
     * 渠道类型：01-贵农购 02-微信小程序 03-支付宝小程序 04-农批系统 05-智慧贵农app 06-外联服务
     */
    private String chanlNo;

    /**
     * 其它文件URL
     */
    private String otherFileUrl;

    //银行卡
    private String bankNo;
    //证件类型
    private String certTp;
    //是否允许提现
    private String isWithdraw;
    //客户银行卡list
    @JsonIgnore
    private List<TCustomBankcardVo> backCardList;
}
