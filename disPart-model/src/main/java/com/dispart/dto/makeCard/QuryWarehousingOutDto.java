package com.dispart.dto.makeCard;

import lombok.Data;

import java.util.List;

/**
 * @author:zhongfei
 * @date:Created in 2021/8/07 11:25
 * @description 制卡申请查询响应dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class QuryWarehousingOutDto {
    //返回员工信息列表
    private List<QuryWarehousingParamOutDto> list;
    //总条数
    private Integer tolPageNum;
}
