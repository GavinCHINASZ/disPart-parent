package com.dispart.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 行内提现响应报文
 */
@Data
public class DISP20210282RespVo implements Serializable {
    private static final long serialVersionUID = -8693281211397035137L;

    //凭证号
    private String creditNo;

    //自定义输出名称1
    private String individualName1;

    //自定义输出内容1
    private String individual1;

    //自定义输出名称2
    private String individualName2;

    //自定义输出内容2
    private String individual2;

    //备注1
    private String remark;

    //备注2
    private String remark2;

}
