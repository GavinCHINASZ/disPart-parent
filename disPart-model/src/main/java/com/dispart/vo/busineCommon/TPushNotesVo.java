package com.dispart.vo.busineCommon;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dispart.vo.basevo.PageInfo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 个推
 *
 * @author 黄贵川
 * @date 2021/08/28
 * @version 1.0
 */
@Data
@ApiModel(description = "app推送消息通知")
@TableName("t_push_notes")
public class TPushNotesVo extends PageInfo {
    /**
     * 推送消息编号 11
     */
    private Integer pushNo;
    /**
     * 业务单号 20
     */
    private String busineNo;
    /**
     * 用户ID 20
     */
    private String userId;
    /**
     * 客户编号 16
     */
    private String provId;

    /**
     * 要推送的消息内容
     */
    private String pushMsg;
    /**
     * 渠道类型
     */
    private String chanlNo;
    /**
     *  是否已读 0-未读 1-已读
     */
    private String isRead;
    /**
     * 备注
     */
    private String remark;
    /**
     * 已读日期
     */
    private Date readTime;
    /**
     * 创建时间
     */
    private Date creatTime;
    /**
     * 更新时间
     */
    private Date upTime;
    /**
     * 参数类型
     *
     */
    private Integer parameterType;
    /**
     * 参数list
     */
    private List<String> parameterList;

    /**
     * cid
     */
    private String clientId;
    /**
     * 标题
     */
    private String title;
}