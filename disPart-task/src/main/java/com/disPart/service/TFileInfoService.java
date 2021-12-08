package com.disPart.service;

import com.dispart.model.order.TFileInfo;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
public interface TFileInfoService extends IService<TFileInfo>{


    List<TFileInfo> selectByFileStatus(String fileStatus);

}
