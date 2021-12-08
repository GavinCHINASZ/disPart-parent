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

@ApiModel(value="com-dispart-model-base-TOrganizationInfo")
@Data
@TableName(value = "t_organization_info")
public class OrganizationVo {
    private static final long serialVersionUID = 1L;
    /**
     * 机构ID
     */
    @TableId(value = "ORG_ID", type = IdType.INPUT)
    @ApiModelProperty(value="机构ID")
    private String orgId;

    /**
     * 机构类别
     */
    @TableField(value = "ORG_TYPE")
    @ApiModelProperty(value="机构类别")
    private String orgType;

    /**
     * 机构名称
     */
    @TableField(value = "ORG_NM")
    @ApiModelProperty(value="机构名称")
    private String orgNm;

    /**
     * 机构简称
     */
    @TableField(value = "ORG_SHRT_NM")
    @ApiModelProperty(value="机构简称")
    private String orgShrtNm;

    /**
     * 税务登记号
     */
    @TableField(value = "TAX_NUM")
    @ApiModelProperty(value="税务登记号")
    private String taxNum;

    /**
     * 账号
     */
    @TableField(value = "ACCOUNT")
    @ApiModelProperty(value="账号")
    private String account;

    /**
     * 地区代码
     */
    @TableField(value = "AREA_CD")
    @ApiModelProperty(value="地区代码")
    private String areaCd;

    /**
     * 机构地址
     */
    @TableField(value = "ORG_ADDR")
    @ApiModelProperty(value="机构地址")
    private String orgAddr;

    /**
     * 电话
     */
    @TableField(value = "TELEPHONE")
    @ApiModelProperty(value="电话")
    private String telephone;

    /**
     * 传真
     */
    @TableField(value = "FAX_NO")
    @ApiModelProperty(value="传真")
    private String faxNo;

    /**
     * 邮编
     */
    @TableField(value = "POSTCODE")
    @ApiModelProperty(value="邮编")
    private String postcode;

    /**
     * 法人
     */
    @TableField(value = "LEGAL_PERSON")
    @ApiModelProperty(value="法人")
    private String legalPerson;

    /**
     * 联系人
     */
    @TableField(value = "CONTACTS")
    @ApiModelProperty(value="联系人")
    private String contacts;

    /**
     * 机构状态
     */
    @TableField(value = "ORG_ST")
    @ApiModelProperty(value="机构状态")
    private String orgSt;

    /**
     * 开户日期
     */
    @TableField(value = "OPEN_DT")
    @ApiModelProperty(value="开户日期")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date openDt;

    /**
     * 创建日期
     */
    @TableField(value = "CREAT_DT")
    @ApiModelProperty(value="创建日期")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatDt;

    /**
     * 操作员
     */
    @TableField(value = "OPER_ID")
    @ApiModelProperty(value="操作员")
    private String operId;

    /**
     * 审核员
     */
    @TableField(value = "AUDITOR")
    @ApiModelProperty(value="审核员")
    private String auditor;

    /**
     * 备注
     */
    @TableField(value = "REMARK")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 机构级别
     */
    @TableField(value = "ORG_LEVEL")
    @ApiModelProperty(value="机构级别")
    private String orgLevel;

    /**
     * 上级机构
     */
    @TableField(value = "PARENT_ORG_ID")
    @ApiModelProperty(value="上级机构")
    private String parentOrgId;

    /**
     * 时间戳
     */
    @TableField(value = "UPDATE_DT")
    @ApiModelProperty(value="时间戳")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDt;

    public static final String COL_ORG_ID = "ORG_ID";

    public static final String COL_ORG_TYPE = "ORG_TYPE";

    public static final String COL_ORG_NM = "ORG_NM";

    public static final String COL_ORG_SHRT_NM = "ORG_SHRT_NM";

    public static final String COL_TAX_NUM = "TAX_NUM";

    public static final String COL_ACCOUNT = "ACCOUNT";

    public static final String COL_AREA_CD = "AREA_CD";

    public static final String COL_ORG_ADDR = "ORG_ADDR";

    public static final String COL_TELEPHONE = "TELEPHONE";

    public static final String COL_FAX_NO = "FAX_NO";

    public static final String COL_POSTCODE = "POSTCODE";

    public static final String COL_LEGAL_PERSON = "LEGAL_PERSON";

    public static final String COL_CONTACTS = "CONTACTS";

    public static final String COL_ORG_ST = "ORG_ST";

    public static final String COL_OPEN_DT = "OPEN_DT";

    public static final String COL_CREAT_DT = "CREAT_DT";

    public static final String COL_OPER_ID = "OPER_ID";

    public static final String COL_AUDITOR = "AUDITOR";

    public static final String COL_REMARK = "REMARK";

    public static final String COL_ORG_LEVEL = "ORG_LEVEL";

    public static final String COL_PARENT_ORG_ID = "PARENT_ORG_ID";

    public static final String COL_UPDATE_DT = "UPDATE_DT";
}