package com.dispart.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

@ApiModel(value="com-dispart-model-order-File")
@Data
@TableName(value = "logistics.`file`")
public class File {
    /**
     * 文件名
     */
    @TableId(value = "FILE_NAME", type = IdType.INPUT)
    @ApiModelProperty(value="文件名")
    private String fileName;

    /**
     * 文件绝对路径
     */
    @TableField(value = "FILE_PATH")
    @ApiModelProperty(value="文件绝对路径")
    private String filePath;

    /**
     * 是否已解析
     */
    @TableField(value = "FILE_STATUS")
    @ApiModelProperty(value="是否已解析")
    private String fileStatus;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "UPDATE_TIME")
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    public static final String COL_FILE_NAME = "FILE_NAME";

    public static final String COL_FILE_PATH = "FILE_PATH";

    public static final String COL_FILE_STATUS = "FILE_STATUS";

    public static final String COL_CREATE_TIME = "CREATE_TIME";

    public static final String COL_UPDATE_TIME = "UPDATE_TIME";
}