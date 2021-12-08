package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.baseDto.BaseSelectionOutDto;
import com.dispart.dao.OrderQueryMapper;
import com.dispart.dto.customdto.ExportCustomInfoOutDto;
import com.dispart.dto.dataquery.*;
import com.dispart.entity.DatabaseCount;
import static com.dispart.enums.BaseEnum.*;
import static com.dispart.result.ResultCodeEnum.SUCCESS;
import static com.dispart.result.UserResultCodeEnum.USER_UPLOAD_FILE_FAIL;

import com.dispart.model.businessCommon.TransMdEnum;
import com.dispart.model.order.OrderStatusEnum;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.OrderInfoQueryService;
import com.dispart.utils.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OrderInfoQueryServiceImpl implements OrderInfoQueryService {

    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate2;

    @Value("${orderFilePath}")
    private String orderFilePath;

    @Value("${exportMaxItemNum}")
    private Integer exportMaxItemNum;

    @Resource
    OrderQueryMapper orderQueryMapper;

    @Override
    public Result<Disp20210069OutDto> quryOrderInfo(Disp20210069InDto param) {

        log.info("订单查询开始执行，传入参数：{}",JSON.toJSONString(param));
        Result<Disp20210069OutDto> result = null;

        if (ObjectUtils.isEmpty(param)) {
            result = Result.build(null, ResultCodeEnum.PARAM_ERROR);
            return result;
        }

        if (StringUtils.isEmpty(param.getCurPage()) || StringUtils.isEmpty(param.getPageSize())) {
            result = Result.build(null, ResultCodeEnum.PARAM_ERROR);
            return result;
        }

        Disp20210069OutDto outDto = new Disp20210069OutDto();
        //若按一级货品ID作为查询条件,查找该品种子类
        if(!StringUtils.isEmpty(param.getPrdctTypeId())){
            //先查询所有的分类
            List<String> secondLevelTypeId;
            try{
                secondLevelTypeId = orderQueryMapper.queryDownLevelPrdctType(param.getPrdctTypeId());
            }catch (Exception e){
                log.error("查询货品下一级分类异常");
                throw new RuntimeException(e);
            }
            if (secondLevelTypeId.size()>0){
                param.setPrdctTypeIdList(secondLevelTypeId);
            }else {
                outDto.setRecNum(new Long(0));
                return Result.ok(outDto);
            }
        }

        param.setStartIndex((param.getCurPage()-1)*param.getPageSize());
        DatabaseCount databaseCount;
        try{
            databaseCount = orderQueryMapper.quryOrderInfo_count(param);
        }catch (Exception e){
            log.error("获取订单总条数异常");
            throw new RuntimeException(e);
        }
        outDto.setRecNum(databaseCount.getCountRec().longValue());
        outDto.setTotalAmt(databaseCount.getTolAmt());
        if (databaseCount.getCountRec().longValue() < 1) {
            log.info("订单查询结束，返回结果：{}", JSON.toJSONString(outDto));
            return Result.build(outDto, ResultCodeEnum.SUCCESS);
        }

        ArrayList<Disp20210069OutMx> outList;
        try {
            outList = orderQueryMapper.quryOrderInfo(param);
        }catch (Exception e){
            log.error("订单查询异常");
            throw new RuntimeException(e);
        }
        outDto.setList(outList);
        result = Result.build(outDto, ResultCodeEnum.SUCCESS);
        log.info("订单查询结束，返回结果：{}", JSON.toJSONString(outDto));
        return result;
    }

    @Override
    public Result<BaseSelectionOutDto> getOrderGoods(Disp20210335InDto inDto) {
        log.info("订单商品查询开始，参数：" + JSON.toJSONString(inDto));
        if (StringUtils.isEmpty(inDto.getOrderId())){
            return Result.build(PARAM_NULL.getCode(),"订单ID不能为空");
        }
        if((inDto.getPageNum() != null && inDto.getPageSize() != null)){
            inDto.setPageNum((inDto.getPageNum()-1) * inDto.getPageSize());
        }
        BaseSelectionOutDto outDto = new BaseSelectionOutDto();
        Integer tolPageNum = orderQueryMapper.getOrderGoodsCount(inDto);
        outDto.setTolPageNum(tolPageNum);
        if (tolPageNum == 0){
            return Result.ok(outDto);
        }
        ArrayList<Disp20210335OutDto> dataList = orderQueryMapper.getOrderGoods(inDto);
        outDto.setDataList(dataList);
        log.info("订单商品查询结束，返回：" + JSON.toJSONString(outDto));
        return Result.ok(outDto);
    }

    /**
     * 导出订单查询的结果，保持和查询的条件一致
     * @author  zhaoshihao
     * @date 2021/10/29
    */
    @Override
    public Result exportData(Disp20210069InDto param) {
        log.info("订单导出业务开始，参数：" + JSON.toJSONString(param));
        param.setPageSize((long) exportMaxItemNum);
        param.setStartIndex((long) 0);
        ArrayList<Disp20210069OutMx> outList;
        try {
            outList = orderQueryMapper.quryOrderInfo(param);
        }catch (Exception e){
            log.error("订单查询异常");
            throw new RuntimeException(e);
        }
        log.info("订单查询成功，开始生成xls文件");
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("订单数据表");
        HSSFRow headRow = sheet.createRow(0);
        String[] str = {"编号","主订单号","子订单号","供应商名称","采购商名称","订单金额(元)",
                "优惠金额(元)","实付金额(元)","付款方式","采购商手机号","订单状态","下单模式","下单日期"};
        for (int i=0; i<str.length; i++){
            headRow.createCell(i).setCellValue(str[i]);
        }

        HSSFRow row;
        int rowIndex = 1;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Disp20210069OutMx data : outList){
            row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(rowIndex);
            row.createCell(1).setCellValue(data.getMainOrderId());
            row.createCell(2).setCellValue(data.getOrderId());
            row.createCell(3).setCellValue(data.getProvNm());
            row.createCell(4).setCellValue(data.getUserNm());
            if (data.getPrdctAmt()!= null){
                row.createCell(5).setCellValue(data.getPrdctAmt().toString());
            }
            if (data.getAdditAmt()!=null){
                row.createCell(6).setCellValue(data.getAdditAmt().toString());
            }
            if (data.getTxnAmt() != null){
                row.createCell(7).setCellValue(data.getTxnAmt().toString());
            }
            row.createCell(8).setCellValue(enumTransform("payMentMode",data.getPaymentMode()));
            row.createCell(9).setCellValue(data.getUserPhone());
            row.createCell(10).setCellValue(enumTransform("orderSt",data.getOrderSt()));
            row.createCell(11).setCellValue(enumTransform("orderModel",data.getOrderModel()));
            row.createCell(12).setCellValue(df.format(data.getOrderTm()));
            rowIndex++;
        }

        //宽度自适应大小
        for (int z = 0; z < str.length; z++) {
            sheet.autoSizeColumn(z);
        }

        String fileName = System.currentTimeMillis() + ".xls";
        File file = new File(orderFilePath + "/" +fileName);
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
        String remoteFilePath = "orderFile";
        FileUploadUtil fileUploadUtil = new FileUploadUtil();
        Result result = fileUploadUtil.post(file, remoteFilePath,restTemplate2);
        log.info("充值交易导出-文件上传成功，返回：" + result.getData());
        return result;
    }


    /**
     * 字段code转换
     * @author  zhaoshihao
     * @date 2021/10/29
    */
    private String enumTransform(String enumName, String code){
        String res = "";
        if (enumName.equals("orderSt")){
            switch (code){
                case "1":
                    res = OrderStatusEnum.REFUND_FAIL.getDesc();
                    break;
                case "2":
                    res = OrderStatusEnum.SUCCESS.getDesc();
                    break;
                case "3":
                    res = OrderStatusEnum.FAIL.getDesc();
                    break;
                case "4":
                    res = OrderStatusEnum.ALL_REFUND.getDesc();
                    break;
                case "5":
                    res = OrderStatusEnum.PART_REFUND.getDesc();
                    break;
                case "6":
                    res = OrderStatusEnum.EXPIRE.getDesc();
                    break;
                case "7":
                    res = OrderStatusEnum.FOR_REFUND.getDesc();
                    break;
                case "9":
                    res = OrderStatusEnum.FOR_POLLING.getDesc();
                    break;
                default:
                    res = code;
            }
        }

        if (enumName.equals("orderModel")){
            switch (code){
                case "0":
                    res = "简易模式";
                    break;
                case "1":
                    res = "明细模式";
                    break;
                default:
                    res = code;
            }
        }

        if (enumName.equals("payMentMode")){
            switch (code){
                case "03":
                    res = "银行卡支付";
                    break;
                case "05":
                    res = "微信支付";
                    break;
                case "07":
                    res = "聚合支付";
                    break;
                case "08":
                    res = "龙支付";
                    break;
                default:
                    res = code;
            }
        }

        return res;
    }
}
