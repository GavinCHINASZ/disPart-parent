package com.dispart.vo.entrance;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dispart.vo.basevo.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * TSubsidInfoVo 补贴申请
 *
 * @author 黄贵川
 * @version 1.0.0:
 * @date 2021/8/23
 */
@Data
@ApiModel(description = "补贴申请")
@TableName("t_subsid_info")
public class TSubsidInfo extends PageInfo {

    // 进出场ID
    private String inId;
    // 客户编号
    private String provId;
    // 用户ID
    private String userId;
    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    private String vehicleNum;

    // 补贴重量 小数点2位
    private BigDecimal subsidWght;
    // 补贴单价 小数点2位
    private BigDecimal subsidPrice;
    // 补贴总额 小数点2位
    private BigDecimal subsidTtlAmt;

    // 领取人姓名
    private String rcvName;
    // 领取人电话
    private String rcvPhone;
    // 领取人姓名+领取人电话
    private String nameAndPhone;
    // 补贴货物类别
    private String prdctType;

    // 补贴方式 0-现金 1-一卡通
    private String subsidTp;
    // 补贴一卡通卡号
    private String cardNo;
    // 状态 0-申请中 1-已发放 2-已撤回 3-待撤回  4--作废 5--撤回申请中
    private String status;
    private String[] statusArr;

    // 备注
    private String remark;
    // 原因，交易失败原因
    private String cause;
    // 申请人
    private String proposer;
    // 审核人
    private String auditor;

    // 操作员
    private String operId;
    private String operName;

    // 0货车(供应商) 1空车(采购商)
    private String carType;

    /**
     * 补贴流水号
     */
    private String jrnlNum;

    private Date creatTime;
    private Date upTime;

}
