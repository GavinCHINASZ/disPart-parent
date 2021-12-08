package com.dispart.service.impl;


import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.dispart.dao.mapper.TCargoInfoReportDetailsMapper;
import com.dispart.dao.mapper.TCargoInfoReportMapper;
import com.dispart.dao.mapper.TCustomInfoManagerMapper;
import com.dispart.dao.mapper.THopmmProducingareaMapper;
import com.dispart.dao.mapper.TProductTypeInfoMapper;
import com.dispart.dao.mapper.TVechicleProcurerMapper;
import com.dispart.dao.mapper.TVechicleRecordMapper;
import com.dispart.dto.entrance.D_0223FindDto;
import com.dispart.dto.entrance.D_0224AddDto;
import com.dispart.dto.entrance.D_0225FindDto;
import com.dispart.dto.entrance.D_0225FindOutDto;
import com.dispart.dto.entrance.D_0225FindOutYDto;
import com.dispart.dto.entrance.D_0226UpInDto;
import com.dispart.dto.entrance.D_0228FindInDto;
import com.dispart.dto.entrance.D_0228FindOutDto;
import com.dispart.dto.entrance.D_0238FindDto;
import com.dispart.dto.entrance.D_0281UpInDto;
import com.dispart.dto.entrance.D_0345FindDto;
import com.dispart.feign.DISP20210310Feign;
import com.dispart.request.Request;
import com.dispart.request.RequestHead;
import com.dispart.result.EntranceResult_WEnum;
import com.dispart.result.Result;
import com.dispart.service.EntranceCargoService;
import com.dispart.vo.entrance.TCargoInfoReport;
import com.dispart.vo.entrance.TCargoInfoReportDetails;
import com.dispart.vo.entrance.TCustomInfoManager;
import com.dispart.vo.entrance.THopmmProducingarea;
import com.dispart.vo.entrance.TVechicleRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

//来货报备
@Slf4j
@Service
public class EntranceCargoServiceImpl implements EntranceCargoService {
    @Autowired
    TCustomInfoManagerMapper tCustomInfoManagerMapper;

    @Autowired
    TProductTypeInfoMapper tProductTypeInfoMapper;

    @Autowired
    TCargoInfoReportMapper tCargoInfoReportMapper;

    @Autowired
    TCargoInfoReportDetailsMapper tCargoInfoReportDetailsMapper;

    @Autowired
    TVechicleProcurerMapper tVechicleProcurerMapper;

    @Autowired
    TVechicleRecordMapper tVechicleRecordMapper;

    @Autowired
    THopmmProducingareaMapper tHopmmProducingareaMapper;

