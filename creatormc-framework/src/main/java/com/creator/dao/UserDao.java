package com.creator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.creator.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-24 19:35:22
 */
@SuppressWarnings("UnusedReturnValue")
@Mapper
public interface UserDao extends BaseMapper<User> {

    @Update("update sys_user set " +
            "user_name = #{userName}, " +
            "nick_name = #{nickName}, " +
            "type = #{type}, " +
            "status = #{status}, " +
            "email = #{email}, " +
            "phonenumber = #{phonenumber}, " +
            "sex = #{sex}, " +
            "ban_end_time = #{banEndTime} " +
            "where id = #{id}")
    boolean updateByIdAndBanEndTime(User user);
}

