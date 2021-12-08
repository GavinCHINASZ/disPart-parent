package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dispart.dao.*;
import com.dispart.dto.ResultOutDto;
import com.dispart.enums.UserStatEnum;
import com.dispart.model.ParmeterInfo;
import com.dispart.model.businessCommon.IncomEnum;
import com.dispart.model.businessCommon.TAccnoInfo;
import com.dispart.model.businessCommon.TransMdEnum;
import com.dispart.model.businessCommon.TxnTypeEnum;
import com.dispart.parmeterdto.*;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.CustomManagerService;
import com.dispart.service.MemberShipInfoService;
import com.dispart.utils.*;
import com.dispart.vo.AccnoInfoDetailVo;
import com.dispart.vo.AccnoInfoVo;
import com.dispart.vo.MumberShipInfoVo;
import com.dispart.vo.basevo.TCardInfoVo;
import com.dispart.vo.user.CustomWithdrawTmpVo;
import com.dispart.vo.user.MemberShipInfoVo;
import com.dispart.vo.user.TCustomBankcardVo;
import com.dispart.vo.user.TCustomInfoManagerVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import static com.dispart.enums.CustomAccountEnum.*;
import static com.dispart.result.UserResultCodeEnum.*;

/**
 */
@Service
@Slf4j
@Transactional
public class MemberShipInfoServiceImpl implements MemberShipInfoService {
    @Resource
    private MemberShipInfoDao memberShipInfoDao;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    CustomManagerService customManagerService;
    @Resource
    TCustomInfoManagerDao tCustomInfoManagerDao;
    @Resource
    CustomAccountDao customAccountDao;
    @Resource
    CustomWithdrawTmpDao customWithdrawTmpDao;
    @Resource
    AccnoBalChangeMapper accnoBalChangeMapper;

    /**
     * DISP20210181开户
     */
    @Override
    public Result<ResultOutDto> openAccountInfo(DISP20210181MemberShipInfoInDto mbin, String operator, String chanlNo) {
        log.info("DISP20210181 开户传入数据:" + mbin);

        String cardNo = mbin.getCardNo();
        log.debug("接收前端发送的卡号:{}", cardNo);

        // 生成账户号
        String imageId = String.format("%09d", memberShipInfoDao.selectAccnoId());
        String account = MumberShipUtil.ACCOUNT + imageId + MumberShipUtil.getThreeRandom();
        mbin.setAccount(account);

        // 如果是虚拟卡自动生成卡号   19位   0-实体卡 1-虚拟卡
        if (Integer.parseInt(mbin.getCardTp()) == 1) {
            String virtualCardId = MumberShipUtil.virtualType + String.format("%08d", memberShipInfoDao.selectVirtualCardId());
            String virtualCard = MumberShipUtil.ACCOUNT + virtualCardId + MumberShipUtil.getTwoRandom();
            mbin.setCardNo(virtualCard);
        } else {
            // 自动生成账户
            // 判断输入的卡是否已经使用
            if (!mbin.getCardNo().equals("") || !mbin.getCardNo().isEmpty()) {
                TCardInfoVo tCardInfoVo = new TCardInfoVo();
                tCardInfoVo.setCardNo(mbin.getCardNo());
                TCardInfoVo tCardInfoVoReturn = memberShipInfoDao.selectCardInfoIsUser(tCardInfoVo);
                if (null == tCardInfoVoReturn) {
                    log.error("没有查询到实体卡");
                    return MumberShipUtil.getResultFailDto(CARD_IS_FAIL);
                }
                if (tCardInfoVoReturn.getStatus().equals("1")) {
                    log.error("实体卡已经使用");
                    return MumberShipUtil.getResultFailDto(CARD_IS_USER);
                }
            }
        }

        // 插入开户记录表
        try {
            // 生成流水号
            String sndDtTm = MumberShipUtil.getTimeStamp();//发起方时间戳
            AccnoInfoDetailVo accnoInfoDetailVo = new AccnoInfoDetailVo();
            accnoInfoDetailVo.setCardNo(mbin.getCardNo());
            // 银行卡号
            accnoInfoDetailVo.setBankNo(mbin.getBankNo());
            // 银行联行号
            accnoInfoDetailVo.setBankPayNo(mbin.getBankPayNo());
            accnoInfoDetailVo.setAccount(account);
            accnoInfoDetailVo.setID(sndDtTm);
            accnoInfoDetailVo.setOperId(operator);
            accnoInfoDetailVo.setProvId(mbin.getProvId());
            // 交易类型 0-开户 1-冻结 2-调账 6-挂失 7-修改密码 8-重置密码 9-销户 10-解冻 11-解挂 3-提现
            accnoInfoDetailVo.setTxnDt(new Date());
            accnoInfoDetailVo.setTxnType(AccnoInfoDetailVo.OPEN_ACCOUNT);
            accnoInfoDetailVo.setTransChnl(chanlNo);
            accnoInfoDetailVo.setSummary("开户");
            accnoInfoDetailVo.setProvNm(mbin.getProvNm());
            accnoInfoDetailVo.setChanlNo(chanlNo);
            int resultDetail = memberShipInfoDao.addAccountInfoDetail(accnoInfoDetailVo);
            if (1 != resultDetail) {
                log.error("开户记录表t_accno_info_details插入失败");
                return MumberShipUtil.getResultFailDto(OPEN_ACCOUNT_FAIL);
            }

            // 插入客户账户信息
            TAccnoInfo tccnoInfo = new TAccnoInfo();
            tccnoInfo.setProvId(mbin.getProvId());
            tccnoInfo.setOperId(operator);
            tccnoInfo.setAccount(mbin.getAccount());
            // MAC需要调接口生成
            String mac = MacUtil.initMac(mbin.getProvId(), account);
            if (StringUtils.isEmpty(mac)) {
                log.error("mac生成失败;");
                return Result.fail();
            }
            //@NotNull String provId, @NotNull String account, @NotNull BigDecimal acctBal,
            //@NotNull BigDecimal availBal, @NotNull BigDecimal freezeAmt
            tccnoInfo.setMac(mac);
            tccnoInfo.setDayDt(new Date());
            tccnoInfo.setRemark("开户默认开账户t_accno_info");
            tccnoInfo.setChanlNo(chanlNo);
            int resultTAccnoInfo = memberShipInfoDao.insertTAccnoInfo(tccnoInfo);
            if (1 != resultTAccnoInfo) {
                log.error("开户默认开t_accno_info账户");
                return MumberShipUtil.getResultFailDto(OPEN_ACCOUNT_FAIL);
            }
        } catch (DataAccessException e) {
            log.error("开户默认开账户", e);
            return Result.build(201, "一卡通开户失败");
        }

        // 插入会员卡信息
        int result;
        // 密码MD5加密入库
        try {
            try {
                if(UserStatEnum.FARMING_SYS.getCode().equals(chanlNo)) {
                    String pwdPasswd = PwdKeyboardUtil.genClearTextPwd(mbin.getPasswd(), cardNo);
                    mbin.setPasswd(pwdPasswd);
                }

//                log.debug("密码：{}", mbin.getPasswd());

                String md5Pass = Md5Util.getMd5(mbin.getPasswd());
                mbin.setPasswd(md5Pass);
            } catch (Exception e) {
                log.error("MD5加密失败", e);
                return Result.build(201, "密码加密失败");
            }

            mbin.setStatus(DISP20210181MemberShipInfoInDto.EFFICIENT);
            mbin.setChanlNo(chanlNo);
            result = memberShipInfoDao.openAccountInfo(mbin);
            if (1 != result) {
                log.error("t_membership_info开户失败 ");
                return MumberShipUtil.getResultFailDto(OPEN_ACCOUNT_FAIL);
            }

            // 如果是虚拟卡，插入卡虚拟卡信息
            if (Integer.parseInt(mbin.getCardTp()) == 1) {
                TCardInfoVo tc = new TCardInfoVo();
                tc.setCardNo(mbin.getCardNo());
                tc.setDocumentNum(MumberShipUtil.virtual);
                tc.setCardTp(mbin.getCardTp());
                tc.setStatus(MumberShipUtil.virtualStatus);
                tc.setOperId(operator);
                memberShipInfoDao.insertVirtualCard(tc);
            } else {
                // 实体卡片如果开户成功需要修改卡状态  0-未使用 1-已使用 2-申请中 3-已注销 4-已取消
                TCardInfoVo tc = new TCardInfoVo();
                tc.setCardNo(mbin.getCardNo());
                tc.setStatus(MumberShipUtil.virtualStatus);
                memberShipInfoDao.updateCardStatusByCardNo(tc);
            }
        } catch (DataAccessException e) {
            log.error("开户失败", e);
            return Result.build(201, "一卡通开户失败");
        }

        // 没有绑定银行卡
        boolean bankNoBoolean = null == mbin.getBankNo() || mbin.getBankNo().equals("");
        if (bankNoBoolean && (null == mbin.getBankPayNo() || mbin.getBankPayNo().equals(""))) {
            return UserResUtil.getResultSuccessDto();
        } else {
            Request<TCustomBankcardVo> param = new Request<>();
            //开户是否需要绑卡
            TCustomBankcardVo tCustomBankcardVo = new TCustomBankcardVo();
            tCustomBankcardVo.setProvNm(mbin.getProvName());
            tCustomBankcardVo.setProvAcct(mbin.getAccount());
            tCustomBankcardVo.setProvId(mbin.getProvId());
            tCustomBankcardVo.setCardNo(mbin.getCardNo());
            tCustomBankcardVo.setOperId(operator);
            tCustomBankcardVo.setBankName(mbin.getBankName());
            tCustomBankcardVo.setBankNo(mbin.getBankNo());
            tCustomBankcardVo.setBankPayNo(mbin.getBankPayNo());
            tCustomBankcardVo.setIsCcb(mbin.getIsCcb());
            tCustomBankcardVo.setRemark(mbin.getRemark());
            param.setBody(tCustomBankcardVo);
            // 调用绑定银行卡解卡
            Result resultl = customManagerService.addCustomBankcard(param);
            if (resultl != null) {
                if (!resultl.getCode().equals(200)) {
                    return UserResUtil.buildFail(USER_MESSAGE_SELECT_ERROR);
                }
            }

            log.info("用户业务-客户卡片绑定银行卡成功");
        }
        return UserResUtil.getResultSuccessDto();
    }

