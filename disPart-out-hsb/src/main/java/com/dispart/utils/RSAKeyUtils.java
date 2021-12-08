package com.dispart.utils;

import com.dispart.service.impl.DISP20210044ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 获取惠市宝公钥 和 物流园私钥
 */
public class RSAKeyUtils {

    private static final Logger logger = LoggerFactory.getLogger(RSAKeyUtils.class);

    private static final
    DISP20210044ServiceImpl disp20210044Service = SpringUtil.getBean("disp20210044Service", DISP20210044ServiceImpl.class);

    //惠市宝公钥
    private static PublicKey hsbPublicKey = null;

    //物流园私钥
    private static PrivateKey localPrivateKey = null;

    private RSAKeyUtils() {}

    /**
     * 获取惠市宝的公钥
     * @return 惠市宝的公钥
     */
    public static PublicKey getHsbPublicKey() {
        if(hsbPublicKey == null) {
            synchronized (RSAKeyUtils.class) {
                if(hsbPublicKey == null) {
                    //外呼获取秘钥
                    String publicKey = disp20210044Service.service("01","hsb.publicKey");
                    //String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0vbHtcG6udUl8OPW2YMyHY/0oYletfG/7zQ87RYSv4ltZw9czMaO9mA/ch0V/I1Vp6wcg8C9fNdOxytp2Gx7vLdwXMXfJN/7aWYKLGwzqFxMQ7iRtNnMEseI2wiuseVlr7nBjvrY6DGgdaTxLqyukfAFcpHBMwenqNGxko8emVA4d6VBEjF0ypF6EDv3yaH6tQYm1lKuSqQHXjS/soxVFGYNOTx9iu10RafJ8HvbQ0Nx671MdR4cjT/yBcVP10kfw+7mbGLmDnoYgV++pkM2A/D6HJ0tHoY6XMoaO0f45hrg+DugRnVsQrYaza60XeoNN9P0730YnLYrhCQDEkKn/wIDAQAB";
                    logger.debug("获取惠市宝公钥:{}", publicKey);
                    hsbPublicKey = RSASignUtils.restorePublicKey(publicKey);
                }
            }

            if(hsbPublicKey == null) {
                logger.error("获取惠市宝RSA公钥失败");
                throw new RuntimeException("获取惠市宝RSA公钥失败");
            }
        }
        return hsbPublicKey;
    }

    /**
     * 获取物流园的私钥
     */
    public static PrivateKey getLocalPrivateKey() {
        if(localPrivateKey == null) {
            synchronized (RSAKeyUtils.class) {
                if(localPrivateKey == null) {
                    //外呼获取秘钥
                    String privateKey = disp20210044Service.service("01","local.privateKey");
                    //String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDS9se1wbq51SXw49bZgzIdj/ShiV618b/vNDztFhK/iW1nD1zMxo72YD9yHRX8jVWnrByDwL18107HK2nYbHu8t3Bcxd8k3/tpZgosbDOoXExDuJG02cwSx4jbCK6x5WWvucGO+tjoMaB1pPEurK6R8AVykcEzB6eo0bGSjx6ZUDh3pUESMXTKkXoQO/fJofq1BibWUq5KpAdeNL+yjFUUZg05PH2K7XRFp8nwe9tDQ3HrvUx1HhyNP/IFxU/XSR/D7uZsYuYOehiBX76mQzYD8PocnS0ehjpcyho7R/jmGuD4O6BGdWxCthrNrrRd6g030/TvfRictiuEJAMSQqf/AgMBAAECggEAHa61OMCSSjVQSk10XFRWR8yKafQPDGCAVeKus9kIOETYzMhfkTxavxWZt6+Z+VfVdmsD9BG5V4hfwCw+j0HsQwg4WgVJOUH+eLzvr4Jl3klmPZ0Jez2ttfK3McJN+h/Bp/Dl5/0paboZzpOvj5aiVUxFJ/KUEV8BWwJuDqXuczmRsG/JwXCDnLsrIEMmyBXDgvGSEiu/L6mjfIMNpwBPGkTiiJGRlBSWIAWdQr/jNw/po0zb+jlCVGWoPcivWGAafXJQX66aAk4JMiNCuLjdkH+xen/xSWU/QEQ9nWwLRJh6l9shvPWY3bfqQKPYkDGyyLdIUzxpIQBpkn+SZp5TSQKBgQD7KOtBC0bcVPpOVu333BC/pxMzqA2HPMyflQuUr7Di6HXZulxb2qEuXfafRMgVo7puLu/TDozZa83L7W1nEf5nbjClVEgVMbhT1uNgkIRuJ8CJu50wnDWUBsmMB4+Dn6kUG7YD/Psp4M1xzhWQSXvyxFXzAf6+DzrISK0FymJeVQKBgQDXB462cxKLbot8la1rQIjw2lX6p/WMBASuqOnPFlkDjIZuQbYfXchWxJRXVLCUikPzBLjWvJHbjrhmBTeIyxTyib+JThqaMzyMf0Y77/GiUJdUBx7PFHFyhDaj+jSmOxi7ceSZF37RNn539D2S6E7Qusj3OYPlaQrSV1WmLxBZAwKBgQDh9lSBdnXQMRvpc0gxoOnoo5Yg+WcCbu7h/CQpJ1ALNX0h4ArMEQzGPH9vl2A0J9PI4a2eww5xZg4HFJtDCetKftaBSCx59PuTYle7PwoGWPlecU7gtwl1Hg4iT4MMto5VqwC84dPOP5RWeUTpRVOgfIefVAIuWGFYZBpWhViu6QKBgQDLU3gdCX6VnbgD3DyZV/KlXK9ETyGefgY3ab18djNBadWL2FLwIevYMBXc5lX6fyt1Vhe55aE+LRwsS+6RSQbLuHkGynXZLW2ppIezEVY5F1+gswLs6PXFRUOtll/Gd8cRJ8bzBAaEqbS4lJjMmyI7uQNi0l3nxYXYE4EHnSUmJQKBgHqcrvr0P2fl01Yma4CxU/CVppAWpN2xPFrIaOgIhMRwAIB/VJxMIcA35hjf5IDMFeevGFtqUCMHtxvFeDWsQlFw4WVR3aaATnQypNC3lukH4kgko7v6IsVj0NoaVOJd2kpNmivlcQGNGX1fg+wfkuF2vksKWfsJVZjM8GUoqMiu";
                    logger.debug("获取物流园私钥:{}", privateKey);
                    localPrivateKey = RSASignUtils.restorePrivateKey(privateKey);
                }
            }

            if(localPrivateKey == null) {
                logger.error("获取惠市宝RSA公钥失败");
                throw new RuntimeException("获取惠市宝RSA公钥失败");
            }
        }

        return localPrivateKey;
    }


}
