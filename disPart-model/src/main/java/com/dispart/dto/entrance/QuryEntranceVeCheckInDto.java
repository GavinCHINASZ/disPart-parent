package com.dispart.dto.entrance;


import lombok.Data;

import java.util.List;
/**
 * 进场货物核验-查询货物信息
 */
@Data
public class QuryEntranceVeCheckInDto {
    /**
     * 进出场ID YYMMDD + 8位序列号
     */
    private String inId;

    /**
     * 车牌号
     */
    private String vehicleNum;

    /**
     * 客户编号
     */
    private String provId;

    /**
     * 客户卡号
     */
    private String cardNo;

    /**
     * 客户名称
     */
    private String provNm;

    /**
     * 手机号码
     */
    private String phone;




    /**
     * 进场地点
     */
    private String inDoor;


    /**
     * 进场操作人
     */
    private String inOperId;

    /**
     * 进出场类型 0-进场 1-出场 2-完部
     */
    private String inOutTp;

    /**
     * 用户Id或时间
     */
    private String value;

    /**
     * 操作人
     */
    private String operId;

    /**
     *出场地点
     */
    private String outDoor;

    /**
     * 是否核验
     */
    private String isCheck;

    private String startDate;

    private String endDate;

    //分页页数
    private Integer pageSize;
    //分页条数
    private Integer pageNum;

    //开始记录数
    private Integer strNum;
    //渠道号
    private String chanlNo;

    //进出场时间
    private String inOutTime;

}