    /**
     * 销户
     *
     * @param mbin 卡状态 0-有效 1-冻结 6-挂失 7-禁用 8-注销 9-冻结 A-黑名单
     */
    @Override
    public Result<ResultOutDto> cancelllationAccount(DISP20210181MemberShipInfoInDto mbin, String operator, String chanlNo) {
        log.info("DISP20210182 销户传入数据:{}", mbin);

        try {
            DISP20210184CusAccountDto disp20210184CusAccountDto = new DISP20210184CusAccountDto();
            disp20210184CusAccountDto.setAccount(mbin.getAccount());
            log.info("DISP20210182 查询客户账户详细-查询参数： " + disp20210184CusAccountDto);
            AccnoInfoVo dto = customAccountDao.queryCustomAccountDetail(disp20210184CusAccountDto);
            if (dto != null && dto.getAcctBal() != null) {
                BigDecimal zero = new BigDecimal("0");
                int a = dto.getAcctBal().compareTo(zero);
                if (a > 0) {
                    return Result.build(201, "账户余额大于零元");
                }
            }
        } catch (Exception e) {
            log.error("查询客户账户详细");
            return Result.build(201, "客户查询异常");
        }

        // 插入开户记录表
        try {
            mbin.setStatus(DISP20210181MemberShipInfoInDto.FREEZE);
            // 发起方时间戳
            String sndDtTm = MumberShipUtil.getTimeStamp();
            AccnoInfoDetailVo accnoInfoDetailVo = new AccnoInfoDetailVo();
            accnoInfoDetailVo.setCardNo(mbin.getCardNo());
            accnoInfoDetailVo.setAccount(mbin.getAccount());
            accnoInfoDetailVo.setID(sndDtTm);
            accnoInfoDetailVo.setTxnDt(new Date());
            accnoInfoDetailVo.setOperId(operator);
            accnoInfoDetailVo.setProvId(mbin.getProvId());
            //交易类型 0-开户 1-冻结 2-调账 6-挂失 7-修改密码 8-重置密码 9-销户 10-解冻 11-解挂 3-提现
            accnoInfoDetailVo.setTxnType(AccnoInfoDetailVo.LOGOUT);
            accnoInfoDetailVo.setTransChnl(chanlNo);
            accnoInfoDetailVo.setSummary("销户");
            int resultDetail = memberShipInfoDao.addAccountInfoDetail(accnoInfoDetailVo);
            if (1 != resultDetail) {
                log.error("销户");
                return MumberShipUtil.getResultFailDto(CANCCLELATION_ACCOUNT_FAIL);
            }
        } catch (DataAccessException e) {
            log.error("销户", e);
            return Result.build(201, "销户失败");
        }

        int result;
        try {
            result = memberShipInfoDao.cancelllationAccount(mbin);
            if (1 != result) {
                return MumberShipUtil.getResultFailDto(CANCCLELATION_ACCOUNT_FAIL);
            }
        } catch (DataAccessException e) {
            log.error("数据更新异常", e);
            return Result.build(201, "销户失败");
        }
        return UserResUtil.getResultSuccessDto();
    }

    /**
     * 冻结卡金额
     *
     * @param mbin 卡状态 0-有效 7-禁用 8-注销 9-冻结 A-黑名单 B-挂失
     */
    @Override
    public Result<ResultOutDto> frozenAccount(DISP20210181MemberShipInfoInDto mbin, String operator, String chanlNo) {
        log.info("DISP20210185冻结卡金额金额传入数据:{}", mbin);
        /*
         * 账户金额相应减少，冻结金额增加
         * 冻结金额=已有冻结金额+新冻结金额
         * 可用余额=可用余额-新冻结金额
         * 账户余额不变
         */
        MemberShipInfoOutDto customAccount = memberShipInfoDao.queryCustomAccountDetail(mbin);
        // 账户余额
        BigDecimal acctBal = customAccount.getAcctBal();
        // 可用余额
        BigDecimal availBal = customAccount.getAvailBal();
        // 已有冻结金额
        BigDecimal freezeAmt = customAccount.getFreezeAmt();
        // 新冻结金额
        BigDecimal freezeAmtIn = mbin.getFrozenAmount();

        BigDecimal zero = new BigDecimal("0");
        int a = availBal.compareTo(zero);
        int b = availBal.compareTo(mbin.getFrozenAmount());
        int c = freezeAmtIn.compareTo(zero);
        if (c == 0) {
            log.error("传入的冻结金额为0,不能冻结");
            return MumberShipUtil.getResultFailDto(FREEZEAMT_IS_ZERO);
        } else if (c < 0) {
            log.error("传入的冻结金额为负，不能冻结");
            return MumberShipUtil.getResultFailDto(FREEZEAMT_IS_NEGATIVE);
        }

        if (a == 0) {
            log.error("用户可用余额为0,不能冻结");
            return MumberShipUtil.getResultFailDto(AVAILBAL_IS_ZERO);
        } else if (b < 0) {
            log.info("传入的冻结金额大于可用余额，不能冻结");
            return MumberShipUtil.getResultFailDto(FREEZEAMT_IS_BEYONGD_AVAILBAL);
        }

        DISP20210181MemberShipInfoInDto dto = memberShipInfoDao.queryCardByProvId(mbin.getAccount());

        // 插入冻结记录表
        AccnoInfoDetailVo accnoInfoDetailVo = new AccnoInfoDetailVo();
        try {
            // 发起方时间戳
            String sndDtTm = MumberShipUtil.getTimeStamp();

            accnoInfoDetailVo.setCardNo(dto.getCardNo());
            accnoInfoDetailVo.setAccount(dto.getAccount());
            accnoInfoDetailVo.setID(sndDtTm);
            accnoInfoDetailVo.setTxnDt(new Date());
            accnoInfoDetailVo.setOperId(operator);
            accnoInfoDetailVo.setProvId(dto.getProvId());
            accnoInfoDetailVo.setBeforeAmt(availBal);
            accnoInfoDetailVo.setAfterAmt(availBal.subtract(freezeAmtIn));
            accnoInfoDetailVo.setTxnAmt(freezeAmtIn);
            accnoInfoDetailVo.setRemark(mbin.getRemark());
            //交易类型 0-开户 1-冻结 2-调账 6-挂失 7-修改密码 8-重置密码 9-销户 10-解冻 11-解挂 3-提现
            accnoInfoDetailVo.setTxnType(AccnoInfoDetailVo.FREEZE);
            accnoInfoDetailVo.setTransChnl(chanlNo);
            accnoInfoDetailVo.setSummary("冻结");
            int resultDetail = memberShipInfoDao.addAccountInfoDetail(accnoInfoDetailVo);
            if (1 != resultDetail) {
                log.error("插入冻结记录失败");
                return MumberShipUtil.getResultFailDto(ADD_FROZEN_RECORD_ERROR);
            }
        } catch (DataAccessException e) {
            log.error("插入冻结记录失败", e);
            return Result.build(201, "插入冻结记录失败!");
        }
        /*
         * 改变冻结金额，可用余额，账户金额
         */
        try {
            String oldmac = MacUtil.getMac(mbin.getProvId(), mbin.getAccount(), acctBal, availBal, freezeAmt);
            // 可用余额=可用余额-新冻结金额
            mbin.setAvailBal(availBal.subtract(freezeAmtIn));
            // 冻结金额=已有冻结金额+新冻结金额
            mbin.setFrozenAmount(freezeAmt.add(freezeAmtIn));

            String mac = MacUtil.getMac(mbin.getProvId(), dto.getAccount(), acctBal, mbin.getAvailBal(), mbin.getFrozenAmount());
            if (StringUtils.isEmpty(mac)) {
                log.error("mac生成失败;");
                return Result.build(201, "冻结金额失败!");
            }

            mbin.setMac(mac);
            mbin.setOldmac(oldmac);
            int frozen = memberShipInfoDao.frozenAccountByProvId(mbin);
            if (1 != frozen) {
                return MumberShipUtil.getResultFailDto(UPDATE_ACCNO_INFO_ERROR);
            }
        } catch (DataAccessException e) {
            log.error("数据更新异常", e);
            return Result.build(201, "冻结金额失败!");
        }

        // 账户明细表
        AccnoChangeDetailDto detailDto = new AccnoChangeDetailDto();
        detailDto.setJrnlNum(accnoInfoDetailVo.getID());
        detailDto.setCardNo(accnoInfoDetailVo.getCardNo());
        detailDto.setProvId(accnoInfoDetailVo.getProvId());
        detailDto.setProvNm(accnoInfoDetailVo.getProvNm());
        detailDto.setTxnType(TxnTypeEnum.FREEZE.getTxnType());
        detailDto.setTransMd(TransMdEnum.CARD.getTransMDStatus());
        detailDto.setIncomeTp(IncomEnum.OUT.getValue());
        detailDto.setAcctBal(acctBal);
        detailDto.setBeforeAmt(availBal);
        detailDto.setAfterAmt(mbin.getAvailBal());
        detailDto.setTxnAmt(freezeAmtIn);
        detailDto.setTxnTm(new Date());
        detailDto.setSummary(TxnTypeEnum.FREEZE.getDesc());
        detailDto.setOperId(operator);

        int num = accnoBalChangeMapper.add(detailDto);
        if(num != 1) {
            log.error("更新账户明细表失败,更新数据：{}", JSON.toJSONString(detailDto));
        }

        return UserResUtil.getResultSuccessDto();
    }

