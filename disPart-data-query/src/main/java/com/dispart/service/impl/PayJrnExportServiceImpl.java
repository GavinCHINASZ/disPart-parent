package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.dao.CardReturnTaskMapper;
import com.dispart.dao.TransactionQueryMapper;
import com.dispart.dto.dataquery.*;
import com.dispart.dto.transactionDto.*;
import com.dispart.model.PayJrn;
import com.dispart.result.Result;
import com.dispart.service.PayJrnExportService;
import com.dispart.utils.DateUtils;
import com.dispart.utils.EnumTransUtil;
import com.dispart.utils.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

@Service
@Slf4j
public class PayJrnExportServiceImpl implements PayJrnExportService {

    @Value("${exportMaxItemNum}")
    private Integer exportMaxItemNum;

    @Value("${inOutFilePath}")
    private String inOutFilePath;

    @Value("${chargeFilePath}")
    private String chargeFilePath;

    @Value("${withdrawFilePath}")
    private String withdrawFilePath;

    @Value("${frazeFilePath}")
    private String frazeFilePath;

    @Value("${chargeRecheckPath}")
    private String chargeRecheckPath;

    @Value("${outRefundFilePath}")
    private String outRefundFilePath;

    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate2;

    @Resource
    private TransactionQueryMapper mapper;

    @Resource
    private CardReturnTaskMapper cardReturnTaskMapper;

