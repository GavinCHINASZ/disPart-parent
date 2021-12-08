package com.dispart.service.impl;

import com.dispart.dao.mapper.BaseDepartmentMapper;
import com.dispart.dao.mapper.BaseOrganizationMapper;
import com.dispart.dto.departmentdto.DISP20210019DepFindByParamInDto;
import com.dispart.dto.menudto.*;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeBaseEnum;
import com.dispart.service.BaseChangRoleMeToBusine;
import com.dispart.vo.basevo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dispart.dao.mapper.BaseMenuMapper;
import com.dispart.service.BaseMenuService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
public class BaseMenuServiceImpl extends ServiceImpl<BaseMenuMapper, MenuVo> implements BaseMenuService {

    @Autowired
    BaseMenuMapper baseMenuMapper;
    @Autowired
    BaseDepartmentMapper baseDepartmentMapper;
    @Autowired
    BaseOrganizationMapper baseOrganizationMapper;

    @Autowired
    private BaseChangRoleMeToBusine baseChangRoleMeToBusine;
//    private RestTemplate restTemplate;

    /**
     * 查询全部菜单（菜单树）
     * @param disp20210031SeMenuInDto
     * @return
     */
    @Override
    public Result<List<DISP20210031SeMenuOutDto>> findAllMenu(DISP20210031SeMenuInDto disp20210031SeMenuInDto) {
        List<DISP20210031SeMenuOutDto> resLsit = new ArrayList<>();
        try{
            resLsit=baseMenuMapper.findAllMenu(disp20210031SeMenuInDto);
        }catch (Exception e){
            log.info("查询全部菜单-查询失败： "+e);
            throw new RuntimeException("系统错误，查询全部菜单异常");
        }

        //树状处理
        List<DISP20210031SeMenuOutDto> resFirstD=new ArrayList<>();
        if(resLsit.size()==1){
            log.info("查询全部菜单-指定查单条菜单："+disp20210031SeMenuInDto);
            return  Result.build(resLsit,ResultCodeBaseEnum.SUCCESS);
        }

        log.info("查询全部菜单-菜单列表： " +resLsit);
        //1、取第一个菜单节点--父节点
        for(DISP20210031SeMenuOutDto menuF : resLsit){
            if(null==menuF.getParentMenuId()||"".equals(menuF.getParentMenuId()) || "0000".equals(menuF.getParentMenuId())){
                resFirstD.add(menuF);
            }
        }
        log.info("查询全部菜单父节点： " +resFirstD);
        //2、获取递归子节点
        for(DISP20210031SeMenuOutDto menuN : resFirstD){
            menuN=menuChild(menuN,resLsit);
        }
        log.info("查询全部菜单-菜单树--------：" +resFirstD);
        return Result.build(resFirstD, ResultCodeBaseEnum.SUCCESS);
    }