    /**
     * 解冻卡金额
     *
     * @param mbin 卡状态 0-有效 7-禁用 8-注销 9-冻结 A-黑名单 B-挂失
     */
    @Override
    public Result<ResultOutDto> unfreezeAccount(DISP20210181MemberShipInfoInDto mbin, String operator, String chanlNo) {
        log.debug("DISP20210186解冻卡金额传入数据:{}", mbin);

        /*
        账户金额相应增加，冻结金额减少
        */
        MemberShipInfoOutDto customAccount = memberShipInfoDao.queryCustomAccountDetail(mbin);
        // 账户余额
        BigDecimal acctBal = customAccount.getAcctBal();
        // 可用余额
        BigDecimal availBal = customAccount.getAvailBal();
        // 冻结金额
        BigDecimal freezeAmt = customAccount.getFreezeAmt();
        // 解冻金额
        BigDecimal unfreezeAmtIn = mbin.getUnfreezeAmount();

        BigDecimal zero = new BigDecimal("0");

        int c = unfreezeAmtIn.compareTo(zero);
        if (c == 0) {
            log.error("传入的解冻金额为0,不能解冻");
            return MumberShipUtil.getResultFailDto(UNFREEZEAMT_IS_ZERO);
        } else if (c < 0) {
            log.error("传入的解冻金额为负，不能解冻");
            return MumberShipUtil.getResultFailDto(UNFREEZEAMT_IS_NEGATIVE);
        }

        //int b = availBal.compareTo(unfreezeAmtIn);
        /*if (b < 0) {
            log.error("传入的解冻金额大于可用余额，不能解冻");
            return MumberShipUtil.getResultFailDto(UNFREEZEAMT_IS_BEYONGD_AVAILBAL);
        }*/

        //插入解冻记录表
        int resultDetail;
        DISP20210181MemberShipInfoInDto dto = memberShipInfoDao.queryCardByProvId(mbin.getAccount());

        AccnoInfoDetailVo accnoInfoDetailVo = new AccnoInfoDetailVo();
        try {
            // 发起方时间戳
            String sndDtTm = MumberShipUtil.getTimeStamp();
            accnoInfoDetailVo.setCardNo(dto.getCardNo());
            accnoInfoDetailVo.setAccount(dto.getAccount());
            accnoInfoDetailVo.setID(sndDtTm);
            accnoInfoDetailVo.setTxnDt(new Date());
            accnoInfoDetailVo.setOperId(operator);
            accnoInfoDetailVo.setProvId(dto.getProvId());
            accnoInfoDetailVo.setBeforeAmt(availBal);
            accnoInfoDetailVo.setAfterAmt(availBal.add(unfreezeAmtIn));
            accnoInfoDetailVo.setTxnAmt(unfreezeAmtIn);
            accnoInfoDetailVo.setRemark(mbin.getRemark());
            // 交易类型 0-开户 1-冻结 2-调账 6-挂失 7-修改密码 8-重置密码 9-销户 10-解冻 11-解挂 3-提现
            accnoInfoDetailVo.setTxnType(AccnoInfoDetailVo.UNFREEZE_ACCOUNT);
            accnoInfoDetailVo.setTransChnl(chanlNo);
            accnoInfoDetailVo.setSummary("解冻");
            resultDetail = memberShipInfoDao.addAccountInfoDetail(accnoInfoDetailVo);
            if (1 != resultDetail) {
                log.error("DISP20210186插入解冻记录失败");
                return MumberShipUtil.getResultFailDto(UNFREEZE_ACCOUNT_FAIL);
            }
        } catch (DataAccessException e) {
            log.error("DISP20210186插入解冻记录失败", e);
            return Result.build(201, "解冻失败");
        }

        try {
            /*
             * 冻结金额=冻结金额-解冻金额
             * 可用余额=可用余额+解冻金额
             * 账户余额=不改变
             */
            String oldmac = MacUtil.getMac(mbin.getProvId(), mbin.getAccount(), acctBal, availBal, freezeAmt);
            // 可用余额=可用余额+解冻金额
            mbin.setAvailBal(availBal.add(unfreezeAmtIn));
            // 冻结金额=冻结金额-解冻金额
            mbin.setFrozenAmount(freezeAmt.subtract(unfreezeAmtIn));

            String mac = MacUtil.getMac(mbin.getProvId(), dto.getAccount(), acctBal, mbin.getAvailBal(), mbin.getFrozenAmount());
            if (StringUtils.isEmpty(mac)) {
                log.error("mac生成失败;");
                return Result.fail();
            }

            mbin.setMac(mac);
            mbin.setOldmac(oldmac);
            int unfreeze = memberShipInfoDao.unfreezeAccountByProvId(mbin);
            if (1 != unfreeze) {
                return MumberShipUtil.getResultFailDto(UPDATE_ACCNO_INFO_ERROR);
            }
        } catch (DataAccessException e) {
            log.error("DISP20210186数据更新异常", e);
            return Result.fail();
        }

        //账户明细记录表
        AccnoChangeDetailDto detailDto = new AccnoChangeDetailDto();
        detailDto.setJrnlNum(accnoInfoDetailVo.getID());
        detailDto.setCardNo(accnoInfoDetailVo.getCardNo());
        detailDto.setProvId(accnoInfoDetailVo.getProvId());
        detailDto.setProvNm(accnoInfoDetailVo.getProvNm());
        detailDto.setTxnType(TxnTypeEnum.UNFREEZE.getTxnType());
        detailDto.setTransMd(TransMdEnum.CARD.getTransMDStatus());
        detailDto.setIncomeTp(IncomEnum.IN.getValue());
        detailDto.setAcctBal(acctBal);
        detailDto.setBeforeAmt(availBal);
        detailDto.setAfterAmt(mbin.getAvailBal());
        detailDto.setTxnAmt(unfreezeAmtIn);
        detailDto.setTxnTm(new Date());
        detailDto.setSummary(TxnTypeEnum.UNFREEZE.getDesc());
        detailDto.setOperId(operator);

        int num = accnoBalChangeMapper.add(detailDto);
        if(num != 1) {
            log.error("解冻账户金额, 记录账户明细表出错, 出错记录:{}", JSON.toJSONString(detailDto));
        }

        return UserResUtil.getResultSuccessDto();
    }

    @Override
    public Result<ResultOutDto> queryCard(DISP20210181MemberShipInfoInDto mbin) {
        return null;
    }

    /**
     * 客户卡查询
     * 卡状态 0-有效 1-冻结 6-挂失 7-禁用 8-注销 9-冻结 A-黑名单
     */
    @Override
    public Result<DISP20210197OutDto> queryCardListDetail(DISP20210181MemberShipInfoInDto into) {
        log.info("DISP20210197 查询客户账户-查询参数:{} ", into);

        DISP20210197OutDto dto = new DISP20210197OutDto();
        // 有分页参数才做分页查询
        if (into.getPageNum() != null && into.getPageNum() > 0 && into.getPageSize() > 0) {
            Long pageNum = (into.getPageNum() - 1) * into.getPageSize();
            into.setStartIndex(pageNum);
            Integer TolPageNum;
            try {
                TolPageNum = memberShipInfoDao.countCardAccount(into);
                if (TolPageNum > 0) {
                    dto.setTolPageNum(TolPageNum);
                    dto.setCusAccountList(memberShipInfoDao.queryCardListDetail(into));
                } else {
                    return Result.build(ResultCodeEnum.DATA_NO_ERROR.getCode(), ResultCodeEnum.DATA_NO_ERROR.getMessage());
                }
            } catch (Exception e) {
                log.error("查询客户账户条数异常", e);
            }
        } else {
            try {
                List<MumberShipInfoVo> arr = memberShipInfoDao.queryCardListDetail(into);
                dto.setCusAccountList(arr);
                log.debug(JSON.toJSONString(arr));
            } catch (Exception e) {
                log.error("查询用户余额，可用余额，冻结金额异常", e);
            }
        }
        return Result.ok(dto);
    }

    /**
     * 挂失卡片
     *
     * @param mbin 卡状态 0-有效 1-冻结 6-挂失 7-禁用 8-注销 9-冻结 A-黑名单
     */
    @Override
    public Result<ResultOutDto> reportLossCard(DISP20210181MemberShipInfoInDto mbin, String operator, String chanlNo) {
        log.info("挂失卡片传入数据:{}", mbin);
        mbin.setStatus(AccnoInfoDetailVo.REPORT_LOSS);
        DISP20210181MemberShipInfoInDto dto = memberShipInfoDao.queryCardByCardNo(mbin.getCardNo());
        // 插入挂失记录表
        int resultDetail;
        try {
            // 发起方时间戳
            String sndDtTm = MumberShipUtil.getTimeStamp();
            AccnoInfoDetailVo accnoInfoDetailVo = new AccnoInfoDetailVo();
            accnoInfoDetailVo.setCardNo(dto.getCardNo());
            accnoInfoDetailVo.setAccount(dto.getAccount());
            accnoInfoDetailVo.setID(sndDtTm);
            accnoInfoDetailVo.setTxnDt(new Date());
            accnoInfoDetailVo.setOperId(operator);
            accnoInfoDetailVo.setProvId(dto.getProvId());
            accnoInfoDetailVo.setTxnAmt(dto.getFrozenAmount());
            // 交易类型 0-开户 1-冻结 2-调账 6-挂失 7-修改密码 8-重置密码 9-销户 10-解冻 11-解挂 3-提现
            accnoInfoDetailVo.setTxnType(AccnoInfoDetailVo.REPORT_LOSS);
            accnoInfoDetailVo.setTransChnl(chanlNo);
            accnoInfoDetailVo.setSummary("挂失");
            resultDetail = memberShipInfoDao.addAccountInfoDetail(accnoInfoDetailVo);
            if (1 != resultDetail) {
                log.error("插入挂失记录失败");
                return MumberShipUtil.getResultFailDto(CARD_REPORTLOSS_FAIL);
            }
        } catch (DataAccessException e) {
            log.error("插入挂失记录失败", e);
            throw new RuntimeException("挂失客户数据库插入异常");
        }

        int result;
        try {
            result = memberShipInfoDao.cancelllationAccount(mbin);
            if (1 != result) {
                return MumberShipUtil.getResultFailDto(CARD_REPORTLOSS_FAIL);
            }
        } catch (DataAccessException e) {
            log.error("数据更新异常", e);
        }
        return UserResUtil.getResultSuccessDto();
    }

