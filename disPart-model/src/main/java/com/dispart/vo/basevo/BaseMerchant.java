package com.dispart.vo.basevo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@ApiModel(value="com-dispart-model-base-BaseMerchant")
@Data
@TableName(value = "base_merchant")
public class BaseMerchant {
    /**
     * 助记码
     */
    @TableId(value = "merchantcode", type = IdType.INPUT)
    @ApiModelProperty(value="助记码")
    private String merchantcode;

    /**
     * 公司属性
     */
    @TableField(value = "companytype")
    @ApiModelProperty(value="公司属性")
    private String companytype;

    /**
     * 客户类型
     */
    @TableField(value = "merchanttype")
    @ApiModelProperty(value="客户类型")
    private String merchanttype;

    /**
     * 行业类型
     */
    @TableField(value = "idtcode")
    @ApiModelProperty(value="行业类型")
    private String idtcode;

    /**
     * 英文名称
     */
    @TableField(value = "ename")
    @ApiModelProperty(value="英文名称")
    private String ename;

    /**
     * 中文名称
     */
    @TableField(value = "cname")
    @ApiModelProperty(value="中文名称")
    private String cname;

    /**
     * 中文简称
     */
    @TableField(value = "rname")
    @ApiModelProperty(value="中文简称")
    private String rname;

    /**
     * 税务登记号
     */
    @TableField(value = "taxno")
    @ApiModelProperty(value="税务登记号")
    private String taxno;

    /**
     * 开户银行
     */
    @TableField(value = "bankno")
    @ApiModelProperty(value="开户银行")
    private String bankno;

    /**
     * 银行账号
     */
    @TableField(value = "bankacct")
    @ApiModelProperty(value="银行账号")
    private String bankacct;

    /**
     * 地区编码
     */
    @TableField(value = "areacode")
    @ApiModelProperty(value="地区编码")
    private String areacode;

    /**
     * 电话
     */
    @TableField(value = "telno")
    @ApiModelProperty(value="电话")
    private String telno;

    /**
     * 传真
     */
    @TableField(value = "faxno")
    @ApiModelProperty(value="传真")
    private String faxno;

    /**
     * 邮编
     */
    @TableField(value = "zipno")
    @ApiModelProperty(value="邮编")
    private String zipno;

    /**
     * 法人
     */
    @TableField(value = "ceo")
    @ApiModelProperty(value="法人")
    private String ceo;

    /**
     * 联系人
     */
    @TableField(value = "contacts")
    @ApiModelProperty(value="联系人")
    private String contacts;

    /**
     * 户名全称
     */
    @TableField(value = "settleaccno")
    @ApiModelProperty(value="户名全称")
    private String settleaccno;

    /**
     * 状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态")
    private String status;

    /**
     * 级次
     */
    @TableField(value = "levnum")
    @ApiModelProperty(value="级次")
    private String levnum;

    /**
     * 上级客户
     */
    @TableField(value = "merchantcode_f")
    @ApiModelProperty(value="上级客户")
    private String merchantcodeF;

    /**
     * 是否合并
     */
    @TableField(value = "sumflg")
    @ApiModelProperty(value="是否合并")
    private String sumflg;

    /**
     * 是否门店
     */
    @TableField(value = "isstore")
    @ApiModelProperty(value="是否门店")
    private String isstore;

    /**
     * 备注
     */
    @TableField(value = "memo")
    @ApiModelProperty(value="备注")
    private String memo;

    /**
     * 建立日期
     */
    @TableField(value = "entdate")
    @ApiModelProperty(value="建立日期")
    private Date entdate;

    /**
     * 修改日期
     */
    @TableField(value = "upddate")
    @ApiModelProperty(value="修改日期")
    private Date upddate;

    /**
     * 操作员
     */
    @TableField(value = "operator")
    @ApiModelProperty(value="操作员")
    private String operator;

    /**
     * 审核员
     */
    @TableField(value = "auditing")
    @ApiModelProperty(value="审核员")
    private String auditing;

    /**
     * 所属机构
     */
    @TableField(value = "orgcode")
    @ApiModelProperty(value="所属机构")
    private String orgcode;

    /**
     * 客户简称
     */
    @TableField(value = "abbreviation")
    @ApiModelProperty(value="客户简称")
    private String abbreviation;

