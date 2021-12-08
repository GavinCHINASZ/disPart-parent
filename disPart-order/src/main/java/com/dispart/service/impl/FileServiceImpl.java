package com.dispart.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dispart.dao.FileMapper;
import com.dispart.model.order.File;
import com.dispart.service.FileService;
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService{

}