    /**
     * 解挂卡片
     *
     * @param mbin 卡状态 0-有效 1-冻结 6-挂失 7-禁用 8-注销 9-冻结 A-黑名单
     */
    @Override
    public Result<ResultOutDto> untieCard(DISP20210181MemberShipInfoInDto mbin, String operator, String chanlNo) {
        log.info("解挂卡片传入数据:{}", mbin);
        mbin.setStatus(DISP20210181MemberShipInfoInDto.EFFICIENT);
        DISP20210181MemberShipInfoInDto dto = memberShipInfoDao.queryCardByCardNo(mbin.getCardNo());
        // 插入挂失记录表
        int resultDetail;
        try {
            String sndDtTm = MumberShipUtil.getTimeStamp();//发起方时间戳
            AccnoInfoDetailVo accnoInfoDetailVo = new AccnoInfoDetailVo();
            accnoInfoDetailVo.setCardNo(dto.getCardNo());
            accnoInfoDetailVo.setAccount(dto.getAccount());
            accnoInfoDetailVo.setID(sndDtTm);
            accnoInfoDetailVo.setTxnDt(new Date());
            accnoInfoDetailVo.setOperId(operator);
            accnoInfoDetailVo.setProvId(dto.getProvId());
            accnoInfoDetailVo.setTxnAmt(dto.getFrozenAmount());
            // 交易类型 0-开户 1-冻结 2-调账 6-挂失 7-修改密码 8-重置密码 9-销户 10-解冻 11-解挂 3-提现
            accnoInfoDetailVo.setTxnType(AccnoInfoDetailVo.UNTIE_CARD);
            accnoInfoDetailVo.setTransChnl(chanlNo);
            accnoInfoDetailVo.setSummary("解挂");
            resultDetail = memberShipInfoDao.addAccountInfoDetail(accnoInfoDetailVo);
            if (1 != resultDetail) {
                log.error("插入解挂记录失败");
                return MumberShipUtil.getResultFailDto(CARD_SOLUTIONS_FAIL);
            }
        } catch (DataAccessException e) {
            log.error("插入解挂记录失败", e);
            throw new RuntimeException("解挂客户数据库插入异常");
        }

        int result;
        try {
            result = memberShipInfoDao.cancelllationAccount(mbin);
            if (1 != result) {
                return MumberShipUtil.getResultFailDto(CARD_SOLUTIONS_FAIL);
            }
        } catch (DataAccessException e) {
            log.error("数据更新异常", e);
        }

        return UserResUtil.getResultSuccessDto();
    }

    /**
     * 补卡换卡
     *
     * @param mbin 卡状态 0-有效 1-冻结 6-挂失 7-禁用 8-注销 9-冻结 A-黑名单
     */
    @Override
    public Result<ResultOutDto> reChangeCard(DISP20210181MemberShipInfoInDto mbin, String operator, String chanlNo) {
        log.info("DISP20210200 补卡换卡传入数据:{}", mbin);
        if (StringUtils.isEmpty(mbin.getPasswd())) {
            log.error("原密码为空");
            return UserResUtil.paramFail("原密码为空");
        }
        if (StringUtils.isEmpty(mbin.getNewPassword())) {
            log.error("新密码为空");
            return UserResUtil.paramFail("新密码为空");
        }
        if (StringUtils.isEmpty(mbin.getVerifyUsPaWd())) {
            log.error("确认密码为空");
            return UserResUtil.paramFail("确认密码为空");
        }

        DISP20210181MemberShipInfoInDto dto = memberShipInfoDao.queryCardByCardNo(mbin.getCardNo());
        try {
            //原密码是否一致
            String md5PassWd = Md5Util.getMd5(mbin.getPasswd());
            if (!md5PassWd.equals(dto.getPasswd())) {
                log.error("原密码不一致");
                return UserResUtil.buildFail(USER_PASSWD_ERROR);
            }
        } catch (Exception e) {
            log.error("密码加密失败", e);
            return Result.build(201, "密码加密失败");
        }

        //确认密码失败
        if (!mbin.getNewPassword().equals(mbin.getVerifyUsPaWd())) {
            return UserResUtil.buildFail(USER_VERIFY_PASSWD_FAIL);
        }

        // 判断输入的卡是否已经使用
        if (!mbin.getCardNo().equals("") || !mbin.getCardNo().isEmpty()) {
            TCardInfoVo tCardInfoVo = new TCardInfoVo();
            tCardInfoVo.setCardNo(mbin.getNewCardNo());
            TCardInfoVo tCardInfoVoReturn = memberShipInfoDao.selectCardInfoIsUser(tCardInfoVo);
            if (null == tCardInfoVoReturn) {
                log.error("没有查询到实体卡");
                return MumberShipUtil.getResultFailDto(CARD_IS_FAIL);
            }
            if (tCardInfoVoReturn.getStatus().equals("1")) {
                log.error("实体卡已经使用");
                return MumberShipUtil.getResultFailDto(CARD_IS_USER);
            }
        }

        // 注销原来卡片
        try {
            mbin.setStatus(DISP20210181MemberShipInfoInDto.FREEZE);
            int result = memberShipInfoDao.cancelllationAccount(mbin);
            if (1 != result) {
                log.error("客户卡补卡换卡失败");
                return MumberShipUtil.getResultFailDto(CARD_RECHANGE_FAIL);
            }
        } catch (DataAccessException e) {
            log.error("数据更新异常", e);
            return Result.build(201, "注销原来卡失败");
        }

        // 添加新的卡片
        try {
            mbin.setCardNo(mbin.getNewCardNo());
            mbin.setPasswd(mbin.getNewPassword());
            mbin.setProvId(dto.getProvId());
            mbin.setCardTp(DISP20210181MemberShipInfoInDto.PHYSICALCARD);
            mbin.setCashIndent(dto.getCashIndent());
            mbin.setCashPledge(dto.getCashPledge());
            mbin.setPhone(dto.getPhone());
            mbin.setRemark(dto.getRemark());
            mbin.setOpenId(dto.getOpenId());
            mbin.setStatus(DISP20210181MemberShipInfoInDto.EFFICIENT);
            // 生成账户号
            String imageId = String.format("%09d", memberShipInfoDao.selectAccnoId());
            String account = MumberShipUtil.ACCOUNT + imageId + MumberShipUtil.getThreeRandom();
            mbin.setAccount(account);
            //mbin.setAccount(dto.getAccount());
            // 开户 t_membership_info
            int result = memberShipInfoDao.openAccountInfo(mbin);
            if (1 != result) {
                return MumberShipUtil.getResultFailDto(CARD_RECHANGE_FAIL);
            }

            // 修改卡为已经使用
            TCardInfoVo tc = new TCardInfoVo();
            tc.setCardNo(mbin.getCardNo());
            tc.setStatus(MumberShipUtil.virtualStatus);
            memberShipInfoDao.updateCardStatusByCardNo(tc);
        } catch (DataAccessException e) {
            log.error("补/换卡失败 数据更新异常", e);
            return Result.build(201, "补/换卡失败");
        }

        // 账户操作记录 t_accno_info_details
        try {
            // 发起方时间戳
            String sndDtTm = MumberShipUtil.getTimeStamp();
            AccnoInfoDetailVo accnoInfoDetailVo = new AccnoInfoDetailVo();
            accnoInfoDetailVo.setCardNo(mbin.getNewCardNo());
            accnoInfoDetailVo.setAccount(mbin.getAccount());
            accnoInfoDetailVo.setID(sndDtTm);
            accnoInfoDetailVo.setOperId(operator);
            accnoInfoDetailVo.setProvId(mbin.getProvId());
            // 交易类型 0-开户 1-冻结 2-调账 3-提现 4-补卡换卡 6-挂失 7-修改密码 8-重置密码 9-销户 10-解冻 11-解挂
            accnoInfoDetailVo.setTxnDt(new Date());
            accnoInfoDetailVo.setTxnType(AccnoInfoDetailVo.REPLACE_CARD);
            accnoInfoDetailVo.setTransChnl(chanlNo);
            accnoInfoDetailVo.setSummary("补卡换卡");
            int resultDetail = memberShipInfoDao.addAccountInfoDetail(accnoInfoDetailVo);
            if (1 != resultDetail) {
                log.error("补卡换卡--开户失败");
                return MumberShipUtil.getResultFailDto(OPEN_ACCOUNT_FAIL);
            }
        } catch (DataAccessException e) {
            log.error("补卡换卡", e);
            return MumberShipUtil.getResultFailDto(OPEN_ACCOUNT_FAIL);
        }

        return UserResUtil.getResultSuccessDto();
    }

