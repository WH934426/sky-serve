package com.wh.handler;

import com.wh.constant.MessageConstant;
import com.wh.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有异常
     * @param e 异常类型
     * @return 异常信息
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e){
        log.error(e.getMessage());
        return Result.error(StringUtils.hasLength(e.getMessage())? e.getMessage() : "操作失败");
    }

    /**
     * 处理SQL异常
     * @param e 异常类型
     * @return 异常信息
     */
    @ExceptionHandler
    public Result<String> handleException(SQLIntegrityConstraintViolationException e) {
        String message = e.getMessage();
        if (message.contains("Duplicate entry")) {
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.ALREADY_EXISTS;
            return Result.error(msg);
        } else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }
}
