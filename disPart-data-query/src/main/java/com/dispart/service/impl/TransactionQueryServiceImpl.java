package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.baseDto.BaseSelectionOutDto;
import com.dispart.dao.PayJrnMapper;
import com.dispart.dao.TransactionQueryMapper;
import com.dispart.dto.billDto.DISP20210307OutDto;
import com.dispart.dto.dataquery.*;
import com.dispart.dto.transactionDto.*;
import com.dispart.enums.PayStatusEnum;
import com.dispart.model.PayJrn;
import static com.dispart.model.businessCommon.TxnTypeEnum.*;

import static com.dispart.model.businessCommon.TransMdEnum.*;

import com.dispart.model.businessCommon.TransMdEnum;
import com.dispart.model.businessCommon.TxnTypeEnum;
import com.dispart.result.Result;
import com.dispart.service.TransactionQueryService;
import com.dispart.vo.dataQuery.Disp20210218OutVo;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

@Service
@Slf4j
public class TransactionQueryServiceImpl implements TransactionQueryService {

    @Autowired
    private TransactionQueryMapper mapper;

    @Resource
    private PayJrnMapper payJrnMapper;

    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate2;

    @Value("${adjustFilePath}")
    private String adjustFilePath;

    @Value("${exportMaxItemNum}")
    private Integer exportMaxItemNum;

    @Override
    public Result chargeTransactionQuery(TransactionSelectInDto inDto) {
        log.info("充值交易查询开始，传入参数：" + JSON.toJSONString(inDto));
        if (inDto.getPageNum() != null && inDto.getPageSize() != null){
            inDto.setPageNum((inDto.getPageNum()-1)*inDto.getPageSize());
        }
        Integer tolPageNum = mapper.chargeTransQueryCount(inDto);
        ArrayList<ChargeQueryOutDto> payJrns = mapper.chargeTransQuery(inDto);
        BaseSelectionOutDto<ChargeQueryOutDto> outDto = new BaseSelectionOutDto<>();
        outDto.setTolPageNum(tolPageNum);
        outDto.setDataList(payJrns);
        log.info("充值交易结束，返回：" + JSON.toJSONString(outDto));
        return Result.ok(outDto);
    }

    @Override
    public Result withdrawTransactionQuery(TransactionSelectInDto inDto) {
        log.info("提现交易查询开始，传入参数：" + JSON.toJSONString(inDto));
        if (inDto.getPageNum() != null && inDto.getPageSize() != null){
            inDto.setPageNum((inDto.getPageNum()-1)*inDto.getPageSize());
        }
        inDto.setTxnType("1");
        BaseSelectionOutDto<PayJrn> outDto = new BaseSelectionOutDto<>();
        Integer tolPageNum = mapper.transQueryCount(inDto);
        outDto.setTolPageNum(tolPageNum);
        if (tolPageNum == 0){
            log.info("提现交易查询结束，返回：" + JSON.toJSONString(outDto));
            return Result.ok(outDto);
        }
        ArrayList<PayJrn> payJrns = mapper.transQuery(inDto);
        outDto.setTolPageNum(tolPageNum);
        outDto.setDataList(payJrns);
        log.info("提现交易查询结束，返回：" + JSON.toJSONString(outDto));
        return Result.ok(outDto);
    }

    @Override
    public Result areaInOutTransactionQuery(TransactionSelectInDto inDto) {
        log.info("进出场交易查询，传入参数：" + JSON.toJSONString(inDto));
        if (inDto.getPageNum() != null && inDto.getPageSize() != null){
            inDto.setPageNum((inDto.getPageNum()-1)*inDto.getPageSize());
        }
        Integer tolPageNum = mapper.areaInOutQueryCount(inDto);
        ArrayList<DISP20210216OutDto> res = mapper.areaInOutQuery(inDto);

        //退费时，付款人和收款人交换
        ArrayList<DISP20210216OutDto> dataList = new ArrayList<>();
        for (DISP20210216OutDto data : res) {
            if (TxnTypeEnum.CARFEE_PRE.getTxnType().equals(data.getTxnType())
                    || TxnTypeEnum.IN_WITHDAW.getTxnType().equals(data.getTxnType())){
                data.setPayerNo(data.getPayeeNum());
                data.setPayName(data.getPayeeName());
            }
            dataList.add(data);
        }

        BaseSelectionOutDto<DISP20210216OutDto> outDto = new BaseSelectionOutDto<>();
        outDto.setTolPageNum(tolPageNum);
        outDto.setDataList(dataList);
        log.info("进出场交易查询结束，返回：" + JSON.toJSONString(outDto));
        return Result.ok(outDto);
    }

