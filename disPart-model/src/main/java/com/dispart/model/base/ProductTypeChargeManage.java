package com.dispart.model.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 品种进出场收费标准
 */
@ApiModel(value = "com-dispart-model-base-ProductTypeChargeManage")
@Data
@TableName(value = "t_product_type_info")
public class ProductTypeChargeManage {

    /**
     * 判断逻辑
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer judeLogic=0;
    /**
     * 按照查询等级来查询品种
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer searchLevel;
    /**
     * 品种编号
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "品种编号")
    @TableField(value = "VARIETY_NO")
    private String varietyNo;

    /**
     *品种名称
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "品种名称")
    @TableField(value = "PRDCT_NM")
    private String prdctNm;

    /**
     *父品种编号
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "父品种编号")
    @TableField(value = "PARENT_PRDT_ID")
    private String parentPrdtId;
    /**
     *父品种名称
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "父品种名称")
    @TableField(value = "PARENT_PRDT")
    private String parentPrdt;
    /**
     *品种状态
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "品种状态")
    @TableField(value = "PRDCT_ST")
    private String prdctSt;
    /**
     *等级
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "等级")
    @TableField(value = "LEVEL")
    private String level;
    /**
     *品种简称
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "品种简称")
    @TableField(value = "PRDCT_ABBR")
    private String prdctAbbr;
    /**
     *品种费率
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "品种费率")
    @TableField(value = "RATE")
    private Double rate;
    /**
     *品种价格
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "品种价格")
    @TableField(value = "PRICE")
    private Double price;
    /**
     *备注
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "备注")
    @TableField(value = "REMARK")
    private String remark;
    /**
     *操作员
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "部门Id")
    @TableField(value = "DEP_ID")
    private String depId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "机构Id")
    @TableField(value = "SUB_ORG")
    private String subOrg;
    @ApiModelProperty(value = "操作员")
    @TableField(value = "OPER_ID")
    private String operId;
    /**
     *创建时间
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREAT_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatTime;
    /**
     *更新时间
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "更新时间")
    @TableField(value = "UP_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date upTime;
    private List<ProductTypeChargeManage> productTypeChargeManageList=new ArrayList<>();
}
