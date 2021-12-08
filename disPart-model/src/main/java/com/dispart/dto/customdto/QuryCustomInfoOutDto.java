package com.dispart.dto.customdto;

import lombok.Data;

import java.util.List;

/**
 * @author:zhongfei
 * @date:Created in 2021/8/07 11:25
 * @description 查询客户信息响应dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class QuryCustomInfoOutDto {
    //返回员工信息列表
    private List<QuryCustomInfoOutParamDto> list;
    //总条数
    private Integer tolPageNum;
}
