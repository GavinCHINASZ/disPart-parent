package com.disPart.utils;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.notify.Notify;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 个推:消息推送
 *
 * @author 黄贵川
 * @date 2021/08/30
 */
@Slf4j
public class PushMessageUtil {
    /*private static String appId = "VxHF6idnsf6CmxXwbn0nu5";
    private static String appKey = "J4qlTVnmv98pX4fwkTrMB9";
    private static String masterSecret = "r1UmvyhNY8ATvn8Mwuw6q1";
    private static String clientId = "c3c0298df6d5a47cad762fda2d4f8b42";
    // 如果需要使用HTTPS，直接修改url即可
    //private static String url = "https://api.getui.com/apiex.htm";
    private static String url = "http://api.getui.com/apiex.htm";

    public static void main(String[] args) {
        Map<String, Object> stringObjectMap = pushMessage(url, appId, appKey, masterSecret, clientId,
                "标题22", "内容22", null);

        for(Map.Entry<String, Object> entry : stringObjectMap.entrySet()){
            String mapKey = entry.getKey();
            String mapValue = entry.getValue().toString();
            System.out.println(mapKey + ":" +mapValue);
        }
    }*/

    /**
     * 推送消息
     */
    public static Map<String, Object> pushMessage(String url, String appId, String appKey,
        String masterSecret, String clientId, String title, String text, Integer messageType) {

        SingleMessage message = new SingleMessage();
        if (messageType == null){
            // 透传
            TransmissionTemplate template = getTransmissionTemplate(appId, appKey, title, text);
            message.setData(template);
        }else {
            // 通知
            NotificationTemplate template = getNotificationTemplate(appId, appKey, title, text);
            message.setData(template);
        }

        // 消息离线是否存储
        message.setOffline(true);
        // 消息离线存储多久，单位为毫秒
        message.setOfflineExpireTime(1000 * 600);
        // 添加要推送的终端
        Target target = new Target();
        target.setAppId(appId);
        target.setClientId(clientId);

        IPushResult result;
        Map<String, Object> response = null;
        IGtPush push = new IGtPush(url, appKey, masterSecret);
        // 执行推送
        try {
            result = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            log.error("个推PushMessageUtil执行推送异常", e);
            result = push.pushMessageToSingle(message, target, e.getRequestId());
        }

        if (result != null) {
            response = result.getResponse();
        }
        return response;
    }

    /**
     * 透传模板
     *
     * @param appId appId
     * @param appKey appKey
     * @param title 标题
     * @param text 内容
     * @return TransmissionTemplate
     */
    private static TransmissionTemplate getTransmissionTemplate(String appId, String appKey, String title, String text) {
        // 使用透传模板
        TransmissionTemplate template = new TransmissionTemplate();
        // 1:收到通知直接激活app, 2:客服端自行处理
        template.setTransmissionType(2);
        // 透传内容
        template.setTransmissionContent(text);
        template.setAppId(appId);
        template.setAppkey(appKey);

        Notify notify = new Notify();
        // 通知栏显示标题
        notify.setTitle(title);
        // 通知栏内容
        notify.setContent(text);
        // 应用内页面intent
        //String intent = "intent:#Intent;action=android.intent.action.oppopush;launchFlags=0x14000000;package=xxx;component=xxx;end";
        //notify.setIntent(intent);

        // 设置第三方通知
        template.set3rdNotifyInfo(notify);
        return template;
    }

    /**
     * 通知模板
     *
     * @return NotificationTemplate
     */
    private static NotificationTemplate getNotificationTemplate(String appId, String appKey, String title, String text) {
        Style0 style = new Style0();
        // STEP2：设置推送标题、推送内容
        style.setTitle(title);
        style.setText(text);
        // 设置推送图标
        //style.setLogo("push.png");
        // STEP3：设置响铃、震动等推送效果
        // 设置响铃
        style.setRing(true);
        // 设置震动
        style.setVibrate(true);

        // STEP4：选择通知模板
        NotificationTemplate template = new NotificationTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setStyle(style);
        return template;
    }
}
