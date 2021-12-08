package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dispart.baseDto.BaseSelectionOutDto;
import com.dispart.dto.billDto.*;
import com.dispart.dao.BillingDetailMapper;
import com.dispart.enums.BaseEnum;
import com.dispart.enums.MCardEnum;
import com.dispart.enums.PayStatusEnum;
import com.dispart.enums.bill.BillResultCodeEnum;
import com.dispart.model.PayJrn;
import com.dispart.model.businessCommon.TransMdEnum;
import com.dispart.model.businessCommon.TxnTypeEnum;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.BillService;
import com.dispart.utils.DateUtil;
import com.dispart.vo.busineCommon.TPushNotesVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omg.CORBA.WStringSeqHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import static com.dispart.enums.bill.BillStatus.*;
import static com.dispart.enums.PayStatusEnum.*;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
@Slf4j
public class BillServiceImpl implements BillService{

    @Autowired
    private BillingDetailMapper mapper;

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate2;

    @Value("${fileProps.billRemotePath}")
    private String remotePath;

    @Value("${fileProps.billLocalPath}")
    private String localPath;

    @Value("${maxExportNum}")
    private Integer maxExportNum;

    /**
     * 录入账单
     * @author  zhaoshihao
     * @date 2021/8/9
    */
    @Override
    public Result addBill(BillInsertInDto billingDetail) {
        if (StringUtils.isEmpty(billingDetail.getProvId())){
            log.error("客户编号为空");
            return Result.build(BillResultCodeEnum.PARAM_NULL.getCode(),"客户编号不能为空");
        }
        if(StringUtils.isEmpty(billingDetail.getProvNm())){
            log.error("客户名称为空");
            return Result.build(BillResultCodeEnum.PARAM_NULL.getCode(),"客户名称不能为空");
        }
        if(StringUtils.isEmpty(billingDetail.getPayItem())){
            log.error("缴费项目为空");
            return Result.build(BillResultCodeEnum.PARAM_NULL.getCode(),"缴费项目不能为空");
        }
        if(StringUtils.isEmpty(billingDetail.getPayId())){
            log.error("缴费项目ID为空");
            return Result.build(BillResultCodeEnum.PARAM_NULL.getCode(),"缴费项目ID不能为空");
        }
        if(StringUtils.isEmpty(billingDetail.getDepId())){
            log.error("部门Id为空");
            return Result.build(BillResultCodeEnum.PARAM_NULL.getCode(),"部门Id不能为空");
        }
        if(StringUtils.isEmpty(billingDetail.getOperId())){
            log.error("操作员ID为空");
            return Result.build(BillResultCodeEnum.PARAM_NULL.getCode(),"操作员ID不能为空");
        }
        if(StringUtils.isEmpty(billingDetail.getOperNm())){
            log.error("操作员名称为空");
            return Result.build(BillResultCodeEnum.PARAM_NULL.getCode(),"操作员名称不能为空");
        }
        if (billingDetail.getActRecevAmt() == null){
            return Result.build(BillResultCodeEnum.PARAM_NULL.getCode(),"实付金额不能为空");
        }
        if (billingDetail.getActRecevAmt().compareTo(new BigDecimal("0.00"))<=0){
            return Result.build(BillResultCodeEnum.INVALID_AMT.getCode(),"实付金额不合法");
        }

        log.debug("获取账单序列号");
        Integer seq;
        try{
            seq = mapper.getBillNumSeq();
        }catch (Exception e){
            log.error("获取账单编号序列异常", e);
            throw new RuntimeException(e);
        }
        String billNum = DateUtil.getDateString(new Date()) + String.format("%06d",seq);
        billingDetail.setBillNum(billNum);
        billingDetail.setBillSt(NORMAL.getCode());
        billingDetail.setPaymentSt(NOT_PAY.getCode());

        log.debug("开始插入数据");
        Integer i;
        try{
            i = mapper.addBill(billingDetail);
        }catch (Exception e){
            log.error("数据插入异常", e);
            throw new RuntimeException(e);
        }
        if (i>0){
            log.info("账单录入成功");
            DISP20210205OutDto outDto = new DISP20210205OutDto();
            BeanUtils.copyProperties(billingDetail,outDto);
            return Result.ok(outDto);
        }else {
            log.error("账单录入失败");
            return Result.build(307,"数据插入失败");
        }
    }

