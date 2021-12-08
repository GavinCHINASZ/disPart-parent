package com.dispart.dao;

import com.dispart.dto.dataquery.Disp20210209InDto;
import com.dispart.dto.dataquery.Disp20210209OutDto;
import com.dispart.model.CustomInfoManager;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface CustomInfoManagerMapper {

    ArrayList<Disp20210209OutDto> queryCustomInfoWithAccountAndCard(Disp20210209InDto inDto);

    ArrayList<Disp20210209OutDto> queryCustomInfoWithAccount(Disp20210209InDto inDto);

    ArrayList<Disp20210209OutDto> queryCustomInfo(Disp20210209InDto inDto);

    ArrayList<Disp20210209OutDto> queryCustomInfoByCardNo(Disp20210209InDto inDto);

    ArrayList<Disp20210209OutDto> queryCustomInfoWithCard(Disp20210209InDto inDto);
}