    /**
     * 机构或部门查询对应权限
     * @param disp20210022DepFindMenuInDto
     * @return
     */
    @Override
    public Result<DISP20210022DepFindMenuOutDto> findMenuTree(DISP20210022DepFindMenuInDto disp20210022DepFindMenuInDto){
        DISP20210022DepFindMenuOutDto depOrg22Out = new DISP20210022DepFindMenuOutDto();


        if(disp20210022DepFindMenuInDto.getId() == null || "".equals(disp20210022DepFindMenuInDto.getId())){
           return Result.build(null,1,"部门ID或机构ID为空");
        }

        List<DISP20210022DepFindMenuPOutDto> menuList = new ArrayList<>();
        if(0==disp20210022DepFindMenuInDto.getDOSt()){//是部门
            DepartmentVo dep1 = new DepartmentVo();
            try {
                dep1 = baseDepartmentMapper.findPDepIdByDepId(disp20210022DepFindMenuInDto.getId());
            }catch (Exception e){
                log.info("机构或部门查询对应权限-查询部门信息失败： "+e);
                throw new RuntimeException("系统错误，数据库查询异常");
            }

           if(!(dep1==null || dep1.getParentDepId()==null || "".equals(dep1.getParentDepId()) || "000000".equals(dep1.getParentDepId()))){//有上级部门时，取上级部门权限集
               //检查上级部门是存在
               DISP20210019DepFindByParamInDto d19find = new DISP20210019DepFindByParamInDto();
               d19find.setDepId(dep1.getParentDepId());
               try {
                   if(baseDepartmentMapper.findDepByParam(d19find).size()<=0){
                       return Result.build(null,1,"该部门的上级部门不存在");
                   }

                   menuList = baseMenuMapper.find2DepOrgMenuList(disp20210022DepFindMenuInDto.getId(),dep1.getParentDepId());
                   if(menuList.size()<=0){
                       return Result.build(null,1,"该部门的上级部门无对应菜单，请先添加对应上级部门的权限");
                   }
               }catch (Exception e){
                   log.info("机构或部门查询对应权限-查询上级部门信息及对应菜单失败： "+e);
                   throw new RuntimeException("系统错误，数据库查询异常");
               }


           }else if(!(dep1==null || dep1.getSubOrg()==null || "".equals(dep1.getSubOrg()))){//无上级部门，取所属机构权限集
               try {
                   menuList = baseMenuMapper.find2DepOrgMenuList(disp20210022DepFindMenuInDto.getId(),dep1.getSubOrg());
                   if(menuList.size()<=0){
                       return Result.build(null,1,"该部门的所属机构无对应菜单，请先添加对应所属机构的权限");
                   }
               }catch (Exception e){
                   log.info("机构或部门查询对应权限-查询部门所属信息失败： "+e);
                   throw new RuntimeException("系统错误，数据库查询异常");
               }

           }else {
               return Result.build(null,1,"所属机构号为空");
           }

        }else {
            try {
                OrganizationVo org1 = baseOrganizationMapper.findPOrgIDByOrgId(disp20210022DepFindMenuInDto.getId());
                log.info("机构查询对应权限-上级机构： "+org1);
                if(!(org1==null || org1.getParentOrgId()==null || "".equals(org1.getParentOrgId()) || "00000000".equals(org1.getParentOrgId()))){//有上级机构时且不是00000000，取上级机构权限集
                    menuList = baseMenuMapper.find2DepOrgMenuList(disp20210022DepFindMenuInDto.getId(),org1.getParentOrgId());
                    if(menuList.size()<=0){
                        return Result.build(null,1,"该机构的上级机构无对应菜单，请先添加对应上级机构的权限");
                    }
                }else {//无上级机构或上级机构是00000000时，取全部权限集
                    log.info("机构查询对应权限-开始获取全部菜单");
                    menuList = baseMenuMapper.find1OrgMenuList(disp20210022DepFindMenuInDto.getId());
                    log.info("机构查询对应权限-获取全部菜单： ");
                    if(menuList.size()<=0){
                        return Result.build(null,1,"该机构无对应权限");
                    }
                }
            }catch (Exception e){
                log.info("机构或部门查询对应权限-查询机构和对应菜单信息失败： "+e);
                throw new RuntimeException("系统错误，数据库查询异常");
            }

        }


        for( DISP20210022DepFindMenuPOutDto menuStDo:menuList){
            if(disp20210022DepFindMenuInDto.getId().equals(menuStDo.getId())){
                menuStDo.setMenuSt("1");//机构或部门已添加该权限
            }else {
                menuStDo.setMenuSt("0");//机构或部门未添加该权限
                menuStDo.setId(disp20210022DepFindMenuInDto.getId());
            }

        }

        List<DISP20210022DepFindMenuPOutDto> resFirstD=new ArrayList<>();
        log.info("查询权限-菜单列表--------：" +menuList);
        //1、取第一个菜单节点--父节点
        for(DISP20210022DepFindMenuPOutDto menuF : menuList){
            if(null == menuF.getParentMenuId() || "".equals(menuF.getParentMenuId()) || "0000".equals(menuF.getParentMenuId())){
                resFirstD.add(menuF);
            }
        }
        log.info("查询权限-父节点--------：" +resFirstD);
        //2、获取递归子节点
        for(DISP20210022DepFindMenuPOutDto menuN : resFirstD){
            menuN = menuChild(menuN,menuList);
        }

        log.info("查询权限-菜单树--------：" +resFirstD);



        depOrg22Out.setChildrenPc(resFirstD);
        return Result.build(depOrg22Out, ResultCodeBaseEnum.SUCCESS);
    }

