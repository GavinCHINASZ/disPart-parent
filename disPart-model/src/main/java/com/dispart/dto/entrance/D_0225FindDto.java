package com.dispart.dto.entrance;

import com.baomidou.mybatisplus.annotation.TableField;
import com.dispart.vo.basevo.PageInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author:王思州
 * @date:Created in 2021/8/07 11:25
 * @description 增加货物信息申请dto
 * @modified by:
 * @version: 1.0
 */
@Data
public class D_0225FindDto extends PageInfo{

    //客户名称
    private String provNm;
    //客户编号
    private String provId;
    //车牌号
    private String vehicleNum;

    //进场地点
    private String inDoor;
    //电话号码
    private String phone;
    //开始日期
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDt;
    //结束日期
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDt;
    //审核状态 （0-未审核 1-审核通过 2-审核未通过 3-已进场）
    private String auditorSt;
    //移动端查询参数
    private String appSelectKey;
}