    /**
     * 账单查询
     * @author  zhaoshihao
     * @date 2021/8/9
    */
    @Override
    public Result selectBills(Request<BillSelectionInDto> inDto) {
        BillSelectionInDto selectionInDto = inDto.getBody();
        log.debug("账单查询开始，参数：" + JSON.toJSONString(inDto));
        //PC端查询需要进行数据权限验证
        if (!inDto.getHead().getChanlNo().equals("01")
                && !inDto.getHead().getChanlNo().equals("02")
                && !inDto.getHead().getChanlNo().equals("03")){
            if (StringUtils.isEmpty(selectionInDto.getMenuId())){
                return Result.build(BaseEnum.PARAM_NULL.getCode(),"菜单ID不能为空");
            }
            if (StringUtils.isEmpty(selectionInDto.getRoleId())){
                return Result.build(BaseEnum.PARAM_NULL.getCode(),"角色ID不能为空");
            }
            if (StringUtils.isEmpty(selectionInDto.getDepId())){
                return Result.build(BaseEnum.PARAM_NULL.getCode(),"部门ID不能为空");
            }
            if (StringUtils.isEmpty(selectionInDto.getOperId())){
                return Result.build(BaseEnum.PARAM_NULL.getCode(),"操作人ID不能为空");
            }
            log.info("开始查询数据权限，参数：" +selectionInDto.getRoleId()+ "&&" +selectionInDto.getMenuId());
            String dataRole = null;
            try{
                dataRole = mapper.getDataRole(selectionInDto.getRoleId(), selectionInDto.getMenuId());
                if (StringUtils.isEmpty(dataRole)){
                    return Result.build(307,"查询数据权限失败");
                }
            }catch (Exception e){
                log.error("查询权限异常");
                throw new RuntimeException(e);
            }
            selectionInDto.setDataRole(dataRole);
        }else {
            //默认为机构权限
            selectionInDto.setDataRole("3");
        }
        log.info("账单查询--查询业务开始");
        if (selectionInDto.getPageNum()!=null && selectionInDto.getPageSize()!=null){
            selectionInDto.setPageNum((selectionInDto.getPageNum()-1)*selectionInDto.getPageSize());
        }
        BaseSelectionOutDto<DISP20210307OutDto> outDto = new BaseSelectionOutDto<>();
        Integer tolPageNum = mapper.selectBillsCount(selectionInDto);
        outDto.setTolPageNum(tolPageNum);
        if (tolPageNum ==0){
            return Result.ok(outDto);
        }
        ArrayList<DISP20210307OutDto> result = mapper.selectBills(selectionInDto);

        log.info("查找最近的一条交易流水的posOrderId");
        ArrayList<DISP20210307OutDto> dataList = new ArrayList<>();
        DISP20210307OutDto item;
        for (DISP20210307OutDto data : result){
            item = new DISP20210307OutDto();
            PayJrn payInfo = mapper.getNewestPayInfo(data.getBillNum());
            BeanUtils.copyProperties(data,item);
            if (payInfo != null){
                if (!StringUtils.isEmpty(payInfo.getPosOrderId())){
                    item.setPosOrderId(payInfo.getPosOrderId());
                }
                if(payInfo.getTxnTm()!=null){
                    item.setTxnTm(payInfo.getTxnTm());
                }
            }
            dataList.add(item);
        }
        outDto.setDataList(dataList);
        log.debug("账单查询成功，返回："+ JSON.toJSONString(outDto));
        return Result.ok(outDto);
    }


