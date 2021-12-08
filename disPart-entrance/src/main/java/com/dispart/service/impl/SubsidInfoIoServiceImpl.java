package com.dispart.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dispart.dao.mapper.TSubsidInfoMapper;
import com.dispart.dao.mapper.TVechicleProcurerMapper;
import com.dispart.dto.customdto.ExportCustomInfoOutDto;
import com.dispart.dto.entrance.D_0297FindDto;
import com.dispart.model.EmployeeInfo;
import com.dispart.request.Request;
import com.dispart.result.EntranceResult_WEnum;
import com.dispart.result.Result;
import com.dispart.service.SubsidInfoIoService;
import com.dispart.vo.commons.TRoleMenuInfo;
import com.dispart.vo.entrance.TSubsidInfo;
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
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.dispart.result.ResultCodeEnum.SUCCESS;
import static com.dispart.result.UserResultCodeEnum.USER_UPLOAD_FILE_FAIL;


/**
 * 补贴申请导出
 *
 * @author 黄贵川
 * @date 2021/11/11
 */
@Service
@Slf4j
public class SubsidInfoIoServiceImpl implements SubsidInfoIoService {
    @Resource
    TSubsidInfoMapper tSubsidInfoMapper;

    @Resource
    TVechicleProcurerMapper tVechicleProcurerMapper;

    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate2;

    @Value("${fileProps.fileLocalPath}")
    private String fileLocalPath;

    @Value("${fileProps.filePageSize}")
    private String filePageSize;

    /**
     * 供应商补贴导出
     *
     * @author 黄贵川
     * @date 2021/11/11
     * @param record Request<D_0297FindDto>
     * @return Result<ExportCustomInfoOutDto>
     */
    @Override
    public Result<ExportCustomInfoOutDto> supplySubsidInfoIoMethod(Request<D_0297FindDto> record) {
        D_0297FindDto body = record.getBody();

        log.info("DISP20210367 供应商补贴导出:" + JSONObject.toJSONString(body));
        // 有分页参数才做分页查询
        if (body.getCurPage() != null && body.getCurPage() > 0 && body.getPageSize() > 0) {
            Integer curPage = (body.getCurPage() - 1) * body.getPageSize();
            body.setCurPage(curPage);
        } else {
            body.setCurPage(0);
            body.setPageSize(Integer.valueOf(filePageSize));
        }

        try {
            if (StringUtils.isNotEmpty(body.getCarType())) {
                TRoleMenuInfo roleMenuInfo = tVechicleProcurerMapper.findRoleMenuInfo(body);
                if (roleMenuInfo != null && "0".equals(roleMenuInfo.getDataParm())) {
                    // 无感支付 0-人工  1-半无感  2-全无感
                    if (body.getNonInductive() == 2){
                        log.info("DISP20210297 数据权限0-个人 无感支付2-全无感 直接返回--未查询到相关数据！");
                        return Result.build(200,"未查询到相关数据！");
                    }
                    // 操作人ID
                    String operator = record.getHead().getOperator();
                    body.setOperator(operator);
                } else if (roleMenuInfo != null && "1".equals(roleMenuInfo.getDataParm())) {
                    EmployeeInfo employeeInfo = tVechicleProcurerMapper.findEmployeeInfo(body);
                    if (employeeInfo != null) {
                        body.setDepId(employeeInfo.getSubDep());
                    }
                }
            }
        }catch (Exception e){
            log.error("DISP20210351 进出场信息查询导出查询角色异常", e);
            return Result.fail();
        }

        List<D_0297FindDto> d_0297FindDtoList;
        try {
            String[] inIdArr = tVechicleProcurerMapper.findVechicleProcurerInId(body);
            if (inIdArr == null || inIdArr.length <= 0){
                return Result.fail();
            }
            body.setInIdArr(inIdArr);
            // 进出场查询
            d_0297FindDtoList = tVechicleProcurerMapper.findEntranceMessage(body);
        }catch (Exception e){
            log.error("DISP20210351 进出场查询失败", e);
            return Result.fail();
        }

        if (d_0297FindDtoList != null && d_0297FindDtoList.size() > 0) {
            if(d_0297FindDtoList.size() > 10000){
                return Result.fail();
            }
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
                String[] cellNames = new String[]{"进场id", "进场日期", "客户名称", "客户编码", "手机号", "入口", "出口",
                        "车牌号", "车辆皮重(千克)", "进场品种重量(千克)", "进场总重量(千克)", "是否核验", "进场操作人", "出场操作人",
                        "补贴状态", "流水号", "状态", "进场收款金额(元)", "出场(收费/退费)"};

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
                String subsidStatus = "";
                int i = 2;
                // 列值
                for (D_0297FindDto item : d_0297FindDtoList){
                    if(StringUtils.isNotEmpty(item.getStatus())){
                        // 状态
                        switch (item.getStatus()){
                            case "0": {
                                statusName = "未付款";
                                break;
                            }
                            case "1": {
                                statusName = "已进场";
                                break;
                            }
                            case "2": {
                                statusName = "退款中";
                                break;
                            }
                            case "3": {
                                statusName = "已出场";
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

                    nextRow = hssfsheet.createRow(i++);
                    int k = 0;
                    // 进场id
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getInId());
                    hssfcell.setCellStyle(tableStyle);

                    // 进场日期
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue("" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getInTime()));
                    hssfcell.setCellStyle(tableStyle);

                    // 客户名称
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getProvNm());
                    hssfcell.setCellStyle(tableStyle);

                    // 客户编码
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getProvId());
                    hssfcell.setCellStyle(tableStyle);

                    // 手机号
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getPhone());
                    hssfcell.setCellStyle(tableStyle);

                    // 入口
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getInDoorNm() == null ? "" : item.getInDoorNm());
                    hssfcell.setCellStyle(tableStyle);

