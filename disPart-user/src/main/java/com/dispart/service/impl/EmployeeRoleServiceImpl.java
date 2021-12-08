package com.dispart.service.impl;

import com.dispart.dao.IEmployeeDao;
import com.dispart.dao.IEmployeeRoleDao;
import com.dispart.dto.ResultOutDto;
import com.dispart.dto.empdto.*;
import com.dispart.dto.userdto.EmpFindMenuInDto;
import com.dispart.dto.userdto.EmpRoleFindMenuOutDto;
import com.dispart.enums.EmpStatEnum;
import com.dispart.result.Result;

import static com.dispart.result.ResultCodeEnum.*;

import com.dispart.result.ResultCodeEnum;
import com.dispart.service.EmployeeRoleService;
import com.dispart.utils.UserResUtil;
import com.dispart.vo.user.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

import static com.dispart.result.UserResultCodeEnum.*;

@Service
@Slf4j
@Transactional
public class EmployeeRoleServiceImpl implements EmployeeRoleService {

    @Resource
    IEmployeeRoleDao iEmployeeRoleDao;

    @Resource
    private IEmployeeDao iEmployeeDao;

    @Override
    public Result<QuryBindRoleOutDto> quryBindRole(QuryBindRoleInDto param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(USER_PARAM_NULL.getCode(), "输入参数为空");
        }
        if (StringUtils.isEmpty(param.getEmpId())) {
            return Result.build(USER_PARAM_NULL.getCode(), "员工编号为空");
        }
        if (param.getPageNum() == null || param.getPageSize() == null) {
            return Result.build(USER_PARAM_NULL.getCode(), "分页参数为空");
        }
        if (param.getPageSize() <= 0) {
            return Result.build(USER_PARAM_ERROR.getCode(), "分页条数输入错误");
        }
        if (param.getPageNum() <= 0) {
            return Result.build(USER_PARAM_ERROR.getCode(), "分页页数输入错误");
        }
        EmployeeInfoVo empVo = null;
        QuryBindRoleOutDto outDto = new QuryBindRoleOutDto();
        List<QuryBindRoleOutParamDto> paramDtos = new ArrayList<QuryBindRoleOutParamDto>();
        //查询员工表
        try {
            empVo = iEmployeeDao.selectEmpInfoById(param.getEmpId());
        } catch (DataAccessException e) {
            log.error("数据查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (empVo == null) {
            log.info("用户业务-员工信息不存在");
            return UserResUtil.getResultFailDto(null, USER_DATA_NO_ERROR);
        }
        int count = 0;
        try {
            count = iEmployeeRoleDao.quryEmpRoleInfoCount(param.getEmpId(), empVo.getSubOrg(), param.getChanlNo());
        } catch (DataAccessException e) {
            log.error("数据查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (count <= 0) {
            log.info("用户业务-用户绑定角色信息数为0");
            outDto.setTolPageNum(count);//总条数
            return Result.build(outDto, SUCCESS);
        }
        int strNum = (param.getPageNum() - 1) * param.getPageSize();
        int endNum = param.getPageSize();
        List<RoleStatVo> voList = null;
        //根据员工编号查询绑定角色表
        try {
            voList = iEmployeeRoleDao.quryEmpRoleInfoList(param.getEmpId(), empVo.getSubOrg(), param.getChanlNo(), strNum, endNum);
        } catch (DataAccessException e) {
            log.error("数据查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }

        for (RoleStatVo vo : voList) {
            QuryBindRoleOutParamDto paramDto = new QuryBindRoleOutParamDto();
            paramDto.setRoleNm(vo.getRoleNm());
            paramDto.setRoleId(vo.getRoleId());
            paramDto.setOrgId(empVo.getSubOrg());
            paramDto.setStat(vo.getStat());
            paramDto.setChanlNo(vo.getChanlNo());
            paramDtos.add(paramDto);
        }
        outDto.setTolPageNum(count);//总条数
        outDto.setList(paramDtos);
        return Result.build(outDto, SUCCESS);
    }


    @Override
    public Result<ResultOutDto> bindRole(BundingRoleInDto param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(USER_PARAM_NULL.getCode(), "输入参数为空");
        }
        if (StringUtils.isEmpty(param.getEmpId())) {
            return Result.build(USER_PARAM_NULL.getCode(), "员工编号为空");
        }
        if (ObjectUtils.isEmpty(param.getList()) || param.getList().size() <= 0) {
            return Result.build(USER_PARAM_NULL.getCode(), "角色列表为空");
        }

        EmployeeInfoVo empVo = null;
        //查询员工表
        try {
            empVo = iEmployeeDao.selectEmpInfoById(param.getEmpId());
        } catch (DataAccessException e) {
            log.error("数据查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (empVo == null) {
            log.info("用户业务-查询员工信息不存在");
            return UserResUtil.getResultFailDto(USER_INFO_NULL);
        }
        if (EmpStatEnum.DETEED.getCode().equals(empVo.getEmpSt())) {
            log.info("用户业务-员工已注销");
            return UserResUtil.getResultFailDto(USER_EMP_LOG_OFF);
        }
        List<BundingRoleParamInDto> roleIdList = param.getList();
        List<EmployeeRoleInfoVo> empRolevoList = new ArrayList<>();
        for (BundingRoleParamInDto inDtoList : roleIdList) {
            if (StringUtils.isEmpty(inDtoList.getRoleId())) {
                log.info("用户业务-角色编号为空");
                return Result.build(USER_PARAM_NULL.getCode(), "角色编号为空");
            }

            //查询用户角色绑定信息
            int empRoleCount = 0;
            try {
                empRoleCount = iEmployeeRoleDao.quryEmpRoleInfo(inDtoList.getRoleId(), param.getEmpId());
            } catch (DataAccessException e) {
                log.error("数据查询异常", e);
                throw new RuntimeException("数据库查询异常");
            }
            if (empRoleCount >= 1) {
                return UserResUtil.buildFail(USER_EMP_ROLE_INFO_ISEXIT, "角色id：" + inDtoList.getRoleId() + "，绑定关系已存在");
            }
            EmployeeRoleInfoVo vo = new EmployeeRoleInfoVo();
            vo.setEmpId(param.getEmpId());
            vo.setRoleId(inDtoList.getRoleId());
            vo.setUpdateDt(new Date());
            vo.setRemark("备注");
            empRolevoList.add(vo);
        }
        //插入员工角色绑定表
        int result;
        try {
            result = iEmployeeRoleDao.insertEmpRoleList(empRolevoList);
        } catch (DataAccessException e) {
            log.error("数据插入异常", e);
            throw new RuntimeException("数据库插入异常");
        }
        if (result != empRolevoList.size()) {
            log.info("用户业务-绑定失败：成功条数:" + result + "需要插入条数" + empRolevoList.size());
            throw new RuntimeException("数据库插入异常");
        }
        log.info("用户业务-绑定成功");
        return UserResUtil.getResultSuccessDto();
    }

    @Override
    public Result<ResultOutDto> unBindRole(UnBundingRoleByInDto param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(USER_PARAM_NULL.getCode(), "输入参数为空");
        }
        if (StringUtils.isEmpty(param.getEmpId())) {
            return Result.build(USER_PARAM_NULL.getCode(), "员工编号为空");
        }
        if (ObjectUtils.isEmpty(param.getList()) || param.getList().size() <= 0) {
            return Result.build(USER_PARAM_NULL.getCode(), "角色列表为空");
        }
        EmployeeInfoVo empVo = null;
        //查询员工表
        try {
            empVo = iEmployeeDao.selectEmpInfoById(param.getEmpId());
        } catch (DataAccessException e) {
            log.error("数据查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (empVo == null) {
            log.info("用户业务-查询员工信息不存在");
            return UserResUtil.getResultFailDto(USER_INFO_NULL);
        }
        if (EmpStatEnum.DETEED.getCode().equals(empVo.getEmpSt())) {
            log.info("用户业务-员工已注销");
            return UserResUtil.getResultFailDto(USER_EMP_LOG_OFF);
        }
        int result;
        try {
            result = iEmployeeRoleDao.deleteEmpRoleList(param.getList(), param.getEmpId());
        } catch (DataAccessException e) {
            log.error("数据删除异常", e);
            throw new RuntimeException("数据库删除异常");
        }
        if (result < 1) {
            return UserResUtil.getResultFailDto(USER_EMP_OLE_INSERT_FAIL);
        }
        log.info("用户业务-解绑成功");
        return UserResUtil.getResultSuccessDto();
    }


    @Override
    public Result<List<EmpRoleFindMenuOutDto>> qryEmpAuthMenu(EmpFindMenuInDto param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(USER_PARAM_NULL.getCode(), "输入参数为空");
        }
        if (StringUtils.isEmpty(param.getEmpId())) {
            return Result.build(USER_PARAM_NULL.getCode(), "员工编号为空");
        }
        if (StringUtils.isEmpty(param.getRoleId())) {
            return Result.build(USER_PARAM_NULL.getCode(), "角色编号为空");
        }
        //根据用户id查询用户信息
        EmployeeInfoVo empVo = null;
        try {
            empVo = iEmployeeDao.selectEmpInfoById(param.getEmpId());
        } catch (DataAccessException e) {
            log.error("数据查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (empVo == null) {
            log.info("用户业务-未查询到用户信息");
            return Result.build(USER_INFO_NULL.getCode(), USER_INFO_NULL.getMessage());
        }
        //查询用户角色绑定信息
        int empRoleCount = 0;
        try {
            empRoleCount = iEmployeeRoleDao.quryEmpRoleInfo(param.getRoleId(), param.getEmpId());
        } catch (DataAccessException e) {
            log.error("数据查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (empRoleCount < 1) {
            log.info("用户业务-未查询到用户角色绑定信息");
            return Result.build(USER_EMP_ROLE_NULL1.getCode(), USER_EMP_ROLE_NULL1.getMessage());
        }

        //根据员工查询所有权限菜单
        List<RoleMenuVo> empMenuList = null;
        try {
            empMenuList = iEmployeeRoleDao.selectAllEmpMenuList(param.getRoleId(), empVo.getSubDep());
        } catch (DataAccessException e) {
            log.error("数据查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (empMenuList == null || empMenuList.size() <= 0) {
            log.info("用户业务-用户权限菜单为空");
            return Result.build(USER_AUTH_MENU_NULL.getCode(), USER_AUTH_MENU_NULL.getMessage());
        }
        //用菜单id为key存入hashMap
        Map<String, RoleMenuVo> empMenuMap = new HashMap<>();
        for (RoleMenuVo roleMenuVo : empMenuList) {
            empMenuMap.put(roleMenuVo.getMenuId(), roleMenuVo);
        }
        //查询所有菜单根可根据渠道类型
        List<EmpRoleFindMenuOutDto> resLsit = null;
        try {
            resLsit = iEmployeeRoleDao.findAllMenu(param.getChnalNoType());
        } catch (DataAccessException e) {
            log.error("数据查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (resLsit == null || resLsit.size() <= 0) {
            log.info("用户业务-查询所有权限菜单为空");
            return Result.build(USER_AUTH_MENU_NULL.getCode(), USER_AUTH_MENU_NULL.getMessage());
        }
        //树状处理
        List<EmpRoleFindMenuOutDto> resFirstD = new ArrayList<>();
        //1、取第一个菜单节点--父节点
        for (EmpRoleFindMenuOutDto menuF : resLsit) {
            if (StringUtils.isEmpty(menuF.getParentMenuId())) {
                RoleMenuVo roleMenuVo = empMenuMap.get(menuF.getMenuId());
                if (!ObjectUtils.isEmpty(roleMenuVo)) {
                    menuF.setMenuType(roleMenuVo.getDataParm());//数据权限
                    menuF.setMenuSt("1");//角色已添加该权限
                    resFirstD.add(menuF);
                } else {
                    menuF.setMenuSt("0");//角色未添加该权限
                    resFirstD.add(menuF);
                }
            }
        }
        //2、获取递归子节点
        for (EmpRoleFindMenuOutDto menuN : resFirstD) {
            menuN = menuChild(menuN, resLsit, empMenuMap);
        }
        log.info("用户业务-查询用户权限菜单成功");
        return Result.ok(resFirstD);
    }

    /**
     * 获取全部菜单树
     *
     * @param menuN 父级菜单
     * @param hList 全部菜单
     * @return
     */
    public EmpRoleFindMenuOutDto menuChild(EmpRoleFindMenuOutDto menuN, List<EmpRoleFindMenuOutDto> hList, Map<String, RoleMenuVo> empMenuMap) {
        for (EmpRoleFindMenuOutDto menu : hList) {
            if (Objects.equals(menuN.getMenuId(), menu.getParentMenuId())) {
                RoleMenuVo roleMenuVo = empMenuMap.get(menu.getMenuId());
                if (!ObjectUtils.isEmpty(roleMenuVo)) {
                    menu.setMenuType(roleMenuVo.getDataParm());//数据权限
                    menu.setMenuSt("1");//角色已添加该权限
                } else {
                    menu.setMenuSt("0");//角色未添加该权限
                }
                menuN.getChildren().add(menu);
                menu = menuChild(menu, hList, empMenuMap);
            }
        }
        return menuN;
    }
}
