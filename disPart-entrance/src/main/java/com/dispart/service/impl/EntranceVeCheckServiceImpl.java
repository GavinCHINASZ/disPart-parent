package com.dispart.service.impl;

import com.dispart.dao.TProductInventoryInfoMapper;
import com.dispart.dao.mapper.TVechicleProcurerDetailsMapper;
import com.dispart.dao.mapper.TVechicleProcurerMapper;
import com.dispart.dto.ResultOutDto;
import com.dispart.dto.entrance.*;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.EntranceVeCheckService;
import com.dispart.vo.entrance.TVechicleProcurer;
import com.dispart.vo.entrance.TVechicleProcurerDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.dispart.result.ResultCodeEnum.SUCCESS;

@Service
@Slf4j
public class EntranceVeCheckServiceImpl implements EntranceVeCheckService {

    @Resource
    TVechicleProcurerMapper tVechicleProcurerMapper;
    @Resource
    TVechicleProcurerDetailsMapper tVechicleProcurerDetailsMapper;
    @Resource
    TProductInventoryInfoMapper tProductInventoryInfoMapper;

    /**
     * 查询进场货物信息
     *
     * @param param
     * @return
     */
    @Override
    public Result<QuryEntranceVeCheckOutDto> quryVeCheckInfo(Request<QuryEntranceVeCheckInDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(1, "输入参数为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(1, "输入参数为空");
        }
        if (StringUtils.isEmpty(param.getHead().getChanlNo())) {
            return Result.build(1, "渠道号为空");
        }
        QuryEntranceVeCheckInDto inDto = param.getBody();

        if(StringUtils.isEmpty(inDto.getInId())) {
            if (inDto.getPageNum() == null || inDto.getPageSize() == null) {
                log.info("进出场业务-分页参数不能为空");
                return Result.build(1, "分页参数为空");
            }
            if (inDto.getPageSize() <= 0) {
                log.info("进出场业务-分页条数输入错误");
                return Result.build(1, "分页条数输入错误");
            }
            if (inDto.getPageNum() <= 0) {
                log.info("进出场业务-分页页数输入错误");
                return Result.build(1, "分页页数输入错误");
            }

            inDto.setChanlNo(param.getHead().getChanlNo());
            int strNum = (inDto.getPageNum() - 1) * inDto.getPageSize();
            inDto.setStrNum(strNum);//起始记录数
            int tolNum = 0;//总记录数
            QuryEntranceVeCheckOutDto outBody = new QuryEntranceVeCheckOutDto();

            try {
                tolNum = tVechicleProcurerMapper.selectWhereCount(inDto);
            } catch (DataAccessException e) {
                log.error("数据库查询异常", e);
                throw new RuntimeException("数据库查询异常");
            }
            outBody.setTolPageNum(tolNum);//总条数
            if (tolNum <= 0) {
                log.info("进出场业务-查询记录数为0");
                return Result.build(outBody, SUCCESS);
            }
            List<QuryEntranceCheckParamOutDto> paramList = null;
            try {
                paramList = tVechicleProcurerMapper.selectWhere(inDto);
            } catch (DataAccessException e) {
                log.error("数据库查询异常", e);
                throw new RuntimeException("数据库查询异常");
            }
            log.info("进出场业务-查询进场货物信息成功");
            outBody.setList(paramList);
            return Result.build(outBody, SUCCESS);
        }else{//查询详情
            int tolNum = 0;//总记录数
            QuryEntranceVeCheckOutDto outBody = new QuryEntranceVeCheckOutDto();

            try {
                tolNum = tVechicleProcurerMapper.selectByInIdCount(inDto);
            } catch (DataAccessException e) {
                log.error("数据库查询异常", e);
                throw new RuntimeException("数据库查询异常");
            }
            outBody.setTolPageNum(tolNum);//总条数
            if (tolNum <= 0) {
                log.info("进出场业务-查询记录数为0");
                return Result.build(outBody, SUCCESS);
            }
            List<QuryEntranceCheckParamOutDto> paramList = null;
            try {
                paramList = tVechicleProcurerMapper.selectWhereByInId(inDto);
            } catch (DataAccessException e) {
                log.error("数据库查询异常", e);
                throw new RuntimeException("数据库查询异常");
            }
            log.info("进出场业务-查询进场货物信息成功");
            outBody.setList(paramList);
            return Result.build(outBody, SUCCESS);
        }

    }