    /**
     * 修改密码
     *
     * @param param 卡状态 0-有效 1-冻结 6-挂失 7-禁用 8-注销 9-冻结 A-黑名单
     */
    @Override
    public Result<ResultOutDto> modifyCardPass(DISP20210181MemberShipInfoInDto param, String operator, String chanlNo) {
        log.info("修改密码, 请求报文:{}", param);
        DISP20210181MemberShipInfoInDto dto = memberShipInfoDao.queryCardByCardNo(param.getCardNo());
        if (StringUtils.isEmpty(param.getPasswd())) {
            return UserResUtil.paramFail("原密码为空");
        }

        if(UserStatEnum.FARMING_SYS.getCode().equals(chanlNo)) {
            String pwdPasswd = PwdKeyboardUtil.genClearTextPwd(param.getPasswd(), param.getCardNo());
            String newPasswd = PwdKeyboardUtil.genClearTextPwd(param.getNewPassword(), param.getCardNo());
            //param.getVerifyUsPaWd()
            String verifyPawd = PwdKeyboardUtil.genClearTextPwd(param.getVerifyUsPaWd(), param.getCardNo());
            param.setPasswd(pwdPasswd);
            param.setNewPassword(newPasswd);
            param.setVerifyUsPaWd(verifyPawd);
        }

        /*log.debug("旧密码：{}", param.getPasswd());
        log.debug("新密码：{}", param.getNewPassword());*/

        // 原密码是否一致
        try {
            String md5Pass = Md5Util.getMd5(param.getPasswd());
            if (!md5Pass.equals(dto.getPasswd())) {
                return UserResUtil.buildFail(USER_PASSWD_ERROR);
            }
        } catch (Exception e) {
            log.error("原密码MD5加密失败", e);
            return UserResUtil.buildFail(USER_PASSWD_ERROR);
        }

        if (StringUtils.isEmpty(param.getNewPassword())) {
            return UserResUtil.paramFail("新密码为空");
        }
        if (StringUtils.isEmpty(param.getVerifyUsPaWd())) {
            return UserResUtil.paramFail("确认密码为空");
        }
        //确认密码失败
        if (!param.getNewPassword().equals(param.getVerifyUsPaWd())) {
            return UserResUtil.buildFail(USER_VERIFY_PASSWD_FAIL);
        }

        //修改密码记录表
        try {
            String sndDtTm = MumberShipUtil.getTimeStamp();//发起方时间戳
            AccnoInfoDetailVo accnoInfoDetailVo = new AccnoInfoDetailVo();
            accnoInfoDetailVo.setCardNo(dto.getCardNo());
            accnoInfoDetailVo.setAccount(dto.getAccount());
            accnoInfoDetailVo.setID(sndDtTm);
            accnoInfoDetailVo.setTxnDt(new Date());
            accnoInfoDetailVo.setOperId(operator);
            accnoInfoDetailVo.setProvId(dto.getProvId());
            accnoInfoDetailVo.setTxnAmt(dto.getFrozenAmount());
            // 交易类型 0-开户 1-冻结 2-调账 6-挂失 7-修改密码 8-重置密码 9-销户 10-解冻 11-解挂 3-提现
            accnoInfoDetailVo.setTxnType(AccnoInfoDetailVo.CHANGE_PASS);
            accnoInfoDetailVo.setRemark(param.getRemark());
            accnoInfoDetailVo.setTransChnl(chanlNo);
            accnoInfoDetailVo.setSummary("修改密码");
            int resultDetail = memberShipInfoDao.addAccountInfoDetail(accnoInfoDetailVo);
            if (1 != resultDetail) {
                log.info("修改密码记录失败");
                return MumberShipUtil.getResultFailDto(CARD_CHANGEPASS_FAIL);
            }
        } catch (DataAccessException e) {
            log.error("修改密码记录失败", e);
            return Result.build(201, "修改密码失败");
        }

        log.info("修改密码传入数据:{}", param);

        // 新密码MD5
        try {
            String md5Pass = Md5Util.getMd5(param.getNewPassword());
            param.setPasswd(md5Pass);
        } catch (Exception e) {
            log.error("原密码MD5加密失败", e);
            return UserResUtil.buildFail(USER_PASSWD_ERROR);
        }

        try {
            int result = memberShipInfoDao.updatePass(param);
            if (1 != result) {
                return MumberShipUtil.getResultFailDto(CARD_CHANGEPASS_FAIL);
            }
        } catch (DataAccessException e) {
            log.error("修改密码记录失败", e);
            return Result.build(201, "修改密码失败");
        }
        return UserResUtil.getResultSuccessDto();
    }

    /**
     * 密码重置
     *
     * @param param 卡状态 0-有效 1-冻结 6-挂失 7-禁用 8-注销 9-冻结 A-黑名单
     */
    @Override
    public Result<ResultOutDto> resetCard(DISP20210181MemberShipInfoInDto param, String operator, String chanlNo) {
        log.info("修改重置参数： " + JSON.toJSONString(param));
        int result = 0;
        if (ObjectUtils.isEmpty(param)) {
            return UserResUtil.paramFail("输入参数为为空");
        }
        if (StringUtils.isEmpty(param.getNewPassword())) {
            return UserResUtil.paramFail("新密码为空");
        }
        if (StringUtils.isEmpty(param.getPhone())) {
            return UserResUtil.paramFail("手机号码为空");
        }
        if (StringUtils.isEmpty(param.getVerifyUsPaWd())) {
            return UserResUtil.paramFail("确认密码为空");
        }
        if (StringUtils.isEmpty(param.getRegCode())) {
            return UserResUtil.paramFail("验证码为空");
        }
        // 确认密码失败
        if (!param.getNewPassword().equals(param.getVerifyUsPaWd())) {
            return UserResUtil.buildFail(USER_VERIFY_PASSWD_FAIL);
        }

        String backRegCode = redisUtil.get(param.getPhone());
        if (StringUtils.isEmpty(backRegCode) || Integer.parseInt(param.getRegCode()) != Integer.parseInt(backRegCode)) {
            return UserResUtil.buildFail(USER_REG_CODE_ERROR);
        }
        // 密码重置记录表
        int resultDetail;
        try {
            DISP20210181MemberShipInfoInDto dto = memberShipInfoDao.queryCardByCardNo(param.getCardNo());
            String sndDtTm = MumberShipUtil.getTimeStamp();//发起方时间戳
            AccnoInfoDetailVo accnoInfoDetailVo = new AccnoInfoDetailVo();
            accnoInfoDetailVo.setCardNo(dto.getCardNo());
            accnoInfoDetailVo.setAccount(dto.getAccount());
            accnoInfoDetailVo.setID(sndDtTm);
            accnoInfoDetailVo.setTxnDt(new Date());
            accnoInfoDetailVo.setOperId(operator);
            accnoInfoDetailVo.setProvId(dto.getProvId());
            accnoInfoDetailVo.setTxnAmt(dto.getFrozenAmount());
            // 交易类型 0-开户 1-冻结 2-调账 6-挂失 7-修改密码 8-重置密码 9-销户 10-解冻 11-解挂 3-提现
            accnoInfoDetailVo.setTxnType(AccnoInfoDetailVo.RESET_PASS);
            accnoInfoDetailVo.setTransChnl(chanlNo);
            accnoInfoDetailVo.setSummary("密码重置");
            resultDetail = memberShipInfoDao.addAccountInfoDetail(accnoInfoDetailVo);
            if (1 != resultDetail) {
                log.info("密码重置记录失败");
                return MumberShipUtil.getResultFailDto(OPEN_ACCOUNT_FAIL);
            }
        } catch (DataAccessException e) {
            log.error("密码重置记录失败", e);
        }

        if(UserStatEnum.FARMING_SYS.getCode().equals(chanlNo)) {
            String pwdPasswd = PwdKeyboardUtil.genClearTextPwd(param.getNewPassword(), param.getCardNo());
            param.setNewPassword(pwdPasswd);
        }

        // 新密码MD5
        try {
            String md5Pass = Md5Util.getMd5(param.getNewPassword());
            param.setPasswd(md5Pass);
        } catch (Exception e) {
            log.error("新密码MD5加密失败", e);
            return UserResUtil.buildFail(USER_PASSWD_ERROR);
        }

        try {
            result = memberShipInfoDao.updatePass(param);
        } catch (Exception e1) {
            log.error("用户业务-重置密码失败", e1);
        }
        if (result != 1) {
            log.info("用户业务-重置密码失败");
            return UserResUtil.buildFail(USER_UPDATE_FAIL);
        }
        log.info("用户业务-重置密码成功");
        return UserResUtil.getResultSuccessDto();
    }

    /**
     * 调账申请
     *
     */
    @Override
    public Result<ResultOutDto> applyReconciliationByParam(AccnoInfoDetailVo findDepByParam, String operator, String chanlNo) {
        log.info("转账申请参数： " + JSON.toJSONString(findDepByParam));
        // 调账申请记录表
        int resultDetail;
        BigDecimal zero = new BigDecimal("0");
        int a = findDepByParam.getApplyAmount().compareTo(zero);
        if (a == 0) {
            log.info("传入的调账金额为0,不能调账");
            return MumberShipUtil.getResultFailDto(RECONCILIATION_IS_ZERO);
        } else if (a < 0) {
            log.info("传入的调账金额为负，不能调账");
            return MumberShipUtil.getResultFailDto(RECONCILIATION_IS_NEGATIVE);
        }

        try {
                /*
        账户金额相应减少，冻结金额增加
         */
            DISP20210181MemberShipInfoInDto shipInfoInDto = new DISP20210181MemberShipInfoInDto();
            shipInfoInDto.setAccount(findDepByParam.getAccount());
            MemberShipInfoOutDto customAccount = memberShipInfoDao.queryCustomAccountDetail(shipInfoInDto);
            BigDecimal acctBal = customAccount.getAcctBal();
            DISP20210181MemberShipInfoInDto dto = memberShipInfoDao.queryCardByCardNo(findDepByParam.getCardNo());
            String sndDtTm = MumberShipUtil.getTimeStamp();//发起方时间戳
            AccnoInfoDetailVo accnoInfoDetailVo = new AccnoInfoDetailVo();
            accnoInfoDetailVo.setCardNo(dto.getCardNo());
            accnoInfoDetailVo.setAccount(dto.getAccount());
            accnoInfoDetailVo.setBeforeAmt(acctBal);
            accnoInfoDetailVo.setAfterAmt(acctBal);
            accnoInfoDetailVo.setID(sndDtTm);
            accnoInfoDetailVo.setTxnDt(new Date());
            accnoInfoDetailVo.setOperId(operator);
            accnoInfoDetailVo.setProvId(dto.getProvId());
            accnoInfoDetailVo.setOperTp(findDepByParam.getOperTp());
            accnoInfoDetailVo.setTxnAmt(findDepByParam.getApplyAmount());
            //交易类型 0-开户 1-冻结 2-调账 6-挂失 7-修改密码 8-重置密码 9-销户 10-解冻 11-解挂 3-提现
            accnoInfoDetailVo.setTxnType(AccnoInfoDetailVo.RECONCILIATION);
            accnoInfoDetailVo.setRemark(findDepByParam.getRemark());
            accnoInfoDetailVo.setSummary("");
            accnoInfoDetailVo.setTransChnl(chanlNo);
            accnoInfoDetailVo.setStatus(AccnoInfoDetailVo.APPLY_RECONCILIATION);
            resultDetail = memberShipInfoDao.addAccountInfoDetail(accnoInfoDetailVo);
            if (1 != resultDetail) {
                log.info("调账申请记录失败");
                return MumberShipUtil.getResultFailDto(RECONCILIATION_APPLY_FAIL);
            }
        } catch (DataAccessException e) {
            log.error("调账申请记录失败", e);
        }
        log.info("调账申请成功");
        return UserResUtil.getResultSuccessDto();
    }

