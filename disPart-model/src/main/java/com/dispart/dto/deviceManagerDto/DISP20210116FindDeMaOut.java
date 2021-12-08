package com.dispart.dto.deviceManagerDto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DISP20210116FindDeMaOut {
    ArrayList<DISP20210116FindDeMa> deMaList = new ArrayList<>();
    //总条数
    private Integer tolPageNum;
}
