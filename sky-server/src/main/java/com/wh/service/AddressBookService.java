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

    /**
     * 根据id查询地址
     *
     * @param id id
     * @return 地址簿信息
     */
    AddressBookEntity getAddressBookById(Long id);

    /**
     * 根据id修改地址
     *
     * @param addressBook 要求改的地址信息
     */
    void updateAddressBookByUserId(AddressBookEntity addressBook);

    /**
     * 根据id删除地址
     *
     * @param id 地址id
     */
    void deleteAddressBookById(Long id);
}
