package com.dispart.dto.dataquery;

import com.dispart.baseDto.BaseSelectionInDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
public class Disp20210349InDto extends BaseSelectionInDto {

    @ApiModelProperty(value="客户姓名")
    private String provNm;

    @ApiModelProperty(value="卡号")
    private String cardNo;

    @ApiModelProperty(value="状态 0-待充值 9-充值成功")
    private String status;

    @ApiModelProperty(value = "出场时间查询开始参数")
    private String outStTime;

    @ApiModelProperty(value = "出场时间查询开始参数")
    private String outEndTime;

    @ApiModelProperty(value = "出场操作人名称")
    private String operNm;

    @ApiModelProperty(value = "车牌号")
    private String vehicleNum;


}
