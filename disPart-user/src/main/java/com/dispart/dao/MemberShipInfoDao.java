package com.dispart.dao;

import com.dispart.dto.ResultOutDto;
import com.dispart.model.ParmeterInfo;
import com.dispart.model.businessCommon.TAccnoInfo;
import com.dispart.parmeterdto.DISP20210181MemberShipInfoInDto;
import com.dispart.parmeterdto.DISP20210184CusAccountDto;
import com.dispart.parmeterdto.DISP20210197OutDto;
import com.dispart.parmeterdto.MemberShipInfoOutDto;
import com.dispart.result.Result;
import com.dispart.vo.AccnoInfoDetailVo;
import com.dispart.vo.AccnoInfoVo;
import com.dispart.vo.CustomInfoManagerVo;
import com.dispart.vo.MumberShipInfoVo;
import com.dispart.vo.basevo.TCardInfoVo;
import com.dispart.vo.user.MemberShipInfoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author:xiejin
 * @date:Created in 2021/8/11 16:07
 * @description
 * @modified by:
 * @version: 1.0
 */
@Mapper
public interface MemberShipInfoDao {
    /**
     * 开户
     *
     * @param mbin
     * @return Integer
     */
    public Integer openAccountInfo(DISP20210181MemberShipInfoInDto mbin);

    /**
     * 插入客户账户信息
     *
     * @param mbin
     * @return Integer
     */
    public Integer insertTAccnoInfo(TAccnoInfo mbin);

    /**
     * 查询输入的实体卡是否可用
     *
     * @param mbin
     * @return Integer
     */
    public TCardInfoVo selectCardInfoIsUser(TCardInfoVo mbin);

    /**
     * 销户，冻结，解冻卡
     *
     * @param mbin
     * @return Integer
     */
    public Integer cancelllationAccount(DISP20210181MemberShipInfoInDto mbin);

    /**
     * 销户，冻结，解冻卡
     *
     * @param mbin
     * @return Integer
     */
    public Integer frozenAccountByProvId(DISP20210181MemberShipInfoInDto mbin);

    /**
     * 解冻金额
     *
     * @param mbin DISP20210181MemberShipInfoInDto
     * @return Integer
     */
    public Integer unfreezeAccountByProvId(DISP20210181MemberShipInfoInDto mbin);

    /**
     * 销户，冻结，解冻卡
     *
     * @param mbin
     * @return Integer
     */
    public Integer updateReconciliationByProvId(DISP20210181MemberShipInfoInDto mbin);

    /**
     * 查询用户余额，可用余额，冻结金额
     *
     * @param mbin
     * @return Integer
     */
    public MemberShipInfoOutDto queryCustomAccountDetail(DISP20210181MemberShipInfoInDto mbin);

    /**
     * 查询用户余额，可用余额，冻结金额
     *
     * @param mbin
     * @return Integer
     */
    public AccnoInfoVo queryAccuInfoByAccount(DISP20210181MemberShipInfoInDto mbin);

    /**
     * 查询客户账户条数
     */
    public Integer countCardAccount(DISP20210181MemberShipInfoInDto into);

    /**
     * 查询用户余额，可用余额，冻结金额
     *
     * @param mbin
     * @return Integer
     */
    public List<MumberShipInfoVo> queryCardListDetail(DISP20210181MemberShipInfoInDto mbin);

    /**
     * 设置密码，重置密码
     *
     * @param mbin
     * @return Integer
     */
    public Integer updatePass(DISP20210181MemberShipInfoInDto mbin);

    /**
     * 查询卡片详情
     *
     * @param cardNO
     * @return DISP20210181MemberShipInfoInDto
     */
    public DISP20210181MemberShipInfoInDto queryCardByCardNo(String cardNO);

    /**
     * 查询根据证件号和名称电话查询客户id
     *
     * @param MemberShipInfoInDto
     * @return DISP20210181MemberShipInfoInDto
     */
    public CustomInfoManagerVo queryProvIDByCertNum(DISP20210181MemberShipInfoInDto MemberShipInfoInDto);

    /**
     * 查询卡片详情
     *
     * @param provId
     * @return DISP20210181MemberShipInfoInDto
     */
    public DISP20210181MemberShipInfoInDto queryCardByProvId(String provId);

    /**
     * @param findDepByParam
     * @return
     * @PostMapping("/DISP20210187")
     * @ApiOperation(value = "调账申请")
     * addAccountInfoDetail,updateAccountInfoDetalByParm,countCustomAccount,countAccountInfoDetail,queryAccnoInfoDetailList
     */
    int addAccountInfoDetail(AccnoInfoDetailVo findDepByParam);

    /**
     * @param findDepByParam
     * @return
     * @PostMapping("/DISP20210188")
     * @ApiOperation(value = "调账申请查询")
     */
    List<AccnoInfoDetailVo> queryAccnoInfoDetailList(AccnoInfoDetailVo findDepByParam);

    /**
     * 查询客户处理账户条数
     */
    int countAccountInfoDetail(AccnoInfoDetailVo into);

    /**
     * @param findDepByParam
     * @return
     * @PostMapping("/DISP20210189")
     * @ApiOperation(value = "调账申请处理更新数据")
     */
    Integer updateAccountInfoDetalByParm(AccnoInfoDetailVo findDepByParam);

    /**
     * @param findDepByParam
     * @return
     * @PostMapping("/DISP20210189")
     * @ApiOperation(value = "提现申请不通过")
     */
    Integer reviewHandleWithdraw(AccnoInfoDetailVo findDepByParam);


    /**
     * @param findDepByParam
     * @return
     * @PostMapping("/DISP20210189")
     * @ApiOperation(value = "调账申请处理")
     */
    Integer handleApplyReconcliByParam(AccnoInfoDetailVo findDepByParam);

    /**
     * @param findDepByParam
     * @return
     * @PostMapping("/DISP20210189")
     * @ApiOperation(value = "提现申请处理")
     */
    Result<ResultOutDto> transferyParam(AccnoInfoDetailVo findDepByParam);

    /**
     * 生成账户终端规则
     *
     * @return
     */
    Integer selectAccnoId();

    /**
     * 生成虚拟卡终端规则
     *
     * @return Integer 自动增加1
     */
    Integer selectVirtualCardId();

    /**
     * 插入虚拟卡
     *
     * @returnCard
     */
    int insertVirtualCard(TCardInfoVo record);

    /**
     * 卡片使用后更改卡状态 0-未使用 1-已使用 2-申请中 3-已注销 4-已取消
     *
     * @returnCard
     */
    Integer updateCardStatusByCardNo(TCardInfoVo tCardInfoVo);

    /**
     * 根据卡好查询调账
     *
     * @returnCard
     */
    AccnoInfoDetailVo queryAccnoInfoDetail(AccnoInfoDetailVo accnoInfoDetailVo);

    /**
     * 查询 会员信息（一卡通）
     *
     * @param memberShipInfoVo MemberShipInfoVo
     * @return MemberShipInfoVo
     * @author 黄贵川
     */
    MemberShipInfoVo selectMemberShipInfoVo(MemberShipInfoVo memberShipInfoVo);

    /**
     * 查询 提现优惠参数
     *
     * @author 黄贵川
     * @date 2021-09-22
     * @return List<ParmeterInfo>
     */
    List<ParmeterInfo> findParmeterInfo();
}