    /**
     * 调账申请查询
     *
     */
    @Override
    public Result<DISP20210188OutDto> queryReconciliationByParm(AccnoInfoDetailVo findDepByParam) {
        log.info("DISP20210188 调账记录查询-查询参数： " + findDepByParam);

        DISP20210188OutDto dto = new DISP20210188OutDto();
        // 设置调账记录
        findDepByParam.setTxnType(AccnoInfoDetailVo.RECONCILIATION);
        // 有分页参数才做分页查询
        if (findDepByParam.getPageNum() != null && findDepByParam.getPageNum() > 0 && findDepByParam.getPageSize() > 0) {
            Long pageNum = (findDepByParam.getPageNum() - 1) * findDepByParam.getPageSize();
            findDepByParam.setStartIndex(pageNum);

            try {
                int TolPageNum = memberShipInfoDao.countAccountInfoDetail(findDepByParam);
                if (TolPageNum > 0) {
                    dto.setTolPageNum(TolPageNum);
                    List<AccnoInfoDetailVo> accnoInfoDetailVoList = memberShipInfoDao.queryAccnoInfoDetailList(findDepByParam);
                    dto.setCusAccountInfoDetailList(accnoInfoDetailVoList);
                } else {
                    return Result.build(ResultCodeEnum.DATA_NO_ERROR.getCode(), ResultCodeEnum.DATA_NO_ERROR.getMessage());
                }
            } catch (Exception e) {
                log.error("查询客户处理账户条数异常", e);
            }
        } else {
            try {
                List<AccnoInfoDetailVo> arr = memberShipInfoDao.queryAccnoInfoDetailList(findDepByParam);
                dto.setCusAccountInfoDetailList(arr);
                log.debug(JSON.toJSONString(arr));
            } catch (Exception e) {
                log.error("调账申请查询异常", e);
            }
        }
        return Result.ok(dto);
    }

    /**
     * 调账申请处理
     *
     */
    @Override
    public Result<ResultOutDto> handleApplyReconcliByParam(AccnoInfoDetailVo findDepByParam, String operator, String chanlNo) {
        log.info("调账处理记录-传入参数： " + findDepByParam);
        DISP20210181MemberShipInfoInDto shipInfoInDto = new DISP20210181MemberShipInfoInDto();
        shipInfoInDto.setAccount(findDepByParam.getAccount());
        AccnoInfoVo customAccount = memberShipInfoDao.queryAccuInfoByAccount(shipInfoInDto);
        if (customAccount == null) {
            return Result.build(null, ResultCodeEnum.DATA_NO_ERROR);
        }
        BigDecimal acctBal = customAccount.getAcctBal();
        BigDecimal availBal = customAccount.getAvailBal();

        //判断是否复核通过
        if (Integer.parseInt(findDepByParam.getReviewStatus()) == 1) {
            /*
             * 调账改变状态
             */
            int tzApply;
            try {
                //改为已经调账状态
                findDepByParam.setStatus(AccnoInfoDetailVo.REVIEW_FAIL);
                findDepByParam.setSummary(findDepByParam.getRemark());
                findDepByParam.setBeforeAmt(shipInfoInDto.getAcctBal());
                findDepByParam.setAfterAmt(shipInfoInDto.getAcctBal());
                tzApply = memberShipInfoDao.updateAccountInfoDetalByParm(findDepByParam);
                if (1 != tzApply) {
                    return MumberShipUtil.getResultFailDto(RECONCILIATION_PROCESSING_FAIL);
                }
            } catch (DataAccessException e) {
                log.error("数据更新异常", e);
                return Result.build(201, "调账改变状态失败");
            }
            return UserResUtil.getResultSuccessDto();
        }

        //查询调账金额和用户可用余额对比
        AccnoInfoDetailVo accnoInfoDetailVo = new AccnoInfoDetailVo();
        accnoInfoDetailVo.setID(findDepByParam.getReId());
        AccnoInfoDetailVo accnoInfoDetail = memberShipInfoDao.queryAccnoInfoDetail(accnoInfoDetailVo);
        if (Integer.parseInt(accnoInfoDetail.getStatus()) == 2) {
            log.error("你的这笔交易已经调账，不能再次进行调账");
            return MumberShipUtil.getResultFailDto(RECONCILIATION_IS_USER);
        }

        int a = availBal.compareTo(accnoInfoDetail.getTxnAmt());
        BigDecimal applyAmount = accnoInfoDetail.getTxnAmt();
        if (Integer.parseInt(accnoInfoDetail.getOperTp()) == 1) {
            if (a < 0) {
                log.error("当前调账金额大于可用余额，不能调账");
                return MumberShipUtil.getResultFailDto(RECONCILIATION_IS_BEYONGD_AVAILBAL);
            }
        }

        if (Integer.parseInt(accnoInfoDetail.getOperTp()) == 0) {
            shipInfoInDto.setAcctBal(acctBal.add(applyAmount));
            shipInfoInDto.setAvailBal(availBal.add(applyAmount));
        } else {
            shipInfoInDto.setAcctBal(acctBal.subtract(applyAmount));
            shipInfoInDto.setAvailBal(availBal.subtract(applyAmount));
        }

        /*
         * 用户账号表里面修改账户余额，可用余额
         * String provId, String account, BigDecimal acctBal,
         *                                     BigDecimal availBal, BigDecimal freezeAmt
         */
        try {
            String mac = MacUtil.getMac(customAccount.getProvId(), customAccount.getAccount(), shipInfoInDto.getAcctBal(),
                    shipInfoInDto.getAvailBal(), customAccount.getFreezeAmt());
            if (StringUtils.isEmpty(mac)) {
                log.error("mac生成失败;");
                return Result.fail();
            }
            shipInfoInDto.setMac(mac);
            int Apply = memberShipInfoDao.updateReconciliationByProvId(shipInfoInDto);
            if (1 != Apply) {
                return MumberShipUtil.getResultFailDto(RECONCILIATION_PROCESSING_FAIL);
            }
        } catch (DataAccessException e) {
            log.error("数据更新异常", e);
            return Result.build(201, "调账改变状态失败");
        }

        /*
         * 调账改变状态
         */
        int tzApply;
        try {
            findDepByParam.setSummary(findDepByParam.getRemark());
            //改为已经调账状态
            findDepByParam.setStatus(AccnoInfoDetailVo.RECONCILED);
            findDepByParam.setBeforeAmt(acctBal);
            findDepByParam.setAfterAmt(shipInfoInDto.getAcctBal());
            tzApply = memberShipInfoDao.updateAccountInfoDetalByParm(findDepByParam);
            if (1 != tzApply) {
                return MumberShipUtil.getResultFailDto(RECONCILIATION_PROCESSING_FAIL);
            }
        } catch (DataAccessException e) {
            log.error("数据更新异常", e);
            return Result.build(201, "调账改变状态失败");
        }

        AccnoChangeDetailDto detailDto = new AccnoChangeDetailDto();
        detailDto.setJrnlNum(findDepByParam.getReId());
        detailDto.setCardNo(findDepByParam.getCardNo());
        detailDto.setProvId(findDepByParam.getProvId());
        detailDto.setProvNm(findDepByParam.getProvNm());
        detailDto.setTxnType(TxnTypeEnum.ADJUST.getTxnType());
        detailDto.setTransMd(TransMdEnum.CARD.getTransMDStatus());
        detailDto.setIncomeTp(accnoInfoDetail.getOperTp());
        //detailDto.setAcctBal();
        detailDto.setBeforeAmt(findDepByParam.getBeforeAmt());
        detailDto.setAfterAmt(findDepByParam.getAfterAmt());
        detailDto.setTxnAmt(applyAmount);
        detailDto.setTxnTm(new Date());
        detailDto.setSummary(TxnTypeEnum.ADJUST.getDesc());
        detailDto.setOperId(operator);

        int num = accnoBalChangeMapper.add(detailDto);
        if(num == 1) {
            log.error("调账功能, 添加账户明细出错:{}", JSON.toJSONString(detailDto));
        }

        return UserResUtil.getResultSuccessDto();
    }

