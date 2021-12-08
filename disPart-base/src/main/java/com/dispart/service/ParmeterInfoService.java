package com.dispart.service;

import com.dispart.dto.parmeterdto.ParmeterInfoDto;
import com.dispart.dto.parmeterdto.ParmeterSelectInVo;
import com.dispart.result.Result;

public interface ParmeterInfoService{

    Result selectByPrimaryKey(ParmeterSelectInVo keys);

    Result updateByPrimaryKey(ParmeterInfoDto record);

    Result secretkeyInit();

}
