package com.dispart.service;


import com.dispart.dto.deviceManagerDto.DeviceVo;
import com.dispart.dto.deviceManagerDto.TLoudspeakerInfo;
import com.dispart.result.Result;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
@Component
public interface PlaySignService {

    /**
     * 语音播报
     * @param indto 主订单编号
     * @return
     */
    Result getPlaySoundSign(String indto);

}
