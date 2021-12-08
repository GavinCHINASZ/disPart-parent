package com.dispart.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dispart.baseDto.BaseSelectionOutDto;
import com.dispart.dao.mapper.TSubsidInfoMapper;
import com.dispart.dao.mapper.TVechicleProcurerDetailsMapper;
import com.dispart.dao.mapper.TVechicleProcurerMapper;
import com.dispart.dto.customdto.ExportCustomInfoOutDto;
import com.dispart.dto.entrance.D_0297FindDto;
import com.dispart.dto.entrance.D_0299FindOutDto;
import com.dispart.dto.orderdto.AddOrdersByParam;
import com.dispart.model.EmployeeInfo;
import com.dispart.request.Request;
import com.dispart.request.RequestHead;
import com.dispart.result.EntranceResult_WEnum;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.SubsidInfoService;
import com.dispart.vo.busineCommon.TPushNotesVo;
import com.dispart.vo.commons.TRoleMenuInfo;
import com.dispart.vo.entrance.TProductTypeInfo;
import com.dispart.vo.entrance.TSubsidInfo;
import com.dispart.vo.entrance.TVechicleProcurerDetails;
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
import org.springframework.dao.DataAccessException;
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
import java.util.List;

import static com.dispart.result.ResultCodeEnum.SUCCESS;
import static com.dispart.result.UserResultCodeEnum.USER_UPLOAD_FILE_FAIL;


/**
 * 补贴申请
 *
 * @author 黄贵川
 * @date 2021/08/23
 */
@Service
@Slf4j
public class SubsidInfoServiceImpl implements SubsidInfoService {
    @Resource
    TSubsidInfoMapper tSubsidInfoMapper;

    @Resource
    TVechicleProcurerMapper tVechicleProcurerMapper;

    @Resource
    TVechicleProcurerDetailsMapper tVechicleProcurerDetailsMapper;

    @Value("${fileProps.filePageSize}")
    private String filePageSize;

    @Autowired
    @Qualifier("restTemplate2")
    private RestTemplate restTemplate2;

    @Value("${fileProps.fileLocalPath}")
    private String fileLocalPath;

    /**
     * 补贴申请--供应商/采购商进出场信息查询
     *
     * @param record Request<D_0297FindDto>
     * @return Result
     * @author 黄贵川
     * @date 2021/08/24
     */
    @Override
    public Result findEntranceMessage(Request<D_0297FindDto> record) {
        D_0297FindDto body = record.getBody();

        log.info("DISP20210297 补贴申请--供应商 采购商进出场信息查询:" + JSONObject.toJSONString(body));
        // 有分页参数才做分页查询
        if (body.getCurPage() != null && body.getCurPage() > 0 && body.getPageSize() > 0) {
            Integer curPage = (body.getCurPage() - 1) * body.getPageSize();
            body.setCurPage(curPage);
        } else {
            body.setPageSize(0);
        }

        BaseSelectionOutDto<D_0297FindDto> outDto = new BaseSelectionOutDto<>();
        try {
            // 角色 菜单
            if(StringUtils.isNotEmpty(body.getCarType()) && StringUtils.isNotEmpty(body.getRoleId())
                    && StringUtils.isNotEmpty(body.getMenuId())){
                TRoleMenuInfo roleMenuInfo = tVechicleProcurerMapper.findRoleMenuInfo(body);
                // 数据权限(0-个人，1-部门，2-机构(查看全部数据))
                if (roleMenuInfo != null && "0".equals(roleMenuInfo.getDataParm())){
                    // 无感支付 0-人工  1-半无感  2-全无感
                    if (body.getNonInductive() == 2){
                        log.info("DISP20210297 数据权限0-个人 无感支付2-全无感 直接返回--未查询到相关数据！");
                        return Result.build(200,"未查询到相关数据！");
                    }
                    // 操作人ID
                    String operator = record.getHead().getOperator();
                    body.setOperator(operator);
                }else if (roleMenuInfo != null && "1".equals(roleMenuInfo.getDataParm())){
                    EmployeeInfo employeeInfo = tVechicleProcurerMapper.findEmployeeInfo(body);
                    if (employeeInfo != null){
                        body.setDepId(employeeInfo.getSubDep());
                    }
                }
            }

            String[] inIdArr = tVechicleProcurerMapper.findVechicleProcurerInId(body);
            if (inIdArr == null || inIdArr.length <= 0){
                outDto.setTolPageNum(0);
                outDto.setDataList(new ArrayList<>());
                return Result.build(outDto, EntranceResult_WEnum.SUCCESS);
            }
            body.setInIdArr(inIdArr);
            // 进出场查询
            List<D_0297FindDto> d_0297FindDtos = tVechicleProcurerMapper.findEntranceMessage(body);
            if (d_0297FindDtos == null || d_0297FindDtos.size() <= 0) {
                outDto.setTolPageNum(0);
                outDto.setDataList(new ArrayList<>());
                return Result.build(outDto, EntranceResult_WEnum.SUCCESS);
            }
            outDto.setDataList(d_0297FindDtos);

            if (body.getPageSize() > 0) {
                Integer toleNum = tVechicleProcurerMapper.findEntranceMessageCount(body);
                outDto.setTolPageNum(toleNum);
            }

            // carType 0货车 供应商    1空车 采购商   2查询全部
            // 查询 进出场的商品信息
            List<TVechicleProcurerDetails> tVechicleProcurerDetailsList = tVechicleProcurerDetailsMapper.findByD0297FindDtoList(d_0297FindDtos);
            if (tVechicleProcurerDetailsList != null && tVechicleProcurerDetailsList.size() > 0){
                for (D_0297FindDto d_0297FindDto : d_0297FindDtos) {
                    List<TVechicleProcurerDetails> list = new ArrayList<>(4);
                    for (TVechicleProcurerDetails tVechicleProcurerDetails : tVechicleProcurerDetailsList) {
                        if (tVechicleProcurerDetails.getInId().equals(d_0297FindDto.getInId())){
                            list.add(tVechicleProcurerDetails);
                        }
                    }

                    d_0297FindDto.setTVechicleProcurerDetailsList(list);
                }
            }

            for (D_0297FindDto d_0297FindDto : d_0297FindDtos) {
                if (d_0297FindDto.getTVechicleProcurerDetailsList() == null){
                    d_0297FindDto.setTVechicleProcurerDetailsList(new ArrayList<>(1));
                }
            }
        }catch (DataAccessException e) {
            log.error("补贴申请--供应商/采购商进出场信息查询异常", e);
            return Result.fail("补贴申请--供应商/采购商进出场信息查询异常");
        }

        return Result.build(outDto, EntranceResult_WEnum.SUCCESS);
    }

