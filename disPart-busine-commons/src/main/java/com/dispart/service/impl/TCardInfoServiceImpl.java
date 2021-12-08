package com.dispart.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dispart.model.businessCommon.TCardInfo;
import com.dispart.dao.TCardInfoMapper;
import com.dispart.service.TCardInfoService;

@Service
public class TCardInfoServiceImpl extends ServiceImpl<TCardInfoMapper, TCardInfo> implements TCardInfoService{

//    @Autowired
//    private TPayJrnMapper tPayJrnMapper;
//
//    @Autowired
//    private TAccnoInfoMapper tAccnoInfoMapper;
//
//    @Override
//    public Result<Object> insertPayJrn(InsertPayJrnDTO params) {
//        log.debug("新增流水 +" + JSON.toJSONString(params));
//        if (StringUtil.isNullOrEmpty(params.getTxnType())) {
//            return Result.build(PARAMS_ERROR.getCode(), PARAMS_ERROR.getMessage());
//        }
//
//        if (StringUtil.isNullOrEmpty(params.getTransMd())) {
//            return Result.build(PARAMS_ERROR.getCode(), PARAMS_ERROR.getMessage());
//        }
//
//        if (params.getTxnAmt() == null || params.getTxnAmt().compareTo(BigDecimal.ZERO) < 0) {
//            return Result.build(PARAMS_ERROR.getCode(), PARAMS_ERROR.getMessage());
//        }
//
//        if (StringUtil.isNullOrEmpty(params.getPayerNo())) {
//            return Result.build(PARAMS_ERROR.getCode(), PARAMS_ERROR.getMessage());
//        }
//
//        if (StringUtil.isNullOrEmpty(params.getPayAcct())) {
//            return Result.build(PARAMS_ERROR.getCode(), PARAMS_ERROR.getMessage());
//        }
//
//        TPayJrn tPayJrn = new TPayJrn();
//        try {
//            BeanUtils.copyProperties(tPayJrn,params);
//            Map map = baseMapper.queryJnrlNum();
//            Integer jrnlNum = (Integer) map.get("jrnlNum");
//
//            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMdd");
//            LocalDateTime localDateTime = LocalDateTime.now();
//            String newLocalDateTime = localDateTime.format(dtf);
//            String rightAppend = String.valueOf(jrnlNum);
//            if (rightAppend.length() < 7) {
//                rightAppend = String.format("%7d", jrnlNum).replace(" ", "0");
//            } else {
//                rightAppend = rightAppend.substring(rightAppend.length() - 7, rightAppend.length());
//            }
//
//            String jrnl = newLocalDateTime + rightAppend;
//            tPayJrn.setJrnlNum(jrnl);
//
//            Date date = new Date();
//            tPayJrn.setUpTime(date);
//            tPayJrn.setCreatTime(date);
//            if (params.getTransMd().equals(CASH.getTransMDStatus()) || params.getTransMd().equals(CARD.getTransMDStatus())) {
//                tPayJrn.setTxnTm(date);
//                tPayJrn.setStatus(SUCCESS.getCardInfoStatus());
//            }
//
//            int insert = tPayJrnMapper.insert(tPayJrn);
//            if (insert != 1) {
//                log.error("插入数据库失败 + " + JSON.toJSONString(params));
//                return Result.build(JNML_INSERT_ERROR.getCode(), JNML_INSERT_ERROR.getMessage());
//            }
//
//            return Result.ok();
//
//        }catch (Exception e) {
//            log.error("系统异常",e);
//            return Result.build(SYS_ERROR.getCode(),SYS_ERROR.getMessage());
//        }
//    }
//
//    @Transactional
//    @Override
//    public Result<Object> updatePayJrnAndInsertCardInfo(UpdatePayJrnAndInsertCardInfoDTO updatePayJrnAndInsertCardInfoDTO) {
//        log.debug("更新流水状态 +" + JSON.toJSONString(updatePayJrnAndInsertCardInfoDTO));
//        if (StringUtil.isNullOrEmpty(updatePayJrnAndInsertCardInfoDTO.getOrderStatus())) {
//            return Result.build(PARAMS_ERROR.getCode(), PARAMS_ERROR.getMessage());
//        }
//
//        if (StringUtil.isNullOrEmpty(updatePayJrnAndInsertCardInfoDTO.getMainOrderId())) {
//            return Result.build(PARAMS_ERROR.getCode(), PARAMS_ERROR.getMessage());
//        }
//
//        if (updatePayJrnAndInsertCardInfoDTO.getTxnAmt() == null || updatePayJrnAndInsertCardInfoDTO.getTxnAmt().compareTo(BigDecimal.ZERO) < 0) {
//            return Result.build(PARAMS_ERROR.getCode(), PARAMS_ERROR.getMessage());
//        }
//
//        QueryWrapper<TPayJrn> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("BUSINESS_NO",updatePayJrnAndInsertCardInfoDTO.getMainOrderId());
//        TPayJrn tPayJrn = null;
//        try {
//            tPayJrn = tPayJrnMapper.selectOne(queryWrapper);
//            if (tPayJrn == null) {
//                log.error("找不到对应的流水记录 + " + JSON.toJSONString(updatePayJrnAndInsertCardInfoDTO));
//                return Result.build(JNML_SELECT_ERROR.getCode(), JNML_SELECT_ERROR.getMessage());
//            }
//        }catch (Exception e) {
//            log.error("系统异常",e);
//            return Result.build(SYS_ERROR.getCode(),SYS_ERROR.getMessage());
//        }
//
//        UpdateWrapper<TPayJrn> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq("BUSINESS_NO",updatePayJrnAndInsertCardInfoDTO.getMainOrderId());
//        if (updatePayJrnAndInsertCardInfoDTO.getOrderStatus().equals(OrderStatusEnum.SUCCESS.getOrderStatus())) {
//            tPayJrn.setStatus(SUCCESS.getCardInfoStatus());
//        } else {
//            tPayJrn.setStatus(FAIL.getCardInfoStatus());
//        }
//
//        tPayJrn.setTxnTm(new Date());
//
//        // 更新流水表
//        try {
//            int update = tPayJrnMapper.update(tPayJrn, updateWrapper);
//            if (update != 1) {
//                log.error("更新流水失败 + " + JSON.toJSONString(updatePayJrnAndInsertCardInfoDTO));
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                return Result.build(JNML_UPDATE_ERROR.getCode(), JNML_UPDATE_ERROR.getMessage());
//            }
//        } catch(Exception e) {
//            log.error("系统异常",e);
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            return Result.build(SYS_ERROR.getCode(),SYS_ERROR.getMessage());
//        }
//
//        // 记帐
//        TAccnoInfo tAccnoInfo = null;
//
//
//
//        return Result.ok();
//    }
}