    /**
     * 提现申请
     *
     * @param findDepByParam AccnoInfoDetailVo
     * @param operator 操作人
     * @param chanlNo 渠道号
     * @return Result
     */
    @Override
    public Result applyWithdrawByParam(AccnoInfoDetailVo findDepByParam, String operator, String chanlNo) {
        log.info("DISP20210190 提现申请记录-传入参数： " + findDepByParam);
        DISP20210181MemberShipInfoInDto shipInfoInDto = new DISP20210181MemberShipInfoInDto();
        shipInfoInDto.setAccount(findDepByParam.getAccount());
        MemberShipInfoOutDto customAccount = memberShipInfoDao.queryCustomAccountDetail(shipInfoInDto);

        try {
            // 判断是否是否实名
            TCustomInfoManagerVo vo = tCustomInfoManagerDao.selectByPrimaryKey(customAccount.getProvId());
            if (vo.getIsReal() == null || "".equals(vo.getIsReal())) {
                return UserResUtil.paramFail("用户没有实名，不能做提现申请！");
            }
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
        }

        BigDecimal acctBal = customAccount.getAcctBal();
        BigDecimal zero = new BigDecimal("0");
        int a = findDepByParam.getApplyAmount().compareTo(zero);
        if (a == 0) {
            log.info("传入的提现金额为0,不能提现");
            return MumberShipUtil.getResultFailDto(WITHDRAWAL_IS_ZERO);
        } else if (a < 0) {
            log.info("传入的提现金额为负，不能提现");
            return MumberShipUtil.getResultFailDto(WITHDRAWAL_IS_NEGATIVE);
        }

        // 调账申请记录表
        try {
            DISP20210181MemberShipInfoInDto dto = memberShipInfoDao.queryCardByCardNo(findDepByParam.getCardNo());
            // 发起方时间戳
            String sndDtTm = MumberShipUtil.getTimeStamp();

            AccnoInfoDetailVo accnoInfoDetailVo = new AccnoInfoDetailVo();
            accnoInfoDetailVo.setCardNo(dto.getCardNo());
            accnoInfoDetailVo.setAccount(dto.getAccount());
            accnoInfoDetailVo.setID(sndDtTm);
            accnoInfoDetailVo.setProvNm(dto.getProvNm());
            accnoInfoDetailVo.setBeforeAmt(acctBal);
            accnoInfoDetailVo.setAfterAmt(acctBal);
            accnoInfoDetailVo.setTxnDt(new Date());
            accnoInfoDetailVo.setOperId(operator);
            accnoInfoDetailVo.setProvId(dto.getProvId());
            accnoInfoDetailVo.setTxnAmt(findDepByParam.getApplyAmount());
            // 交易类型 0-开户 1-冻结 2-调账 6-挂失 7-修改密码 8-重置密码 9-销户 10-解冻 11-解挂 3-提现
            accnoInfoDetailVo.setTxnType(AccnoInfoDetailVo.WITHDRAW);
            accnoInfoDetailVo.setRemark(findDepByParam.getRemark());
            accnoInfoDetailVo.setSummary("");
            accnoInfoDetailVo.setTransChnl(chanlNo);
            accnoInfoDetailVo.setStatus(AccnoInfoDetailVo.APPLY_WITHDRAWAL);
            accnoInfoDetailVo.setOpenAccuName(findDepByParam.getOpenAccuName());
            accnoInfoDetailVo.setBankNo(findDepByParam.getBankNo());
            accnoInfoDetailVo.setBankPayNo(findDepByParam.getBankPayNo());
            accnoInfoDetailVo.setBankNo(findDepByParam.getBankNo());
            accnoInfoDetailVo.setOpenAccuName(findDepByParam.getOpenAccuName());
            // 渠道号
            accnoInfoDetailVo.setChanlNo(chanlNo);
            int resultDetail = memberShipInfoDao.addAccountInfoDetail(accnoInfoDetailVo);
            if (1 != resultDetail) {
                log.info("提现申请记录失败");
                return MumberShipUtil.getResultFailDto(OPEN_ACCOUNT_FAIL);
            }
        } catch (DataAccessException e) {
            log.error("提现申请客户数据库插入异常", e);
        }
        log.info("提现申请成功");
        return UserResUtil.getResultSuccessDto();
    }

    /**
     * 提现查询
     *
     */
    @Override
    public Result<DISP20210188OutDto> queryWithdrawByParam(AccnoInfoDetailVo findDepByParam) {
        log.info("DISP20210191 提现查询-查询参数： " + findDepByParam);

        DISP20210188OutDto dto = new DISP20210188OutDto();
        // 设置调账记录
        findDepByParam.setTxnType(AccnoInfoDetailVo.WITHDRAW);
        // 有分页参数才做分页查询
        if (findDepByParam.getPageNum() != null && findDepByParam.getPageNum() > 0 && findDepByParam.getPageSize() > 0) {
            Long pageNum = (findDepByParam.getPageNum() - 1) * findDepByParam.getPageSize();
            findDepByParam.setStartIndex(pageNum);

            try {
                int TolPageNum = memberShipInfoDao.countAccountInfoDetail(findDepByParam);
                if (TolPageNum > 0) {
                    dto.setTolPageNum(TolPageNum);
                    dto.setCusAccountInfoDetailList(memberShipInfoDao.queryAccnoInfoDetailList(findDepByParam));
                } else {
                    return Result.build(ResultCodeEnum.DATA_NO_ERROR.getCode(), ResultCodeEnum.DATA_NO_ERROR.getMessage());
                }
            } catch (Exception e) {
                log.error("提现查询", e);
                return Result.fail();
            }
        } else {
            try {
                List<AccnoInfoDetailVo> arr = memberShipInfoDao.queryAccnoInfoDetailList(findDepByParam);
                dto.setCusAccountInfoDetailList(arr);
                log.debug(JSON.toJSONString(arr));
            } catch (Exception e) {
                log.error("调账申请查询异常", e);
                return Result.fail();
            }
        }
        return Result.ok(dto);
    }

    /**
     * 提现复核
     *
     * @param findDepByParam 1-申请调账 2-已调账 3-申请提现 4-申请提现复核 5-已提现 6-调账复核不通过 7-提现申请不通过
     */
    @Override
    public Result<ResultOutDto> reviewAndHandleWithdrawByParam(AccnoInfoDetailVo findDepByParam) {
        log.info("提现复核参数： " + findDepByParam);
        try {
            // 查询调账金额和用户可用余额对比
            AccnoInfoDetailVo accnoInfoDetailVo = new AccnoInfoDetailVo();
            accnoInfoDetailVo.setID(findDepByParam.getReId());
            AccnoInfoDetailVo accnoInfoDetail = memberShipInfoDao.queryAccnoInfoDetail(accnoInfoDetailVo);
            if (Integer.parseInt(accnoInfoDetail.getStatus()) == 7) {
                log.info("你的这笔提现交易已经复核，不能再次进行复核");
                return MumberShipUtil.getResultFailDto(WITHDRAWALAPPLI_IS_USER);
            }
        } catch (DataAccessException e) {
            log.error("提现复核数据更新异常", e);
            return Result.build(201, "提现复核失败");
        }

        try {
            findDepByParam.setSummary(findDepByParam.getRemark());
            findDepByParam.setStatus(AccnoInfoDetailVo.WITHDRAWAL_FAIL);
            int result = memberShipInfoDao.reviewHandleWithdraw(findDepByParam);
            if (1 != result) {
                return MumberShipUtil.getResultFailDto(WITHDRAWAL_REVIEW_FAIL);
            }
        } catch (DataAccessException e) {
            log.error("提现复核数据更新异常", e);
            return Result.build(201, "提现复核失败");
        }
        return UserResUtil.getResultSuccessDto();
    }

    /**
     * 提现转账
     */
    @Override
    public Result<ResultOutDto> transferyParam(AccnoInfoDetailVo findDepByParam) {
        log.info("提现转账： " + findDepByParam);
        DISP20210181MemberShipInfoInDto shipInfoInDto = new DISP20210181MemberShipInfoInDto();
        shipInfoInDto.setAccount(findDepByParam.getAccount());
        MemberShipInfoOutDto customAccount = memberShipInfoDao.queryCustomAccountDetail(shipInfoInDto);

        try {
            findDepByParam.setStatus(AccnoInfoDetailVo.WITHDRAWN);
            int result = memberShipInfoDao.updateAccountInfoDetalByParm(findDepByParam);
            if (1 != result) {
                return MumberShipUtil.getResultFailDto(WITHDRAWAL_PROCESSING_FAIL);
            }
        } catch (DataAccessException e) {
            log.error("提现数据更新异常", e);
            return Result.build(201, "提现转账失败");
        }
        return UserResUtil.getResultSuccessDto();
    }

    /**
     * 一卡通密码校验
     *
     * @return Result
     * @author 黄贵川
     */
    @Override
    public Result memberShipInfoPasswdCheck(Request<MemberShipInfoVo> param) {
        log.debug("DISP20210314 一卡通密码校验：" + JSON.toJSONString(param));
        if (ObjectUtils.isEmpty(param)) {
            log.info("DISP20210314 输入参数为空");
            return Result.build(1, "输入参数为空");
        }

        if (ObjectUtils.isEmpty(param.getBody())) {
            log.info("DISP20210314 body为空");
            return Result.build(1, "输入参数为空");
        }

        if (StringUtils.isEmpty(param.getHead().getChanlNo())) {
            log.info("DISP20210314 渠道号为空");
            return Result.build(1, "渠道号为空");
        }

        /*
         * app:md5加密
         * pc：解密-->mde加密
         */
        try {
            MemberShipInfoVo memberShipInfoVo = param.getBody();
            String passwd = memberShipInfoVo.getPasswd();

            memberShipInfoVo.setPasswd("");
            memberShipInfoVo.setChanlNo("");
            MemberShipInfoVo entity = memberShipInfoDao.selectMemberShipInfoVo(memberShipInfoVo);
            if (entity == null) {
                log.info("DISP20210314 没有查询到一卡通信息");
                return Result.build(1, "没有查询到一卡通信息");
            }

            if (StringUtils.isEmpty(entity.getPasswd())){
                log.info("DISP20210314 此卡密码为空,请先设置密码!");
                return Result.build(201, "此卡密码为空,请先设置密码!");
            }

            String chanlNo = param.getHead().getChanlNo();
            String md5PassWd;
            // PC
            if (chanlNo.equals(UserStatEnum.FARMING_SYS.getCode())) {
                // 密码键盘解密
                String pwdPasswd = PwdKeyboardUtil.genClearTextPwd(passwd, memberShipInfoVo.getCardNo());
                log.info("密码键盘解密pwdPasswd=" + pwdPasswd);
                // md5加密
                md5PassWd = Md5Util.getMd5(pwdPasswd);
            } else {
                // app:md5加密
                // md5加密
                md5PassWd = Md5Util.getMd5(passwd);
            }

            if (md5PassWd.equals(entity.getPasswd())) {
                return Result.ok();
            }
        } catch (Exception e) {
            log.error("Md5Util加密异常", e);
            return Result.build(201, "密码校验失败,请确认输入人密码是否正确!");
        }

        log.info("DISP20210314密码校验失败");
        return Result.build(201, "密码校验失败,请确认输入人密码是否正确!");
    }

