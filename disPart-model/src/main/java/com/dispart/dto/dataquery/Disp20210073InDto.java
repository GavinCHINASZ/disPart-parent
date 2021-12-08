package com.dispart.dto.dataquery;

import lombok.Data;

import java.util.Date;

@Data
public class Disp20210073InDto {

    /* 用户ID */
    private String userId;
    /* 用户名称 */
    private String userNm;
    /* 用户手机号码 */
    private String userPhone;
    /* 分页条数 */
    private Long pageSize;
    /* 分页页数 */
    private Long curPage;
    /* 起始记录数 */
    private Long startIndex;
    /* 用户昵称 */
    private String userNickNm;

    /* 注册时间 */
    //@JsonFormat(pattern = "yyyy-MM-dd")
    private Date regDt;
    private Date txnStDate;
    private Date txnEndDate;

    /**
     * 渠道号
     */
    private String chanlNo;
}
