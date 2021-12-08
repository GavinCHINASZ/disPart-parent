package com.dispart.parmeterdto;

import com.dispart.vo.MumberShipInfoVo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:xiejin
 * @date:Created in 2021/9/3 14:56
 * @description
 * @modified by:
 * @version: 1.0
 */
@Data
public class DISP20210197OutDto {
    //总条数
    private Integer tolPageNum;
    List<MumberShipInfoVo> cusAccountList = new ArrayList<>();
}
