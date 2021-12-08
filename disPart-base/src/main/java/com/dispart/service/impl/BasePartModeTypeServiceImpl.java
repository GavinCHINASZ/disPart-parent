package com.dispart.service.impl;

import com.dispart.dao.mapper.BaseOrganizationMapper;
import com.dispart.dto.partmodetype.DISP20210101SePMTInDto;
import com.dispart.dto.partmodetype.DISP20210101SePMTOutDto;
import com.dispart.dto.roledto.DISP20210033FindRoleOutDto;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeBaseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.dispart.dao.mapper.BasePartModeTypeMapper;
import com.dispart.vo.basevo.PartModeTypeVo;
import com.dispart.service.BasePartModeTypeService;

import java.util.Date;

@Slf4j
@Service
public class BasePartModeTypeServiceImpl implements BasePartModeTypeService {

    @Resource
    private BasePartModeTypeMapper basePartModeTypeMapper;
    @Autowired
    BaseOrganizationMapper baseOrganizationMapper;

    /**
     * 查询分账模式信息
     * @param record
     * @return
     */
    @Override
    public Result<DISP20210101SePMTOutDto> sePMT(DISP20210101SePMTInDto record) {
        DISP20210101SePMTOutDto seOut = new DISP20210101SePMTOutDto();

        if(!(record.getPageNum()==null || record.getPageSize()==null) ){//若需条件查询则添加总数
            try {
                Integer pNum=basePartModeTypeMapper.pMTNum(record);
                seOut.setTolPageNum(pNum);
                int pageNum=(record.getPageNum()-1)*record.getPageSize();
                record.setPageNum(pageNum);
                seOut.setModelList(basePartModeTypeMapper.sePMT(record));
                return Result.build(seOut, ResultCodeBaseEnum.SUCCESS);
            }catch (Exception e){
                log.info("查询分账模式信息-获取分账模式列表失败： "+e);
                throw new RuntimeException("系统错误，数据库查询异常");
            }
        }
        try{
            record.setPageSize(0);
            seOut.setModelList(basePartModeTypeMapper.sePMT(record));
        }catch (Exception e){
            log.info("查询分账模式信息-获取分账模式列表失败： "+e);
            throw new RuntimeException("系统错误，数据库查询异常");
        }

        return Result.build(seOut, ResultCodeBaseEnum.SUCCESS);
    }

    /**
     * 添加分账模式
     * @param record
     * @return
     */
    @Override
    public Result insert(PartModeTypeVo record) {
        Integer maxId=10000;
        try {
            maxId+=baseOrganizationMapper.findNextval("partMdId");
        }catch (Exception e){
            log.info("添加分账模式-获取序列号失败： "+e);
            throw new RuntimeException("系统错误，数据库查询异常");
        }

        //比例模式的分账数值<1
        if(record.getPartMdVal().doubleValue()>=1 && "0".equals(record.getPartMdTp())){
            return Result.build(null,ResultCodeBaseEnum.PARTMDVAL_0);
        }
        record.setStatus("1");//默认为1-未选中状态
        record.setPartMdId(maxId+"");
        record.setUpdateDt(new Date());
        log.info("添加分账模式-模式类型： "+record.getPartMdTp());
        try{
            if(basePartModeTypeMapper.insert(record)>0){
                return Result.ok();
            }else {
                return Result.fail();
            }
        }catch (Exception e){
            log.info("添加分账模式-添加失败： "+e);
            throw new RuntimeException("系统错误，数据库插入异常");
        }


    }

    /**
     * 修改分账模式信息
     * @param record
     * @return
     */
    @Override
    public Result updateByPrimaryKey(PartModeTypeVo record) {
        try {
            if(record.getPartMdId()==null || "".equals(record.getPartMdId())){
                return Result.build(1,"分账模式编号不能为空");
            }
            record.setUpdateDt(new Date());
            if(basePartModeTypeMapper.updateByPrimaryKey(record)>0){
                return Result.ok();
            }
            return Result.fail();
        }catch (Exception e){
            log.info("修改分账模式信息-添加失败： "+e);
            throw new RuntimeException("系统错误，修改分账模式信息异常");
        }

    }

    /**
     * 修改分账模式选中状态（只允许有一个是选中状态）
     * @param record
     * @return
     */
    @Override
    public Result upStByPrimaryKey(PartModeTypeVo record) {
        try{
            if(record.getPartMdId()==null || "".equals(record.getPartMdId())){
                return Result.build(1,"分账模式编号不能为空");
            }
            int upSt1=basePartModeTypeMapper.upSt1ByPrimaryKey(record);
            int upSt0=basePartModeTypeMapper.upSt0ByPrimaryKey(record);
            if(upSt0==1){
                return Result.ok();
            }
            return Result.fail();
        }catch (Exception e){
            log.info("修改分账模式选中状态-修改失败： "+e);
            throw new RuntimeException("系统错误，修改分账模式选中状态异常");
        }

    }

}
