package com.dispart.service.impl;


import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dispart.dao.TDeviceRelevanceMapper;
import com.dispart.dao.mapper.TCargoInfoReportDetailsMapper;
import com.dispart.dao.mapper.TCargoInfoReportMapper;
import com.dispart.dao.mapper.TCarioGoWhereMapper;
import com.dispart.dao.mapper.TCustomInfoManagerMapper;
import com.dispart.dao.mapper.TParmeterInfoMapper;
import com.dispart.dao.mapper.TPayJrnMapper;
import com.dispart.dao.mapper.TProductTypeInfoMapper;
import com.dispart.dao.mapper.TVechicleProcurerDetailsMapper;
import com.dispart.dao.mapper.TVechicleProcurerMapper;
import com.dispart.dao.mapper.TVechicleRecordMapper;
import com.dispart.dao.mapper.TVehicleBlacklistMapper;
import com.dispart.dao.mapper.TVehicleCustomInfoMapper;
import com.dispart.dao.mapper.TVehicleManagerMapper;
import com.dispart.dao.mapper.TVehicleMonthMapper;
import com.dispart.dao.mapper.TvechiclePayManagerMapper;
import com.dispart.dto.entrance.D_0227FindDto;
import com.dispart.dto.entrance.D_0227OutDto;
import com.dispart.dto.entrance.D_0229addInDto;
import com.dispart.dto.entrance.D_0230addInDto;
import com.dispart.dto.entrance.D_0230addOutDto;
import com.dispart.dto.entrance.D_0231upInDto;
import com.dispart.dto.entrance.D_0232FindDto;
import com.dispart.dto.entrance.D_0233Dto;
import com.dispart.dto.entrance.D_0234findInDto;
import com.dispart.dto.entrance.D_0235UpInDto;
import com.dispart.dto.entrance.D_0236Dto;
import com.dispart.dto.entrance.D_0236OutDto;
import com.dispart.dto.entrance.D_0237FindDto;
import com.dispart.dto.entrance.D_0346FindDto;
import com.dispart.dto.entrance.D_0365FindDto;
import com.dispart.dto.entrance.D_0503FindDto;
import com.dispart.dto.entrance.EntranceVeCheckOutDatilsDto;
import com.dispart.dto.entrance.TVehicleCustomInfoInDto;
import com.dispart.enums.PayStatusEnum;
import com.dispart.feign.DISP20210105Feign;
import com.dispart.feign.DISP20210261Feign;
import com.dispart.feign.DISP20210310Feign;
import com.dispart.model.TDeviceRelevance;
import com.dispart.model.base.CarChargeStdManage;
import com.dispart.model.businessCommon.TPayJrn;
import com.dispart.model.businessCommon.TransMdEnum;
import com.dispart.model.businessCommon.TxnTypeEnum;
import com.dispart.model.entranceCommon.EntranceStaEnum;
import com.dispart.request.Request;
import com.dispart.request.RequestHead;
import com.dispart.result.EntranceResult_WEnum;
import com.dispart.result.Result;
import com.dispart.service.EntranceVeService;
import com.dispart.vo.commons.TParmeterInfo;
import com.dispart.vo.entrance.D_0365OutVo;
import com.dispart.vo.entrance.D_0503OutVO;
import com.dispart.vo.entrance.TCargoInfoReport;
import com.dispart.vo.entrance.TCargoInfoReportDetails;
import com.dispart.vo.entrance.TCarioGoWhere;
import com.dispart.vo.entrance.TCustomInfoManager;
import com.dispart.vo.entrance.TVechicleProcurer;
import com.dispart.vo.entrance.TVechicleProcurerDetails;
import com.dispart.vo.entrance.TVechicleRecord;
import com.dispart.vo.entrance.TVehicleBlacklist;
import com.dispart.vo.entrance.TVehicleManager;
import com.dispart.vo.entrance.TVehicleMonth;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.dispart.result.ResultCodeEnum.SUCCESS;
import static com.dispart.result.UserResultCodeEnum.USER_UPLOAD_FILE_FAIL;

//来货报备
@Slf4j
@Service
@RefreshScope
public class EntranceVeServiceImpl implements EntranceVeService {
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
    TVehicleManagerMapper tVehicleManagerMapper;

    @Autowired
    TVechicleProcurerDetailsMapper tVechicleProcurerDetailsMapper;

    @Autowired
    TvechiclePayManagerMapper tvechiclePayManagerMapper;

    @Autowired
    TVehicleMonthMapper tVehicleMonthMapper;

    @Autowired
    TDeviceRelevanceMapper tDeviceRelevanceMapper;

    @Autowired
    TCarioGoWhereMapper tCarioGoWhereMapper;

    @Autowired
    TParmeterInfoMapper tParmeterInfoMapper;

    @Autowired
    TVehicleBlacklistMapper tVehicleBlacklistMapper;

    @Autowired
    TPayJrnMapper tPayJrnMapper;

    @Autowired
    TVehicleCustomInfoMapper tVehicleCustomInfoMapper;

    @Autowired
    DISP20210261Feign disp20210261Feign;

    @Autowired
    DISP20210310Feign disp20210310Feign;

    @Autowired
    DISP20210105Feign disp20210105Feign;

    @Value("${vehicle.vehicleId}")
    String vehicleId;

    @Value("${vehicle.vehicle}")
    String vehicle;

    @Value("${vehicle.vehicleTpId}")
    String vehicleTpId;

    @Value("${vehicle.vehicleTp}")
    String vehicleTp;

    @Value("${tempAccount.tempProvId}")
    String tempProvId;

    @Value("${fileProps.fileLocalPath}")
    private String fileLocalPath;

    @Value("${fileProps.filePageSize}")
    private String filePageSize;

