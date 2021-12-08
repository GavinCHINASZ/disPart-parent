package com.dispart.parmeterdto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:xiejin
 * @date:Created in 2021/9/3 15:20
 * @description
 * @modified by:
 * @version: 1.0
 */
@Data
public class DISP20210183OutDto {
    //总条数
    private Integer tolPageNum;
    List<DISP20210184CusAccountDto> cusAccountList = new ArrayList<>();
}
