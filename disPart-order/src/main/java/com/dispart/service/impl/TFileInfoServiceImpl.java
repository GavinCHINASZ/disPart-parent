package com.dispart.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dispart.dao.mapper.TFileInfoMapper;
import com.dispart.model.order.TFileInfo;
import com.dispart.service.TFileInfoService;

@Service
public class TFileInfoServiceImpl extends ServiceImpl<TFileInfoMapper, TFileInfo> implements TFileInfoService {

}

