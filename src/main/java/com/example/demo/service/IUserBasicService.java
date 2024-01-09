package com.example.demo.service;

import com.example.demo.common.Result;
import com.example.demo.entity.UserBasic;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.catalina.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2023-12-21
 */
public interface IUserBasicService extends IService<UserBasic> {

    Result insert(UserBasic userBasic, String code);

    Result login(String email, String password);

    Result updatePassword(String email,String passwd, String code);

    Result getUserDetail(String identity);

    Result getList();
}
