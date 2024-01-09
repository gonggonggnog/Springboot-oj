package com.example.demo.service;

import com.example.demo.entity.SubmitBasic;
import com.example.demo.mapper.ProblemsBasicMapper;
import com.example.demo.mapper.SubmitBasicMapper;
import com.example.demo.mapper.UserBasicMapper;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AscnyMethod {
    @Resource
    SubmitBasicMapper submitBasicMapper;
    @Resource
    ProblemsBasicMapper problemsBasicMapper;
    @Resource
    UserBasicMapper userBasicMapper;
    @Async
    public  void updateSubmitNum(String submitIdentity,String userIdentity, String problemIdentity, Integer status) {
        //添加提交记录
        submitBasicMapper.insert(new SubmitBasic(submitIdentity,userIdentity,problemIdentity, status));
        //更改用户信息和问题信息
        problemsBasicMapper.updateSubmitNum(problemIdentity,status==1);
        userBasicMapper.updateSubmitNum(userIdentity,status==1);
    }
}
