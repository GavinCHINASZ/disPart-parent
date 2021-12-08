package com.dispart.dto.imageInfoDto;

import lombok.Data;

@Data
public class ImageSelectInVo {

    private String belong;

    private String imageId;

    private String title;

    private String imageNm;

    private Integer pageNum;

    private Integer pageSize;
}
