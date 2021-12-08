package com.dispart.service;

import com.dispart.model.base.CarBlackList;
import com.dispart.result.Result;
import com.sun.org.apache.xpath.internal.objects.XString;
import org.apache.ibatis.annotations.Param;

public interface CarBlackListService {
    Result inserCarBlackList(@Param("carBlackList") CarBlackList carBlackList, String operId);
    Result deteCarBlackList(@Param("carBlackList") CarBlackList carBlackList);
    Result updateCarBlackList(@Param("carBlackList") CarBlackList carBlackList, String operId);
    Result selectCarBlackList(@Param("carBlackList") com.dispart.model.base.CarBlackList carBlackList);
}
