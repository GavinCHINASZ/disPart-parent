package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dispart.dao.ITCustomBankcardDao;
import com.dispart.dao.TCommonBankNameDao;
import com.dispart.dao.TCustomInfoManagerDao;
import com.dispart.dto.ResultOutDto;
import com.dispart.dto.customdto.*;
import com.dispart.dto.userdto.TCustomBankcardDto;
import com.dispart.enums.CertTpEnum;
import com.dispart.enums.CustomStatusEnum;
import com.dispart.enums.CustomTpEnum;
import com.dispart.enums.IsRealEnum;
import com.dispart.request.Request;
import com.dispart.result.EntranceResult_WEnum;
import com.dispart.result.Result;
import com.dispart.service.CustomManagerService;
import com.dispart.utils.UserResUtil;
import com.dispart.vo.DISP20210284RepVo;
import com.dispart.vo.user.TCommonBankNameVo;
import com.dispart.vo.user.TCustomBankcardVo;
import com.dispart.vo.user.TCustomInfoManagerVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.dispart.result.ResultCodeEnum.SUCCESS;
import static com.dispart.result.UserResultCodeEnum.*;

@Service
@Slf4j
public class CustomManagerServiceImpl implements CustomManagerService {

    @Resource
    TCustomInfoManagerDao tCustomInfoManagerDao;

    @Resource
    ITCustomBankcardDao iTCustomBankcardDao;

    @Resource
    TCommonBankNameDao tCommonBankNameDao;

    @Value("${custom.publicId}")
    private String publicId;

    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate2;

    @Value("${fileProps.fileLocalPath}")
    private String fileLocalPath;


