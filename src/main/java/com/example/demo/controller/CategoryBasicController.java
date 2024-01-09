package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

import com.example.demo.common.Result;
import com.example.demo.service.ICategoryBasicService;
import com.example.demo.entity.CategoryBasic;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-12-21
 */
@RestController
@RequestMapping("/category-basic")
public class CategoryBasicController {

    @Resource
    private ICategoryBasicService categoryBasicService;

    @PostMapping
    @Operation(summary = "新增或修改")
    public Result save(@RequestParam String name) {
        CategoryBasic categoryBasic = new CategoryBasic();
        categoryBasic.setName(name);
        return categoryBasicService.save(categoryBasic) ? Result.success() : Result.error();
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "删除")
    public Result delete(@PathVariable Integer id) {
        return categoryBasicService.removeById(id) ? Result.success() : Result.error();
    }

    @GetMapping
    @Operation(summary = "查询所有")
    public Result findAll() {
        return Result.success(categoryBasicService.list());
    }

}

