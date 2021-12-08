package com.dispart.controller.discounts;

import com.dispart.request.Request;
import com.dispart.result.Result;
import com.dispart.service.discounts.DiscountsActivityService;
import com.dispart.vo.base.DiscountsActivityVo;
import com.dispart.vo.base.DiscountsDetailVo;
import com.dispart.vo.base.DiscountsUserVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 优惠活动
 *
 * @author 黄贵川
 * @date 2021-09-06
 * @version 1.0
 */
@Slf4j
@Api(tags = "优惠活动")
@RestController
@CrossOrigin
@RequestMapping("/securityCenter")
public class DiscountsActivityController {

    @Resource
    private DiscountsActivityService discountsActivityService;

    /**
     * 优惠活动 新增
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param Request<ParmeterSelectInVo>
     * @return Result
     */
    @PostMapping("/DISP20210318")
    public Result insertDiscountsActivity(@RequestBody Request<DiscountsActivityVo> param){
        return discountsActivityService.insertDiscountsActivity(param);
    }

    /**
     * 优惠活动 查询
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param Request<ParmeterSelectInVo>
     * @return Result
     */
    @PostMapping("/DISP20210319")
    public Result selectDiscountsActivity(@RequestBody Request<DiscountsActivityVo> param){
        return discountsActivityService.selectDiscountsActivity(param);
    }

    /**
     * 优惠活动 删除
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param Request<ParmeterSelectInVo>
     * @return Result
     */
    @PostMapping("/DISP20210320")
    public Result deleteDiscountsActivity(@RequestBody Request<DiscountsActivityVo> param){
        return discountsActivityService.deleteDiscountsActivity(param);
    }

    /**
     * 优惠活动 修改
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param Request<ParmeterSelectInVo>
     * @return Result
     */
    @PostMapping("/DISP20210321")
    public Result updateDiscountsActivity(@RequestBody Request<DiscountsActivityVo> param){
        return discountsActivityService.updateDiscountsActivity(param);
    }

    /**
     * 优惠活动详情 新增
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param Request<ParmeterSelectInVo>
     * @return Result
     */
    @PostMapping("/DISP20210322")
    public Result insertDiscountsDetail(@RequestBody Request<DiscountsDetailVo> param){
        return discountsActivityService.insertDiscountsDetail(param);
    }

    /**
     * 优惠活动详情 删除
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param Request<ParmeterSelectInVo>
     * @return Result
     */
    @PostMapping("/DISP20210323")
    public Result deleteDiscountsDetail(@RequestBody Request<DiscountsDetailVo> param){
        return discountsActivityService.deleteDiscountsDetail(param);
    }

    /**
     * 查询优惠金额
     *
     * @author 黄贵川
     * @date 2021-09-06
     * @param param Request<ParmeterSelectInVo>
     * @return Result
     */
    @PostMapping("/DISP20210324")
    public Result selectDiscountsDetailPrice(@RequestBody Request<DiscountsDetailVo> param){
        return discountsActivityService.selectDiscountsDetailPrice(param);
    }

    /**
     * 保存用户参加优惠活动记录
     *
     * @author 黄贵川
     * @date 2021-09-08
     * @param param Request<ParmeterSelectInVo>
     * @return Result

    @PostMapping("/DISP20210325")
    public Result insertDiscountsUser(@RequestBody Request<DiscountsUserVo> param){
        return discountsActivityService.insertDiscountsUser(param);
    }

     */
}