    /**
     * 进场货物核验
     *
     * @param param
     * @return
     */
    @Override
    @Transactional
    public Result<ResultOutDto> upDateVeCheckInfo(Request<UpdateEntranceVeCheckInDto> param) {

        if (ObjectUtils.isEmpty(param)) {
            return Result.build(1, "输入参数为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(1, "输入参数为空");
        }
        UpdateEntranceVeCheckInDto inDto = param.getBody();
        if (StringUtils.isEmpty(inDto.getInId())) {
            log.info("进出场业务-进场id为空");
            return Result.build(1, "进场id为空");
        }
        if (StringUtils.isEmpty(inDto.getProvId())) {
            log.info("进出场业务-客户id为空");
            return Result.build(1, "客户id为空");
        }
        if (StringUtils.isEmpty(inDto.getIsReturn())) {
            log.info("进出场业务-是否退费标志为空");
            return Result.build(1, "是否退费标志为空");
        }
        if (param.getBody().getActAmt() == null) {
            log.info("进出场业务-收费金额为空");
            return Result.build(1, "收费金额为空");
        }
        //查询进场信息表
        TVechicleProcurer tVechicleProcurer = null;
        try {
            tVechicleProcurer = tVechicleProcurerMapper.selectByPrimaryKey(param.getBody().getInId());
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (ObjectUtils.isEmpty(tVechicleProcurer)) {
            return Result.build(1, "未查询到进场信息");
        }

        //明细必输项检查
        if (!ObjectUtils.isEmpty(inDto.getList()) && inDto.getList().size() > 0) {
            for (EntranceVeCheckOutDatilsDto datilsDto : inDto.getList()) {
                datilsDto.setInId(tVechicleProcurer.getInId());
                datilsDto.setProvId(inDto.getProvId());
                if (StringUtils.isEmpty(datilsDto.getVarietyId())) {
                    log.info("进出场业务-品种id为空");
                    return Result.build(1, "品种id为空");
                }
                if (datilsDto.getNum() == null) {
                    log.info("进出场业务-货品数量为空");
                    return Result.build(1, "货品数量为空");
                }
                if (StringUtils.isEmpty(datilsDto.getUnit())) {
                    log.info("进出场业务-单位为空");
                    return Result.build(1, "单位为空");
                }
                if (datilsDto.getRate() == null) {
                    log.info("进出场业务-费率为空");
                    return Result.build(1, "费率为空");
                }

            }
        }

//        BigDecimal maxRate = new BigDecimal(0);

        //明细核验
        if (!ObjectUtils.isEmpty(inDto.getList()) && inDto.getList().size() > 0) {
            List<TVechicleProcurerDetails> tvpDlist = new ArrayList<TVechicleProcurerDetails>();
            for (EntranceVeCheckOutDatilsDto datilsDto : inDto.getList()) {
//                if (datilsDto.getRate().compareTo(maxRate) > 0) {
//                    maxRate = datilsDto.getRate();
//                }
                TVechicleProcurerDetails tVechicleProcurerDetails = null;
                try {
                    tVechicleProcurerDetails = tVechicleProcurerDetailsMapper.selectByPrimaryKey(datilsDto.getInId(), datilsDto.getVarietyId(),datilsDto.getUnit());
                } catch (DataAccessException e) {
                    log.error("数据库查询异常", e);
                    throw new RuntimeException("数据库查询异常");
                }
                //不为空更新
                if (!ObjectUtils.isEmpty(tVechicleProcurerDetails)) {
                    tVechicleProcurerDetails.setNum(datilsDto.getNum());//数量
                    tVechicleProcurerDetails.setUnit(datilsDto.getUnit());//单位
                    tVechicleProcurerDetails.setProdPlace(datilsDto.getProdPlace());//产地
                    tVechicleProcurerDetails.setManufactEnter(datilsDto.getManufactEnter());//生产企业
                    tVechicleProcurerDetails.setOperId(param.getHead().getOperator());//操作员
                    tVechicleProcurerDetails.setRate(datilsDto.getRate());//费率
                    tVechicleProcurerDetails.setCategoryId(datilsDto.getCategoryId());//品种Id
                    tVechicleProcurerDetails.setCategoryNm(datilsDto.getCategoryNm());//品种名称
                    tVechicleProcurerDetails.setUpTime(new Date());
                    tVechicleProcurerDetails.setOperId(param.getHead().getOperator());//操作员
                    tVechicleProcurerDetails.setProvId(datilsDto.getProvId());//客户id
                    int result;
                    try {
                        result = tVechicleProcurerDetailsMapper.updateByPrimaryKey(tVechicleProcurerDetails);
                    } catch (DataAccessException e) {
                        log.error("数据库查询异常", e);
                        throw new RuntimeException("数据库查询异常");
                    }
                    if (result != 1) {
                        throw new RuntimeException("修改进场货物明细失败");
                    }
                    //更新库存
                    this.UpdateGoodsManager(tVechicleProcurerDetails);

                } else {
                    //为空新增明细
                    tVechicleProcurerDetails = new TVechicleProcurerDetails();
                    tVechicleProcurerDetails.setInId(datilsDto.getInId());//进场id
                    tVechicleProcurerDetails.setNum(datilsDto.getNum());//数量
                    tVechicleProcurerDetails.setUnit(datilsDto.getUnit());//单位
                    tVechicleProcurerDetails.setProdPlace(datilsDto.getProdPlace());//产地
                    tVechicleProcurerDetails.setPrdctNm(datilsDto.getPrdctNm());//品种名称
                    tVechicleProcurerDetails.setManufactEnter(datilsDto.getManufactEnter());//生产企业
                    tVechicleProcurerDetails.setOperId(param.getHead().getOperator());//操作员
                    tVechicleProcurerDetails.setProvId(tVechicleProcurer.getProvId());//客户编号
                    tVechicleProcurerDetails.setVarietyId(datilsDto.getVarietyId());
                    tVechicleProcurerDetails.setUpTime(new Date());//更新日期
                    tVechicleProcurerDetails.setRate(datilsDto.getRate());
                    tVechicleProcurerDetails.setVehicleNum(tVechicleProcurer.getVehicleNum());//车牌号
                    tVechicleProcurerDetails.setExpTp("0");//进场品种收费
                    tVechicleProcurerDetails.setCategoryId(datilsDto.getCategoryId());//品种Id
                    tVechicleProcurerDetails.setCategoryNm(datilsDto.getCategoryNm());//品种名称
                    tVechicleProcurerDetails.setOperId(param.getHead().getOperator());//操作员
                    tVechicleProcurerDetails.setProvId(datilsDto.getProvId());//客户id
                    tvpDlist.add(tVechicleProcurerDetails);
                    //更新库存
                    this.UpdateGoodsManager(tVechicleProcurerDetails);
                }


            }
            //插入进场货物明细表
            if (tvpDlist.size() > 0) {
                try {
                    tVechicleProcurerDetailsMapper.insert(tvpDlist);
                } catch (DataAccessException e) {
                    log.error("数据库插入异常", e);
                    throw new RuntimeException("数据库插入异常");
                }
            }

        }
        //删除货物明细表核验后不存在的货物明细
        try{
            tVechicleProcurerDetailsMapper.deleteDetails(inDto.getList(),inDto.getInId());
        } catch (DataAccessException e) {
            log.error("数据库删除异常", e);
            throw new RuntimeException("数据库删除异常");
        }

        //费用计算
        TVechicleProcurer upDateTVechicleProcurer = new TVechicleProcurer();
        //如果货物净重有变化，更新货物重量
//        if (param.getBody().getCargoWght().compareTo(tVechicleProcurer.getCargoWght()) != 0) {
//            upDateTVechicleProcurer.setCargoWght(param.getBody().getCargoWght());//货物净重
//            //车辆总重=皮重+货物总重
//            BigDecimal tolWght = tVechicleProcurer.getTareWght().add(param.getBody().getCargoWght());
//            upDateTVechicleProcurer.setInTtlWght(tolWght);//车辆总重
//        }
        //不为减免和不为固定收费类型重新计算费用
        if (!tVechicleProcurer.getIsDerated().equals("1") && !tVechicleProcurer.getIsFixed().equals("1")) {
//                maxRate = maxRate.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                //计算费用
                //核验进场费用=货物净重*最高费率
//                BigDecimal checkAmt = param.getBody().getCargoWght().multiply(maxRate);
            //收费金额前端市场算
            BigDecimal checkAmt = param.getBody().getActAmt();

            //核验后实际费用=核验进场费用-优惠金额
                BigDecimal checkActAmt = checkAmt.subtract(tVechicleProcurer.getInAmt());
                //核验实际进场费用>优惠金额
                if (checkActAmt.compareTo(new BigDecimal(0)) > 0) {
                    //核验后实际费用与进场实际费用进行比较，多退少补
                    BigDecimal actBalance = checkActAmt.subtract(tVechicleProcurer.getInActAmt());
                    //核验后实际费用>实际收费金额
                    if (actBalance.compareTo(new BigDecimal(0)) > 0) {
                        upDateTVechicleProcurer.setCheckSupptAmt(actBalance);//应补金额
                    }
                    //核验后实际费用<实际收费金额
                    else if (actBalance.compareTo(new BigDecimal(0)) < 0) {
                        upDateTVechicleProcurer.setCheckReturnAmt(actBalance.abs());//应退金额
                    }
                    //核验后实际费用=实际收费金额 没有差额
                    else {
                        upDateTVechicleProcurer.setCheckReturnAmt(new BigDecimal(0));//应退金额
                        upDateTVechicleProcurer.setCheckSupptAmt(new BigDecimal(0));//应补金额
                    }
                } else {//核验进场费用<=优惠金额
                    upDateTVechicleProcurer.setCheckReturnAmt(tVechicleProcurer.getInActAmt());//应退金额
                }

        }

        //赋值
        upDateTVechicleProcurer.setProvId(param.getBody().getProvId());//商户id
        upDateTVechicleProcurer.setProvNm(param.getBody().getProvNm());//商户名称
        upDateTVechicleProcurer.setPhone(param.getBody().getPhone());//电话
        upDateTVechicleProcurer.setCardNo(param.getBody().getCardNo());//商户卡号
        upDateTVechicleProcurer.setIsCheck("1");//已核验
        upDateTVechicleProcurer.setUpTime(new Date());//更新时间
        upDateTVechicleProcurer.setOperId(param.getHead().getOperator());//操作员
        upDateTVechicleProcurer.setInId(tVechicleProcurer.getInId());
        upDateTVechicleProcurer.setIsReturn(param.getBody().getIsReturn());//是否退费

        //更新进场信息表
        int result;
        try {
            result = tVechicleProcurerMapper.updateByPrimaryKeySelectiveCheck(upDateTVechicleProcurer);
        } catch (DataAccessException e) {
            log.error("数据库更新异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (result != 1) {
            throw new RuntimeException("数据库更新异常");
        }
        log.info("进出场业务-进场货物核验成功");
        ResultOutDto resDto = new ResultOutDto();
        resDto.setResult(ResultOutDto.SUCCESS);
        return Result.build(resDto, SUCCESS);

    }

    /**
     * 更新库存
     *
     * @param datilsDto
     */
    private void UpdateGoodsManager(TVechicleProcurerDetails datilsDto) {
        TProductInventoryInfoDto productInventoryInfoDto = null;
        try {
            productInventoryInfoDto = tProductInventoryInfoMapper.selectByPrimaryKey(datilsDto.getVarietyId(), datilsDto.getProvId(), datilsDto.getUnit());
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        //货物不存在新增
        if (ObjectUtils.isEmpty(productInventoryInfoDto)) {
            productInventoryInfoDto = new TProductInventoryInfoDto();
            productInventoryInfoDto.setPrdctId(datilsDto.getVarietyId());//品种id
            productInventoryInfoDto.setStock(datilsDto.getNum());//库存
            productInventoryInfoDto.setProvId(datilsDto.getProvId());//客户编号
            productInventoryInfoDto.setPrdctNm(datilsDto.getPrdctNm());//商品名称
            productInventoryInfoDto.setUnit(datilsDto.getUnit());//单位
            productInventoryInfoDto.setUpdateDt(new Date());//更新时间
            productInventoryInfoDto.setPrdctTypeId(datilsDto.getCategoryId());//品种Id
            productInventoryInfoDto.setPrdctType(datilsDto.getCategoryNm());//品种名称

            try {
                tProductInventoryInfoMapper.insertSelective(productInventoryInfoDto);
            } catch (DataAccessException e) {
                log.error("数据库更新异常", e);
                throw new RuntimeException("数据库更新异常");
            }
        } else {
            //存在，更新库存
            if(null==productInventoryInfoDto.getStock()){
                productInventoryInfoDto.setStock(new BigDecimal(0));
            }
            productInventoryInfoDto.setStock(productInventoryInfoDto.getStock().add(datilsDto.getNum()));//库存
            productInventoryInfoDto.setUpdateDt(new Date());
            try {
                tProductInventoryInfoMapper.updateByPrimaryKeySelective(productInventoryInfoDto);
            } catch (DataAccessException e) {
                log.error("数据库更新异常", e);
                throw new RuntimeException("数据库更新异常");
            }
        }

    }
}
