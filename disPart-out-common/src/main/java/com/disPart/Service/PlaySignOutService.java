package com.disPart.Service;


import com.dispart.result.Result;
import org.springframework.stereotype.Component;

@Component
public interface PlaySignOutService {

    /**
     * 语音播报
     * @param path 通讯请求
     * @return
     */
    Result getPlaySoundSign(String path);

}
