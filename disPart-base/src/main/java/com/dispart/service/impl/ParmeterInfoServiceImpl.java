package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.dto.parmeterdto.ParmeterInfoDto;
import com.dispart.dto.parmeterdto.ParmeterSelectInVo;
import com.dispart.dto.parmeterdto.ParmeterSelectOutVo;
import com.dispart.enums.ParmResultCodeEnum;
import com.dispart.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.dispart.model.ParmeterInfo;
import com.dispart.dao.mapper.ParmeterInfoMapper;
import com.dispart.service.ParmeterInfoService;
import org.springframework.util.StringUtils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhaoshihao
 * @version 1.0.0
 * @title ParmeterInfoServiceImpl
 * @creat 2021/6/16 19:31
 * @Copyright 2020-2021
 */
@Service
@Slf4j
public class ParmeterInfoServiceImpl implements ParmeterInfoService {

    @Resource
    private ParmeterInfoMapper parmeterInfoMapper;

    /**
     * @author zhaoshihao
     * @date 2021/6/16
     */
    @Override
    public Result selectByPrimaryKey(ParmeterSelectInVo inVo) {
        log.info("---参数查询开始，传入参数：{}", JSON.toJSONString(inVo));

        if (inVo.getPageNum() != null && inVo.getPageSize() != null) {
            inVo.setPageNum((inVo.getPageNum() - 1) * inVo.getPageSize());
        }
        List<ParmeterInfo> infos;
        Integer count;
        try {
            infos = parmeterInfoMapper.selectParmeter(inVo);
            count = parmeterInfoMapper.selectParmeterCount(inVo);
        } catch (Exception e) {
            log.error("---查询参数列表失败，查询异常---", e);
            throw new RuntimeException("查询参数列表失败，查询异常");
        }
        List<ParmeterInfoDto> dtos = new ArrayList<>();

        //查询结果转为DTO
        for (ParmeterInfo info : infos) {
            ParmeterInfoDto dto = new ParmeterInfoDto();
            BeanUtils.copyProperties(info, dto);
            dtos.add(dto);
        }

        //组装返回结果
        ParmeterSelectOutVo outVo = new ParmeterSelectOutVo();
        outVo.setTolPageNum(count);
        outVo.setParmeterList(dtos);
        log.info("---查询参数列表结束---{}",JSON.toJSONString(outVo));
        return Result.ok(outVo);
    }

    /**
     * 根据主键更新参数表
     *
     * @author zhaoshihao
     * @date 2021/6/16
     */
    @Override
    public Result updateByPrimaryKey(ParmeterInfoDto inVo) {
        log.info("---参数修改开始,传入参数：", JSON.toJSONString(inVo));
        if (StringUtils.isEmpty(inVo.getParamNm())) {
            return Result.build(ParmResultCodeEnum.PARAM_NULL.getCode(), "参数名不能为空");
        }
        if (StringUtils.isEmpty(inVo.getParamType())) {
            return Result.build(ParmResultCodeEnum.PARAM_NULL.getCode(), "参数类型不能为空");
        }
        ParmeterInfo parmeterInfo = new ParmeterInfo();
        BeanUtils.copyProperties(inVo, parmeterInfo);
        int i;
        try {
            i = parmeterInfoMapper.updateByPrimaryKeySelective(parmeterInfo);
        } catch (Exception e) {
            log.error("---数据库更新异常---",e);
            throw new RuntimeException("数据库更新异常");
        }
        if (i < 1) {
            log.info("---修改记录不存在---");
            return Result.build(ParmResultCodeEnum.UPDATE_FAIL.getCode(), ParmResultCodeEnum.UPDATE_FAIL.getMessage());
        }
        log.info("---修改成功----");
        return Result.ok();
    }

    @Override
    public Result secretkeyInit() {
        log.info("---开始获取物流园平台秘钥---");
        ParmeterSelectInVo publicKey = new ParmeterSelectInVo("01", "local.publicKey");
        ParmeterSelectInVo privateKey = new ParmeterSelectInVo("01", "local.privateKey");
        log.info("---开始查询公钥---");
        ParmeterInfo key1 = SecretKeyDeal(publicKey);
        log.info("---开始查询私钥---");
        ParmeterInfo key2 = SecretKeyDeal(privateKey);
        List<ParmeterInfo> keys = new ArrayList<>();
        keys.add(key1);
        keys.add(key2);
        log.info("---物流园秘钥获取结束，返回数据：{}",keys);
        return Result.ok(keys);
    }

    private ParmeterInfo SecretKeyDeal(ParmeterSelectInVo secretKey) {
        try {
            ParmeterInfo parmeterInfo = parmeterInfoMapper.selectByTypeAndNm(secretKey.getParamType(), secretKey.getParamNm());
            if (parmeterInfo != null) {
                log.info("查询结束");
                return parmeterInfo;
            }
            log.info("开始生成秘钥" + secretKey.getParamNm());
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            if (secretKey.getParamNm().equals("local.publicKey")) {
                RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
                String publicKeyValue = Base64.encodeBase64String(publicKey.getEncoded());
                ParmeterInfo publicKeyInfo = new ParmeterInfo();
                BeanUtils.copyProperties(secretKey, publicKeyInfo);
                publicKeyInfo.setParamVal(publicKeyValue);
                publicKeyInfo.setParamDesc("平台的公钥");
                publicKeyInfo.setUpdateDt(new Date());
                publicKeyInfo.setPermission("1");
                parmeterInfoMapper.insertSelective(publicKeyInfo);
            }
            if (secretKey.getParamNm().equals("local.privateKey")) {
                RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
                String privateKeyValue = Base64.encodeBase64String(privateKey.getEncoded());
                ParmeterInfo privateKeyInfo = new ParmeterInfo();
                BeanUtils.copyProperties(secretKey, privateKeyInfo);
                privateKeyInfo.setParamVal(privateKeyValue);
                privateKeyInfo.setParamDesc("平台的私钥");
                privateKeyInfo.setUpdateDt(new Date());
                privateKeyInfo.setPermission("1");
                parmeterInfoMapper.insertSelective(privateKeyInfo);
            }
            log.info("---查询结束---");
            return parmeterInfoMapper.selectByTypeAndNm(secretKey.getParamType(), secretKey.getParamNm());
        } catch (DataAccessException e) {
            log.error("---数据库异常---");
            throw new RuntimeException("数据库异常");
        } catch (NoSuchAlgorithmException e) {
            log.error("---秘钥生成异常---", e);
            throw new RuntimeException("秘钥生成异常");
        }
    }
}
