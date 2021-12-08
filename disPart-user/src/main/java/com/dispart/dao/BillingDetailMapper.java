package com.dispart.dao;

import com.dispart.dto.billDto.*;
import com.dispart.model.BillingDetail;
import com.dispart.model.PayJrn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface BillingDetailMapper {

    String getDataRole(@Param("roleId")String roleId, @Param("menuId") String menuId);

    Integer getBillNumSeq();

    Integer addBill(BillInsertInDto billingDetail);

    Integer selectBillsCount(BillSelectionInDto selectionInDto);

    ArrayList<DISP20210307OutDto> selectBills(BillSelectionInDto selectionInDto);

    PayJrn getNewestPayInfo(@Param("billNum") String billNum);

    Integer abolishBill(BillUpdateInDto billUpdateInDto);

    ArrayList<BillPayItemsOutDto> selectPayItems(BillSelectionInDto inDto);

    Integer updateBillPayStatus(DISP20210332InDto inDto);

}