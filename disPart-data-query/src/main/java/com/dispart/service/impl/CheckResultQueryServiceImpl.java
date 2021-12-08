package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.baseDto.BaseSelectionOutDto;
import com.dispart.dao.CheckQueryMapper;
import com.dispart.dao.TReconciliationResultMapper;
import com.dispart.dto.dataquery.Disp20210072InDto;
import com.dispart.dto.dataquery.Disp20210072OutDto;
import com.dispart.dto.dataquery.Disp20210072OutMx;
import com.dispart.dto.dataquery.Disp20210334InDto;
import com.dispart.dto.transactionDto.ChargeQueryOutDto;
import com.dispart.entity.DatabaseCount;
import com.dispart.model.TReconciliationResult;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.CheckResultQueryService;
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
public class CheckResultQueryServiceImpl implements CheckResultQueryService {

    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate2;

    @Resource
    private TReconciliationResultMapper resultMapper;

    @Resource
    private CheckQueryMapper checkQueryMapper;

    @Value("${exportMaxItemNum}")
    private Integer exportMaxItemNum;

    @Value("${checkResultFilePath}")
    private String checkResultFilePath;

    @Override
    public Result<Disp20210072OutDto> quryCheckResult(Disp20210072InDto param) {

        log.info("对账结果明细查询开始执行，传入参数：{}", JSON.toJSONString(param));

        Result<Disp20210072OutDto> result = null;

        if (ObjectUtils.isEmpty(param)) {
            result = Result.build(null, ResultCodeEnum.PARAM_ERROR);
            return result;
        }

        if (StringUtils.isEmpty(param.getCurPage()) || StringUtils.isEmpty(param.getPageSize())) {
            result = Result.build(null, ResultCodeEnum.PARAM_ERROR);
            log.info("分页参数不能为空");
            return result;
        }

        param.setStartIndex((param.getCurPage() - 1) * param.getPageSize());
        DatabaseCount databaseCount;
        try {
            databaseCount = checkQueryMapper.quryCheckResult_count(param);
        } catch (Exception e) {
            log.error("查询对账结果明细总条数异常");
            throw new RuntimeException(e);
        }
        Disp20210072OutDto outDto = new Disp20210072OutDto();
        outDto.setRecNum(databaseCount.getCountRec().longValue());
        if (databaseCount.getCountRec().longValue() < 1) {
            log.info("对账结果明细查询执行成功，返回：{}", JSON.toJSONString(outDto));
            return Result.build(outDto, ResultCodeEnum.SUCCESS);
        }
        ArrayList<Disp20210072OutMx> outList;
        try {
            outList = checkQueryMapper.quryCheckResult(param);
        } catch (Exception e) {
            log.error("查询对账结果明细异常");
            throw new RuntimeException(e);
        }
        outDto.setList(outList);

        result = Result.build(outDto, ResultCodeEnum.SUCCESS);
        log.info("对账结果明细查询执行成功，返回：{}", JSON.toJSONString(outDto));
        return result;
    }

    @Override
    public Result<BaseSelectionOutDto> quryMainCheckResult(Disp20210334InDto inDto) {
        if (inDto.getPageNum() != null && inDto.getPageSize() != null) {
            inDto.setPageNum((inDto.getPageNum() - 1) * inDto.getPageSize());
        }
        log.info("对账结果查询开始，传入参数：" + JSON.toJSONString(inDto));
        Integer tolPageNum = resultMapper.quryMainCheckResultCount(inDto);
        BaseSelectionOutDto outDto = new BaseSelectionOutDto();
        if (tolPageNum == 0) {
            log.info("对账结果查询结束，返回：" + JSON.toJSONString(outDto));
            outDto.setTolPageNum(tolPageNum);
            return Result.ok(outDto);
        }
        ArrayList<TReconciliationResult> dataList = resultMapper.quryMainCheckResult(inDto);
        outDto.setTolPageNum(tolPageNum);
        outDto.setDataList(dataList);
        log.info("对账结果查询结束，返回：" + JSON.toJSONString(outDto));
        return Result.ok(outDto);
    }

    @Override
    public Result exportMainCheckResult(Disp20210334InDto inDto) {
        log.info("对账结果导出业务开始，参数：" + JSON.toJSONString(inDto));
        inDto.setPageSize(exportMaxItemNum);
        inDto.setPageNum(0);
        ArrayList<TReconciliationResult> outList;
        try {
            outList = resultMapper.quryMainCheckResult(inDto);
        } catch (Exception e) {
            log.error("查询异常");
            throw new RuntimeException(e);
        }
        log.info("对账结果查询成功，开始生成xls文件");
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("对账结果数据表");
        XSSFRow headRow = sheet.createRow(0);
        String[] str = {"编号", "对账日期", "市场编号", "对账结果", "备注", "创建日期", "更新日期"};
        for (int i = 0; i < str.length; i++) {
            headRow.createCell(i).setCellValue(str[i]);
        }
        XSSFRow row;
        int rowIndex = 1;
        for (TReconciliationResult data : outList) {
            row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(rowIndex);
            row.createCell(1).setCellValue(data.getMarketId());
            row.createCell(2).setCellValue(data.getReconRslt()==null?"":EnumTransUtil.convertReconRslt(data.getReconRslt()));
            row.createCell(3).setCellValue(data.getRemark());
            row.createCell(4).setCellValue(data.getCreatTime()==null?"":DateUtils.convertToDataTime(data.getCreatTime()));
            row.createCell(5).setCellValue(data.getUpTime()==null?"":DateUtils.convertToDataTime(data.getUpTime()));
            rowIndex++;
        }
        //宽度自适应大小
        for (int z = 0; z < str.length; z++) {
            sheet.autoSizeColumn(z);
        }
        final String remotePath = "checkResultFile";
        String fileName = System.currentTimeMillis() + ".xls";
        File file = new File(checkResultFilePath + "/" + fileName);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception e) {
            log.error("文件异常", e);
        } finally {
            try {
                outputStream.close();
                wb.close();
            } catch (Exception e) {
                log.error("文件异常", e);
            }
        }
        FileUploadUtil fileUploadUtil = new FileUploadUtil();
        Result result = fileUploadUtil.post(file, remotePath,restTemplate2);
        log.info("对账结果交易导出-文件上传成功，返回：" + result.getData());
        return result;
    }
}
