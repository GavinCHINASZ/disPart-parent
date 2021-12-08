package com.dispart.service;

import com.dispart.dto.ResultOutDto;
import com.dispart.dto.customdto.*;;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.vo.user.TCommonBankNameVo;
import com.dispart.vo.user.TCustomBankcardVo;
import com.dispart.vo.user.TCustomInfoManagerVo;

public interface CustomManagerService {
    /**
     * 新增客户信息
     * @param param
     * @return
     */
    public Result<ResultOutDto> addCustomInfo(Request<AddCustomInfoInDto> param);

    /**
     * 修改客户信息
     * @param param
     * @return
     */
    public Result<ResultOutDto> updateCustomInfo(Request<UpdateCustomInfoInDto> param);

    /**
     * 禁用客户
     * @param param
     * @return
     */
    public Result<ResultOutDto> disableCustomInfo(Request<DisableCustomInfoInDto> param);

    /**
     * 下载溯源码
     * @param param
     * @return
     */
    public Result<DownLoadqrcodeOutDto> downLoadqrcod(Request<DownLoadqrcodeInDto> param);

    /**
     * 查询客户信息
     * @param param
     * @return
     */
    public Result<QuryCustomInfoOutDto> quryCustomInfo(String userNo,Request<QuryCustomInfoInDto> param);


    /**
     * 导出客户信息
     * @param param
     * @return
     */
    public Result<ExportCustomInfoOutDto> exportCustomInfo(Request<ExportCustomInfoInDto> param);

    /**
     * 常用银行信息查询
     *
     * @author 黄贵川
     * @date 2021/08/18
     * @return TCommonBankNameVo 银行常用列表
     */
    Result<QueryTCommonBanNameListDto> selectTCommonBankNameVoList();

    /**
     * 客户卡片绑定银行卡
     *
     * @author 黄贵川
     * @date 2021/08/11
     * @param param TCustomBankcardVo
     * @return ResultOutDto
     */
    Result<ResultOutDto> addCustomBankcard(Request<TCustomBankcardVo> param);

    /**
     * 客户已绑定银行卡查询
     *
     * @author 黄贵川
     * @date 2021/08/25
     * @param param TCustomBankcardVo
     * @return ResultOutDto
     */
    Result findCustomBankcardList(Request<TCustomBankcardVo> param);

    /**
     * 客户卡片解绑银行卡
     *
     * @author 黄贵川
     * @date 2021/08/11
     * @param param TCustomBankcardVo
     * @return ResultOutDto
     */
    Result<ResultOutDto> deleteCustomBankcard(Request<TCustomBankcardVo> param);

    /**
     * 会员身份证信息实名认证
     *
     * @author 黄贵川
     * @date 2021/08/11
     * @param param TCustomInfoManagerVo
     * @return ResultOutDto
     */
    Result<ResultOutDto> realNameTCustomInfoManager(Request<TCustomInfoManagerVo> param);

    /**
     * 查询客户信息详情
     * @param param
     * @return
     */
    Result<QuryCustomInfoOutParamDto> quryCustomInfoDatils(Request<ExportCustomInfoInParamDto> param);

    /**
     * 会员信息补充
     *
     * @author 黄贵川
     * @date 2021/08/17
     * @param param 客户信息管理
     * @return TCustomInfoManagerVo 客户信息管理
     */
    Result<ResultOutDto> updateTCustomInfoManagerVo(Request<TCustomInfoManagerVo> param);

    /**
     * 是否允许客户提现
     *
     * @author zhongfei
     * @date 2021/08/17
     * @param param 是否允许客户提现
     * @return DisableCustomInfoInDto 客户信息管理
     */
    Result<ResultOutDto> setIsWithdraw(Request<DisableCustomInfoInDto> param);
}
