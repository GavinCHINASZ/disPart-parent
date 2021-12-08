package com.disPart.Service;

import com.dispart.dto.auth.WxCheck;
import com.dispart.request.Request;
import com.dispart.result.Result;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author:xts
 * @date:Created in 2021/6/30 21:58
 * @description
 * @modified by:
 * @version: 1.0
 */
public interface LoginAuthService {
    /**
     * 获取微信或支付宝OPENid
     *  code
     *  chanlNo
     * @return
     */
    Result loginAuthCheck(String paramStr);
}