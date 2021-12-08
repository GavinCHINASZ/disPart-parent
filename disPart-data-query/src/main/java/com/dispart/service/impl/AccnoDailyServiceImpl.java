package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.baseDto.BaseSelectionOutDto;
import com.dispart.dao.TransactionQueryMapper;
import com.dispart.dto.dataquery.Disp20210336InDto;
import com.dispart.dto.dataquery.Disp20210336OutDto;
import com.dispart.result.Result;
import com.dispart.service.AccnoDailyService;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

@Service
@Slf4j
public class AccnoDailyServiceImpl implements AccnoDailyService {

    @Autowired
    private TransactionQueryMapper mapper;

    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate2;

    @Value("${accnoDailyFilePath}")
    private String accnoDailyFilePath;

    @Override
    public Result<BaseSelectionOutDto> getAccnoDaily(Disp20210336InDto inDto) {
        log.info("日终查询开始，参数：" + JSON.toJSONString(inDto));
        if (inDto.getPageNum() != null && inDto.getPageSize() != null){
            inDto.setPageNum((inDto.getPageNum()-1)*inDto.getPageSize());
        }
        Integer tolPageNum = mapper.getAccnoDailyCount(inDto);
        BaseSelectionOutDto outDto = new BaseSelectionOutDto();
        outDto.setTolPageNum(tolPageNum);
        if (tolPageNum == 0){
            return Result.ok(outDto);
        }
        ArrayList<Disp20210336OutDto> dataList = mapper.getAccnoDaily(inDto);
        outDto.setDataList(dataList);
        log.info("日终查询开始，返回：" + JSON.toJSONString(inDto));
        return Result.ok(outDto);
    }

    @Override
    public Result exportAccnoDaily(Disp20210336InDto inDto) {
        log.info("日终导出业务开始，参数：" + JSON.toJSONString(inDto));
        inDto.setPageSize(null);
        inDto.setPageNum(null);
        ArrayList<Disp20210336OutDto> outList;
        try {
            outList = mapper.getAccnoDaily(inDto);
        }catch (Exception e){
            log.error("查询异常");
            throw new RuntimeException(e);
        }
        log.info("调账交易查询成功，开始生成xls文件");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("调账交易数据表");
        XSSFRow headRow = sheet.createRow(0);
        String[] str = {"编号","结算日期","账户余额(元)","可用余额(元)","冻结金额(元)","日借记金额(元)", "日贷记金额(元)"};
        for (int i=0; i<str.length; i++){
            headRow.createCell(i).setCellValue(str[i]);
        }
        XSSFRow row;
        int rowIndex = 1;
        for (Disp20210336OutDto data : outList){
            row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(rowIndex);
            row.createCell(1).setCellValue(data.getDayDt());
            row.createCell(2).setCellValue(data.getCurrAmt().toString());
            row.createCell(3).setCellValue(data.getAvailBal().toString());
            row.createCell(4).setCellValue(data.getFreezeAmt().toString());
            row.createCell(5).setCellValue(data.getDebitAmt().toString());
            row.createCell(6).setCellValue(data.getCreditAmt().toString());
            rowIndex++;
        }
        //宽度自适应大小
        for (int z = 0; z < str.length; z++) {
            sheet.autoSizeColumn(z);
        }
        final String remotePath = "adjustFile";
        String fileName = (new Date()).toString() + ".xls";
        File file = new File(accnoDailyFilePath + "/" +fileName);
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
        log.info("日终交易导出-文件上传成功，返回：" + result.getData());
        return Result.ok((String)result.getData());
    }
}
