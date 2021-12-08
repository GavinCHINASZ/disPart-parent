package com.dispart.service.discounts;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dispart.dto.menudto.*;
import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.vo.base.DiscountsActivityVo;
import com.dispart.vo.base.DiscountsDetailVo;
import com.dispart.vo.base.DiscountsUserVo;
import com.dispart.vo.basevo.MenuVo;

import java.util.List;

public interface DiscountsActivityService {

    /**
     * 优惠活动 新增
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param Request<ParmeterSelectInVo>
     * @return Result
     */
    Result insertDiscountsActivity(Request<DiscountsActivityVo> param);

    /**
     * 优惠活动 查询
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param Request<ParmeterSelectInVo>
     * @return Result
     */
    Result selectDiscountsActivity(Request<DiscountsActivityVo> param);

    /**
     * 优惠活动 删除
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param Request<ParmeterSelectInVo>
     * @return Result
     */
    Result deleteDiscountsActivity(Request<DiscountsActivityVo> param);

    /**
     * 优惠活动 修改
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param Request<ParmeterSelectInVo>
     * @return Result
     */
    Result updateDiscountsActivity(Request<DiscountsActivityVo> param);

    /**
     * 优惠活动详情 新增
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param Request<DiscountsDetailVo>
     * @return Result
     */
    Result insertDiscountsDetail(Request<DiscountsDetailVo> param);

    /**
     * 优惠活动详情 删除
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param Request<DiscountsDetailVo>
     * @return Result
     */
    Result deleteDiscountsDetail(Request<DiscountsDetailVo> param);

    /**
     * 查询优惠金额
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param Request<ParmeterSelectInVo>
     * @return Result
     */
    Result selectDiscountsDetailPrice(Request<DiscountsDetailVo> param);

    /**
     * 保存用户参加优惠活动记录
     *
     * @author 黄贵川
     * @date 2021-09-08
     * @param param Request<ParmeterSelectInVo>
     * @return Result
     */
    Result insertDiscountsUser(Request<DiscountsUserVo> param);
}