    /**
     * 补贴申请--新增
     *
     * @param record TSubsidInfo
     * @return Result
     * @author 黄贵川
     * @date 2021/08/23
     */
    @Override
    public Result addSubsidInfo(Request<TSubsidInfo> record) {
        TSubsidInfo body = record.getBody();
        log.info("DISP20210298补贴申请 新增:" + JSONObject.toJSONString(body));
        if (body == null) {
            return Result.fail("参数为空");
        }

        try {
            TSubsidInfo tSubsidInfo = new TSubsidInfo();
            tSubsidInfo.setInId(body.getInId());
            int numByParm = tSubsidInfoMapper.findNumByParm(body);
            if (numByParm > 0){
                return Result.build(201, "进场id已新增过补贴申请!");
            }

            body.setOperId(record.getHead().getOperator());
            int addNum = tSubsidInfoMapper.addSubsidInfo(body);
            if (addNum != 1) {
                return Result.fail("补贴申请--新增失败");
            }
        } catch (DataAccessException e) {
            log.error("DISP20210298补贴申请--新增数据插入异常", e);
            return Result.fail();
        }

        log.info("DISP20210298进出场业务-补贴申请新增成功");
        return Result.build(body, EntranceResult_WEnum.SUCCESS);
    }

    /**
     * 补贴申请--查询
     *
     * @param body TSubsidInfo
     * @return Result
     * @author 黄贵川
     * @date 2021/08/23
     */
    @Override
    public Result selectSubsidInfo(TSubsidInfo body) {
        log.info("DISP20210299补贴申请 查询:" + JSONObject.toJSONString(body));
        // 有分页参数才做分页查询
        if (body.getCurPage() != null && body.getCurPage() > 0 && body.getPageSize() > 0) {
            Integer curPage = (body.getCurPage() - 1) * body.getPageSize();
            body.setCurPage(curPage);
        } else {
            body.setPageSize(0);
        }

        D_0299FindOutDto d_0299FindOutDto = new D_0299FindOutDto();
        try {
            List<TSubsidInfo> d_0299FindOutDtoList = tSubsidInfoMapper.selectSubsidInfoList(body);
            d_0299FindOutDto.setReportList(d_0299FindOutDtoList);

            if (body.getPageSize() > 0) {
                Integer toleNum = tSubsidInfoMapper.findNumByParm(body);
                d_0299FindOutDto.setTolPageNum(toleNum);
            }
        } catch (Exception e) {
            log.error("进出场业务-补贴申请 查询失败", e);
            return Result.build(ResultCodeEnum.DATA_NO_ERROR.getCode(), ResultCodeEnum.DATA_NO_ERROR.getMessage());
        }
        return Result.build(d_0299FindOutDto, EntranceResult_WEnum.SUCCESS);
    }

