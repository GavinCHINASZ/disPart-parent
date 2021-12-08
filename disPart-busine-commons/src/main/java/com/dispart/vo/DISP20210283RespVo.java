package com.dispart.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 转账结果查询 响应报文
 */
@Data
public class DISP20210283RespVo implements Serializable {
    private static final long serialVersionUID = -3021718053071454551L;

    //凭证号
    private String creditNo;
}
