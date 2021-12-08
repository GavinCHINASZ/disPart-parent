package com.dispart.service;

import com.dispart.dto.ResultOutDto;
import com.dispart.parmeterdto.DISP20210181MemberShipInfoInDto;
import com.dispart.parmeterdto.DISP20210188OutDto;
import com.dispart.parmeterdto.DISP20210197OutDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.vo.AccnoInfoDetailVo;
import com.dispart.vo.AccnoInfoVo;
import com.dispart.vo.user.MemberShipInfoVo;


public interface MemberShipInfoService {
    /**
     * 开户
     *
     * @param mbin
     * @return Integer
     */
    Result<ResultOutDto> openAccountInfo(DISP20210181MemberShipInfoInDto mbin, String operator, String chanlNo);

    /**
     * 销户
     *
     * @param mbin
     * @return Integer
     */
    Result<ResultOutDto> cancelllationAccount(DISP20210181MemberShipInfoInDto mbin, String operator, String chanlNo);

    /**
     * 冻结
     *
     * @param mbin
     * @return Integer
     */
    Result<ResultOutDto> frozenAccount(DISP20210181MemberShipInfoInDto mbin, String operator, String chanlNo);

    /**
     * 解冻卡
     *
     * @param mbin
     * @return Integer
     */
    Result<ResultOutDto> unfreezeAccount(DISP20210181MemberShipInfoInDto mbin, String operator, String chanlNo);

    /**
     * 客户卡片查询
     *
     * @param mbin
     * @return Integer
     */
    Result<ResultOutDto> queryCard(DISP20210181MemberShipInfoInDto mbin);


    /**
     * 客户卡片查询
     *
     * @param mbin
     * @return Integer
     */
    Result<DISP20210197OutDto> queryCardListDetail(DISP20210181MemberShipInfoInDto mbin);

    /**
     * 客户卡片挂失
     *
     * @param mbin
     * @return Integer
     */
    Result<ResultOutDto> reportLossCard(DISP20210181MemberShipInfoInDto mbin, String operator, String chanlNo);

    /**
     * 客户卡片解挂
     *
     * @param mbin
     * @return Integer
     */
    Result<ResultOutDto> untieCard(DISP20210181MemberShipInfoInDto mbin, String operator, String chanlNo);

    /**
     * 客户卡片补卡换卡
     *
     * @param mbin
     * @return Integer
     */
    Result<ResultOutDto> reChangeCard(DISP20210181MemberShipInfoInDto mbin, String operator, String chanlNo);

    /**
     * 客户卡片密码修改
     *
     * @param mbin
     * @return Integer
     */
    Result<ResultOutDto> modifyCardPass(DISP20210181MemberShipInfoInDto mbin, String operator, String chanlNo);

    /**
     * 客户卡片密码修改
     *
     * @param mbin
     * @return Integer
     */
    Result<ResultOutDto> resetCard(DISP20210181MemberShipInfoInDto mbin, String operator, String chanlNo);

    /**
     * @param findDepByParam
     * @return
     * @PostMapping("/DISP20210187")
     * @ApiOperation(value = "调账申请")
     */
    Result<ResultOutDto> applyReconciliationByParam(AccnoInfoDetailVo findDepByParam, String operator, String chanlNo);

    /**
     * @param findDepByParam
     * @return
     * @PostMapping("/DISP20210188")
     * @ApiOperation(value = "调账申请查询")
     */
    Result<DISP20210188OutDto> queryReconciliationByParm(AccnoInfoDetailVo findDepByParam);

    /**
     * @param findDepByParam
     * @return
     * @PostMapping("/DISP20210189")
     * @ApiOperation(value = "调账申请处理")
     */
    Result<ResultOutDto> handleApplyReconcliByParam(AccnoInfoDetailVo findDepByParam, String operator, String chanlNo);

    /**
     * @param findDepByParam
     * @return
     * @PostMapping("/DISP20210190")
     * @ApiOperation(value = "提现申请")
     */
    Result applyWithdrawByParam(AccnoInfoDetailVo findDepByParam, String operator, String chanlNo);

    /**
     * @param findDepByParam
     * @return
     * @PostMapping("/DISP20210189")
     * @ApiOperation(value = "提现查询")
     */
    Result<DISP20210188OutDto> queryWithdrawByParam(AccnoInfoDetailVo findDepByParam);

    /**
     * @param findDepByParam
     * @return
     * @PostMapping("/DISP20210192")
     * @ApiOperation(value = "复核")
     */
    Result<ResultOutDto> reviewAndHandleWithdrawByParam(AccnoInfoDetailVo findDepByParam);

    /**
     * 提现转账 DISP20210193
     *
     * @param findDepByParam AccnoInfoDetailVo
     * @return Result<ResultOutDto>
     */
     Result<ResultOutDto> transferyParam(AccnoInfoDetailVo findDepByParam);

    /**
     * 一卡通密码校验
     *
     * @param param Request<MemberShipInfoVo>
     * @return Result
     * @author 黄贵川
     */
    Result memberShipInfoPasswdCheck(Request<MemberShipInfoVo> param);

    /**
     * 提现校验
     *
     * @author 黄贵川
     * @date 2021-09-18
     * @param param
     * @return Result
     */
    Result withdrawCheck(Request<AccnoInfoVo> param);

    /**
     * 现金提现
     *
     * @param param AccnoInfoDetailVo
     * @return Result
     * @author 黄贵川
     * @date 2021-11-10

    Result cashWithdraw(Request<AccnoInfoDetailVo> param);
     */
}
