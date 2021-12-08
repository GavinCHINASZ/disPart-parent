package com.dispart.service.impl;

import com.dispart.dao.*;
import com.dispart.dto.customdto.ExportCustomInfoOutDto;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.MemberShipInfoIoService;
import com.dispart.vo.AccnoInfoDetailVo;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.dispart.result.ResultCodeEnum.SUCCESS;
import static com.dispart.result.UserResultCodeEnum.*;

/**
 * 导出
 *
 */
@Service
@Slf4j
@Transactional
public class MemberShipInfoIoServiceImpl implements MemberShipInfoIoService {
    @Resource
    private MemberShipInfoDao memberShipInfoDao;

    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate2;

    @Value("${fileProps.fileLocalPath}")
    private String fileLocalPath;

    /**
     * 调账申请查询导出
     *
     * @author 黄贵川
     * @date 2021/11/11
     * @param inDto Request<ExportCustomInfoOutDto>
     * @return Result<ExportCustomInfoOutDto>
     */
    @Override
    public Result<ExportCustomInfoOutDto> queryReconciliationByParmIo(Request<AccnoInfoDetailVo> inDto) {
        AccnoInfoDetailVo body = inDto.getBody();
        log.info("DISP20210373 调账申请查询导出body=" + body);

        // 设置调账记录
        body.setTxnType(AccnoInfoDetailVo.RECONCILIATION);
        List<AccnoInfoDetailVo> accnoInfoDetailVoList = null;
        // 有分页参数才做分页查询
        if (body.getPageNum() != null && body.getPageNum() > 0 && body.getPageSize() > 0) {
            Long pageNum = (body.getPageNum() - 1) * body.getPageSize();
            body.setStartIndex(pageNum);
        } else {
            body.setPageSize(0L);
        }

        try {
            accnoInfoDetailVoList = memberShipInfoDao.queryAccnoInfoDetailList(body);
        } catch (Exception e) {
            log.error("调账申请查询异常", e);
        }

        if (accnoInfoDetailVoList != null && accnoInfoDetailVoList.size() > 0) {
            try {
                String excelFileName = "调账申请查询导出";
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
                String[] cellNames = new String[]{"编号", "资金流水ID", "卡号", "账号", "调账金额(元)", "调账标识", "客户名称",
                        "手机号", "状态", "操作时间", "操作人"};

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

                // 状态
                String statusName = "";
                int i = 2;
                int rowNum = 0;
                // 列值
                for (AccnoInfoDetailVo item : accnoInfoDetailVoList){
                    nextRow = hssfsheet.createRow(i++);
                    int k = 0;
                    rowNum++;

                    // 编号
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(rowNum);
                    hssfcell.setCellStyle(tableStyle);

                    // 资金流水号
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getID());
                    hssfcell.setCellStyle(tableStyle);

                    // 卡号
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getCardNo());
                    hssfcell.setCellStyle(tableStyle);

                    // 账号
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getAccount());
                    hssfcell.setCellStyle(tableStyle);

                    // 调账金额
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getTxnAmt() + "");
                    hssfcell.setCellStyle(tableStyle);

                    // 调账标识
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue("0".equals(item.getOperTp()) ? "加余额" : "减余额");
                    hssfcell.setCellStyle(tableStyle);

