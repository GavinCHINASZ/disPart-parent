package com.dispart.parmeterdto;

import com.dispart.vo.AccnoInfoDetailVo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:xiejin
 * @date:Created in 2021/9/3 15:27
 * @description
 * @modified by:
 * @version: 1.0
 */
@Data
public class DISP20210188OutDto {
    //总条数
    private Integer tolPageNum;
    List<AccnoInfoDetailVo> cusAccountInfoDetailList = new ArrayList<>();
}
