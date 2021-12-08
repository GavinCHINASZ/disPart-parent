package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.dao.CheckQueryMapper;
import com.dispart.dto.MCardInfoDto.DISP20210307OutDto;
import com.dispart.dto.dataquery.Disp20210071InDto;
import com.dispart.dto.dataquery.Disp20210071OutDto;
import com.dispart.dto.dataquery.Disp20210071OutMx;
import com.dispart.entity.DatabaseCount;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.CheckDetailQueryService;
import com.dispart.utils.EnumTransUtil;
import com.dispart.utils.FileUploadUtil;
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
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;


@Service
@Slf4j
public class CheckDetailQueryServiceImpl implements CheckDetailQueryService {

    @Resource
    CheckQueryMapper checkQueryMapper;

    @Value("${checkDetailFilePath}")
    private String checkDetailFilePath;

    @Value("${exportMaxItemNum}")
    private Integer exportMaxItemNum;

    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate2;


    @Override
    public Result<Disp20210071OutDto> quryCheckDetail(Disp20210071InDto param) {

        log.info("对账明细查询开始执行，传入参数：{}", JSON.toJSONString(param));
        Result<Disp20210071OutDto> result = null;

        if(ObjectUtils.isEmpty(param)){
            result = Result.build(null, ResultCodeEnum.PARAM_ERROR);
            return result;
        }

        if(StringUtils.isEmpty(param.getCurPage())||StringUtils.isEmpty(param.getPageSize())){
            result = Result.build(null, ResultCodeEnum.PARAM_ERROR);
            log.info("分页参数不能为空");
            return result;
        }

        param.setStartIndex((param.getCurPage()-1)*param.getPageSize());
        DatabaseCount databaseCount;
        try{
            databaseCount = checkQueryMapper.quryCheckDetail_count(param);
        }catch (Exception e){
            log.error("查询分账明细总条数异常");
            throw new RuntimeException(e);
        }
        Disp20210071OutDto outDto = new Disp20210071OutDto();
        outDto.setRecNum(databaseCount.getCountRec().longValue());
        outDto.setTotalAmt(databaseCount.getTolAmt());
        if (databaseCount.getCountRec().longValue()<1){
            log.info("对账明细查询执行成功，返回: {}",JSON.toJSONString(outDto));
            return Result.build(outDto, ResultCodeEnum.SUCCESS);
        }
        ArrayList<Disp20210071OutMx> outList;
        try{
            outList = checkQueryMapper.quryCheckDetail(param);
        }catch (Exception e){
            log.error("查询分账明细异常");
            throw new RuntimeException(e);
        }
        outDto.setList(outList);

        result= Result.build(outDto, ResultCodeEnum.SUCCESS);
        log.info("对账明细查询执行成功，返回: {}",JSON.toJSONString(outDto));
        return result;
    }

    @Override
    public Result exportCheckDetail(Disp20210071InDto inDto) {
        log.info("分账明细查询导出业务开始，参数：" + JSON.toJSONString(inDto));
        inDto.setPageSize((long)exportMaxItemNum);
        inDto.setStartIndex((long)0);
        ArrayList<Disp20210071OutMx> outList;
        try {
            outList = checkQueryMapper.quryCheckDetail(inDto);
            if (outList.size()==0){
                return Result.build(306,"导出内容为空");
            }
        }catch (Exception e){
            log.error("查询异常");
            throw new RuntimeException(e);
        }
        log.info("分账明细查询成功，开始生成xls文件");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("分账明细表");
        XSSFRow headRow = sheet.createRow(0);
        String[] str = {"编号","供应商编号","供应商名称","业务订单号","业务订单金额（元）","分账订单号",
                "分账金额（元）","支付方式","分账模式","结算日期","分账状态","手续费（元）"};
        for (int i=0; i<str.length; i++){
            headRow.createCell(i).setCellValue(str[i]);
        }

        XSSFRow row;
        int rowIndex = 1;
        for (Disp20210071OutMx data : outList){
            row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(rowIndex);
            row.createCell(1).setCellValue(data.getProvId());
            row.createCell(3).setCellValue(data.getProvNm());
            row.createCell(4).setCellValue(data.getOrderId());
            row.createCell(5).setCellValue(data.getPrdctAmt());
            row.createCell(6).setCellValue(data.getSubOrderId());
            row.createCell(7).setCellValue(data.getPartAmt().toString());
            row.createCell(8).setCellValue(EnumTransUtil.convertPayMethod(data.getPayMethod()));
            row.createCell(9).setCellValue(data.getPartModeNm());
            row.createCell(10).setCellValue(data.getTxnDate());
            row.createCell(11).setCellValue(data.getRespSt());
            row.createCell(12).setCellValue(data.getServChrg().toString());
            rowIndex++;
        }

        //宽度自适应大小
        for (int z = 0; z < str.length; z++) {
            sheet.autoSizeColumn(z);
        }

        String fileName = System.currentTimeMillis() + ".xls";
        File file = new File(checkDetailFilePath + "/" +fileName);
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
        String remotePath = "checkDetailFile";
        FileUploadUtil fileUploadUtil = new FileUploadUtil();
        Result result = fileUploadUtil.post(file, remotePath,restTemplate2);
        return result;
    }
}
