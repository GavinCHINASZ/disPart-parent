package com.dispart.service.impl.discounts;

import com.alibaba.fastjson.JSONObject;
import com.dispart.dao.mapper.DiscountsActivityMapper;
import com.dispart.dao.mapper.DiscountsDetailMapper;
import com.dispart.dao.mapper.DiscountsUserMapper;
import com.dispart.dto.base.DiscountsActivityDto;
import com.dispart.model.CustomInfoManager;
import com.dispart.request.Request;
import com.dispart.result.EntranceResult_WEnum;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.discounts.DiscountsActivityService;
import com.dispart.vo.base.DiscountsActivityVo;
import com.dispart.vo.base.DiscountsDetailVo;
import com.dispart.vo.base.DiscountsUserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 优惠活动,优惠活动详情
 *
 * @author 黄贵川
 * @version 1.0.0
 * @date 2021-09-06
 */
@Service
@Slf4j
public class DiscountsActivityServiceImpl implements DiscountsActivityService {
    @Resource
    private DiscountsActivityMapper discountsActivityMapper;
    @Resource
    private DiscountsDetailMapper discountsDetailMapper;
    @Resource
    private DiscountsUserMapper discountsUserMapper;

    /**
     * 优惠活动 新增
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param Request<DiscountsActivityVo>
     * @return Result
     */
    @Override
    public Result insertDiscountsActivity(Request<DiscountsActivityVo> param) {
        DiscountsActivityVo body = param.getBody();
        log.info("DISP20210318 优惠活动 新增 body:" + body );
        try {
            int insertNum = discountsActivityMapper.insertDiscountsActivity(body);
            if (insertNum != 1){
                return Result.fail();
            }
        }catch (Exception e){
            log.error("DISP20210318 优惠活动 新增 异常", e);
            return Result.fail();
        }
        return Result.ok();
    }

    /**
     * 优惠活动 查询
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param Request<DiscountsActivityVo>
     * @return Result
     */
    @Override
    public Result selectDiscountsActivity(Request<DiscountsActivityVo> param) {
        DiscountsActivityVo body = param.getBody();
        log.info("DISP20210319 优惠活动 查询 body:" + body );
        // 有分页参数才做分页查询
        if (body.getCurPage() != null && body.getCurPage() > 0 && body.getPageSize() > 0) {
            Integer curPage = (body.getCurPage() - 1) * body.getPageSize();
            body.setCurPage(curPage);
        } else {
            body.setPageSize(0);
        }

        DiscountsActivityDto discountsActivityDto = new DiscountsActivityDto();
        try {
            List<DiscountsActivityVo> discountsActivityList = discountsActivityMapper.selectDiscountsActivityList(body);
            if (discountsActivityList == null || discountsActivityList.size() <= 0) {
                return Result.build(ResultCodeEnum.DATA_NO_ERROR.getCode(), ResultCodeEnum.DATA_NO_ERROR.getMessage());
            }
            List<DiscountsDetailVo> detailVoList = discountsDetailMapper.findByDiscountsActivityList(discountsActivityList);
            if (detailVoList != null && detailVoList.size() > 0){
                for (DiscountsActivityVo discountsActivityVo : discountsActivityList) {
                    List<DiscountsDetailVo> discountsDetailVoList = new ArrayList<>();
                    for (DiscountsDetailVo discountsDetailVo : detailVoList) {
                        if(discountsDetailVo.getActId().equals(discountsActivityVo.getActId())){
                            discountsDetailVoList.add(discountsDetailVo);
                        }
                    }

                    discountsActivityVo.setDiscountsDetailVoList(discountsDetailVoList);
                }
            }

            discountsActivityDto.setList(discountsActivityList);

            if (body.getPageSize() > 0) {
                Integer toleNum = discountsActivityMapper.selectDiscountsActivityCount(body);
                discountsActivityDto.setTolPageNum(toleNum);
            }
        }catch (DataAccessException e) {
            log.error("DISP20210319 优惠活动 查询异常", e);
            return Result.build(ResultCodeEnum.DATA_NO_ERROR.getCode(), ResultCodeEnum.DATA_NO_ERROR.getMessage());
        }

        return Result.build(discountsActivityDto, EntranceResult_WEnum.SUCCESS);
    }

