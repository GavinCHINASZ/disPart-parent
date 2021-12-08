package com.dispart.service.impl;

import com.dispart.dao.TProductInventoryInfoMapper;
import com.dispart.dto.ResultOutDto;
import com.dispart.dto.entrance.TProductInventoryInfoDto;
import com.dispart.dto.entrance.TProductInventoryInfoOutDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.GoodsManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

import static com.dispart.result.ResultCodeEnum.FAIL;
import static com.dispart.result.ResultCodeEnum.SUCCESS;

@Service
@Slf4j
public class GoodsManagerServiceImpl implements GoodsManagerService {


    @Autowired
    TProductInventoryInfoMapper tProductInventoryInfoMapper;

    @Override
    public Result<TProductInventoryInfoOutDto> quryGoodsInfo(String userNo,Request<TProductInventoryInfoDto> param) {
        if (ObjectUtils.isEmpty(param)) {
            return Result.build(1, "输入参数为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(1, "输入参数为空");
        }
        TProductInventoryInfoDto inDto = param.getBody();
        if (inDto.getPageNum() == null || inDto.getPageSize() == null) {
            log.info("货物管理业务-分页参数不能为空");
            return Result.build(1, "分页参数为空");
        }
        if (inDto.getPageSize() <= 0) {
            log.info("货物管理业务-分页条数输入错误");
            return Result.build(1, "分页条数输入错误");
        }
        if (inDto.getPageNum() <= 0) {
            log.info("货物管理业务-分页页数输入错误");
            return Result.build(1, "分页页数输入错误");
        }
        if(param.getHead().getChanlNo().equals("01")){//贵农购
            if(StringUtils.isEmpty(userNo)){
                return Result.build(1, "用户编号为空");
            }
        }

        int strNum = (inDto.getPageNum() - 1) * inDto.getPageSize();
        inDto.setStrNum(strNum);//起始记录数
        int tolNum = 0;
        TProductInventoryInfoOutDto outBody = new TProductInventoryInfoOutDto();

        //贵农购app
        if(param.getHead().getChanlNo().equals("01")){
            log.info("货物管理业务-贵农购app系统货物查询");
            if(StringUtils.isEmpty(userNo)){
                return Result.build(1, "用户编号为空");
            }
            try {
                tolNum = tProductInventoryInfoMapper.selectWhereByPhoneCount(inDto,userNo);
            } catch (DataAccessException e) {
                log.error("数据库查询异常", e);
                throw new RuntimeException("数据库查询异常");
            }

            if (tolNum <= 0) {
                log.info("货物管理业务-查询记录数为0");
                outBody.setTolPageNum(0);//总条数
                return Result.build(outBody, SUCCESS);
            }

            List<TProductInventoryInfoDto> outList =null;
            try {
                outList = tProductInventoryInfoMapper.selectWhereByPhone(inDto,userNo);
            } catch (DataAccessException e) {
                log.error("数据库查询异常", e);
                throw new RuntimeException("数据库查询异常");
            }
            log.info("货物管理业务-查询货物信息成功");
            outBody.setTolPageNum(tolNum);//总条数
            outBody.setList(outList);
            return Result.build(outBody, SUCCESS);
        }

        //农批系统查询
        log.info("货物管理业务-农批系统货物查询");
        try {
            tolNum = tProductInventoryInfoMapper.selectCount(inDto);
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }

        if (tolNum <= 0) {
            log.info("货物管理业务-查询记录数为0");
            outBody.setTolPageNum(0);//总条数
            return Result.build(outBody, SUCCESS);
        }

        List<TProductInventoryInfoDto> outList =null;
        try {
            outList = tProductInventoryInfoMapper.selectWhere(inDto);
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        log.info("货物管理业务-查询货物信息成功");
        outBody.setTolPageNum(tolNum);//总条数
        outBody.setList(outList);
        return Result.build(outBody, SUCCESS);
    }

    @Override
    public Result<ResultOutDto> updateGoodsInfo(Request<TProductInventoryInfoDto> param) {

        if (ObjectUtils.isEmpty(param)) {
            return Result.build(1, "输入参数为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(1, "输入参数为空");
        }
        TProductInventoryInfoDto inDto = param.getBody();
        if (StringUtils.isEmpty(inDto.getPrdctId())) {
            log.info("货物管理业务-商品类型id为空");
            return Result.build(1, "商品类型id为空");
        }
        if (StringUtils.isEmpty(inDto.getProvId())) {
            log.info("货物管理业务-供货商id为空");
            return Result.build(1, "供货商id为空");
        }
        if (StringUtils.isEmpty(inDto.getUnit())) {
            log.info("货物管理业务-单位为空");
            return Result.build(1, "单位为空");
        }
        inDto.setUpdateDt(new Date());
        int tolNum = 0;
        try {
            tolNum = tProductInventoryInfoMapper.updateByPrimaryKeySelective(inDto);
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (tolNum != 1) {
            log.info("货物管理业务-修改库存信息失败");
            ResultOutDto resDto = new ResultOutDto();
            resDto.setResult(ResultOutDto.FAIL);
            return Result.build(resDto, FAIL);
        }

        log.info("货物管理业务-修改货物信息成功");
        ResultOutDto resDto = new ResultOutDto();
        resDto.setResult(ResultOutDto.SUCCESS);
        return Result.build(resDto, SUCCESS);
    }

    /**
     * 新增货物信息
     * @param param
     * @return
     */
    @Override
    public Result<ResultOutDto> addGoodsInfo(Request<TProductInventoryInfoDto> param) {

        if (ObjectUtils.isEmpty(param)) {
            return Result.build(1, "输入参数为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(1, "输入参数为空");
        }
        TProductInventoryInfoDto inDto = param.getBody();
        if (StringUtils.isEmpty(inDto.getPrdctId())) {
            log.info("货物管理业务-商品类型id为空");
            return Result.build(1, "商品类型id为空");
        }
        if (StringUtils.isEmpty(inDto.getProvId())) {
            log.info("货物管理业务-供货商id为空");
            return Result.build(1, "供货商id为空");
        }
        if (StringUtils.isEmpty(inDto.getUnit())) {
            log.info("货物管理业务-单位为空");
            return Result.build(1, "单位为空");
        }
        inDto.setUpdateDt(new Date());

        int result=0;
        try
        {
            result =   tProductInventoryInfoMapper.selectCountByPrimaryKey(inDto);
        } catch (DataAccessException e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("查询异常");
        }

        if(result>=1){
            return Result.build(1, "货物信息已存在");
        }
        int tolNum = 0;
        try {
            tolNum = tProductInventoryInfoMapper.insert(inDto);
        } catch (DataAccessException e) {
            log.error("数据库插入异常", e);
            throw new RuntimeException("新增异常");
        }
        if (tolNum <= 0) {
            log.info("货物管理业务-新增库存信息成功");
            ResultOutDto resDto = new ResultOutDto();
            resDto.setResult(ResultOutDto.FAIL);
            return Result.build(resDto, FAIL);
        }

        log.info("货物管理业务-新增库存信息成功");
        ResultOutDto resDto = new ResultOutDto();
        resDto.setResult(ResultOutDto.SUCCESS);
        return Result.build(resDto, SUCCESS);
    }

    @Override
    public Result<TProductInventoryInfoOutDto> countGoodsInfo(Request<TProductInventoryInfoDto> param) {

        if (ObjectUtils.isEmpty(param)) {
            return Result.build(1, "输入参数为空");
        }
        if (ObjectUtils.isEmpty(param.getBody())) {
            return Result.build(1, "输入参数为空");
        }
        TProductInventoryInfoDto inDto = param.getBody();
        if (inDto.getPageNum() == null || inDto.getPageSize() == null) {
            log.info("货物管理业务-分页参数不能为空");
            return Result.build(1, "分页参数为空");
        }
        if (inDto.getPageSize() <= 0) {
            log.info("货物管理业务-分页条数输入错误");
            return Result.build(1, "分页条数输入错误");
        }
        if (inDto.getPageNum() <= 0) {
            log.info("货物管理业务-分页页数输入错误");
            return Result.build(1, "分页页数输入错误");
        }
        int strNum = (inDto.getPageNum() - 1) * inDto.getPageSize();
        inDto.setStrNum(strNum);//起始记录数
        int tolNum = 0;
        TProductInventoryInfoOutDto outBody = new TProductInventoryInfoOutDto();

        try {
            tolNum = tProductInventoryInfoMapper.selectTolCount(inDto);
        } catch (Exception e) {
            log.error("数据库新增异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        if (tolNum <= 0) {
            log.info("货物管理业务-查询记录数为0");
            outBody.setTolPageNum(0);//总条数
            return Result.build(outBody, SUCCESS);
        }

        List<TProductInventoryInfoDto>  TpIInfoList= null;
        try {
        TpIInfoList =tProductInventoryInfoMapper.selectTolWhere(inDto);
        } catch (Exception e) {
            log.error("数据库查询异常", e);
            throw new RuntimeException("数据库查询异常");
        }
        log.info("货物管理业务-查询货物信息成功");
        outBody.setTolPageNum(tolNum);//总条数
        outBody.setList(TpIInfoList);
        return Result.build(outBody, SUCCESS);
    }
}
