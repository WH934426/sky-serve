package com.wh.service.impl;

import com.wh.entity.AddressBookEntity;
import com.wh.mapper.AddressBookMapper;
import com.wh.service.AddressBookService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 地址簿 Service实现类
 */
@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Resource
    private AddressBookMapper addressBookMapper;

    /**
     * 查询当前登录用户的所有地址信息
     *
     * @param addressBook 地址簿实体
     * @return 当前登录用户的所有地址信息
     */
    @Override
    public List<AddressBookEntity> queryAllAddressBook(AddressBookEntity addressBook) {
        return addressBookMapper.queryAllAddressBook(addressBook);
    }
}
