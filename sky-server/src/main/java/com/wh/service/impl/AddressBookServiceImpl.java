package com.wh.service.impl;

import com.wh.context.BaseContext;
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

    /**
     * 新增地址
     *
     * @param addressBook 地址簿信息
     */
    @Override
    public void addAddressBook(AddressBookEntity addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(0);
        addressBookMapper.addAddressBook(addressBook);
    }

    /**
     * 根据id查询地址
     *
     * @param id id
     * @return 地址簿信息
     */
    @Override
    public AddressBookEntity getAddressBookById(Long id) {
        return addressBookMapper.getAddressBookById(id);
    }

    /**
     * 根据id修改地址
     *
     * @param addressBook 要求改的地址信息
     */
    @Override
    public void updateAddressBookByUserId(AddressBookEntity addressBook) {
        addressBookMapper.updateAddressBookByUserId(addressBook);
    }

    /**
     * 根据id删除地址
     *
     * @param id 地址id
     */
    @Override
    public void deleteAddressBookById(Long id) {
        addressBookMapper.deleteAddressBookById(id);
    }

    /**
     * 设置默认地址
     *
     * @param addressBook 地址簿信息
     */
    @Override
    public void setDefaultAddress(AddressBookEntity addressBook) {
        // 将该用户的所有地址设为非默认
        addressBook.setIsDefault(0);
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.updateIsDefaultByUserId(addressBook);

        // 将用户当前地址设为默认
        addressBook.setIsDefault(1);
        addressBookMapper.updateAddressBookByUserId(addressBook);
    }
}
