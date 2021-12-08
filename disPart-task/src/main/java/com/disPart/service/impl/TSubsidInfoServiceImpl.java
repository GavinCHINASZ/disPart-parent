package com.disPart.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.disPart.dao.TSubsidInfoMapper;
import com.disPart.service.TSubsidInfoService;
import com.dispart.dto.orderdto.AddOrdersByParam;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.vo.entrance.TSubsidInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class TSubsidInfoServiceImpl implements TSubsidInfoService {
    @Resource
    TSubsidInfoMapper tSubsidInfoMapper;

    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate2;

    /**
     * 修改 3待撤回 的补贴申请
     *
     * @author 黄贵川
     * @date 2021/08/25
     */
    @Override
    public void updateSubsidInfo() {
        log.info("补贴申请任务updateSubsidInfo");

        List<TSubsidInfo> tSubsidInfoList = tSubsidInfoMapper.selectSubsidInfoList();
        if (tSubsidInfoList != null && tSubsidInfoList.size() > 0){
            Request request = new Request();
            for (TSubsidInfo body : tSubsidInfoList) {
                AddOrdersByParam addOrdersByParam = new AddOrdersByParam();
                // txnType 9补贴撤销
                addOrdersByParam.setTxnType("9");
                // transMd 2现金 4一卡通
                addOrdersByParam.setTransMd(body.getSubsidTp().equals("0") ? "2" : "4");
                // txnAmt 金额
                addOrdersByParam.setTxnAmt(body.getSubsidTtlAmt());
                // payeeAcct 收款人账号
                addOrdersByParam.setPayeeAcct(body.getCardNo());
                // payerNo 付款人编号
                addOrdersByParam.setPayerNo(body.getProvId());

                request.setBody(addOrdersByParam);
                Result result = restTemplate2.postForObject("http://" + "disPart-order" + "/securityCenter/DISP20210261", request, Result.class);
                if (request != null) {
                    JSONObject resultJson = (JSONObject) JSONObject.toJSON(result);
                    String code = resultJson.get("code").toString();
                    if (code.equals("200")) {
                        tSubsidInfoMapper.updateSubsidInfoStatus(body.getInId(), "2");
                        log.debug("补贴申请任务updateSubsidInfo撤销成功inId：" + body.getInId());
                    }
                }
            }
        }
    }

    /**
     * 查询是否有可执行的任务
     *
     * @author 黄贵川
     * @date 2021/08/25
     */
    @Override
    public boolean judgeTaskStatus() {
        int dataNum = tSubsidInfoMapper.judgeTaskStatus();
        if (dataNum > 0){
            return true;
        }
        return false;
    }
}
