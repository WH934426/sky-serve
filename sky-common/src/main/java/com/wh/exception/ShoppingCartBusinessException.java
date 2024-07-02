package com.wh.exception;

/**
 * 购物车业务异常
 */
public class ShoppingCartBusinessException extends BaseException {
    public ShoppingCartBusinessException() {
    }

    public ShoppingCartBusinessException(String message) {
        super(message);
    }
}