    /**
     * 账单作废：修改账单状态为“已作废”
     * @author  zhaoshihao
     * @date 2021/8/9
    */
    @Override
    public Result abolishBill(BillUpdateInDto billUpdateInDto) {
        log.debug("账单作废开始，参数："+ JSON.toJSONString(billUpdateInDto));
        if (StringUtils.isEmpty(billUpdateInDto.getOperId())){
            return Result.build(BillResultCodeEnum.PARAM_NULL.getCode(),"操作员ID不能为空");
        }
        if (StringUtils.isEmpty(billUpdateInDto.getOperNm())){
            return Result.build(BillResultCodeEnum.PARAM_NULL.getCode(),"操作员名称不能为空");
        }
        if (StringUtils.isEmpty(billUpdateInDto.getBillNum())){
            return Result.build(BillResultCodeEnum.PARAM_NULL.getCode(),"账单编号不能为空");
        }

        log.info("先做查询，检查账单状态");
        BillSelectionInDto param = new BillSelectionInDto();
        param.setBillNum(billUpdateInDto.getBillNum());
        ArrayList<DISP20210307OutDto> result = mapper.selectBills(param);
        DISP20210307OutDto targetBill = result.get(0);
        log.debug("查询结果：" + JSON.toJSONString(targetBill));
        if (targetBill == null){
            log.info("查询结果为空，返回");
            return Result.build(BillResultCodeEnum.INVALID_BILLNUM.getCode(),BillResultCodeEnum.INVALID_BILLNUM.getDesc());
        }
        if (targetBill.getBillSt().equals(ABOLISHED.getCode())){
            log.info("账单已经是作废状态，返回");
            return Result.build(BillResultCodeEnum.BILL_IS_ABOLISHED.getCode(),BillResultCodeEnum.BILL_IS_ABOLISHED.getDesc());
        }
        if (!StringUtils.isEmpty(targetBill.getPaymentSt())){
            if (!targetBill.getPaymentSt().equals(NOT_PAY.getCode())){
                log.info("账单已经支付，不能作废，返回");
                return Result.build(BillResultCodeEnum.BILL_IS_PAYED.getCode(),BillResultCodeEnum.BILL_IS_PAYED.getDesc());
            }
        }

        Integer i = mapper.abolishBill(billUpdateInDto);
        if (i<1){
            log.error("更新数据库失败");
            return Result.build(BillResultCodeEnum.UPDATE_FAIL.getCode(),"更新数据库失败");
        }
        return Result.ok();
    }

    @Override
    public Result selectPayItems(BillSelectionInDto selectionInDto) {
        if(StringUtils.isEmpty(selectionInDto.getDepId())){
            log.error("部门ID为空");
            return Result.build(BillResultCodeEnum.PARAM_NULL.getCode(),"部门ID不能为空");
        }
        ArrayList<BillPayItemsOutDto> payItemsOutDtos = mapper.selectPayItems(selectionInDto);
        return Result.ok(payItemsOutDtos);
    }

