package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author baomidou
 * @since 2023-12-21
 */
@Getter
@Setter
@TableName("problems_basic")
@ApiModel(value = "ProblemsBasic对象", description = "")
public class ProblemsBasic implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String identity;

    private String title;

    private String content;

    private Integer maxRuntime;

    private Integer maxMem;

    @TableField(fill = FieldFill.INSERT)
    private Integer submitNum;
    @TableField(fill = FieldFill.INSERT)
    private Integer passNum;

    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;

    @TableField(exist = false)
    private List<ProblemCategory> problemCategories;

    @TableField(exist = false)
    private List<TestCase> testCases;
    public ProblemsBasic(String identity,String title, String content, Integer maxRuntime, Integer maxMem) {
        this.identity = identity;
        this.title = title;
        this.content = content;
        this.maxRuntime = maxRuntime;
        this.maxMem = maxMem;
    }
    public ProblemsBasic(Integer id,String title, String content, Integer maxRuntime, Integer maxMem) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.maxRuntime = maxRuntime;
        this.maxMem = maxMem;
    }
    public ProblemsBasic() {
    }
}
