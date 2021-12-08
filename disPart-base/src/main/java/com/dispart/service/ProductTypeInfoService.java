package com.dispart.service;

import com.dispart.dto.producttypedto.AddProductType;
import com.dispart.model.ProductTypeInfo;
import com.dispart.result.Result;


public interface

ProductTypeInfoService {

    Result findProductTypeInfo(String isTop);

    Result deleteByPrimaryKey(String prdctTypeId);

    Result insert(AddProductType addProductType);

    Result updateByPrimaryKey(ProductTypeInfo record);

}
