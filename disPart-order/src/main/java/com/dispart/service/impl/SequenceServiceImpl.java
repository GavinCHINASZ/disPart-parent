package com.dispart.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dispart.dao.SequenceMapper;
import com.dispart.model.order.Sequence;
import com.dispart.service.SequenceService;
@Service
public class SequenceServiceImpl extends ServiceImpl<SequenceMapper, Sequence> implements SequenceService{


}
