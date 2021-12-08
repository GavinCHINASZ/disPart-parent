package com.disPart.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dispart.model.order.TFileInfo;
import java.util.List;
import com.disPart.dao.TFileInfoMapper;
import com.disPart.service.TFileInfoService;
@Service
public class TFileInfoServiceImpl extends ServiceImpl<TFileInfoMapper, TFileInfo> implements TFileInfoService{

    @Override
    public List<TFileInfo> selectByFileStatus(String fileStatus) {
        return baseMapper.selectByFileStatus(fileStatus);
    }

}
