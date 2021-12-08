package com.dispart.service;

import com.dispart.dto.orderdto.AddOrdersByParam;
import com.dispart.model.order.ResultHSBCallBack;
import com.dispart.result.Result;
import com.dispart.result.ResultToHSBOut;
import com.dispart.vo.order.PayOrderVo;

import java.io.File;
import java.util.HashMap;

public interface AutoTransction {

    Result<Object> firstStep(PayOrderVo payOrderVo);

    Result<Object> secondStep(HashMap hashMap);

    ResultToHSBOut InsertFile(File file);

    Result<Object> commitToHSB(AddOrdersByParam addOrdersByParam);

    Result<Object> returnToHSB(HashMap hashMap);

    Result<Object> thirdStep(HashMap hashMap);

    Result<Object> withDrawToHSB(HashMap hashMap);
}