    /**
     * 补贴申请--修改
     *
     * @param body TSubsidInfo
     * @return Result
     * @author 黄贵川
     * @date 2021/08/23
     */
    @Override
    public Result updateSubsidInfo(TSubsidInfo body) {
        log.info("DISP20210300补贴申请 修改:" + JSONObject.toJSONString(body));
        try {
            int updateNum = tSubsidInfoMapper.updateSubsidInfo(body);
            if (updateNum != 1){
                return Result.fail();
            }
        }catch (Exception e){
            log.error("进出场业务--补贴申请--修改失败", e);
            throw new RuntimeException("进出场业务--补贴申请--修改失败");
        }

        return Result.ok();
    }

    /**
     * 补贴申请--作废
     *
     * @param body TSubsidInfo
     * @return Result
     * @author 黄贵川
     * @date 2021/08/24
     */
    @Override
    public Result deleteSubsidInfo(TSubsidInfo body) {
        log.info("DISP20210301补贴申请 作废:" + JSONObject.toJSONString(body));
        try {
            int updateNum = tSubsidInfoMapper.updateSubsidInfo(body);
            if (updateNum != 1){
                return Result.fail();
            }
        }catch (Exception e){
            log.error("进出场业务--补贴申请--作废失败", e);
            throw new RuntimeException("进出场业务--补贴申请--作废失败");
        }

        return Result.ok();
    }

    /**
     * 补贴申请--发放
     *
     * @param record TSubsidInfo
     * @return Result
     * @author 黄贵川
     * @date 2021/08/24
     */
    @Override
    public Result grantSubsidInfo(Request<TSubsidInfo> record) {
        TSubsidInfo body = record.getBody();
        log.info("DISP20210302补贴申请 发放:" + JSONObject.toJSONString(body));
        AddOrdersByParam addOrdersByParam = new AddOrdersByParam();
        if (StringUtils.isNotEmpty(body.getCarType()) && "0".equals(body.getCarType())){
            // txnType 8补贴发放 供应商
            addOrdersByParam.setTxnType("8");
        }else {
            // txnType 14补贴发放 采购商
            addOrdersByParam.setTxnType("14");
        }

        // transMd 2现金 4一卡通
        addOrdersByParam.setTransMd("4");
        // payCard 付款人卡号
        addOrdersByParam.setPayCard(body.getCardNo());
        addOrdersByParam.setPayerNo(body.getProvId());
        // txnAmt 金额
        addOrdersByParam.setTxnAmt(body.getSubsidTtlAmt());
        // operId 操作员
        //addOrdersByParam.setOperId(body.getOperId());
        // 进出场ID
        addOrdersByParam.setBusinessNo(body.getInId());

        Request request = new Request();
        RequestHead head = record.getHead();
        request.setHead(head);
        request.setBody(addOrdersByParam);
        Result result = restTemplate2.postForObject("http://" + "disPart-order" + "/securityCenter/DISP20210261", request, Result.class);
        if (request != null) {
            JSONObject resultJson = (JSONObject) JSONObject.toJSON(result);
            String code = resultJson.get("code").toString();
            if (!code.equals("200")) {
                log.info("发放失败code!=200");
                // 发放失败
                return Result.build(Integer.valueOf(code), resultJson.get("message").toString());
            }else {
                TSubsidInfo entity = new TSubsidInfo();
                entity.setAuditor(body.getAuditor());
                entity.setInId(body.getInId());
                entity.setStatus(body.getStatus());
                int updateNum = tSubsidInfoMapper.updateSubsidInfo(entity);
                if (updateNum == 1){
                    log.info("补贴发放成功调用个推------>");
                    geTuiPushMessage(body, "补贴发放", head);
                    return Result.ok();
                }
            }
        }
        return Result.fail();
    }

