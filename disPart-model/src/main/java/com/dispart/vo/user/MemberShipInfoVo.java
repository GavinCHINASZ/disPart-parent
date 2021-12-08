package com.dispart.vo.user;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员卡信息
 *
 * @author 黄贵川
 * @version 1.0.0:
 * @date 2021/09/02
 */
@Data
@ApiModel(description = "会员卡信息")
@TableName("t_membership_info")
public class MemberShipInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 密码
     */
    private String passwd;
    /**
     * 账户号
     */
    private String account;
    /**
     *
     */
    private String provId;
    /**
     * 卡类型
     */
    private String cardTp;
    private String phone;
    /**
     * 卡状态 0-正常 1-冻结 6-挂失 9-销户
     */
    private String status;
    /**
     * 押金标志 00-无押金 10-有押金 11-有押金可消费
     */
    private String cashIndent;
    /**
     * 押金金额 小数点2位
     */
    private BigDecimal cashPledge;
    private String remark;
    private String operId;

    /**
     * 创建时间
     */
    private Date creatTime;

    /**
     * 更新时间
     */
    private Date upTime;

    /**
     * 渠道号
     */
    private String chanlNo;
}
