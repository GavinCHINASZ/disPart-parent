package com.disPart.Service.impl;

import com.alibaba.fastjson.JSON;
import com.disPart.Service.GeTuiService;
import com.disPart.utils.PushMessageUtil;
import com.dispart.result.Result;
import com.dispart.vo.busineCommon.TPushNotesVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;


/**
 * 个推
 *
 * @author 黄贵川
 * @version 1.0
 * @date 2021/08/28
 */
@Service
@Slf4j
public class GeTuiServiceImpl implements GeTuiService {
    @Value("${getui.appId}")
    private String appId;

    @Value("${getui.appKey}")
    private String appKey;

    @Value("${getui.masterSecret}")
    private String masterSecret;

    @Value("${getui.url}")
    private String url;

    /**
     * 个推--向个人推送消息
     *
     * @author 黄贵川
     * @date 2021/08/28
     * @param tPushNotesVo TPushNotesVo
     * @return Result
     */
    @Override
    public Result geTuiPushMessage(TPushNotesVo tPushNotesVo) {
        log.info("DISP20210313 个推--向个人推送消息" + JSON.toJSONString(tPushNotesVo));
        try{
            log.debug("DISP20210313 url:" + url);
            log.debug("DISP20210313 appId:" + appId);
            log.debug("DISP20210313 appKey:" + appKey);
            log.debug("DISP20210313 masterSecret:" + masterSecret);
            String title = StringUtils.isEmpty(tPushNotesVo.getTitle()) ? "" : tPushNotesVo.getTitle();
            log.debug("DISP20210313 title:" + title);
            String pushMsg = tPushNotesVo.getPushMsg();
            log.debug("DISP20210313 pushMsg:" + pushMsg);

            Map<String, Object> resultMap = PushMessageUtil.pushMessage(url, appId, appKey, masterSecret, tPushNotesVo.getClientId(),
                                            title, pushMsg,null);
            /**
             * 返回:
             * result:ok
             * taskId:OSS-0828_5b774e2d194e77ae13a6d7009a01564f
             * status:successed_offline
             */
            if (resultMap.get("result").equals("ok")){
                log.info("DISP20210313 个推--向个人推送消息成功");
                return Result.ok("个推--向个人推送消息成功");
            }else {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, Object> entry : resultMap.entrySet()){
                    sb.append(entry.getKey());
                    sb.append(":");
                    sb.append(entry.getValue());
                }
                log.error("DISP20210313 个推--向个人推送消息异常:" + sb.toString());
                return Result.fail("个推--向个人推送消息失败");
            }
        }catch (Exception e){
            log.error("DISP20210313 个推--向个人推送消息异常", e);
            return Result.fail("个推--向个人推送消息异常");
        }
    }
}