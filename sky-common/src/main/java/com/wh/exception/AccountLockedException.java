package com.wh.exception;

/**
 * 账号被锁定异常
 */
public class AccountLockedException extends BaseException{

    public AccountLockedException(){}
    public AccountLockedException(String message) {
        super(message);
    }
}
