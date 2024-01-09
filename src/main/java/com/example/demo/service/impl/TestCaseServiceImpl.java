package com.example.demo.service.impl;

import com.example.demo.entity.TestCase;
import com.example.demo.mapper.TestCaseMapper;
import com.example.demo.service.ITestCaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-12-23
 */
@Service
public class TestCaseServiceImpl extends ServiceImpl<TestCaseMapper, TestCase> implements ITestCaseService {

}
