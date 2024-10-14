package com.wh.exception;

/**
 * 密码修改失败异常
 */
public class PasswordEditFailedException extends BaseException {
    public PasswordEditFailedException() {
    }

    public PasswordEditFailedException(String message) {
        super(message);
    }
}
