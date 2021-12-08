package com.dispart.service.impl;

import com.dispart.dao.mapper.BaseOrganizationMapper;
import com.dispart.dao.mapper.TCardInfoMapper;
import com.dispart.dao.mapper.TCardManagerMapper;
import com.dispart.dto.ResultOutDto;
import com.dispart.dto.makeCard.*;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.MakeCardManagerService;
import com.dispart.utils.DateUtil;
import com.dispart.vo.basevo.TCardInfoVo;
import com.dispart.vo.basevo.TCardManagerVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

import static com.dispart.result.ResultCodeEnum.SUCCESS;

/**
 * @author zhongfei
 * @version 1.0.0
 * @title MakeCardManagerServiceImpl
 * @creat 2021/8/01 17:50
 * @Copyright 2020-2021
 */
@Service
@Slf4j
public class MakeCardManagerServiceImpl implements MakeCardManagerService {

    @Autowired
    TCardManagerMapper tCardManagerMapper;

    @Autowired
    BaseOrganizationMapper baseOrganizationMapper;

    @Autowired
    TCardInfoMapper tCardInfoMapper;

    /**
     * 查询制卡申请信息
     *
     * @param param
     * @return
     */
    @Override
    public Result<QuryMakeCardInfoOutDto> quryMakeCardApply(Request<QuryMakeCardInfoInDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(1, "输入参数为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(1, "输入参数为空");
        }
        QuryMakeCardInfoInDto inDto = param.getBody();
        if (inDto.getPageNum() == null || inDto.getPageSize() == null) {
            log.info("用户业务-分页参数不能为空");
            return Result.build(1, "分页参数为空");
        }
        if (inDto.getPageSize() <= 0) {
            log.info("用户业务-分页条数输入错误");
            return Result.build(1, "分页条数输入错误");
        }
        if (inDto.getPageNum() <= 0) {
            log.info("用户业务-分页页数输入错误");
            return Result.build(1, "分页页数输入错误");
        }
        int strNum = (inDto.getPageNum() - 1) * inDto.getPageSize();
        inDto.setStrNum(strNum);//起始记录数
        QuryMakeCardInfoOutDto outBody = new QuryMakeCardInfoOutDto();
        //查询总记录数
        int tolNum = 0;
        try {
            tolNum = tCardManagerMapper.queryCount(inDto);
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("查询异常");
        }
        if (tolNum <= 0) {
            log.info("基础业务-查询制卡申请数为0");
            outBody.setTolPageNum(0);//总条数
            return Result.build(outBody, SUCCESS);
        }
        List<QuryMakeCardInfoOutParamDto> outList = null;

        try {
            outList = tCardManagerMapper.selectAll(inDto);
        } catch (Exception e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("查询异常");
        }
        log.info("基础业务-查询制卡申请数成功");
        outBody.setTolPageNum(tolNum);//总条数
        outBody.setList(outList);
        return Result.build(outBody, SUCCESS);
    }

    /**
     * 、
     * 新增制卡信息
     *
     * @param param
     * @return
     */
    @Override
    @Transactional
    public Result<ResultOutDto> addMakeCardApply(Request<ApplyMakeCardInDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(1, "输入参数为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(1, "输入参数为空");
        }
        if (StringUtils.isEmpty(param.getBody().getNum())) {
            return Result.build(1, "申请张数为空");
        }
        int num = Integer.valueOf(param.getBody().getNum());
        if (num <= 0) {
            return Result.build(1, "申请张数输入错误");
        }
        //单据号:YYMM + 2位序列号
        Integer documentId = baseOrganizationMapper.findNextval("documentId");
        String dateFormat = DateUtil.getDateToString("YYMM");
        String documentNm = dateFormat + String.format("%0" + 2 + "d", documentId % 100);
        //插入卡表明细表 返回起始卡号，终止卡号TCardManagerVo
        TCardManagerVo vo = insertCardNoDatil(num, documentNm, param.getHead().getOperator());
        vo.setDocumentNum(documentNm);
        vo.setNum(Integer.valueOf(param.getBody().getNum()));
        vo.setStatus(TCardManagerVo.APPLY);//申请
        vo.setCreatTime(new Date());
        vo.setUpTime(new Date());
        vo.setOperId(param.getHead().getOperator());

        int result = 0;
        try {
            result = tCardManagerMapper.insertSelective(vo);
        } catch (DataAccessException e) {
            log.error("数据库插入异常", e);
            throw new RuntimeException("申请异常");
        }
        if (1 != result) {
            return Result.build(1, "申请制卡失败");
        }
        log.info("基础业务-新增制卡申请成功");
        ResultOutDto resultOutDto = new ResultOutDto();
        resultOutDto.setResult(ResultOutDto.SUCCESS);
        return Result.build(resultOutDto, SUCCESS);
    }