    /**
     * 新增客户信息
     *
     * @param param
     * @return
     */
    @Override
    public Result<ResultOutDto> addCustomInfo(Request<AddCustomInfoInDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(USER_PARAM_NULL.getCode(), "输入参数为为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(USER_PARAM_NULL.getCode(), "输入参数为为空");
        }

        if (StringUtils.isEmpty(param.getBody().getCustomTp())) {
            return UserResUtil.paramFail("客户类型为空");
        }

        AddCustomInfoInDto body = param.getBody();
        log.info("DISP20210167 body=" + body);
        if (StringUtils.isEmpty(body.getPhone())) {
            return UserResUtil.paramFail("联系电话为空");
        }

        int isRealNum = 1;// 0-实名  1-未实名
        // 个人
        if (CustomTpEnum.PERSONAL.getCode().equals(body.getCustomTp())) {
            /*if (StringUtils.isEmpty(body.getProvNm())) {
                return UserResUtil.paramFail("客户名称为空");
            }
            if (StringUtils.isEmpty(body.getCertTp())) {
                return UserResUtil.paramFail("证件类型为空");
            }
            if (StringUtils.isEmpty(body.getCertNum())) {
                return UserResUtil.paramFail("证件号码为空");
            }
            if (StringUtils.isEmpty(body.getCertAddr())) {
                return UserResUtil.paramFail("证件住址为空");
            }
            if (StringUtils.isEmpty(body.getCertPrd())) {
                return UserResUtil.paramFail("证件有效期为空");
            }
            if (StringUtils.isEmpty(body.getReverserUrl())) {
                return UserResUtil.paramFail("个人身份证正面地址为空");
            }
            if (StringUtils.isEmpty(body.getFrontUrl())) {
                return UserResUtil.paramFail("个人身份证反面地址为空");
            }*/

            if (StringUtils.isNotEmpty(body.getCertNum()) && StringUtils.isNotEmpty(body.getReverserUrl())
                    && StringUtils.isNotEmpty(body.getFrontUrl())){
                isRealNum = 0;
            }

            // 查询客户信息是否存在
            List<TCustomInfoManagerVo> customVoList;
            try {
                customVoList = tCustomInfoManagerDao.selectCustomInfoByPhone(body.getPhone());

                // 关联客户信息已存在
                if (null != customVoList && customVoList.size() >= 1) {
                    return UserResUtil.buildFail(USER_CUSTOM_INFO_ISEXIT);
                }
            } catch (DataAccessException e) {
                log.error("数据库查询异常", e);
                throw new RuntimeException("数据库查询异常");
            }
        }

        // 企业
        if (CustomTpEnum.BUSINESS.getCode().equals(body.getCustomTp())) {
            /*if (StringUtils.isEmpty(body.getProvNm())) {
                return UserResUtil.paramFail("客户名称为空");
            }
            if (StringUtils.isEmpty(body.getCertTp())) {
                return UserResUtil.paramFail("证件类型为空");
            }
            if (StringUtils.isEmpty(body.getCertNum())) {
                return UserResUtil.paramFail("证件号码为空");
            }
            if (StringUtils.isEmpty(body.getCertAddr())) {
                return UserResUtil.paramFail("证件住址为空");
            }
            if (StringUtils.isEmpty(body.getCertPrd())) {
                return UserResUtil.paramFail("证件有效期为空");
            }

            if (StringUtils.isEmpty(body.getLegalName())) {
                return UserResUtil.paramFail("法人名称为空");
            }

            if (StringUtils.isEmpty(body.getLegalPhone())) {
                return UserResUtil.paramFail("法人电话为空");
            }
            if (StringUtils.isEmpty(body.getLegalCertTp())) {
                return UserResUtil.paramFail("法人证件类型为空");
            }
            if (StringUtils.isEmpty(body.getLegalCertNum())) {
                return UserResUtil.paramFail("法人证件号码为空");
            }

            if (StringUtils.isEmpty(body.getReverserUrl())) {
                return UserResUtil.paramFail("法人身份证正面地址为空");
            }
            if (StringUtils.isEmpty(body.getFrontUrl())) {
                return UserResUtil.paramFail("法人身份证反面地址为空");
            }
            if (StringUtils.isEmpty(body.getBusinessUrl())) {
                return UserResUtil.paramFail("营业执照地址为空");
            }*/

            if (StringUtils.isNotEmpty(body.getCertNum()) && StringUtils.isNotEmpty(body.getBusinessUrl())
                && StringUtils.isNotEmpty(body.getLegalCertNum()) && StringUtils.isNotEmpty(body.getReverserUrl())
                    && StringUtils.isNotEmpty(body.getFrontUrl())){
                isRealNum = 0;
            }

            // 手机号查询客户信息是否存在
            List<TCustomInfoManagerVo> customVoList;
            try {
                customVoList = tCustomInfoManagerDao.selectCustomInfoByPhone(body.getPhone());
                // 为空新增客户信息
                if (null != customVoList && customVoList.size() >= 1) {
                    return UserResUtil.buildFail(USER_CUSTOM_INFO_ISEXIT);
                }
            } catch (DataAccessException e) {
                log.error("数据库查询异常", e);
                throw new RuntimeException("数据库查询异常");
            }

            if(!StringUtils.isEmpty(body.getLegalPhone())) {
                // 手机号查询客户信息是否存在
                try {
                    customVoList = tCustomInfoManagerDao.selectCustomInfoByPhone(body.getLegalPhone());
                    // 关联客户信息已存在
                    if (null != customVoList && customVoList.size() >= 1) {
                        return UserResUtil.buildFail(USER_CUSTOM_INFO_ISEXIT);
                    }
                } catch (DataAccessException e) {
                    log.error("数据库查询异常", e);
                    throw new RuntimeException("数据库查询异常");
                }
            }
        }

        int count = 0;
        if (!StringUtils.isEmpty(param.getBody().getCertNum())) {
            try {
                count = tCustomInfoManagerDao.selectByCertNumCount(body.getCertNum(), null);
            } catch (Exception e) {
                log.error("数据查询异常", e);
                throw new RuntimeException("数据查询异常");
            }
        }
        if (count > 0) {
            return UserResUtil.buildFail(USER__CUSTOM_CERTNUM_ISEXIT);
        }

        int seq = tCustomInfoManagerDao.selectCustomIdSeq();
        // 客户编号 6005000 +8位顺序号
        String customId = publicId + String.format("%0" + 8 + "d", seq);

        TCustomInfoManagerVo vo = new TCustomInfoManagerVo();
        vo.setProvId(customId);
        vo.setCustomTp(body.getCustomTp());
        vo.setProvNm(body.getProvNm());
        vo.setShrtNm(body.getShrtNm());
        vo.setCertType(body.getCertTp());
        vo.setCertNum(body.getCertNum());
        vo.setCertAddr(body.getCertAddr());
        vo.setCertPrd(body.getCertPrd());
        vo.setPhone(body.getPhone());
        vo.setLegalName(body.getLegalName());
        vo.setLegalPhone(body.getLegalPhone());
        vo.setLegalCertTp(body.getLegalCertTp());
        vo.setLegalCertNum(body.getLegalCertNum());
        vo.setAgentName(body.getAgentName());
        vo.setAgentPhone(body.getAgentPhone());
        vo.setAgentAddr(body.getAgentAddr());
        vo.setAgentCertNo(body.getAgentCertNo());
        vo.setTermPrctn(body.getTermPrctn());
        vo.setTermDueDt(body.getTermDueDt());
        vo.setStatus("0");//启用
        // 是否实名 0-实名  1-未实名
        vo.setIsReal("" + isRealNum);
        vo.setOperId(param.getHead().getOperator());
        vo.setCreatTime(new Date());
        //vo.setUpTime(new Date());
        vo.setReverserUrl(body.getReverserUrl());
        vo.setFrontUrl(body.getFrontUrl());
        vo.setBusinessUrl(body.getBusinessUrl());

        vo.setAgentFrontUrl(body.getAgentFrontUrl());
        vo.setAgentReverserUrl(body.getAgentReverserUrl());
        vo.setAgentTermBookUrl(body.getAgentTermBookUrl());
        // 渠道号
        vo.setChanlNo(param.getHead().getChanlNo());
        vo.setOtherFileUrl(body.getOtherFileUrl());
        int result;
        try {
            result = tCustomInfoManagerDao.insertSelective(vo);
        } catch (DataAccessException e) {
            log.error("数据插入异常", e);
            throw new RuntimeException("数据库插入异常");
        }
        if (result != 1) {
            return UserResUtil.buildFail(USER__CUSTOM_INFO_INSERT);
        }

        log.info("用户业务-新增客户管理信息成功");
        return UserResUtil.getResultSuccessDto();
    }

    /**
     * DISP20210170修改客户信息
     *
     * @param param Request<UpdateCustomInfoInDto>
     * @return Result<ResultOutDto>
     */
    @Override
    public Result<ResultOutDto> updateCustomInfo(Request<UpdateCustomInfoInDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(USER_PARAM_NULL.getCode(), "输入参数为为空");
        }

        UpdateCustomInfoInDto body = param.getBody();
        log.info("DISP20210170 boyd=" + body);
        if (ObjectUtils.isEmpty(body)) {
            return Result.build(USER_PARAM_NULL.getCode(), "输入参数为为空");
        }

        if (StringUtils.isEmpty(param.getBody().getPhone())) {
            return UserResUtil.paramFail("联系电话为空");
        }

        int isRealNum = 1;
        // 个人
        if (CustomTpEnum.PERSONAL.getCode().equals(body.getCustomTp())) {
            if (StringUtils.isNotEmpty(body.getCertNum()) && StringUtils.isNotEmpty(body.getReverserUrl())
                    && StringUtils.isNotEmpty(body.getFrontUrl())){
                isRealNum = 0;
            }

            // 查询客户信息是否存在
            List<TCustomInfoManagerVo> customVoList = null;
            try {
                customVoList = tCustomInfoManagerDao.selectCustomInfoByPhoneNOEqProvId(body.getPhone(), body.getProvId());
            } catch (DataAccessException e) {
                log.error("数据库查询异常", e);
                throw new RuntimeException("数据库查询异常");
            }

            // 关联客户信息已存在
            if (null != customVoList && customVoList.size() >= 1) {
                return UserResUtil.buildFail(USER_CUSTOM_INFO_ISEXIT);
            }
        }

