package com.dispart.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 网银nacos需要的配置信息
 */
@Data
@Component
public class BankNacosConfig {

    //签约账户号
    @Value("${bank.accno}")
    private String accno;

    //签约账户名称
    @Value("${bank.accnoName}")
    private String accnoName;

    //代发代扣编码
    @Value("${bank.billCode}")
    private String billCode;

    //用途编码
    @Value("${bank.useOfCode}")
    private String useOfCode;

    //客户号
    @Value("${bank.custId}")
    private String custId;

    //操作员号
    @Value("${bank.userId}")
    private String userId;

    //密码
    @Value("${bank.password}")
    private String password;

    //语言
    @Value("${bank.language}")
    private String language;

    //前置机地址
    @Value("${bank.server.ip}")
    private String ip;

    //前置机服务器端口
    @Value("${bank.server.port}")
    private Integer port;

    //超时时间
    @Value("${bank.server.timeout}")
    private Integer timeout;

    //编码
    @Value("${bank.server.charset}")
    private String charset;

    //建行一级行号
    @Value("${bank.branchCode}")
    private String branchCode;
}
