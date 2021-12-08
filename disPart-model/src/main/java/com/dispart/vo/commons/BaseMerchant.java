package com.dispart.vo.commons;

import java.io.Serializable;

/**
 * (BaseMerchant)实体类
 *
 * @author makejava
 * @since 2021-06-13 22:07:19
 */
public class BaseMerchant implements Serializable {
    private static final long serialVersionUID = 399454407368452651L;
    /**
    * 助记码
    */
    private String merchantcode;
    /**
    * 公司属性
    */
    private String companytype;
    /**
    * 客户类型
    */
    private String merchanttype;
    /**
    * 行业类型
    */
    private String idtcode;
    /**
    * 英文名称
    */
    private String ename;
    /**
    * 中文名称
    */
    private String cname;
    /**
    * 中文简称
    */
    private String rname;
    /**
    * 税务登记号
    */
    private String taxno;
    /**
    * 开户银行
    */
    private String bankno;
    /**
    * 银行账号
    */
    private String bankacct;
    /**
    * 地区编码
    */
    private String areacode;
    /**
    * 电话
    */
    private String telno;
    /**
    * 传真
    */
    private String faxno;
    /**
    * 邮编
    */
    private String zipno;
    /**
    * 法人
    */
    private String ceo;
    /**
    * 联系人
    */
    private String contacts;
    /**
    * 户名全称
    */
    private String settleaccno;
    /**
    * 状态
    */
    private String status;
    /**
    * 级次
    */
    private String levnum;
    /**
    * 上级客户
    */
    private String merchantcodeF;
    /**
    * 是否合并
    */
    private String sumflg;
    /**
    * 是否门店
    */
    private String isstore;
    /**
    * 备注
    */
    private String memo;
    /**
    * 建立日期
    */
    private Object entdate;
    /**
    * 修改日期
    */
    private Object upddate;
    /**
    * 操作员
    */
    private String operator;
    /**
    * 审核员
    */
    private String auditing;
    /**
    * 所属机构
    */
    private String orgcode;
    /**
    * 客户简称
    */
    private String abbreviation;
    /**
    * 清算银行网点名称
    */
    private String settlebankno;
    /**
    * 清算银行账户
    */
    private String settlebankacct;
    /**
    * 风控模板号
    */
    private Integer riskctrlid;
    /**
    * 法人电话
    */
    private String ceotelno;
    /**
    * 联系人电话
    */
    private String contactstelno;
    /**
    * 入网日期
    */
    private Object opendate;
    /**
    * 法人身份证
    */
    private String ceoid;
    /**
    * 清算网点号
    */
    private String banknetname;
    /**
    * 对应客户管理卡号
    */
    private String cardcode;
    /**
    * 身份证号
    */
    private String idcard;
    /**
    * 主客户号
    */
    private String merchantcodeM;
    /**
    * 是否实名
    */
    private String isrealname;
    /**
    * 户主身份地址
    */
    private String ceoaddress;
    /**
    * 代办人身份证地址
    */
    private String contactaddress;
    /**
    * 身份证正面照
    */
    private String positivePhone;
    /**
    * 身份证反面照
    */
    private String oppositePhone;
    /**
    * 返现额度
    */
    private Double rebackAmount;
    /**
    * 预缴金额
    */
    private Double amount;
    /**
    * 是否用预付款
    */
    private String isAdvance;
    /**
    * 备用字段1
    */
    private String text1;
    /**
    * 备用字段2
    */
    private String text2;
    /**
    * 销售地点
    */
    private String saleaddress;
    /**
    * 法人姓名
    */
    private String legalname;
    /**
    * 法人电话
    */
    private String legaltel;
    /**
    * 委托人姓名
    */
    private String entrustname;
    /**
    * 委托人电话
    */
    private String entrusttel;
    /**
    * 委托人身份证号
    */
    private String entrustid;
    /**
    * 委托人地址
    */
    private String entrustaddress;
    /**
    * 委托类型
    */
    private String entrusttype;
    /**
    * 委托期限
    */
    private Object entrustdate;
    /**
    * 经营板块用
    */
    private String businessSector;
    /**
    * 助记码
    */
    private String mnemonicCode;
    /**
    * 二维码
    */
    private Object qrcode;


    public String getMerchantcode() {
        return merchantcode;
    }

    public void setMerchantcode(String merchantcode) {
        this.merchantcode = merchantcode;
    }

    public String getCompanytype() {
        return companytype;
    }

    public void setCompanytype(String companytype) {
        this.companytype = companytype;
    }

    public String getMerchanttype() {
        return merchanttype;
    }

    public void setMerchanttype(String merchanttype) {
        this.merchanttype = merchanttype;
    }

    public String getIdtcode() {
        return idtcode;
    }

