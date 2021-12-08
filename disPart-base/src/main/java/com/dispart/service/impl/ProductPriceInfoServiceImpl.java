package com.dispart.service.impl;

import com.dispart.dao.mapper.ProductPriceInfoMapper;
import com.dispart.dto.prdctPriceDto.InputErrorItemOutDto;
import com.dispart.enums.ImageInfoResultCodeEnum;
import com.dispart.enums.PrdctPriceResultCodeEnum;
import com.dispart.model.ProductPriceInfo;
import com.dispart.result.Result;
import com.dispart.service.ProductPriceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhaoshihao
 * @version 1.0.0
 * @title ProductPriceInfoServiceImpl
 * @creat 2021/6/16 19:28
 * @Copyright 2020-2021
 */
@Service
@Slf4j
public class ProductPriceInfoServiceImpl implements ProductPriceInfoService {

    @Resource
    private ProductPriceInfoMapper mapper;

    @Override
    @Transactional
    public Result addProductInfo(MultipartFile file) {
        log.info("数据价格导入开始执行");
        if (file.isEmpty()) {
            log.error("传入文件为空");
            return Result.build(ImageInfoResultCodeEnum.PARAM_NULL.getCode(), ImageInfoResultCodeEnum.PARAM_NULL.getMessage());
        }
        XSSFWorkbook wb;
        XSSFSheet sheet;
        XSSFRow row;
        try {
            wb = new XSSFWorkbook(file.getInputStream());
        } catch (IOException ioException) {
            log.error("系统IO异常", ioException);
            throw new RuntimeException("系统IO异常");
        }
        sheet = wb.getSheetAt(0);
        List<InputErrorItemOutDto> errorInfo = new ArrayList<>();
        List<ProductPriceInfo> successInfo = new ArrayList<>();
        InputErrorItemOutDto errorOutVo;
        ProductPriceInfo inputInfo;
        SimpleDateFormat df;

        //一二行是标题和菜单，所以从三行开始读数据
        log.info("读取文件成功，开始检查数据");
        for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            if (row == null) {
                continue;
            }

            errorOutVo = new InputErrorItemOutDto();
            inputInfo = new ProductPriceInfo();

            //原文件信息
            errorOutVo.setPrdctNm(row.getCell(0).toString());
            errorOutVo.setMaxPrice(row.getCell(1).toString());
            errorOutVo.setMidPrice(row.getCell(2).toString());
            errorOutVo.setMinPrice(row.getCell(3).toString());
            errorOutVo.setUnit(row.getCell(4).toString());
            errorOutVo.setDate(row.getCell(5).toString());

            //单元格非空校验
            if (StringUtils.isEmpty(errorOutVo.getPrdctNm())) {
                errorOutVo.setErrorRow(i + 1);
                errorOutVo.setErrorMsg("商品名不能为空");
            }
            if (StringUtils.isEmpty(errorOutVo.getMaxPrice())) {
                errorOutVo.setErrorRow(i + 1);
                errorOutVo.setErrorMsg("最高价不能为空");
            }
            if (StringUtils.isEmpty(errorOutVo.getMidPrice())) {
                errorOutVo.setErrorRow(i + 1);
                errorOutVo.setErrorMsg("中间价不能为空");
            }
            if (StringUtils.isEmpty(errorOutVo.getMinPrice())) {
                errorOutVo.setErrorRow(i + 1);
                errorOutVo.setErrorMsg("最低价不能为空");
            }
            if (StringUtils.isEmpty(errorOutVo.getUnit())) {
                errorOutVo.setErrorRow(i + 1);
                errorOutVo.setErrorMsg("单位不能为空");
            }
            if (StringUtils.isEmpty(errorOutVo.getDate())) {
                errorOutVo.setErrorRow(i + 1);
                errorOutVo.setErrorMsg("日期不能为空");
            }

            //商品名和单位均为字符型，不需要校验
            inputInfo.setPrdctNm(row.getCell(0).toString().trim());
            inputInfo.setUnit(row.getCell(4).toString().trim());

            //最高价格式校验
            if (row.getCell(1).getCellTypeEnum() == CellType.NUMERIC) {
                inputInfo.setMaxPrice(BigDecimal.valueOf(row.getCell(1).getNumericCellValue()));
            } else {
                errorOutVo.setErrorRow(i + 1);
                errorOutVo.setErrorMsg("最高价格式错误，须为数值类型");
            }

            //中间价格式校验
            if (row.getCell(2).getCellTypeEnum() == CellType.NUMERIC) {
                inputInfo.setMidPrice(BigDecimal.valueOf(row.getCell(2).getNumericCellValue()));
            } else {
                errorOutVo.setErrorRow(i + 1);
                errorOutVo.setErrorMsg("中间价格式错误，须为数值类型");
            }

            //最低价格式校验
            if (row.getCell(3).getCellTypeEnum() == CellType.NUMERIC) {
                inputInfo.setMinPrice(BigDecimal.valueOf(row.getCell(3).getNumericCellValue()));
            } else {
                errorOutVo.setErrorRow(i + 1);
                errorOutVo.setErrorMsg("最低价格式错误，须为数值类型");
            }

            //日期格式校验
            try {
                if (HSSFDateUtil.isCellDateFormatted(row.getCell(5))) {
                    df = new SimpleDateFormat("yyyy-MM-dd");
                    String date = df.format(row.getCell(5).getDateCellValue());
                    String[] dateStr = date.split("-");
                    if (dateStr[0].length() == 4 && dateStr[1].length() == 2 && dateStr[2].length() == 2) {
                        try {
                            inputInfo.setDate(df.parse(date));
                        } catch (ParseException e) {
                            log.error("日期转换异常",e);
                        }

                    } else {
                        errorOutVo.setErrorRow(i + 1);
                        errorOutVo.setErrorMsg("日期格式不正确");
                    }
                } else {
                    errorOutVo.setErrorRow(i + 1);
                    errorOutVo.setErrorMsg("日期格式不正确");
                }
            } catch (Exception e) {
                errorOutVo.setErrorRow(i + 1);
                errorOutVo.setErrorMsg("日期格式不正确");
            }

            if (!StringUtils.isEmpty(errorOutVo.getErrorMsg())) {
                errorInfo.add(errorOutVo);
            } else {
                successInfo.add(inputInfo);
            }

        }

        if (errorInfo.size() > 0) {
            log.info("商品价格录入失败，错误记录数：" + errorInfo.size());
            return Result.build(errorInfo, PrdctPriceResultCodeEnum.EXIST_FAIL_ITEM.getCode(), PrdctPriceResultCodeEnum.EXIST_FAIL_ITEM.getMessage());
        } else {
            log.info("当前表没有非空字段，开始插入数据");
            try {
                for (ProductPriceInfo info : successInfo) {
                    //直接删除原存在数据，然后插入
                    int i = mapper.selectIsExist(info);
                    if (i<1){
                        info.setUpdateDt(new Date());
                        mapper.insert(info);
                    }
                }
            } catch (Exception e) {
                log.error("数据库异常", e);
                throw new RuntimeException("数据库异常");
            }
            log.info("商品价格信息录入结束，全部录入成功");
            return Result.ok();
        }
    }
}

