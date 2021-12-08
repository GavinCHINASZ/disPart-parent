package com.dispart.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
    * 对账结果
    */
@ApiModel(value="com-dispart-model-TReconciliationResult")
@Data
public class TReconciliationResult {
    /**
    * 交易日期
    */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value="交易日期")
    private Date txnDt;

    /**
    * 市场编号
    */
    @ApiModelProperty(value="市场编号")
    private String marketId;

    /**
    * 对账结果, 0-对账一致 1-对账不一致
    */
    @ApiModelProperty(value="对账结果, 0-对账一致 1-对账不一致")
    private String reconRslt;

    /**
    * 备注
    */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date creatTime;

    /**
    * 更新时间
    */
    @ApiModelProperty(value="更新时间")
    private Date upTime;
}