        // 企业
        if (CustomTpEnum.BUSINESS.getCode().equals(param.getBody().getCustomTp())) {
            if (StringUtils.isNotEmpty(body.getCertNum()) && StringUtils.isNotEmpty(body.getBusinessUrl())
                    && StringUtils.isNotEmpty(body.getLegalCertNum()) && StringUtils.isNotEmpty(body.getReverserUrl())
                    && StringUtils.isNotEmpty(body.getFrontUrl())){
                isRealNum = 0;
            }

            // 手机号查询客户信息是否存在
            List<TCustomInfoManagerVo> customVoList = null;
            try {
                customVoList = tCustomInfoManagerDao.selectCustomInfoByPhoneNOEqProvId(body.getPhone(), body.getProvId());
            } catch (DataAccessException e) {
                log.error("数据库查询异常", e);
                throw new RuntimeException("数据库查询异常");
            }

            // 为空更新客户信息
            if (null != customVoList && customVoList.size() >= 1) {
                return UserResUtil.buildFail(USER_CUSTOM_INFO_ISEXIT);
            }

            if (!StringUtils.isEmpty(body.getLegalPhone())) {
                //手机号查询客户信息是否存在
                try {
                    customVoList = tCustomInfoManagerDao.selectCustomInfoByPhoneNOEqProvId(body.getLegalPhone(), body.getProvId());
                } catch (DataAccessException e) {
                    log.error("数据库查询异常", e);
                    throw new RuntimeException("数据库查询异常");
                }

                // 关联客户信息已存在
                if (null != customVoList && customVoList.size() >= 1) {
                    return UserResUtil.buildFail(USER_CUSTOM_INFO_ISEXIT);
                }
            }
        }

        TCustomInfoManagerVo vo;
        try {
            vo = tCustomInfoManagerDao.selectByPrimaryKey(body.getProvId());
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (ObjectUtils.isEmpty(vo)) {
            log.info("未查询到该客户管理信息");
            return Result.build(null, USER__CUSTOM_INFO_NULL.getCode(), USER__CUSTOM_INFO_NULL.getMessage());
        }

        if (!StringUtils.isEmpty(vo.getCertNum()) && StringUtils.isNotEmpty(body.getCertNum())) {
            int result;
            try {
                result = tCustomInfoManagerDao.selectByCertNumCount(body.getCertNum(), body.getProvId());
            } catch (DataAccessException e) {
                log.error("数据库查询异常", e);
                throw new RuntimeException("数据库查询异常");
            }
            if (result >= 1) {
                return Result.build(null, USER__CUSTOM_CERTNUM_ISEXIT.getCode(), USER__CUSTOM_CERTNUM_ISEXIT.getMessage());
            }
        }

        vo.setCustomTp(body.getCustomTp());
        vo.setProvNm(body.getProvNm());
        vo.setShrtNm(body.getShrtNm());
        vo.setCertType(body.getCertTp());
        vo.setCertNum(body.getCertNum());
        vo.setCertAddr(body.getCertAddr());
        vo.setCertPrd(body.getCertPrd());
        vo.setPhone(body.getPhone());
        vo.setLegalName(body.getLegalName());
        vo.setLegalPhone(body.getLegalPhone());
        vo.setLegalCertTp(body.getLegalCertTp());
        vo.setLegalCertNum(body.getLegalCertNum());
        vo.setAgentName(body.getAgentName());
        vo.setAgentPhone(body.getAgentPhone());
        vo.setAgentAddr(body.getAgentAddr());
        vo.setTermPrctn(body.getTermPrctn());
        vo.setTermDueDt(body.getTermDueDt());
        vo.setOperId(param.getHead().getOperator());
        vo.setUpTime(new Date());
        vo.setAgentCertNo(body.getAgentCertNo());
        vo.setReverserUrl(body.getReverserUrl());
        vo.setFrontUrl(body.getFrontUrl());
        vo.setBusinessUrl(body.getBusinessUrl());
        // 是否实名 0-实名  1-未实名
        vo.setIsReal("" + isRealNum);

        vo.setAgentFrontUrl(body.getAgentFrontUrl());
        vo.setAgentReverserUrl(body.getAgentReverserUrl());
        vo.setAgentTermBookUrl(body.getAgentTermBookUrl());
        int result;
        try {
            result = tCustomInfoManagerDao.updateByPrimaryKeySelective(vo);
        } catch (DataAccessException e) {
            log.error("数据更新异常", e);
            throw new RuntimeException("数据库更新异常");
        }
        if (result != 1) {
            return UserResUtil.buildFail(USER__CUSTOM_INFO_UPDATE);
        }
        log.info("用户业务-更新客户管理成功");
        return UserResUtil.getResultSuccessDto();
    }