    //大车进场-查询报备信息
    @Override
    public Result findVePro(Request<D_0227FindDto> d_0227FindDtoRequest) {
        D_0227FindDto record = d_0227FindDtoRequest.getBody();
        TCargoInfoReport tCargoInfoReport = null;
        if(StringUtil.isNotBlank(record.getVehicleNum())){
            try {
                tCargoInfoReport = tCargoInfoReportMapper.findVeByVehicleNum(record.getVehicleNum());
            }catch (Exception e){
                log.error("大车进场-查询报备信息-主表信息查询失败： "+record,e);
                throw new RuntimeException("大车进场-查询报备信息-主表信息查询失败");
            }
        }
        //客户信息
        JSONObject customInfo = new JSONObject();
        TCustomInfoManager tCustomInfoManager = null ;
        if(StringUtil.isNotBlank(record.getKey())&&tCargoInfoReport==null){
            try {
                tCustomInfoManager =  tCustomInfoManagerMapper.selectByPhoneOrMnmnCode(record.getKey());
                if(tCustomInfoManager==null){
                    tCustomInfoManager =  tCustomInfoManagerMapper.selectByCardNo(record.getKey());
                }
            }catch (Exception e){
                log.error("大车进场-查询客户信息-查询失败： "+record,e);
                throw new RuntimeException("大车进场-查询客户信息-查询失败");
            }
        }
        if(tCustomInfoManager!=null){
            customInfo.put("provId",tCustomInfoManager.getProvId());
            customInfo.put("provNm",tCustomInfoManager.getProvNm());
            customInfo.put("customTp",tCustomInfoManager.getCustomTp());
            customInfo.put("phone",tCustomInfoManager.getPhone());
        }else if(tCargoInfoReport!=null){
            customInfo.put("provId",tCargoInfoReport.getProvId());
            customInfo.put("provNm",tCargoInfoReport.getProvNm());
            customInfo.put("customTp",tCargoInfoReport.getCustomTp());
            customInfo.put("phone",tCargoInfoReport.getPhone());
        }
        D_0227OutDto res0227 = new D_0227OutDto();
        if(customInfo.isEmpty()){
            customInfo=null;
        }
        res0227.setCustomInfo(customInfo);
        //车辆信息
        JSONObject vechicleInfo = new JSONObject();
        TVechicleRecord tVechicleRecord;
        try {
            tVechicleRecord = tVechicleRecordMapper.selectByPrimaryKey(record.getVehicleNum());
        }catch (Exception e){
            log.error("大车进场-查询车辆信息-查询失败： "+record,e);
            throw new RuntimeException("大车进场-查询车辆信息-查询失败");
        }
        //如果车辆信息没有查询到，就使用报备信息中车辆信息
        if(tVechicleRecord!=null){
            vechicleInfo.put("vehicleNum",record.getVehicleNum());
            vechicleInfo.put("vehicleTp",tVechicleRecord.getVehicleTp());
            vechicleInfo.put("vehicleTpId",tVechicleRecord.getVehicleTpId());
            vechicleInfo.put("vehicle",tVechicleRecord.getVehicle());
            vechicleInfo.put("remark",tVechicleRecord.getRemark());
            vechicleInfo.put("operId",tVechicleRecord.getOperId());
            vechicleInfo.put("creatTime",tVechicleRecord.getCreatTime());
            vechicleInfo.put("upTime",tVechicleRecord.getUpTime());
            vechicleInfo.put("vehicleId",tVechicleRecord.getVehicleId());
            vechicleInfo.put("vehicleWeight",tVechicleRecord.getEmptyWght());
        }
        //报备信息
        if(tCargoInfoReport==null){
            res0227.setCargoInfo(null);
        }else {
            TVehicleManager tVehicleManager = new TVehicleManager();
            if(StringUtil.isNotBlank(tCargoInfoReport.getVehicleId())&&StringUtil.isNotBlank(tCargoInfoReport.getVehicleTpId())){
                tVehicleManager.setVehicleId(tCargoInfoReport.getVehicleId());
                tVehicleManager.setVehicleTpId(tCargoInfoReport.getVehicleTpId());
                try {
                    tVehicleManager = tVehicleManagerMapper.selectByVeIdVeTpId(tVehicleManager);
                } catch (Exception e) {
                    log.error("大车进场-查询车辆管理信息-查询失败： "+record,e);
                }
            }
            if(StringUtil.isBlank(vechicleInfo.getString("vehicleNum"))){
                vechicleInfo.put("vehicleNum",tCargoInfoReport.getVehicleNum());
            }
            if(StringUtil.isBlank(vechicleInfo.getString("vehicleId"))){
                if(tVehicleManager!=null&&StringUtil.isNotBlank(tVehicleManager.getVehicleId())){
                    vechicleInfo.put("vehicleId",tVehicleManager.getVehicleId());
                }else {
                    vechicleInfo.put("vehicleId",tCargoInfoReport.getVehicleId());
                }
            }
            if(tVehicleManager!=null&&StringUtil.isBlank(vechicleInfo.getString("vehicle"))){
                vechicleInfo.put("vehicle",tVehicleManager.getVehicle());
            }
            if(StringUtil.isBlank(vechicleInfo.getString("vehicleTpId"))){
                if(tVehicleManager!=null&&StringUtil.isNotBlank(tVehicleManager.getVehicleTpId())){
                    vechicleInfo.put("vehicleTpId",tVehicleManager.getVehicleTpId());
                }else {
                    vechicleInfo.put("vehicleTpId",tCargoInfoReport.getVehicleTpId());
                }
            }
            if(tVehicleManager!=null&&StringUtil.isBlank(vechicleInfo.getString("vehicleTp"))){
                vechicleInfo.put("vehicleTp",tVehicleManager.getVehicleTp());
            }
            if(StringUtil.isBlank(vechicleInfo.getString("vehicleWeight"))){
                vechicleInfo.put("vehicleWeight",tCargoInfoReport.getVehicleWeight());
            }
            if(StringUtil.isBlank(customInfo.getString("provNm"))){
                customInfo.put("provNm",tCargoInfoReport.getProvNm());
                customInfo.put("customTp",tCargoInfoReport.getCustomTp());
                customInfo.put("phone",tCargoInfoReport.getPhone());
            }
            try {
                List<TCargoInfoReportDetails> tcs;
                tcs = tCargoInfoReportDetailsMapper.selectByPrimaryKey(tCargoInfoReport.getReportId());
//            报备信息
                JSONObject cargoInfo = new JSONObject();
                cargoInfo.put("reportId",tCargoInfoReport.getReportId());
                cargoInfo.put("reportDt",tCargoInfoReport.getReportDt());
                cargoInfo.put("vehicleTtlWght",tCargoInfoReport.getVehicleTtlWght());
                cargoInfo.put("prdctWght",tCargoInfoReport.getPrdctWght());
                cargoInfo.put("placeUrl",tCargoInfoReport.getPlaceUrl());
                cargoInfo.put("isFree",tCargoInfoReport.getIsFree());
                cargoInfo.put("recordSt",tCargoInfoReport.getRecordSt());
                cargoInfo.put("auditorSt",tCargoInfoReport.getAuditorSt());
                cargoInfo.put("creator",tCargoInfoReport.getCreator());
                cargoInfo.put("auditorTm",tCargoInfoReport.getAuditorTm());
                cargoInfo.put("modifier",tCargoInfoReport.getModifier());
                cargoInfo.put("modifyTime",tCargoInfoReport.getModifyTime());
                cargoInfo.put("remark",tCargoInfoReport.getRemark());
                cargoInfo.put("operId",tCargoInfoReport.getOperId());
                cargoInfo.put("creatTime",tCargoInfoReport.getCreatTime());
                cargoInfo.put("upTime",tCargoInfoReport.getUpTime());
                if(tcs.size()>0){
                    cargoInfo.put("prdList",tcs);
                }
                res0227.setCargoInfo(cargoInfo);
            }catch (Exception e){
                log.error("大车进场-查询报备信息-明细表信息查询失败： "+record,e);
                throw new RuntimeException("大车进场-查询报备信息-明细表信息查询失败");
            }
        }
        if(vechicleInfo.isEmpty()){
            vechicleInfo=null;
        }
        res0227.setVechicleInfo(vechicleInfo);
        //车辆黑名单
        TVehicleBlacklist tVehicleBlacklist = null;
        try {
            tVehicleBlacklist = tVehicleBlacklistMapper.selectByPk(record.getVehicleNum());
        } catch (Exception e) {
            log.error("大车进场-查询报备信息-车辆黑名单询失败： "+record,e);
        }
        res0227.setVehicleBlacklist(tVehicleBlacklist);
        return Result.build(res0227,EntranceResult_WEnum.SUCCESS);
    }
//大车进场登记
    @Override
    @Transactional
    public Result addVeDate(Request<D_0229addInDto> d_0229addInDtoRequest) {
        D_0229addInDto record = d_0229addInDtoRequest.getBody();
        RequestHead head =d_0229addInDtoRequest.getHead();
        record.setInOperId(head.getOperator());
        record.setInOperNm(head.getOperName());
        if(record.getVehicleNum()==null || "".equals(record.getVehicleNum())){
            return Result.build(null, 1,"车牌号不能为空");
        }
        if(record.getVehicleId()==null || "".equals(record.getVehicleId())){
            return Result.build(null, 1,"车辆车型不能为空");
        }
        if(record.getInTtlWght()==null || "".equals(record.getInTtlWght())){
            return Result.build(null, 1,"进场总重不能为空");
        }
        if(record.getInDoor()==null || "".equals(record.getInDoor())){
            return Result.build(null, 1,"进场地点不能为空");
        }
        if(record.getInOperId()==null || "".equals(record.getInOperId())){
            return Result.build(null, 1,"进场操作人不能为空");
        }
        if(StringUtil.isBlank(record.getIsDerated())){
            return Result.build(null, 1,"是否减免标识不能为空");
        }
        if(StringUtil.isNotBlank(record.getInDoor())){
            D_0232FindDto d0232FindDto =new D_0232FindDto();
            d0232FindDto.setDeviceNo(record.getInDoor());
            if(StringUtil.isBlank(d0232FindDto.getDeviceNo())){
                return Result.build(null, 1,"设备编号不能为空");
            }
            TDeviceRelevance tDeviceRelevance=null;
            try {
                tDeviceRelevance = tDeviceRelevanceMapper.selectIOByInOutId(d0232FindDto);
            } catch (Exception e) {
                log.error("获取进出场名称-获取进出场名称失败");
            }
            if(tDeviceRelevance!=null){
                record.setInDoorNm(tDeviceRelevance.getInOut());
            }
        }
        //根据车牌号 获取 车辆档案,若车辆档案为空或者与当前录入的车辆车型信息不一致则更新档案表
        TVechicleRecord tvr =new TVechicleRecord();
        tvr.setProvId(record.getProvId());
        tvr.setVehicleNum(record.getVehicleNum());
        tvr.setEmptyWght(record.getTareWght());
        tvr.setVehicle(record.getVehicle());
        tvr.setVehicleId(record.getVehicleId());
        tvr.setVehicleTp(record.getVehicleTp());
        tvr.setVehicleTpId(record.getVehicleTpId());
        TCustomInfoManager tCustomInfoManager = null;
        if(StringUtil.isBlank(record.getProvId())){
            record.setProvId(tempProvId);
        }
        try {
            tCustomInfoManager = tCustomInfoManagerMapper.selectByPrimaryKey(record.getProvId());
        } catch (Exception e) {
            log.error("大车入场登记-获取客户信息失败");
        }
        if(tCustomInfoManager!=null){
            record.setProvNm(tCustomInfoManager.getProvNm());
            record.setPhone(tCustomInfoManager.getPhone());
            tvr.setDriverNm(tCustomInfoManager.getProvNm());
            tvr.setDriverPhone(tCustomInfoManager.getPhone());
        }else {
            tvr.setDriverNm(record.getProvNm());
            tvr.setDriverPhone(record.getPhone());
        }
        maintainVehicleRecord(tvr,false,null);
        //设置进场时间
        record.setInTime(new Date());
        //获取序列值，组装进出场ID
        int xl = tVechicleProcurerMapper.findNextval("inId");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String inIdq = sdf.format(new Date());
        String inId= inIdq+(10000000+xl);
        record.setInId(inId);

        //设置进出场未收款状态(状态 0-未付款 1-已进场 2-退款中 3-已出场)
        record.setStatus("0");
        D_0230addOutDto d_0230addOutDto = new D_0230addOutDto();
        d_0230addOutDto.setInId(inId);

        //进场金额计算 是否固定收取 0-默认 1-是
        if(StringUtil.isBlank(record.getIsFixed())){
            record.setIsFixed("0");
        }
        if("1".equals(record.getIsDerated())){
            record.setInActAmt(BigDecimal.ZERO);
            record.setFixedAmt(BigDecimal.ZERO);
        }else if("1".equals(record.getIsFixed())){
           if(record.getFixedAmt()==null) {
               record.setFixedAmt(BigDecimal.ZERO);
           }
        }else if("0".equals(record.getIsFixed())){
            record.setFixedAmt(BigDecimal.ZERO);
            if(record.getInTtlAmt()==null){
                record.setInTtlAmt(BigDecimal.ZERO);
            }
            if(record.getInAmt()==null){
                record.setInAmt(BigDecimal.ZERO);
            }
            if(record.getInActAmt()==null){
                record.setInActAmt(BigDecimal.ZERO);
            }
        }
        record.setInPayStatus("9");
        record.setOutPayStatus("9");
        if(record.getCargoWght()==null){
            record.setCargoWght(BigDecimal.ZERO);
        }
        if(record.getPeelReturnAmt()==null){
            record.setPeelReturnAmt(BigDecimal.ZERO);
        }
        if(record.getPeelSupptAmt()==null){
            record.setPeelSupptAmt(BigDecimal.ZERO);
        }
        if(record.getCheckReturnAmt()==null){
            record.setCheckReturnAmt(BigDecimal.ZERO);
        }
        if(record.getCheckSupptAmt()==null){
            record.setCheckSupptAmt(BigDecimal.ZERO);
        }
        if(record.getOutTtlAmt()==null){
            record.setOutTtlAmt(BigDecimal.ZERO);
        }
        //登记进出场主表
        try{
            tVechicleProcurerMapper.insert(record);
        }catch (Exception e){
            log.error("大车进场登记-添加车辆进出管理表信息失败： "+record,e);
            throw new RuntimeException("大车进场登记-添加车辆进出管理表信息失败");
        }
        try{
            if(record.getPrdList()!=null&&record.getPrdList().size()>0){
                String expTp = "0";//收费类型
                if(!(record.getBoothAmt()==null || "".equals(record.getBoothAmt()))){ //临时摊位费大于0，收费类型取 1-摊位费
                    expTp="1";
                }
                for(TVechicleProcurerDetails tve : record.getPrdList()){//向明细数据添加客户编号与车辆管理主编号
                    tve.setProvId(record.getProvId());
                    tve.setInId(inId);
                    tve.setVehicleNum(record.getVehicleNum());
                    tve.setExpTp(expTp);
                }
                log.debug("明细添加进场ID ： "+record.getPrdList());
                tVechicleProcurerDetailsMapper.insert(record.getPrdList());
            }
        }catch (Exception e){
            log.error("大车进场登记-添加进场货物明细信息失败： "+record,e);
            throw new RuntimeException("大车进场登记-添加进场货物明细信息失败");
        }
        return Result.build(d_0230addOutDto,EntranceResult_WEnum.SUCCESS);
    }

