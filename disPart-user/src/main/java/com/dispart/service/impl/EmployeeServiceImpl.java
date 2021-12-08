package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.dao.IEmployeeDao;
import com.dispart.dto.ResultOutDto;
import com.dispart.dto.empdto.*;
import com.dispart.enums.EmpStatEnum;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.EmployeeService;
import com.dispart.utils.Md5Util;
import com.dispart.utils.UserResUtil;
import com.dispart.vo.user.EmployeeInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.dispart.result.ResultCodeEnum.SUCCESS;
import static com.dispart.result.UserResultCodeEnum.*;


@Service
@Slf4j
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private IEmployeeDao iEmployeeDao;

    @Override
    public Result<ResultOutDto> insertEmployeeInfo(AddEmpInDto param, String operator) {
        if (ObjectUtils.isEmpty(param)) {
            return UserResUtil.paramFail("输入参数为空");
        }
        if (StringUtils.isEmpty(param.getEmpNm())) {
            return UserResUtil.paramFail("员工姓名为空");
        }
        if (StringUtils.isEmpty(param.getSex())) {
            return UserResUtil.paramFail("员工性别为空");
        }
        if (StringUtils.isEmpty(param.getCertTp())) {
            return UserResUtil.paramFail("证件类型为空");
        }
        if (StringUtils.isEmpty(param.getCertNum())) {
            return UserResUtil.paramFail("证件号码为空");
        }
        if (StringUtils.isEmpty(param.getTelePhone())) {
            return UserResUtil.paramFail("电话号码为空");
        }
        if (StringUtils.isEmpty(param.getSubOrg())) {
            return UserResUtil.paramFail("机构编号为空");
        }
        if (StringUtils.isEmpty(param.getSubDep())) {
            return UserResUtil.paramFail("部门编号为空");
        }
        if (StringUtils.isEmpty(param.getUsPaWd())) {
            return UserResUtil.paramFail("用户密码为空");
        }
        int employeeIdSeq = 0;
        try {
            employeeIdSeq = iEmployeeDao.selectEmployeeIdSeq();
        } catch (DataAccessException e) {
            log.error("数据查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }

        //Md5加密密码
        String passwd = null;
        try {
            passwd = Md5Util.getMd5(param.getUsPaWd());
        } catch (Exception e) {
            log.error("用户业务-密码加密失败{}", e);
            ResultOutDto outDto = new ResultOutDto();
            outDto.setResult(ResultOutDto.FAIL);
            return Result.fail(outDto);
        }
        EmployeeInfoVo empVo = new EmployeeInfoVo();
        //员工序号 机构号后六位加6位序列号
        String seq = String.format("%0" + 6 + "d", employeeIdSeq);
        String subOrg = param.getSubOrg();
        String empId = subOrg.substring(subOrg.length() - 2, subOrg.length()) + seq;
        empVo.setEmpId(empId);
        empVo.setEmpNm(param.getEmpNm());
        empVo.setLoginAcct(param.getLoginAcct());
        empVo.setSex(param.getSex());
        empVo.setCertType(param.getCertTp());
        empVo.setCertNum(param.getCertNum());
        empVo.setTelephone(param.getTelePhone());
        empVo.setDeduAddr(param.getDeduAddr());
        empVo.setEmail(param.getEmail());
        empVo.setPostcode(param.getPostCode());
        empVo.setSubDep(param.getSubDep());
        empVo.setSubOrg(param.getSubOrg());
        empVo.setRemark(param.getRemark());
        empVo.setPasswd(passwd);
        empVo.setEffeDt(new Date());
        empVo.setOperId(operator);//操作员
        empVo.setEmpSt(EmpStatEnum.NORMAR.getCode());
        try {
            Integer result = iEmployeeDao.insertEmployeeInfo(empVo);
            if (1 != result) {
                return UserResUtil.getResultFailDto(USER_INSERT_ERROR);
            }
        } catch (DataAccessException e) {
            log.error("数据新增异常", e);
            throw new RuntimeException("数据库新增异常");
        }
        return UserResUtil.getResultSuccessDto();
    }

    @Override
    public Result<ResultOutDto> updateEmployeeInfo(Request<UpdateEmpInDto> param) {
        if (ObjectUtils.isEmpty(param) || ObjectUtils.isEmpty(param.getBody())) {
            return UserResUtil.paramFail("输入参数为空");
        }
        if (StringUtils.isEmpty(param.getBody().getEmpId())) {
            return UserResUtil.paramFail("员工编号为空");
        }
        if (StringUtils.isEmpty(param.getBody().getEmpNm())) {
            return UserResUtil.paramFail("员工姓名为空");
        }
        if (StringUtils.isEmpty(param.getBody().getSex())) {
            return UserResUtil.paramFail("员工性别为空");
        }
        if (StringUtils.isEmpty(param.getBody().getCertTp())) {
            return UserResUtil.paramFail("证件类型为空");
        }
        if (StringUtils.isEmpty(param.getBody().getCertNum())) {
            return UserResUtil.paramFail("证件号码为空");
        }
        if (StringUtils.isEmpty(param.getBody().getTelePhone())) {
            return UserResUtil.paramFail("电话号码为空");
        }

        EmployeeInfoVo empVo = null;
        try {
            empVo = iEmployeeDao.selectEmpInfoById(param.getBody().getEmpId());
        } catch (DataAccessException e) {
            log.error("数据查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (empVo == null) {
            return UserResUtil.getResultFailDto(USER_INFO_NULL);
        }
        String passwd = null;
        if (!StringUtils.isEmpty(param.getBody().getUsPaWd())) {
            //Md5加密密码
            try {
                passwd = Md5Util.getMd5(param.getBody().getUsPaWd());
            } catch (Exception e) {
                log.error("用户业务-密码加密失败", e);
                ResultOutDto outDto = new ResultOutDto();
                outDto.setResult(ResultOutDto.FAIL);
                return Result.fail(outDto);
            }
            empVo.setPasswd(passwd);
            empVo.setUpdatePasswdDtTm(new Date());
        }
        empVo.setEmpId(param.getBody().getEmpId());
        empVo.setEmpNm(param.getBody().getEmpNm());
        empVo.setLoginAcct(param.getBody().getLoginAcct());
        empVo.setSex(param.getBody().getSex());
        empVo.setCertType(param.getBody().getCertTp());
        empVo.setCertNum(param.getBody().getCertNum());
        empVo.setTelephone(param.getBody().getTelePhone());
        empVo.setDeduAddr(param.getBody().getDeduAddr());
        empVo.setEmail(param.getBody().getEmail());
        empVo.setPostcode(param.getBody().getPostCode());
        empVo.setSubDep(param.getBody().getSubDep());
        empVo.setSubOrg(param.getBody().getSubOrg());
        empVo.setRemark(param.getBody().getRemark());
        empVo.setUpdateDt(new Date());
        empVo.setOperId(param.getHead().getOperator());
        int result = 0;
        try {
            result = iEmployeeDao.updateEmployeeInfo(empVo);
        } catch (DataAccessException e) {
            log.error("数据更新异常", e);
            throw new RuntimeException("数据库更新异常");
        }
        if (result != 1) {
            return UserResUtil.getResultFailDto(USER_UPDATE_FAIL);
        }
        log.info("用户业务-修改员工修息成功");
        return UserResUtil.getResultSuccessDto();
    }

    /**
     * 用户锁定解锁
     *
     * @param param
     * @return
     */
    @Override
    public Result<ResultOutDto> updateEmployeeEmpSt(Request<LockEmpInDto> param) {
        if (ObjectUtils.isEmpty(param) || ObjectUtils.isEmpty(param.getBody())) {
            return UserResUtil.paramFail("输入参数为空");
        }
        if (StringUtils.isEmpty(param.getBody().getEmpId())) {
            return UserResUtil.paramFail("员工编号为空");
        }
        if (StringUtils.isEmpty(param.getBody().getEmpSt())) {
            return UserResUtil.paramFail("锁定标志为空");
        }
        String origSt = param.getBody().getEmpSt().equals(EmpStatEnum.LOCK.getCode()) ? EmpStatEnum.NORMAR.getCode() : EmpStatEnum.LOCK.getCode();
        //sql参数对象
        UpdateEmpParamDto paramDto = new UpdateEmpParamDto();
        paramDto.setEmpId(param.getBody().getEmpId());
        paramDto.setEmpSt(param.getBody().getEmpSt());
        paramDto.setOrigSt(origSt);
        paramDto.setOperId(param.getHead().getOperator());
        paramDto.setUpdateDt(new Date());
        //更新员工
        int result;
        try {
            result = iEmployeeDao.updateEmpStById(paramDto);
        } catch (DataAccessException e) {
            log.error("数据查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (result != 1) {
            return UserResUtil.getResultFailDto(USER_LOCK_ON_FAIL);
        }
        return UserResUtil.getResultSuccessDto();
    }

    @Override
    public Result<ResultOutDto> updateEmployeeLogOff(Request<LockEmpInDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return UserResUtil.paramFail("输入参数为空");
        }
        if (StringUtils.isEmpty(param.getBody().getEmpId())) {
            return UserResUtil.paramFail("员工编号为空");
        }
        //sql参数对象
        UpdateEmpParamDto paramDto = new UpdateEmpParamDto();
        paramDto.setEmpId(param.getBody().getEmpId());
        paramDto.setEmpSt(EmpStatEnum.DETEED.getCode());
        paramDto.setOperId(param.getHead().getOperator());
        paramDto.setExpDt(new Date());
        paramDto.setUpdateDt(new Date());
        //更新员工
        int result;
        try {
            result = iEmployeeDao.updateDeleteEmpById(paramDto);
        } catch (DataAccessException e) {
            log.error("数据查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (result != 1) {
            log.info("用户业务-注销用户失败");
            return UserResUtil.getResultFailDto(USER_LOGOFF_FAIL);
        }
        return UserResUtil.getResultSuccessDto();
    }

    @Override
    public Result<QuryEmpOutDto> quryEmployeeInfo(QuryEmpInDto param) {

        log.info("员工信息查询开始执行，传入参数：{}", JSON.toJSONString(param));
        Result<QuryEmpOutDto> result = null;

        if (ObjectUtils.isEmpty(param)) {
            return Result.build(null, USER_PARAM_NULL.getCode(), USER_PARAM_NULL.getMessage());
        }
//        if (StringUtils.isEmpty(param.getEmpNm())) {
//            return Result.build(null, USER_PARAM_NULL.getCode(), "员工姓名为空");
//        }
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
            count = iEmployeeDao.selectCount(param);
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
            employeeInfos = iEmployeeDao.selectEmpInfos(param, strNum, endNum, EmpStatEnum.DETEED.getCode());
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
//                outDto.setUsPaWd(vo.getPasswd());
                //查询机构名称
                String orgNm = null;
                orgNm = iEmployeeDao.selectOrgNameByOrgId(vo.getSubOrg());
                //查询部门名称
                String depNm = null;
                depNm = iEmployeeDao.selectDepNameDepId(vo.getSubDep());
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