    /**
     * 修改制卡申请
     *
     * @param param
     * @return
     */
    @Override
    @Transactional
    public Result<ResultOutDto> updateMakeCardApply(Request<UpdateApplyMakeCardInDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(1, "输入参数为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(1, "输入参数为空");
        }
        if (StringUtils.isEmpty(param.getBody().getNum())) {
            return Result.build(1, "申请张数为空");
        }
        if (StringUtils.isEmpty(param.getBody().getDocumentNum())) {
            return Result.build(1, "单据号为空");
        }
        int num = Integer.valueOf(param.getBody().getNum());
        if (num <= 0) {
            return Result.build(1, "申请张数输入错误");
        }

        TCardManagerVo vo = null;
        try {
            vo = tCardManagerMapper.selectByDocumentNum(param.getBody().getDocumentNum());
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("查询异常");
        }
        if (ObjectUtils.isEmpty(vo)) {
            return Result.build(1, "无对应单据号");
        }
        //删除明细表中单据号相关卡号从重新生成
        try {
            tCardInfoMapper.deleteDocumentNum(param.getBody().getDocumentNum());
        } catch (DataAccessException e) {
            log.error("数据库删除异常", e);
            throw new RuntimeException(e);
        }
        //插入卡表明细表 返回起始卡号，终止卡号TCardManagerVo
        TCardManagerVo cardNoVo = insertCardNoDatil(num, param.getBody().getDocumentNum(), param.getHead().getOperator());
        vo.setNum(num);
        vo.setUpTime(new Date());
        vo.setOperId(param.getHead().getOperator());
        int result;
        try {
            result = tCardManagerMapper.updateByPrimaryKeySelective(vo);
        } catch (DataAccessException e) {
            log.error("数据库更新异常", e);
            throw new RuntimeException(e);
        }

        if (1 != result) {
            throw new RuntimeException("更新申请制卡信息失败");
        }
        log.info("基础业务-更新制卡申请信息成功");
        ResultOutDto resultOutDto = new ResultOutDto();
        resultOutDto.setResult(ResultOutDto.SUCCESS);
        return Result.build(resultOutDto, SUCCESS);
    }

    /**
     * 制卡申请取消
     *
     * @param param
     * @return
     */
    @Override
    public Result<ResultOutDto> cancelMakeCardApply(Request<UpdateMakeCardStatInDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(1, "输入参数为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(1, "输入参数为空");
        }
        if (StringUtils.isEmpty(param.getBody().getDocumentNum())) {
            return Result.build(1, "单据号为空");
        }
        TCardManagerVo vo = null;
        try {
            vo = tCardManagerMapper.selectByDocumentNum(param.getBody().getDocumentNum());
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException(e);
        }
        if (ObjectUtils.isEmpty(vo)) {
            return Result.build(1, "无对应单据号");
        }

        if (!TCardManagerVo.APPLY.equals(vo.getStatus())) {
            return Result.build(1, "状态不为申请中");
        }
        TCardInfoVo cardInfoVo = new TCardInfoVo();
        cardInfoVo.setStatus(TCardInfoVo.CANCEL);//取消
        cardInfoVo.setUpTime(new Date());
        cardInfoVo.setOperId(param.getHead().getOperator());
        cardInfoVo.setDocumentNum(param.getBody().getDocumentNum());
        int count = 0;
        try {
            count = tCardInfoMapper.updateByDocumentNumSelective(cardInfoVo);
        } catch (DataAccessException e) {
            log.error("数据库更新异常", e);
            throw new RuntimeException(e);
        }
        TCardManagerVo upDateVo = new TCardManagerVo();
        upDateVo.setDocumentNum(param.getBody().getDocumentNum());
        upDateVo.setStatus(TCardManagerVo.CANCEL);//取消
        upDateVo.setOperId(param.getHead().getOperator());
        upDateVo.setUpTime(new Date());
        int result = 0;
        try {
            result = tCardManagerMapper.updateByPrimaryKeyForSatus(upDateVo, TCardManagerVo.APPLY);
        } catch (DataAccessException e) {
            log.error("数据库更新异常", e);
            throw new RuntimeException(e);
        }
        if (1 != result) {
            log.info("取消制卡申请失败");
            throw new RuntimeException("取消制卡申请失败");
        }
        log.info("基础业务-取消制卡申请成功");
        ResultOutDto resultOutDto = new ResultOutDto();
        resultOutDto.setResult(ResultOutDto.SUCCESS);
        return Result.build(resultOutDto, SUCCESS);
    }