    /**
     * 禁用客户
     *
     * @param param
     * @return
     */
    @Override
    public Result<ResultOutDto> disableCustomInfo(Request<DisableCustomInfoInDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(USER_PARAM_NULL.getCode(), "输入参数为为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(USER_PARAM_NULL.getCode(), "输入参数为为空");
        }
        if (StringUtils.isEmpty(param.getBody().getProvId())) {
            return UserResUtil.paramFail("客户编号为空");
        }
        if (StringUtils.isEmpty(param.getBody().getStatus())) {
            return UserResUtil.paramFail("状态为空");
        }

        if (!TCustomInfoManagerVo.DISABLE.equals(param.getBody().getStatus()) && !TCustomInfoManagerVo.NORMAL.equals(param.getBody().getStatus())) {
            return Result.build(null, USER_PARAM_ERROR.getCode(), "输入状态不正确");
        }

        TCustomInfoManagerVo vo = null;
        try {
            vo = tCustomInfoManagerDao.selectByPrimaryKey(param.getBody().getProvId());
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (ObjectUtils.isEmpty(vo)) {
            log.info("未查询到该客户管理信息");
            return Result.build(null, USER__CUSTOM_INFO_NULL.getCode(), USER__CUSTOM_INFO_NULL.getMessage());
        }
        int result = 0;
        try {
            result = tCustomInfoManagerDao.updateDisableStatus(param.getBody().getProvId(), param.getBody().getStatus());
        } catch (DataAccessException e) {
            log.error("数据更新异常", e);
            throw new RuntimeException("数据库更新异常");
        }
        if (result != 1) {
            return UserResUtil.buildFail(USER__CUSTOM_INFO_DISABLE);
        }
        log.info("用户业务-禁用/启用客户成功");
        return UserResUtil.getResultSuccessDto();

    }

    /**
     * 下载溯源码
     *
     * @param param
     * @return
     */
    @Override
    public Result<DownLoadqrcodeOutDto> downLoadqrcod(Request<DownLoadqrcodeInDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(USER_PARAM_NULL.getCode(), "输入参数为为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(USER_PARAM_NULL.getCode(), "输入参数为为空");
        }
        if (StringUtils.isEmpty(param.getBody().getProvId())) {
            return Result.build(USER_PARAM_NULL.getCode(), "客户编号为空");
        }
        TCustomInfoManagerVo vo = null;
        try {
            vo = tCustomInfoManagerDao.selectByPrimaryKey(param.getBody().getProvId());
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (ObjectUtils.isEmpty(vo)) {
            log.info("用户业务-未查询到该客户管理信息");
            return Result.build(null, USER__CUSTOM_INFO_NULL.getCode(), USER__CUSTOM_INFO_NULL.getMessage());
        }
        if (StringUtils.isEmpty(vo.getQrcodeUrl())) {
            log.info("用户业务-溯源二维码url为空");
            return Result.build(null, USER__CUSTOM_QRCODE_ISNULL.getCode(), USER__CUSTOM_QRCODE_ISNULL.getMessage());
        }
        log.info("用户业务-下载溯源二维码成功");
        DownLoadqrcodeOutDto dto = new DownLoadqrcodeOutDto();
        dto.setQrcodeUrl(vo.getQrcodeUrl());
        Result.build(dto, SUCCESS);
        return Result.build(dto, SUCCESS);

    }

    /**
     * 查询用户信息 DISP20210168
     *
     * @param param Request<QuryCustomInfoInDto>
     * @param userNo userNo
     * @return Result<QuryCustomInfoOutDto>
     */
    @Override
    public Result<QuryCustomInfoOutDto> quryCustomInfo(String userNo, Request<QuryCustomInfoInDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(null, USER_PARAM_NULL.getCode(), USER_PARAM_NULL.getMessage());
        }

        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(null, USER_PARAM_NULL.getCode(), USER_PARAM_NULL.getMessage());
        }

        String chanlNo = param.getHead().getChanlNo();
        QuryCustomInfoInDto inDto = param.getBody();
        log.info("DISP20210168 body=" + inDto + "------>chanlNo=" + chanlNo);
        if (inDto.getPageNum() == null || inDto.getPageSize() == null) {
            log.info("用户业务-分页参数不能为空");
            return Result.build(USER_PARAM_NULL.getCode(), "分页参数为空");
        }

        if (inDto.getPageSize() <= 0) {
            log.info("用户业务-分页条数输入错误");
            return Result.build(USER_PARAM_ERROR.getCode(), "分页条数输入错误");
        }

        if (inDto.getPageNum() <= 0) {
            log.info("用户业务-分页页数输入错误");
            return Result.build(USER_PARAM_ERROR.getCode(), "分页页数输入错误");
        }

        int strNum = (inDto.getPageNum() - 1) * inDto.getPageSize();
        inDto.setStrNum(strNum);
        QuryCustomInfoOutDto outBody = new QuryCustomInfoOutDto();
        List<QuryCustomInfoOutParamDto> reslistOutDto = new ArrayList<>();
        List<QuryCustomInfoOutParamDto> listOutDto = null;
        // 客户银行卡信息
        List<TCustomBankcardVo> cardListVO = null;
        TCustomBankcardVo cardVo = null;

        if (chanlNo.equals("04")) {
            // 银行卡号不为空，客户编号为空
            if (!StringUtils.isEmpty(inDto.getBankNo()) && StringUtils.isEmpty(inDto.getProvId())) {
                log.info("用户业务-根据银行卡号查询客户管理信息");
                try {
                    cardListVO = iTCustomBankcardDao.selectByPrimaryKeyBankNo(inDto.getBankNo());
                } catch (DataAccessException e) {
                    log.error("数据库查询异常", e);
                    throw new RuntimeException("数据库查询异常");
                }

                if (ObjectUtils.isEmpty(cardListVO) || cardListVO.size() <= 0) {
                    log.info("用户业务-银行卡号关联客户编号为null");
                    outBody.setTolPageNum(0);//总条数
                    return Result.build(outBody, SUCCESS);
                }

                int tolNum = 0;
                try {
                    tolNum = tCustomInfoManagerDao.selectCountByProvId(inDto, cardListVO);
                } catch (DataAccessException e) {
                    log.error("数据库查询异常", e);
                    throw new RuntimeException("数据库查询异常");
                }

                outBody.setTolPageNum(tolNum);
                if (tolNum <= 0) {
                    log.info("用户业务-查询客户管理信息数为0");
                    return Result.build(outBody, SUCCESS);
                }

                try {
                    listOutDto = tCustomInfoManagerDao.selectByPrimaryKeyWhereByProvId(inDto, cardListVO);
                } catch (DataAccessException e) {
                    log.error("数据库查询异常", e);
                    throw new RuntimeException("数据库查询异常");
                }

                for (QuryCustomInfoOutParamDto vo : listOutDto) {
                    vo.setBankNo(param.getBody().getBankNo());
                    reslistOutDto.add(vo);
                }

                log.info("用户业务-客户管理信息查询成功");
                outBody.setList(reslistOutDto);

                return Result.build(outBody, SUCCESS);
            }

            // 银行卡号，客户编号都不为空
            if (!StringUtils.isEmpty(inDto.getBankNo()) && !StringUtils.isEmpty(inDto.getProvId())) {
                try {
                    cardVo = iTCustomBankcardDao.selectByPrimaryKey(inDto.getProvId(), inDto.getBankNo());
                } catch (DataAccessException e) {
                    log.error("数据库查询异常", e);
                    throw new RuntimeException("数据库查询异常");
                }

                if (ObjectUtils.isEmpty(cardVo)) {
                    log.info("用户业务-银行卡号关联客户编号为null");
                    outBody.setTolPageNum(0);//总条数
                    return Result.build(outBody, SUCCESS);
                }
            }
        }

        // 查询总数
        int tolNum = 0;
        try {
            if (chanlNo.equals("05")) {
                // 智慧贵农
                tolNum = tCustomInfoManagerDao.selectCountOrValue(inDto);
            } else {
                tolNum = tCustomInfoManagerDao.selectCount(inDto);
            }
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }

        if (tolNum <= 0) {
            log.info("用户业务-查询客户管理信息数为0");
            outBody.setTolPageNum(0);// 总条数
            return Result.build(outBody, SUCCESS);
        }

        try {
            if (chanlNo.equals("05")) {// 智慧贵农
                listOutDto = tCustomInfoManagerDao.selectOrValue(inDto);
            } else {
                listOutDto = tCustomInfoManagerDao.selectByPrimaryKeyWhere(inDto);
            }
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }

        log.info("用户业务-客户管理信息查询成功");
        outBody.setList(listOutDto);
        outBody.setTolPageNum(tolNum);
        return Result.build(outBody, SUCCESS);
    }