    /**
     * 优惠活动 删除
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param Request<DiscountsActivityVo>
     * @return Result
     */
    @Override
    public Result deleteDiscountsActivity(Request<DiscountsActivityVo> param) {
        DiscountsActivityVo body = param.getBody();
        log.info("DISP20210320 优惠活动 删除 body:" + body );
        try {
            int deleteNum = discountsActivityMapper.deleteDiscountsActivity(body);
            if (deleteNum != 1){
                return Result.fail();
            }
            DiscountsDetailVo discountsDetailVo = new DiscountsDetailVo();
            discountsDetailVo.setActId(body.getActId());
            discountsDetailMapper.deleteDiscountsDetail(discountsDetailVo);
        }catch (Exception e){
            log.error("优惠活动 删除失败", e);
            return Result.fail();
        }
        return Result.ok();
    }

    /**
     * 优惠活动 修改
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param Request<DiscountsActivityVo>
     * @return Result
     */
    @Override
    public Result updateDiscountsActivity(Request<DiscountsActivityVo> param) {
        DiscountsActivityVo body = param.getBody();
        log.info("DISP20210321 优惠活动 修改 body:" + body );
        try {
            if (body.getStatus() != null && "1".equals(body.getStatus())){
                DiscountsActivityVo entity = discountsActivityMapper.findDiscountsActivityByActID(body.getActId());
                if(entity.getDiscntPrd() == null || entity.getDiscntNum() == null){
                    return Result.build(201,"请先设置优惠规则!");
                }

                if(entity.getEndDt() == null || entity.getEndDt().getTime() < new Date().getTime()){
                    return Result.build(201,"该活动已过期不能使用!");
                }

                // 查询是否已有启用的活动
                int statusCount = discountsActivityMapper.findStatusCount(body.getStatus());
                if(statusCount > 0){
                    return Result.build(201,"已有优惠活动已启用！");
                }
            }

            int deleteNum = discountsActivityMapper.updateDiscountsActivity(body);
            if (deleteNum != 1){
                return Result.fail();
            }
        }catch (Exception e){
            log.error("优惠活动 修改失败", e);
            return Result.fail();
        }
        return Result.ok();
    }

    /**
     * 优惠活动详情 新增
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param Request<DiscountsDetailVo>
     * @return Result
     */
    @Override
    public Result insertDiscountsDetail(Request<DiscountsDetailVo> param) {
        DiscountsDetailVo body = param.getBody();
        log.info("DISP20210322优惠活动详情 新增body:" + body );
        try {
            int insertNum = discountsDetailMapper.insertDiscountsDetail(body);
            if (insertNum != 1){
                return Result.fail();
            }
        }catch (Exception e){
            log.error("DISP20210322优惠活动详情 新增异常", e);
            return Result.fail();
        }
        return Result.ok();
    }

    /**
     * 优惠活动详情 删除
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param Request<DiscountsDetailVo>
     * @return Result
     */
    @Override
    public Result deleteDiscountsDetail(Request<DiscountsDetailVo> param) {
        DiscountsDetailVo body = param.getBody();
        log.info("DISP20210323优惠活动详情 删除body:" + body );
        try {
            int insertNum = discountsDetailMapper.deleteDiscountsDetail(body);
            if (insertNum != 1){
                return Result.fail();
            }
        }catch (Exception e){
            log.error("DISP20210323优惠活动详情 删除异常", e);
            return Result.fail();
        }
        return Result.ok();
    }

