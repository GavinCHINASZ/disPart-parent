package com.dispart.baseDto;

import lombok.Data;

import java.util.List;

@Data
public class BaseSelectionOutDto<T> {

    private Integer tolPageNum;

    private List<T> dataList;

}