    @Autowired
    DISP20210310Feign disp20210310Feign;
    //客户信息查询
    @Override
    public Result findCus(D_0223FindDto record) {
        if(StringUtil.isBlank(record.getPhone())){
            return Result.build(null,1,"电话号码不能为空");
        }
        TCustomInfoManager res0223=new TCustomInfoManager();
        try {
            res0223 = tCustomInfoManagerMapper.findCustomerInfoByPhone(record);
        }catch (Exception e){
            log.error("客户信息查询-查询失败: "+record,e);
            throw new RuntimeException("客户信息查询-查询失败");
        }
        return Result.build(res0223,EntranceResult_WEnum.SUCCESS);
    }
    //查询品种信息
    @Override
    public Result findProd(D_0228FindInDto record) {
        D_0228FindOutDto res0228 = new D_0228FindOutDto();
        if(!"0".equals(record.getType())){//0-查询品种全部信息，其他-查询品种名称集合
            try {
                res0228.setPrdctNmList(tProductTypeInfoMapper.findListByNm(record));

            }catch (Exception e){
                log.error("查询品种信息-查询品种名称集合失败",e);
                throw new RuntimeException("查询品种信息-查询品种名称集合失败");
            }
            return Result.build(res0228,EntranceResult_WEnum.SUCCESS);
        }
        try {
            if(record.getPrdctNm()==null || "".equals(record.getPrdctNm())){
                return Result.build(null,1,"品种名称不能为空");
            }
            res0228 = tProductTypeInfoMapper.findByNm(record);
        }catch (Exception e){
            log.error("查询品种信息-品种具体信息查询失败",e);
            throw new RuntimeException("查询品种信息-品种具体信息查询失败");
        }
        return Result.build(res0228,EntranceResult_WEnum.SUCCESS);
    }
    //来货报备申请
    @Override
    @Transactional
    public Result inCargo(Request<D_0224AddDto>  d_0224AddDtoRequest) {
        D_0224AddDto record  = d_0224AddDtoRequest.getBody();
        RequestHead requestHead = d_0224AddDtoRequest.getHead();
        if(record.getPhone() == null || "".equals(record.getPhone())){
            return Result.build(null,1,"手机号码不能为空");
        }
        if(record.getProvNm() == null || "".equals(record.getProvNm())){
            return Result.build(null,1,"客户名称不能为空");
        }
        if(record.getProvId() == null || "".equals(record.getProvId())){
            return Result.build(null,1,"客户ID不能为空");
        }
        if(record.getVehicleNum() == null || "".equals(record.getVehicleNum())){
            return Result.build(null,1,"车牌号不能为空");
        }
        if(record.getVehicleId() == null || "".equals(record.getVehicleId())){
            return Result.build(null,1,"车辆车型ID不能为空");
        }
        if(record.getVehicleTpId() == null || "".equals(record.getVehicleTpId())){
            return Result.build(null,1,"车辆类别ID不能为空");
        }
        if(record.getReportDt() == null || "".equals(record.getReportDt())){
            return Result.build(null,1,"车辆最后进场日期不能为空");
        }
        if(record.getVehicleWeight() == null || "".equals(record.getVehicleWeight())){
            return Result.build(null,1,"车辆净重不能为空");
        }
        TCargoInfoReport report = null;
        try {
            report = tCargoInfoReportMapper.findByVehicleNum(record.getVehicleNum());
        } catch (Exception e) {
            log.error("来货报备申请-查询来货报备主信息失败",e);
            throw new RuntimeException("来货报备申请-查询来货报备主信息失败");
        }
        if(report!=null&&StringUtil.isNotBlank(report.getAuditorSt())){
            if("0".equals(report.getAuditorSt())){
                return Result.build(null,1,"该车辆已存在待审核的报备信息");
            }else if("1".equals(report.getAuditorSt())){
                return Result.build(null,1,"该车辆已存在审核通过的报备信息");
            }
        }
        //设置主表ID
        int xl = tVechicleProcurerMapper.findNextval("reportId");
        String reportId= ""+(1000000+xl);
        record.setReportId(reportId);
        record.setOperId(requestHead.getOperator());
        record.setCreator(requestHead.getOperator());
        //登记主表
        try {
            record.setAuditorSt("0");//设置为未审核状态
            tCargoInfoReportMapper.insert0224(record);
        }catch (Exception e){
            log.error("来货报备申请-添加来货报备主信息失败",e);
            throw new RuntimeException("来货报备申请-添加来货报备主信息失败");
        }
        //登记明细表
        if(record.getPrdList().size()<=0){
            return Result.ok();
        }
        try {
            log.debug("报备明细-入参 ： "+ record.getPrdList());
            for(TCargoInfoReportDetails tCar :record.getPrdList()){
                tCar.setReportId(reportId);
            }
            tCargoInfoReportDetailsMapper.insertList(record.getPrdList());
        }catch (Exception e){
            log.error("来货报备申请-添加来货报备产品明细信息失败",e);
            throw new RuntimeException("来货报备申请-添加来货报备产品明细信息失败");
        }
        //待审核报备信息推送
        JSONObject result;
        Request d0310Request = new Request();
        RequestHead head = new RequestHead();
        head.setReqSeqNo(String.valueOf(System.currentTimeMillis()+new Random().nextInt(999999)));
        d0310Request.setHead(head);
        JSONObject body = new JSONObject();
        body.put("busineNo",record.getReportId());
        body.put("provId",record.getProvId());
        body.put("parameterType",5);
        d0310Request.setBody(body);
        try {
            result = disp20210310Feign.DISP20210310(d0310Request);
        } catch (Exception e) {
            log.error("来货报备-调用信息推送接口失败: ",e);
            return Result.ok();
        }
        if(!("200".equals(result.getString("code")))){
            log.error("来货报备-信息推送失败: "+result.getString("message"));
        }
        return Result.ok();
    }

