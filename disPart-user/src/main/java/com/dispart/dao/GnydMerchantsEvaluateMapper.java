package com.dispart.dao;

import com.dispart.dto.GnydMerchantsEvaluateDto;

import java.util.List;

/**
 * 市场方对客户评价表DAO
 */
public interface GnydMerchantsEvaluateMapper {

    int addEvalteInfo(GnydMerchantsEvaluateDto gnydMerchantsEvaluateDto);

    int updateEvalteInfo(GnydMerchantsEvaluateDto gnydMerchantsEvaluateDto);

    List<GnydMerchantsEvaluateDto> query(GnydMerchantsEvaluateDto gnydMerchantsEvaluateDto);

    int count(GnydMerchantsEvaluateDto gnydMerchantsEvaluateDto);

}
