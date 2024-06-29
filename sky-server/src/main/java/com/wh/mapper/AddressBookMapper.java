package com.wh.mapper;

import com.wh.entity.AddressBookEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 新增地址
     *
     * @param addressBook 地址簿实体类
     */
    void addAddressBook(AddressBookEntity addressBook);

    /**
     * 根据 id查询地址
     *
     * @param id id
     * @return 地址簿信息
     */
    @Select("select * from address_book where id = #{id}")
    AddressBookEntity getAddressBookById(Long id);

    /**
     * 根据用户id修改地址
     *
     * @param addressBook 要修改的地址信息
     */
    void updateAddressBookByUserId(AddressBookEntity addressBook);

    /**
     * 根据id删除地址
     *
     * @param id 地址id
     */
    @Delete("delete from address_book where id=#{id}")
    void deleteAddressBookById(Long id);
}