    //空车进场登记
    @Override
    @Transactional
    public Result addMinVeDate(Request<D_0230addInDto> d_0230addInDtoRequest) {
        D_0230addInDto record = d_0230addInDtoRequest.getBody();
        RequestHead head =d_0230addInDtoRequest.getHead();
        record.setInOperId(head.getOperator());
        if(StringUtil.isNotBlank(record.getInDoor())){
            D_0232FindDto d0232FindDto =new D_0232FindDto();
            d0232FindDto.setDeviceNo(record.getInDoor());
            if(StringUtil.isBlank(d0232FindDto.getDeviceNo())){
                return Result.build(null, 1,"设备编号不能为空");
            }
            TDeviceRelevance tDeviceRelevance=null;
            try {
                tDeviceRelevance = tDeviceRelevanceMapper.selectIOByInOutId(d0232FindDto);
            } catch (Exception e) {
                log.error("获取进出场名称-获取进出场名称失败");
            }
            if(tDeviceRelevance!=null){
                record.setInDoorNm(tDeviceRelevance.getInOut());
            }
        }
        if(record.getInDoor()==null || "".equals(record.getInDoor())){
            return Result.build(null, 1,"进场地点不能为空");
        }
        if(record.getVehicleNum()==null || "".equals(record.getVehicleNum())){
            return Result.build(null, 1,"车牌号不能为空");
        }
        record.setInTime(new Date());
        record.setStatus("1");
        if(record.getInOperId()==null || "".equals(record.getInOperId())){
            return Result.build(null, 1,"进场操作人不能为空");
        }
        TVehicleManager tVehicleManager =new TVehicleManager();
        tVehicleManager.setVehicleTpId(record.getVehicleTpId());
        tVehicleManager.setVehicleId(record.getVehicleId());
        try {
            tVehicleManager = tVehicleManagerMapper.selectByVeIdVeTpId(tVehicleManager);
        }catch (Exception e) {
            log.error("空车进场-查询车辆档案信息失败： ",e);
        }
        //获取序列值
        int xl = tVechicleProcurerMapper.findNextval("inId");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String inIdq = sdf.format(new Date());
        String inId= inIdq+(10000000+xl);
        record.setInId(inId);
        record.setTareWght(record.getTareWght());
        record.setInTtlWght(record.getInTtlWght());
        if(tVehicleManager==null||StringUtil.isBlank(tVehicleManager.getVehicleId())||StringUtil.isBlank(tVehicleManager.getVehicleTp())){
            record.setVehicleId(vehicleId);
            record.setVehicle(vehicle);
            record.setVehicleTp(vehicleTp);
            record.setVehicleTpId(vehicleTpId);
        }else{
            record.setVehicleId(tVehicleManager.getVehicleId());
            record.setVehicleTp(tVehicleManager.getVehicleTp());
        }
        record.setOutPayStatus("9");
        record.setInPayStatus("9");
        //根据车牌号 获取 车辆档案,若车辆档案为空或者与当前录入的车辆车型信息不一致则更新档案表
        TVechicleRecord tvr =new TVechicleRecord();
        if(StringUtil.isBlank(record.getProvId())){
            record.setProvId(tempProvId);
        }
        tvr.setProvId(record.getProvId());
        TCustomInfoManager tCustomInfoManager = null;
        try {
            tCustomInfoManager = tCustomInfoManagerMapper.selectByPrimaryKey(record.getProvId());
        } catch (Exception e) {
            log.error("空车入场登记-获取客户信息失败");
        }
        if(tCustomInfoManager!=null){
            record.setProvNm(tCustomInfoManager.getProvNm());
            record.setPhone(tCustomInfoManager.getPhone());
            tvr.setDriverNm(tCustomInfoManager.getProvNm());
            tvr.setDriverPhone(tCustomInfoManager.getPhone());
        }else {
            tvr.setDriverNm(record.getProvNm());
            tvr.setDriverPhone(record.getPhone());
        }
        tvr.setVehicleNum(record.getVehicleNum());
        tvr.setEmptyWght(record.getTareWght());
        tvr.setVehicle(record.getVehicle());
        tvr.setVehicleId(record.getVehicleId());
        tvr.setVehicleTp(record.getVehicleTp());
        tvr.setVehicleTpId(record.getVehicleTpId());
        maintainVehicleRecord(tvr,false,null);
        try{
            tVechicleProcurerMapper.insertMin(record);
        }catch (Exception e){
            log.error("空车进场登记-添加车辆进出管理表信息失败： "+record,e);
            throw new RuntimeException("空车进场登记-添加车辆进出管理表信息失败");
        }
        D_0230addOutDto d_0230addOutDto = new D_0230addOutDto();
        d_0230addOutDto.setInId(inId);

        return Result.build(d_0230addOutDto,EntranceResult_WEnum.SUCCESS);
    }
//收款后变更记录状态
    @Override
    public Result upVeSt(Request<D_0231upInDto>  d_0231upInDtoRequest) {
        D_0231upInDto record = d_0231upInDtoRequest.getBody();
        RequestHead head = d_0231upInDtoRequest.getHead();
        if(record.getInId()==null || "".equals(record.getInId())){
            return Result.build(null,1,"进场ID不能为空");
        }
        try {
            tVechicleProcurerMapper.upVeStByPrimaryKey(record);
        }catch (Exception e){
            log.error("收款后变更记录状态-修改车辆进出管理信息失败： "+record,e);
            throw new RuntimeException("收款后变更记录状态-修改车辆进出管理信息失败");
        }

        //大车出场消息推送
        JSONObject result = new JSONObject();
        if("3".equals(record.getStatus())||"1".equals(record.getStatus())){
            TVechicleProcurer tVechicleProcurer;
            List<EntranceVeCheckOutDatilsDto> prdList =null;
            try {
                tVechicleProcurer = tVechicleProcurerMapper.selectByPrimaryKey(record.getInId());
                prdList = tVechicleProcurerDetailsMapper.selectByInId(tVechicleProcurer.getInId());
            } catch (Exception e) {
                log.error("大车出场状态变更-获取进出场信息失败: ",e);
                return Result.ok();
            }
            //筛选供货车
            if(prdList==null&&prdList.size()==0){
                return Result.ok();
            }
            Request d0310Request = new Request();
            d0310Request.setHead(head);
            JSONObject body = new JSONObject();
            List<String> parameterList = new ArrayList<String>();
            body.put("busineNo",tVechicleProcurer.getInId());
            body.put("provId",tVechicleProcurer.getProvId());
            parameterList.add(tVechicleProcurer.getVehicleNum());
            //parameterType 1- 大车进场消息推送 2-大车出场消息推送
            if("1".equals(record.getStatus())){
                body.put("parameterType",1);
                parameterList.add(tVechicleProcurer.getCargoWght().toString());
                parameterList.add(tVechicleProcurer.getInActAmt().toString());
                //进场修改报备信息状态
                if(StringUtil.isNotBlank(tVechicleProcurer.getReportId())){
                    try {
                        tCargoInfoReportMapper.updatAuStByPrimaryKey(tVechicleProcurer.getReportId());
                    } catch (Exception e) {
                        log.error("大车出场状态变更-出场修改报备信息状态失败: ",e);
                    }
                }
            }else if("3".equals(record.getStatus())){
                //根据车牌号 获取 车辆档案,若车辆档案为空或者与当前录入的车辆车型信息不一致则更新档案表
                // 是否更新大车皮重标识
                Boolean isUpTareWght = false;
                if((record.getTareWght()!=null&&record.getOutTtlWght()!=null&&record.getTareWght().compareTo(BigDecimal.ZERO)!=0&&record.getOutTtlWght().compareTo(record.getTareWght())<0)){
                    record.setTareWght(record.getOutTtlWght());
                    isUpTareWght = true;
                }
                TVechicleRecord tvr = new TVechicleRecord();
                tvr.setVehicleNum(record.getVehicleNum());
                tvr.setEmptyWght(record.getTareWght());
                tvr.setVehicle(record.getVehicle());
                tvr.setVehicleId(record.getVehicleId());
                tvr.setVehicleTp(record.getVehicleTp());
                tvr.setVehicleTpId(record.getVehicleTpId());
                maintainVehicleRecord(tvr,isUpTareWght,record.getOutTtlWght());
                //推送服务
                body.put("parameterType",2);
                BigDecimal outActAmt = BigDecimal.ZERO;
                String amtType;
                BigDecimal parkActAmt = tVechicleProcurer.getParkActAmt();
                if(parkActAmt==null){
                    parkActAmt = BigDecimal.ZERO;
                }
                //有应退金额
                if(tVechicleProcurer.getRefundAmt()!=null&&tVechicleProcurer.getRefundAmt().compareTo(BigDecimal.ZERO)>0){
                    outActAmt = parkActAmt.subtract(tVechicleProcurer.getRefundAmt());
                    if(outActAmt.compareTo(BigDecimal.ZERO)<0){
                        amtType ="退款";
                        outActAmt = outActAmt.abs();
                    }else {
                        amtType ="缴费";
                    }
                    //有应补金额
                }else {
                    amtType ="缴费";
                    BigDecimal actRecevAmt = tVechicleProcurer.getActRecevAmt();
                    if(actRecevAmt==null){
                        actRecevAmt = BigDecimal.ZERO;
                    }
                    outActAmt = parkActAmt.add(actRecevAmt);
                }
                parameterList.add(amtType);
                parameterList.add(outActAmt.toString());
            }
            body.put("parameterList",parameterList);
            d0310Request.setBody(body);
            //调用推送服务接口
            try {
                result = disp20210310Feign.DISP20210310(d0310Request);
            } catch (Exception e) {
                log.error("大车出场状态变更-调用信息推送解口失败: ",e);
                return Result.ok();
            }
        }
        if(!("200".equals(result.getString("code")))){
            log.error("大车出场状态变更-信息推送失败: "+result.getString("message"));
        }
        return Result.ok();
    }

