package com.wh.service.impl;

import com.wh.mapper.AddressBookMapper;
import com.wh.service.AddressBookService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 地址簿 Service实现类
 */
@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Resource
    private AddressBookMapper addressBookMapper;
}
