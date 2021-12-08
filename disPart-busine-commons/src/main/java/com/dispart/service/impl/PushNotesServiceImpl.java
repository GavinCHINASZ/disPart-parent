package com.dispart.service.impl;

import com.alibaba.fastjson.JSON;
import com.dispart.dao.TPushNotesDao;
import com.dispart.dto.entrance.PushNotesListDto;
import com.dispart.request.Request;
import com.dispart.result.EntranceResult_WEnum;
import com.dispart.result.Result;
import com.dispart.result.ResultCodeEnum;
import com.dispart.service.PushNotesService;
import com.dispart.vo.busineCommon.TPushNotesVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * 推送消息
 *
 * @author 黄贵川
 * @date 2021-09-04
 * @version 1.0
 */
@Service
@Slf4j
public class PushNotesServiceImpl implements PushNotesService {
    @Resource
    private TPushNotesDao tPushNotesDao;

    /**
     * 查询推送的历史消息
     *
     * @author 黄贵川
     * @date 2021-09-04
     * @param param 请求参数
     * @return Result
     */
    @Override
    public Result findPushNotesList(Request<TPushNotesVo> param) {
        log.info("DISP20210315 查询推送的历史消息 param:" + JSON.toJSONString(param));

        TPushNotesVo body = param.getBody();
        // 有分页参数才做分页查询
        if (body.getCurPage() != null && body.getCurPage() > 0 && body.getPageSize() > 0) {
            Integer curPage = (body.getCurPage() - 1) * body.getPageSize();
            body.setCurPage(curPage);
        } else {
            body.setPageSize(0);
        }

        PushNotesListDto pushNotesListDto = new PushNotesListDto();
        try {
            List<TPushNotesVo> pushNotesList = tPushNotesDao.findPushNotesList(body);
            if (pushNotesList == null || pushNotesList.size() <= 0) {
                return Result.build("", ResultCodeEnum.DATA_NO_ERROR);
            }
            pushNotesListDto.setList(pushNotesList);

            if (body.getPageSize() > 0) {
                Integer toleNum = tPushNotesDao.findPushNotesCount(body);
                pushNotesListDto.setTolPageNum(toleNum);
            }
        }catch (DataAccessException e) {
            log.error("查询推送的历史消息查询异常", e);
            return Result.fail("查询推送的历史消息查询异常");
        }
        log.info("DISP20210315 查询推送的历史消息成功");
        return Result.build(pushNotesListDto, EntranceResult_WEnum.SUCCESS);
    }

    /**
     * 更新消息
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param 请求参数
     * @return Result
     */
    @Override
    public Result updatePushNotes(Request<TPushNotesVo> param) {
        log.info("DISP20210316 更新消息 param:" + JSON.toJSONString(param.getBody()));
        try {
            Integer updateNum = tPushNotesDao.updatePushNotes(param.getBody());
            if (updateNum != 1){
                return Result.fail();
            }
        }catch (Exception e){
            log.error("DISP20210316 更新消息 findPushNotesCount失败");
            return Result.fail();
        }
        log.info("DISP20210316 更新消息成功");
        return Result.ok();
    }
}