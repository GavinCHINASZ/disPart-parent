package com.dispart.dao;

import com.dispart.dto.customdto.ExportCustomInfoInParamDto;
import com.dispart.dto.customdto.QuryCustomInfoInDto;
import com.dispart.dto.customdto.QuryCustomInfoOutParamDto;
import com.dispart.vo.user.TCustomBankcardVo;
import com.dispart.vo.user.TCustomInfoManagerVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface TCustomInfoManagerDao {
    int deleteByPrimaryKey(String provId);

    int insert(TCustomInfoManagerVo record);

    int insertSelective(TCustomInfoManagerVo record);

    TCustomInfoManagerVo selectByPrimaryKey(String provId);

    QuryCustomInfoOutParamDto selectByPrimaryKeyDatils(String provId);

    int updateByPrimaryKeySelective(TCustomInfoManagerVo record);

    int updateByPrimaryKey(TCustomInfoManagerVo record);

    /**
     * 获取用户序列员
     *
     * @return
     */
    Integer selectCustomIdSeq();

    /**
     * 修改客户状态为禁用
     *
     * @param provId
     * @param status
     * @return
     */
    Integer updateDisableStatus(@Param("provId") String provId, @Param("status") String status);

    /**
     * 根据条件查询客户信息列表
     *
     * @param record
     * @return
     */
    List<QuryCustomInfoOutParamDto> selectByPrimaryKeyWhere(QuryCustomInfoInDto record);

    /**
     * 根据条件查询客户信息列表
     *
     * @param record
     * @return
     */
    List<QuryCustomInfoOutParamDto> selectByPrimaryKeyWhereByProvId(@Param("paramDto") QuryCustomInfoInDto record, @Param("cardList") List<TCustomBankcardVo> cardList);


    /**
     * 根据条件查询客户总条数
     *
     * @param record
     * @return
     */
    int selectCount(QuryCustomInfoInDto record);

    /**
     * 根据条件查询客户总条数
     *
     * @param record
     * @return
     */
    Integer selectCountByProvId(@Param("paramDto") QuryCustomInfoInDto record, @Param("cardList") List<TCustomBankcardVo> cardList);

    /**
     * 根据客户编号list查询客户管理信息
     *
     * @param provIdList
     * @return
     */
    List<TCustomInfoManagerVo> selectByWhereByProvIdList(@Param("provIdList") List<ExportCustomInfoInParamDto> provIdList);

    Integer selectByCertNumCount(@Param("certNum") String certNum, @Param("provId") String provId);

    /**
     * 根据客户名称模糊查询，或手机号查询总条数
     *
     * @param record
     * @return
     */
    Integer selectCountOrValue(QuryCustomInfoInDto record);

    /**
     * 根据客户名称模糊查询，或手机号查询总条数
     *
     * @param record
     * @return
     */
    List<QuryCustomInfoOutParamDto> selectOrValue(QuryCustomInfoInDto record);

    /**
     * 根据手机号或法人电话查询客户信息
     *
     * @param phone
     * @return
     */
    List<TCustomInfoManagerVo> selectCustomInfoByPhone(@Param("phone") String phone);

    /**
     * 根据手机号或法人电话查询客户信息
     *
     * @param phone
     * @return
     */
    List<TCustomInfoManagerVo> selectCustomInfoByPhoneNOEqProvId(@Param("phone") String phone, @Param("provId") String provId);

    /**
     * 修改客户是否允许提现标志
     *
     * @param provId
     * @param isWithdraw
     * @return
     */
    Integer updateWithDrawStatus(@Param("provId") String provId, @Param("isWithdraw") String isWithdraw);

}
