package com.wh.controller.user;

import com.wh.context.BaseContext;
import com.wh.entity.AddressBookEntity;
import com.wh.result.Result;
import com.wh.service.AddressBookService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Slf4j
public class AddressBookController {

    @Resource
    private AddressBookService addressBookService;

    /**
     * 查询当前登录用户的所有地址信息
     *
     * @return 封装后的当前登录用户的所有地址信息
     */
    @GetMapping("/list")
    public Result<List<AddressBookEntity>> queryAllAddressBook() {
        AddressBookEntity addressBook = new AddressBookEntity();
        addressBook.setUserId(BaseContext.getCurrentId()); // 设置当前登录用户的id
        List<AddressBookEntity> addressBookList = addressBookService.queryAllAddressBook(addressBook);
        return Result.success(addressBookList);
    }

    /**
     * 新增地址
     *
     * @param addressBook 新增的地址信息
     * @return 提示信息
     */
    @PostMapping
    public Result<String> addAddressBook(@RequestBody AddressBookEntity addressBook) {
        log.info("新增地址:{}", addressBook);
        addressBookService.addAddressBook(addressBook);
        return Result.success();
    }
}
