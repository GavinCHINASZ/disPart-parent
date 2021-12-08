package com.dispart.service;

import com.dispart.dto.hsbdto.HsbCustomDto;
import com.dispart.dto.hsbdto.QuryCustomInfoInDto;
import com.dispart.result.Result;

/**
 * @author:xts
 * @date:Created in 2021/6/13 21:20
 * @description 惠市宝交易接口
 * @modified by:
 * @version: 1.0
 */
public interface HsbService {
    /**
     * 惠市宝客户信息增量交易回调接口
     * @param params json格式请求参数
     * @return
     */
    String customInfoCallback(String params);

    /**
     * 惠市宝客户信息增量交易回调接口
     *
     * @param params json格式请求参数
     * @return
     */
    Result<HsbCustomDto> customInfoQury(QuryCustomInfoInDto params);


}