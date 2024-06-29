package com.wh.service;

import com.wh.entity.AddressBookEntity;

import java.util.List;

/**
 * 地址簿 Service层接口
 */
public interface AddressBookService {

    /**
     * 查询当前登录用户的所有地址信息
     *
     * @param addressBook 地址簿信息
     * @return 当前登录用户的所有地址信息
     */
    List<AddressBookEntity> queryAllAddressBook(AddressBookEntity addressBook);

    /**
     * 新增地址
     *
     * @param addressBook 地址簿信息
     */
    void addAddressBook(AddressBookEntity addressBook);
}
