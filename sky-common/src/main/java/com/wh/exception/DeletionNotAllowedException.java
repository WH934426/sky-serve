package com.wh.exception;

public class DeletionNotAllowedException extends BaseException{
    public DeletionNotAllowedException(){}

    public DeletionNotAllowedException(String message){
        super(message);
    }
}
