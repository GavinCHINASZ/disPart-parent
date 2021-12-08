package com.dispart.dto.customdto;

import com.dispart.vo.user.TCommonBankNameVo;
import lombok.Data;

import java.util.List;

/**
 * @author 黄贵川
 * @date 2021/8/18
 * @description 查询 银行常用列表dto
 * @version 1.0
 */
@Data
public class QueryTCommonBanNameListDto {
    // 银行常用列表
    private List<TCommonBankNameVo> list;

    // 总条数
    private Integer tolPageNum;
}
