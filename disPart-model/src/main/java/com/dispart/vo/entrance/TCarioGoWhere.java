package com.dispart.vo.entrance;

import lombok.Data;

import java.util.Date;

/**
 * 去向地信息管理
 */
@Data
public class TCarioGoWhere {
    /**
     * 去向地编号
     */
    private String id;
    private String address;
    private String orgcode;
    private Date createTime;
    private Date updateTime;
    private String operator;
    private String text1;
}
