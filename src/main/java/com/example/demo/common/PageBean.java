package com.example.demo.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//分页返回结果对象
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBean <T>{
    private Long total;//总条数
    private List<Object> items;//当前页数据集合

    public PageBean(long total, List<Object> items){
        this.total = total;
        this.items = items;
    }
}