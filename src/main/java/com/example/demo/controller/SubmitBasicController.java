package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.SubmitBasic;
import com.example.demo.service.ISubmitBasicService;
import com.example.demo.define;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-12-21
 */
@RestController
@RequestMapping("/submit-basic")
public class SubmitBasicController {

    @Resource
    private ISubmitBasicService submitBasicService;

    @PostMapping
    @Operation(summary = "新增")
    public Result save(@RequestBody SubmitBasic submitBasic) {
        return submitBasicService.save(submitBasic) ? Result.success() : Result.error();
    }


    @GetMapping("/page")
    @Operation(summary = "分页查询")
    public Result findPage(@RequestParam(defaultValue = define.defaultPage) Integer pageNum,
                           @RequestParam(defaultValue = define.defaultPageSize) Integer pageSize,
                           @RequestParam(required = false) Integer status) {
        return submitBasicService.getSubmitList(pageNum,pageSize,status);
    }

}