                    // 客户名称
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getProvNm());
                    hssfcell.setCellStyle(tableStyle);

                    // 手机号
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getPhone());
                    hssfcell.setCellStyle(tableStyle);

                    if(StringUtils.isNotEmpty(item.getStatus())){
                        // 状态 1-申请调账 2-已调账 3-申请提现 4-申请提现复核 5-已提现 6-调账复核不通过 7-提现申请不通过
                        switch (item.getStatus()){
                            case "1": {
                                statusName = "申请调账";
                                break;
                            }
                            case "2": {
                                statusName = "已调账";
                                break;
                            }
                            case "3": {
                                statusName = "申请提现";
                                break;
                            }
                            case "4": {
                                statusName = "申请提现复核";
                                break;
                            }
                            case "5": {
                                statusName = "已提现";
                                break;
                            }
                            case "6": {
                                statusName = "调账复核不通过";
                                break;
                            }
                            case "7": {
                                statusName = "提现申请不通过";
                                break;
                            }
                            default: {
                                statusName = "";
                                break;
                            }
                        }
                    }else {
                        statusName = "";
                    }
                    // 状态
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(statusName);
                    hssfcell.setCellStyle(tableStyle);

                    // 操作时间
                    hssfcell = nextRow.createCell(k++);
                    if (!ObjectUtils.isEmpty(item.getTxnDt())){
                        hssfcell.setCellValue(item.getTxnDt());
                    }
                    hssfcell.setCellStyle(dateStyle2);

                    // 操作人
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(StringUtils.isEmpty(item.getOperName()) ? "" : item.getOperName());
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
                    result = restTemplate2.postForObject("http://" + "disPart-files" + "/securityCenter/DISP20210105", multiValueBody, Result.class);
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
     * 调账申请查询导出
     *
     * @author 黄贵川
     * @date 2021/11/11
     * @param inDto Request<ExportCustomInfoOutDto>
     * @return Result<ExportCustomInfoOutDto>
     */
    @Override
    public Result<ExportCustomInfoOutDto> queryWithdrawByParamIo(Request<AccnoInfoDetailVo> inDto) {
        AccnoInfoDetailVo body = inDto.getBody();
        log.info("DISP20210191 提现查询-查询参数： " + body);

        List<AccnoInfoDetailVo> accnoInfoDetailVoList = null;
        // 设置调账记录
        body.setTxnType(AccnoInfoDetailVo.WITHDRAW);
        // 有分页参数才做分页查询
        if (body.getPageNum() != null && body.getPageNum() > 0 && body.getPageSize() > 0) {
            Long pageNum = (body.getPageNum() - 1) * body.getPageSize();
            body.setStartIndex(pageNum);
        } else {
            body.setPageSize(0L);
        }

        try {
            accnoInfoDetailVoList = memberShipInfoDao.queryAccnoInfoDetailList(body);
        } catch (Exception e) {
            log.error("调账申请查询异常", e);
            return Result.fail();
        }

        if (accnoInfoDetailVoList != null && accnoInfoDetailVoList.size() > 0) {
            try {
                String excelFileName = "供应商补贴查询";
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
                String[] cellNames = new String[]{"编号", "资金流水ID", "客户名称", "客户编号", "提现金额(元)", "状态", "备注",
                        "操作时间", "操作人"};

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

                // 状态
                String statusName = "";
                int i = 2;
                int rowNum = 0;
                // 列值
                for (AccnoInfoDetailVo item : accnoInfoDetailVoList){
                    nextRow = hssfsheet.createRow(i++);
                    int k = 0;
                    rowNum++;

                    // 编号
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(rowNum);
                    hssfcell.setCellStyle(tableStyle);

                    // 资金流水号
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getID());
                    hssfcell.setCellStyle(tableStyle);

                    // 卡号
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getCardNo());
                    hssfcell.setCellStyle(tableStyle);

                    // 客户名称
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getProvNm());
                    hssfcell.setCellStyle(tableStyle);

                    // 客户编码
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getProvId());
                    hssfcell.setCellStyle(tableStyle);

                    // 提现金额
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getTxnAmt() + "");
                    hssfcell.setCellStyle(tableStyle);

                    if(StringUtils.isNotEmpty(item.getStatus())){
                        // 状态 1-申请调账 2-已调账 3-申请提现 4-申请提现复核 5-已提现 6-调账复核不通过 7-提现申请不通过
                        switch (item.getStatus()){
                            case "1": {
                                statusName = "申请调账";
                                break;
                            }
                            case "2": {
                                statusName = "已调账";
                                break;
                            }
                            case "3": {
                                statusName = "申请提现";
                                break;
                            }
                            case "4": {
                                statusName = "申请提现复核";
                                break;
                            }
                            case "5": {
                                statusName = "已提现";
                                break;
                            }
                            case "6": {
                                statusName = "调账复核不通过";
                                break;
                            }
                            case "7": {
                                statusName = "提现申请不通过";
                                break;
                            }
                            default: {
                                statusName = "";
                                break;
                            }
                        }
                    }else {
                        statusName = "";
                    }
                    // 状态
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(statusName);
                    hssfcell.setCellStyle(tableStyle);

                    // 状态
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(StringUtils.isEmpty(item.getRemark()) ? "" : item.getRemark());
                    hssfcell.setCellStyle(tableStyle);

                    // 操作时间
                    hssfcell = nextRow.createCell(k++);
                    if (!ObjectUtils.isEmpty(item.getTxnDt())) {
                        hssfcell.setCellValue(item.getTxnDt());
                    }
                    hssfcell.setCellStyle(dateStyle2);

                    // 操作人
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(StringUtils.isEmpty(item.getOperName()) ? "" : item.getOperName());
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
                // uploadMkdir全英文  全小写  无特殊符号
                multipartBodyBuilder.part("uploadMkdir", uploadMkdir);
                multipartBodyBuilder.part("file", new FileSystemResource(file));
                MultiValueMap<String, HttpEntity<?>> multiValueBody = multipartBodyBuilder.build();

                // 上传文件服务
                Result result;
                try {
                    result = restTemplate2.postForObject("http://" + "disPart-files" + "/securityCenter/DISP20210105", multiValueBody, Result.class);
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
