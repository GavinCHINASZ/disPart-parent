package com.dispart.dto.entrance;

import com.dispart.vo.entrance.TSubsidInfo;
import lombok.Data;

import java.util.List;

/**
 * 补贴申请dto
 *
 * @author 黄贵川
 * @date  2021/8/24
 * @version 1.0
 */
@Data
public class D_0299FindOutDto {
    // 补贴发放记录表
    private List<TSubsidInfo> reportList;

    // 总条数
    private Integer tolPageNum;
}