    public void setIdtcode(String idtcode) {
        this.idtcode = idtcode;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getTaxno() {
        return taxno;
    }

    public void setTaxno(String taxno) {
        this.taxno = taxno;
    }

    public String getBankno() {
        return bankno;
    }

    public void setBankno(String bankno) {
        this.bankno = bankno;
    }

    public String getBankacct() {
        return bankacct;
    }

    public void setBankacct(String bankacct) {
        this.bankacct = bankacct;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public String getFaxno() {
        return faxno;
    }

    public void setFaxno(String faxno) {
        this.faxno = faxno;
    }

    public String getZipno() {
        return zipno;
    }

    public void setZipno(String zipno) {
        this.zipno = zipno;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getSettleaccno() {
        return settleaccno;
    }

    public void setSettleaccno(String settleaccno) {
        this.settleaccno = settleaccno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLevnum() {
        return levnum;
    }

    public void setLevnum(String levnum) {
        this.levnum = levnum;
    }

    public String getMerchantcodeF() {
        return merchantcodeF;
    }

    public void setMerchantcodeF(String merchantcodeF) {
        this.merchantcodeF = merchantcodeF;
    }

    public String getSumflg() {
        return sumflg;
    }

    public void setSumflg(String sumflg) {
        this.sumflg = sumflg;
    }

    public String getIsstore() {
        return isstore;
    }

    public void setIsstore(String isstore) {
        this.isstore = isstore;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Object getEntdate() {
        return entdate;
    }

    public void setEntdate(Object entdate) {
        this.entdate = entdate;
    }

    public Object getUpddate() {
        return upddate;
    }

    public void setUpddate(Object upddate) {
        this.upddate = upddate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getAuditing() {
        return auditing;
    }

    public void setAuditing(String auditing) {
        this.auditing = auditing;
    }

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getSettlebankno() {
        return settlebankno;
    }

    public void setSettlebankno(String settlebankno) {
        this.settlebankno = settlebankno;
    }

    public String getSettlebankacct() {
        return settlebankacct;
    }

    public void setSettlebankacct(String settlebankacct) {
        this.settlebankacct = settlebankacct;
    }

    public Integer getRiskctrlid() {
        return riskctrlid;
    }

    public void setRiskctrlid(Integer riskctrlid) {
        this.riskctrlid = riskctrlid;
    }

    public String getCeotelno() {
        return ceotelno;
    }

    public void setCeotelno(String ceotelno) {
        this.ceotelno = ceotelno;
    }

    public String getContactstelno() {
        return contactstelno;
    }

    public void setContactstelno(String contactstelno) {
        this.contactstelno = contactstelno;
    }

    public Object getOpendate() {
        return opendate;
    }

    public void setOpendate(Object opendate) {
        this.opendate = opendate;
    }

    public String getCeoid() {
        return ceoid;
    }

    public void setCeoid(String ceoid) {
        this.ceoid = ceoid;
    }

    public String getBanknetname() {
        return banknetname;
    }

    public void setBanknetname(String banknetname) {
        this.banknetname = banknetname;
    }

    public String getCardcode() {
        return cardcode;
    }

    public void setCardcode(String cardcode) {
        this.cardcode = cardcode;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getMerchantcodeM() {
        return merchantcodeM;
    }

    public void setMerchantcodeM(String merchantcodeM) {
        this.merchantcodeM = merchantcodeM;
    }

    public String getIsrealname() {
        return isrealname;
    }

    public void setIsrealname(String isrealname) {
        this.isrealname = isrealname;
    }

    public String getCeoaddress() {
        return ceoaddress;
    }

    public void setCeoaddress(String ceoaddress) {
        this.ceoaddress = ceoaddress;
    }

    public String getContactaddress() {
        return contactaddress;
    }

    public void setContactaddress(String contactaddress) {
        this.contactaddress = contactaddress;
    }

    public String getPositivePhone() {
        return positivePhone;
    }

    public void setPositivePhone(String positivePhone) {
        this.positivePhone = positivePhone;
    }

    public String getOppositePhone() {
        return oppositePhone;
    }

    public void setOppositePhone(String oppositePhone) {
        this.oppositePhone = oppositePhone;
    }

    public Double getRebackAmount() {
        return rebackAmount;
    }

    public void setRebackAmount(Double rebackAmount) {
        this.rebackAmount = rebackAmount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getIsAdvance() {
        return isAdvance;
    }

    public void setIsAdvance(String isAdvance) {
        this.isAdvance = isAdvance;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getSaleaddress() {
        return saleaddress;
    }

    public void setSaleaddress(String saleaddress) {
        this.saleaddress = saleaddress;
    }

    public String getLegalname() {
        return legalname;
    }

    public void setLegalname(String legalname) {
        this.legalname = legalname;
    }

    public String getLegaltel() {
        return legaltel;
    }

    public void setLegaltel(String legaltel) {
        this.legaltel = legaltel;
    }

    public String getEntrustname() {
        return entrustname;
    }

    public void setEntrustname(String entrustname) {
        this.entrustname = entrustname;
    }

    public String getEntrusttel() {
        return entrusttel;
    }

    public void setEntrusttel(String entrusttel) {
        this.entrusttel = entrusttel;
    }

    public String getEntrustid() {
        return entrustid;
    }

    public void setEntrustid(String entrustid) {
        this.entrustid = entrustid;
    }

    public String getEntrustaddress() {
        return entrustaddress;
    }

    public void setEntrustaddress(String entrustaddress) {
        this.entrustaddress = entrustaddress;
    }

    public String getEntrusttype() {
        return entrusttype;
    }

    public void setEntrusttype(String entrusttype) {
        this.entrusttype = entrusttype;
    }

    public Object getEntrustdate() {
        return entrustdate;
    }

    public void setEntrustdate(Object entrustdate) {
        this.entrustdate = entrustdate;
    }

    public String getBusinessSector() {
        return businessSector;
    }

    public void setBusinessSector(String businessSector) {
        this.businessSector = businessSector;
    }

    public String getMnemonicCode() {
        return mnemonicCode;
    }

    public void setMnemonicCode(String mnemonicCode) {
        this.mnemonicCode = mnemonicCode;
    }

    public Object getQrcode() {
        return qrcode;
    }

    public void setQrcode(Object qrcode) {
        this.qrcode = qrcode;
    }

}