    //来货报备信息查询
    @Override
    public Result findCargo(Request<D_0225FindDto>  d_0225FindDtoRequest) {
        D_0225FindDto record = d_0225FindDtoRequest.getBody();
        //查询主表信息
        List<D_0225FindOutYDto> d_0225FindOutYDtoS = new ArrayList<D_0225FindOutYDto>();
        D_0225FindOutDto d_0225FindOutDto = new D_0225FindOutDto();
        Integer toleNum = 0;
        if(record.getCurPage() != null && record.getCurPage()>0 && record.getPageSize()>0) {//有分页参数才做分页查询
            Integer curPage = (record.getCurPage()-1)*record.getPageSize();
            record.setCurPage(curPage);
        }else {
            record.setPageSize(0);
        }
        try {
            d_0225FindOutYDtoS=tCargoInfoReportMapper.findByParm(record);
            if(record.getPageSize()>0){
                toleNum = tCargoInfoReportMapper.findNumByParm(record);
                d_0225FindOutDto.setTolPageNum(toleNum);
            }
        }catch (Exception e){
            log.error("来货报备信息查询-查询来货报备主表信息失败",e);
            throw new RuntimeException("来货报备信息查询-查询来货报备主表信息失败");
        }
        //查询子表信息
        try {
            if(d_0225FindOutYDtoS.size()>0){
                for(D_0225FindOutYDto d_0225FindOutYDto : d_0225FindOutYDtoS ){
                    d_0225FindOutYDto.setPrdList(tCargoInfoReportDetailsMapper.selectByPrimaryKey(d_0225FindOutYDto.getReportId()));
                }
            }
        }catch (Exception e){
            log.error("来货报备信息查询-查询来货报备明细信息失败",e);
            throw new RuntimeException("来货报备信息查询-查询来货报备明细信息失败");
        }
        d_0225FindOutDto.setReportList(d_0225FindOutYDtoS);
        return Result.build(d_0225FindOutDto,EntranceResult_WEnum.SUCCESS);
    }

    //来货报备信息复核
    @Override
    public Result updateByPrimaryKey(Request<D_0226UpInDto>  d_0226UpInDtoRequest) {
        D_0226UpInDto record = d_0226UpInDtoRequest.getBody();
        RequestHead head = d_0226UpInDtoRequest.getHead();
        record.setAuditor(head.getOperator());
        if(record.getReportId() == null || "".equals(record.getReportId())){
            return Result.build(null,1,"来货报备ID不能为空");
        }
        if(record.getAuditorSt() == null || "".equals(record.getAuditorSt())){
            return Result.build(null,1,"审核意见不能为空");
        }
        if(record.getIsFree() == null || "".equals(record.getIsFree())){
            return Result.build(null,1,"请设置是否免除进场费");
        }
        if(!("1".equals(record.getAuditorSt()) || "2".equals(record.getAuditorSt()))){
            return Result.build(null,1,"录入的审批意见不对");
        }
        try {
            String auSt= tCargoInfoReportMapper.findCargoAuSt(record.getReportId());
            log.debug("审核来货报备信息-检查审核状态： "+auSt);
            if (!("0".equals(auSt) || "1".equals(auSt) || "2".equals(auSt))){//未进场的都可以审核（0,1,2）
                return Result.build(null,1,"该记录已被进场使用，不能再次审核");
            }
        }catch (Exception e){
            log.error("审核来货报备信息-查询审核状态失败: "+record,e);
            throw new RuntimeException("审核来货报备信息-查询审核状态失败");
        }
        try {
            record.setAuditorTm(new Date());
            tCargoInfoReportMapper.updateByPrimaryKey(record);
        }catch (Exception e){
            log.error("审核来货报备信息-修改报备审核信息失败: "+record,e);
            throw new RuntimeException("审核来货报备信息-修改报备审核信息失败");
        }
        //报备信息审核结果消息推送
        TCargoInfoReport tCargoInfoReport;
        try {
             tCargoInfoReport = tCargoInfoReportMapper.selectByPrimaryKey(record.getReportId());
        } catch (Exception e) {
            log.error("审核来货报备信息-获取报备信息失败: "+record,e);
            return Result.ok();
        }
        JSONObject result;
        Request d0310Request = new Request();
        d0310Request.setHead(head);
        JSONObject body = new JSONObject();
        body.put("busineNo",tCargoInfoReport.getReportId());
        body.put("provId",tCargoInfoReport.getProvId());
        body.put("parameterType",4);
        List parameterList = new ArrayList();

//        审核状态 1-审核通过 2-审核未通过
        if("1".equals(record.getAuditorSt())){
            parameterList.add("审核通过");
        }else if("2".equals(record.getAuditorSt())){
            parameterList.add("驳回");
        }
        body.put("parameterList",parameterList);
        d0310Request.setBody(body);
        try {
            result = disp20210310Feign.DISP20210310(d0310Request);
        } catch (Exception e) {
            log.error("审核来货报备信息-调用信息推送接口失败: "+record,e);
            return Result.ok();
        }
        if(!("200".equals(result.getString("code")))){
            log.error("审核来货报备信息-信息推送失败: "+result.getString("message"));
        }
        return Result.ok();
    }

