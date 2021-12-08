package com.dispart.dto.entrance;

import com.dispart.vo.busineCommon.TPushNotesVo;
import lombok.Data;

import java.util.List;

/**
 * 推送消息
 */
@Data
public class PushNotesListDto {
    private List<TPushNotesVo> list;

    // 总条数
    private Integer tolPageNum;
}