package com.dispart.vo.entrance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "com-dispart-vo-entrance-TVehicleBlacklist")
@Data
public class TVehicleBlacklist {
    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    private String vehicelNum;

    /**
     * 类型 0-黑名单 1-白名单
     */
    @ApiModelProperty(value = "类型 0-黑名单 1-白名单")
    private String type;

    /**
     * 创建人ID
     */
    @ApiModelProperty(value = "创建人ID")
    private String operId;

    /**
     * 修改人ID
     */
    @ApiModelProperty(value = "修改人ID")
    private String modifyId;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 所属机构
     */
    @ApiModelProperty(value = "所属机构")
    private String subOrg;

    /**
     * 部门编号
     */
    @ApiModelProperty(value = "部门编号")
    private String depId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date creatTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date upTime;
}