    //查询进场记录
    @Override
    public Result findInVe(Request<D_0234findInDto>  d_0234findInDtoRequest) {
        D_0234findInDto record = d_0234findInDtoRequest.getBody();
        TVechicleProcurer tvp;
        TVechicleRecord tVechicleRecord;
        if(record.getVehicleNum()==null || "".equals(record.getVehicleNum())){
            return Result.build(null, 1,"车牌号不能为空");
        }
        if(StringUtil.isBlank(record.getOutNum())){
            return Result.build(null, 1,"出场地点编号不能为空");
        }
        if(record.getOutTtlWght()==null){
            return Result.build(null, 1,"出场总重不能为空");
        }
        try{
            tvp = tVechicleProcurerMapper.findInVe(record);
        }catch (Exception e){
            log.error("查询进场记录-查询车辆进出管理信息失败： "+record,e);
            throw new RuntimeException("查询进场记录-查询车辆进出管理信息失败");
        }
        if(tvp==null||"3".equals(tvp.getStatus())){
            return Result.build(null, 1,"没有查询到相关进场数据");
        }
        try {
             tVechicleRecord =tVechicleRecordMapper.selectByPrimaryKey(record.getVehicleNum());
        } catch (Exception e) {
            log.error("查询进场记录-查询车辆记录信息失败： "+record,e);
            throw new RuntimeException("查询进场记录-查询车辆记录信息失败");
        }
        //停车费计算
        if(StringUtil.isNotBlank(record.getVehicleId())&&StringUtil.isNotBlank(record.getVehicleTp())){
            tvp.setVehicleTp(record.getVehicleTp());
            tvp.setVehicleId(record.getVehicleId());
        }
        JSONObject parkJson = getParkAmt(tvp,record.getOutNum());
        tvp.setParkAmt(parkJson.getBigDecimal("parkAmt"));
        tvp.setParkActAmt(parkJson.getBigDecimal("parkActAmt"));
        tvp.setParkTime(parkJson.getIntValue("parkTime"));
        List<EntranceVeCheckOutDatilsDto> prdList =null;
        try {
            prdList = tVechicleProcurerDetailsMapper.selectByInId(tvp.getInId());
        } catch (Exception e) {
            log.error("查询进场记录-查询进出场明细信息失败： "+e);
            throw new RuntimeException("查询进场记录-查询进出场明细信息失败");
        }
        //计算应退补金额

        // 0-采购商 1-供应商
        String isSuppPurch = "0";
        if(prdList!=null&&prdList.size()>0){
            isSuppPurch="1";
        }
        tvp.setIsPeel("0");
        BigDecimal maxRate = null;
        if(record.getOutTtlWght()!=null&&tvp.getTareWght()!=null&&tvp.getTareWght().compareTo(BigDecimal.ZERO)>0&&record.getOutTtlWght().compareTo(tvp.getTareWght())!=0){
            if("1".equals(isSuppPurch)){
                BigDecimal supptAmt ;
                BigDecimal refundAmt;
                //查询最大rate
                EntranceVeCheckOutDatilsDto entranceVeCheckOutDatilsDto = prdList.stream().max(Comparator.comparing(EntranceVeCheckOutDatilsDto::getRate)).get();
                maxRate = entranceVeCheckOutDatilsDto.getRate();
                BigDecimal wghtDiff;
                if(record.getOutTtlWght().compareTo(tvp.getTareWght())>0){
                    wghtDiff = record.getOutTtlWght().subtract(tvp.getTareWght());
                    refundAmt = maxRate.multiply(wghtDiff);
                    refundAmt = refundAmt.setScale(0, BigDecimal.ROUND_HALF_UP);
                    tvp.setPeelReturnAmt(refundAmt);
                    tvp.setPeelSupptAmt(BigDecimal.ZERO);
                    tvp.setIsPeel("1");
                }else if(record.getOutTtlWght().compareTo(tvp.getTareWght())<0){
                    wghtDiff = tvp.getTareWght().subtract(record.getOutTtlWght());
                    supptAmt = maxRate.multiply(wghtDiff);
                    supptAmt = supptAmt.setScale(0, BigDecimal.ROUND_HALF_UP);
                    tvp.setPeelSupptAmt(supptAmt);
                    tvp.setPeelReturnAmt(BigDecimal.ZERO);
                    tvp.setIsPeel("1");
                }
            }
        }
        if("0".equals(tvp.getIsPeel())){
            tvp.setPeelReturnAmt(BigDecimal.ZERO);
            tvp.setPeelSupptAmt(BigDecimal.ZERO);
        }
        //车辆黑名单
        TVehicleBlacklist tVehicleBlacklist = null;
        try {
            tVehicleBlacklist = tVehicleBlacklistMapper.selectByPk(record.getVehicleNum());
        } catch (Exception e) {
            log.error("大车出场-查询进场信息-车辆黑名单询失败： "+record,e);
        }

//        查询该进出场记录关联的流水信息
        TPayJrn tPayJrn = new TPayJrn();
        try {
            tPayJrn =tPayJrnMapper.selectByInId(tvp.getInId());
        } catch (Exception e) {
            log.error("大车出场-查询进出场记录关联流水信息失败： ",e);
        }
        JSONObject tPayJrnJson = new JSONObject();
        if(tPayJrn!=null){
            tPayJrnJson.put("posOrderId",tPayJrn.getPosOrderId());
            tPayJrnJson.put("txnTm",tPayJrn.getTxnTm());
        }else {
            tPayJrnJson = null;
        }
        JSONObject result = new JSONObject();
        result.put("maxRate",maxRate);
        result.put("isSuppPurch",isSuppPurch);
        result.put("tVechicleProcurer",tvp);
        result.put("tVechicleRecord",tVechicleRecord);
        result.put("parkTimeStr",parkJson.getString("parkTimeStr"));
        result.put("vehicleBlacklist",tVehicleBlacklist);
        result.put("tPayJrn",tPayJrnJson);
        return Result.build(result,EntranceResult_WEnum.SUCCESS);
    }
    //变更进场记录
   @Override
   @Transactional
   public Result upInVe(Request<D_0235UpInDto> d_0235UpInDtoRequest){
       D_0235UpInDto record = d_0235UpInDtoRequest.getBody();
       RequestHead head =d_0235UpInDtoRequest.getHead();
       record.setOutOperId(head.getOperator());
       record.setOutOperNm(head.getOperName());
       TVechicleProcurer tVechicleProcurer = tVechicleProcurerMapper.selectByPrimaryKey(record.getInId());
       if(tVechicleProcurer==null){
           return Result.build(1,"变更进场记录-没有该车辆的进出场信息");
       }
       if(StringUtil.isNotBlank(record.getOutDoor())){
           D_0232FindDto d0232FindDto =new D_0232FindDto();
           d0232FindDto.setDeviceNo(record.getOutDoor());
           TDeviceRelevance tDeviceRelevance=null;
           try {
               tDeviceRelevance = tDeviceRelevanceMapper.selectIOByInOutId(d0232FindDto);
           } catch (Exception e) {
               log.error("获取进出场名称-获取进出场名称失败");
           }
           if(tDeviceRelevance!=null){
               record.setOutDoorNm(tDeviceRelevance.getInOut());
           }
       }
       //若为供货车出场，更新未核验的进出场明细重量（分摊）
       List<EntranceVeCheckOutDatilsDto> prdList;
       try {
           prdList = tVechicleProcurerDetailsMapper.selectByInId(record.getInId());
       } catch (Exception e) {
           log.error("查询进场记录-查询进出场明细信息失败： "+e);
           throw new RuntimeException("查询进场记录-查询进出场明细信息失败");
       }
       if(prdList!=null&&prdList.size()>0&&record.getOutTtlWght()!=null&&record.getOutTtlWght().compareTo(BigDecimal.ZERO)>0
               &&record.getTareWght()!=null&&record.getTareWght().compareTo(BigDecimal.ZERO)>0&&
               record.getOutTtlWght().compareTo(record.getTareWght())>0
               &&record.getIsCheck()!=null&&"0".equals(record.getIsCheck())){
           BigDecimal dWght = record.getOutTtlWght().subtract(record.getTareWght());
           BigDecimal addWght = dWght.divide(BigDecimal.valueOf(prdList.size()),2, BigDecimal.ROUND_HALF_UP);
           for(EntranceVeCheckOutDatilsDto details : prdList){
               TVechicleProcurerDetails procurerDetails = new TVechicleProcurerDetails();
               procurerDetails.setInId(details.getInId());
               procurerDetails.setVarietyId(details.getVarietyId());
               procurerDetails.setUnit(details.getUnit());
               procurerDetails.setNum(details.getNum().subtract(addWght));
               tVechicleProcurerDetailsMapper.updateByPrimaryKeySelective(procurerDetails);
           }
       }
       if("0".equals(record.getIsParkFree())){
           if(!"1".equals(tVechicleProcurer.getStatus())&&"1".equals(tVechicleProcurer.getIsParkFree())){
               return Result.build(1,"不是已进场状态");
           }
       }
        try {
            tVechicleProcurerMapper.updateByPrimaryKey(record);
        }catch (Exception e){
            log.error("变更进场记录-查询车辆进出管理信息失败： "+record,e);
            throw new RuntimeException("变更进场记录-查询车辆进出管理信息失败");
        }
//        进出场客户信息修改记录
       if(record.getActRecevAmt()==null&&record.getOutTime()==null&&record.getOutTtlWght()==null&&StringUtil.isNotBlank(record.getProvId())){
           TVehicleCustomInfoInDto customInfoInDto = new TVehicleCustomInfoInDto(null,record.getInId(),tVechicleProcurer.getProvId(),tVechicleProcurer.getProvNm(),tVechicleProcurer.getCardNo(),record.getProvId(),record.getProvNm(),record.getCardNo(),null,head.getOperator(),null,null,null,null);
           try {
               tVehicleCustomInfoMapper.insert(customInfoInDto);
           } catch (Exception e) {
               log.error("变更进场记录-进出场客户信息修改记录失败： "+record,e);
           }
       }
       return Result.ok();
    }
    /**
     * 出场缴费-获取订单信息和支付二维码
     * @param d_0236DtoRequest
     * @return
     */
    @Override
    public Result outPay(Request<D_0236Dto> d_0236DtoRequest) {
        D_0236Dto record = d_0236DtoRequest.getBody();
        if(StringUtil.isBlank(record.getPayerNo())){
            return Result.build(1,"客户编号为空");
        }
        RequestHead head = d_0236DtoRequest.getHead();
        TVechicleProcurer tvp ;
        try {
            tvp = tVechicleProcurerMapper.selectByOutNum(record.getOutId());
        } catch (Exception e) {
            log.error("出场缴费-查询车辆进出信息失败： "+record,e);
            throw new RuntimeException("出场缴费-查询车辆进出信息失败");
        }
        if(tvp==null){
            return Result.build(null, 1,"没有查询到该车辆的进出场记录");
        }
        //根据车牌号 获取 车辆档案,若车辆档案为空或者与当前录入的车辆车型信息不一致则更新档案表
        TVechicleRecord tvr =new TVechicleRecord();
        tvr.setVehicleNum(tvp.getVehicleNum());
        tvr.setProvId(record.getPayerNo());
        TCustomInfoManager tCustomInfoManager = null;
        try {
            tCustomInfoManager = tCustomInfoManagerMapper.selectByPrimaryKey(record.getPayerNo());
        } catch (Exception e) {
            log.error("出场扫码-获取客户信息失败");
        }
        if(tCustomInfoManager!=null){
            tvr.setDriverNm(tCustomInfoManager.getProvNm());
            tvr.setDriverPhone(tCustomInfoManager.getPhone());
        }
        maintainVehicleRecord(tvr,false,null);
        Request d0261Request = new Request();
        d0261Request.setHead(head);
        JSONObject body = new JSONObject();
//        支付模式 03-app 05-微信 07-支付宝
        if("05".equals(record.getPaymentMode())){
            body.put("paymentMode","05");
            body.put("appId",record.getAppId());
            body.put("userIdent",record.getUserIdent());
        }else if("07".equals(record.getPaymentMode())) {
            body.put("paymentMode","07");
        }else if("03".equals(record.getPaymentMode())){
            body.put("paymentMode","03");
        }else {
            return Result.build(null, 1,"非法支付模式");
        }
        body.put("txnType","3");
        body.put("transMd","1");
        body.put("businessNo",tvp.getInId());
        body.put("payerNo",record.getPayerNo());
        body.put("txnAmt",tvp.getParkActAmt());
        JSONArray orderList = new JSONArray();
        JSONObject order = new JSONObject();
        order.put("purchId",tvp.getProvId());
        order.put("prdctId","3");
        orderList.add(order);
        body.put("orderList",orderList);
        d0261Request.setBody(body);
        JSONObject result=null;
        try {
            result = disp20210261Feign.DISP20210261(d0261Request);
        } catch (Exception e) {
            throw new RuntimeException("出场缴费-调用缴费接口失败");
        }
        if(result==null){
            return Result.build(null, 1,"出场缴费-调用缴费接口失败");
        }else if(!("200".equals(result.getString("code")))){
            return Result.build(null, 1,result.getString("message"));
        }
        JSONObject resultDate = result.getJSONObject("data");
        JSONObject orderInfo = new JSONObject();
        orderInfo.put("orderId",resultDate.get("orderId"));
        orderInfo.put("endTime",resultDate.get("endTime"));
        JSONObject vechicleInfo = new JSONObject();
        vechicleInfo.put("vehicleNum",tvp.getVehicleNum());
        vechicleInfo.put("parkTime",getFormatTime(tvp.getInTime(),tvp.getOutTime()));
        vechicleInfo.put("parkActAmt",tvp.getParkActAmt());
        String inExportNm = "";
        if(StringUtil.isNotBlank(tvp.getOutDoor())){
            D_0232FindDto d0232FindDto =new D_0232FindDto();
            d0232FindDto.setDeviceNo(tvp.getOutDoor());
            TDeviceRelevance tDeviceRelevance=null;
            try {
                tDeviceRelevance = tDeviceRelevanceMapper.selectIOByInOutId(d0232FindDto);
            } catch (Exception e) {
                log.error("获取进出场名称-获取进出场名称失败");
            }
            if(tDeviceRelevance!=null){
                inExportNm=tDeviceRelevance.getInOut();
            }
        }
        vechicleInfo.put("exportNum",inExportNm);
        String payUrl="";
        if("05".equals(record.getPaymentMode())){
           payUrl = resultDate.getString("param");
        }else if("07".equals(record.getPaymentMode())) {
            payUrl = resultDate.getString("qrCode");
        }else if("03".equals(record.getPaymentMode())){
            payUrl = resultDate.getString("deskUrl");
        }
        D_0236OutDto d0236OutDto = new D_0236OutDto(orderInfo,vechicleInfo,payUrl);
        return Result.build(d0236OutDto,EntranceResult_WEnum.SUCCESS);
    }

