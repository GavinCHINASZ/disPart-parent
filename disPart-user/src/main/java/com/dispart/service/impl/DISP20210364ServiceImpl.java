package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.dao.AccnoBalChangeMapper;
import com.dispart.dto.customdto.ExportCustomInfoOutDto;
import com.dispart.model.businessCommon.TransMdEnum;
import com.dispart.model.businessCommon.TxnTypeEnum;
import com.dispart.parmeterdto.AccnoChangeDetailDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.vo.DISP20210364RespVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import static com.dispart.result.ResultCodeEnum.SUCCESS;
import static com.dispart.result.UserResultCodeEnum.USER_UPLOAD_FILE_FAIL;

/**
 * 账户明细查询
 */
@Component
@Slf4j
public class DISP20210364ServiceImpl {

    @Resource
    AccnoBalChangeMapper accnoBalChangeMapper;

    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate2;

    @Value("${fileProps.fileLocalPath}")
    private String fileLocalPath;

    public Result<DISP20210364RespVo> queryAccnoDetails(AccnoChangeDetailDto dto) {

        DISP20210364RespVo respVo = new DISP20210364RespVo();

        log.debug("账户明细查询, 查询条件:{}", JSON.toJSONString(dto));

        int count = accnoBalChangeMapper.count(dto);
        respVo.setTolPageNum(count);

        if(count < 1) {
            log.warn("账户明细查询, 没有满足条件的数据");
            return Result.ok(respVo);
        }
        dto.setPageNum(dto.getPageNum() * dto.getPageSize() - dto.getPageSize());
        List<AccnoChangeDetailDto> list = accnoBalChangeMapper.queryList(dto);

        log.debug("账户明细查询，查询结果:{}", JSON.toJSONString(list));

        respVo.setList(list);

        return Result.ok(respVo);
    }

    /**
     * 账户明晰查询 导出表格
     *
     */
    public Result<ExportCustomInfoOutDto> queryAccnoDetailsIo(Request<AccnoChangeDetailDto> request) {
        AccnoChangeDetailDto body = request.getBody();
        log.info("DISP20210373 调账申请查询导出body=" + body);

        List<AccnoChangeDetailDto> accnoChangeDetailDtoList = null;
        try {
            accnoChangeDetailDtoList = accnoBalChangeMapper.queryList(body);
        } catch (Exception e) {
            log.error("调账申请查询异常", e);
        }

        if (accnoChangeDetailDtoList != null && accnoChangeDetailDtoList.size() > 0) {
            try {
                String excelFileName = "一卡通账户明细查询导出";
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
                String[] cellNames = new String[]{"编号", "流水号", "卡号", "客户名称", "客户编号", "交易类型", "交易方式", "进账类型",
                        "交易前可用余额(元)", "交易后可用余额(元)", "交易金额(元)", "交易时间", "操作人", "摘要"};

                // 标题
                HSSFRow nextRow = hssfsheet.createRow(1);
                HSSFCell hssfcell;
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

                // 状态
                int i = 2;
                int rowNum = 0;
                // 列值
                for (AccnoChangeDetailDto item : accnoChangeDetailDtoList){
                    nextRow = hssfsheet.createRow(i++);
                    int k = 0;
                    rowNum++;

                    // 编号
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(rowNum);
                    hssfcell.setCellStyle(tableStyle);

                    // 流水号
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getJrnlNum());
                    hssfcell.setCellStyle(tableStyle);

                    // 卡号
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getCardNo());
                    hssfcell.setCellStyle(tableStyle);

                    // 客户名称
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getProvNm());
                    hssfcell.setCellStyle(tableStyle);

                    // 客户编号
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getProvId());
                    hssfcell.setCellStyle(tableStyle);

                    // 交易类型
                    hssfcell = nextRow.createCell(k++);
                    //String desc = TxnTypeEnum.getDesc(item.getTxnType());
                    hssfcell.setCellValue(TxnTypeEnum.getDesc(item.getTxnType()));
                    hssfcell.setCellStyle(tableStyle);

