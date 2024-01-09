package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.common.PageBean;
import com.example.demo.common.Result;
import com.example.demo.entity.ProblemCategory;
import com.example.demo.entity.ProblemsBasic;
import com.example.demo.entity.TestCase;
import com.example.demo.mapper.ProblemsBasicMapper;
import com.example.demo.service.ICategoryBasicService;
import com.example.demo.service.IProblemCategoryService;
import com.example.demo.service.IProblemsBasicService;
import com.example.demo.service.ITestCaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-12-21
 */
@Service
public class ProblemsBasicServiceImpl extends ServiceImpl<ProblemsBasicMapper, ProblemsBasic> implements IProblemsBasicService {

    @Resource
    ITestCaseService testCaseService;
    @Resource
    IProblemCategoryService problemCategoryService;

    @Resource
    ProblemsBasicMapper problemsBasicMapper;

    @Resource
    ICategoryBasicService categoryBasicService;


    @Override
    public Result insert(ProblemsBasic problemsBasic, ArrayList<String> TestCase, ArrayList<Integer> CategoryIds) {
        if (!save(problemsBasic))
            return Result.error("创建失败");
        //获取问题id
        QueryWrapper<ProblemsBasic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("identity", problemsBasic.getIdentity());
        Integer ProblemId = getOne(queryWrapper).getId();
        //添加测试用例和分类
        for (String s : TestCase) {
            Map map;
            try {
                 map = JSONObject.parseObject(s, Map.class);
            }catch (Exception e){
                return Result.error("测试用例格式错误");
            }
            TestCase testCase = new TestCase(UUID.randomUUID().toString(), problemsBasic.getIdentity(),
                    (String) map.get("input"), (String) map.get("output"));
            if (!testCaseService.save(testCase))
                return Result.error("创建失败");
        }
        for (Integer categoryId : CategoryIds) {
            ProblemCategory problemCategory = new ProblemCategory(problemsBasic.getId(), categoryId);
            if (!problemCategoryService.save(problemCategory))
                return Result.error("创建失败");
        }
        return Result.success("创建成功");
    }

    @Override
    public Result update(ProblemsBasic ProblemsBasic, ArrayList<String> testCase, ArrayList<Integer> categoryIds) {
        //1.更新基本信息
        QueryWrapper<ProblemsBasic> problemQuery = new QueryWrapper<>();
        problemQuery.eq("id", ProblemsBasic.getId());
        ProblemsBasic problemsBasic = getOne(problemQuery);
        //复制修改信息
        problemsBasic.setContent(ProblemsBasic.getContent());
        problemsBasic.setMaxMem(ProblemsBasic.getMaxMem());
        problemsBasic.setTitle(ProblemsBasic.getTitle());
        problemsBasic.setMaxRuntime(ProblemsBasic.getMaxRuntime());

        if (!updateById(problemsBasic))
            return Result.error("更新失败");
        //2.删除相关分类和测试用例
        QueryWrapper<ProblemCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("problem_id", problemsBasic.getId());
        problemCategoryService.remove(queryWrapper);
        QueryWrapper<TestCase> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("problem_identity", problemsBasic.getIdentity());
        testCaseService.remove(queryWrapper1);
        //3.重新添加
        for (String s : testCase) {
            Map map = JSONObject.parseObject(s, Map.class);
            TestCase TestCase = new TestCase(UUID.randomUUID().toString(), problemsBasic.getIdentity(),
                    (String) map.get("input"), (String) map.get("output"));
            if (!testCaseService.save(TestCase))
                return Result.error("更新失败");
        }
        for (Integer categoryId : categoryIds) {
            ProblemCategory problemCategory = new ProblemCategory(problemsBasic.getId(), categoryId);
            if (!problemCategoryService.save(problemCategory))
                return Result.error("更新失败");
        }
        //4.返回成功
        return Result.success("更新成功");
    }

    @Override
    public Result getProblemDetail(String identity) {
        QueryWrapper<ProblemsBasic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("identity", identity);
        ProblemsBasic problemsBasic = null;
        try {
            problemsBasic = getOne(queryWrapper);
            //获取分类信息
            QueryWrapper<ProblemCategory> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("problem_id", problemsBasic.getId());
            List<ProblemCategory> problemCategories = problemCategoryService.list(queryWrapper1); //
            for (ProblemCategory problemCategory : problemCategories) {
                problemCategory.setCategoryBasic(categoryBasicService.getById(problemCategory.getCategoryId()));
            }
            problemsBasic.setProblemCategories(problemCategories);
            //获取测试用例
            QueryWrapper<TestCase> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("problem_identity",problemsBasic.getIdentity());
            List<TestCase> testCases = testCaseService.list(queryWrapper2);
            problemsBasic.setTestCases(testCases);
        } catch (Exception e) {
            return Result.error("未知错误"+ e.toString());
        }
        return Result.success(problemsBasic);
    }

    @Override
    public Result getProblemList(Integer pageNum, Integer pageSize, String keyword, Integer categoryId) {
        List<ProblemsBasic> problemList = new ArrayList<>();
        try {
            problemList = problemsBasicMapper.getProblemList((pageNum-1)*pageSize, pageSize, keyword, categoryId);
            for (ProblemsBasic problemsBasic : problemList) {
                QueryWrapper<ProblemCategory> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("problem_id", problemsBasic.getId());
                List<ProblemCategory> problemCategories = problemCategoryService.list(queryWrapper); //
                for (ProblemCategory problemCategory : problemCategories) {
                    problemCategory.setCategoryBasic(categoryBasicService.getById(problemCategory.getCategoryId()));
                }
                problemsBasic.setProblemCategories(problemCategories);
            }
        } catch (Exception e) {
            return Result.error("未知错误"+e.toString());
        }
        return Result.success(new PageBean(
                problemsBasicMapper.getProblemSize(keyword, categoryId),
                problemList));
    }
}