    /**
     * 清算银行网点名称
     */
    @TableField(value = "settlebankno")
    @ApiModelProperty(value="清算银行网点名称")
    private String settlebankno;

    /**
     * 清算银行账户
     */
    @TableField(value = "settlebankacct")
    @ApiModelProperty(value="清算银行账户")
    private String settlebankacct;

    /**
     * 风控模板号
     */
    @TableField(value = "riskctrlid")
    @ApiModelProperty(value="风控模板号")
    private Integer riskctrlid;

    /**
     * 法人电话
     */
    @TableField(value = "ceotelno")
    @ApiModelProperty(value="法人电话")
    private String ceotelno;

    /**
     * 联系人电话
     */
    @TableField(value = "contactstelno")
    @ApiModelProperty(value="联系人电话")
    private String contactstelno;

    /**
     * 入网日期
     */
    @TableField(value = "opendate")
    @ApiModelProperty(value="入网日期")
    private Date opendate;

    /**
     * 法人身份证
     */
    @TableField(value = "ceoid")
    @ApiModelProperty(value="法人身份证")
    private String ceoid;

    /**
     * 清算网点号
     */
    @TableField(value = "banknetname")
    @ApiModelProperty(value="清算网点号")
    private String banknetname;

    /**
     * 对应客户管理卡号
     */
    @TableField(value = "cardcode")
    @ApiModelProperty(value="对应客户管理卡号")
    private String cardcode;

    /**
     * 身份证号
     */
    @TableField(value = "idcard")
    @ApiModelProperty(value="身份证号")
    private String idcard;

    /**
     * 主客户号
     */
    @TableField(value = "merchantcode_m")
    @ApiModelProperty(value="主客户号")
    private String merchantcodeM;

    /**
     * 是否实名
     */
    @TableField(value = "isrealname")
    @ApiModelProperty(value="是否实名")
    private String isrealname;

    /**
     * 户主身份地址
     */
    @TableField(value = "ceoaddress")
    @ApiModelProperty(value="户主身份地址")
    private String ceoaddress;

    /**
     * 代办人身份证地址
     */
    @TableField(value = "contactaddress")
    @ApiModelProperty(value="代办人身份证地址")
    private String contactaddress;

    /**
     * 身份证正面照
     */
    @TableField(value = "positive_phone")
    @ApiModelProperty(value="身份证正面照")
    private String positivePhone;

    /**
     * 身份证反面照
     */
    @TableField(value = "opposite_phone")
    @ApiModelProperty(value="身份证反面照")
    private String oppositePhone;

    /**
     * 返现额度
     */
    @TableField(value = "reback_amount")
    @ApiModelProperty(value="返现额度")
    private BigDecimal rebackAmount;

    /**
     * 预缴金额
     */
    @TableField(value = "amount")
    @ApiModelProperty(value="预缴金额")
    private BigDecimal amount;

    /**
     * 是否用预付款
     */
    @TableField(value = "is_advance")
    @ApiModelProperty(value="是否用预付款")
    private String isAdvance;

    /**
     * 备用字段1
     */
    @TableField(value = "text1")
    @ApiModelProperty(value="备用字段1")
    private String text1;

    /**
     * 备用字段2
     */
    @TableField(value = "text2")
    @ApiModelProperty(value="备用字段2")
    private String text2;

    /**
     * 销售地点
     */
    @TableField(value = "saleaddress")
    @ApiModelProperty(value="销售地点")
    private String saleaddress;

    /**
     * 法人姓名
     */
    @TableField(value = "legalname")
    @ApiModelProperty(value="法人姓名")
    private String legalname;

    /**
     * 法人电话
     */
    @TableField(value = "legaltel")
    @ApiModelProperty(value="法人电话")
    private String legaltel;

    /**
     * 委托人姓名
     */
    @TableField(value = "entrustname")
    @ApiModelProperty(value="委托人姓名")
    private String entrustname;

    /**
     * 委托人电话
     */
    @TableField(value = "entrusttel")
    @ApiModelProperty(value="委托人电话")
    private String entrusttel;

    /**
     * 委托人身份证号
     */
    @TableField(value = "entrustid")
    @ApiModelProperty(value="委托人身份证号")
    private String entrustid;

    /**
     * 委托人地址
     */
    @TableField(value = "entrustaddress")
    @ApiModelProperty(value="委托人地址")
    private String entrustaddress;

