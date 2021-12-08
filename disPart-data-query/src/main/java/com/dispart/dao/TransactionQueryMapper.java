package com.dispart.dao;

import com.dispart.dto.dataquery.DISP20210216OutDto;
import com.dispart.dto.dataquery.Disp20210336InDto;
import com.dispart.dto.dataquery.Disp20210336OutDto;
import com.dispart.dto.transactionDto.*;
import com.dispart.model.PayJrn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.ArrayList;

@Mapper
public interface TransactionQueryMapper {

    Integer transQueryCount(TransactionSelectInDto inDto);

    ArrayList<PayJrn> transQuery(TransactionSelectInDto inDto);

    Integer chargeTransQueryCount(TransactionSelectInDto inDto);

    ArrayList<ChargeQueryOutDto> chargeTransQuery(TransactionSelectInDto inDto);

    Integer areaInOutQueryCount(TransactionSelectInDto inDto);

    ArrayList<DISP20210216OutDto> areaInOutQuery(TransactionSelectInDto inDto);

    Integer frazeTransQueryCount(FrazeTransInDto inDto);

    ArrayList<FrazeTransOutDto> frazeTransQuery(FrazeTransInDto inDto);

    Integer adjustTransQueryCount(AdjustTransInDto inDto);

    BigDecimal getAdjustAmt(AdjustTransInDto inDto);

    ArrayList<AdjustTransOutDto> adjustTransQuery(AdjustTransInDto inDto);

    Integer getAccnoDailyCount(Disp20210336InDto inDto);

    ArrayList<Disp20210336OutDto> getAccnoDaily(Disp20210336InDto inDto);

    Integer getAccnoRevereApplyCount(TransactionSelectInDto inDto);

    ArrayList<ChargeQueryOutDto> getAccnoRevereApplyInfo(TransactionSelectInDto inDto);

}
