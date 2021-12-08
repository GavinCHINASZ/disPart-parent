package com.dispart.dto.hsbdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

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
public class QuryCustomInfoHsbResDto {
    //交易状态 00成功，01失败
    private String txnSt;
    //错误码
    private String errCode;
    //错误信息
    private String errMsg;
    //记录数
    private Long recordNum;
    private List<QuryCustomInfoHsbResParamDto> list;

 }