    /**
     * 制卡申请入库
     *
     * @param param
     * @return
     */
    @Override
    @Transactional
    public Result<ResultOutDto> warehousingMakeCardApply(Request<WarehousingInDto> param) {

        if (ObjectUtils.isEmpty(param)) {
            return Result.build(1, "输入参数为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(1, "输入参数为空");
        }
        if (StringUtils.isEmpty(param.getBody().getDocumentNum())) {
            return Result.build(1, "单据号为空");
        }
        TCardManagerVo vo = null;
        try {
            vo = tCardManagerMapper.selectByDocumentNum(param.getBody().getDocumentNum());
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException(e);
        }
        if (ObjectUtils.isEmpty(vo)) {
            return Result.build(1, "无对应单据号");
        }

        if (!TCardManagerVo.SENDING.equals(vo.getStatus())) {
            log.info("状态不为寄送中");
            return Result.build(1, "入库失败");
        }
        TCardInfoVo cardInfoVo = new TCardInfoVo();
        cardInfoVo.setStatus(TCardInfoVo.NOTUSR);//未使用
        cardInfoVo.setUpTime(new Date());
        cardInfoVo.setOperId(param.getHead().getOperator());
        cardInfoVo.setDocumentNum(param.getBody().getDocumentNum());
        int count = 0;
        try {
            count = tCardInfoMapper.updateByDocumentNumSelective(cardInfoVo);
        } catch (DataAccessException e) {
            log.error("数据库更新异常", e);
            throw new RuntimeException(e);
        }
        if (count <= 0) {
            log.info("入库失败");
            throw new RuntimeException("入库失败");
        }

        TCardManagerVo upDateVo = new TCardManagerVo();
        upDateVo.setDocumentNum(param.getBody().getDocumentNum());
        upDateVo.setStatus(TCardManagerVo.NORMAL);//正常
        upDateVo.setOperId(param.getHead().getOperator());
        upDateVo.setUpTime(new Date());
        int result = 0;
        try {
            result = tCardManagerMapper.updateByPrimaryKeyForSatus(upDateVo, TCardManagerVo.SENDING);
        } catch (DataAccessException e) {
            log.error("数据库更新异常", e);
            throw new RuntimeException(e);
        }
        if (1 != result) {
            log.info("制卡申请入库失败");
            throw new RuntimeException("制卡申请入库失败");
        }
        log.info("基础业务-制卡申请入库成功");
        ResultOutDto resultOutDto = new ResultOutDto();
        resultOutDto.setResult(ResultOutDto.SUCCESS);
        return Result.build(resultOutDto, SUCCESS);

    }

    /**
     * 查询入库制卡信息
     *
     * @param param
     * @return
     */
    @Override
    public Result<QuryWarehousingOutDto> quryWarehousingCard(Request<QuryWarehousingInDto> param) {
        QuryWarehousingOutDto outBody = new QuryWarehousingOutDto();

        if (ObjectUtils.isEmpty(param)) {
            return Result.build(1, "输入参数为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(1, "输入参数为空");
        }

        //入库的为查询正常和注销状态
        if (!StringUtils.isEmpty(param.getBody().getStatus())
                && (!param.getBody().getStatus().equals(TCardManagerVo.LOGOUT)
                && !param.getBody().getStatus().equals(TCardManagerVo.NORMAL))) {
            log.info("基础业务-查询制卡申请数为0");
            outBody.setTolPageNum(0);//总条数
            return Result.build(outBody, SUCCESS);
        }
        QuryWarehousingInDto inDto = param.getBody();
        if (inDto.getPageNum() == null || inDto.getPageSize() == null) {
            log.info("用户业务-分页参数不能为空");
            return Result.build(1, "分页参数为空");
        }
        if (inDto.getPageSize() <= 0) {
            log.info("用户业务-分页条数输入错误");
            return Result.build(1, "分页条数输入错误");
        }
        if (inDto.getPageNum() <= 0) {
            log.info("用户业务-分页页数输入错误");
            return Result.build(1, "分页页数输入错误");
        }
        int strNum = (inDto.getPageNum() - 1) * inDto.getPageSize();
        inDto.setStrNum(strNum);//起始记录数
        //查询总记录数
        int tolNum = 0;
        try {
            tolNum = tCardManagerMapper.queryCountByStatus(inDto);
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException(e);
        }
        if (tolNum <= 0) {
            log.info("基础业务-查询制卡申请数为0");
            outBody.setTolPageNum(0);//总条数
            return Result.build(outBody, SUCCESS);
        }
        List<QuryWarehousingParamOutDto> outList = null;

        try {
            outList = tCardManagerMapper.selectAllByStatus(inDto);
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException(e);
        }
        log.info("基础业务-查询制卡入库信息成功");
        outBody.setTolPageNum(tolNum);//总条数
        outBody.setList(outList);
        return Result.build(outBody, SUCCESS);
    }

    /**
     * 查询入库信息详情
     *
     * @param param
     * @return
     */
    @Override
    public Result<QuryWarehousingDetailsOutDto> quryWarehousingCardDetails(Request<QuryWarehousingDetailsInDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(1, "输入参数为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(1, "输入参数为空");
        }
        QuryWarehousingDetailsInDto inDto = param.getBody();
        if (StringUtils.isEmpty(inDto.getDocumentNum())) {
            log.info("用户业务-单据号为空");
            return Result.build(1, "单据号为空");
        }
        TCardManagerVo vo = null;
        try {
            vo = tCardManagerMapper.selectByDocumentNum(param.getBody().getDocumentNum());
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException(e);
        }
        if (ObjectUtils.isEmpty(vo)) {
            return Result.build(1, "无对应单据号");
        }

        log.info("基础业务-查询制卡申请入库信息详情成功");
        QuryWarehousingDetailsOutDto resOutDto = new QuryWarehousingDetailsOutDto();
        resOutDto.setDocumentNum(vo.getDocumentNum());
        resOutDto.setSendNo(vo.getSendNo());
        resOutDto.setStartCard(vo.getStartCard());
        resOutDto.setEndCard(vo.getEndCard());
        resOutDto.setNum(String.valueOf(vo.getNum()));
        resOutDto.setStatus(vo.getStatus());
        return Result.build(resOutDto, SUCCESS);
    }

    /**
     * 入库登记查询
     *
     * @param param
     * @return
     */
    @Override
    public Result<QuryWarehousingOutDto> quryCanWarehousingCardInfo(Request<QuryWarehousingInDto> param) {
        QuryWarehousingOutDto outBody = new QuryWarehousingOutDto();

        if (ObjectUtils.isEmpty(param)) {
            return Result.build(1, "输入参数为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(1, "输入参数为空");
        }

        QuryWarehousingInDto inDto = param.getBody();
        if (inDto.getPageNum() == null || inDto.getPageSize() == null) {
            log.info("用户业务-分页参数不能为空");
            return Result.build(1, "分页参数为空");
        }
        if (inDto.getPageSize() <= 0) {
            log.info("用户业务-分页条数输入错误");
            return Result.build(1, "分页条数输入错误");
        }
        if (inDto.getPageNum() <= 0) {
            log.info("用户业务-分页页数输入错误");
            return Result.build(1, "分页页数输入错误");
        }
        int strNum = (inDto.getPageNum() - 1) * inDto.getPageSize();
        inDto.setStrNum(strNum);//起始记录数
        //查询总记录数
        int tolNum = 0;
        try {
            tolNum = tCardManagerMapper.queryCountByWarehousingStatus(inDto);
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException(e);
        }
        if (tolNum <= 0) {
            log.info("基础业务-查询制卡申请数为0");
            outBody.setTolPageNum(0);//总条数
            return Result.build(outBody, SUCCESS);
        }
        List<QuryWarehousingParamOutDto> outList = null;

        try {
            outList = tCardManagerMapper.queryAllByWarehousing(inDto);
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException(e);
        }
        log.info("基础业务-查询制卡入库登记成功");
        outBody.setTolPageNum(tolNum);//总条数
        outBody.setList(outList);
        return Result.build(outBody, SUCCESS);
    }

    /**
     * 制卡状态变更
     *
     * @param param
     * @return
     */
    @Override
    public Result<ResultOutDto> updateCardInfoStatus(Request<UpdateMakeCardStatInDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(1, "输入参数为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(1, "输入参数为空");
        }
        if (StringUtils.isEmpty(param.getBody().getDocumentNum())) {
            return Result.build(1, "单据号为空");
        }
        if (StringUtils.isEmpty(param.getBody().getStatus())) {
            return Result.build(1, "状态为空");
        }
        if (param.getBody().getStatus().equals(TCardManagerVo.SENDING)) {
            if (StringUtils.isEmpty(param.getBody().getSendNo())) {
                return Result.build(1, "寄送单号为空");
            }
        }

        TCardManagerVo vo = null;
        try {
            vo = tCardManagerMapper.selectByDocumentNum(param.getBody().getDocumentNum());
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException(e);
        }
        if (ObjectUtils.isEmpty(vo)) {
            return Result.build(1, "无对应单据号");
        }

        String origStatus = "";
        switch (param.getBody().getStatus()) {
            case TCardManagerVo.ACCEPT:
                origStatus = TCardManagerVo.APPLY;
                break;
            case TCardManagerVo.COMPLETE:
            case TCardManagerVo.MAKEERR:
                origStatus = TCardManagerVo.ACCEPT;
                break;
            case TCardManagerVo.SENDING:
                origStatus = TCardManagerVo.COMPLETE;
                break;
            case TCardManagerVo.SENDERR:
                origStatus = TCardManagerVo.SENDING;
                break;
            case TCardManagerVo.SENDED:
                origStatus = TCardManagerVo.NORMAL;
                break;
            default:
                return Result.build(1, "变更状态错误");

        }

        TCardManagerVo upDateVo = new TCardManagerVo();
        upDateVo.setDocumentNum(param.getBody().getDocumentNum());
        upDateVo.setStatus(param.getBody().getStatus());//取消
        upDateVo.setOperId(param.getHead().getOperator());
        upDateVo.setUpTime(new Date());
        if (param.getBody().getStatus().equals(TCardManagerVo.MAKEERR) || param.getBody().getStatus().equals(TCardManagerVo.SENDERR)) {
            upDateVo.setRemark(param.getBody().getRemark());
        }
        if (param.getBody().getStatus().equals(TCardManagerVo.SENDING)) {
            upDateVo.setSendNo(param.getBody().getSendNo());
        }
        int result = 0;
        try {
            result = tCardManagerMapper.updateStatusByDocumentNum(upDateVo, origStatus);
        } catch (DataAccessException e) {
            log.error("数据库更新异常", e);
            throw new RuntimeException(e);
        }
        if (1 != result) {
            return Result.build(1, "制卡状态变更失败");
        }
        log.info("基础业务-制卡状态变更成功");
        ResultOutDto resultOutDto = new ResultOutDto();
        resultOutDto.setResult(ResultOutDto.SUCCESS);
        return Result.build(resultOutDto, SUCCESS);
    }

    /**
     * 入库制卡信息修改
     *
     * @param param
     * @return
     */
    @Override
    @Transactional
    public Result<ResultOutDto> updateWarehousingCardInfo(Request<QuryWarehousingInDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(1, "输入参数为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(1, "输入参数为空");
        }
        if (StringUtils.isEmpty(param.getBody().getDocumentNum())) {
            return Result.build(1, "单据号为空");
        }
        if (StringUtils.isEmpty(param.getBody().getStatus())) {
            return Result.build(1, "状态为空");
        }


        if (!param.getBody().getStatus().equals(TCardManagerVo.LOGOUT)) {
            log.info("基础业务-状态不为注销，未注销任何信息");
            return Result.build(1, "未注销任何信息");
        }

        TCardManagerVo vo = null;
        try {
            vo = tCardManagerMapper.selectByDocumentNum(param.getBody().getDocumentNum());
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException(e);
        }
        if (ObjectUtils.isEmpty(vo)) {
            return Result.build(1, "无对应单据号");
        }
        TCardInfoVo cardInfoVo = new TCardInfoVo();
        cardInfoVo.setStatus(TCardInfoVo.LOGOUT);//已注销
        cardInfoVo.setUpTime(new Date());
        cardInfoVo.setOperId(param.getHead().getOperator());
        cardInfoVo.setDocumentNum(param.getBody().getDocumentNum());
        int count = 0;
        try {
            count = tCardInfoMapper.updateByDocumentNumSelective(cardInfoVo);
        } catch (DataAccessException e) {
            log.error("数据库更新异常", e);
            throw new RuntimeException(e);
        }
        if (count <= 0) {
            log.info("注销失败");
            throw new RuntimeException("注销失败");
        }
        TCardManagerVo upDateVo = new TCardManagerVo();
        upDateVo.setDocumentNum(param.getBody().getDocumentNum());
        upDateVo.setStatus(TCardManagerVo.SENDING);//寄送中
        upDateVo.setOperId(param.getHead().getOperator());
        upDateVo.setUpTime(new Date());
        int result = 0;
        try {
            result = tCardManagerMapper.updateStatusByDocumentNum(upDateVo, TCardManagerVo.NORMAL);
        } catch (DataAccessException e) {
            log.error("数据库更新异常", e);
            throw new RuntimeException(e);
        }
        if (1 != result) {
            throw new RuntimeException("取消制卡申请失败");
        }
        log.info("基础业务-取消制卡申请成功");
        ResultOutDto resultOutDto = new ResultOutDto();
        resultOutDto.setResult(ResultOutDto.SUCCESS);
        return Result.build(resultOutDto, SUCCESS);
    }

    /**
     * @param num        制卡张数
     * @param documentNm 单据号
     * @param operId     操作员
     * @return
     */
    protected TCardManagerVo insertCardNoDatil(int num, String documentNm, String operId) {
        TCardManagerVo vo = new TCardManagerVo();
        //固定位
        String binId = "52012300";
        //起始卡号 终止卡号
        String startCard = "";
        String endCard = "";
        TCardInfoVo cardInfo = new TCardInfoVo();
        cardInfo.setCreatTime(new Date());
        cardInfo.setUpTime(new Date());
        cardInfo.setCardTp("0");//实体卡
        cardInfo.setOperId(operId);
        cardInfo.setStatus(TCardInfoVo.APPLY);//申请中
        for (int i = 1; i <= num; i++) {
            String cardNo = "";
            //卡号=52012300+卡类型（0-实体卡，1-虚拟卡）+加8位顺序号+3位随机号
            Integer cardId = baseOrganizationMapper.findNextval("cardId");
            int random2 = (int) (Math.random() * 90) + 10;
            cardNo = binId +"0"+ String.format("%0" + 8 + "d", cardId)+ random2;
            if (i == 1) {
                startCard = cardNo;
            }
            if (i == num) {
                endCard = cardNo;
            }
            cardInfo.setDocumentNum(documentNm);
            cardInfo.setCardNo(cardNo);
            //插入卡片明细表
            try {
                tCardInfoMapper.insertSelective(cardInfo);
            } catch (DataAccessException e) {
                log.error("数据库插入异常", e);
                throw new RuntimeException("数据库插入异常");
            }
        }
        vo.setStartCard(startCard);
        vo.setEndCard(endCard);
        return vo;
    }


}
