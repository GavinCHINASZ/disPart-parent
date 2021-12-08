package com.dispart.vo.commons;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @author zhongfei
 * @version 1.0.0:
 * @title TCustomInfoManagerVo
 * @Description TODO 客户信息管理
 * @dateTime 2021/8/9 11:14
 * @Copyright 2020-2021
 */
@Data
@ApiModel(description = "客户信息管理")
@TableName("t_custom_info_manager")
public class TCustomManagerInfo {
  //正常
  public  static final String  NORMAL="0";
  //禁用
  public  static final String  DISABLE="1";
  //待审核
  public  static final String  NOAUDIT="3";
  //审核通过
  public  static final String  ISPASS="4";
  //审核未通过
  public  static final String  NOPASS="5";

  private String provId;
  // 税务登记号
  private String taxNum;
  // 客户类型
  private String customTp;
  // 扶贫类型
  private String pvtyTp;
  // 客户名称
  private String provNm;
  // 客户简称
  private String shrtNm;
  // 证件类型
  private String certType;
  //证件号码
  private String certNum;
  // 证件地址
  private String certAddr;
  // 证件有效期
  private Date certPrd;
  private String phone;
  // 法人名称
  private String legalName;
  private String legalPhone;
  //客户状态
  private String status;
  //是否实名
  private String isReal;
  //是否允许提现
  private String isWithdraw;

}
