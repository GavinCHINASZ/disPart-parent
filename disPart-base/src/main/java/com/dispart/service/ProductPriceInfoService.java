package com.dispart.service;

import com.dispart.model.ProductPriceInfo;
import com.dispart.result.Result;
import org.springframework.web.multipart.MultipartFile;

public interface ProductPriceInfoService {

    Result addProductInfo(MultipartFile file);

}