    @Override
    public Result frazeTransQuery(FrazeTransInDto inDto) {
        log.info("冻结交易查询开始，传入参数："+JSON.toJSONString(inDto));
        if (inDto.getPageNum() != null && inDto.getPageSize() != null){
            inDto.setPageNum((inDto.getPageNum()-1)*inDto.getPageSize());
        }

        Integer tolPageNum = mapper.frazeTransQueryCount(inDto);
        ArrayList<FrazeTransOutDto> result = mapper.frazeTransQuery(inDto);

        BaseSelectionOutDto<FrazeTransOutDto> outDto = new BaseSelectionOutDto<>();
        outDto.setTolPageNum(tolPageNum);
        outDto.setDataList(result);
        log.info("冻结交易查询结束，返回：" + JSON.toJSONString(outDto));
        return Result.ok(outDto);
    }

    @Override
    public Result adjustTransQuery(AdjustTransInDto inDto) {
        log.info("调账交易查询开始，传入参数："+JSON.toJSONString(inDto));
        if (inDto.getPageNum() != null && inDto.getPageSize() != null){
            inDto.setPageNum((inDto.getPageNum()-1)*inDto.getPageSize());
        }
        Integer tolPageNum = mapper.adjustTransQueryCount(inDto);
        ArrayList<AdjustTransOutDto> result = mapper.adjustTransQuery(inDto);
        inDto.setOperTp("0");
        BigDecimal incAmt = mapper.getAdjustAmt(inDto);
        inDto.setOperTp("1");
        BigDecimal decAmt = mapper.getAdjustAmt(inDto);

        Disp20210218OutVo outVo = new Disp20210218OutVo();
        outVo.setTolPageNum(tolPageNum);
        outVo.setIncAmt(incAmt);
        outVo.setDecAmt(decAmt);
        outVo.setDataList(result);
        return Result.ok(outVo);
    }

