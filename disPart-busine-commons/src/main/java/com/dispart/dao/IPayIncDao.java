package com.dispart.dao;

import com.dispart.model.MCardInfo;
import com.dispart.model.PayJrn;
import com.dispart.model.VechicleMonthPayDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IPayIncDao {

    Integer updateBillStatus(PayJrn payJrn);

    Integer updateMCardStatus(PayJrn payJrn);

    VechicleMonthPayDetails selectMCardPayDetail(PayJrn payJrn);

    MCardInfo getMcardInfo(VechicleMonthPayDetails param);

    Integer updateMCardInfo(MCardInfo mCardInfo);

    Integer updateInStatus(PayJrn payJrn);

    Integer updateOutStatus(PayJrn payJrn);

}
