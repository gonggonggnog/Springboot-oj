package com.example.demo.mapper;

import com.example.demo.entity.UserBasic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2023-12-21
 */
public interface UserBasicMapper extends BaseMapper<UserBasic> {
    @Update("update user_basic set password = #{password} where email = #{email}")
    int updatePassword(String email, String password);

    int updateSubmitNum(String identity,boolean isRight);
}
