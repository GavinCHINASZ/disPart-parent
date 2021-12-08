package com.dispart.dao;

import com.dispart.vo.commons.BaseMerchant;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author:xts
 * @date:Created in 2021/6/13 22:14
 * @description 客户（会员）信息接口
 * @modified by:
 * @version: 1.0
 */
@Mapper
public interface IBaseMerchantDao {
   List<BaseMerchant> queryAll(BaseMerchant baseMerchantDao);
}