    /**
     * 提现校验
     *
     * @return Result
     * @author 黄贵川
     * @date 2021-09-18
     */
    @Override
    public Result withdrawCheck(Request<AccnoInfoVo> param) {
        AccnoInfoVo body = param.getBody();
        log.debug("DISP20210339 提现校验body：" + JSON.toJSONString(body));
        try {
            DISP20210184CusAccountDto dto = new DISP20210184CusAccountDto();
            dto.setAccount(body.getAccount());
            AccnoInfoVo accnoInfoVo = customAccountDao.queryCustomAccountDetail(dto);
            // 账户余额
            BigDecimal availBal = accnoInfoVo.getAvailBal();

            // 提现金额
            BigDecimal applyAmount = body.getApplyAmount();
            JSONObject jsonObject = new JSONObject(3);
            jsonObject.put("applyAmount", applyAmount);
            jsonObject.put("availBal", availBal);

            int a = applyAmount.compareTo(availBal);
            if (1 == a) {
                jsonObject.put("rateAmount", "0.00");
                return Result.build(jsonObject, 201, "提现金额大于可用余额!");
            }

            // t_custom_withdraw_tmp
            CustomWithdrawTmpVo customWithdrawTmpVo = customWithdrawTmpDao.findByProvId(body.getProvId());
            // 已提现额度
            BigDecimal customAdd = new BigDecimal(0);
            if(customWithdrawTmpVo != null){
                customAdd = customWithdrawTmpVo.getAccruAmt();
            }

            String quotaString = "";
            String rateString = "";
            /*
             * t_parmeter_info ParmeterInfo 参数
             * 08	withdrawAmt 累计提现金额
             * 08	withdrawRate 提现费率
             */
            List<ParmeterInfo> parmeterInfoList = memberShipInfoDao.findParmeterInfo();
            if (parmeterInfoList != null && parmeterInfoList.size() > 0) {
                for (ParmeterInfo parmeterInfo : parmeterInfoList) {
                    if ("withdrawAmt".equals(parmeterInfo.getParamNm())) {
                        quotaString = parmeterInfo.getParamVal();
                    } else {
                        rateString = parmeterInfo.getParamVal();
                    }
                }
            }

            // 额度
            BigDecimal quota = new BigDecimal("0");
            if (!StringUtils.isEmpty(quotaString)) {
                quota = new BigDecimal(quotaString);
            }

            // 费率
            BigDecimal rate = new BigDecimal("0");
            if (!StringUtils.isEmpty(rateString)) {
                rate = new BigDecimal(rateString);
            }

            BigDecimal zero = new BigDecimal("0");
            // 需要算手续费的提现金额=提现金额+累加提现金额-提现额度
            BigDecimal quotaAmount = (applyAmount.add(customAdd)).subtract(quota);
            int i = quotaAmount.compareTo(zero);
            if (i <= 0 ) {
                jsonObject.put("rateAmount", "0.00");
                // 需要算手续费的提现金额<=0
                return Result.build(jsonObject, ResultCodeEnum.SUCCESS);
            }

            // 手续费=需要算手续费的提现金额*手续费率
            BigDecimal rateAmount = quotaAmount.multiply(rate);
            // 保留小数点后两位
            DecimalFormat df = new DecimalFormat("#0.00");
            String format = df.format(rateAmount);
            rateAmount = new BigDecimal(format);

            jsonObject.put("rateAmount", rateAmount);
            /*
             * 提现金额+手续费>账户可用余额
             */
            if ((applyAmount.add(rateAmount)).compareTo(availBal) > 0) {
                return Result.build(jsonObject, 201,
                        "提现金额(元)" + applyAmount + "+手续费(元)" + rateAmount + ">可用余额(元)" + availBal);
            }

            return Result.build(jsonObject, ResultCodeEnum.SUCCESS);
        } catch (Exception e) {
            log.error("DISP20210339 提现校验异常", e);
            return Result.build(201, "提现校验失败");
        }
    }

    /**
     * 现金提现
     *
     * @param param AccnoInfoDetailVo
     * @return Result
     * @author 黄贵川
     * @date 2021-11-10

    @Override
    public Result cashWithdraw(Request<AccnoInfoDetailVo> param) {
        AccnoInfoDetailVo body = param.getBody();
        log.info("DISP20210364 现金提现body= " + body);

        DISP20210181MemberShipInfoInDto shipInfoInDto = new DISP20210181MemberShipInfoInDto();
        shipInfoInDto.setAccount(body.getAccount());
        MemberShipInfoOutDto customAccount = memberShipInfoDao.queryCustomAccountDetail(shipInfoInDto);
        try {
            // 判断是否是否实名
            TCustomInfoManagerVo vo = tCustomInfoManagerDao.selectByPrimaryKey(customAccount.getProvId());
            if (vo.getIsReal() == null || "".equals(vo.getIsReal())) {
                return UserResUtil.paramFail("用户没有实名，不能做提现申请！");
            }
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
        }

        // 账户余额
        BigDecimal acctBal = customAccount.getAcctBal();
        // 可用余额
        BigDecimal availBal = customAccount.getAvailBal();
        // 提现金额
        BigDecimal applyAmount = body.getApplyAmount();

        BigDecimal zero = new BigDecimal("0");
        int a = applyAmount.compareTo(zero);
        if (a == 0) {
            log.info("传入的提现金额为0,不能提现");
            return MumberShipUtil.getResultFailDto(WITHDRAWAL_IS_ZERO);
        } else if (a < 0) {
            log.info("传入的提现金额为负，不能提现");
            return MumberShipUtil.getResultFailDto(WITHDRAWAL_IS_NEGATIVE);
        }

        // 手续费
        BigDecimal charges = body.getCharges();
        // 提现金额+手续费
        BigDecimal c = applyAmount.add(charges);
        int b = availBal.compareTo(c);
        if (b == -1){
            // -1 availBal < c       0 availBal=c      1 availBal>c
            // 提现金额加手续费大于可用余额
            return MumberShipUtil.getResultFailDto(CASH_WITHDRAW_ERROR);
        }

        // 调账申请记录表
        try {
            DISP20210181MemberShipInfoInDto dto = memberShipInfoDao.queryCardByCardNo(body.getCardNo());

            AccnoInfoDetailVo accnoInfoDetailVo = new AccnoInfoDetailVo();
            accnoInfoDetailVo.setCardNo(dto.getCardNo());
            String account = body.getAccount();
            accnoInfoDetailVo.setAccount(account);
            // 发起方时间戳
            String sndDtTm = MumberShipUtil.getTimeStamp();
            accnoInfoDetailVo.setID(sndDtTm);
            accnoInfoDetailVo.setProvNm(dto.getProvNm());
            accnoInfoDetailVo.setBeforeAmt(acctBal);
            accnoInfoDetailVo.setAfterAmt(acctBal.subtract(c));
            accnoInfoDetailVo.setTxnDt(new Date());
            accnoInfoDetailVo.setOperId(param.getHead().getOperator());
            accnoInfoDetailVo.setProvId(dto.getProvId());
            accnoInfoDetailVo.setTxnAmt(c);
            // 交易类型 0-开户 1-冻结 2-调账 6-挂失 7-修改密码 8-重置密码 9-销户 10-解冻 11-解挂 3-提现 12现金提现
            accnoInfoDetailVo.setTxnType(AccnoInfoDetailVo.CASH_WITHDRAW);
            accnoInfoDetailVo.setRemark(body.getRemark());
            accnoInfoDetailVo.setSummary("");
            accnoInfoDetailVo.setTransChnl(param.getHead().getChanlNo());
            accnoInfoDetailVo.setStatus(AccnoInfoDetailVo.WITHDRAWN);
            accnoInfoDetailVo.setOpenAccuName("");
            accnoInfoDetailVo.setBankNo("");
            accnoInfoDetailVo.setBankPayNo("");
            accnoInfoDetailVo.setBankNo("");
            accnoInfoDetailVo.setOpenAccuName("");
            // 渠道号
            accnoInfoDetailVo.setChanlNo(param.getHead().getChanlNo());
            int resultDetail = memberShipInfoDao.addAccountInfoDetail(accnoInfoDetailVo);
            if (1 != resultDetail) {
                log.info("保存现金提现申请记录失败");
                return MumberShipUtil.getResultFailDto(SAVE_CASH_WITHDRAW_ERROR);
            }

            String provId = customAccount.getProvId();
            String oldMac = MacUtil.getMac(provId, account, acctBal, availBal, customAccount.getFreezeAmt());

            // 可用余额=可用余额-提现金额-手续费
            BigDecimal newAvailBal = availBal.subtract(c);
            dto.setAvailBal(newAvailBal);

            String mac = MacUtil.getMac(provId, account, acctBal, newAvailBal, customAccount.getFreezeAmt());
            if (StringUtils.isEmpty(mac)) {
                log.error("mac生成失败;");
                return Result.fail();
            }

            dto.setMac(mac);
            dto.setOldmac(oldMac);
            // 更新金额
            int unfreeze = memberShipInfoDao.unfreezeAccountByProvId(dto);
            if (1 != unfreeze) {
                return MumberShipUtil.getResultFailDto(UPDATE_ACCNO_INFO_ERROR);
            }
        } catch (DataAccessException e) {
            log.error("提现申请客户数据库插入异常", e);
        }

        log.info("现金提现成功");
        return UserResUtil.getResultSuccessDto();
    }
     */
}
