package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.dao.IQuryEmployeeMapper;
import com.dispart.dto.empdto.QuryEmpInDto;
import com.dispart.dto.empdto.QuryEmpOutDto;
import com.dispart.dto.empdto.QuryEmpOutParamDto;
import com.dispart.enums.EmpStatEnum;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;

import static com.dispart.result.ResultCodeEnum.SUCCESS;
import static com.dispart.result.UserResultCodeEnum.*;

import com.dispart.service.QuryEmployeeService;
import com.dispart.vo.user.EmployeeInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class QuryEmployeeServiceImpl implements QuryEmployeeService {

    @Resource
    private IQuryEmployeeMapper iQuryEmployeeDao;

    @Override
    public Result<QuryEmpOutDto> quryEmployeeInfo(QuryEmpInDto param) {

        log.info("员工信息查询开始执行，传入参数：{}", JSON.toJSONString(param));
        Result<QuryEmpOutDto> result = null;

        if (ObjectUtils.isEmpty(param)) {
            return Result.build(null, USER_PARAM_NULL.getCode(), USER_PARAM_NULL.getMessage());
        }
        if (param.getPageNum() == null || param.getPageSize() == null) {
            log.info("分页参数不能为空");
            return Result.build(USER_PARAM_NULL.getCode(), "分页参数为空");
        }
        if (param.getPageSize() <= 0) {
            log.info("分页条数输入错误");
            return Result.build(USER_PARAM_ERROR.getCode(), "分页条数输入错误");
        }
        if (param.getPageNum() <= 0) {
            log.info("分页页数输入错误");
            return Result.build(USER_PARAM_ERROR.getCode(), "分页页数输入错误");
        }
        int strNum = (param.getPageNum() - 1) * param.getPageSize();
        int endNum = param.getPageSize();
        List<QuryEmpOutParamDto> outDtoList = null;
        Integer count = 0;
        try {
            count = iQuryEmployeeDao.selectCount(param, EmpStatEnum.DETEED.getCode());
        } catch (DataAccessException e) {
            log.error("数据库查询异常");
            throw new RuntimeException(e);
        }
        QuryEmpOutDto outBody = new QuryEmpOutDto();
        if (count <= 0) {
            log.info("用户业务-用户绑定角色信息数为0");
            outBody.setTolPageNum(count);//总条数
            log.info("查询结束，返回：{}",outBody);
            return Result.build(outBody, SUCCESS);
        }
        outDtoList = new ArrayList<>();
        List<EmployeeInfoVo> employeeInfos = null;
        try {
            employeeInfos = iQuryEmployeeDao.selectEmpInfos(param, strNum, endNum, EmpStatEnum.DETEED.getCode());
        } catch (DataAccessException e) {
            log.info("数据库查询异常");
            throw new RuntimeException(e);
        }
        try{
            for (EmployeeInfoVo vo : employeeInfos) {
                QuryEmpOutParamDto outDto = new QuryEmpOutParamDto();
                outDto.setEmpId(vo.getEmpId());
                outDto.setEmpNm(vo.getEmpNm());
//            outDto.setLoginAcct(vo.getLoginAcct());
                outDto.setSex(vo.getSex());
                outDto.setCertTp(vo.getCertType());
                outDto.setCertNum(vo.getCertNum());
                outDto.setTelePhone(vo.getTelephone());
                outDto.setDeduAddr(vo.getDeduAddr());
                outDto.setEmail(vo.getEmail());
                outDto.setPostCode(vo.getPostcode());
                outDto.setSubDep(vo.getSubDep());
                outDto.setSubOrg(vo.getSubOrg());
                outDto.setRemark(vo.getRemark());
                outDto.setUpdateDt(vo.getUpdateDt());
                outDto.setExpDt(vo.getExpDt());
                outDto.setUpdatePasswdDtTm(vo.getUpdatePasswdDtTm());
                outDto.setEffDt(vo.getEffeDt());
                outDto.setOperId(vo.getOperId());
                outDto.setEmpSt(vo.getEmpSt());
                outDto.setUsPaWd(vo.getPasswd());
                //查询机构名称
                String orgNm = null;
                orgNm = iQuryEmployeeDao.selectOrgNameByOrgId(vo.getSubOrg());
                //查询部门名称
                String depNm = null;
                depNm = iQuryEmployeeDao.selectDepNameDepId(vo.getSubDep());
                outDto.setOrgNm(orgNm);
                outDto.setDepNm(depNm);
                outDtoList.add(outDto);
            }
        }catch (Exception e){
            log.error("员工信息异常");
            throw new RuntimeException(e);
        }

        outBody.setList(outDtoList);
        outBody.setTolPageNum(count);
        result = Result.build(outBody, ResultCodeEnum.SUCCESS);
        log.info("用户业务-查询成功，返回：{}",outBody);
        return result;

    }
}
