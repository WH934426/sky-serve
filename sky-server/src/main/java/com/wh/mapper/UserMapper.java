package com.wh.mapper;

import com.wh.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户接口
 */
@Mapper
public interface UserMapper {

    @Select("select * from user where openid=#{openid}")
    UserEntity getUserByOpenid(String openid);

    /**
     * 添加用户
     * @param user 用户信息
     */
    void addUser(UserEntity user);
}
