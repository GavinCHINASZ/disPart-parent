package com.dispart.model.base;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CarChargeAndManage {
    Integer tolPageNum;
    List<CarTypeManage> carTypeManage=new ArrayList<>();
}
