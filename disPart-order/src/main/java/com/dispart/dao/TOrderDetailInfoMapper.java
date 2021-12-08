package com.dispart.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dispart.model.order.TOrderDetailInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

@Mapper
public interface TOrderDetailInfoMapper extends BaseMapper<TOrderDetailInfo> {

    @Select("select nextval('orderId') as orderId")
    Map queryOrderId();

    @Select("select nextval('mainOrderId') as mainOrderId")
    Map queryMainOrderId();

    @Select("select nextval('HSBOrderId') as hsbOrderId")
    Map queryHSBOrderId();

    @Select("select param_val from t_parmeter_info where param_type = '02' and param_nm = 'hsb.marketNumber'")
    String getMarketId();

    @Select("select merchantcode from base_merchant where telno = #{telephoneNum} ")
    String queryProvId(String telephoneNum);

    @Select("select user_phone from t_user_info where user_id = #{userId} ")
    String queryUserTelephone(String userId);

    @Select("select param_val from t_parmeter_info where param_type = '04' and param_nm = 'wly.provId'")
    String getWuliuyuanProvId();

    @Select("select param_val from t_parmeter_info where param_type = '04' and param_nm = 'wly.provId2'")
    String getWuliuyuanProvId2();

//    @Select("select param_val from t_parmeter_info where param_type = '04' and param_nm = 'wly.provId2'")
//    String getWuliuyuanOtherId();

    @Select("select prov_id from t_custom_sign_info where merchant_code = #{merchantCode}")
    String getHSBProvId(String merchantCode);

    @Select("select status from t_membership_info where card_no = #{card}")
    String getCardStatus(String card);

    @Select("select t1.payment_trace_id from t_order_info t1 left join t_pay_jrn t2 on t1.main_order_id = t2.main_order_id where t2.main_order_id = #{businessNo}")
    String getPaymentTraceId(String businessNo);

    @Select("select t1.CCB_ORDER_DETAIL_ID from t_order_relevancy_info t1 left join t_pay_jrn t2 on t1.main_order_id = t2.main_order_id where t2.main_order_id = #{businessNo}")
    String getSubOrderNo(String businessNO);

    @Select("select in_pay_status from t_vechicle_procurer where in_id = #{businessNo}")
    String getVechicleProurerInStatus(String businessNo);

    @Select("select out_pay_status from t_vechicle_procurer where in_id = #{businessNo}")
    String getVechicleProurerOutStatus(String businessNo);

    @Select("select out_time from t_vechicle_procurer where in_id = #{businessNo}")
    String getVechicleOutTime(String businessNo);

    @Select("select payment_st from t_billing_detail where bill_num = #{businessNo} ")
    String getBillStatus(String businessNo);

    @Select("select payment_st from t_vechicle_month_pay_details where pay_order = #{businessNo}")
    String getMCardStatus(String businessNo);

    @Select("select param_val from t_parmeter_info where param_type = '07' and param_nm = 'hsb.prjId'")
    String getHsbprjId();

    @Select("select param_val from t_parmeter_info where param_type = '07' and param_nm = 'hsb.prjNm'")
    String getHsbpriNm();

//    @Update("update t_pay_jrn set status = ''")
//    int updatePayJrn(String jrnl);
}