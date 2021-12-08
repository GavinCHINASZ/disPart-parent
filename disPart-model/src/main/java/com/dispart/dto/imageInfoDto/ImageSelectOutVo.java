package com.dispart.dto.imageInfoDto;

import com.dispart.model.ImagesInfo;
import lombok.Data;

import java.util.List;

@Data
public class ImageSelectOutVo {

    private Integer tolPageNum;

    private List<ImagesInfo> imagesList;
}
