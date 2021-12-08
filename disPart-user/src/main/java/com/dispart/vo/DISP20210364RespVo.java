package com.dispart.vo;

import com.dispart.parmeterdto.AccnoChangeDetailDto;
import lombok.Data;

import java.util.List;

@Data
public class DISP20210364RespVo {

    /* 记录数 */
    private int tolPageNum;

    /* 明细 */
    private List<AccnoChangeDetailDto> list;
}