    /**
     * 角色查询对应权限
     * @param disp20210037RoleFindMenuInDto
     * @return
     */
    @Override
    public Result<DISP20210037RoleFindMenuOutDto> findMenuTree(DISP20210037RoleFindMenuInDto disp20210037RoleFindMenuInDto) {
        DISP20210037RoleFindMenuOutDto role37Out = new DISP20210037RoleFindMenuOutDto();
        List<DISP20210037RoleFindMenuPOutDto> resLsit = new ArrayList<>();
        try{
           resLsit = baseMenuMapper.findRoleMenuVoList(disp20210037RoleFindMenuInDto);
        }catch (Exception e){
            log.info("角色查询对应权限-查询失败： "+e);
            throw new RuntimeException("系统错误，角色查询对应权限异常");
        }

        for( DISP20210037RoleFindMenuPOutDto menuStDo:resLsit){
            if(disp20210037RoleFindMenuInDto.getRoleId().equals(menuStDo.getRoleId())){
                menuStDo.setMenuSt("1");//角色已添加该权限

            }else {
                menuStDo.setMenuSt("0");//角色未添加该权限
                menuStDo.setDataParm("0");//角色未拥有的权限默认个人级
                menuStDo.setRoleId(disp20210037RoleFindMenuInDto.getRoleId());
            }

        }

        //树状处理
        List<DISP20210037RoleFindMenuPOutDto> resFirstD=new ArrayList<>();
        log.info("角色查询对应权限-菜单列表--------：" +resLsit);
        //1、取第一个菜单节点--父节点
        for(DISP20210037RoleFindMenuPOutDto menuF : resLsit){
            if(null==menuF.getParentMenuId()||"".equals(menuF.getParentMenuId()) || "0000".equals(menuF.getParentMenuId())){
                resFirstD.add(menuF);
            }
        }
        log.info("角色查询对应权限-父节点--------：" +resFirstD);
        //2、获取递归子节点
        for(DISP20210037RoleFindMenuPOutDto menuN : resFirstD){
            menuN=menuChild(menuN,resLsit);
        }
        log.info("角色查询对应权限-菜单树--------：" +resFirstD);
        role37Out.setChildrenPc(resFirstD);
        return Result.build(role37Out,ResultCodeBaseEnum.SUCCESS);
    }

    /**
     * 维护角色权限
     * @param disp20210038RoleUpMenuInDto
     * @return
     */
    @Transactional
    @Override
    public Result upRoleMenu(DISP20210038RoleUpMenuInDto disp20210038RoleUpMenuInDto) {
        log.info("维护角色权限-入参DISP20210038： "+disp20210038RoleUpMenuInDto);
        if(disp20210038RoleUpMenuInDto.getRoleId()==null || "".equals(disp20210038RoleUpMenuInDto.getRoleId())){
            return Result.build(1,"角色编号不能为空");
        }
        int deNum=0;
        int inNum=0;
        try {
            deNum = baseMenuMapper.deRoleMenu(disp20210038RoleUpMenuInDto.getRoleId());//删除角色原有权限
        }catch (Exception e){
            log.info("维护角色权限-删除角色原有权限失败： "+e);
            throw new RuntimeException("系统错误，数据库修改角色权限异常");
        }
        if(disp20210038RoleUpMenuInDto.getChildren().size() > 0){
            Date upDate = new Date();
            for(RoleMenu roleMenu:disp20210038RoleUpMenuInDto.getChildren()){
                roleMenu.setUpdateDt(upDate);
            }
            try{
                inNum= baseMenuMapper.inRoleMenu(disp20210038RoleUpMenuInDto.getChildren());
            }catch (Exception e){
                log.info("维护角色权限-添加角色权限失败： "+e);
                throw new RuntimeException("系统错误，维护角色权限-数据库添加角色权限异常");
            }
        }
        if(inNum>0 || deNum>0){
            try {
                //告知安全中心该角色权限有变更
                Map<String,String> roleId = new HashMap<>();
                roleId.put("roleId",disp20210038RoleUpMenuInDto.getRoleId());
                log.info("维护角色权限-开始通知安全中心变更角色权限 roldId： "+ roleId);
                Result responseRole=baseChangRoleMeToBusine.busineCommonsRole(roleId);
                log.info("维护角色权限-通知安全中心变更权限结果： "+ responseRole);
                if(responseRole.getCode()!=200){
                    log.info("维护角色权限-通知安全中心变更权限结果： 通知失败 ");
                    return Result.build(null,ResultCodeBaseEnum.ROLE_MENU_PARTSUCCESS);
                }
            }catch (Exception e){
                log.info("维护角色权限-通知安全中心变更权限结果： 通讯失败： "+e);
                throw new RuntimeException("系统错误，维护角色权限-与安全中心通讯异常");
            }
            return  Result.ok();
        }
        return Result.fail();


    }

