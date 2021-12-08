package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dispart.dao.DeviceMapper;
import com.dispart.dao.TLoudspeakerInfoMapper;
import com.dispart.dto.deviceManagerDto.DeviceVo;
import com.dispart.dto.deviceManagerDto.TLoudspeakerInfo;
import com.dispart.dto.deviceManagerDto.YunInfoVo;
import com.dispart.httpclient.HttpClient;
import com.dispart.request.Request;
import com.dispart.request.RequestHead;
import com.dispart.result.Result;
import com.dispart.result.ResultToHSBOut;
import com.dispart.service.PlaySignService;
import com.dispart.service.SequenceService;
import com.dispart.utils.YunKeyUtils;
import com.dispart.utils.base.AssenbleManager;
import com.dispart.vo.busineCommon.TPushNotesVo;
import com.dispart.vo.horn.TerminalPlayModel;
import com.dispart.vo.order.ReturnFromBusiness;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
@RefreshScope
public class PlaySignServiceImpl implements PlaySignService {
    @Autowired
    DeviceMapper deviceMapper;

    @Autowired
    TLoudspeakerInfoMapper tLoudspeakerInfoMapper;

    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate2;

    @Override
    @Async
    public Result getPlaySoundSign(String indto) {

        log.info("获取云喇叭参数。。。。。");
        String accessId = YunKeyUtils.getYunAccessId();//账号
        String accessKey = YunKeyUtils.getYunAccessKey();//密码
        String ProductKey = YunKeyUtils.getYunProductKey();//产品key
        log.debug( " 云喇叭账号:"+accessId+" 云喇叭密码:"+accessKey + " 云喇叭Key:"+ProductKey);
        int timeout = 0;//系统允许超时的时间
        int volume =100;//音量（1-100）没用
        String template = "{贵农购}{收款}${元}";
        String action = "play"; //请求类型
        //外需参数：客户编号，收款金额
        Date newDate = new Date();
        String newTime= new SimpleDateFormat("yyyyMMddHHmmss").format(newDate);

        //通过主订单编号查询子订单金额与客户编号
        ArrayList<YunInfoVo> yunInfoVos= new ArrayList<>();
        try {
            yunInfoVos=deviceMapper.findYunInfoVo(indto);
        }catch (Exception e){
            log.error("通过主订单编号查询子订单金额与客户编号-失败： ",e);
        }

        if(yunInfoVos.size()>0){
            for(YunInfoVo yunInfoVo:yunInfoVos){
                TLoudspeakerInfo loud = new TLoudspeakerInfo();
//获取通讯流水号
                Integer reId = deviceMapper.findReqId("speechId");
                String traceId = "T"+newTime+reId;//播报流水号
                String requestId = "R"+newTime+reId; //请求的唯一标识
                log.info("语音播报流水号： "+requestId);
                //播报金额 = 交易金额+附加金额
                BigDecimal amound = new BigDecimal(yunInfoVo.getTxnAmt());

                if (yunInfoVo.getAdditAmt() != null) {
                    amound = amound.add(new BigDecimal(yunInfoVo.getAdditAmt()));
                }

                try {
                    TPushNotesVo tPushNotesVo = new TPushNotesVo();
                    tPushNotesVo.setBusineNo(indto);
                    tPushNotesVo.setProvId(yunInfoVo.getProvId());
                    tPushNotesVo.setParameterType(6);
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(amound.toString());
                    tPushNotesVo.setParameterList(arrayList);
                    log.info("推送参数" + tPushNotesVo);

                    Request request = new Request<>();
                    RequestHead head = new RequestHead();
                    head.setReqSeqNo(String.valueOf(System.currentTimeMillis()));
                    request.setHead(head);
                    request.setBody(tPushNotesVo);
                    ReturnFromBusiness result1 = restTemplate2.postForObject("http://" + "disPart-busine-commons" + "/securityCenter/DISP20210310", request, ReturnFromBusiness.class);
                    if (result1 != null && result1.getCode() == 200) {
                        log.error("调用推送失败" + result1);
                    } else {
                        log.info("调用推送成功" + result1);
                    }

                } catch (Exception e) {
                    log.error(e.getMessage());
                }

                String deviceId=null;
                try{
                    log.info("通过客户编号获取设备编号");
                    deviceId=deviceMapper.findDeviceIdByCustomer(yunInfoVo.getProvId());
                    if(deviceId==null){
                        log.error("该客户未绑定语音设备");
                        loud.setErrMsg("该客户未绑定语音设备");
                    }
                }catch (Exception e){
                    log.error("通过客户编号获取设备编号-失败",e);
                    loud.setErrMsg("通过客户编号获取设备编号-失败");
                }

                //记录播报内容
                loud.setRequestId(requestId);//
                loud.setDeviceId(deviceId);
                loud.setRequestTime(newTime);
                loud.setAction(action);
                loud.setAmount(amound);
                loud.setTimeout(timeout);//超时时间
                try{
                    if(deviceId==null){
                        log.info("登记播报类容-无对应设备");
                        loud.setDeviceId("000000");//设置默认设备编号以便登记播报记录000000
                        int inNum=tLoudspeakerInfoMapper.insert(loud);
                        continue;
                    }
                    log.info("登记播报内容");
                    int inNum=tLoudspeakerInfoMapper.insert(loud);
                    if(inNum<0){
                        log.error("登记播报内容-失败");
                        continue;
                    }
                }catch (Exception e){
                    log.error("登记播报类容-失败", e);
                    continue;
                }

                //组装语音播报报文
                TerminalPlayModel terminalPlayModel = new TerminalPlayModel();
                terminalPlayModel.setAccessId(accessId);
                terminalPlayModel.setAction(action);
                terminalPlayModel.setAmount(amound.toString());
                terminalPlayModel.setDeviceId(deviceId);
                terminalPlayModel.setTemplate(template);
                terminalPlayModel.setTraceId(traceId);//播报流水号（唯一）
                terminalPlayModel.setTraceInfo("贵龙购收款");//播报内容描述
                terminalPlayModel.setTimeout(timeout);
                terminalPlayModel.setProductKey(ProductKey);
                terminalPlayModel.setVolume(volume);
                terminalPlayModel.setRequestId(requestId);
                String playSign = AssenbleManager.getParamStringByModel(terminalPlayModel,accessKey);
                String path = playSign;
                log.info("云喇叭报文-播报请求： "+path);
                Map<String,String> result = serviceOutYun(path);
                //修改播报内容
                loud.setErrCode(result.get("code"));//返回码
                loud.setErrMsg(result.get("message"));//错误信息
                //loud.setRemark("123");//备注
                try {
                    log.info("更新播报结果");
                    int upNum=tLoudspeakerInfoMapper.updateByPrimaryKey(loud);
                    if(upNum<0){
                        log.error("更新播报结果-失败,对应返回结果： "+loud);
                    }
                }catch (Exception e){
                    log.error("登记播报结果-对应返回结果： "+loud,  e);
                }
            }
        }

        return Result.ok();
    }

    @Value("${service.url.PlaySignOut}")
    private String url;//="http://localhost:8090/outCommon/securityCenter/PlaySignOut"
    private String getURL() {
        log.info("获取语音播报的请求URL地址:{}", url);
        return url;
    }
    public Map<String,String> serviceOutYun(String path) {
        // 请求报文
        String reqJson = path;
        // 外呼请求
        String respJson = HttpClient.post(getURL(), reqJson, true);
        JSONObject jsonObject = JSON.parseObject(respJson);
        log.info("播报通讯返回： "+jsonObject.toString());
        Map<String,String> getMap=new HashMap<>();
        getMap.put("code",jsonObject.get("code").toString());
        getMap.put("message",jsonObject.getString("message"));
        return getMap;
    }
}
