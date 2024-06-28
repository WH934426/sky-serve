package com.wh.controller.user;

import com.wh.service.AddressBookService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/addressBook")
@Slf4j
public class AddressBookController {

    @Resource
    private AddressBookService addressBookService;
}