    /**
     * 补贴申请--撤销
     *
     * @param record TSubsidInfo
     * @return Result
     * @author 黄贵川
     * @date 2021/08/24
     */
    @Override
    public Result cancelSubsidInfo(Request<TSubsidInfo> record) {
        TSubsidInfo body = record.getBody();
        log.info("DISP20210303补贴申请 撤销:" + JSONObject.toJSONString(body));
        AddOrdersByParam addOrdersByParam = new AddOrdersByParam();
        if (StringUtils.isNotEmpty(body.getCarType()) && "0".equals(body.getCarType())){
            // txnType 8补贴撤销 供应商
            addOrdersByParam.setTxnType("8");
        }else {
            // txnType 14补贴撤销 采购商
            addOrdersByParam.setTxnType("14");
        }

        // txnAmt 金额
        addOrdersByParam.setTxnAmt(body.getSubsidTtlAmt());
        // 进出场ID
        addOrdersByParam.setBusinessNo(body.getInId());

        Request request = new Request();
        RequestHead head = record.getHead();
        request.setHead(head);
        request.setBody(addOrdersByParam);
        Result result = restTemplate2.postForObject("http://" + "disPart-order" + "/securityCenter/DISP20210263", request, Result.class);
        if (request != null) {
            JSONObject resultJson = (JSONObject) JSONObject.toJSON(result);
            String code = resultJson.get("code").toString();
            if (!code.equals("200")) {
                // 撤销失败 状态设置为 3待撤回
                TSubsidInfo entity = new TSubsidInfo();
                entity.setInId(body.getInId());
                entity.setStatus("3");
                entity.setAuditor(body.getAuditor());
                int updateNum = tSubsidInfoMapper.updateSubsidInfo(entity);
                if (updateNum != 1){
                    return Result.fail();
                }
                log.info("撤销失败,状态修改为 待撤回code!=200");
                return Result.build("撤销失败,状态修改为 待撤回", EntranceResult_WEnum.FAIL);
            }else {
                TSubsidInfo entity = new TSubsidInfo();
                entity.setInId(body.getInId());
                entity.setStatus(body.getStatus());
                int updateNum = tSubsidInfoMapper.updateSubsidInfo(entity);
                if (updateNum == 1){
                    log.info("补贴撤回成功调用个推------>");
                    geTuiPushMessage(body, "补贴撤回", head);
                    return Result.ok();
                }
            }
        }
        return Result.fail();
    }

    /**
     * 查询品类
     *
     * @param record TSubsidInfo
     * @return Result
     * @author 黄贵川
     * @date 2021/20/25
     */
    @Override
    public Result findProductTypeInfo(Request<TSubsidInfo> record) {
        try {
            List<TProductTypeInfo> productTypeInfoList = tVechicleProcurerMapper.findProductTypeInfo();
            return Result.build(productTypeInfoList, EntranceResult_WEnum.SUCCESS);
        }catch (Exception e){
            log.error("查询品类异常", e);
            return Result.fail();
        }
    }

