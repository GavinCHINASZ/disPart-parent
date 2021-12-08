package com.dispart.dto.customdto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


/**
 * @author:zhongfei
 * @date:Created in 2021/8/07 11:25
 * @description 修改客户请求dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class UpdateCustomInfoInDto {
    //客户编号
    private String provId;
    //客户名称
    private String provNm;
    //客户类型
    private String customTp;
    //客户简称
    private String shrtNm;
    //肋记码
    private String mnmnCode;
    //客户状态
    private String status;
    //扶贫类型
    private String pvtyTp;
    //手机号码
    private String phone;
    //是否实名
    private String isReal;
    //证件类型
    private String certTp;
    //证件号码
    private String certNum;
    //证件住址
    private String certAddr;
    //证件有效期
    @JsonFormat(pattern ="yyyy-MM-dd",timezone = "GMT+8")
    private Date certPrd;
    //税务登记号
    private String taxNum;
    //开户银行名称
    private String settlebanknm;
    //银行卡号
    private String bankNo;
    //法人名称
    private String legalName;
    //法人电话
    private String legalPhone;
    //法人证件类型
    private String legalCertTp;
    //法人身份证号
    private String legalCertNum;
    //代办人电话
    private String agentPhone;
    //代办人名称
    private String agentName;
    //代办人身份证号
    private String agentCertNo;
    //代办人地址
    private String agentAddr;
    // 代办人身份证正面url
    private String agentFrontUrl;
    // 代办人身份证反面url
    private String agentReverserUrl;
    // 代办委托书 AGENT_TERM_BOOK_URL
    private String agentTermBookUrl;

    //委托期限
    private String termPrctn;
    //委托日期
    @JsonFormat(pattern ="yyyy-MM-dd",timezone = "GMT+8")
    private Date termDueDt;
    //所属摊位
    private String booth;
    //备注
    private String remark;
    //经营范围
    private String businessRange;
    //身份证反面Url为空
    private String reverserUrl;
    //身份证正面Url为空
    private String frontUrl;
    //营业执照
    private String businessUrl;

}
