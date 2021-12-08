package com.dispart.dto.entrance;

import lombok.Data;

import java.util.List;

/**
 * 车辆进出管理
 */
@Data
public class D_0297FindListDto<T> {
    private List<T> list;

    // 总条数
    private Integer tolPageNum;
}