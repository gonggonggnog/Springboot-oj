package com.example.demo.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component

@Configuration
public class DateConfig implements MetaObjectHandler {

    /**
     * 使用mp做添加操作时候，这个方法执行
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //设置属性值
        this.setFieldValByName("createdAt",new Date(),metaObject);
        this.setFieldValByName("updatedAt",new Date(),metaObject);
        this.setFieldValByName("submitNum",0,metaObject);
        this.setFieldValByName("passNum",0,metaObject);
        this.setFieldValByName("isAdmin",0,metaObject);
        this.setFieldValByName("identity", UUID.randomUUID().toString(),metaObject);
    }

    /**
     * 使用mp做修改操作时候，这个方法执行
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updatedAt",new Date(),metaObject);
    }
}