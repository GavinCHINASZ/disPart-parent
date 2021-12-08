package com.dispart.dao;

import com.dispart.dto.GnydMerchantsInfoDto;

import java.util.List;

/**
 * 商户贷款信息
 */
public interface GnydMerchantsInfoMapper {

    int addloanInfo(GnydMerchantsInfoDto gnydMerchantsInfoDto);

    int updateLoanInfo(GnydMerchantsInfoDto gnydMerchantsInfoDto);

    GnydMerchantsInfoDto queryByPK(GnydMerchantsInfoDto gnydMerchantsInfoDto);


}