    /**
     * 导出客户信息
     *
     * @param param ExportCustomInfoInDto
     * @return ExportCustomInfoInDto
     */
    @Override
    public Result<ExportCustomInfoOutDto> exportCustomInfo(Request<ExportCustomInfoInDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(null, USER_PARAM_NULL.getCode(), USER_PARAM_NULL.getMessage());
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(null, USER_PARAM_NULL.getCode(), USER_PARAM_NULL.getMessage());
        }
        if (param.getBody().getList().isEmpty() || param.getBody().getList().size() <= 0) {
            return Result.build(null, USER_PARAM_NULL.getCode(), USER_PARAM_NULL.getMessage());
        }

        for (ExportCustomInfoInParamDto infoInDto : param.getBody().getList()) {
            if (StringUtils.isEmpty(infoInDto.getProvId())) {
                return Result.build(null, USER_PARAM_NULL.getCode(), "商户编号为空");
            }
        }
        ExportCustomInfoInDto body = param.getBody();
        log.info("DISP20210169 导出客户信息body=" + body);

        List<TCustomInfoManagerVo> voList = null;
        try {
            voList = tCustomInfoManagerDao.selectByWhereByProvIdList(body.getList());
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (ObjectUtils.isEmpty(voList) || voList.size() <= 0) {
            log.info("用户业务-根据客户编号查询客户管理信息为空");
            return Result.build(null, USER_DATA_NO_ERROR.getCode(), USER_DATA_NO_ERROR.getMessage());
        }

        // 生成电子表格
        HSSFWorkbook workBook = new HSSFWorkbook();
        HSSFSheet hssfsheet = workBook.createSheet("客户信息查询导出");
        HSSFRow row = hssfsheet.createRow(0);

        // 设置标题字体格式
        HSSFFont titlFont = workBook.createFont();
        titlFont.setFontName("黑体");
        titlFont.setFontName("宋体");
        titlFont.setFontHeightInPoints((short) 12);
        titlFont.setBold(true);

        // 设置列名字体格式
        HSSFFont cellNameFont = workBook.createFont();
        cellNameFont.setFontName("黑体");
        cellNameFont.setFontName("宋体");
        cellNameFont.setBold(true);
        cellNameFont.setFontHeightInPoints((short) 10);

        // 标题样式
        HSSFCellStyle titlStyle = workBook.createCellStyle();
        titlStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        titlStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        titlStyle.setFont(titlFont);

        // 设置日期单元格格式
        HSSFCellStyle dateStyle1 = workBook.createCellStyle();
        CreationHelper creationHelper = workBook.getCreationHelper();
        dateStyle1.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd"));
        dateStyle1.setAlignment(HorizontalAlignment.LEFT);
        HSSFCellStyle dateStyle2 = workBook.createCellStyle();
        dateStyle2.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd hh:mm:ss"));
        dateStyle2.setAlignment(HorizontalAlignment.LEFT);

        //单元格样式边框
        HSSFCellStyle tableStyle = workBook.createCellStyle();
        this.setBorder(tableStyle);
        //列名样式边框
        HSSFCellStyle cellNameStyle = workBook.createCellStyle();
        this.setBorder(cellNameStyle);
        cellNameStyle.setFont(cellNameFont);
        //标题样式边框
        this.setBorder(titlStyle);
        //日期格式边框
        this.setBorder(dateStyle1);
        this.setBorder(dateStyle2);

        // 列名
        String[] cellNames = new String[]{"客户名称", "客户编号", "客户简称", "手机号码", "证件号码", "客户类型", "证件类型", "证件住址",
                "证件有效期", "客户状态", "是否实名", "是否允许提现", "来源", "操作人", "入驻日期"};

        // 标题
        HSSFRow nextRow = hssfsheet.createRow(1);
        HSSFCell hssfcell = null;
        hssfcell = row.createCell(0);
        hssfcell.setCellValue("客户信息查询导出");
        hssfcell.setCellStyle(titlStyle);

        // 其它每个单元格设置边框，再进行合并才会有边框
        for (int i = 1; i < cellNames.length; i++) {
            hssfcell = row.createCell(i);
            hssfcell.setCellStyle(titlStyle);
        }

        // 合并标题单元格
        CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, cellNames.length - 1);
        hssfsheet.addMergedRegion(region1);

