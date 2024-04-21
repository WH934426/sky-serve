package com.wh.exception;

/**
 * 基础异常类，后续所有异常类都继承该类
 */
public class BaseException extends RuntimeException {
    public BaseException() {
    }

    public BaseException(String msg) {
        super(msg);
    }
}
