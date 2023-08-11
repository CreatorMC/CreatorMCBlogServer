package com.creator.handler.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.creator.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = null;
        //注册时没有用户，不可能会有用户id，从而引发异常
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            userId = -1L;   //表示是自己创建
        }
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("createBy", userId, metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            //获取不到当前用户id
            //情况：在程序启动后会定时执行把Redis中的文章浏览量保存到数据库中的操作，此时不是用户发送的请求，故没有相应的token，所以获取不到。
            //throw new RuntimeException(e);
            return;
        }
        //防止定时任务设置文章的更新时间
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }
}
