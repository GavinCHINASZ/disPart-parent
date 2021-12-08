package com.dispart.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhaoshihao
 * @version 1.0.0
 * @title ImagesInfo
 * @creat 2021/6/11 15:22
 * @Copyright 2020-2021
 */
@Data
@ApiModel(description = "广告信息表")
@TableName("t_images_info")
public class ImagesInfo implements Serializable {

    @ApiModelProperty(value = "图片ID")
    @TableField("image_id")
    private String imageId;

    @ApiModelProperty(value = "归属")
    @TableField("belong")
    private String belong;

    @ApiModelProperty(value = "图片名称")
    @TableField("param_type")
    private String imageNm;

    @ApiModelProperty(value = "图片描述")
    @TableField("image_desc")
    private String imageDesc;

    @ApiModelProperty(value = "图片地址")
    @TableField("image_url")
    private String imageUrl;

    @ApiModelProperty(value = "超链接地址")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "优先级")
    @TableField("priority")
    private String priority;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "时间戳")
    @TableField("update_dt")
    private Date updateDt;

    private static final long serialVersionUID = 1L;
}