    /**
     * 根据设备编号查询进出场口信息
     * @param d_0232FindDtoRequest
     * @return
     */
    @Override
    public Result findVeInOut(Request<D_0232FindDto>  d_0232FindDtoRequest) {
        D_0232FindDto record = d_0232FindDtoRequest.getBody();
        if(StringUtil.isBlank(record.getDeviceNo())){
            return Result.build(null, 1,"设备编号不能为空");
        }
        TDeviceRelevance tDeviceRelevance;
        try {
             tDeviceRelevance = tDeviceRelevanceMapper.selectIOByInOutId(record);
        } catch (Exception e) {
            throw new RuntimeException("获取进出场名称-获取进出场名称失败");
        }
        return Result.build(tDeviceRelevance,EntranceResult_WEnum.SUCCESS);

    }
    /**
     * 无人工进出场
     * @param d_0233DtoRequest
     * @return
     */
    @Override
    public Result upInOut(Request<D_0233Dto> d_0233DtoRequest) {
        D_0233Dto dto = d_0233DtoRequest.getBody();
        String operateTp = dto.getOperateTp();
        if(StringUtil.isBlank(operateTp)){
            return Result.build(null, 1,"操作类型不能为空");
        }
        String inExportNm = "";
        if(StringUtil.isNotBlank(dto.getInExportNum())){
            D_0232FindDto d0232FindDto =new D_0232FindDto();
            d0232FindDto.setDeviceNo(dto.getInExportNum());
            TDeviceRelevance tDeviceRelevance=null;
            try {
                tDeviceRelevance = tDeviceRelevanceMapper.selectIOByInOutId(d0232FindDto);
            } catch (Exception e) {
                log.error("获取进出场名称-获取进出场名称失败");
            }
            if(tDeviceRelevance!=null){
                inExportNm=tDeviceRelevance.getInOut();
            }
        }
//        操作类型 0-进场 1-出场
        if("0".equals(operateTp)){

            TVechicleRecord tVechicleRecord = null;
            TCustomInfoManager tCustomInfoManager = null;
            TCustomInfoManager defaultTCustomInfoManager = null;
            try {
                defaultTCustomInfoManager= tCustomInfoManagerMapper.selectByPrimaryKey(tempProvId);
            } catch (Exception e) {
                log.error("无人工进出场-查询临时客户信息信息失败： ",e);
            }
            try {
                tVechicleRecord = tVechicleRecordMapper.selectByPrimaryKey(dto.getVehicleNum());
                if(tVechicleRecord!=null&&StringUtil.isNotBlank(tVechicleRecord.getProvId())){
                    tCustomInfoManager = tCustomInfoManagerMapper.selectByPrimaryKey(tVechicleRecord.getProvId());
                }
            }catch (Exception e) {
                log.error("无人工进出场-查询车辆档案信息失败： ",e);
            }
            TVechicleProcurer tvp = new TVechicleProcurer();
            tvp.setVehicleNum(dto.getVehicleNum());
            tvp.setInTtlWght(dto.getInOutWght());
            tvp.setTareWght(dto.getInOutWght());
            tvp.setInDoor(dto.getInExportNum());
            tvp.setVehicleUrl(dto.getVehicleUrl());
            tvp.setInTime(new Date());
            tvp.setProvId(tempProvId);
            tvp.setOutPayStatus("9");
            tvp.setInPayStatus("9");
            tvp.setInDoorNm(inExportNm);
            //获取序列号
            int xl = tVechicleProcurerMapper.findNextval("inId");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String inIdq = sdf.format(new Date());
            String inId= inIdq+(10000000+xl);
            tvp.setInId(inId);
            tvp.setFixedAmt(BigDecimal.ZERO);
            tvp.setInTtlAmt(BigDecimal.ZERO);
            tvp.setInActAmt(BigDecimal.ZERO);
            tvp.setPeelReturnAmt(BigDecimal.ZERO);
            tvp.setPeelSupptAmt(BigDecimal.ZERO);
            tvp.setCheckReturnAmt(BigDecimal.ZERO);
            tvp.setCheckSupptAmt(BigDecimal.ZERO);
            tvp.setRefundAmt(BigDecimal.ZERO);
            tvp.setSupptAmt(BigDecimal.ZERO);
            tvp.setOutTtlAmt(BigDecimal.ZERO);
            tvp.setPreferPrice(BigDecimal.ZERO);
            tvp.setStatus("1");
            tvp.setVehicleId(vehicleId);
            tvp.setVehicleTp(vehicleTp);
            if(defaultTCustomInfoManager!=null){
                tvp.setProvNm(defaultTCustomInfoManager.getProvNm());
                tvp.setPhone(defaultTCustomInfoManager.getPhone());
            }
            if(tVechicleRecord!=null){
                if(dto.getInOutWght()==null||dto.getInOutWght().compareTo(BigDecimal.ZERO)<=0){
                    tvp.setTareWght(tVechicleRecord.getEmptyWght());
                }
                if(StringUtil.isNotBlank(tVechicleRecord.getVehicleId())&&StringUtil.isNotBlank(tVechicleRecord.getVehicleTp())){
                    tvp.setVehicleId(tVechicleRecord.getVehicleId());
                    tvp.setVehicleTp(tVechicleRecord.getVehicleTp());
                }
                if(tCustomInfoManager!=null&&StringUtil.isNotBlank(tCustomInfoManager.getProvNm())&&StringUtil.isNotBlank(tCustomInfoManager.getPhone())){
                    tvp.setProvId(tVechicleRecord.getProvId());
                    tvp.setProvNm(tCustomInfoManager.getProvNm());
                    tvp.setPhone(tCustomInfoManager.getPhone());
                }
            }else if(dto.getInOutWght()!=null){
                //根据车牌号 获取 车辆档案,若车辆档案为空或者与当前录入的车辆车型信息不一致则更新档案表
                TVechicleRecord tvr =new TVechicleRecord();
                tvr.setVehicleNum(dto.getVehicleNum());
                tvr.setEmptyWght(dto.getInOutWght());
                tvr.setVehicle(vehicle);
                tvr.setVehicleId(vehicleId);
                tvr.setVehicleTp(vehicleTp);
                tvr.setVehicleTpId(vehicleTpId);
                maintainVehicleRecord(tvr,false,null);
            }
            try {
                tVechicleProcurerMapper.insertByNoBody(tvp);
                JSONObject result = new JSONObject();
                result.put("inId",tvp.getInId());
                result.put("operateTp","0");
                result.put("vehicleNum",tvp.getVehicleNum());
                result.put("inTtlWght",tvp.getInTtlWght());
                result.put("outTtlWght",null);
                result.put("vehicleUrl",tvp.getVehicleUrl());
                result.put("outDoor",null);
                result.put("inDoor",tvp.getInDoor());
                result.put("inTime",tvp.getInTime());
                result.put("outTime",null);
                result.put("parkTime",null);
                result.put("parkActAmt",null);
                return Result.ok(result);
            } catch (Exception e) {
                log.error("无人工进出场-新增进场消息失败： ",e);
                throw new RuntimeException("无人工进出场-新增进场消息失败");
            }
        }else if("1".equals(operateTp)){
            TVechicleProcurer tvp;
            try {
                tvp = tVechicleProcurerMapper.findInVeByVehicleNum(dto.getVehicleNum());
                if(tvp==null){
                    return Result.build(1,"无人工进出场-没有该车辆的进出场信息");
                }
                JSONObject parkJson = getParkAmt(tvp,dto.getInExportNum());
                tvp.setParkAmt(parkJson.getBigDecimal("parkAmt"));
                tvp.setParkActAmt(parkJson.getBigDecimal("parkActAmt"));
                tvp.setParkTime(parkJson.getIntValue("parkTime"));
                tvp.setOutTime(parkJson.getDate("outTime"));
                tvp.setOutDoor(dto.getInExportNum());
                tvp.setOutDoorNm(inExportNm);
                tvp.setOutTtlWght(dto.getInOutWght());
                tvp.setChrgTp(parkJson.getString("chrgTp"));
                tVechicleProcurerMapper.updateByPrimaryKeySelective(tvp);
                boolean isUpTareWght = false;
                if(dto.getInOutWght()!=null&&tvp.getTareWght()!=null&&dto.getInOutWght().compareTo(tvp.getTareWght())<0){
                    isUpTareWght = true;
                }
                JSONObject result = new JSONObject();
                result.put("inId",tvp.getInId());
                result.put("vehicleNum",tvp.getVehicleNum());
                result.put("parkTime",getFormatTime(tvp.getInTime(),tvp.getOutTime()));
                result.put("parkActAmt",tvp.getParkActAmt());
                result.put("inTtlWght",tvp.getInTtlWght());
                result.put("outTtlWght",tvp.getOutTtlWght());
                result.put("inTime",tvp.getInTime());
                result.put("outTime",tvp.getOutTime());
                result.put("operateTp","1");
                result.put("outDoor",tvp.getOutDoor());
                result.put("inDoor",tvp.getInDoor());
                result.put("vehicleUrl",dto.getVehicleUrl());
                return Result.ok(result);
            } catch (Exception e) {
                log.error("无人工进出场-新增进场消息失败： ",e);
                throw new RuntimeException("无人工进出场-新增进场消息失败");
            }
        }else {
            return Result.build(null, 1,"非法操作类型");
        }
    }

