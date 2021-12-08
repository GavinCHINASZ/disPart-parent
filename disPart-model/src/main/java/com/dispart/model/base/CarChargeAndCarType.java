package com.dispart.model.base;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CarChargeAndCarType {
    String vehicleTp;
    String vehicleTpId;
    String vehicleId;
    List<CarChargeStdManage>carChargeStdManage=new ArrayList<>();
}
