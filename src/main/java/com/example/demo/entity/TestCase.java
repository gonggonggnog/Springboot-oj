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
  @TableName("test_case")
@ApiModel(value = "TestCase对象", description = "")
public class TestCase implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String identity;

    private String problemIdentity;

    private String input;

    private String output;

    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;

    public TestCase(String identity,String problemIdentity, String input, String output) {
        this.identity = identity;
        this.problemIdentity = problemIdentity;
        this.input = input;
        this.output = output;
    }
    public TestCase() {
    }
}
