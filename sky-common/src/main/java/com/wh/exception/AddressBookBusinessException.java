package com.wh.exception;

/**
 * 地址簿业务异常
 */
public class AddressBookBusinessException extends BaseException {
    public AddressBookBusinessException() {
    }

    public AddressBookBusinessException(String message) {
        super(message);
    }
}
