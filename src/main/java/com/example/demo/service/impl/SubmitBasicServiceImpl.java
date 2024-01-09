package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.SubmitBasic;
import com.example.demo.mapper.SubmitBasicMapper;
import com.example.demo.service.ISubmitBasicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-12-21
 */
@Service
public class SubmitBasicServiceImpl extends ServiceImpl<SubmitBasicMapper, SubmitBasic> implements ISubmitBasicService {

    @Override
    public Result getSubmitList(Integer pageNum, Integer pageSize, Integer status) {
        QueryWrapper<SubmitBasic> queryWrapper = new QueryWrapper<>();
        if (status != null) {
            queryWrapper.eq("status",status);
        }
        return Result.success(page(new Page<>(pageNum,pageSize),queryWrapper));
    }
}
