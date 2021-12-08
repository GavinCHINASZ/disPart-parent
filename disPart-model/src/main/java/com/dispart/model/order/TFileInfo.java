package com.dispart.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

@ApiModel(value = "com-dispart-model-order-TFileInfo")
@Data
@TableName(value = "logistics.t_file_info")
public class TFileInfo {
    /**
     * 文件名
     */
    @TableId(value = "FILE_NAME", type = IdType.INPUT)
    @ApiModelProperty(value = "文件名")
    private String fileName;

    /**
     * 是否已解析
     */
    @TableField(value = "FILE_STATUS")
    @ApiModelProperty(value = "是否已解析")
    private String fileStatus;

    /**
     * 更新时间
     */
    @TableField(value = "UPDATE_TIME")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 日期
     */
    @TableField(value = "TIME")
    @ApiModelProperty(value = "日期")
    private Date time;

    /**
     * 文件字节流
     */
    @TableField(value = "FILE_DATA")
    @ApiModelProperty(value = "文件字节流")
    private byte[] fileData;

    public static final String COL_FILE_NAME = "FILE_NAME";

    public static final String COL_FILE_STATUS = "FILE_STATUS";

    public static final String COL_UPDATE_TIME = "UPDATE_TIME";

    public static final String COL_TIME = "TIME";

    public static final String COL_FILE_DATA = "FILE_DATA";
}