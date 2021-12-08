package com.dispart.dao;

import com.dispart.vo.busineCommon.TPushNotesVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * TPushNotesDao
 *
 * @author 黄贵川
 * @date 2021/08/28
 */
@Mapper
public interface TPushNotesDao {
    /**
     * 新增消息
     *
     * @param  tPushNotesVo TPushNotesVo
     * @author 黄贵川
     * @date 2021/08/28
     * @return int
     */
    int insertTPushNotes(TPushNotesVo tPushNotesVo);

    /**
     * 查询消息list
     *
     * @param  pushNotesVo TPushNotesVo
     * @author 黄贵川
     * @date 2021/08/28
     * @return List<TPushNotesVo>
     */
    List<TPushNotesVo> findPushNotesList(TPushNotesVo pushNotesVo);

    /**
     * 查询消息数量
     *
     * @param  pushNotesVo TPushNotesVo
     * @author 黄贵川
     * @date 2021/08/28
     * @return Integer
     */
    Integer findPushNotesCount(TPushNotesVo pushNotesVo);

    /**
     * 更新消息
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param body 请求参数
     * @return Integer
     */
    Integer updatePushNotes(TPushNotesVo body);
}