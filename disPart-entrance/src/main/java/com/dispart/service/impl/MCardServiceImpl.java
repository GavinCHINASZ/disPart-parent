package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.baseDto.BaseSelectionOutDto;
import com.dispart.dao.MCardInfoMapper;
import com.dispart.dao.VechicleMonthPayDetailsMapper;
import com.dispart.dto.MCardInfoDto.*;
import com.dispart.dto.dataquery.Disp20210069OutMx;
import com.dispart.enums.MCardEnum;
import com.dispart.model.MCardInfo;
import com.dispart.model.VechicleMonthPayDetails;
import com.dispart.result.MCardServiceEnum;
import com.dispart.result.Result;
import com.dispart.service.MCardService;
import com.dispart.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import static com.dispart.enums.bill.BillPaymentSt.*;
import static com.dispart.enums.bill.BillStatus.*;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class MCardServiceImpl implements MCardService {

    @Autowired
    private MCardInfoMapper mapper;

    @Autowired
    private VechicleMonthPayDetailsMapper payDetailsMapper;

    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate2;

    @Value("${mcardPayOrder.mcardPayOrderFileRemotePath}")
    private String remotePath;

    @Value("${mcardPayOrder.mcardPayOrderFileLocalPath}")
    private String localPath;

    /**
     * 月卡信息录入
     *
     * @author zhaoshihao
     * @date 2021/8/9
     */
    @Override
    @Transactional
    public Result addMCardInfo(AddMCardInfoInDto info) {
        log.info("月卡信息录入开始，传入参数：" + JSON.toJSONString(info));
        if (StringUtils.isEmpty(info.getVehicleNum())) {
            return Result.build(MCardServiceEnum.PARAM_NULL.getCode(), "车牌号不能为空");
        }
        if (StringUtils.isEmpty(info.getMcardTp())) {
            return Result.build(MCardServiceEnum.PARAM_NULL.getCode(), "月卡类型不能为空");
        }
        if (StringUtils.isEmpty(info.getProvId())) {
            return Result.build(MCardServiceEnum.PARAM_NULL.getCode(), "客户ID不能为空");
        }
        if (info.getMcardTp().equals("0")) {
            if (StringUtils.isEmpty(info.getInNum())) {
                return Result.build(MCardServiceEnum.PARAM_NULL.getCode(), "办理普通月卡，进场口编号不能为空");
            }
            if (StringUtils.isEmpty(info.getOutNum())) {
                return Result.build(MCardServiceEnum.PARAM_NULL.getCode(), "办理普通月卡，出场口编号不能为空");
            }
        }
        if (StringUtils.isEmpty(info.getStartDt())) {
            return Result.build(MCardServiceEnum.PARAM_NULL.getCode(), "开始日期不能为空");
        }
        if (StringUtils.isEmpty(info.getDueDt())) {
            return Result.build(MCardServiceEnum.PARAM_NULL.getCode(), "到期时间不能为空");
        }
        if (info.getPayAmt() == null) {
            return Result.build(MCardServiceEnum.PARAM_NULL.getCode(), "应付金额不能为空");
        }
        if (info.getPreferPrice() == null) {
            return Result.build(MCardServiceEnum.PARAM_NULL.getCode(), "优惠金额不能为空");
        }
        if (info.getRecvAmt() == null) {
            return Result.build(MCardServiceEnum.PARAM_NULL.getCode(), "实付金额不能为空");
        }
        if (info.getPayAmt().compareTo(new BigDecimal("0.00"))<0){
            return Result.build(MCardServiceEnum.PARAM_NULL.getCode(), "应付金额错误");
        }
        if (info.getPreferPrice().compareTo(new BigDecimal("0.00"))<0){
            return Result.build(MCardServiceEnum.PARAM_NULL.getCode(), "优惠金额错误");
        }
        if (info.getPayAmt().compareTo(new BigDecimal("0.00"))<0){
            return Result.build(MCardServiceEnum.PARAM_NULL.getCode(), "实付金额错误");
        }
        MCardInfoInsertDto mcardInfo = new MCardInfoInsertDto();
        McardPayDetailInsertDto payDetails = new McardPayDetailInsertDto();
        String mCardNum;  //月卡账单生成绑定的月卡号

        //检查是否有该车牌的月卡信息存在，有的话若为作废状态则进行更新，若不是则返回
        MCardInfoSelectionInDto selectionInDto = new MCardInfoSelectionInDto();
        selectionInDto.setVehicleNum(info.getVehicleNum());
        ArrayList<MCardInfo> mCardInfos = mapper.selectMcardInfo(selectionInDto);
        if (mCardInfos.size() > 0) {
            MCardInfo res = mCardInfos.get(0);
            if (res.getStatus().equals("8")){  //8-作废
                mCardNum = res.getMcardNum();  //将已存在的月卡号给月卡账单作绑定
                MCardInfoInsertDto insertDto = new MCardInfoInsertDto();
                BeanUtils.copyProperties(info,insertDto);
                insertDto.setMcardNum(mCardNum);
                //若实付金额为0，则修改月卡状态为已支付
                if (info.getRecvAmt().compareTo(new BigDecimal(0.00)) == 0){
                    mcardInfo.setStatus("2");
                }else {
                    insertDto.setStatus("9");  //9-未支付
                }
                try{
                    mapper.updateByPrimaryKeySelective(insertDto);
                }catch (Exception e){
                    log.error("月开录入，更新作废月开信息异常");
                    throw new RuntimeException(e);
                }
            }else {
                return Result.build(308,"该月卡信息已经存在");
            }
        }else{
            //开始新增月卡信息
            int key = mapper.getMcardNum();
            mCardNum = "N" + DateUtil.getDateString() + String.format("%07d", key);
            info.setMcardNum(mCardNum);
            BeanUtils.copyProperties(info, mcardInfo);
            mcardInfo.setStatus(NOT_PAY.getCode());
            //若实付金额为0，则修改月卡状态为已支付
            if (info.getRecvAmt().compareTo(new BigDecimal(0.00)) == 0){
                mcardInfo.setStatus("2");
            }
            mapper.insertSelective(mcardInfo);
        }

        //开始生成月卡缴费账单
        int keySequence = payDetailsMapper.getMonthPayOrder();
        String payOrder = "M" + DateUtil.getDateString() + String.format("%07d", keySequence);
        BeanUtils.copyProperties(info, payDetails);
        payDetails.setProvId(info.getProvId());
        payDetails.setPayStDt(info.getStartDt());
        payDetails.setPayDeadline(info.getDueDt());
        payDetails.setStatus(NORMAL.getCode());
        payDetails.setPaymentSt(NOT_PAY.getCode());
        payDetails.setPayOrder(payOrder);
        payDetails.setMcardNum(mCardNum);

        //若实付金额为0，则修改账单状态为已支付
        if (info.getRecvAmt().compareTo(new BigDecimal(0.00)) == 0){
            payDetails.setPaymentSt(SUCCESS.getCode());
        }
        payDetailsMapper.insertSelective(payDetails);
        AddMCardInfoOutDto outDto = new AddMCardInfoOutDto();
        outDto.setMcardNum(mCardNum);
        outDto.setPayOrder(payOrder);
        log.info("--月卡录入交易完成--");
        return Result.ok(outDto);
    }

    /**
     * 月卡信息查询
     *
     * @author zhaoshihao
     * @date 2021/8/9
     */
    @Override
    public Result selectMCardInfo(MCardInfoSelectionInDto inDto) {

        log.info("--月卡信息查询开始,传入参数：" + JSON.toJSONString(inDto));
        if (inDto.getPageNum() != null && inDto.getPageSize() != null) {
            inDto.setPageNum((inDto.getPageNum() - 1) * inDto.getPageSize());
        }

        BaseSelectionOutDto<DISP20210220OutDto> outDto = new BaseSelectionOutDto<>();
        List<MCardInfo> mcardList;
        Integer tolPageNum = mapper.selectMcardInfoCount(inDto);
        if (tolPageNum < 1) {
            outDto.setTolPageNum(0);
            return Result.ok(outDto);
        }
        mcardList = mapper.selectMcardInfo(inDto);

        List<DISP20210220OutDto> dataList = new ArrayList<>();
        DISP20210220OutDto data;
        for (MCardInfo mcard : mcardList) {
            data = new DISP20210220OutDto();
            VechicleMonthPayDetails newMonthDetail = payDetailsMapper.getNewMonthDetail(mcard.getMcardNum());
            BeanUtils.copyProperties(mcard, data);
            if (newMonthDetail != null) {
                data.setPayOrder(newMonthDetail.getPayOrder());
                data.setRecvAmt(newMonthDetail.getRecvAmt());
                data.setPaymentTime(newMonthDetail.getPaymentTime());
            }
            log.info("转换进场口名称");
            if (!StringUtils.isEmpty(mcard.getInNum())){
                StringBuffer inNum = new StringBuffer();
                String[] inNums = mcard.getInNum().split(",");
                for (String deviceNo : inNums){
                    String name = mapper.getInOutNm(deviceNo);
                    inNum.append(name).append(",");
                }
                inNum.deleteCharAt(inNum.lastIndexOf(","));
                data.setInNum(inNum.toString());
            }else{
                data.setInNum("");
            }

            log.info("转换出场口名称");
            if (!StringUtils.isEmpty(mcard.getOutNum())){
                StringBuffer outNum = new StringBuffer();
                String[] outNums = mcard.getOutNum().split(",");
                for (String deviceNo : outNums){
                    String name = mapper.getInOutNm(deviceNo);
                    outNum.append(name).append(",");
                }
                outNum.deleteCharAt(outNum.lastIndexOf(","));
                data.setOutNum(outNum.toString());
            }
            dataList.add(data);
        }

        outDto.setTolPageNum(tolPageNum);
        outDto.setDataList(dataList);
        log.info("--月卡信息查询结束，返回：" + JSON.toJSONString(outDto));
        return Result.ok(outDto);
    }

    /**
     * 月卡信息作废
     * 由于车牌号是唯一的，这里直接进行物理删除
     * @author zhaoshihao
     * @date 2021/8/9
     */
    @Override
    @Transactional
    public Result abolishMCardInfo(MCardInfoInsertDto inDto) {
        log.info("月卡信息作废开始,传入参数：" + JSON.toJSONString(inDto));
        if (StringUtils.isEmpty(inDto.getMcardNum())) {
            return Result.build(MCardServiceEnum.PARAM_NULL.getCode(), "月卡编号不能为空");
        }
        if (StringUtils.isEmpty(inDto.getOperId())){
            return Result.build(MCardServiceEnum.PARAM_NULL.getCode(), "操作员ID不能为空");
        }
        inDto.setStatus("8");
        try{
            mapper.updateByPrimaryKeySelective(inDto);
        }catch (Exception e){
            log.error("月卡作废--数据库更新异常");
            throw new RuntimeException(e);
        }
        log.info("--月卡信息作废，结束--");
        return Result.ok();
    }

    /**
     * 月卡续费
     *
     * @author zhaoshihao
     * @date 2021/8/9
     */
    @Override
    @Transactional
    public Result updateMCardInfo(McardPayDetailUpdateInDto info) {
        log.info("月卡续费开始，传入参数：" + JSON.toJSONString(info));
        if (StringUtils.isEmpty(info.getMcardNum())) {
            return Result.build(MCardServiceEnum.PARAM_NULL.getCode(), "月卡单号不能为空");
        }
        if (StringUtils.isEmpty(info.getPayStDt())) {
            return Result.build(MCardServiceEnum.PARAM_NULL.getCode(), "开始日期不能为空");
        }
        if (StringUtils.isEmpty(info.getPayDeadline())) {
            return Result.build(MCardServiceEnum.PARAM_NULL.getCode(), "截止日期不能为空");
        }
        if (info.getRecvAmt() == null){
            return Result.build(MCardServiceEnum.PARAM_NULL.getCode(), "实付金额不能为空");
        }
        if (info.getRecvAmt().compareTo(new BigDecimal(0.00)) < 0){
            return Result.build(MCardServiceEnum.INVALID_AMT.getCode(), "金额错误");
        }

        MCardInfoSelectionInDto select = new MCardInfoSelectionInDto();
        select.setMcardNum(info.getMcardNum());
        ArrayList<MCardInfo> mCardInfos = mapper.selectMcardInfo(select);

        if (mCardInfos.size()>0){
            if (mCardInfos.get(0).getStatus().equals(NOT_PAY.getCode())){
                return Result.build(MCardEnum.IS_NOT_PAY.getCode(),MCardEnum.IS_NOT_PAY.getMsg());
            }
            if (StringUtils.isEmpty(mCardInfos.get(0).getProvId())){
                return Result.build(MCardServiceEnum.NO_PROV_ID.getCode(),MCardServiceEnum.NO_PROV_ID.getMessage());
            }
        }else {
            return Result.build(312,"无效的月卡单号");
        }

        log.info("开始生成月卡缴费账单");
        McardPayDetailInsertDto payDetails = new McardPayDetailInsertDto();
        int keySequence = payDetailsMapper.getMonthPayOrder();
        String payOrder = "M" + DateUtil.getDateString() + String.format("%07d", keySequence);
        BeanUtils.copyProperties(info, payDetails);
        payDetails.setStatus(NORMAL.getCode());
        payDetails.setPaymentSt(NOT_PAY.getCode());
        payDetails.setPayOrder(payOrder);
        payDetails.setMcardNum(info.getMcardNum());
        payDetails.setStatus(NOT_PAY.getCode());

        if (info.getRecvAmt().compareTo(new BigDecimal(0.00)) == 0 ){
            log.info("续费月卡,金额为零，直接更新月卡生效时间");
            MCardInfoInsertDto mCardInfoInsertDto = new MCardInfoInsertDto();
            mCardInfoInsertDto.setMcardNum(info.getMcardNum());
            mCardInfoInsertDto.setStartDt(info.getPayStDt());
            mCardInfoInsertDto.setDueDt(info.getPayDeadline());
            mCardInfoInsertDto.setOperId(info.getOperId());
            mapper.updateByPrimaryKeySelective(mCardInfoInsertDto);
            //账单金额为零，则默认支付状态为已支付
            payDetails.setPaymentSt(SUCCESS.getCode());
        }
        payDetailsMapper.insertSelective(payDetails);
        AddMCardInfoOutDto outDto = new AddMCardInfoOutDto();
        outDto.setPayOrder(payOrder);
        outDto.setMcardNum(info.getMcardNum());
        log.info("--月卡续费交易结束--");
        return Result.ok(outDto);
    }


    /**
     * 月卡账单查询
     *
     * @author zhaoshihao
     * @date 2021/8/9
     */
    @Override
    public Result selectMCardPayDetails(MCardInfoSelectionInDto inDto) {
        if (inDto.getPageNum() != null && inDto.getPageSize() != null) {
            inDto.setPageNum((inDto.getPageNum() - 1) * inDto.getPageSize());
        }
        log.info("月卡账单查询开始，参数：" + JSON.toJSONString(inDto));
        BaseSelectionOutDto outDto = new BaseSelectionOutDto();
        Integer tolPageNum = payDetailsMapper.getMonthPayDetailsCount(inDto);
        outDto.setTolPageNum(tolPageNum);
        if (tolPageNum < 1) {
            return Result.ok(outDto);
        }
        ArrayList<DISP20210307OutDto> monthPayDetails = payDetailsMapper.getMonthPayDetails(inDto);
        outDto.setDataList(monthPayDetails);
        log.info("月卡账单查询结束，返回：" + JSON.toJSONString(outDto));
        return Result.ok(outDto);
    }

    /**
     * 月卡账单导出
     * @author  zhaoshihao
     * @date 2021/11/10
    */
    @Override
    public Result exportMCardPayDetails(MCardInfoSelectionInDto inDto){
        log.info("月卡账单导出业务开始，参数：" + JSON.toJSONString(inDto));
        inDto.setPageSize(50000);
        inDto.setPageNum(0);
        ArrayList<DISP20210307OutDto> outList;
        try {
            outList = payDetailsMapper.getMonthPayDetails(inDto);
        }catch (Exception e){
            log.error("查询异常");
            throw new RuntimeException(e);
        }
        log.info("月卡账单查询成功，开始生成xls文件");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("月卡账单数据表");
        XSSFRow headRow = sheet.createRow(0);
        String[] str = {"序号","车牌号","月卡类型","月卡单号","客户名称","开卡日期",
                "开始日期","到期日期","账单号","应付金额（元）","优惠金额（元）","实付金额（元）",
                "支付方式","支付状态","操作人","备注"};
        for (int i=0; i<str.length; i++){
            headRow.createCell(i).setCellValue(str[i]);
        }

        XSSFRow row;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        int rowIndex = 1;
        for (DISP20210307OutDto data : outList){
            row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(rowIndex);
            row.createCell(1).setCellValue(mcardInfoTrans(data.getMcardTp(),"mcardTp"));
            row.createCell(2).setCellValue(data.getMcardNum());
            row.createCell(3).setCellValue(data.getProvNm());
            row.createCell(4).setCellValue(df.format(data.getCreatTime()));
            row.createCell(5).setCellValue(df1.format(data.getPayStDt()));
            row.createCell(6).setCellValue(df1.format(data.getPayDeadline()));
            row.createCell(7).setCellValue(data.getPayOrder());
            row.createCell(8).setCellValue(data.getPayAmt().toString());
            row.createCell(9).setCellValue(data.getPreferPrice().toString());
            row.createCell(10).setCellValue(data.getRecvAmt().toString());
            row.createCell(11).setCellValue(mcardInfoTrans(data.getPaymentMode(),"paymentMode"));
            row.createCell(12).setCellValue(mcardInfoTrans(data.getPaymentSt(),"payStatus"));
            row.createCell(13).setCellValue(data.getRemark());
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



    @Transactional
    @Override
    public Result updateMCardPayDetailStatus(DISP20210331InDto inDto) {
        log.info("修改未知交易状态开始, 传入参数：" + JSON.toJSONString(inDto));
        if (StringUtils.isEmpty(inDto.getPayOrder())){
            return Result.build(MCardServiceEnum.PARAM_NULL.getCode(),"月卡账单号不能为空");
        }
        if (StringUtils.isEmpty(inDto.getPaymentSt())){
            return Result.build(MCardServiceEnum.PARAM_NULL.getCode(),"状态值不能为空");
        }
        if (!"2".equals(inDto.getPaymentSt()) && !"9".equals(inDto.getPaymentSt())){
            return Result.build(MCardServiceEnum.INVALID_ST_VAL.getCode(),MCardServiceEnum.INVALID_ST_VAL.getMessage());
        }

        try{
            payDetailsMapper.updateMCardPayDetailStatus(inDto);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return Result.ok();
    }

    /**
     * 转换月卡账单字段
     * @author  zhaoshihao
     * @date 2021/11/10
    */
    private String mcardInfoTrans(String value, String param){
        if ("mcardTp".equals(param)){
            if ("0".equals(value)){ return "普通月卡"; }
            if ("1".equals(value)){ return "超级月卡"; }
            if ("2".equals(value)){ return "免费月卡"; }
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
        if ("payStatus".equals(param)){
            if ("9".equals(value)){ return "未支付"; }
            if ("0".equals(value)){ return "支付处理中"; }
            if ("1".equals(value)){ return "支付失败"; }
            if ("2".equals(value)){ return "支付成功"; }
            if ("3".equals(value)){ return "支付结果未知"; }
            if ("4".equals(value)){ return "退款处理中"; }
            if ("5".equals(value)){ return "退款成功"; }
        }
        return "";
    }
}
