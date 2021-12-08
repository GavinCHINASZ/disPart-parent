package com.dispart.service.impl;

import com.dispart.result.Result;
import com.dispart.service.CallGeTui;
import com.dispart.service.GeTuiService;
import com.dispart.vo.busineCommon.TPushNotesVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class CallGeTuiImpl implements CallGeTui {

    @Autowired
    private GeTuiService geTuiService;

    @Override
    @Async
    public void callGeTui(TPushNotesVo tPushNotesVo) {
        geTuiService.geTuiPushMessage(tPushNotesVo);
    }
}
