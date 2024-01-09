package com.example.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.demo.common.Result;
import com.example.demo.service.IProblemsBasicService;
import com.example.demo.entity.ProblemsBasic;
import com.example.demo.define;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-12-21
 */
@RestController
@RequestMapping("/problems-basic")
public class ProblemsBasicController {

    @Resource
    private IProblemsBasicService problemsBasicService;

    @PostMapping("add")
    @Operation(summary = "新增问题")
    //{"input":"1 2","output":"3"}
    public Result save(@RequestParam String title, @RequestParam String content,
                       @RequestParam Integer maxRuntime, @RequestParam Integer maxMem,
                       @RequestParam ArrayList<String> test_case, @RequestParam ArrayList<Integer> CategoryIds) {
        ProblemsBasic problemsBasic =
                new ProblemsBasic(UUID.randomUUID().toString(), title, content, maxRuntime, maxMem);
        return problemsBasicService.insert(problemsBasic,test_case,CategoryIds);
    }

    @PostMapping("update")
    @Operation(summary = "更新问题")
    public Result update(@RequestParam Integer id,
            @RequestParam String title, @RequestParam String content,
            @RequestParam Integer maxRuntime, @RequestParam Integer maxMem,
            @RequestParam ArrayList<String> test_case, @RequestParam ArrayList<Integer> CategoryIds) {
        ProblemsBasic problemsBasic =
                new ProblemsBasic(id, title, content, maxRuntime, maxMem);
        return problemsBasicService.update(problemsBasic,test_case,CategoryIds);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "删除")
    public Result delete(@PathVariable Integer id) {
        return problemsBasicService.removeById(id) ? Result.success() : Result.error();
    }


    @GetMapping("/detail")
    @Operation(summary = "查询单个")
    public Result findOne(@RequestParam String identity) {
        return problemsBasicService.getProblemDetail(identity);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询")
    public Result findPage(@RequestParam(defaultValue = define.defaultPage) Integer pageNum,
                           @RequestParam(defaultValue = define.defaultPageSize) Integer pageSize,
                           @RequestParam(required = false) String keyword,
                           @RequestParam(required = false) Integer categoryId) {
        return problemsBasicService.getProblemList(pageNum, pageSize,keyword,categoryId);
    }

}

