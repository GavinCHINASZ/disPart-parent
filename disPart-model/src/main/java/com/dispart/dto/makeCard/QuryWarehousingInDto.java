package com.dispart.dto.makeCard;

import lombok.Data;

/**
 * @author:zhongfei
 * @date:Created in 2021/8/07 11:25
 * @description 入库制卡查询dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class QuryWarehousingInDto {
    //单据号
    private String documentNum;
    //状态
    private String status;
    //分页页数
    private Integer pageSize;
    //分页条数
    private Integer pageNum;
    //分面起始
    private Integer strNum;



}