    /**
     * 进出场查询导出
     *
     * @author 黄贵川
     * @date 2021/10/28
     * @param record Request<D_0297FindDto>
     */
    @Override
    public Result<ExportCustomInfoOutDto> getExcel(Request<D_0297FindDto> record) {
        D_0297FindDto body = record.getBody();

        log.info("DISP20210351 进出场信息查询导出:" + JSONObject.toJSONString(body));
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

        List<D_0297FindDto> d_0297FindDtoList = null;
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

        if (d_0297FindDtoList == null || d_0297FindDtoList.size() <= 0) {
            log.info("DISP20210351 未查询到相关数据");
            return Result.build(null, ResultCodeEnum.DATA_NO_ERROR);
        }

        try {
            // 查询 进出场的商品信息
            List<TVechicleProcurerDetails> tVechicleProcurerDetailsList =
                    tVechicleProcurerDetailsMapper.findByD0297FindDtoList(d_0297FindDtoList);

            if (tVechicleProcurerDetailsList != null && tVechicleProcurerDetailsList.size() > 0){
                for (D_0297FindDto d_0297FindDto : d_0297FindDtoList) {
                    List<TVechicleProcurerDetails> list = new ArrayList<>(1);

                    for (TVechicleProcurerDetails tVechicleProcurerDetails : tVechicleProcurerDetailsList) {
                        if (tVechicleProcurerDetails.getInId().equals(d_0297FindDto.getInId())){
                            list.add(tVechicleProcurerDetails);
                            d_0297FindDto.setTVechicleProcurerDetailsList(list);
                            break;
                        }
                    }
                }
            }
        }catch (Exception e){
            log.error("查询商品信息异常", e);
            return Result.build(201, "查询商品信息失败");
        }

        try {
            String excelFileName = ("0".equals(body.getCarType()) ? "供应商" : "采购商") + "进出场查询";
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

            // 列名  0供应商 1采购商
            String[] cellNames;
            if ("0".equals(body.getCarType())){
                cellNames = new String[]{"进场id", "进场时间", "出场时间", "客户名称", "客户编码", "手机号", "入口", "出口",
                        "车牌号", "车辆皮重(千克)", "进场品种重量(千克)", "进场总重量(千克)", "出场总重量(千克)", "客户车型", "品种", "品类",
                        "一级品类", "产地", "进场操作人", "出场操作人", "状态", "进场优惠金额(元)", "进场实收金额(元)", "进场支付方式",
                        "进场支付状态", "出场去皮应收金额(元)", "出场去皮应退金额(元)", "停车收费金额(元)", "出场优惠金额(元)", "出场实收/退金额(元)",
                        "出场支付方式", "出场支付状态", "进场备注", "出场备注"};
            } else {
                cellNames = new String[]{"进场编号", "进场时间", "出场时间", "客户名称", "客户编码", "手机号", "进口", "出口",
                        "车牌号", "车辆皮重(千克)", "出场货物重量(千克)", "总重量(千克)", "采购品种", "客户车型", "是否免停车费",
                        "状态", "进场操作人", "出场操作人", "停车收费金额", "出场优惠金额", "出场实收金额", "出场支付方式", "出场支付状态"};
            }

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

            // 供应商
            if ("0".equals(body.getCarType())){
                excelMethodList(d_0297FindDtoList, hssfsheet, tableStyle, dateStyle2);
            } else {
                excelMethodList2(d_0297FindDtoList, hssfsheet, tableStyle, dateStyle2);
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
                    if (workBook != null){
                        workBook.close();
                    }
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

            log.info("DISP20210351 进出场信息查询导出文件成功");
            ExportCustomInfoOutDto outBody = new ExportCustomInfoOutDto();
            outBody.setFileUrl((String) result.getData());
            return Result.build(outBody, SUCCESS);
        }catch (Exception e){
            log.error("DISP20210351 进出场信息查询导出文件异常", e);
            return Result.fail();
        }
    }

    /**
     * 供应商
     *
     * @param d_0297FindDtoList List<D_0297FindDto>
     * @param hssfsheet HSSFSheet
     * @param tableStyle HSSFCellStyle
     */
    private void excelMethodList(List<D_0297FindDto> d_0297FindDtoList, HSSFSheet hssfsheet, HSSFCellStyle tableStyle,
                                 HSSFCellStyle dateStyle2) {
        HSSFRow nextRow;
        HSSFCell hssfcell;

        // 状态
        String statusName = "";
        String paymentModeName = "";
        String outPaymentModeName = "";
        String inPayStatusName = "";
        String outPayStatusName = "";
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

            if(StringUtils.isNotEmpty(item.getPaymentMode())){
                // 进场支付方式 支付模式  1-HSB,2-现金,3-pos银行卡,4-一卡通,5-银行卡,6pos二维码(pos扫码)
                switch (item.getPaymentMode()){
                    case "1": {
                        paymentModeName = "HSB";
                        break;
                    }
                    case "2": {
                        paymentModeName = "现金";
                        break;
                    }
                    case "3": {
                        paymentModeName = "pos银行卡";
                        break;
                    }
                    case "4": {
                        paymentModeName = "一卡通";
                        break;
                    }
                    case "5": {
                        paymentModeName = "银行卡";
                        break;
                    }
                    case "6": {
                        paymentModeName = "pos扫码";
                        break;
                    }
                    default: {
                        paymentModeName = "";
                        break;
                    }
                }
            }else {
                paymentModeName = "";
            }

            if(StringUtils.isNotEmpty(item.getOutPaymentMode())){
                // 出场支付方式 支付模式  1-HSB,2-现金,3-pos银行卡,4-一卡通,5-银行卡,6pos二维码(pos扫码)
                switch (item.getOutPaymentMode()){
                    case "1": {
                        outPaymentModeName = "HSB";
                        break;
                    }
                    case "2": {
                        outPaymentModeName = "现金";
                        break;
                    }
                    case "3": {
                        outPaymentModeName = "pos银行卡";
                        break;
                    }
                    case "4": {
                        outPaymentModeName = "一卡通";
                        break;
                    }
                    case "5": {
                        outPaymentModeName = "银行卡";
                        break;
                    }
                    case "6": {
                        outPaymentModeName = "pos扫码";
                        break;
                    }
                    default: {
                        outPaymentModeName = "";
                        break;
                    }
                }
            }else {
                outPaymentModeName = "";
            }

            nextRow = hssfsheet.createRow(i++);
            int k = 0;

            // 1进场id
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(item.getInId());
            hssfcell.setCellStyle(tableStyle);

            // 2进场时间
            hssfcell = nextRow.createCell(k++);
            if (!ObjectUtils.isEmpty(item.getInTime())) {
                hssfcell.setCellValue(item.getInTime());
            }
            hssfcell.setCellStyle(dateStyle2);

            // 出场时间
            hssfcell = nextRow.createCell(k++);
            if (!ObjectUtils.isEmpty(item.getOutTime())) {
                hssfcell.setCellValue(item.getOutTime());
            }
            hssfcell.setCellStyle(dateStyle2);

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

            // 出场总重量(千克)
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(item.getOutTtlWght() == null ? "" : "" + item.getOutTtlWght());
            hssfcell.setCellStyle(tableStyle);

            // 客户车型
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue("供货车");
            hssfcell.setCellStyle(tableStyle);

            TVechicleProcurerDetails tVechicleProcurerDetails =
                    item.getTVechicleProcurerDetailsList() != null ? item.getTVechicleProcurerDetailsList().get(0) : null;
            // 品种
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(tVechicleProcurerDetails != null && StringUtils.isNotEmpty(tVechicleProcurerDetails.getPrdctNm())
                    ? tVechicleProcurerDetails.getPrdctNm() : "");
            hssfcell.setCellStyle(tableStyle);

            // 品类
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(tVechicleProcurerDetails != null && StringUtils.isNotEmpty(tVechicleProcurerDetails.getCategoryNm())
                    ? tVechicleProcurerDetails.getCategoryNm() : "");
            hssfcell.setCellStyle(tableStyle);

            // 品类
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(tVechicleProcurerDetails != null && StringUtils.isNotEmpty(tVechicleProcurerDetails.getOneCategoryNm())
                    ? tVechicleProcurerDetails.getOneCategoryNm() : "");
            hssfcell.setCellStyle(tableStyle);

            // 产地
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(tVechicleProcurerDetails != null && StringUtils.isNotEmpty(tVechicleProcurerDetails.getProdPlace())
                    ? tVechicleProcurerDetails.getProdPlace() : "");
            hssfcell.setCellStyle(tableStyle);

            // 进场操作人
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(StringUtils.isEmpty(item.getInOperName()) ? "" : item.getInOperName());
            hssfcell.setCellStyle(tableStyle);

            // 出场操作人
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(StringUtils.isEmpty(item.getOutOperName()) ? "" : item.getOutOperName());
            hssfcell.setCellStyle(tableStyle);

            // 状态
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(statusName);
            hssfcell.setCellStyle(tableStyle);

            // 进场优惠金额
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(item.getInAmt() == null ? "0" : "" + item.getInAmt());
            hssfcell.setCellStyle(tableStyle);

            // 进场实收金额
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(item.getInActAmt() == null ? "0" : "" + item.getInActAmt());
            hssfcell.setCellStyle(tableStyle);

            // 进场支付方式
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(paymentModeName);
            hssfcell.setCellStyle(tableStyle);

            // 进场支付状态 0-待支付 1-支付失败 2-支付成功
            String inPayStatus = item.getInPayStatus();
            if (StringUtils.isNotEmpty(inPayStatus)){
                switch (inPayStatus){
                    case "0": {
                        inPayStatusName = "待支付";
                        break;
                    }
                    case "1": {
                        inPayStatusName = "支付失败";
                        break;
                    }
                    case "2": {
                        inPayStatusName = "支付成功";
                        break;
                    }
                    default: {
                        inPayStatusName = "";
                        break;
                    }
                }
            }else {
                inPayStatusName = "";
            }
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(inPayStatusName);
            hssfcell.setCellStyle(tableStyle);

            // 出场去皮应收金额
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(item.getPeelSupptAmt() == null ? "0" : "" + item.getPeelSupptAmt());
            hssfcell.setCellStyle(tableStyle);

            // 出场去皮应退金额
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(item.getPeelReturnAmt() == null ? "0" : "" + item.getPeelReturnAmt());
            hssfcell.setCellStyle(tableStyle);

            // 停车收费金额
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(item.getParkAmt() == null ? "0" : "" + item.getParkAmt());
            hssfcell.setCellStyle(tableStyle);

            // 出场优惠金额
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(item.getPreferPrice() == null ? "0" : "" + item.getPreferPrice());
            hssfcell.setCellStyle(tableStyle);

            // 出场实收/退金额
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(item.getActRecevAmt() == null ? "0" : "" + item.getActRecevAmt());
            hssfcell.setCellStyle(tableStyle);

            // 出场支付方式
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(outPaymentModeName);
            hssfcell.setCellStyle(tableStyle);

            // 出场支付状态 0-待支付 1-支付失败 2-支付成功
            String outPayStatus = item.getInPayStatus();
            if (StringUtils.isNotEmpty(outPayStatus)){
                switch (inPayStatus){
                    case "0": {
                        outPayStatusName = "待支付";
                        break;
                    }
                    case "1": {
                        outPayStatusName = "支付失败";
                        break;
                    }
                    case "2": {
                        outPayStatusName = "支付成功";
                        break;
                    }
                    default: {
                        outPayStatusName = "";
                        break;
                    }
                }
            }else {
                outPayStatusName = "";
            }
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(outPayStatusName);
            hssfcell.setCellStyle(tableStyle);

            // 进场备注
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(StringUtils.isEmpty(item.getInRemark()) ? "" : item.getInRemark());
            hssfcell.setCellStyle(tableStyle);

            // 出场备注
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(StringUtils.isEmpty(item.getRemark()) ? "" : item.getRemark());
            hssfcell.setCellStyle(tableStyle);
        }
    }