    @Override
    public Result exportAdjuestTrans(AdjustTransInDto inDto) {
        log.info("调账导出业务开始，参数：" + JSON.toJSONString(inDto));
        inDto.setPageSize(exportMaxItemNum);
        inDto.setPageNum(0);
        ArrayList<AdjustTransOutDto> outList;
        try {
            outList = mapper.adjustTransQuery(inDto);
        }catch (Exception e){
            log.error("查询异常");
            throw new RuntimeException(e);
        }
        log.info("调账交易查询成功，开始生成xls文件");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("调账交易数据表");
        XSSFRow headRow = sheet.createRow(0);
        String[] str = {"编号","客户帐号","客户卡号","客户姓名","调账金额（元）","金额标识",
                "交易时间","操作人","备注"};
        for (int i=0; i<str.length; i++){
            headRow.createCell(i).setCellValue(str[i]);
        }
        XSSFRow row;
        int rowIndex = 1;
        for (AdjustTransOutDto data : outList){
            row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(rowIndex);
            row.createCell(1).setCellValue(data.getAccount());
            row.createCell(2).setCellValue(data.getCardNo());
            row.createCell(3).setCellValue(data.getProvNm());
            row.createCell(4).setCellValue(data.getTxnAmt().toString());
            if ("0".equals(data.getOperTp())){ row.createCell(5).setCellValue("加余额"); }
            if ("1".equals(data.getOperTp())){ row.createCell(5).setCellValue("减余额"); }
            row.createCell(6).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data.getTxnDt()));
            row.createCell(7).setCellValue(data.getOperNm());
            row.createCell(8).setCellValue(data.getRemark());
            rowIndex++;
        }
        //宽度自适应大小
        for (int z = 0; z < str.length; z++) {
            sheet.autoSizeColumn(z);
        }
        final String remotePath = "adjustFile";
        String fileName = (new Date()).toString() + ".xls";
        File file = new File(adjustFilePath + "/" +fileName);
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
        log.info("文件生成成功，开始上传文件");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("uploadMkdir", remotePath);
        multipartBodyBuilder.part("file", new FileSystemResource(file));
        MultiValueMap<String, HttpEntity<?>> multiValueBody = multipartBodyBuilder.build();
        //上传文件服务
        Result result = null;
        try {
            result = restTemplate2.postForObject("http://" + "disPart-files" + "/securityCenter/DISP20210105", multiValueBody, Result.class);
        } catch (Exception e) {
            log.error("调用文件上传服务失败" + e);
            throw new RuntimeException("调用文件上传服务失败");
        }
        if (ObjectUtils.isEmpty(result) || result.getCode() != 200) {
            return Result.build(310,"文件上传失败");
        }
        log.info("调账交易导出-文件上传成功，返回：" + result.getData());
        return Result.ok((String)result.getData());
    }

    @Override
    public Result getAccnoRevereApplyInfo(TransactionSelectInDto inDto) {
        log.info("冲正查询开始，传入参数：" + JSON.toJSONString(inDto));
        if (inDto.getPageNum() != null && inDto.getPageSize() != null){
            inDto.setPageNum((inDto.getPageNum()-1)*inDto.getPageSize());
        }
        Integer tolPageNum = mapper.getAccnoRevereApplyCount(inDto);
        ArrayList<ChargeQueryOutDto> payJrns = mapper.getAccnoRevereApplyInfo(inDto);
        BaseSelectionOutDto<ChargeQueryOutDto> outDto = new BaseSelectionOutDto<>();
        outDto.setTolPageNum(tolPageNum);
        outDto.setDataList(payJrns);
        log.info("冲正查询结束，返回：" + JSON.toJSONString(outDto));
        return Result.ok(outDto);
    }

    @Override
    public Result<BaseSelectionOutDto> getPayJrn(Disp20210338InDto inDto) {
        log.info("业务交易查询开始，参数：" + JSON.toJSONString(inDto));
        if (inDto.getPageNum()!= null && inDto.getPageSize()!=null){
            inDto.setPageNum((inDto.getPageNum()-1)*inDto.getPageSize());
        }
        BaseSelectionOutDto<Disp20210338OutDto> outDto = new BaseSelectionOutDto<>();
        Integer tolPageNum = payJrnMapper.getPayJrnCount(inDto);
        outDto.setTolPageNum(tolPageNum);
        if (tolPageNum == 0){
            log.info("业务交易查询结束，未查询到数据");
            return Result.ok(outDto);
        }
        ArrayList<Disp20210338OutDto> dataList = payJrnMapper.getPayJrn(inDto);

        outDto.setDataList(dataList);
        log.info("业务交易查询结束，返回：" + JSON.toJSONString(outDto));
        return Result.ok(outDto);
    }

    @Override
    public void getExcel(Disp20210338InDto inDto, HttpServletResponse response) {
        log.info("交易数据导出，参数：" + JSON.toJSONString(inDto));
        inDto.setPageNum(0);
        inDto.setPageSize(exportMaxItemNum);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow row = sheet.createRow(0);
        String [] str = {"序号","交易流水号","业务单号","交易类型","交易方式","交易金额(元)","手续费金额(元)","交易状态","办理人","交易时间",
                "付款人名称","付款人卡号","收款人名称","收款人卡号","惠市宝单号","pos单号"};
        for (int i=0; i<str.length; i++){
            row.createCell(i).setCellValue(str[i]);
        }

        int rowNum = 1;
        String txnTypeName = "";
        String transMdName = "";
        String statusName = "";
        ArrayList<Disp20210338OutDto> payJrn = payJrnMapper.getPayJrn(inDto);
        for (Disp20210338OutDto item : payJrn){

            //交易类型转化
            switch (item.getTxnType()){
                case "0": {
                    txnTypeName = CHARGE.getDesc();
                    break;
                }
                case "1": {
                    txnTypeName = CASHOUT.getDesc();
                    break;
                }
                case "2": {
                    txnTypeName = ENTRYFEENTR.getDesc();
                    break;
                }
                case "3": {
                    txnTypeName = CARFEE.getDesc();
                    break;
                }
                case "4": {
                    txnTypeName = WITHDRAW.getDesc();
                    break;
                }
                case "5": {
                    txnTypeName = MARKET.getDesc();
                    break;
                }
                case "6": {
                    txnTypeName = BILL.getDesc();
                    break;
                }
                case "7": {
                    txnTypeName = MCARD.getDesc();
                    break;
                }
                case "8": {
                    txnTypeName = SUPPLY_SUBSIDY.getDesc();
                    break;
                }
                case "9": {
                    txnTypeName = SUPPLY_CALCELSUBSIDY.getDesc();
                    break;
                }
                case "10": {
                    txnTypeName = IN_WITHDAW.getDesc();
                    break;
                }
                case "12": {
                    txnTypeName = ENTRYFEENTR_PRE.getDesc();
                    break;
                }
                case "13": {
                    txnTypeName = MCARD_WITHDRAW.getDesc();
                    break;
                }
                case "14": {
                    txnTypeName = PURCH_SUBSIDY.getDesc();
                    break;
                }
                case "15": {
                    txnTypeName = PURCH_CALCELSUBSIDY.getDesc();
                    break;
                }
                case "16": {
                    txnTypeName = CARFEE_PRE.getDesc();
                    break;
                }
                default: {
                    txnTypeName = "";
                    break;
                }
            }

            //交易方式转化
            switch (item.getTransMd()){
                case "1": {
                    transMdName = HSB.getDesc();
                    break;
                }
                case "2": {
                    transMdName = CASH.getDesc();
                    break;
                }
                case "3": {
                    transMdName = POS_CARD.getDesc();
                    break;
                }
                case "4": {
                    transMdName = CARD.getDesc();
                    break;
                }
                case "5": {
                    transMdName = BANK.getDesc();
                    break;
                }
                case "6": {
                    transMdName = POS_ERWEIMA.getDesc();
                    break;
                }
                case "7": {
                    transMdName = CHONGZHENG.getDesc();
                    break;
                }
                case "8": {
                    transMdName = OTHER.getDesc();
                }
                default: {
                    transMdName = "";
                    break;
                }
            }

            //交易状态转化
            switch (item.getStatus()){
                case "0": {
                    statusName = PayStatusEnum.DEALING.getMsg();
                    break;
                }
                case "1": {
                    statusName = PayStatusEnum.FAIL.getMsg();
                    break;
                }
                case "2": {
                    statusName = PayStatusEnum.SUCCESS.getMsg();
                    break;
                }
                case "3": {
                    statusName = PayStatusEnum.UNKNOWN.getMsg();
                    break;
                }
                case "4": {
                    statusName = PayStatusEnum.REFUNDING.getMsg();
                    break;
                }
                case "5": {
                    statusName = PayStatusEnum.REFUNDED.getMsg();
                    break;
                }
                case "9": {
                    statusName = PayStatusEnum.NOT_PAY.getMsg();
                    break;
                }
                default: {
                    statusName = "";
                    break;
                }
            }

            XSSFRow rows = sheet.createRow(rowNum);
            rows.createCell(0).setCellValue(rowNum);
            rows.createCell(1).setCellValue(item.getJrnlNum());
            rows.createCell(2).setCellValue(item.getBusinessNo());
            rows.createCell(3).setCellValue(txnTypeName);
            rows.createCell(4).setCellValue(transMdName);
            rows.createCell(5).setCellValue(item.getTxnAmt().doubleValue());
            rows.createCell(6).setCellValue(item.getExtraAmt().doubleValue());
            rows.createCell(7).setCellValue(statusName);
            rows.createCell(8).setCellValue(item.getOperId());
            rows.createCell(9).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getTxnTm()));
            rows.createCell(10).setCellValue(item.getPayName());
            rows.createCell(11).setCellValue(item.getPayCard());
            rows.createCell(12).setCellValue(item.getPayee());
            rows.createCell(13).setCellValue(item.getPayeeCard());
            rows.createCell(14).setCellValue(item.getMainOrderId());
            rows.createCell(15).setCellValue(item.getPosOrderId());
            rowNum ++;
        }

        //宽度自适应大小
        for (int z = 0; z < str.length; z++) {
            sheet.autoSizeColumn(z);
        }

        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xls";
        OutputStream outputStream = null;
        try{
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.addHeader("Content-Disposition","attachment;filename=" + fileName);
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
        }catch (Exception e){
            log.error("文件异常",e);
        }finally {
            try{
                outputStream.close();
            }catch (Exception e){
                log.error("文件异常",e);
            }
        }
    }
}