    @Override
    public Result exportInOutTrans(TransactionSelectInDto inDto) {
        log.info("进出场明细导出业务开始，参数：" + JSON.toJSONString(inDto));
        inDto.setPageSize(exportMaxItemNum);
        inDto.setPageNum(0);
        ArrayList<DISP20210216OutDto> outList;
        try {
            outList = mapper.areaInOutQuery(inDto);
            if (outList.size()==0){
                return Result.build(306,"导出内容为空");
            }
        }catch (Exception e){
            log.error("查询异常");
            throw new RuntimeException(e);
        }
        log.info("进出场明细交易查询成功，开始生成xls文件");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("进出场明细交易数据表");
        XSSFRow headRow = sheet.createRow(0);
        String[] str = {"交易流水号","业务号","客户姓名","交易方式","交易金额","交易类型",
                "交易时间","状态","车牌号","进场时间","出场时间","操作员","备注"};
        for (int i=0; i<str.length; i++){
            headRow.createCell(i).setCellValue(str[i]);
        }
        XSSFRow row;
        int rowIndex = 1;
        for (DISP20210216OutDto data : outList){
            row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(data.getJrnlNum());
            row.createCell(1).setCellValue(data.getBusinessNo());
            row.createCell(2).setCellValue(data.getPayName());
            row.createCell(3).setCellValue(data.getTransMd()==null?"":EnumTransUtil.convertPaymentMode(data.getTransMd()));
            row.createCell(4).setCellValue(data.getTxnAmt()==null? "":data.getTxnAmt().toString());
            row.createCell(5).setCellValue(data.getTxnType()==null?"":EnumTransUtil.convertTxnType(data.getTxnType()));
            row.createCell(6).setCellValue(data.getTxnTm()==null?"":DateUtils.convertToDataTime(data.getTxnTm()));
            row.createCell(7).setCellValue(data.getStatus()==null?"":EnumTransUtil.convertPaymentSt(data.getStatus()));
            row.createCell(8).setCellValue(data.getVehicleNum());
            row.createCell(9).setCellValue(data.getInTime()==null?"":DateUtils.convertToDataTime(data.getInTime()));
            row.createCell(10).setCellValue(data.getOutTime()==null?"":DateUtils.convertToDataTime(data.getOutTime()));
            row.createCell(11).setCellValue(data.getOperId());
            row.createCell(12).setCellValue(data.getRemark());
            rowIndex++;
        }
        //宽度自适应大小
        for (int z = 0; z < str.length; z++) {
            sheet.autoSizeColumn(z);
        }
        final String remotePath = "inOutFile";
        String fileName = System.currentTimeMillis() + ".xls";
        File file = new File(inOutFilePath + "/" +fileName);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()){
            parentFile.mkdirs();
        }
        FileOutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(file);
            wb.write(outputStream);
            outputStream.flush();
        }catch (Exception e){
            log.error("文件异常",e);
        }finally {
            try{
                outputStream.close();
                wb.close();
            }catch (Exception e){
                log.error("文件异常",e);
            }
        }
        Result result = FileUploadUtil.post(file, remotePath,restTemplate2);
        log.info("进出场交易导出-文件上传成功，返回：" + result.getData());
        return result;
    }

    @Override
    public Result exportChargeTrans(TransactionSelectInDto inDto) {
        log.info("充值明细导出业务开始，参数：" + JSON.toJSONString(inDto));
        inDto.setPageSize(exportMaxItemNum);
        inDto.setPageNum(0);
        ArrayList<ChargeQueryOutDto> outList;
        try {
            outList = mapper.chargeTransQuery(inDto);
            if (outList.size()==0){
                return Result.build(306,"导出内容为空");
            }
        }catch (Exception e){
            log.error("查询异常");
            throw new RuntimeException(e);
        }
        log.info("充值交易明细交易查询成功，开始生成xls文件");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("充值交易明细数据表");
        XSSFRow headRow = sheet.createRow(0);
        String[] str = {"交易流水号","业务流水号","充值卡号","客户姓名","充值方式","充值金额(元)",
                "交易类型","冲正状态","冲正复核人","pos单号","惠市宝单号","操作人","充值时间","支付状态","备注"};
        for (int i=0; i<str.length; i++){
            headRow.createCell(i).setCellValue(str[i]);
        }
        XSSFRow row;
        int rowIndex = 1;
        for (ChargeQueryOutDto data : outList){
            row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(data.getJrnlNum());
            row.createCell(1).setCellValue(data.getBusinessNo());
            row.createCell(2).setCellValue(data.getPayCard());
            row.createCell(3).setCellValue(data.getPayName());
            row.createCell(4).setCellValue(EnumTransUtil.convertPaymentMode(data.getTransMd()));
            row.createCell(5).setCellValue(data.getTxnAmt().toString());
            row.createCell(6).setCellValue(EnumTransUtil.convertTxnType(data.getTxnType()));
            row.createCell(7).setCellValue(EnumTransUtil.convertCheckStatus(data.getCheckSt()));
            row.createCell(8).setCellValue(data.getCheckNm());
            row.createCell(9).setCellValue(data.getPosOrderId());
            row.createCell(10).setCellValue(data.getHsbOrderId());
            row.createCell(11).setCellValue(data.getOperId());
            row.createCell(12).setCellValue(DateUtils.convertToDataTime(data.getTxnTm()));
            row.createCell(13).setCellValue(EnumTransUtil.convertPaymentSt(data.getStatus()));
            row.createCell(14).setCellValue(data.getRemark());
            rowIndex++;
        }
        //宽度自适应大小
        for (int z = 0; z < str.length; z++) {
            sheet.autoSizeColumn(z);
        }
        final String remotePath = "chargeFile";
        String fileName = System.currentTimeMillis() + ".xls";
        File file = new File(chargeFilePath + "/" +fileName);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()){
            parentFile.mkdirs();
        }
        FileOutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(file);
            wb.write(outputStream);
            outputStream.flush();
        }catch (Exception e){
            log.error("文件异常",e);
        }finally {
            try{
                outputStream.close();
                wb.close();
            }catch (Exception e){
                log.error("文件异常",e);
            }
        }
        Result result = FileUploadUtil.post(file, remotePath,restTemplate2);
        log.info("充值交易导出-文件上传成功，返回：" + result.getData());
        return result;
    }

    @Override
    public Result exportWithdrawTrans(TransactionSelectInDto inDto) {
        log.info("提现明细导出业务开始，参数：" + JSON.toJSONString(inDto));
        inDto.setPageSize(exportMaxItemNum);
        inDto.setPageNum(0);
        ArrayList<PayJrn> outList;
        try {
            inDto.setTxnType("1");
            outList = mapper.transQuery(inDto);
            if (outList.size()==0){
                return Result.build(306,"导出内容为空");
            }
        }catch (Exception e){
            log.error("查询异常");
            throw new RuntimeException(e);
        }
        log.info("提现交易明细交易查询成功，开始生成xls文件");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("提现交易明细数据表");
        XSSFRow headRow = sheet.createRow(0);
        String[] str = {"交易流水号","卡号","提现方式","提现金额(元)","客户编号","客户姓名",
                "操作人","提现时间","交易类型","状态","备注"};
        for (int i=0; i<str.length; i++){
            headRow.createCell(i).setCellValue(str[i]);
        }
        XSSFRow row;
        int rowIndex = 1;
        for (PayJrn data : outList){
            row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(data.getJrnlNum());
            row.createCell(1).setCellValue(data.getPayCard());
            row.createCell(2).setCellValue(EnumTransUtil.convertPaymentMode(data.getTransMd()));
            row.createCell(3).setCellValue(data.getTxnAmt().toString());
            row.createCell(4).setCellValue(data.getPayerNo());
            row.createCell(5).setCellValue(data.getPayName());
            row.createCell(6).setCellValue(data.getOperId());
            row.createCell(7).setCellValue(DateUtils.convertToDataTime(data.getTxnTm()));
            row.createCell(8).setCellValue(EnumTransUtil.convertTxnType(data.getTxnType()));
            row.createCell(9).setCellValue(EnumTransUtil.convertPaymentSt(data.getStatus()));
            row.createCell(10).setCellValue(data.getRemark());
            rowIndex++;
        }
        //宽度自适应大小
        for (int z = 0; z < str.length; z++) {
            sheet.autoSizeColumn(z);
        }
        final String remotePath = "withDrawFile";
        String fileName = System.currentTimeMillis() + ".xls";
        File file = new File(withdrawFilePath + "/" +fileName);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()){
            parentFile.mkdirs();
        }
        FileOutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(file);
            wb.write(outputStream);
            outputStream.flush();
        }catch (Exception e){
            log.error("文件异常",e);
        }finally {
            try{
                outputStream.close();
                wb.close();
            }catch (Exception e){
                log.error("文件异常",e);
            }
        }
        Result result = FileUploadUtil.post(file, remotePath,restTemplate2);
        log.info("提现交易导出-文件上传成功，返回：" + result.getData());
        return result;
    }

    @Override
    public Result exportFrazeTrans(FrazeTransInDto inDto) {
        log.info("冻结明细导出业务开始，参数：" + JSON.toJSONString(inDto));
        inDto.setPageSize(exportMaxItemNum);
        inDto.setPageNum(0);
        ArrayList<FrazeTransOutDto> outList;
        try {
            outList = mapper.frazeTransQuery(inDto);
            if (outList.size()==0){
                return Result.build(306,"导出内容为空");
            }
        }catch (Exception e){
            log.error("查询异常");
            throw new RuntimeException(e);
        }
        log.info("冻结交易明细交易查询成功，开始生成xls文件");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("冻结交易明细数据表");
        XSSFRow headRow = sheet.createRow(0);
        String[] str = {"编号","客户帐号","客户卡号","客户姓名","操作前金额(元)","操作金额(元)",
                "操作后金额(元)","操作","操作时间","操作人","备注"};
        for (int i=0; i<str.length; i++){
            headRow.createCell(i).setCellValue(str[i]);
        }
        XSSFRow row;
        int rowIndex = 1;
        for (FrazeTransOutDto data : outList){
            row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(rowIndex);
            row.createCell(1).setCellValue(data.getAccount());
            row.createCell(2).setCellValue(data.getCardNo());
            row.createCell(3).setCellValue(data.getProvNm());
            row.createCell(4).setCellValue(data.getBeforAmt().toString());
            row.createCell(5).setCellValue(data.getAfterAmt().toString());
            row.createCell(6).setCellValue(EnumTransUtil.convertFrazeStatus(data.getTxnType()));
            row.createCell(7).setCellValue(DateUtils.convertToDataTime(data.getTxnDt()));
            row.createCell(8).setCellValue(data.getOperNm());
            row.createCell(9).setCellValue(data.getRemark());
            rowIndex++;
        }
        //宽度自适应大小
        for (int z = 0; z < str.length; z++) {
            sheet.autoSizeColumn(z);
        }
        final String remotePath = "frazeFile";
        String fileName = System.currentTimeMillis() + ".xls";
        File file = new File(frazeFilePath + "/" +fileName);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()){
            parentFile.mkdirs();
        }
        FileOutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(file);
            wb.write(outputStream);
            outputStream.flush();
        }catch (Exception e){
            log.error("文件异常",e);
        }finally {
            try{
                outputStream.close();
                wb.close();
            }catch (Exception e){
                log.error("文件异常",e);
            }
        }
        Result result = FileUploadUtil.post(file, remotePath,restTemplate2);
        log.info("冻结交易导出-文件上传成功，返回：" + result.getData());
        return result;
    }

    @Override
    public Result exportChargeRecheckTrans(TransactionSelectInDto inDto) {
        log.info("充值冲正导出业务开始，参数：" + JSON.toJSONString(inDto));
        inDto.setPageSize(exportMaxItemNum);
        inDto.setPageNum(0);
        ArrayList<ChargeQueryOutDto> outList;
        try {
            outList = mapper.getAccnoRevereApplyInfo(inDto);
            if (outList.size()==0){
                return Result.build(306,"导出内容为空");
            }
        }catch (Exception e){
            log.error("查询异常");
            throw new RuntimeException(e);
        }
        log.info("充值冲正交易明细交易查询成功，开始生成xls文件");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("充值冲正交易明细数据表");
        XSSFRow headRow = sheet.createRow(0);
        String[] str = {"交易流水号","业务流水号","充值卡号","客户编号","客户姓名","充值方式",
                "充值金额(元)","交易类型","冲正状态","冲正复核人","pos单号","惠市宝单号","操作人","充值时间","支付状态","备注"};
        for (int i=0; i<str.length; i++){
            headRow.createCell(i).setCellValue(str[i]);
        }
        XSSFRow row;
        int rowIndex = 1;
        for (ChargeQueryOutDto data : outList){
            row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(data.getJrnlNum());
            row.createCell(1).setCellValue(data.getBusinessNo());
            row.createCell(2).setCellValue(data.getPayCard());
            row.createCell(3).setCellValue(data.getPayerNo());
            row.createCell(4).setCellValue(data.getPayName());
            row.createCell(5).setCellValue(data.getTransMd()==null?"":EnumTransUtil.convertPaymentMode(data.getTransMd()));
            row.createCell(6).setCellValue(data.getTxnAmt()==null?"":data.getTxnAmt().toString());
            row.createCell(7).setCellValue(data.getTxnType()==null?"":EnumTransUtil.convertTxnType(data.getTxnType()));
            row.createCell(8).setCellValue(data.getCheckSt()==null?"":EnumTransUtil.convertCheckStatus(data.getCheckSt()));
            row.createCell(9).setCellValue(data.getCheckNm());
            row.createCell(10).setCellValue(data.getPosOrderId());
            row.createCell(11).setCellValue(data.getHsbOrderId());
            row.createCell(12).setCellValue(data.getOperId());
            row.createCell(13).setCellValue(data.getTxnTm()==null?"":DateUtils.convertToDataTime(data.getTxnTm()));
            row.createCell(14).setCellValue(data.getStatus()==null?"":EnumTransUtil.convertPaymentSt(data.getStatus()));
            row.createCell(15).setCellValue(data.getRemark());
            rowIndex++;
        }
        //宽度自适应大小
        for (int z = 0; z < str.length; z++) {
            sheet.autoSizeColumn(z);
        }
        final String remotePath = "chargeRecheckFile";
        String fileName = System.currentTimeMillis() + ".xls";
        File file = new File(chargeRecheckPath + "/" +fileName);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()){
            parentFile.mkdirs();
        }
        FileOutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(file);
            wb.write(outputStream);
            outputStream.flush();
        }catch (Exception e){
            log.error("文件异常",e);
        }finally {
            try{
                outputStream.close();
                wb.close();
            }catch (Exception e){
                log.error("文件异常",e);
            }
        }
        FileUploadUtil fileUploadUtil = new FileUploadUtil();
        Result result = FileUploadUtil.post(file, remotePath,restTemplate2);
        log.info("充值冲正交易导出-文件上传成功，返回：" + result.getData());
        return result;
    }

    @Override
    public Result exportOutRefundTrans(Disp20210349InDto inDto) {
        log.info("出场退费导出业务开始，参数：" + JSON.toJSONString(inDto));
        inDto.setPageSize(exportMaxItemNum);
        inDto.setPageNum(0);
        ArrayList<Disp20210349OutDto> outList;
        try {
            outList = cardReturnTaskMapper.selectCardReturnTask(inDto);
            if (outList.size()==0){
                return Result.build(306,"导出内容为空");
            }
        }catch (Exception e){
            log.error("查询异常");
            throw new RuntimeException(e);
        }
        log.info("出场退费交易明细交易查询成功，开始生成xls文件");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("出场退费交易明细数据表");
        XSSFRow headRow = sheet.createRow(0);
        String[] str = {"客户卡号","交易金额(元)","交易方式","交易状态","出场办理人","车牌号",
                "出场时间","创建时间","交易时间"};
        for (int i=0; i<str.length; i++){
            headRow.createCell(i).setCellValue(str[i]);
        }
        XSSFRow row;
        int rowIndex = 1;
        for (Disp20210349OutDto data : outList){
            row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(data.getCardNo());
            row.createCell(1).setCellValue(data.getAmount().toString());
            row.createCell(2).setCellValue(EnumTransUtil.convertPaymentMode("12"));
            row.createCell(3).setCellValue(EnumTransUtil.convertPaymentSt(data.getStatus()));
            row.createCell(4).setCellValue(data.getOperNm());
            row.createCell(5).setCellValue(data.getVehicleNum());
            row.createCell(6).setCellValue(data.getOutTime()==null? "":DateUtils.convertToDataTime(data.getOutTime()));
            row.createCell(7).setCellValue(DateUtils.convertToDataTime(data.getCreatTime()));
            row.createCell(8).setCellValue(DateUtils.convertToDataTime(data.getUpTime()));
            rowIndex++;
        }
        //宽度自适应大小
        for (int z = 0; z < str.length; z++) {
            sheet.autoSizeColumn(z);
        }
        final String remotePath = "outRefundFile";
        String fileName = System.currentTimeMillis() + ".xls";
        File file = new File(outRefundFilePath + "/" +fileName);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()){
            parentFile.mkdirs();
        }
        FileOutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(file);
            wb.write(outputStream);
            outputStream.flush();
        }catch (Exception e){
            log.error("文件异常",e);
        }finally {
            try{
                outputStream.close();
                wb.close();
            }catch (Exception e){
                log.error("文件异常",e);
            }
        }
        Result result = FileUploadUtil.post(file, remotePath,restTemplate2);
        log.info("出场退费交易导出-文件上传成功，返回：" + result.getData());
        return result;
    }
}
