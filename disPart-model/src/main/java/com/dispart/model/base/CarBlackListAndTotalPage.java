package com.dispart.model.base;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.List;

@Data
public class CarBlackListAndTotalPage {
    private Integer tolPageNum;
    List<CarBlackList> carBlackListList;
}
