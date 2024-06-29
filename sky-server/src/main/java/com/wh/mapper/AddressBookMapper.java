package com.wh.mapper;

import com.wh.entity.AddressBookEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 地址簿Mapper层接口
 */
@Mapper
public interface AddressBookMapper {

    /**
     * 查询当前登录用户的所有地址信息
     *
     * @param addressBook 地址簿实体类
     * @return 当前登录用户的所有地址信息
     */
    List<AddressBookEntity> queryAllAddressBook(AddressBookEntity addressBook);
}