    /**
     * 查询出场支付结果
     * @param d_0237FindDtoRequest
     * @return
     */
    @Override
    public Result finOutPayInfo(Request<D_0237FindDto> d_0237FindDtoRequest) {
        D_0237FindDto findDto = d_0237FindDtoRequest.getBody();
        TVechicleProcurer tvp ;
        try {
            tvp = tVechicleProcurerMapper.selectByOutNum(findDto.getOutId());
        } catch (Exception e) {
            throw new RuntimeException("查询出场支付结果失败");
        }
        if(tvp==null){
            return Result.build(1,"没有该出口的进出场记录");
        }
        JSONObject result = new JSONObject();
        result.put("inId",tvp.getInId());
        result.put("vehicleNum",tvp.getVehicleNum());
        result.put("provId",tvp.getProvId());
        result.put("outPayStatus",tvp.getOutPayStatus());
        return Result.ok(result);
    }
    /**
     * 大车出场-货物去向查询
     * @param d_0346FindDtoRequest
     * @return
     */
    @Override
    public Result findWhither(Request<D_0346FindDto> d_0346FindDtoRequest) {
        D_0346FindDto dto = d_0346FindDtoRequest.getBody();
        List<TCarioGoWhere> list;
        try {
            list = tCarioGoWhereMapper.getList(dto);
        } catch (Exception e) {
            log.error("货物去向查询失败",e);
            throw new RuntimeException("货物去向查询失败");
        }
        return Result.ok(list);
    }
    /**
     * 进出场管理-收费员收费统计
     * @param d_0365FindDtoRequest
     * @return
     */
    @Override
    public Result chargeStatistics(Request<D_0365FindDto> d_0365FindDtoRequest) {
        D_0365FindDto body = d_0365FindDtoRequest.getBody();
        // 有分页参数才做分页查询
        if (body.getCurPage() != null && body.getCurPage() > 0 && body.getPageSize() > 0) {
            Integer curPage = (body.getCurPage() - 1) * body.getPageSize();
            body.setCurPage(curPage);
        } else {
            body.setPageSize(0);
        }
        List<D_0365OutVo> list ;
        Integer tolPageNum;
        try {
            list = tPayJrnMapper.selectByDto(body);
            tolPageNum = tPayJrnMapper.selectCountByDto(body);
        } catch (Exception e) {
            log.error("进出场管理-收费员收费统计查询失败",e);
            throw new RuntimeException("进出场管理-收费员收费统计查询失败");
        }
        JSONObject result = new JSONObject();
        result.put("list",list);
        result.put("tolPageNum",tolPageNum);
        return Result.ok(result);
    }

