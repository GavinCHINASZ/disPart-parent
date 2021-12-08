package com.dispart.service;

import com.dispart.dto.departmentdto.DISP20210019DepFindByParamInDto;
import com.dispart.model.base.PageInfo;
import com.dispart.model.base.PayItemManage;
import com.dispart.result.Result;
import org.apache.ibatis.annotations.Param;

public interface PayItemManageService {
    /**
     * 缴费项目数据写入
     * @param payItemManage
     * @return
     */
    Result insertPayItem(PayItemManage payItemManage);

    /**
     * 缴费项目数据修改
     * @param payItemManage
     * @param payItemManage
     * @return
     */
    Result updatePayItem(PayItemManage payItemManage);
    /**
     * 通过缴费项目Id或缴费项目名称查询
     * @param payItemManage
     * @return
     */
    Result findPayItemByPayIdOrPayNm(PayItemManage payItemManage);
    /**
     * 缴费项目数据删除
     * @param payId
     * @return
     */
    Result deletePayItemByPayId(String payId);
    /**
     * 机构编号及名称查询
     * @return
     */
    Result selectDepInfo(DISP20210019DepFindByParamInDto depID);
    Result selectPayItemSub();
}
