package com.dispart.dao;

import com.dispart.vo.user.TCustomBankcardVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ITCustomBankcardDao {
    int deleteByPrimaryKey(@Param("provId") String provId, @Param("bankNo") String bankNo);

    int insert(TCustomBankcardVo record);

    int insertSelective(TCustomBankcardVo record);

    TCustomBankcardVo selectByPrimaryKey(@Param("provId") String provId, @Param("bankNo") String bankNo);

    int updateByPrimaryKeySelective(TCustomBankcardVo record);

    int updateByPrimaryKey(TCustomBankcardVo record);

    List<TCustomBankcardVo> selectByPrimaryKeyProvId(@Param("provId") String provId);

    List<TCustomBankcardVo> selectByPrimaryKeyBankNo(@Param("bankNo") String bankNo);

    /**
     * 客户卡片绑定银行卡
     *量
     * @author 黄贵川
     * @param tCustomBankcardVo TCustomBankcardVo
     * @return int　保存成功数量
     */
    int addCustomBankcard(TCustomBankcardVo tCustomBankcardVo);

    /**
     * 客户已绑定银行卡查询
     *
     * @author 黄贵川
     * @date 2021/08/25
     * @param body TCustomBankcardVo
     * @return ResultOutDto
     */
    List<TCustomBankcardVo> findCustomBankcardList(TCustomBankcardVo body);

    /**
     * 客户已绑定银行卡查询数量
     *
     * @author 黄贵川
     * @date 2021/08/25
     * @param body TCustomBankcardVo
     * @return ResultOutDto
     */
    Integer findCustomBankcardCount(TCustomBankcardVo body);

    /**
     * 客户卡片解绑银行卡
     *
     * @author 黄贵川
     * @date 2021/08/11
     * @param tCustomBankcardVo TCustomBankcardVo
     * @return int 删除成功数量
     */
    int deleteCustomBankcard(TCustomBankcardVo tCustomBankcardVo);
}