    /**
     * 进出场管理-收费员收费统计导出
     * @param d_0365FindDtoRequest
     * @return
     */
    @Override
    public Result getStatisticsExcel(Request<D_0365FindDto> d_0365FindDtoRequest) {
        D_0365FindDto body = d_0365FindDtoRequest.getBody();

        // 有分页参数才做分页查询
        if (body.getCurPage() != null && body.getCurPage() > 0 && body.getPageSize() > 0) {
            Integer curPage = (body.getCurPage() - 1) * body.getPageSize();
            body.setCurPage(curPage);
        } else {
            body.setCurPage(0);
            body.setPageSize(Integer.valueOf(filePageSize));
        }
        List<D_0365OutVo> list ;
        try {
            list = tPayJrnMapper.selectByDto(body);
        } catch (Exception e) {
            log.error("进出场管理-收费员收费统计查询失败",e);
            throw new RuntimeException("进出场管理-收费员收费统计查询失败");
        }
        if (list!=null && list.size()>0){
            try {
                String excelFileName = ("0".equals(body.getOperateTp()) ? "进场" : "出场") + "收费员收费统计";
                // 生成电子表格
                HSSFWorkbook workBook = new HSSFWorkbook();
                HSSFSheet hssfsheet = workBook.createSheet(excelFileName);
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

                // 单元格样式边框
                HSSFCellStyle tableStyle = workBook.createCellStyle();
                this.setBorder(tableStyle);

                // 列名样式边框
                HSSFCellStyle cellNameStyle = workBook.createCellStyle();
                this.setBorder(cellNameStyle);
                cellNameStyle.setFont(cellNameFont);

                // 标题样式边框
                this.setBorder(titlStyle);

                // 日期格式边框
                this.setBorder(dateStyle1);
                this.setBorder(dateStyle2);

                //列名
                String[] cellNames = new String[]{"收费员ID","收费员", "流水号","业务号","交易类型", "交易方式", "进出场状态", "交易状态", "交易金额","交易时间"};

                // 标题
                HSSFRow nextRow = hssfsheet.createRow(1);
                HSSFCell hssfcell = null;
                hssfcell = row.createCell(0);
                hssfcell.setCellValue(excelFileName);
                hssfcell.setCellStyle(titlStyle);

                //其它每个单元格设置边框，再进行合并才会有边框
                for (int i = 1; i < cellNames.length; i++) {
                    hssfcell = row.createCell(i);
                    hssfcell.setCellStyle(titlStyle);
                }

                // 合并标题单元格
                CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, cellNames.length - 1);
                hssfsheet.addMergedRegion(region1);

                //设置列名
                for (int i = 0; i < cellNames.length; i++) {
                    hssfcell = nextRow.createCell(i);
                    hssfcell.setCellValue(cellNames[i]);
                    hssfcell.setCellStyle(cellNameStyle);
                }
                int i=2;
                // 列值
                for(D_0365OutVo item: list){
                    nextRow = hssfsheet.createRow(i++);
                    int k = 0;
                    // 收费员ID
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getOperId());
                    hssfcell.setCellStyle(tableStyle);
                    // 收费员
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getOperator());
                    hssfcell.setCellStyle(tableStyle);
                    // 流水号
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getJrnlNum());
                    hssfcell.setCellStyle(tableStyle);
                    // 业务号
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getBusinessNo());
                    hssfcell.setCellStyle(tableStyle);
                    // 交易类型
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(TxnTypeEnum.getDesc(item.getTxnType()));
                    hssfcell.setCellStyle(tableStyle);
                    // 交易方式
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(TransMdEnum.getDesc(item.getTransMd()));
                    hssfcell.setCellStyle(tableStyle);
                    // 进出场状态
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(EntranceStaEnum.getDesc(item.getEntStatus()));
                    hssfcell.setCellStyle(tableStyle);
                    // 交易状态
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(PayStatusEnum.getDesc(item.getStatus()));
                    hssfcell.setCellStyle(tableStyle);
                    // 交易金额
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getTxnAmt().toString());
                    hssfcell.setCellStyle(tableStyle);
                    // 交易时间
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getTxnTm());
                    hssfcell.setCellStyle(dateStyle2);
                }
                //宽度自适应大小
                for (int z = 0; z < cellNames.length; z++) {
                    hssfsheet.autoSizeColumn(z);
                }
                FileOutputStream out = null;
                String fileName = System.currentTimeMillis() + ".xls";
                String uploadMkdir = "entrancexls";
                File file = new File(fileLocalPath + "/" + uploadMkdir + "/" + fileName);
                try {
                    out = new FileOutputStream(file);
                    workBook.write(out);
                } catch (Exception e) {
                    log.error("进出场管理-收费员收费统计导出失败", e);
                    throw new RuntimeException("进出场管理-收费员收费统计导出失败");
                } finally {
                    try {
                        if (out != null){
                            out.close();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    try {
                        if (workBook != null){
                            workBook.close();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                // 组请求报文
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
                multipartBodyBuilder.part("uploadMkdir", uploadMkdir);
                multipartBodyBuilder.part("file", new FileSystemResource(file));
                MultiValueMap<String, HttpEntity<?>> multiValueBody = multipartBodyBuilder.build();

                // 上传文件服务
                JSONObject result=null;
                try {
                    result = disp20210105Feign.DISP20210105(multiValueBody);
                } catch (Exception e) {
                    throw new RuntimeException("进出场管理-收费员收费统计导出失败");
                }
                if(result==null||!("200".equals(result.getString("code")))){
                    return Result.build(null, USER_UPLOAD_FILE_FAIL.getCode(), USER_UPLOAD_FILE_FAIL.getMessage());
                }

                log.info("DISP20210370 进出场管理-收费员收费统计导出成功");
                JSONObject jsonObject = new JSONObject();
                String fileUrl = result.getString("data");
                jsonObject.put("fileUrl",fileUrl);
                return Result.build(jsonObject, SUCCESS);

            } catch (Exception e) {
                log.error("DISP20210370 进出场管理-收费员收费统计导出异常", e);
                return Result.fail();
            }
        }
        return Result.fail();
    }

    /**
     * 进出场管理-进出场停车费查询
     * @param d_0503FindDtoRequest
     * @return
     */
    @Override
    public Result getParkingInfo(Request<D_0503FindDto> d_0503FindDtoRequest) {
        D_0503FindDto body = d_0503FindDtoRequest.getBody();
        RequestHead head = d_0503FindDtoRequest.getHead();
        // 有分页参数才做分页查询
        if (body.getCurPage() != null && body.getCurPage() > 0 && body.getPageSize() > 0) {
            Integer curPage = (body.getCurPage() - 1) * body.getPageSize();
            body.setCurPage(curPage);
        } else {
            body.setPageSize(0);
        }
        List<D_0503OutVO> list ;
        Integer tolPageNum;
        try {
            list = tVechicleProcurerMapper.getParkingInfo(body);
            tolPageNum = tVechicleProcurerMapper.getParkingInfoCount(body);
        } catch (Exception e) {
            log.error("进出场管理-进出场停车费查询失败",e);
            throw new RuntimeException("进出场管理-进出场停车费查询失败");
        }
        JSONObject result = new JSONObject();
        result.put("list",list);
        result.put("tolPageNum",tolPageNum);
        return Result.ok(result);
    }
    /**
     * 进出场管理-进出场停车费查询导出
     * @param d_0503FindDtoRequest
     * @return
     */
    @Override
    public Result getParkingExcel(Request<D_0503FindDto> d_0503FindDtoRequest) {
        D_0503FindDto body = d_0503FindDtoRequest.getBody();
        RequestHead head = d_0503FindDtoRequest.getHead();
        // 有分页参数才做分页查询
        if (body.getCurPage() != null && body.getCurPage() > 0 && body.getPageSize() > 0) {
            Integer curPage = (body.getCurPage() - 1) * body.getPageSize();
            body.setCurPage(curPage);
        } else {
            body.setCurPage(0);
            body.setPageSize(Integer.valueOf(filePageSize));
        }
        List<D_0503OutVO> list ;
        try {
            list =tVechicleProcurerMapper.getParkingInfo(body);
        } catch (Exception e) {
            log.error("进出场管理-进出场停车费查询失败",e);
            throw new RuntimeException("进出场管理-进出场停车费查询失败");
        }
        if (list!=null && list.size()>0){
            try {
                String excelFileName = "进出场停车费查询统计";
                // 生成电子表格
                HSSFWorkbook workBook = new HSSFWorkbook();
                HSSFSheet hssfsheet = workBook.createSheet(excelFileName);
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

                // 单元格样式边框
                HSSFCellStyle tableStyle = workBook.createCellStyle();
                this.setBorder(tableStyle);

                // 列名样式边框
                HSSFCellStyle cellNameStyle = workBook.createCellStyle();
                this.setBorder(cellNameStyle);
                cellNameStyle.setFont(cellNameFont);

                // 标题样式边框
                this.setBorder(titlStyle);

                // 日期格式边框
                this.setBorder(dateStyle1);
                this.setBorder(dateStyle2);

                //列名
                String[] cellNames = new String[]{"进出场编号","车牌号", "进场时间",
                        "进场口","出场时间", "出场口", "停车费","优惠金额",
                        "进出场状态", "进场支付状态","出场支付状态", "进场支付类型",
                        "出场支付类型","进场操作人","出场操作人","进出场类型"};

                // 标题
                HSSFRow nextRow = hssfsheet.createRow(1);
                HSSFCell hssfcell = null;
                hssfcell = row.createCell(0);
                hssfcell.setCellValue(excelFileName);
                hssfcell.setCellStyle(titlStyle);

                //其它每个单元格设置边框，再进行合并才会有边框
                for (int i = 1; i < cellNames.length; i++) {
                    hssfcell = row.createCell(i);
                    hssfcell.setCellStyle(titlStyle);
                }

                // 合并标题单元格
                CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, cellNames.length - 1);
                hssfsheet.addMergedRegion(region1);

                //设置列名
                for (int i = 0; i < cellNames.length; i++) {
                    hssfcell = nextRow.createCell(i);
                    hssfcell.setCellValue(cellNames[i]);
                    hssfcell.setCellStyle(cellNameStyle);
                }
                int i=2;
                // 列值
                for(D_0503OutVO item: list){
                    nextRow = hssfsheet.createRow(i++);
                    int k = 0;
                    // 进出场编号
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getInId());
                    hssfcell.setCellStyle(tableStyle);
                    // 车牌号
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getVehicleNum());
                    hssfcell.setCellStyle(tableStyle);
                    // 进场时间
                    hssfcell = nextRow.createCell(k++);
                    if(item.getInTime() == null){
                        hssfcell.setCellValue("");
                        hssfcell.setCellStyle(tableStyle);
                    }else{
                        hssfcell.setCellValue(item.getInTime());
                        hssfcell.setCellStyle(dateStyle2);
                    }
                    // 进场口
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getInDoorNm() == null ? "" :item.getInDoorNm());
                    hssfcell.setCellStyle(tableStyle);
                    // 出场时间
                    hssfcell = nextRow.createCell(k++);
                    if(item.getOutTime() == null){
                        hssfcell.setCellValue("");
                        hssfcell.setCellStyle(tableStyle);
                    }else{
                        hssfcell.setCellValue(item.getOutTime());
                        hssfcell.setCellStyle(dateStyle2);
                    }
                    // 出场口
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getOutDoorNm() == null ? "" : item.getOutDoorNm() );
                    hssfcell.setCellStyle(tableStyle);
                    // 停车费
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue( item.getParkActAmt() == null ? "" :item.getParkActAmt().toString());
                    hssfcell.setCellStyle(tableStyle);
                    // 优惠金额
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getParkCpn() == null ? "" : item.getParkCpn().toString());
                    hssfcell.setCellStyle(tableStyle);
                    // 进出场状态
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(EntranceStaEnum.getDesc(item.getStatus()));
                    hssfcell.setCellStyle(tableStyle);
                    // 进场支付状态
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(PayStatusEnum.getDesc(item.getInPayStatus())== null ? "" :PayStatusEnum.getDesc(item.getInPayStatus()));
                    hssfcell.setCellStyle(tableStyle);
                    // 出场支付状态
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(PayStatusEnum.getDesc(item.getOutPayStatus()) == null ? "" : PayStatusEnum.getDesc(item.getOutPayStatus()));
                    hssfcell.setCellStyle(tableStyle);
                    // 进场支付类型
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(TransMdEnum.getDesc(item.getPaymentMode()) == null ? "" :TransMdEnum.getDesc(item.getPaymentMode()));
                    hssfcell.setCellStyle(tableStyle);
                    // 出场支付类型
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(TransMdEnum.getDesc(item.getOutPaymentMode()) == null ? "" : TransMdEnum.getDesc(item.getOutPaymentMode()));
                    hssfcell.setCellStyle(tableStyle);
                    // 进场操作人
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getInOperNm() == null ? "" : item.getInOperNm());
                    hssfcell.setCellStyle(tableStyle);
                    // 出场操作人
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getOutOperNm() == null ? "" : item.getOutOperNm());
                    hssfcell.setCellStyle(tableStyle);
                    // 进出场类型
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getType());
                    hssfcell.setCellStyle(tableStyle);
                }
                //宽度自适应大小
                for (int z = 0; z < cellNames.length; z++) {
                    hssfsheet.autoSizeColumn(z);
                }
                FileOutputStream out = null;
                String fileName = System.currentTimeMillis() + ".xls";
                String uploadMkdir = "entrancexls";
                File file = new File(fileLocalPath + "/" + uploadMkdir + "/" + fileName);
                try {
                    out = new FileOutputStream(file);
                    workBook.write(out);
                } catch (Exception e) {
                    log.error("进出场管理-进出场停车费查询导出失败", e);
                    throw new RuntimeException("进出场管理-进出场停车费查询导出失败");
                } finally {
                    try {
                        if (out != null){
                            out.close();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    try {
                        if (workBook != null){
                            workBook.close();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                // 组请求报文
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
                multipartBodyBuilder.part("uploadMkdir", uploadMkdir);
                multipartBodyBuilder.part("file", new FileSystemResource(file));
                MultiValueMap<String, HttpEntity<?>> multiValueBody = multipartBodyBuilder.build();

                // 上传文件服务
                JSONObject result=null;
                try {
                    result = disp20210105Feign.DISP20210105(multiValueBody);
                } catch (Exception e) {
                    throw new RuntimeException("进出场管理-进出场停车费查询导出失败",e);
                }
                if(result==null||!("200".equals(result.getString("code")))){
                    return Result.build(null, USER_UPLOAD_FILE_FAIL.getCode(), USER_UPLOAD_FILE_FAIL.getMessage());
                }

                log.info("DISP20210504 进出场管理-进出场停车费查询导出成功");
                JSONObject jsonObject = new JSONObject();
                String fileUrl = result.getString("data");
                jsonObject.put("fileUrl",fileUrl);
                return Result.build(jsonObject, SUCCESS);

            } catch (Exception e) {
                log.error("DISP20210504" +
                        " 进出场管理-进出场停车费查询导出异常", e);
                return Result.fail();
            }
        }
        return Result.fail();
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

    //计算时间差
    private int getDiffHour(Date stTime, Date edTime){
        double diff = edTime.getTime() - stTime.getTime();
        double base = 1000*60*60;
        double diffTime = Math.ceil(diff/base);
        int diffHour = (int)diffTime;
        if(diffHour<0){
            diffHour =0;
        }
        return diffHour;
    }
    //时间差格式
    private String getFormatTime(Date stTime, Date edTime){
        long nh = 1000*60*60;
        long nm = 1000*60;
        long dTime = edTime.getTime()-stTime.getTime();
        long hour = dTime / nh;
        long min = (dTime % nh)/nm;
        String time = hour+"-"+min;
        return time;
    }
    //返回停车时间字符串
    private String getParkTimeStr(Date stTime, Date edTime){
        long nh = 1000*60*60;
        long nm = 1000*60;
        long dTime = edTime.getTime()-stTime.getTime();
        long hour = dTime / nh;
        long min = (dTime % nh)/nm;
        String timeStr;
        if(hour<=0){
            timeStr = min+"分钟";
        }else {
            timeStr =hour+"小时"+ min+"分钟";
        }
        return timeStr;
    }
//    判断是否在时间区间内
    private Boolean isBetweenTwoDate(Date star,Date end,Date now,int range){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        if(range==0){
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
        }else if(range==1){
            calendar.set(Calendar.MINUTE,0);
        }
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        now = calendar.getTime();
        if(now.compareTo(star)>=0&&now.compareTo(end)<=0){
            return true;
        }
        return false;
    }
//    string 转换成 list
    private List<String> getList(String listStr){
        if (listStr==null){
            return new ArrayList<>();
        }
        return Arrays.asList(listStr.split(","));
    }

    /**
     * 计算停车费用
     * @param tvp
     * @param outNumParam
     * @return
     */
    private JSONObject getParkAmt(TVechicleProcurer tvp,String outNumParam){
        JSONObject result = new JSONObject();
        //计算停车时长
        Date inTime = tvp.getInTime();
        Date outTime =new Date();
        int parkTime = getDiffHour(inTime,outTime);
        tvp.setOutTime(outTime);
        result.put("outTime",outTime);
        tvp.setParkTime(parkTime);
        result.put("parkTime",parkTime);
        result.put("parkTimeStr",getParkTimeStr(inTime,outTime));
        //计算停车费用
        //收费类型 0-空车 1-供货车
        if(tvp.getInTtlAmt()==null){
            tvp.setInTtlAmt(new BigDecimal(0));
        }
        List<TVechicleProcurerDetails> list = new ArrayList<>();
        try {
            list = tVechicleProcurerDetailsMapper.findDetailsListByInId(tvp.getInId());
        } catch (Exception e) {
            log.error("停车费计算-查询车辆进出场明细信息失败： ",e);
        }
        String chrgTp = list.size()>0?"1":"0";
        result.put("chrgTp",chrgTp);
        TVechicleProcurer tVechicleProcurer = new TVechicleProcurer();
        tVechicleProcurer.setChrgTp(chrgTp);
        tVechicleProcurer.setVehicleId(tvp.getVehicleId());
        tVechicleProcurer.setParkTime(tvp.getParkTime());
        tVechicleProcurer.setVehicleTp(tvp.getVehicleTp());
        //查询月卡信息
        TVehicleMonth tVehicleMonth  = tVehicleMonthMapper.selectByVehicleNum(tvp.getVehicleNum());
        List<String> inNum = new ArrayList<>();
        List<String> outNum = new ArrayList<>();
        if(tVehicleMonth!=null){
            inNum = getList(tVehicleMonth.getInNum());
            outNum = getList(tVehicleMonth.getOutNum());
        }
        //有月卡 0-普通月卡 1-超级月卡 2-免费月卡',
        String isNormal = "0";               //是否正常收费标识　０－正常收费　１－月卡收费
        if(tVehicleMonth!=null&&isBetweenTwoDate(tVehicleMonth.getStartDt(),tVehicleMonth.getDueDt(),outTime,0)){
            if(("0".equals(tVehicleMonth.getMcardTp())&&inNum.contains(tvp.getInDoor())&&outNum.contains(outNumParam))||("1".equals(tVehicleMonth.getMcardTp()))){
                result.put("parkAmt",BigDecimal.ZERO);
                result.put("parkActAmt",BigDecimal.ZERO);
                isNormal = "1";
            }else if("2".equals(tVehicleMonth.getMcardTp())){
                TParmeterInfo parm = new TParmeterInfo();
                parm.setParamType("04");
                parm.setParamNm("wly.park.startDate");
                TParmeterInfo parkStartDate = tParmeterInfoMapper.selectByTypeNum(parm);
                parm.setParamType("04");
                parm.setParamNm("wly.park.endDate");
                TParmeterInfo parkEndDate = tParmeterInfoMapper.selectByTypeNum(parm);
                if(parkStartDate!=null&&StringUtil.isNotBlank(parkStartDate.getParamVal())
                        &&parkEndDate!=null&&StringUtil.isNotBlank(parkEndDate.getParamVal())){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date startDate = null;
                    Date endDate = null;
                    try {
                        startDate =  simpleDateFormat.parse(parkStartDate.getParamVal());
                        endDate = simpleDateFormat.parse(parkEndDate.getParamVal());
                    } catch (ParseException e) {
                        log.error("免费月卡日期参数转换错误",e);
                    }
                    if(startDate!=null&&endDate!=null){
                        if(inTime.compareTo(startDate)<0||outTime.compareTo(endDate)>0){
                            Date newInTime = inTime;
                            Date newOutTime = outTime;
                            if(inTime.compareTo(startDate)<0){
                                newInTime = startDate;
                            }
                            if(outTime.compareTo(endDate)>0){
                                newOutTime=endDate;
                            }
                            parkTime = getDiffHour(newInTime,newOutTime);
                        }
                        if(outTime.compareTo(startDate)<=0||inTime.compareTo(endDate)>=0){
                            result.put("parkAmt",BigDecimal.ZERO);
                            result.put("parkActAmt",BigDecimal.ZERO);
                            isNormal = "1";
                        }
                    }
                }
                tVechicleProcurer.setChrgTp("2");
            }
        }
        //正常收费
        if(isNormal.equals("0")){
            //查询车辆收费标准
            List<CarChargeStdManage> chargeStdManageList =new ArrayList<>();
            try{
                chargeStdManageList = tvechiclePayManagerMapper.selectListByVehicleId(tVechicleProcurer);
            }catch (Exception e){
                log.error("查询进场记录-查询车辆收费标准信息失败： ",e);
                throw new RuntimeException("查询进场记录-查询车辆收费标准信息失败");
            }
            BigDecimal parkAmt =BigDecimal.ZERO;
            BigDecimal parkActAmt =BigDecimal.ZERO;
            if(chargeStdManageList!=null&&chargeStdManageList.size()>0){
                //车辆收费方式  0-每多少小时多少钱 1-固定收费
                BigDecimal parkCpn = tvp.getParkCpn();
                for(CarChargeStdManage chargeStdManage : chargeStdManageList){
                    int chrgTm = 1;
                    double chrgRate = 0;
                   if(chargeStdManage.getChrgTm()!=null){
                       chrgTm = chargeStdManage.getChrgTm();
                   }
                   if(!Double.isNaN(chargeStdManage.getChrgRate())){
                       chrgRate = chargeStdManage.getChrgRate();
                   }
                    if(chargeStdManage.getFixedChrg()==null){
                        chargeStdManage.setFixedChrg(0.00);
                    }
                    if(chargeStdManage.getChrgEndTm()!=null&&parkTime>chargeStdManage.getChrgEndTm()){
                        if("0".equals(chargeStdManage.getChrgMd())&&chargeStdManage.getChrgTm()>0){
                            double dTime = chargeStdManage.getChrgEndTm()-chargeStdManage.getChrgStTm();
                            double dCTime =  Math.ceil(dTime/chrgTm);
                            parkAmt = parkAmt.add(BigDecimal.valueOf(chrgRate*dCTime));
                        }else if("1".equals(chargeStdManage.getChrgMd())){
                            parkAmt = parkAmt.add(BigDecimal.valueOf(chargeStdManage.getFixedChrg()));
                        }
                    }else if((chargeStdManage.getChrgEndTm()==null&&parkTime>chargeStdManage.getChrgStTm())||(chargeStdManage.getChrgEndTm()!=null&&chargeStdManage.getChrgStTm()!=null&&parkTime>chargeStdManage.getChrgStTm()&&parkTime<=chargeStdManage.getChrgEndTm())){
                        if("0".equals(chargeStdManage.getChrgMd())&&chargeStdManage.getChrgTm()>0){
                            double dTime = parkTime-chargeStdManage.getChrgStTm();
                            double dCTime =  Math.ceil(dTime/chrgTm);
                            parkAmt = parkAmt.add(BigDecimal.valueOf(chrgRate*dCTime));
                        }else if("1".equals(chargeStdManage.getChrgMd())){
                            parkAmt = parkAmt.add(BigDecimal.valueOf(chargeStdManage.getFixedChrg()));
                        }
                    }
                }
                if(parkCpn==null){
                    parkCpn=BigDecimal.ZERO;
                }
                parkActAmt = parkAmt.subtract(parkCpn);
                if(parkActAmt.compareTo(BigDecimal.ZERO)==-1){
                    parkActAmt = BigDecimal.ZERO;
                }
            }
            parkActAmt = parkActAmt.setScale(0, RoundingMode.HALF_UP);
            result.put("parkActAmt",parkActAmt);
            parkAmt = parkAmt.setScale(0, RoundingMode.HALF_UP);
            result.put("parkAmt",parkAmt);
        }
//        停车费减免
        if("0".equals(tvp.getIsParkFree())){
            result.put("parkActAmt",BigDecimal.ZERO);
        }
        return result;
    }

    /**
     * 车辆档案维护
     * @param paramTvr
     * @param isUpTareWght
     * @param outTtlWght
     */
    private void maintainVehicleRecord(TVechicleRecord paramTvr,boolean isUpTareWght,BigDecimal outTtlWght){
        //根据车牌号 获取 车辆档案,若车辆档案为空或者与当前录入的车辆车型信息不一致则更新档案表
        TVechicleRecord tvr;
        try {
            tvr = tVechicleRecordMapper.selectByPrimaryKey(paramTvr.getVehicleNum());
            if(tvr==null||StringUtil.isBlank(tvr.getVehicleId())){
                if(!(StringUtil.isBlank(paramTvr.getVehicle())||StringUtil.isBlank(paramTvr.getVehicleId())
                        ||StringUtil.isBlank(paramTvr.getVehicleNum())||StringUtil.isBlank(paramTvr.getVehicleTpId()))){
                    tvr = new TVechicleRecord();
                    tvr.setVehicleNum(paramTvr.getVehicleNum());
                    tvr.setEmptyWght(paramTvr.getEmptyWght());
                    tvr.setVehicle(paramTvr.getVehicle());
                    tvr.setVehicleId(paramTvr.getVehicleId());
                    tvr.setVehicleTp(paramTvr.getVehicleTp());
                    tvr.setVehicleTpId(paramTvr.getVehicleTpId());
                    tvr.setProvId(paramTvr.getProvId());
                    tvr.setDriverNm(paramTvr.getDriverNm());
                    tvr.setDriverPhone(paramTvr.getDriverPhone());
                    tVechicleRecordMapper.insert(tvr);
                }
            }else{
                tvr.setProvId(paramTvr.getProvId());
                tvr.setDriverNm(paramTvr.getDriverNm());
                tvr.setDriverPhone(paramTvr.getDriverPhone());
                if(tvr.getVehicleId()!=null&&tvr.getVehicleTpId()!=null&&(!(tvr.getVehicleId().equals(paramTvr.getVehicleId()))||!(tvr.getVehicleTpId().equals(paramTvr.getVehicleTpId())))){
                    tvr.setVehicle(paramTvr.getVehicle());
                    tvr.setVehicleId(paramTvr.getVehicleId());
                    tvr.setVehicleTp(paramTvr.getVehicleTp());
                    tvr.setVehicleTpId(paramTvr.getVehicleTpId());
                }
                if(isUpTareWght){
                    tvr.setEmptyWght(outTtlWght);
                }else if(tvr.getEmptyWght()==null&&paramTvr.getEmptyWght()!=null){
                    tvr.setEmptyWght(paramTvr.getEmptyWght());
                }
                tVechicleRecordMapper.updateByPrimaryKey(tvr);
            }
        }catch (Exception e){
            log.error("大车进场-维护车辆档案失败： "+paramTvr,e);
        }
    }

}
