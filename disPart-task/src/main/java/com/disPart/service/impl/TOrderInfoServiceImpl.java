package com.disPart.service.impl;

import com.dispart.model.order.TOrderInfo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.disPart.dao.TOrderInfoMapper;
import com.disPart.service.TOrderInfoService;
@Service
public class TOrderInfoServiceImpl extends ServiceImpl<TOrderInfoMapper, TOrderInfo> implements TOrderInfoService{

}
