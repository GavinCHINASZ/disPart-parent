package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dispart.dao.TPushNotesDao;
import com.dispart.dao.TUserInfoDao;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.GeTuiService;
import com.dispart.vo.busineCommon.TPushNotesVo;
import com.dispart.vo.commons.TUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    private TUserInfoDao tUserInfoDao;
    @Resource
    private TPushNotesDao tPushNotesDao;
    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;
    @Autowired
    private Environment environment;

    @Value("${disPart-out-common.ip}")
    private String outCommonIp;

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
        log.info("DISP20210310 个推--向个人推送消息,参数tPushNotesVo：" + JSON.toJSONString(tPushNotesVo));

        TUserInfo tUserInfoVo = new TUserInfo();
        tUserInfoVo.setUserId(tPushNotesVo.getUserId());
        tUserInfoVo.setProvId(tPushNotesVo.getProvId());
        TUserInfo tUserInfo = tUserInfoDao.queryTUserInfo(tUserInfoVo);
        if (tUserInfo == null){
            log.info("DISP20210310 未查询到用户信息");
            return Result.fail();
        }

        String clientId = tUserInfo.getClientId();
        log.info("DISP20210310 clientId:" + clientId);
        if (StringUtils.isNotEmpty(clientId)){
            String text = environment.getProperty("getui-push-message.message" + tPushNotesVo.getParameterType());
            List<String> parameterList = tPushNotesVo.getParameterList();
            if (parameterList != null && parameterList.size() >0){
                for (int i = 0; i < parameterList.size(); i++) {
                    text = text.replace("value" + i, parameterList.get(i));
                }
            }
            log.info("DISP20210310 text:" + text);
            tPushNotesVo.setPushMsg(text);
            tPushNotesVo.setClientId(clientId);

            Request request = new Request();
            request.setBody(tPushNotesVo);
            String url = "http://" + outCommonIp + "/securityCenter/DISP20210313";
            log.info("DISP20210310 url:" + url);
            Result result = restTemplate.postForObject(url, request, Result.class);
            if (request != null) {
                JSONObject resultJson = (JSONObject) JSONObject.toJSON(result);
                String code = resultJson.get("code").toString();
                log.info("DISP20210310 个推--code=" + code);
                if (!code.equals("200")) {
                    log.error("DISP20210310 个推--向个人推送消息失败");
                    return Result.fail();
                }
            }

            tPushNotesVo.setUserId(tUserInfo.getUserId());
            // 个推成功
            tPushNotesDao.insertTPushNotes(tPushNotesVo);
            log.info("DISP20210310 insertTPushNotes个推--消息保存成功");
            return Result.ok();
        }
        log.info("DISP20210310 个推--向个人推送消息失败clientId为空");
        return Result.fail("个推--向个人推送消息失败clientId为空");
    }

}