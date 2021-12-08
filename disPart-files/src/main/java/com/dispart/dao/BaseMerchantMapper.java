package com.dispart.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dispart.vo.basevo.BaseMerchant;
import com.dispart.vo.basevo.BaseMerchantVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BaseMerchantMapper extends BaseMapper<BaseMerchant> {
    /**
     * 获取全部没二维码路径的客户信息
     * @return
     */
    List<BaseMerchantVo> findAll();

    /**
     * 给客户维护二维码
     * @param baseMerchantVo
     * @return
     */
    int updateUrlByKey(BaseMerchantVo baseMerchantVo);
}