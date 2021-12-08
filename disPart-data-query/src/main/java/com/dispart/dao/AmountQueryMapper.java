package com.dispart.dao;


import com.dispart.dto.dataquery.Disp20210066InDto;
import com.dispart.dto.dataquery.Disp20210066OutMx;
import com.dispart.dto.dataquery.Disp20210067InDto;
import com.dispart.dto.dataquery.Disp20210067OutMx;
import com.dispart.dto.dataquery.Disp20210068InDto;
import com.dispart.dto.dataquery.Disp20210068OutMx;
import com.dispart.entity.DatabaseCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface AmountQueryMapper {

    public ArrayList<Disp20210066OutMx> queryTransactionAmount(Disp20210066InDto inDto);

    public ArrayList<Disp20210067OutMx> querySettlementAmount(Disp20210067InDto inDto);

    public ArrayList<Disp20210068OutMx> queryUnSettlementAmount(Disp20210068InDto inDto);

    public DatabaseCount queryTransactionAmount_count(Disp20210066InDto inDto);

    public DatabaseCount querySettlementAmount_count(Disp20210067InDto inDto);

    public DatabaseCount queryUnSettlementAmount_count(Disp20210068InDto inDto);

    @Select("select USER_PHONE from t_user_info where USER_ID = #{userId}")
    public String getPhoneNum(String userId);

    @Select("select merchantcode from base_merchant where telno = #{phoneNum} ")
    public String getProvId(String phoneNum);
}
