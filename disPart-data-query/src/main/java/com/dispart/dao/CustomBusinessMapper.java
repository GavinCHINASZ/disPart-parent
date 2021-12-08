package com.dispart.dao;

import com.dispart.dto.dataquery.Disp20210353InDto;
import com.dispart.vo.dataQuery.Disp20210353InfoOutVo;
import com.dispart.vo.dataQuery.Disp20210353OutVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CustomBusinessMapper {

    ArrayList<Disp20210353OutVo> getTxnInfoByPaymentMode(Disp20210353InDto inDto);

    Disp20210353InfoOutVo getTolTxnInfo(Disp20210353InDto inDto);

    Disp20210353InfoOutVo getTxnInfoWithServChrg(Disp20210353InDto inDto);
}
