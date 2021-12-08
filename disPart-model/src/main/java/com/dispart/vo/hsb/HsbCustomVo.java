package com.dispart.vo.hsb;

import lombok.Data;

/**
 * @author:xts
 * @date:Created in 2021/6/13 21:30
 * @description 惠市宝签约客户信息增量交易输入信息
 * @modified by:
 * @version: 1.0
 */
@Data
public class HsbCustomVo {
    private String sndDtTm;
    private String operType;
    private String provId;
    private String provNm;
    private String posNo;
    private String provCertld;
    private String provCertNo;
    private String provCntNo;
    private String certType;
    private String certNum;
    private String contacts;
    private String telephone;
    private String provCustld;
    private String delFlag;
}