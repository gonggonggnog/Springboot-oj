package com.example.demo.mapper;

import com.example.demo.entity.ProblemsBasic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2023-12-21
 */
@Mapper
public interface ProblemsBasicMapper extends BaseMapper<ProblemsBasic> {
    List<ProblemsBasic> getProblemList(Integer page, Integer limit, String keyword, Integer categoryId);

    long getProblemSize(String keyword, Integer categoryId);
    boolean updateSubmitNum(String identity,boolean isRight);
}
