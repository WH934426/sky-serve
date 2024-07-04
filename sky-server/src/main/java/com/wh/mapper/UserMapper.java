package com.wh.mapper;

import com.wh.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户接口
 */
@Mapper
public interface UserMapper {

    /**
     * 根据openid查询用户
     *
     * @param openid 微信授权唯一openid
     * @return 用户信息
     */
    @Select("select * from user where openid=#{openid}")
    UserEntity getUserByOpenid(String openid);

    /**
     * 添加用户
     *
     * @param user 用户信息
     */
    void addUser(UserEntity user);

    /**
     * 根据id查询用户
     *
     * @param userId 用户id
     * @return 用户信息
     */
    @Select("select * from user where id=#{id}")
    UserEntity getUserById(Long userId);
}