    //报备记录修改或作废
    @Override
    @Transactional
    public Result upCargo(Request<D_0281UpInDto> d_0281UpInDtoRequest) {
        D_0281UpInDto record = d_0281UpInDtoRequest.getBody();
        RequestHead requestHead = d_0281UpInDtoRequest.getHead();
        record.setModifier(requestHead.getOperator());
        if(record.getReportId() == null || "".equals(record.getReportId())){
            return Result.build(null,1,"报备主表ID不能为空");
        }
        if(StringUtil.isBlank(record.getWorkType())){
            return Result.build(null,1,"操作类型不能为空");
        }
        if("1".equals(record.getWorkType())){//1-作废

            try {
                String auSt= tCargoInfoReportMapper.findCargoAuSt(record.getReportId());
                log.debug("作废报备申请-检查审核状态： "+auSt);
                if ("3".equals(auSt)){//已进场的不可以作废
                    return Result.build(null,1,"该记录已被进场使用,不能作废");
                }
            }catch (Exception e){
                log.error("作废报备申请-查询审核状态失败: "+record,e);
                throw new RuntimeException("作废报备申请-查询审核状态失败");
            }

            try {
                tCargoInfoReportMapper.updatVeToNoByPrimaryKey(record);
            }catch (Exception e){
                log.error("报备记录作废-修改状态失败",e);
                throw new RuntimeException("报备记录作废-修改失败");
            }
            return Result.ok();
        }

        if(record.getPhone() == null || "".equals(record.getPhone())){
            return Result.build(null,1,"手机号码不能为空");
        }
        if(record.getVehicleNum() == null || "".equals(record.getVehicleNum())){
            return Result.build(null,1,"车牌号不能为空");
        }
        if(record.getVehicleId() == null || "".equals(record.getVehicleId())){
            return Result.build(null,1,"车辆车型不能为空");
        }
        //修改报备主表信息

        try {
            String auSt= tCargoInfoReportMapper.findCargoAuSt(record.getReportId());
            log.debug("修改报备申请-检查审核状态： "+auSt);
            if (!("0".equals(auSt) || "2".equals(auSt))){//未审核与审核不通过的可以修改
                return Result.build(null,1,"该记录已审批通过,不能修改");
            }
        }catch (Exception e){
            log.error("修改报备申请-查询审核状态失败: "+record,e);
            throw new RuntimeException("修改报备申请-查询审核状态失败");
        }
        record.setModifyTime(new Date());
        try {
            tCargoInfoReportMapper.updatVeByPrimaryKey(record);
        }catch (Exception e){
            log.error("修改报备申请-修改报备主表失败："+record,e);
            throw new RuntimeException("修改报备申请-修改报备主表失败");
        }

        //删除明细表中对应记录
        if (record.getPrdList().size()<=0){
            return Result.ok();
        }
        try {
            tCargoInfoReportDetailsMapper.deleteByReportId(record.getReportId());
        }catch (Exception e){
            log.error("修改报备申请-删除来货报备产品明细信息失败",e);
            throw new RuntimeException("修改报备申请-删除来货报备产品明细信息失败");
        }
        //登记明细表
        try {
            for(TCargoInfoReportDetails tcas : record.getPrdList()){
                tcas.setReportId(record.getReportId());
            }
            tCargoInfoReportDetailsMapper.insertList(record.getPrdList());
        }catch (Exception e){
            log.error("修改报备申请-添加来货报备产品明细信息失败",e);
            throw new RuntimeException("修改报备申请-添加来货报备产品明细信息失败");
        }
        return Result.ok();
    }

    /**
     * 查询车辆档案记录
     * @param d_0238FindDtoRequest
     * @return
     */
    @Override
    public Result findVeByVeNum(Request<D_0238FindDto> d_0238FindDtoRequest) {
        D_0238FindDto dto = d_0238FindDtoRequest.getBody();
        if(StringUtil.isBlank(dto.getVehicleNum())){
            return Result.build(1,"车牌号不能为空");
        }
        TVechicleRecord tVechicleRecord;
        try {
            tVechicleRecord = tVechicleRecordMapper.selectByPrimaryKey(dto.getVehicleNum());
        } catch (Exception e) {
            log.error("查询车辆档案记录信息失败",e);
            throw new RuntimeException("查询车辆档案记录信息失败");
        }
        return Result.ok(tVechicleRecord);
    }
    /**
     * 报备商品产地查询
     * @param d_0345FindDtoRequest
     * @return
     */
    @Override
    public Result findProdPlace(Request<D_0345FindDto> d_0345FindDtoRequest) {
        D_0345FindDto dto = d_0345FindDtoRequest.getBody();
        List<THopmmProducingarea> list;
        try {
            list = tHopmmProducingareaMapper.getList(dto);
        } catch (Exception e) {
            log.error("查询产地信息失败",e);
            throw new RuntimeException("查询产地信息失败");
        }
        return Result.ok(list);
    }
}
