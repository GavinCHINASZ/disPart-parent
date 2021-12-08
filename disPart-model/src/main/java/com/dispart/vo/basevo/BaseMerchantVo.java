package com.dispart.vo.basevo;

import lombok.Data;

@Data
public class BaseMerchantVo {
    //客户编号
    private String merchantcode;
    //客户二维码路径
    private String qrcodeUrl;

    //客户编号(新表：t_custom_info_manager )
    private String provId;
}
