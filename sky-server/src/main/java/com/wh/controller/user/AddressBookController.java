package com.wh.controller.user;

import com.wh.context.BaseContext;
import com.wh.entity.AddressBookEntity;
import com.wh.result.Result;
import com.wh.service.AddressBookService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 地址簿Controller控制器
 */
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

    /**
     * 根据id查询地址
     *
     * @param id 地址id
     * @return 封装后的地址信息
     */
    @GetMapping("/{id}")
    public Result<AddressBookEntity> getAddressBookById(@PathVariable Long id) {
        log.info("根据id查询地址:{}", id);
        AddressBookEntity addressBook = addressBookService.getAddressBookById(id);
        return Result.success(addressBook);
    }

    /**
     * 根据id修改地址
     *
     * @param addressBook 要修改的地址信息
     * @return 提示信息
     */
    @PutMapping
    public Result<String> updateAddressBookByUserId(@RequestBody AddressBookEntity addressBook) {
        log.info("修改地址:{}", addressBook);
        addressBookService.updateAddressBookByUserId(addressBook);
        return Result.success();
    }

    /**
     * 根据id删除地址
     *
     * @param id 地址id
     * @return 提示信息
     */
    @DeleteMapping
    public Result<String> deleteAddressBookById(Long id) {
        log.info("被删除地址信息的id:{}", id);
        addressBookService.deleteAddressBookById(id);
        return Result.success();
    }

    /**
     * 设置默认地址
     *
     * @param addressBook 要修改的地址信息
     * @return 提示信息
     */
    @PutMapping("/default")
    public Result<String> setDefaultAddress(@RequestBody AddressBookEntity addressBook) {
        log.info("修改默认地址:{}", addressBook);
        addressBookService.setDefaultAddress(addressBook);
        return Result.success();
    }

    /**
     * 查询当前用户的默认地址信息
     *
     * @return 若存在默认地址，则返回该地址实体的Result对象；否则返回错误信息的Result对象
     */
    @GetMapping("/default")
    public Result<AddressBookEntity> getDefaultAddress() {
        // 构建查询条件，默认地址标志为1
        AddressBookEntity addressBook = new AddressBookEntity();
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(1);

        // 执行查询
        List<AddressBookEntity> defaultAddresses = addressBookService.queryAllAddressBook(addressBook);

        // 如果查询结果不为空且只有一个，默认认为第一个即为默认地址
        if (!CollectionUtils.isEmpty(defaultAddresses) && defaultAddresses.size() == 1) {
            return Result.success(defaultAddresses.get(0));
        }

        // 未找到默认地址时的处理
        return Result.error("未查询到默认地址信息");
    }
}
