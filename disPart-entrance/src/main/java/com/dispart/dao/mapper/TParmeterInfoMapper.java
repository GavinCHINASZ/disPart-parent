package com.dispart.dao.mapper;

import com.dispart.vo.commons.TParmeterInfo;

/**
 * 车辆月卡信息
 */
public interface TParmeterInfoMapper {
    /**
     * select by PARAM_TYPE and PARAM_NM
     * @param tParmeterInfo
     * @return
     */
    public TParmeterInfo selectByTypeNum(TParmeterInfo tParmeterInfo);
}
