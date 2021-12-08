package com.dispart.controller;

//import cn.hutool.core.util.StrUtil;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.dispart.dto.FindUserByParam;
//import com.dispart.vo.User;
//import com.dispart.result.Result;
//import com.dispart.service.UserService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wujie
 * @version 1.0.0:
 * @title UserController
 * @Description TODO
 * @dateTime 2021/6/3 15:24
 * @Copyright 2020-2021
 */
//@RestController
//@RequestMapping("/user")
//@Api(tags = "用户管理")
//@Slf4j
public class UserController {
//    @Autowired
//    private UserService userService;
//    @GetMapping("findAll")
//    @ApiOperation(value = "查找所有")
//    public Result findAllHospitalSet(){
//        List<User> list = userService.list();
//        Result<List<User>> ok = Result.ok(list);
//        log.info("查询成功");
//        return ok;
//    }
//    @PostMapping("findByParam")
//    @ApiOperation(value = "根据条件查找")
//    public Result<List<User>> findByParam(@RequestBody FindUserByParam findUserByParam){
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        if (StrUtil.isNotEmpty(findUserByParam.getUsername())) {
//            queryWrapper.like("username",findUserByParam.getUsername());
//        }
//        if (StrUtil.isNotEmpty(findUserByParam.getNickname())) {
//            queryWrapper.like("nick_name",findUserByParam.getNickname());
//        }
//
//        List<User> list = userService.list(queryWrapper);
//        Result<List<User>> ok = Result.ok(list);
//        log.info("查询成功");
//        return ok;
//    }
//
//    @DeleteMapping("{id}")
//    @ApiOperation(value = "根据id删除")
//    public Result deleteHospitalSet(@PathVariable("id")Long param){
//        boolean flag = userService.removeById(param);
//        if (flag) {
//            log.info("删除成功");
//            return Result.ok();
//        }else {
//            log.error("删除失败");
//            return Result.fail();
//
//        }
//    }
//
//    @PostMapping("addUser")
//    @ApiOperation(value = "增加用户")
//    public Result findAllHospitalSet(@RequestBody User user){
//        boolean save = userService.save(user);
//        if (save) {
//            log.info("增加成功");
//            return Result.ok();
//        }else {
//            log.error("增加失败");
//            return Result.fail();
//
//        }
//    }
//
//    @PutMapping("updateUser")
//    @ApiOperation(value = "修改用户信息")
//    public Result deleteHospitalSet(@RequestBody User user){
//        boolean flag = userService.updateById(user);
//        if (flag) {
//            log.info("修改成功");
//            return Result.ok();
//        }else {
//            log.error("修改失败");
//            return Result.fail();
//
//        }
//    }
//
//    /**
//     * 不需要token访问测试
//     * @return
//     */
//    @GetMapping("/test/no_need_token")
//    public @ResponseBody
//    String test() {
//        return "no_need_token";
//    }
//
//    /**
//     * 需要需要token访问接口测试
//     * @return
//     */
//    @GetMapping("/test/need_token")
//    public @ResponseBody String test2(Authentication authentication) {
//        log.info("{}",authentication);
//        // 由于自定义的principal返回的是包含全部user字段的map
//        Object principal = authentication.getPrincipal();
//        return "need_token";
//    }
//
//    /**
//     * 需要管理员权限
//     * @return
//     */
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    @GetMapping("/test/need_admin")
//    public @ResponseBody String admin() {
//        return "need_admin";
//    }
//    /**
//     * 需要普通权限
//     * @return
//     */
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    @GetMapping("/test/need_user")
//    public @ResponseBody String user() {
//        return "need_user";
//    }
}