    @Override
    public Result updateBillPayStatus(DISP20210332InDto inDto) {
        log.info("账单支付状态修改开始，传入参数："+ JSON.toJSONString(inDto));
        if (StringUtils.isEmpty(inDto.getBillNum())){
            return Result.build(BillResultCodeEnum.PARAM_NULL.getCode(),"账单编号不能为空");
        }
        if (StringUtils.isEmpty(inDto.getOperId())){
            return Result.build(BillResultCodeEnum.PARAM_NULL.getCode(),"操作员编号不能为空");
        }
        if (StringUtils.isEmpty(inDto.getOperNm())){
            return Result.build(BillResultCodeEnum.PARAM_NULL.getCode(),"操作员名称不能为空");
        }
        if (StringUtils.isEmpty(inDto.getPaymentSt())){
            return Result.build(BillResultCodeEnum.PARAM_NULL.getCode(),"支付状态不能为空");
        }
        if (!SUCCESS.getCode().equals(inDto.getPaymentSt()) && !NOT_PAY.getCode().equals(inDto.getPaymentSt())){
            return Result.build(BillResultCodeEnum.INVALID_PARAM.getCode(),"支付状态不合法");
        }
        try{
            mapper.updateBillPayStatus(inDto);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return Result.ok();
    }

    /**
     * 账单导出
     * @author  zhaoshihao
     * @date 2021/11/10
    */
    @Override
    public Result exportBills(Request<BillSelectionInDto> inDto) {
        BillSelectionInDto selectionInDto = inDto.getBody();
        inDto.getBody().setPageNum(0);
        inDto.getBody().setPageSize(maxExportNum);
        log.debug("账单导出开始，参数：" + JSON.toJSONString(selectionInDto));
        if (StringUtils.isEmpty(selectionInDto.getMenuId())){
            return Result.build(BaseEnum.PARAM_NULL.getCode(),"菜单ID不能为空");
        }
        log.info("账单导出，开始进行数据权限验证");
        //PC端查询需要进行数据权限验证
        if (!inDto.getHead().getChanlNo().equals("01")
                && !inDto.getHead().getChanlNo().equals("02")
                && !inDto.getHead().getChanlNo().equals("03")){

            if (StringUtils.isEmpty(selectionInDto.getRoleId())){
                return Result.build(BaseEnum.PARAM_NULL.getCode(),"角色ID不能为空");
            }
            if (StringUtils.isEmpty(selectionInDto.getDepId())){
                return Result.build(BaseEnum.PARAM_NULL.getCode(),"部门ID不能为空");
            }
            if (StringUtils.isEmpty(selectionInDto.getOperId())){
                return Result.build(BaseEnum.PARAM_NULL.getCode(),"操作人ID不能为空");
            }
            log.info("开始查询数据权限，参数：" +selectionInDto.getRoleId()+ "&&" +selectionInDto.getMenuId());
            String dataRole = null;
            try{
                dataRole = mapper.getDataRole(selectionInDto.getRoleId(), selectionInDto.getMenuId());
                if (StringUtils.isEmpty(dataRole)){
                    return Result.build(307,"查询数据权限失败");
                }
            }catch (Exception e){
                log.error("查询权限异常");
                throw new RuntimeException(e);
            }
            selectionInDto.setDataRole(dataRole);
        }else {
            //默认为机构权限
            selectionInDto.setDataRole("3");
        }
        log.info("账单导出，数据权限验证成功");

        log.info("账单导出业务开始，参数：" + JSON.toJSONString(inDto));
        selectionInDto.setPageSize(null);
        selectionInDto.setPageNum(null);
        ArrayList<DISP20210307OutDto> outList;
        try {
            outList = mapper.selectBills(selectionInDto);
        }catch (Exception e){
            log.error("查询异常");
            throw new RuntimeException(e);
        }
        log.info("账单查询成功，开始生成xls文件");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("账单数据表");
        XSSFRow headRow = sheet.createRow(0);
        String[] str = {"账单编号","客户名称","客户电话","缴费项目","实付金额（元）","账单状态",
                "支付状态","支付方式","收款部门","操作人","支付时间","开始日期","结束日期","创建时间","备注"};
        for (int i=0; i<str.length; i++){
            headRow.createCell(i).setCellValue(str[i]);
        }

        XSSFRow row;
        int rowIndex = 1;
        for (DISP20210307OutDto data : outList){
            row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(data.getBillNum());
            row.createCell(1).setCellValue(data.getProvNm());
            row.createCell(2).setCellValue(data.getTelephone());
            row.createCell(3).setCellValue(data.getPayItem());
            row.createCell(4).setCellValue(data.getRecvAmt().toString());
            row.createCell(5).setCellValue(billItemTrans(data.getBillSt(),"billSt"));
            row.createCell(6).setCellValue(billItemTrans(data.getPaymentSt(),"paymentSt"));
            row.createCell(7).setCellValue(billItemTrans(data.getPaymentMode(),"paymentMode"));
            row.createCell(8).setCellValue(data.getDepNm());
            row.createCell(9).setCellValue(data.getOperNm());
            if (data.getPayTime() != null){
                row.createCell(10).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data.getPayTime()));
            }
            if (data.getStartDt() != null){
                row.createCell(11).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(data.getStartDt()));
            }
            if (data.getEndDt() != null){
                row.createCell(12).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(data.getEndDt()));
            }
            if (data.getCreatTime() != null){
                row.createCell(13).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data.getCreatTime()));
            }
            row.createCell(14).setCellValue(data.getRemark());
            rowIndex++;
        }
        //宽度自适应大小
        for (int z = 0; z < str.length; z++) {
            sheet.autoSizeColumn(z);
        }
        String fileName = (new Date()).toString() + ".xls";
        File file = new File(localPath + "/" +fileName);
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
        log.info("订单导出-文件上传成功，返回：" + result.getData());
        return Result.ok((String)result.getData());
    }

    /**
     * 账单催缴
     * @author  zhaoshihao
     * @date 2021/11/11
    */
    @Override
    public Result noticeBills(Request<DISP20210375InDto> inDto) {
        log.info("账单催缴交易开始，参数:" + JSON.toJSONString(inDto));

        ArrayList<DISP20210307OutDto> result;
        BillSelectionInDto selection = new BillSelectionInDto();
        selection.setBillNum(inDto.getBody().getBillNum());
        selection.setPaymentSt(NOT_PAY.getCode());
        selection.setBillSt(NORMAL.getCode());
        selection.setDataRole("3");  //默认机构权限
        result = mapper.selectBills(selection);

        log.info("账单催缴-开始调取推送提醒交易");
        TPushNotesVo newPush;
        Request request;
        ArrayList<String> pushErrorList = new ArrayList<>();
        ArrayList<String> smsErrorList = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        for (DISP20210307OutDto res : result){
            log.info("账单号" + res.getBillNum() + "-开始催缴");
            newPush = new TPushNotesVo();
            newPush.setBusineNo(res.getBillNum());
            newPush.setProvId(res.getProvId());
            newPush.setParameterType(7);
            ArrayList<String> parameterList = new ArrayList();
            parameterList.add(res.getPayItem());
            parameterList.add(res.getActRecevAmt().toString());
            newPush.setParameterList(parameterList);

            request = new Request();
            request.setHead(inDto.getHead());
            request.getHead().setReqSeqNo(df.format(new Date()) + (int)(Math.random()*10));
            request.setBody(newPush);
            Result newsResp = null;
            try {
                newsResp = restTemplate2.postForObject("http://" + "disPart-busine-commons" + "/securityCenter/DISP20210310", request, Result.class);
            }catch (Exception e){
                log.error("账单号" + res.getBillNum() +"-请求催缴信息推送异常");
                throw new RuntimeException(e);
            }
            if (newsResp.getCode() != 200) {
                log.error("账单号" + res.getBillNum() +"-请求催缴信息推送失败");
                pushErrorList.add(res.getBillNum());
            }else {
                log.info("账单号" + res.getBillNum() +"-请求催缴信息推送成功");
            }
            log.info("账单催缴-开始调取短信提醒交易");
            Request request1 = new Request();
            request1.setHead(inDto.getHead());
            BillSMSNoticeDto dto = new BillSMSNoticeDto();
            dto.setType("2");
            dto.setTel(res.getTelephone());
            dto.setCost(res.getActRecevAmt().toString());
            dto.setParam1(res.getPayItem());
            request1.setBody(dto);
            Result smsResp = null;
            try{
                smsResp = restTemplate2.postForObject("http://" + "disPart-busine-commons" + "/securityCenter/DISP20210078", request1, Result.class);
            }catch (Exception e){
                log.error("账单号" + res.getBillNum() +"-请求短信催缴异常");
                throw new RuntimeException(e);
            }
            if (smsResp.getCode() != 200) {
                log.error("账单号" + res.getBillNum() + "-请求短信催缴失败");
                smsErrorList.add(res.getBillNum());
            }else {
                log.error("账单号" + res.getBillNum() + "-请求短信催缴成功");
            }
        }
        log.info("消息推送失败项账单号：" + JSON.toJSONString(pushErrorList));
        log.info("消息推送失败项账单号：" + JSON.toJSONString(smsErrorList));
        log.info("账单催缴交易结束");
        return Result.ok();
    }

    /**
     * 账单字段类型转换
     * @author  zhaoshihao
     * @date 2021/11/10
    */
    private String billItemTrans(String value,String param){
        if ("billSt".equals(param)){
            if ("0".equals(value)){ return "正常"; }
            if ("1".equals(value)){ return "作废"; }
        }
        if ("paymentSt".equals(param)){
            if ("9".equals(value)){ return "未支付"; }
            if ("0".equals(value)){ return "支付处理中"; }
            if ("1".equals(value)){ return "支付失败"; }
            if ("2".equals(value)){ return "支付成功"; }
            if ("3".equals(value)){ return "支付结果未知"; }
            if ("4".equals(value)){ return "退款处理中"; }
            if ("5".equals(value)){ return "退款成功"; }
        }
        if ("paymentMode".equals(param)){
            if ("1".equals(value)){ return "惠市宝"; }
            if ("2".equals(value)){ return "现金"; }
            if ("3".equals(value)){ return "pos机银行卡"; }
            if ("4".equals(value)){ return "一卡通"; }
            if ("5".equals(value)){ return "银行卡"; }
            if ("6".equals(value)){ return "pos机二维码"; }
            if ("7".equals(value)){ return "冲正"; }
            if ("8".equals(value)){ return "其他充值"; }
        }
        return "";
    }

}
