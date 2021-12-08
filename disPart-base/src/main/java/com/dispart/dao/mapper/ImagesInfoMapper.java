package com.dispart.dao.mapper;

import com.dispart.dto.imageInfoDto.ImageSelectInVo;
import com.dispart.model.ImagesInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ImagesInfoMapper {

    @Select("select nextval(#{seqName})")
    int getImageId(String seqName);

    int deleteByPrimaryKey(String imageId);

    int insert(ImagesInfo record);

    int updateByPrimaryKey(ImagesInfo record);

    List<ImagesInfo> selectImagesInfo(ImageSelectInVo inVo);

    Integer selectImagesInfoCount(ImageSelectInVo inVo);
}