    /**
     * 采购商
     *
     * @param d_0297FindDtoList
     * @param hssfsheet
     * @param tableStyle
     */
    private void excelMethodList2(List<D_0297FindDto> d_0297FindDtoList, HSSFSheet hssfsheet, HSSFCellStyle tableStyle,
                                  HSSFCellStyle dateStyle2) {
        HSSFRow nextRow;
        HSSFCell hssfcell;
        String statusName = "";
        String outPaymentModeName = "";
        String outPayStatusName = "";
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

            if(StringUtils.isNotEmpty(item.getOutPayStatus())){
                // 出场支付状态 0-待支付 1-支付失败 2-支付成功
                switch (item.getOutPayStatus()){
                    case "0": {
                        outPayStatusName = "待支付";
                        break;
                    }
                    case "1": {
                        outPayStatusName = "支付失败";
                        break;
                    }
                    case "2": {
                        outPayStatusName = "支付成功";
                        break;
                    }
                    case "9": {
                        outPayStatusName = "未支付";
                        break;
                    }
                    default: {
                        outPayStatusName = "";
                        break;
                    }
                }
            }else {
                outPayStatusName = "";
            }

            nextRow = hssfsheet.createRow(i++);
            int k = 0;

            // 进场id
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(item.getInId());
            hssfcell.setCellStyle(tableStyle);

            // 进场时间
            hssfcell = nextRow.createCell(k++);
            if (!ObjectUtils.isEmpty(item.getInTime())){
                hssfcell.setCellValue(item.getInTime());
            }
            hssfcell.setCellStyle(dateStyle2);

            // 出场时间
            hssfcell = nextRow.createCell(k++);
            if(!ObjectUtils.isEmpty(item.getOutTime())){
                hssfcell.setCellValue(item.getOutTime());
            }
            hssfcell.setCellStyle(dateStyle2);

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

            // 进口
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

            // 出场货物重量(千克)
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(item.getOutCargoWght() == null ? "" : "" + item.getOutCargoWght());
            hssfcell.setCellStyle(tableStyle);

            // 总重量(千克)
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(item.getOutTtlWght() == null ? "" : "" + item.getOutTtlWght());
            hssfcell.setCellStyle(tableStyle);

            // 采购品种
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(StringUtils.isEmpty(item.getPurchPrdt()) ? "" : "" + item.getPurchPrdt());
            hssfcell.setCellStyle(tableStyle);

            // 客户车型
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue("采购车");
            hssfcell.setCellStyle(tableStyle);

            // 是否免停车费
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(0 == item.getIsParkFree() ? "免停车费" : "");
            hssfcell.setCellStyle(tableStyle);

            // 状态
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(statusName);
            hssfcell.setCellStyle(tableStyle);

            // 进场操作人
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(StringUtils.isEmpty(item.getInOperName()) ? "" : item.getInOperName());
            hssfcell.setCellStyle(tableStyle);

            // 出场操作人
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(StringUtils.isEmpty(item.getOutOperName()) ? "" : item.getOutOperName());
            hssfcell.setCellStyle(tableStyle);

            // 停车收费金额
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(item.getParkActAmt() == null ? "0" : "" + item.getParkActAmt());
            hssfcell.setCellStyle(tableStyle);

            // 出场优惠金额
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(item.getPreferPrice() == null ? "0" : "" + item.getPreferPrice());
            hssfcell.setCellStyle(tableStyle);

            // 出场实收金额
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(item.getActRecevAmt() == null ? "0" : "" + item.getActRecevAmt());
            hssfcell.setCellStyle(tableStyle);

            // 出场支付模式  1--HSB,2--现金,3--pos银行卡,4--一卡通,5--银行卡,6--pos二维码
            if(StringUtils.isNotEmpty(item.getOutPaymentMode())){
                switch (item.getOutPaymentMode()){
                    case "1": {
                        outPaymentModeName = "HSB";
                        break;
                    }
                    case "2": {
                        outPaymentModeName = "现金";
                        break;
                    }
                    case "3": {
                        outPaymentModeName = "pos银行卡";
                        break;
                    }
                    case "4": {
                        outPaymentModeName = "一卡通";
                        break;
                    }
                    case "5": {
                        outPaymentModeName = "银行卡";
                        break;
                    }
                    case "6": {
                        outPaymentModeName = "pos二维码";
                        break;
                    }
                    default: {
                        outPaymentModeName = "";
                        break;
                    }
                }
            } else {
                outPaymentModeName = "";
            }
            // 出场支付方式
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(outPaymentModeName);
            hssfcell.setCellStyle(tableStyle);

            // 出场支付状态
            hssfcell = nextRow.createCell(k++);
            hssfcell.setCellValue(outPayStatusName);
            hssfcell.setCellStyle(tableStyle);

            //登记日期
            /*hssfcell = nextRow.createCell(k++);
            if (!ObjectUtils.isEmpty(vo.getCreatTime())) {
                hssfcell.setCellValue(vo.getCreatTime());
            }
            hssfcell.setCellStyle(dateStyle2);*/
        }
    }

    /**
     * 设置单元格边框
     *
     * @param style
     */
    private void setBorder(HSSFCellStyle style) {
        style.setBorderTop(BorderStyle.MEDIUM);//上边框
        style.setBorderBottom(BorderStyle.MEDIUM);//下边框
        style.setBorderLeft(BorderStyle.MEDIUM);//左边框
        style.setBorderRight(BorderStyle.MEDIUM);//右边框
    }

    /**
     * 个推 消息推送
     *
     * @author 黄贵川
     * @param body TSubsidInfo
     * @param value1 String
     * @param head RequestHead
     */
    private void geTuiPushMessage(TSubsidInfo body, String value1, RequestHead head) {
        try {
            // 个推
            TPushNotesVo tPushNotesVo = new TPushNotesVo();
            tPushNotesVo.setBusineNo(body.getInId());
            tPushNotesVo.setUserId(body.getUserId());
            tPushNotesVo.setProvId(body.getProvId());
            tPushNotesVo.setParameterType(3);
            List<String> parameterList = new ArrayList<>(3);
            parameterList.add(body.getCardNo().substring(body.getCardNo().length() - 6));
            parameterList.add(value1);
            parameterList.add(body.getSubsidTtlAmt().toString());
            tPushNotesVo.setParameterList(parameterList);

            Request requestGeTui = new Request();
            requestGeTui.setBody(tPushNotesVo);
            requestGeTui.setHead(head);
            Result resultGeTui = restTemplate2.postForObject("http://" + "disPart-busine-commons" + "/securityCenter/DISP20210310", requestGeTui, Result.class);
            if (resultGeTui != null) {
                JSONObject resultGeTuiJson = (JSONObject) JSONObject.toJSON(resultGeTui);
                String codeGeTui = resultGeTuiJson.get("code").toString();
                if (!codeGeTui.equals("200")) {
                    log.info("补贴发放/撤回调用个推成功code=200------>");
                    log.error(resultGeTuiJson.get("data").toString());
                    //return Result.build(resultGeTuiJson.get("data").toString(), EntranceResult_WEnum.FAIL);
                }
            }
        }catch (Exception e){
            log.error("补贴调用个推服务异常", e);
        }
    }
}
