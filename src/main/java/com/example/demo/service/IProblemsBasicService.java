package com.example.demo.service;

import com.example.demo.common.Result;
import com.example.demo.entity.ProblemsBasic;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2023-12-21
 */
public interface IProblemsBasicService extends IService<ProblemsBasic> {

    Result insert(ProblemsBasic problemsBasic, ArrayList<String> testCase, ArrayList<Integer> CategoryIds);

    Result update(ProblemsBasic problemsBasic, ArrayList<String> testCase, ArrayList<Integer> categoryIds);

    Result getProblemDetail(String identity);

    Result getProblemList(Integer pageNum, Integer pageSize, String keyword,Integer categoryIdentity);

}