    /**
     * 委托类型
     */
    @TableField(value = "entrusttype")
    @ApiModelProperty(value="委托类型")
    private String entrusttype;

    /**
     * 委托期限
     */
    @TableField(value = "entrustdate")
    @ApiModelProperty(value="委托期限")
    private Date entrustdate;

    /**
     * 经营板块用
     */
    @TableField(value = "business_sector")
    @ApiModelProperty(value="经营板块用")
    private String businessSector;

    /**
     * 助记码
     */
    @TableField(value = "mnemonic_code")
    @ApiModelProperty(value="助记码")
    private String mnemonicCode;

    /**
     * 二维码
     */
    @TableField(value = "qrcode")
    @ApiModelProperty(value="二维码")
    private byte[] qrcode;

    public static final String COL_MERCHANTCODE = "merchantcode";

    public static final String COL_COMPANYTYPE = "companytype";

    public static final String COL_MERCHANTTYPE = "merchanttype";

    public static final String COL_IDTCODE = "idtcode";

    public static final String COL_ENAME = "ename";

    public static final String COL_CNAME = "cname";

    public static final String COL_RNAME = "rname";

    public static final String COL_TAXNO = "taxno";

    public static final String COL_BANKNO = "bankno";

    public static final String COL_BANKACCT = "bankacct";

    public static final String COL_AREACODE = "areacode";

    public static final String COL_TELNO = "telno";

    public static final String COL_FAXNO = "faxno";

    public static final String COL_ZIPNO = "zipno";

    public static final String COL_CEO = "ceo";

    public static final String COL_CONTACTS = "contacts";

    public static final String COL_SETTLEACCNO = "settleaccno";

    public static final String COL_STATUS = "status";

    public static final String COL_LEVNUM = "levnum";

    public static final String COL_MERCHANTCODE_F = "merchantcode_f";

    public static final String COL_SUMFLG = "sumflg";

    public static final String COL_ISSTORE = "isstore";

    public static final String COL_MEMO = "memo";

    public static final String COL_ENTDATE = "entdate";

    public static final String COL_UPDDATE = "upddate";

    public static final String COL_OPERATOR = "operator";

    public static final String COL_AUDITING = "auditing";

    public static final String COL_ORGCODE = "orgcode";

    public static final String COL_ABBREVIATION = "abbreviation";

    public static final String COL_SETTLEBANKNO = "settlebankno";

    public static final String COL_SETTLEBANKACCT = "settlebankacct";

    public static final String COL_RISKCTRLID = "riskctrlid";

    public static final String COL_CEOTELNO = "ceotelno";

    public static final String COL_CONTACTSTELNO = "contactstelno";

    public static final String COL_OPENDATE = "opendate";

    public static final String COL_CEOID = "ceoid";

    public static final String COL_BANKNETNAME = "banknetname";

    public static final String COL_CARDCODE = "cardcode";

    public static final String COL_IDCARD = "idcard";

    public static final String COL_MERCHANTCODE_M = "merchantcode_m";

    public static final String COL_ISREALNAME = "isrealname";

    public static final String COL_CEOADDRESS = "ceoaddress";

    public static final String COL_CONTACTADDRESS = "contactaddress";

    public static final String COL_POSITIVE_PHONE = "positive_phone";

    public static final String COL_OPPOSITE_PHONE = "opposite_phone";

    public static final String COL_REBACK_AMOUNT = "reback_amount";

    public static final String COL_AMOUNT = "amount";

    public static final String COL_IS_ADVANCE = "is_advance";

    public static final String COL_TEXT1 = "text1";

    public static final String COL_TEXT2 = "text2";

    public static final String COL_SALEADDRESS = "saleaddress";

    public static final String COL_LEGALNAME = "legalname";

    public static final String COL_LEGALTEL = "legaltel";

    public static final String COL_ENTRUSTNAME = "entrustname";

    public static final String COL_ENTRUSTTEL = "entrusttel";

    public static final String COL_ENTRUSTID = "entrustid";

    public static final String COL_ENTRUSTADDRESS = "entrustaddress";

    public static final String COL_ENTRUSTTYPE = "entrusttype";

    public static final String COL_ENTRUSTDATE = "entrustdate";

    public static final String COL_BUSINESS_SECTOR = "business_sector";

    public static final String COL_MNEMONIC_CODE = "mnemonic_code";

    public static final String COL_QRCODE = "qrcode";
}