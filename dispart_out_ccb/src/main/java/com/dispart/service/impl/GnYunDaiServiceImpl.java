package com.dispart.service.impl;

import com.dispart.service.GnYunDaiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.dispart.util.HttpUtil;

@Service
@Slf4j
public class GnYunDaiServiceImpl implements GnYunDaiService {

    @Value("${url}")
    private String url;

    @Override
    public String relayRequest(String param) {
        log.info("转发客户交易查询交易开始，参数：" + param);
        String postRes = HttpUtil.post(this.url, param);
        log.info("转发交易完成，返回：" + postRes);
        return postRes;
    }
}
