package com.dispart.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

@ApiModel(value="com-dispart-model-order-TDictData")
@Data
@TableName(value = "logistics.t_dict_data")
public class TDictData {
    /**
     * 业务编码
     */
    @TableId(value = "Id", type = IdType.INPUT)
    @ApiModelProperty(value="业务编码")
    private String id;

    /**
     * 名称
     */
    @TableField(value = "item_name")
    @ApiModelProperty(value="名称")
    private String itemName;

    /**
     * 值
     */
    @TableField(value = "item_val")
    @ApiModelProperty(value="值")
    private String itemVal;

    /**
     * 列中文名称
     */
    @TableField(value = "col_name")
    @ApiModelProperty(value="列中文名称")
    private String colName;

    /**
     * 列中文
     */
    @TableField(value = "col_name_cn")
    @ApiModelProperty(value="列中文")
    private String colNameCn;

    /**
     * 类型
     */
    @TableField(value = "item_type")
    @ApiModelProperty(value="类型")
    private String itemType;

    /**
     * 注释
     */
    @TableField(value = "item_desc")
    @ApiModelProperty(value="注释")
    private String itemDesc;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "last_upd_time")
    @ApiModelProperty(value="修改时间")
    private Date lastUpdTime;

    @TableField(value = "spare")
    @ApiModelProperty(value="")
    private String spare;

    /**
     * 机构号
     */
    @TableField(value = "uorg_code")
    @ApiModelProperty(value="机构号")
    private String uorgCode;

    /**
     * 操作人
     */
    @TableField(value = "operator")
    @ApiModelProperty(value="操作人")
    private String operator;

    /**
     * 删除标识 0：未删除 1：已删除
     */
    @TableField(value = "delete_flag")
    @ApiModelProperty(value="删除标识 0：未删除 1：已删除")
    private String deleteFlag;

    public static final String COL_ID = "Id";

    public static final String COL_ITEM_NAME = "item_name";

    public static final String COL_ITEM_VAL = "item_val";

    public static final String COL_COL_NAME = "col_name";

    public static final String COL_COL_NAME_CN = "col_name_cn";

    public static final String COL_ITEM_TYPE = "item_type";

    public static final String COL_ITEM_DESC = "item_desc";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_LAST_UPD_TIME = "last_upd_time";

    public static final String COL_SPARE = "spare";

    public static final String COL_UORG_CODE = "uorg_code";

    public static final String COL_OPERATOR = "operator";

    public static final String COL_DELETE_FLAG = "delete_flag";
}