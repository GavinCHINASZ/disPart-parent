package com.dispart.dto.entrance;

import com.dispart.vo.entrance.TCargoInfoReportDetails;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
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
public class D_0226UpInDto {

    /**
     * 来货报备主表ID
     */
    @ApiModelProperty(value="来货报备主表ID")
    private String reportId;
    /**
     * 是否免进场费
     */
    @ApiModelProperty(value="是否免进场费")
    private String isFree;

    /**
     * 审核状态 0-未审核 1-审核通过 2-审核未通过 3-已进场
     */
    @ApiModelProperty(value="审核状态 0-未审核 1-审核通过 2-审核未通过 3-已进场")
    private String auditorSt;

    /**
     * 审核人
     */
    @ApiModelProperty(value="审核人")
    private String auditor;

    /**
     * 审核时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value="审核时间")
    private Date auditorTm;



}
