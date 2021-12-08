package com.dispart.config;

import lombok.Data;

/**
 * @author:xts
 * @date:Created in 2021/6/21 21:52
 * @description
 * @modified by:
 * @version: 1.0
 */
@Data
public class RsaKeyVo {
    private String code;
    private String success;
    private String message;
    private DataVo data;
}