    /**
     * 查询优惠金额
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param Request<DiscountsDetailVo>
     * @return Result
     */
    @Override
    public Result selectDiscountsDetailPrice(Request<DiscountsDetailVo> param) {
        JSONObject json = new JSONObject(2);
        json.put("preferPrice", 0.00);

        DiscountsDetailVo body = param.getBody();
        String provId = body.getProvId();

        try {
            // 查询客户是否为供应商 供应商没有优惠
            CustomInfoManager customInfoManager = discountsActivityMapper.findCustomInfoManagerByProvId(provId);
            if (customInfoManager != null && StringUtils.isNotEmpty(customInfoManager.getQrcodeUrl())){
                log.info("查询客户是供应商 供应商没有优惠");
                return Result.build(json, ResultCodeEnum.SUCCESS);
            }
        }catch (Exception e){
            log.error("查询客户是供应商异常", e);
            return Result.build(json, ResultCodeEnum.SUCCESS);
        }

        try {
            log.info("DISP20210324查询优惠金额body:" + body );
            // 查找是否有优惠活动　日期和状态
            DiscountsActivityVo activityVo = discountsActivityMapper.findDiscountsActivityOpen();
            if (activityVo == null){
                log.info("DISP20210324查找是否有优惠活动 没有符合人优惠活动");
                return Result.build(json, ResultCodeEnum.SUCCESS);
            }

            // 查找该用户在当天使用过
            int userNum = discountsUserMapper.findDiscountsUserNow(provId);
            if (userNum > 0){
                log.info("DISP20210324查找该用户在当天使用过");
                return Result.build(json, ResultCodeEnum.SUCCESS);
            }

            // 查找该用户在周期内是否使用满次数
            Integer actId = activityVo.getActId();
            body.setActId(actId);
            int useNum = discountsUserMapper.selectUseNum(body);
            if (useNum >= activityVo.getDiscntNum()){
                log.info("DISP20210324查找该用户在周期内是否使用满次数");
                return Result.build(json, ResultCodeEnum.SUCCESS);
            }

            // 判断活动金额是否已经用完
            // 活动优惠总金额
            String discountsAmtStr = discountsActivityMapper.findDiscountsAmt();
            if(discountsAmtStr == null) discountsAmtStr = "0";
            BigDecimal discountsAmt = new BigDecimal(discountsAmtStr);

            // 查询优惠活动的金额总用了多少
            BigDecimal discntAmt = discountsUserMapper.findDiscntAmt(activityVo.getActId());
            if(discntAmt == null) discntAmt = BigDecimal.ZERO;
            // 查询优惠金额
            BigDecimal preferPrice = discountsDetailMapper.selectDiscountsDetailPrice(body);
            log.info("DISP20210324查询优惠金额成功");

            if (discountsAmt.compareTo(discntAmt.add(preferPrice)) == -1){
                log.warn("剩余优惠金额不足");
                return Result.build(json, ResultCodeEnum.SUCCESS);
            }

            json.put("actId", actId);
            json.put("preferPrice", preferPrice);
            return Result.build(json, ResultCodeEnum.SUCCESS);
        }catch (Exception e){
            log.error("DISP20210324查询优惠金额异常", e);
            return Result.build(json, ResultCodeEnum.SUCCESS);
        }
    }

    /**
     * 保存用户参加优惠活动记录
     *
     * @author 黄贵川
     * @date 2021-09-08
     * @param param Request<ParmeterSelectInVo>
     * @return Result
     */
    @Override
    public Result insertDiscountsUser(Request<DiscountsUserVo> param) {
        DiscountsUserVo body = param.getBody();
        log.info("DISP20210325保存客户参加优惠活动记录body:" + body );
        try{
            int insertNum = discountsUserMapper.insertDiscountsUser(body);
            if (insertNum != 1){
                return Result.fail();
            }
        }catch (Exception e){
            log.error("保存客户参加优惠活动记录失败", e);
            return Result.fail();
        }

        return Result.ok();
    }
}
