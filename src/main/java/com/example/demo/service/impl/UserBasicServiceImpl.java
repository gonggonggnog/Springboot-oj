package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.Result;
import com.example.demo.entity.UserBasic;
import com.example.demo.mapper.UserBasicMapper;
import com.example.demo.service.IUserBasicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.utils.tokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-12-21
 */
@Service
public class UserBasicServiceImpl extends ServiceImpl<UserBasicMapper, UserBasic> implements IUserBasicService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    UserBasicMapper userBasicMapper;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public Result insert(UserBasic userBasic, String code) {
        Result response;
        QueryWrapper<UserBasic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", userBasic.getEmail());
        if (getOne(queryWrapper) != null) {
            response = Result.error("邮箱已存在");
            return response;
        }
        String  redisCode = stringRedisTemplate.opsForValue().get(userBasic.getEmail());
        if (redisCode == null || !redisCode.equals(code)) {
            response = Result.error("验证码错误");
            return response;
        }
        userBasic.setPassword(passwordEncoder.encode(userBasic.getPassword()));
        String token = tokenUtils.getToken(userBasic);
        return save(userBasic) ? Result.success(Map.of(
                "token", token,
                "is_admin", userBasic.getIsAdmin(),
                "identity", userBasic.getIdentity()
        )) : Result.error("注册失败");
    }

    @Override
    public Result login(String email, String password) {
        Result response;
        QueryWrapper<UserBasic> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        UserBasic userBasic = getOne(queryWrapper);
        if (userBasic == null) {
            response = Result.error("邮箱不存在");
            return response;
        }
        if (!passwordEncoder.matches(password, userBasic.getPassword())) {
            response = Result.error("密码错误");
            return response;
        }
        String token = tokenUtils.getToken(userBasic);
        return Result.success(Map.of(
                "token", token,
                "is_admin", userBasic.getIsAdmin(),
                "identity", userBasic.getIdentity()
        ));
    }

    @Override
    public Result updatePassword(String email,String passwd, String code) {
        passwordEncoder.encode(passwd);
        return userBasicMapper.
                updatePassword(email, passwd) == 1 ? Result.success("修改成功") : Result.error("修改失败");
    }

    @Override
    public Result getUserDetail(String identity) {
        QueryWrapper<UserBasic> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(UserBasic.class, info -> !info.getColumn().equals("password")); //排除密码
        queryWrapper.eq("identity", identity);
        UserBasic userBasic = getOne(queryWrapper);
        return Result.success(userBasic);
    }

    @Override
    public Result getList() {
        QueryWrapper<UserBasic> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(UserBasic.class, info -> !info.getColumn().equals("password")); //排除密码
        return Result.success(list(queryWrapper));
    }
}
