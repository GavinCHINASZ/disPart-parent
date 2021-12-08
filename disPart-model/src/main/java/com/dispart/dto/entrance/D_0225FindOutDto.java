package com.dispart.dto.entrance;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author:王思州
 * @date:Created in 2021/8/07 11:25
 * @description 增加货物信息申请dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class D_0225FindOutDto {

    //报备主信息集合
    private List<D_0225FindOutYDto> reportList;
    //总条数
    private Integer tolPageNum;

}
