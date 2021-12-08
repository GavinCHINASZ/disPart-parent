package com.dispart.vo.basevo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@ApiModel(value="com-dispart-model-base-TMenuInfo")
@Data
@TableName(value = "t_menu_info")
public class MenuVo {
    private static final long serialVersionUID = 1L;


    /**
     * 菜单ID
     */
    @TableId(value = "MENU_Id", type = IdType.INPUT)
    @ApiModelProperty(value="菜单ID")
    private String menuId;


    @TableId(value = "CHANLNOTYPE")
    @ApiModelProperty(value="菜单渠道类型")
    private String  chanlNoType;

    /**
     * 上级菜单ID
     */
    @TableField(value = "PARENT_MENU_ID")
    @ApiModelProperty(value="上级菜单ID")
    private String parentMenuId;

    /**
     * 图标ID
     */
    @TableField(value = "ICON_ID")
    @ApiModelProperty(value="图标ID")
    private String iconId;

    /**
     * 菜单名称
     */
    @TableField(value = "MENU_NM")
    @ApiModelProperty(value="菜单名称")
    private String menuNm;

    /**
     * 菜单类型
     */
    @TableField(value = "MENU_TYPE")
    @ApiModelProperty(value="菜单类型")
    private String menuType;

    /**
     * 备注
     */
    @TableField(value = "REMARK")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 更新时间戳
     */
    @TableField(value = "UPDATE_DT")
    @ApiModelProperty(value="更新时间戳")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDt;

    /**
     * 菜单URL
     */
    @TableField(value = "MENU_URL")
    @ApiModelProperty(value="菜单URL")
    private String menuUrl;

    public static final String COL_MENU_DI = "MENU_DI";

    public static final String COL_PARENT_MENU_ID = "PARENT_MENU_ID";

    public static final String COL_ICON_ID = "ICON_ID";

    public static final String COL_MENU_NM = "MENU_NM";

    public static final String COL_MENU_TYPE = "MENU_TYPE";

    public static final String COL_REMARK = "REMARK";

    public static final String COL_UPDATE_DT = "UPDATE_DT";

    public static final String COL_MENU_URL = "MENU_URL";

}