                    // 交易方式
                    hssfcell = nextRow.createCell(k++);
                    //String desc = TransMdEnum.getDesc(item.getTransMd());
                    hssfcell.setCellValue(TransMdEnum.getDesc(item.getTransMd()));
                    hssfcell.setCellStyle(tableStyle);

                    // 进账类型
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue("0".equals(item.getIncomeTp()) ? "进账" : "出账");
                    hssfcell.setCellStyle(tableStyle);

                    // 交易前可用余额(元)
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getBeforeAmt() != null ? "" + item.getBeforeAmt() : "");
                    hssfcell.setCellStyle(tableStyle);

                    // 交易后可用余额(元)
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getAfterAmt() != null ? "" + item.getAfterAmt() : "");
                    hssfcell.setCellStyle(tableStyle);

                    // 交易余额(元)
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getTxnAmt() != null ? "" + item.getTxnAmt() : "");
                    hssfcell.setCellStyle(tableStyle);

                    // 交易时间
                    hssfcell = nextRow.createCell(k++);
                    if (!ObjectUtils.isEmpty(item.getTxnTm())){
                        hssfcell.setCellValue(item.getTxnTm());
                    }
                    hssfcell.setCellStyle(dateStyle2);

                    // 操作人
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(StringUtils.isEmpty(item.getOperNm()) ? "" : item.getOperNm());
                    hssfcell.setCellStyle(tableStyle);

                    // 摘要
                    hssfcell = nextRow.createCell(k);
                    hssfcell.setCellValue(StringUtils.isEmpty(item.getSummary()) ? "" : item.getSummary());
                    hssfcell.setCellStyle(tableStyle);
                }

                //宽度自适应大小
                for (int z = 0; z < cellNames.length; z++) {
                    hssfsheet.autoSizeColumn(z);
                }
                FileOutputStream out = null;
                String fileName = System.currentTimeMillis() + ".xls";
                String uploadMkdir = "custominfoxls";
                File file = new File(fileLocalPath + "/" + uploadMkdir + "/" + fileName);

                try {
                    out = new FileOutputStream(file);
                    workBook.write(out);
                } catch (Exception e) {
                    log.error("供应商补贴导出失败", e);
                    throw new RuntimeException("供应商补贴导出失败");
                } finally {
                    try {
                        if (out != null){
                            out.close();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    try {
                        workBook.close();
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
                Result result;
                try {
                    result = restTemplate2.postForObject("http://" + "disPart-files" + "/securityCenter/DISP20210105",
                            multiValueBody, Result.class);
                } catch (Exception e) {
                    log.error("调用文件上传服务失败" + e);
                    throw new RuntimeException("调用文件上传服务失败");
                }

                if (ObjectUtils.isEmpty(result) || result.getCode() != 200) {
                    return Result.build(null, USER_UPLOAD_FILE_FAIL.getCode(), USER_UPLOAD_FILE_FAIL.getMessage());
                }

                log.info("DISP20210367 供应商补贴导出失败文件成功");
                ExportCustomInfoOutDto outBody = new ExportCustomInfoOutDto();
                outBody.setFileUrl((String) result.getData());
                return Result.build(outBody, SUCCESS);
            }catch (Exception e){
                log.error("DISP20210367 供应商补贴导出失败文件异常", e);
                return Result.fail();
            }
        }

        return Result.fail();
    }

    /**
     * 设置单元格边框
     *
     * @param style HSSFCellStyle
     */
    private void setBorder(HSSFCellStyle style) {
        style.setBorderTop(BorderStyle.MEDIUM);//上边框
        style.setBorderBottom(BorderStyle.MEDIUM);//下边框
        style.setBorderLeft(BorderStyle.MEDIUM);//左边框
        style.setBorderRight(BorderStyle.MEDIUM);//右边框
    }

}
