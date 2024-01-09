package com.example.demo.service;

import com.example.demo.common.Result;
import com.example.demo.entity.SubmitBasic;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2023-12-21
 */
public interface ISubmitBasicService extends IService<SubmitBasic> {

    Result getSubmitList(Integer pageNum, Integer pageSize, Integer status);
}
