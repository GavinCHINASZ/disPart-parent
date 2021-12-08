package com.dispart.dao.mapper;

import com.dispart.dto.departmentdto.DISP20210019DepFindByParamInDto;
import com.dispart.model.base.PageInfo;
import com.dispart.model.base.PayItemManage;
import com.dispart.model.base.PayItemSub;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Mapper
public interface PayItemManageMapper {
    Integer insertPayItem(@Param("payItemManage")PayItemManage payItemManage);
    Integer updatePayItem(@Param("payItemManage") PayItemManage payItemManage);
    List<PayItemManage> findPayItemByPayIdOrPayNm(@Param("payItemManage")PayItemManage payItemManage);
    Integer deletePayItemByPayId(@Param("payId") String payId);
    Integer selectPayItemCount();
    Integer selectPayItemCountByByPayIdOrPayNm(@Param("payId") String payId,@Param("payItem") String payItem);
    DISP20210019DepFindByParamInDto selectDepInfo(String depId);
    List<PayItemSub> selectPayItemSub();
    List<PayItemSub> selectPayItemSub1(@Param("payId") String payId);
    List<PayItemManage> selectIfExitByPayId(@Param("payId") String payId);
}