        // 设置列名
        for (int i = 0; i < cellNames.length; i++) {
            hssfcell = nextRow.createCell(i);
            hssfcell.setCellValue(cellNames[i]);
            hssfcell.setCellStyle(cellNameStyle);
        }

        String chanlNoName = "";
        int i = 2;
        // 列值
        for (TCustomInfoManagerVo vo : voList) {
            nextRow = hssfsheet.createRow(i++);
            int k = 0;

            // 客户名称
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(vo.getProvNm());
            hssfcell.setCellStyle(tableStyle);

            // 客户编号
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(vo.getProvId());
            hssfcell.setCellStyle(tableStyle);

            // 客户简称
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(vo.getShrtNm());
            hssfcell.setCellStyle(tableStyle);

            // 手机号码
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(vo.getPhone());
            hssfcell.setCellStyle(tableStyle);

            // 证件号码
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(vo.getCertNum());
            hssfcell.setCellStyle(tableStyle);

            // 客户类型
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(CustomTpEnum.getValue(vo.getCustomTp()));
            hssfcell.setCellStyle(tableStyle);

            // 证件类型
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(CertTpEnum.getValue(vo.getCertType()));
            hssfcell.setCellStyle(tableStyle);

            // 证件住址
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(vo.getCertAddr());
            hssfcell.setCellStyle(tableStyle);

            // 证件有效期
            hssfcell = nextRow.createCell(k++);
            if (!ObjectUtils.isEmpty(vo.getCertPrd())) {
                hssfcell.setCellValue(vo.getCertPrd());
            }
            hssfcell.setCellStyle(dateStyle1);

            // 状态
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(CustomStatusEnum.getValue(vo.getStatus()));
            hssfcell.setCellStyle(tableStyle);

            // 是否实名
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(IsRealEnum.getValue(vo.getIsReal()));
            hssfcell.setCellStyle(tableStyle);

            // 是否允许提现 0允许提现, 1不允许提现
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(StringUtils.isEmpty(vo.getIsWithdraw()) || "0".equals(vo.getIsWithdraw())
                    ? "是" : "否");
            hssfcell.setCellStyle(tableStyle);


            // 来源 渠道类型：01-贵农购 02-微信小程序 03-支付宝小程序 04-农批系统 05-智慧贵农app 06-外联服务
            if (StringUtils.isNotEmpty(vo.getChanlNo())){
                switch (vo.getChanlNo()){
                    case "01": {
                        chanlNoName = "贵农购";
                        break;
                    }
                    case "02": {
                        chanlNoName = "微信小程序";
                        break;
                    }
                    case "03": {
                        chanlNoName = "支付宝小程序";
                        break;
                    }
                    case "04": {
                        chanlNoName = "农批系统";
                        break;
                    }
                    case "05": {
                        chanlNoName = "智慧贵农app";
                        break;
                    }
                    case "06": {
                        chanlNoName = "外联服务";
                        break;
                    }
                    default: {
                        chanlNoName = "";
                        break;
                    }
                }
            }else {
                chanlNoName = "";
            }
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(chanlNoName);
            hssfcell.setCellStyle(tableStyle);

            // 操作人
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(StringUtils.isEmpty(vo.getOperName()) ? "" : vo.getOperName());
            hssfcell.setCellStyle(tableStyle);

            // 入驻日期
            hssfcell = nextRow.createCell(k++);
            if (!ObjectUtils.isEmpty(vo.getCreatTime())) {
                hssfcell.setCellValue(vo.getCreatTime());
            }
            hssfcell.setCellStyle(dateStyle2);
        }

        //宽度自适应大小
        for (int z = 0; z < cellNames.length; z++) {
            hssfsheet.autoSizeColumn(z);
        }
        FileOutputStream out = null;
        String fileName = System.currentTimeMillis() + ".xls";
        String uploadMkdir = "custominfoxls";
        File file = new File(fileLocalPath + "/" + uploadMkdir + "/" + fileName);
        try {
            out = new FileOutputStream(file);
            workBook.write(out);
        } catch (Exception e) {
            log.error("导出客户信息失败" + e);
            throw new RuntimeException("导出客户信息失败");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                workBook.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        //组请求报文
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("uploadMkdir", uploadMkdir);
        multipartBodyBuilder.part("file", new FileSystemResource(file));
        MultiValueMap<String, HttpEntity<?>> multiValueBody = multipartBodyBuilder.build();

        //上传文件服务
        Result result = null;
        try {
            result = restTemplate2.postForObject("http://" + "disPart-files" + "/securityCenter/DISP20210105", multiValueBody, Result.class);
        } catch (Exception e) {
            log.error("调用文件上传服务失败" + e);
            throw new RuntimeException("调用文件上传服务失败");
        }
        if (ObjectUtils.isEmpty(result) || result.getCode() != 200) {
            return Result.build(null, USER_UPLOAD_FILE_FAIL.getCode(), USER_UPLOAD_FILE_FAIL.getMessage());
        }

        log.info("用户业务-导出客户信息成功");
        ExportCustomInfoOutDto outBody = new ExportCustomInfoOutDto();
        outBody.setFileUrl((String) result.getData());
        return Result.build(outBody, SUCCESS);
    }

