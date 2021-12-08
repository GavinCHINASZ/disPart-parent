package com.dispart.dto.base;

import com.dispart.vo.base.DiscountsActivityVo;
import lombok.Data;

import java.util.List;

/**
 * 优惠活动表
 *
 * @author 黄贵川
 * @date 2021/08/24
 */
@Data
public class DiscountsActivityDto {
    private List<DiscountsActivityVo> list;

    // 总条数
    private Integer tolPageNum;
}