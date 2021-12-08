package com.dispart.vo.entrance;

import lombok.Data;

import java.util.Date;

/**
 * 产地信息
 */
@Data
public class THopmmProducingarea {
    private String id;
    private String areacode;
    /**
     * 产地名称
     */
    private String areanames;
    private String irstfight;
    private String goodsid;
    private String memo;
    private Date entdate;
    private Date pddate;
    private String oprcd;
    private String orgcode;
}
