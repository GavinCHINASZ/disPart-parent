package com.dispart.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dispart.dao.TOrderInfoMapper;
import com.dispart.model.order.TOrderInfo;
import com.dispart.service.TOrderInfoService;

@Service
public class TOrderInfoServiceImpl extends ServiceImpl<TOrderInfoMapper, TOrderInfo> implements TOrderInfoService {

//    @Override
//    public Result<List<MainOrderAndOrders>> findByCondiction(FindMainOrdersAndOrdersByParam findMainOrdersAndOrdersByParam) {
//        Integer pageNo = findMainOrdersAndOrdersByParam.getPageNo();
//        Integer pageSize = findMainOrdersAndOrdersByParam.getPageSize();
//
//        IPage<TOrderInfo> page = new Page<>(pageNo,pageSize);
//        IPage<TOrderInfo> tOrderInfoIPage = baseMapper.selectPage(page, null);
//        List<TOrderInfo> records = page.getRecords();
//
//        return null;
//    }
}


