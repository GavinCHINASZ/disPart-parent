package com.dispart.dao;

import com.dispart.dto.empdto.QrcCodeDto;
import com.dispart.vo.user.PlaceOrderTypeVo;
import com.dispart.vo.user.UserInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ICustomInfoDao {
    /**
     * 根据手机号查询有效用户
     *
     * @param phone
     * @return
     */
    public UserInfoVo quryUserInfoByPhone(@Param("phone") String phone);

    /**
     * 根据手机号，渠道号查询有效用户
     *
     * @param phone
     * @return
     */
    public UserInfoVo quryUserInfoByPhoneAndChanlNo(@Param("phone") String phone,@Param("chanlNo") String ChanlNo);

    /**
     * 根据用户id查询有效用户
     *
     * @param userNo
     * @return
     */
    public UserInfoVo quryUserInfoByUserId(@Param("userId") String userNo);

    /**
     * 根据微信appid查询用户是否注册
     *
     * @param appId
     * @return
     */
    public Integer queryUserInfoByWxAppid(@Param("appId") String appId);

    /**
     * 根据支付宝appid查询用户是否注册
     *
     * @param appId
     * @return
     */
    public Integer queryUserInfoByZfbAppid(@Param("appId") String appId);

    /**
     * 查询用户下单码
     *
     * @param provId 客户编号
     * @return
     */
    public QrcCodeDto selectQrcCOdeBylegaltel(@Param("provId") String provId,@Param("userPhone") String userPhone);

    /**
     * 更新用户昵称
     *
     * @return
     */
    public Integer updateNickNm(@Param("userNickNm") String userNickNm, @Param("userNo") String usreNo);

    /**
     * 更新用户头像
     *
     * @param userNo
     * @return
     */
    public Integer updateIcon(@Param("userIcon") String userIcon, @Param("userNo") String userNo);

    /**
     * 更新用户手机号
     *
     * @param userNo
     * @return
     */
    public Integer updatePhone(@Param("userNewPhone") String userNewPhone, @Param("userNo") String userNo);

    /**
     * 获取用户序列员
     *
     * @return
     */
    public Integer selectUserIdSeq();

    /**
     * 插入用户信息
     * prarm vo
     */
    public Integer insertUserInfo(UserInfoVo vo);

    /**
     * 查询总数根据手机号
     * @param userPhone
     * @return
     */
    public Integer quryCountByPhone(@Param("phone") String userPhone);


    /**
     *查询总数根据手机号，渠道号
     * @param userPhone
     * @return
     */
    public Integer quryCountByPhoneAndChanlNo(@Param("phone") String userPhone,@Param("chanlNo") String chanlNo);

    /**
     * 用户修改密码
     * @param userNo
     * @param newPasswd
     * @param updateDt
     * @return
     */
    public Integer updateUserPasswd(@Param("userId") String userNo, @Param("newPasswd") String newPasswd, @Param("updateDt") Date updateDt);

    /**
     * 更新微信openId
     * @param openId
     * @param updateDt
     * @return
     */
    public Integer updateUserOpenId(@Param("openId") String openId,@Param("userId") String userId, @Param("updateDt") Date updateDt);


    /**
     * 用户修改密码
     * @param phone
     * @param newPasswd
     * @param updateDt
     * @return
     */
    public Integer updateUserPasswdByPhone(@Param("phone") String phone, @Param("newPasswd") String newPasswd, @Param("updateDt") Date updateDt);



    /**
     * 查询下单模式
     * @param provId
     * @param placeOrderMd
     * @return
     */
    public PlaceOrderTypeVo selectTpByProvID(@Param("provId") String provId,@Param("placeOrderMd") String placeOrderMd);


    /**
     * 查询下单模式
     * @param provId
     * @param placeOrderMd
     * @return
     */
    public PlaceOrderTypeVo selectTpByProvIDAndStat(@Param("provId") String provId,@Param("placeOrderMd") String placeOrderMd,@Param("status") String status);


    /**
     * 查询下单模式数量
     * @param vo
     * @return
     */
    public Integer insertPlaceOrderTp(PlaceOrderTypeVo vo);

    /**
     * 更新下单模式
     * @param provId
     * @param type
     * @param status
     * @param updateDt
     * @return
     */
    public Integer updateStatusByProvID(@Param("provId") String provId,@Param("type") String type,@Param("status") String status,@Param("updateDt") Date updateDt);



    /**
     * 用户修改密码根据手机号和渠道号
     * @param phone
     * @param newPasswd
     * @param updateDt
     * @return
     */
    public Integer updateUserPasswdByPhoneAndChanlNo(@Param("phone") String phone, @Param("newPasswd") String newPasswd, @Param("updateDt") Date updateDt, @Param("chanlNo") String chanlNo);



}
