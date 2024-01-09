package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

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
 * @since 2023-12-23
 */
@Getter
@Setter
  @TableName("problem_category")
@ApiModel(value = "ProblemCategory对象", description = "")
public class ProblemCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer problemId;

    private Integer categoryId;

    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;

    @TableField(exist = false)
    private CategoryBasic categoryBasic;
    public ProblemCategory(Integer problemId,Integer categoryId) {
        this.problemId = problemId;
        this.categoryId = categoryId;
    }
    public ProblemCategory() {
    }
}