    /**
     * 常用银行信息查询
     *
     * @return TCommonBankNameVo 银行常用列表
     * @author 黄贵川
     * @date 2021/08/18
     */
    @Override
    public Result<QueryTCommonBanNameListDto> selectTCommonBankNameVoList() {
        log.info("DISP20210295 常用银行信息查询");
        // 查询总数
        int tolNum = 0;
        try {
            tolNum = tCommonBankNameDao.selectCount();
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        QueryTCommonBanNameListDto outBody = new QueryTCommonBanNameListDto();
        if (tolNum <= 0) {
            log.info("用户业务-常用银行信息查询数为0");
            outBody.setTolPageNum(0);
            return Result.build(outBody, SUCCESS);
        }
        outBody.setTolPageNum(tolNum);

        List<TCommonBankNameVo> tCommonBankNameVoList;
        try {
            tCommonBankNameVoList = tCommonBankNameDao.selectTCommonBankNameVoList();
            if (!tCommonBankNameVoList.isEmpty() && tCommonBankNameVoList.size() > 0) {
                outBody.setList(tCommonBankNameVoList);
            }
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }

        return Result.build(outBody, SUCCESS);
    }

    /**
     * 客户卡片绑定银行卡
     *
     * @param param TCustomBankcardVo
     * @return TCustomBankcardVo
     * @author 黄贵川
     * @date 2021/08/11
     */
    @Override
    public Result<ResultOutDto> addCustomBankcard(Request<TCustomBankcardVo> param) {
        log.info("DISP20210203 客户卡片绑定银行卡 param:" + JSON.toJSONString(param));
        TCustomBankcardVo vo = param.getBody();
        try {
            /**
             * 客户1--->多会员卡--->多银行卡
             * 1张会员卡可以绑多张银行卡(1张银行卡可以绑在多张会员卡上, 1张银行卡不能重复绑在同一张会员卡上)
             */
            TCustomBankcardVo entity = new TCustomBankcardVo();
            entity.setBankNo(vo.getBankNo());
            entity.setProvAcct(vo.getProvAcct());
            List<TCustomBankcardVo> customBankcardList = iTCustomBankcardDao.findCustomBankcardList(entity);
            if (customBankcardList != null && customBankcardList.size() > 0) {
                return UserResUtil.buildFail(USER_CUSTOM_BANK_CARD_EXIST);
            }

            if (!StringUtils.isEmpty(vo.getIsCcb()) && Integer.parseInt(vo.getIsCcb()) == 1) {
                DISP20210284RepVo disp20210284RepVo = new DISP20210284RepVo();
                // 转入一级行行号
                //disp20210284RepVo.setAccBranchCode(vo.getBankPayNo());
                // 转入账户
                disp20210284RepVo.setAccNo(vo.getBankNo());

                Request request = new Request();
                request.setHead(param.getHead());
                request.setBody(disp20210284RepVo);
                Result result = restTemplate2.postForObject("http://" + "disPart-busine-commons" + "/securityCenter/DISP20210284",
                        request, Result.class);
                if (request != null) {
                    JSONObject resultJson = (JSONObject) JSONObject.toJSON(result);
                    // 账户状态
                    String code = resultJson.get("code").toString();
                    if (!code.equals("200")) {
                        return UserResUtil.buildFail(USER_MESSAGE_SELECT_ERROR);
                    }

                    JSONObject dataJson = (JSONObject) JSONObject.toJSON(result.getData());
                    // 账户状态
                    String accStatus = dataJson.get("accStatus").toString();
                    if (!accStatus.equals("正常")) {
                        return UserResUtil.buildFail(USER_ACC_STATUS_ERROR);
                    }

                    // 账户名称
                    String accName = dataJson.get("accName").toString();
                    if (!vo.getProvNm().equals(accName)) {
                        return UserResUtil.buildFail(USER_BANK_NAME_DISSIMILARITY);
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
            return Result.fail();
        }

        int addNum;
        try {
            addNum = iTCustomBankcardDao.addCustomBankcard(vo);
        } catch (DataAccessException e) {
            log.error("数据插入异常", e);
            return Result.fail();
        }
        if (addNum != 1) {
            return UserResUtil.buildFail(USER_ADD_CUSTOM_BANKCARD_ERROR);
        }
        log.info("用户业务-客户卡片绑定银行卡成功");
        return UserResUtil.getResultSuccessDto();
    }

    /**
     * 客户已绑定银行卡查询
     *
     * @param param TCustomBankcardVo
     * @return ResultOutDto
     * @author 黄贵川
     * @date 2021/08/25
     */
    @Override
    public Result findCustomBankcardList(Request<TCustomBankcardVo> param) {
        log.info("DISP20210306 客户已绑定银行卡查询 param:" + JSON.toJSONString(param));
        TCustomBankcardVo body = param.getBody();
        if (body == null) {
            return Result.build(USER_PARAM_NULL.getCode(), "输入参数为为空");
        }

        // 有分页参数才做分页查询
        if (body.getCurPage() != null && body.getCurPage() > 0 && body.getPageSize() > 0) {
            Integer curPage = (body.getCurPage() - 1) * body.getPageSize();
            body.setCurPage(curPage);
        } else {
            body.setPageSize(0);
        }

        TCustomBankcardDto tCustomBankcardDto = new TCustomBankcardDto();
        try {
            List<TCustomBankcardVo> tCustomBankcardVoList = iTCustomBankcardDao.findCustomBankcardList(body);
            tCustomBankcardDto.setReportList(tCustomBankcardVoList);

            if (body.getPageSize() > 0) {
                Integer toleNum = iTCustomBankcardDao.findCustomBankcardCount(body);
                tCustomBankcardDto.setTolPageNum(toleNum);
            }
        } catch (Exception e) {
            log.error("进出场业务-客户已绑定银行卡查询失败", e);
            throw new RuntimeException("进出场业务-客户已绑定银行卡查询失败");
        }

        return Result.build(tCustomBankcardDto, EntranceResult_WEnum.SUCCESS);
    }

    /**
     * 客户卡片解绑银行卡
     *
     * @param param TCustomBankcardVo
     * @return ResultOutDto
     * @author 黄贵川
     * @date 2021/08/11
     */
    @Override
    public Result<ResultOutDto> deleteCustomBankcard(Request<TCustomBankcardVo> param) {
        log.info("DISP20210204 客户卡片解绑银行卡 param:" + JSON.toJSONString(param));
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(USER_PARAM_NULL.getCode(), "输入参数为为空");
        }

        int deleteNum;
        try {
            deleteNum = iTCustomBankcardDao.deleteCustomBankcard(param.getBody());
        } catch (DataAccessException e) {
            log.error("数据删除异常", e);
            throw new RuntimeException("数据库删除异常");
        }

        if (deleteNum != 1) {
            return UserResUtil.buildFail(USER_DELETE_CUSTOM_BANKCARD_ERROR);
        }

        log.info("用户业务-客户卡片解绑银行卡成功");
        return UserResUtil.getResultSuccessDto();
    }

    /**
     * 会员身份证信息实名认证
     *
     * @param param TCustomInfoManagerVo
     * @return ResultOutDto
     * @author 黄贵川
     * @date 2021/08/11
     */
    @Override
    public Result<ResultOutDto> realNameTCustomInfoManager(Request<TCustomInfoManagerVo> param) {
        TCustomInfoManagerVo vo = param.getBody();
        // 身份证正面url
        String frontUrl = vo.getFrontUrl();
        // 身份证反面URL
        String reverserUrl = vo.getReverserUrl();
        // 客户名称
        String provNm = vo.getProvNm();
        // 证件号码
        String certNum = vo.getCertNum();

        // 图片识别 ???


        int updateNum;
        try {
            // 更新
            vo.setIsReal("1");
            updateNum = tCustomInfoManagerDao.updateByPrimaryKeySelective(vo);
        } catch (DataAccessException e) {
            log.error("数据更新异常", e);
            throw new RuntimeException("数据库更新异常");
        }
        if (updateNum != 1) {
            return UserResUtil.buildFail(USER__CUSTOM_INFO_UPDATE);
        }
        log.info("用户业务-会员身份证信息实名认证成功");
        return UserResUtil.getResultSuccessDto();
    }

    /**
     * 查询客户信息详情
     *
     * @param param Request<ExportCustomInfoInParamDto>
     * @return Result<QuryCustomInfoOutParamDto>
     */
    @Override
    public Result<QuryCustomInfoOutParamDto> quryCustomInfoDatils(Request<ExportCustomInfoInParamDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(USER_PARAM_NULL.getCode(), "输入参数为为空");
        }

        ExportCustomInfoInParamDto body = param.getBody();
        log.info("DISP20210289 body=" + body);
        if (ObjectUtils.isEmpty(body)) {
            return Result.build(USER_PARAM_NULL.getCode(), "输入参数为为空");
        }
        if (StringUtils.isEmpty(body.getProvId())) {
            return Result.build(USER_PARAM_NULL.getCode(), "客户编号为空");
        }

        QuryCustomInfoOutParamDto vo = null;
        try {
            vo = tCustomInfoManagerDao.selectByPrimaryKeyDatils(body.getProvId());
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }

        if (ObjectUtils.isEmpty(vo)) {
            log.info("用户业务-未查询到该客户管理信息");
            return Result.build(null, USER_DATA_NO_ERROR.getCode(), USER_DATA_NO_ERROR.getMessage());
        }
        vo.setBusinessRange(vo.getBusinessScope());
        log.info("用户业务-查询客户管理详情成功");
        return Result.build(vo, SUCCESS);
    }

    /**
     * 会员信息补充
     *
     * @param param 客户信息管理
     * @return TCustomInfoManagerVo 客户信息管理
     * @author 黄贵川
     * @date 2021/08/17
     */
    @Override
    public Result<ResultOutDto> updateTCustomInfoManagerVo(Request<TCustomInfoManagerVo> param) {
        log.info("DISP20210253会员信息补充param:" + JSON.toJSONString(param));
        TCustomInfoManagerVo customInfoManagerVo = param.getBody();
        customInfoManagerVo.setIsReal("0");
        int updateNum = tCustomInfoManagerDao.updateByPrimaryKeySelective(customInfoManagerVo);
        if (updateNum != 1) {
            log.info("用户业务-会员信息补充失败");
            return UserResUtil.buildFail(USER__CUSTOM_INFO_UPDATE);
        }
        log.info("用户业务-会员信息补充成功");
        return UserResUtil.getResultSuccessDto();
    }

    @Override
    public Result<ResultOutDto> setIsWithdraw(Request<DisableCustomInfoInDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(USER_PARAM_NULL.getCode(), "输入参数为为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(USER_PARAM_NULL.getCode(), "输入参数为为空");
        }
        if (StringUtils.isEmpty(param.getBody().getProvId())) {
            return UserResUtil.paramFail("客户编号为空");
        }
        if (StringUtils.isEmpty(param.getBody().getIsWithdraw())) {
            return UserResUtil.paramFail("是否允许提现标志为空");
        }

        if (!"0".equals(param.getBody().getIsWithdraw()) && !"1".equals(param.getBody().getIsWithdraw())) {
            return Result.build(null, USER_PARAM_ERROR.getCode(), "输入参数不正确");
        }

        TCustomInfoManagerVo vo = null;
        try {
            vo = tCustomInfoManagerDao.selectByPrimaryKey(param.getBody().getProvId());
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (ObjectUtils.isEmpty(vo)) {
            log.info("未查询到该客户管理信息");
            return Result.build(null, USER__CUSTOM_INFO_NULL.getCode(), USER__CUSTOM_INFO_NULL.getMessage());
        }
        int result = 0;
        try {
            result = tCustomInfoManagerDao.updateWithDrawStatus(param.getBody().getProvId(), param.getBody().getIsWithdraw());
        } catch (DataAccessException e) {
            log.error("数据更新异常", e);
            throw new RuntimeException("数据库更新异常");
        }
        if (result != 1) {
            return UserResUtil.buildFail(USER__CUSTOM_INFO_UPDATE);
        }
        log.info("用户业务-设置是否允许客户提现成功");
        return UserResUtil.getResultSuccessDto();
    }

    /**
     * 设置单元格边框
     *
     * @param style
     */
    private void setBorder(HSSFCellStyle style) {
        style.setBorderTop(BorderStyle.MEDIUM);//上边框
        style.setBorderBottom(BorderStyle.MEDIUM);//下边框
        style.setBorderLeft(BorderStyle.MEDIUM);//左边框
        style.setBorderRight(BorderStyle.MEDIUM);//右边框
    }
}
