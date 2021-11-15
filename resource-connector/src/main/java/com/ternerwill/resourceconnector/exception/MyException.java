package com.ternerwill.resourceconnector.exception;

public class MyException extends RuntimeException{
    private String message;

    public MyException(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
