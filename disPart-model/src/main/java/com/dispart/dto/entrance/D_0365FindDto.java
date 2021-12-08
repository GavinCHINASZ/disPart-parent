package com.dispart.dto.entrance;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import com.dispart.vo.basevo.PageInfo;
import java.util.Date;

/**
 * 进出场管理-收费员收费统计
 */
@Data
public class D_0365FindDto extends PageInfo {
    /**
     * 操作类型 0-进场 1-出场
     */
    @ApiModelProperty(value="操作类型")
    private String operateTp;

    /**
     * 收费员
     */
    @ApiModelProperty(value="收费员")
    private String operator;

    /**
     * 查开始询时间
     */
    @ApiModelProperty(value="查询开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:sss")
    private Date timeStart;

    /**
     * 查询结束时间
     */
    @ApiModelProperty(value="查询结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:sss")
    private Date timeEnd;


}
