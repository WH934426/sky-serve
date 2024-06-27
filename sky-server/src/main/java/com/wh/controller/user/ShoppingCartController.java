package com.wh.controller.user;

import com.wh.dto.ShoppingCartDTO;
import com.wh.entity.ShoppingCartEntity;
import com.wh.result.Result;
import com.wh.service.ShoppingCartService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车Controller层控制器
 */
@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Resource
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     *
     * @param shoppingCartDTO 购物车给前端展示的dto对象
     * @return 提示信息
     */
    @PostMapping("/add")
    public Result<Void> addShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加购物车的数据信息:{}", shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    /**
     * 查看购物车
     *
     * @return 购物车列表
     */
    @PostMapping("/list")
    public Result<List<ShoppingCartEntity>> showShoppingCart() {
        List<ShoppingCartEntity> shoppingCartList = shoppingCartService.showShoppingCart();
        return Result.success(shoppingCartList);
    }

    /**
     * 清空购物车
     *
     * @return 提示信息
     */
    @DeleteMapping("/clean")
    public Result<String> cleanShoppingCart() {
        shoppingCartService.cleanShoppingCart();
        return Result.success();
    }
}
