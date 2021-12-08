package com.dispart.dto.hsbdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author:zhongfei
 * @date:Created in 2021/6/13 21:24
 * @description 惠市宝商家信息查询
 * @modified by:
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuryCustomInfoHsbResParamDto {
    //删除标识
    @JsonProperty
    private String delFlag;
    //商家编号
    private String provId;
    //商家名称
    private String provNm;
    //pos编号
    private String posNo;
    //商家证件类型
    private String provCertId;
    //商家证件号码
    private String provCertNo;
    //商家柜台编号
    private String provCntNo;
    //证件类型
    private String certType;
    //证件号码
    private String certNum;
    //联系人
    private String contacts;
    //联系人电话
    private String telephone;
    //商家自定义编号
    private String provCustId;

}
