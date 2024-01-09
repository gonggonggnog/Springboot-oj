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
 * @since 2023-12-21
 */
@Getter
@Setter
@TableName("user_basic")
@ApiModel(value = "UserBasic对象", description = "")
public class UserBasic implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String identity;

    private String name;

    private String password;

    private String email;

    private String phone;

    @TableField(fill = FieldFill.INSERT)
    private Integer passNum;

    @TableField(fill = FieldFill.INSERT)
    private Integer submitNum;

    @TableField(fill = FieldFill.INSERT)
    private Integer isAdmin;

    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;

    public UserBasic(String identity,String email, String password, String name, String phone) {
        this.identity = identity;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }
    public UserBasic() {
    }
}
