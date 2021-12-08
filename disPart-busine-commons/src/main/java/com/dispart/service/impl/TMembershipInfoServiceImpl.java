package com.dispart.service.impl;

import com.dispart.service.TMembershipInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dispart.dao.TMembershipInfoMapper;
import com.dispart.model.businessCommon.TMembershipInfo;

@Service
public class TMembershipInfoServiceImpl extends ServiceImpl<TMembershipInfoMapper, TMembershipInfo> implements TMembershipInfoService {

}