                    // 出口
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getOutDoorNm() == null ? "" : item.getOutDoorNm());
                    hssfcell.setCellStyle(tableStyle);

                    // 车牌号
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getVehicleNum());
                    hssfcell.setCellStyle(tableStyle);

                    // 车辆皮重(千克)
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getTareWght() == null ? "" : "" + item.getTareWght());
                    hssfcell.setCellStyle(tableStyle);

                    // 进场品种重量(千克)
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getCargoWght() == null ? "" : "" + item.getCargoWght());
                    hssfcell.setCellStyle(tableStyle);

                    // 进场总重量(千克)
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getInTtlWght() == null ? "" : "" + item.getInTtlWght());
                    hssfcell.setCellStyle(tableStyle);

                    // 是否核验
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getIsCheck() != null && "1".equals(item.getIsCheck()) ?
                            "已核验" : "未核验");
                    hssfcell.setCellStyle(tableStyle);

                    // 进场操作人
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(StringUtils.isEmpty(item.getInOperName()) ? "" : item.getInOperName());
                    hssfcell.setCellStyle(tableStyle);

                    // 出场操作人
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(StringUtils.isEmpty(item.getOutOperName()) ? "" : item.getOutOperName());
                    hssfcell.setCellStyle(tableStyle);

                    if (StringUtils.isNotEmpty(item.getSubsidStatus())){
                        // -1--没有补贴申请 0-申请中 1-已发放 2-已撤回 3-待撤回  4--作废 5--撤回申请中
                        switch (item.getSubsidStatus()){
                            case "-1": {
                                subsidStatus = "没有补贴申请";
                                break;
                            }
                            case "0": {
                                subsidStatus = "申请中";
                                break;
                            }
                            case "1": {
                                subsidStatus = "已发放";
                                break;
                            }
                            case "2": {
                                subsidStatus = "已撤回";
                                break;
                            }
                            case "3": {
                                subsidStatus = "待撤回";
                                break;
                            }
                            case "4": {
                                subsidStatus = "作废";
                                break;
                            }
                            case "5": {
                                subsidStatus = "撤回申请中";
                                break;
                            }
                            default: {
                                subsidStatus = "";
                                break;
                            }
                        }
                    } else {
                        subsidStatus = "";
                    }
                    // 补贴状态
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(subsidStatus);
                    hssfcell.setCellStyle(tableStyle);

                    // 流水号
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(StringUtils.isEmpty(item.getJrnlNum()) ? "" : item.getJrnlNum());
                    hssfcell.setCellStyle(tableStyle);

                    // 状态
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(statusName);
                    hssfcell.setCellStyle(tableStyle);

                    // 进场收款金额
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getInActAmt() == null ? "0" : "" + item.getInActAmt());
                    hssfcell.setCellStyle(tableStyle);

                    // 出场(收费/退费)
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getActRecevAmt() == null ? "0" : "" + item.getActRecevAmt());
                    hssfcell.setCellStyle(tableStyle);
                }

                //宽度自适应大小
                for (int z = 0; z < cellNames.length; z++) {
                    hssfsheet.autoSizeColumn(z);
                }
                FileOutputStream out = null;
                String fileName = System.currentTimeMillis() + ".xls";
                String uploadMkdir = "entrancexls";
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
     * 采购商补贴导出
     *
     * @author 黄贵川
     * @date 2021/11/11
     * @param record Request<TSubsidInfo>
     * @return Result<ExportCustomInfoOutDto>
     */
    @Override
    public Result<ExportCustomInfoOutDto> pruchaseSubsidInfoIoMethod(Request<D_0297FindDto> record) {
        D_0297FindDto body = record.getBody();

        log.info("DISP20210368 采购商补贴导出:" + JSONObject.toJSONString(body));
        // 有分页参数才做分页查询
        if (body.getCurPage() != null && body.getCurPage() > 0 && body.getPageSize() > 0) {
            Integer curPage = (body.getCurPage() - 1) * body.getPageSize();
            body.setCurPage(curPage);
        } else {
            body.setCurPage(0);
            body.setPageSize(Integer.valueOf(filePageSize));
        }

        try {
            if (StringUtils.isNotEmpty(body.getCarType())) {
                TRoleMenuInfo roleMenuInfo = tVechicleProcurerMapper.findRoleMenuInfo(body);
                if (roleMenuInfo != null && "0".equals(roleMenuInfo.getDataParm())) {
                    // 无感支付 0-人工  1-半无感  2-全无感
                    if (body.getNonInductive() == 2){
                        log.info("DISP20210297 数据权限0-个人 无感支付2-全无感 直接返回--未查询到相关数据！");
                        return Result.build(200,"未查询到相关数据！");
                    }
                    // 操作人ID
                    String operator = record.getHead().getOperator();
                    body.setOperator(operator);
                } else if (roleMenuInfo != null && "1".equals(roleMenuInfo.getDataParm())) {
                    EmployeeInfo employeeInfo = tVechicleProcurerMapper.findEmployeeInfo(body);
                    if (employeeInfo != null) {
                        body.setDepId(employeeInfo.getSubDep());
                    }
                }
            }
        }catch (Exception e){
            log.error("DISP20210368 采购商补贴导出查询导出查询角色异常", e);
            return Result.fail();
        }

        List<D_0297FindDto> d_0297FindDtoList;
        try {
            String[] inIdArr = tVechicleProcurerMapper.findVechicleProcurerInId(body);
            if (inIdArr == null || inIdArr.length <= 0){
                return Result.fail();
            }
            body.setInIdArr(inIdArr);
            // 进出场查询
            d_0297FindDtoList = tVechicleProcurerMapper.findEntranceMessage(body);
        }catch (Exception e){
            log.error("DISP20210368 采购商补贴导出查询失败", e);
            return Result.fail();
        }

        if (d_0297FindDtoList != null && d_0297FindDtoList.size() > 0) {
            try {
                String excelFileName = ("采购商补贴导出");
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
                String[] cellNames = new String[]{"进场id", "进场日期", "客户名称", "客户编码", "手机号", "入口", "出口",
                        "车牌号", "车辆皮重(千克)", "出场品种重量(千克)", "出场总重(千克)", "进场操作人", "出场操作人",
                        "补贴状态", "流水号", "状态", "出场收款金额(元)"};

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

                String statusName = "";
                String subsidStatus = "";
                int i = 2;
                // 列值
                for (D_0297FindDto item : d_0297FindDtoList){
                    if(StringUtils.isNotEmpty(item.getStatus())){
                        // 状态
                        switch (item.getStatus()){
                            case "0": {
                                statusName = "未付款";
                                break;
                            }
                            case "1": {
                                statusName = "已进场";
                                break;
                            }
                            case "2": {
                                statusName = "退款中";
                                break;
                            }
                            case "3": {
                                statusName = "已出场";
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

                    nextRow = hssfsheet.createRow(i++);
                    int k = 0;
                    // 进场id
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getInId());
                    hssfcell.setCellStyle(tableStyle);

                    // 进场日期
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue("" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getInTime()));
                    hssfcell.setCellStyle(tableStyle);

                    // 客户名称
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getProvNm());
                    hssfcell.setCellStyle(tableStyle);

                    // 客户编码
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getProvId());
                    hssfcell.setCellStyle(tableStyle);

                    // 手机号
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getPhone());
                    hssfcell.setCellStyle(tableStyle);

                    // 入口
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getInDoorNm() == null ? "" : item.getInDoorNm());
                    hssfcell.setCellStyle(tableStyle);

                    // 出口
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getOutDoorNm() == null ? "" : item.getOutDoorNm());
                    hssfcell.setCellStyle(tableStyle);

                    // 车牌号
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getVehicleNum());
                    hssfcell.setCellStyle(tableStyle);

                    // 车辆皮重(千克)
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getTareWght() == null ? "" : "" + item.getTareWght());
                    hssfcell.setCellStyle(tableStyle);

                    // 出场品种重量(千克)
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getOutCargoWght() == null ? "" : "" + item.getOutCargoWght());
                    hssfcell.setCellStyle(tableStyle);

                    // 出场总重(千克)
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getOutTtlWght() == null ? "" : "" + item.getOutTtlWght());
                    hssfcell.setCellStyle(tableStyle);

                    // 进场操作人
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(StringUtils.isEmpty(item.getInOperName()) ? "" : item.getInOperName());
                    hssfcell.setCellStyle(tableStyle);

                    // 出场操作人
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(StringUtils.isEmpty(item.getOutOperName()) ? "" : item.getOutOperName());
                    hssfcell.setCellStyle(tableStyle);

                    if (StringUtils.isNotEmpty(item.getSubsidStatus())){
                        // -1--没有补贴申请 0-申请中 1-已发放 2-已撤回 3-待撤回  4--作废 5--撤回申请中
                        switch (item.getSubsidStatus()){
                            case "-1": {
                                subsidStatus = "没有补贴申请";
                                break;
                            }
                            case "0": {
                                subsidStatus = "申请中";
                                break;
                            }
                            case "1": {
                                subsidStatus = "已发放";
                                break;
                            }
                            case "2": {
                                subsidStatus = "已撤回";
                                break;
                            }
                            case "3": {
                                subsidStatus = "待撤回";
                                break;
                            }
                            case "4": {
                                subsidStatus = "作废";
                                break;
                            }
                            case "5": {
                                subsidStatus = "撤回申请中";
                                break;
                            }
                            default: {
                                subsidStatus = "";
                                break;
                            }
                        }
                    } else {
                        subsidStatus = "";
                    }
                    // 补贴状态
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(subsidStatus);
                    hssfcell.setCellStyle(tableStyle);

                    // 流水号
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(StringUtils.isEmpty(item.getJrnlNum()) ? "" : item.getJrnlNum());
                    hssfcell.setCellStyle(tableStyle);

                    // 状态
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(statusName);
                    hssfcell.setCellStyle(tableStyle);

                    // 出场收款金额(元)
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getActRecevAmt() == null ? "0" : "" + item.getActRecevAmt());
                    hssfcell.setCellStyle(tableStyle);
                }

                //宽度自适应大小
                for (int z = 0; z < cellNames.length; z++) {
                    hssfsheet.autoSizeColumn(z);
                }
                FileOutputStream out = null;
                String fileName = System.currentTimeMillis() + ".xls";
                String uploadMkdir = "entrancexls";
                File file = new File(fileLocalPath + "/" + uploadMkdir + "/" + fileName);

                try {
                    out = new FileOutputStream(file);
                    workBook.write(out);
                } catch (Exception e) {
                    log.error("导出进出场查询失败", e);
                    throw new RuntimeException("导出进出场查询失败");
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

                log.info("DISP20210368 采购商补贴导出文件成功");
                ExportCustomInfoOutDto outBody = new ExportCustomInfoOutDto();
                outBody.setFileUrl((String) result.getData());
                return Result.build(outBody, SUCCESS);
            }catch (Exception e){
                log.error("DISP20210368 采购商补贴导出文件异常", e);
                return Result.fail();
            }
        }

        return Result.fail();
    }

    /**
     * 补贴申请查询导出
     *
     * @author 黄贵川
     * @date 2021/11/11
     * @param inDto Request<TSubsidInfo>
     * @return Result<ExportCustomInfoOutDto>
     */
    @Override
    public Result<ExportCustomInfoOutDto> subsidInfoIoMethod(Request<TSubsidInfo> inDto) {
        TSubsidInfo body = inDto.getBody();
        log.info("DISP20210369 补贴申请查询导出body=" + JSONObject.toJSONString(body));

        List<TSubsidInfo> subsidInfoList = tSubsidInfoMapper.selectSubsidInfoList(body);
        if (subsidInfoList != null && subsidInfoList.size() > 0) {
            try {
                String excelFileName = "补贴申请查询导出";
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
                String[] cellNames = new String[]{"进场id", "客户编号", "一卡通", "补贴重量(千克)", "补贴单价(元)", "补贴总额(元)",
                    "领取人姓名", "领取人电话", "补贴类别", "补贴方式", "状态", "申请人", "审核人"};

                // 标题
                HSSFRow nextRow = hssfsheet.createRow(1);
                HSSFCell hssfcell = null;
                hssfcell = row.createCell(0);
                hssfcell.setCellValue(excelFileName);
                hssfcell.setCellStyle(titlStyle);

                // 其它每个单元格设置边框，再进行合并才会有边框
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

                // 补贴方式名称
                String subsidTpName = "";
                String statusName = "";
                int i = 2;
                // 列值
                for (TSubsidInfo item : subsidInfoList){
                    nextRow = hssfsheet.createRow(i++);
                    int k = 0;
                    // 进场id
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getInId());
                    hssfcell.setCellStyle(tableStyle);

                    // 客户编码
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getProvId());
                    hssfcell.setCellStyle(tableStyle);

                    // 一卡通
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(item.getCardNo());
                    hssfcell.setCellStyle(tableStyle);

                    // 补贴重量
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue("" + item.getSubsidWght());
                    hssfcell.setCellStyle(tableStyle);

                    // 补贴单价
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue("" + item.getSubsidPrice());
                    hssfcell.setCellStyle(tableStyle);

                     // 补贴单价
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue("" + item.getSubsidTtlAmt());
                    hssfcell.setCellStyle(tableStyle);

                    // 领取人姓名
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(StringUtils.isEmpty(item.getRcvName()) ? "" : item.getRcvName());
                    hssfcell.setCellStyle(tableStyle);

                    // 领取人电话
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(StringUtils.isEmpty(item.getRcvPhone()) ? "" : item.getRcvPhone());
                    hssfcell.setCellStyle(tableStyle);

                    // 补贴类别
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(StringUtils.isEmpty(item.getPrdctType()) ? "" : item.getPrdctType());
                    hssfcell.setCellStyle(tableStyle);

                    if(StringUtils.isNotEmpty(item.getSubsidTp())){
                        // 补贴方式
                        switch (item.getSubsidTp()){
                            case "0": {
                                subsidTpName = "现金";
                                break;
                            }
                            case "1": {
                                subsidTpName = "一卡通";
                                break;
                            }
                            default: {
                                subsidTpName = "一卡通";
                                break;
                            }
                        }
                    }else {
                        subsidTpName = "";
                    }
                    // 补贴方式
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(subsidTpName);
                    hssfcell.setCellStyle(tableStyle);

                    if(StringUtils.isNotEmpty(item.getStatus())){
                        // 状态 0-申请中 1-已发放 2-已撤回 3-待撤回  4--作废 5--撤回申请中
                        switch (item.getStatus()){
                            case "0": {
                                statusName = "申请中";
                                break;
                            }
                            case "1": {
                                statusName = "已发放";
                                break;
                            }
                            case "2": {
                                statusName = "已撤回";
                                break;
                            }
                            case "3": {
                                statusName = "待撤回";
                                break;
                            }
                            case "4": {
                                statusName = "作废";
                                break;
                            }
                            case "5": {
                                statusName = "撤回申请中";
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

                    // 申请人
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(StringUtils.isEmpty(item.getOperName()) ? "" : item.getOperName());
                    hssfcell.setCellStyle(tableStyle);

                    // 审核人
                    hssfcell = nextRow.createCell(k++);
                    hssfcell.setCellValue(StringUtils.isEmpty(item.getAuditor()) ? "" : item.getAuditor());
                    hssfcell.setCellStyle(tableStyle);
                }

                //宽度自适应大小
                for (int z = 0; z < cellNames.length; z++) {
                    hssfsheet.autoSizeColumn(z);
                }
                FileOutputStream out = null;
                String fileName = System.currentTimeMillis() + ".xls";
                String uploadMkdir = "entrancexls";
                File file = new File(fileLocalPath + "/" + uploadMkdir + "/" + fileName);

                try {
                    out = new FileOutputStream(file);
                    workBook.write(out);
                } catch (Exception e) {
                    log.error("补贴申请查询导出失败", e);
                    throw new RuntimeException("补贴申请查询导出失败");
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
                Result result = null;
                try {
                    result = restTemplate2.postForObject("http://" + "disPart-files" + "/securityCenter/DISP20210105", multiValueBody, Result.class);
                } catch (Exception e) {
                    log.error("调用文件上传服务失败" + e);
                    throw new RuntimeException("调用文件上传服务失败");
                }

                if (ObjectUtils.isEmpty(result) || result.getCode() != 200) {
                    return Result.build(null, USER_UPLOAD_FILE_FAIL.getCode(), USER_UPLOAD_FILE_FAIL.getMessage());
                }

                log.info("DISP20210369 补贴申请查询导出文件成功");
                ExportCustomInfoOutDto outBody = new ExportCustomInfoOutDto();
                outBody.setFileUrl((String) result.getData());
                return Result.build(outBody, SUCCESS);
            }catch (Exception e){
                log.error("DISP20210369 补贴申请查询导出文件异常", e);
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
