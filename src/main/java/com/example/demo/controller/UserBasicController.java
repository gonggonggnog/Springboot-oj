package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.define;
import com.example.demo.entity.UserBasic;
import com.example.demo.service.IUserBasicService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-12-21
 */
@RestController
@RequestMapping("/user-basic")
public class UserBasicController {


    @Resource
    private IUserBasicService userBasicService;


    @PostMapping
    @Operation(summary = "修改")
    public Result update(@RequestParam String name,
                       @RequestParam String email,
                       @RequestParam String phone,
                       @RequestParam int id) {
        UserBasic userBasic = new UserBasic();
        userBasic.setId(id);
        userBasic.setName(name);
        userBasic.setEmail(email);
        userBasic.setPhone(phone);
        return userBasicService.updateById(userBasic) ? Result.success() : Result.error();
    }

    @PostMapping("/register")
    @Operation(summary = "注册")
    public Result register(@RequestParam String name,
                       @RequestParam String password,
                       @RequestParam String email,
                       @RequestParam String phone,
                       @RequestParam String code) {
        UserBasic userBasic = new UserBasic(UUID.randomUUID().toString(),email,password,name,phone);
        return userBasicService.insert(userBasic, code);
    }

    @PostMapping("/login")
    @Operation(summary = "登录")
    public Result login(@RequestParam("username") String email,
                        @RequestParam("password") String password) {
        return userBasicService.login(email, password);
    }

    @PostMapping("updatePassword")
    @Operation(summary = "修改密码")
    public Result updatePassword(@RequestParam String email,@RequestParam String passwd, @RequestParam String code ){
        return userBasicService.updatePassword(email,passwd,code);
    }

    @GetMapping("/{identity}")
    @Operation(summary = "查询单个")
    public Result findOne(@PathVariable String identity) {
        return userBasicService.getUserDetail(identity);
    }

    @GetMapping("/page")
    @Operation(summary = "排行榜")
    public Result findPage(@RequestParam(defaultValue = define.defaultPage) Integer pageNum,
                           @RequestParam(defaultValue = define.defaultPageSize) Integer pageSize) {
        QueryWrapper<UserBasic> wrapper = new QueryWrapper<>();
        wrapper.orderBy(true,false,"pass_num")
                .orderBy(true,true,"submit_num");
        return Result.success(userBasicService.page(new Page<>(pageNum, pageSize),wrapper));
    }
}

