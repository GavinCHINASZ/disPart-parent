package com.dispart.dto.userdto;

import com.dispart.vo.entrance.TSubsidInfo;
import com.dispart.vo.user.TCustomBankcardVo;
import lombok.Data;

import java.util.List;

/**
 * @author 黄贵川
 * @version 1.0.0:
 * @title TCustomBankcardDto
 * @description 客户银行卡信息
 * @date 2021/8/25
 * @Copyright 2020-2021
 */
@Data
public class TCustomBankcardDto {
    // 补贴发放记录表
    private List<TCustomBankcardVo> reportList;

    // 总条数
    private Integer tolPageNum;
}