    /**
     * 设置机构或者部门权限
     * @param disp20210023UpDepMenuInDto
     * @return
     */
    @Transactional
    @Override
    public Result upDepOrgMenu(DISP20210023UpDepMenuInDto disp20210023UpDepMenuInDto) {
        if(disp20210023UpDepMenuInDto.getId()==null || "".equals(disp20210023UpDepMenuInDto.getId())){
            return Result.build(1,"部门或机构编号不能为空");
        }
        log.info("部门或机构菜单集合： "+disp20210023UpDepMenuInDto);
        int deNum=0;
        int inNum=0;
        try{
            deNum = baseMenuMapper.deDepOrgMenu(disp20210023UpDepMenuInDto.getId());//删除权限
            if(disp20210023UpDepMenuInDto.getChildren().size()>0){//若有权限集合有值则添加权限，
                Date upDate = new Date();
                for (DepOrgMenuVo depOrgMenuVo:disp20210023UpDepMenuInDto.getChildren()){
                    depOrgMenuVo.setUpdateDt(upDate);
                }
                inNum = baseMenuMapper.inOrgDepMenu(disp20210023UpDepMenuInDto.getChildren());
            }
        }catch (Exception e){
            log.info("设置机构或者部门权限-变更权限结果： 失败： "+e);
            throw new RuntimeException("系统错误，设置机构或者部门权限-数据库异常");
        }

        if(disp20210023UpDepMenuInDto.getIsDepOrg()==1 && (deNum>0 || inNum>0)){//改变部门权限需要通知安全中心
            try {
                //告知安全中心该角色权限有变更
                Map<String,String> depId = new HashMap<>();
                depId.put("depId",disp20210023UpDepMenuInDto.getId());
                log.info("设置部门权限-开始通知安全中心变更权限 depId： "+ depId);
                Result responseRole=baseChangRoleMeToBusine.busineCommonsOrg(depId);
                log.info("设置部门权限-通知安全中心变更结果： "+ responseRole);
            }catch (Exception e){
                e.printStackTrace();
                log.info("设置部门权限-通知安全中心变更权限结果： 通知失败");
                return Result.build(null,ResultCodeBaseEnum.ROLE_MENU_PARTSUCCESS);
            }
        }
        return  Result.ok();
    }


    /**
     * 获取全部菜单树
     * @param menuN  父级菜单
     * @param hList  全部菜单
     * @return
     */
    public DISP20210031SeMenuOutDto menuChild(DISP20210031SeMenuOutDto menuN, List<DISP20210031SeMenuOutDto> hList){
        for(DISP20210031SeMenuOutDto menu : hList){
            if(Objects.equals(menuN.getMenuId(),menu.getParentMenuId())){
                menuN.getChildren().add(menu);
                menu=menuChild(menu,hList);
            }
        }
        return menuN;
    }
    /**
     * 机构或部门获取菜单树
     * @param menuN  父级菜单
     * @param hList  全部菜单
     * @return
     */
    public DISP20210022DepFindMenuPOutDto menuChild(DISP20210022DepFindMenuPOutDto menuN, List<DISP20210022DepFindMenuPOutDto> hList){
        for(DISP20210022DepFindMenuPOutDto menu : hList){
            if(Objects.equals(menuN.getMenuId(),menu.getParentMenuId())){
                menuN.getChildren().add(menu);
                menu=menuChild(menu,hList);
            }
        }
        return menuN;
    }

    /**
     * 角色获取菜单树
     * @param menuN  父级菜单
     * @param hList  全部菜单
     * @return
     */
    public DISP20210037RoleFindMenuPOutDto menuChild(DISP20210037RoleFindMenuPOutDto menuN, List<DISP20210037RoleFindMenuPOutDto> hList){
        for(DISP20210037RoleFindMenuPOutDto menu : hList){
            if(Objects.equals(menuN.getMenuId(),menu.getParentMenuId())){
                menuN.getChildren().add(menu);
                menu=menuChild(menu,hList);
            }
        }
        return menuN;
    }


    /**
     * 把层级对象解析成list集合
     * @param menu22Out
     * @param add22in 要返回的list集合
     * @return
     */
    public List<DISP20210022DepFindMenuPOutDto> menu22toList( List<DISP20210022DepFindMenuPOutDto> menu22Out, List<DISP20210022DepFindMenuPOutDto> add22in){
        for(DISP20210022DepFindMenuPOutDto addin :menu22Out) {
            if (addin.getChildren().size() == 0) {
                add22in.add(addin);
            }else{
                menu22toList(addin.getChildren(),add22in);
                addin.setChildren(null);
                add22in.add(addin);
            }
        }
        return add22in;
    }

    /**
     * 把层级对象解析成list集合
     * @param menu37Out
     * @param add37in //要返回的List集合
     * @return
     */
    public List<DISP20210037RoleFindMenuPOutDto> menu37toList( List<DISP20210037RoleFindMenuPOutDto> menu37Out, List<DISP20210037RoleFindMenuPOutDto> add37in){

        for(DISP20210037RoleFindMenuPOutDto addin :menu37Out) {
            if (addin.getChildren().size() == 0) {
                add37in.add(addin);
            }else{
                menu37toList(addin.getChildren(),add37in);
                addin.setChildren(null);
                add37in.add(addin);
            }
        }
        return add37in;
    }

}
