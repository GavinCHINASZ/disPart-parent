package com.dispart.vo.entrance;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
/**
 * @author zhongfei
 * @version 1.0.0:
 * @title TCardManagerVo
 * @Description TODO 卡片管理
 * @dateTime 2021/8/9 11:14
 * @Copyright 2020-2021
 */
@Data
@ApiModel(description = "卡片管理")
@TableName("t_card_manager")
public class TCardManagerVo {

  private String documentNum;
  private String cardTp;
  private String deputy;
  private String startCard;
  private String endCard;
  private long num;
  private String status;
  private Date sendTm;
  private String sendNo;
  private Date entryTm;
  private String remark;
  private String operId;
  private Date creatTime;
  private Date upTime;


}
