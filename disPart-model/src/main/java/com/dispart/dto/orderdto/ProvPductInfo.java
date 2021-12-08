package com.dispart.dto.orderdto;

import lombok.Data;

import java.util.List;

@Data
public class ProvPductInfo {

    private String name;

    private String boothId;

    private String status;

    private List supportMode;

    private List productInventoryList;

}
