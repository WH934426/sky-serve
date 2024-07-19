package com.wh.mapper;

import com.wh.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

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

    /**
     * 根据条件查询用户
     *
     * @param map 查询条件map集合
     * @return 用户数量
     */
    Integer sumUserByMap(Map<String, Object> map);
}
